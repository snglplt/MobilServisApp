package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.view.View.VISIBLE
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.selpar.pratikhasar.R

class WebviewActivity : AppCompatActivity() {
    lateinit var webView:WebView
    lateinit var txt_selpar:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webView=findViewById(R.id.webview)
        txt_selpar=findViewById(R.id.txt_selpar)
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl(intent.getStringExtra("path").toString())
        if(intent.getStringExtra("path").toString()=="https://pos.param.com.tr/Tahsilat/Default.aspx?k=1582893d-9f14-48b2-aa30-0ff5a9073ac8"){
            val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            builder.setTitle(getString(R.string.uyari))
            builder.setMessage(
                getString(R.string.selpar_odeme)+" " +"\n" +
                        "www.pratikhasar.com"
            )
            builder.setPositiveButton(getString(R.string.tamam)) { dialog, which ->
            }

            val dialog = builder.create()
            dialog.show()

        }
    }
}