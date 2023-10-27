package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hp.jipp.encoding.Text
import com.selpar.pratikhasar.R

class HakkimizdaActivity : AppCompatActivity() {
    lateinit var txtlink:TextView
    lateinit var txt_arama:TextView
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hakkimizda)
        onBaslat()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack.setOnClickListener {
            val i= Intent(this,HomeActivity::class.java)
            i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("gorev",intent.getStringExtra("gorev"))
            startActivity(i)
            finish()
        }
        txtlink.setOnClickListener {
            val i= Intent(applicationContext,WebviewActivity::class.java)
            i.putExtra("path","https://selparteknoloji.com/")
            startActivity(i)
        }
        txt_arama.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + txt_arama.getText().toString())
            startActivity(dialIntent)
        }
    }

    private fun onBaslat() {
        txtlink=findViewById(R.id.txtlink)
        txt_arama=findViewById(R.id.txt_arama)
    }
}