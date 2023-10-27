package com.selpar.pratikhasar.adapter

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.util.*

abstract class VolleyFileUploadRequest(
    method: Int,
    url: String,
    listener: Response.Listener<NetworkResponse>,
    errorListener: Response.ErrorListener
) : Request<NetworkResponse>(method, url, errorListener) {

    private val boundary = UUID.randomUUID().toString()
    private val LINE_FEED = "\r\n"
    private val CHARSET = "UTF-8"

    abstract override fun getParams(): MutableMap<String, String>
    abstract fun getByteData(): MutableMap<String, FileData>

    override fun getBodyContentType(): String {
        return "multipart/form-data;boundary=$boundary;charset=$CHARSET"
    }

    override fun getBody(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val writer = PrintWriter(OutputStreamWriter(outputStream, CHARSET), true)

        // Add parameters to request
        val params = getParams()
        for ((key, value) in params) {
            writer.append("--$boundary").append(LINE_FEED)
            writer.append("Content-Disposition: form-data; name=\"$key\"").append(LINE_FEED)
            writer.append("Content-Type: text/plain; charset=$CHARSET").append(LINE_FEED)
            writer.append(LINE_FEED)
            writer.append(value).append(LINE_FEED)
            writer.flush()
        }

        // Add file data to request
        val byteData = getByteData()
        for ((key, fileData) in byteData) {
            writer.append("--$boundary").append(LINE_FEED)
            writer.append("Content-Disposition: form-data; name=\"$key\"; filename=\"${fileData.fileName}\"")
                .append(LINE_FEED)
            writer.append("Content-Type: ${fileData.contentType}").append(LINE_FEED)
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED)
            writer.append(LINE_FEED)
            writer.flush()

           // outputStream.write(fileData.data)
            outputStream.flush()

            writer.append(LINE_FEED)
            writer.flush()
        }

        // End of multipart/form-data
        writer.append("--$boundary--").append(LINE_FEED)
        writer.close()

        return outputStream.toByteArray()
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<NetworkResponse> {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
    }
}
