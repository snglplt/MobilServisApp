package com.selpar.pratikhasar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.selpar.pratikhasar.R

class SanalPosAyarlariActivity : AppCompatActivity() {
    lateinit var btn_parampos:Button
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sanal_pos_ayarlari)
        onBaslat()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        mBack.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra(   "firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("gorev", intent.getStringExtra("gorev"))
            i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())

            startActivity(i)
        }
        btn_parampos.setOnClickListener {
            val i= Intent(this,SanalPosAyarlariParamActivity::class.java)
            i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra(   "firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("gorev", intent.getStringExtra("gorev"))
            i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())

            startActivity(i)
            finish()
        }
    }

    private fun onBaslat() {
        btn_parampos=findViewById(R.id.btn_parampos)
    }
}