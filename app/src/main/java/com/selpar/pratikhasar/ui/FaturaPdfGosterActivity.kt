package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.github.barteksc.pdfviewer.PDFView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.PdfDocumentAdapter
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import android.graphics.pdf.PdfDocument.PageInfo
import android.graphics.pdf.PdfDocument
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Random

class FaturaPdfGosterActivity : AppCompatActivity() {
    lateinit var firma_id: String
    lateinit var pdfViewer: PDFView
    lateinit var imageyazdir: ImageView
    lateinit var btn_mail_gonder: ImageView
    lateinit var btn_telegram_gonder: ImageView
    lateinit var btn_whatsapp: ImageView
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    lateinit var progressBar: ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fatura_pdf_goster)
        onBaslat()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        overridePendingTransition(R.anim.sag, R.anim.sol)
        progressBar = findViewById(R.id.progressBar)
        startLoadingAnimation()
        /*
        mBack.setOnClickListener {
            val i= Intent(this,KesilenFaturalarActivity::class.java)
            i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("gorev",intent.getStringExtra("gorev"))
            //startActivity(i)
        }*/
        Log.d("FATURA: ", intent.getStringExtra("path").toString())
        val address = Uri.parse(
            "https://pratikhasar.com/netting/e_giden_pdf.php?firma_id=" + firma_id + "&InvoiceUUID=4B478099-3F5E-044F-9D75-EC8803FD9C90"
        )

        var pdfUrl = intent.getStringExtra("path")
        //    "https://pratikhasar.com/netting/e_giden_pdf.php?firma_id=1&InvoiceUUID=4B478099-3F5E-044F-9D75-EC8803FD9C90"
        RetrievePDFFromURL(pdfViewer).execute(pdfUrl)
        imageyazdir.setOnClickListener {

            /* val printmanager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

            val printDocumentAdapter= PdfDocumentAdapter()
            printmanager.print(pdfViewer.toString(),printDocumentAdapter, PrintAttributes.Builder().build())*/
            val url = intent.getStringExtra("path").toString()
            //printUrl(this, url)
            Log.d("URL", url)
            main(url)
            //val url = "https://example.com" // PDF'ye dönüştürülecek URL'yi buraya girin
            /* printUrl(this, url)
             // val url = "https://example.com/sample.pdf"
             val destinationPath = "/storage/emulated/0/Fatura.pdf"
             uploadPdfToUrl(url, destinationPath)
             val printmanager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

             val printDocumentAdapter = PdfDocumentAdapter()
             printmanager.print(
                 pdfViewer.toString(),
                 printDocumentAdapter,
                 PrintAttributes.Builder().build()
             )*/
        }

    }
    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        progressBar.setVisibility(View.VISIBLE)
        progressBar.startAnimation(animation)

        // Simulate loading delay
        // Remove this code in your actual implementation
        Handler().postDelayed(Runnable {
            progressBar.clearAnimation()
            progressBar.setVisibility(View.GONE)
        }, 5000) // Replace 3000 with your desired loading time
    }
    fun main(path: String) {
        val url = path // Replace with the URL of your PDF file
        val random =
            Random(System.currentTimeMillis()) // Rastgelelik için bir tohum (seed) belirtiyoruz

        val randomNumber = random.nextInt()
        val fileName = "e_fatura_" + randomNumber // Replace with the desired file name

        val context = /* Provide your Android Context object here */
            downloadPdf(this, url, fileName)
    }

    fun downloadPdf(context: Context, url: String, fileName: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "$fileName.pdf"
            )

        downloadManager.enqueue(request)
    }


    fun uploadPdfToUrl(url: String, filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            println("File does not exist: $filePath")
            return
        }

        val boundary = "*****" // Random boundary string
        val lineEnd = "\r\n"
        val twoHyphens = "--"

        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 3000
            connection.readTimeout = 3000
            connection.requestMethod = "POST"
            connection.doInput = true
            connection.doOutput = true
            connection.useCaches = false
            connection.setRequestProperty("Connection", "Keep-Alive")
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")

            val outputStream = DataOutputStream(connection.outputStream)

            // Add file parameter
            outputStream.writeBytes(twoHyphens + boundary + lineEnd)
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"${file.name}\"$lineEnd")
            outputStream.writeBytes("Content-Type: application/pdf$lineEnd")
            outputStream.writeBytes(lineEnd)

            // Read file data and write it to the output stream
            val fileInputStream = FileInputStream(file)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (fileInputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            fileInputStream.close()

            // End of multipart/form-data
            outputStream.writeBytes(lineEnd)
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)

            outputStream.flush()
            outputStream.close()

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                println("PDF file uploaded successfully!")
            } else {
                println("Failed to upload PDF file. Response code: $responseCode")
            }

            connection.disconnect()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun printPdfFromUrl(context: Context, pdfUrl: String) {
        val webView = WebView(context)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                val printAdapter = webView.createPrintDocumentAdapter("Document")
                val printAttributes = PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .build()
                //   printManager.print("Document", printAdapter, null)
                printManager.print("Document", printAdapter, printAttributes)


                webView.destroy()
            }
        }

        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$pdfUrl")
    }

    fun printUrlToPdf(context: Context, url: String) {
        val webView = WebView(context)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val pdfFile = File(context.cacheDir, "temp_pdf_file.pdf")

                val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                val printAdapter = webView.createPrintDocumentAdapter()

                printManager.print("print_job_name", printAdapter, null)

                // PDF oluşturma işlemi
                val printAttributes = PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .build()

                val pdfDocument = PrintedPdfDocument(context, printAttributes)
                val pageInfo = PageInfo.Builder(800, 1000, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                val canvas = page.canvas
                webView.draw(canvas)
                pdfDocument.finishPage(page)

                try {
                    val fileOutputStream = FileOutputStream(pdfFile)
                    pdfDocument.writeTo(fileOutputStream)
                    fileOutputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                pdfDocument.close()

                // Yazdırma işlemi
                val printJob = printManager.print("print_job_name", printAdapter, null)
                if (printJob.isCompleted) {
                    // Yazdırma tamamlandı
                } else if (printJob.isFailed) {
                    // Yazdırma başarısız oldu
                }

                webView.destroy()
                pdfFile.delete()
            }
        }

        webView.loadUrl(url)
    }

    fun printUrl(context: Context, url: String) {
        val printmanager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            .build()
        val printAdapter = object : PrintDocumentAdapter() {
            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: CancellationSignal?,
                callback: LayoutResultCallback?,
                extras: Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback?.onLayoutCancelled()
                    return
                }

                val pdi = PrintDocumentInfo.Builder(url)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .build()

                callback?.onLayoutFinished(pdi, true)
            }

            override fun onWrite(
                pages: Array<out PageRange>?,
                destination: ParcelFileDescriptor?,
                cancellationSignal: CancellationSignal?,
                callback: WriteResultCallback?
            ) {
                val webView = WebView(context)
                webView.settings.javaScriptEnabled = true
                webView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        val printAdapter = webView.createPrintDocumentAdapter()
                        // printAdapter.onWrite(pages, destination, cancellationSignal, callback)
                        printmanager.print("Document", printAdapter, printAttributes)

                    }
                }
                webView.loadUrl(url)
            }
        }

        val jobName = "url_print_job"

        printmanager.print(jobName, printAdapter, printAttributes)


    }

    private fun onBaslat() {
        pdfViewer = findViewById(R.id.pdf_web_fatura)
        imageyazdir = findViewById(R.id.imageyazdir)
        firma_id = intent.getStringExtra("firma_id").toString()
    }
}

