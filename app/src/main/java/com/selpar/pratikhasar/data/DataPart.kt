package com.selpar.pratikhasar.data

import java.io.IOException
import java.io.ByteArrayOutputStream
class DataPart (
    val fileName: String,
    val data: ByteArray,
    val mimeType: String
) {
    companion object {
        private const val CRLF = "\r\n"
        private const val BOUNDARY = "Volley-" + 100
        //"Volley-" + System.currentmilis

        /**
         * Returns the multi-part content type for a request with the given boundary string.
         */
        fun getContentType(): String {
            return "multipart/form-data;boundary=$BOUNDARY"
        }
    }

    /**
     * Converts this data part to a byte array representation for use in a multi-part form data request.
     */
    @Throws(IOException::class)
    fun toByteArray(): ByteArray {
        val bos = ByteArrayOutputStream()
        val dos = bos.bufferedWriter().use { it }

        // Add the boundary
        dos.write("--$BOUNDARY$CRLF")

        // Add the content disposition
        dos.write("Content-Disposition: form-data; name=\"file\"; filename=\"$fileName\"$CRLF")

        // Add the content type
        dos.write("Content-Type: $mimeType$CRLF")

        // Add the binary data
        dos.write(CRLF)
        dos.flush()
        bos.write(data)
        bos.flush()

        // Add the closing boundary
        dos.write(CRLF)
        dos.write("--$BOUNDARY--$CRLF")
        dos.flush()

        return bos.toByteArray()
    }
}