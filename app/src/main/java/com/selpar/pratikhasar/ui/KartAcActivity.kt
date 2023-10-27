package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.fragment.*
import java.util.*

class KartAcActivity : AppCompatActivity() {
    lateinit var btnAracBilgi_kart_ac:Button
    lateinit var btnAracSahibi_kart_ac:Button
    lateinit var btnSigorta_kart_ac:Button
    var bundlem=Bundle()
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var firma_id:String
    lateinit var kadi:String
    lateinit var dilsecimi:String
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kart_ac)
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        overridePendingTransition(R.anim.sag, R.anim.sol)

        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        if(dilsecimi=="en"){
            SetLocale("en")
            recreate()
        }
        aracBilgiKartaAc()
        mBack.setOnClickListener {

            val i= Intent(this,HomeActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            startActivity(i)
        }

        /*onBaslat()
        aracBilgiKartaAc()
       btnAracBilgi_kart_ac.setOnClickListener { aracBilgiKartaAc() }
        btnAracSahibi_kart_ac.setOnClickListener { aracSahibiKartaAc() }
        btnSigorta_kart_ac.setOnClickListener { aracSigortaKartaAc() }*/
    }
    private fun SetLocale(lang: String) {
        val locale= Locale(lang)
        Locale.setDefault(locale)
        val config: Configuration = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        val editor: SharedPreferences.Editor=getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang",lang)
        editor.apply()
    }

    private fun aracSigortaKartaAc() {
        btnAracBilgi_kart_ac.setBackgroundColor(0)
        btnAracSahibi_kart_ac.setBackgroundColor(0)
        btnSigorta_kart_ac.setBackgroundColor(Color.GRAY)
        bundlem.putString("kadi", kadi)
        bundlem.putString("dilsecimi",dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kullanici_id", kullnciid)
        bundlem.putString("sifre", sifrem)
        val fragobj = AracSigortaKartAcFragment()

        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_kart_ac,  fragobj)
            .commit()
    }

    public fun aracSahibiKartaAc() {
        onBaslat()
        btnAracBilgi_kart_ac.setBackgroundColor(0)
        btnAracSahibi_kart_ac.setBackgroundColor(Color.GRAY)
        btnSigorta_kart_ac.setBackgroundColor(0)
        val fragobj = AracSahibiKartAcFragment()
        bundlem.putString("kadi", kadi)
        bundlem.putString("dilsecimi",dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kullanici_id", kullnciid)
        bundlem.putString("sifre", sifrem)
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_kart_ac,  fragobj)
            .commit()

    }

    private fun aracBilgiKartaAc() {
      // btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
       // btnAracSahibi_kart_ac.setBackgroundColor(0)
       // btnSigorta_kart_ac.setBackgroundColor(0)
        val fragobj = AracBilgiKartAcFragment()
        bundlem.putString("kadi", kadi)
        bundlem.putString("dilsecimi",dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kullanici_id", kullnciid)
        bundlem.putString("sifre", sifrem)
        bundlem.putString("aracturu", intent.getStringExtra("aracturu"))
        bundlem.putString("plaka", intent.getStringExtra("plaka"))
        bundlem.putString("marka", intent.getStringExtra("marka"))
        bundlem.putString("model", intent.getStringExtra("model"))
        bundlem.putString("model_y", intent.getStringExtra("model_y"))
        bundlem.putString("modelvrs", intent.getStringExtra("modelvrs"))
        bundlem.putString("kasa_tipi", intent.getStringExtra("kasa_tipi"))
        bundlem.putString("km", intent.getStringExtra("km"))
        bundlem.putString("renk", intent.getStringExtra("renk"))
        bundlem.putString("saseno", intent.getStringExtra("saseno"))
        bundlem.putString("motorno", intent.getStringExtra("motorno"))
        bundlem.putString("konum", intent.getStringExtra("konum"))
        bundlem.putString("durum", intent.getStringExtra("durum"))
        bundlem.putString("mua", intent.getStringExtra("mua"))
        bundlem.putString("tahmini_teslim_tarihi", intent.getStringExtra("tahmini_teslim_tarihi"))
        bundlem.putString("egzoz", intent.getStringExtra("egzoz"))
        bundlem.putString("trafik", intent.getStringExtra("trafik"))
        bundlem.putString("kasko", intent.getStringExtra("kasko"))
        bundlem.putString("unvan", intent.getStringExtra("unvan"))
        bundlem.putString("gelenplaka", intent.getStringExtra("gelenplaka"))
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_kart_ac,  fragobj)
            .commit()

    }

    private fun onBaslat() {
        btnAracBilgi_kart_ac=findViewById(R.id.btnAracBilgi_kart_ac)
        btnAracSahibi_kart_ac=findViewById(R.id.btnAracSahibi_kart_ac_)
        btnSigorta_kart_ac=findViewById(R.id.btn_kabul_asamasi)
        btnSigorta_kart_ac.setBackgroundColor(0)
        btnAracSahibi_kart_ac.setBackgroundColor(0)
        btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
    }
}