class RetrievePDFFromURL(pdfView: PDFView) :
    AsyncTask<String, Void, InputStream>() {

    // on below line we are creating a variable for our pdf view.
    val mypdfView: PDFView = pdfView

    // on below line we are calling our do in background method.
    override fun doInBackground(vararg params: String?): InputStream? {
        // on below line we are creating a variable for our input stream.
        var inputStream: InputStream? = null
        try {
            // on below line we are creating an url
            // for our url which we are passing as a string.
            val url = URL(params.get(0))

            // on below line we are creating our http url connection.
            val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

            // on below line we are checking if the response
            // is successful with the help of response code
            // 200 response code means response is successful
            if (urlConnection.responseCode == 200) {
                // on below line we are initializing our input stream
                // if the response is successful.
                inputStream = BufferedInputStream(urlConnection.inputStream)
            }
        }
        // on below line we are adding catch block to handle exception
        catch (e: Exception) {
            // on below line we are simply printing
            // our exception and returning null
            e.printStackTrace()
            return null;
        }
        // on below line we are returning input stream.
        return inputStream;
    }

    // on below line we are calling on post execute
    // method to load the url in our pdf view.
    override fun onPostExecute(result: InputStream?) {
        // on below line we are loading url within our
        // pdf view on below line using input stream.
        mypdfView.fromStream(result).load()

    }
}