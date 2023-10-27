package com.selpar.pratikhasar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.selpar.pratikhasar.R

class GelenManuelFaturalarActivity : AppCompatActivity() {
    lateinit var mBack:ImageView
    lateinit var mForward:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gelen_manuel_faturalar)
        onBaslat()

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
        }
    }

    private fun onBaslat() {
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
    }
}