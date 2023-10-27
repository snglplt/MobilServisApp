package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.fragment.AktarilmayanFragment
import com.selpar.pratikhasar.fragment.GelenReddedilenFragment
import com.selpar.pratikhasar.fragment.IceriAktarFragment
import com.selpar.pratikhasar.fragment.OnarimFragment

class GelenFaturalarActivity : AppCompatActivity() {
    lateinit var btn_iceriaktar_faturalar: Button
    lateinit var btn_aktarilmayan_faturalar: Button
    lateinit var btn_reddedilen_faturalar: Button
    lateinit var dilsecimi: String
    lateinit var yetki: String
    lateinit var firma_id: String
    lateinit var kullanici_id: String
    lateinit var kadi: String
    lateinit var sifre: String
    lateinit var gorev: String
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gelen_faturalar)
        onBaslat()
        onAktarilamayanFaturalar()
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
        }
        btn_iceriaktar_faturalar.setOnClickListener { onIceriAktarFaturalar() }
        btn_aktarilmayan_faturalar.setOnClickListener { onAktarilamayanFaturalar() }
        btn_reddedilen_faturalar.setOnClickListener { onReddedilenFaturalar() }

    }
    private fun onIceriAktarFaturalar() {
        btn_iceriaktar_faturalar.setBackgroundColor(Color.GRAY)
        btn_reddedilen_faturalar.setBackgroundColor(Color.CYAN)
        btn_aktarilmayan_faturalar.setBackgroundColor(Color.CYAN)

        // aracSahibi.setTextColor(R.color.black)


        var bundlem = Bundle()
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kadi", kadi)
        bundlem.putString("sifre", sifre)
        bundlem.putString("kullanici_id", kullanici_id)
        bundlem.putString("dilsecimi", dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("gorev", gorev)

        val fragobj = IceriAktarFragment()
        fragobj.arguments = bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger = getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_gelen_faturalar, fragobj)
            .commit()
    }

    private fun onReddedilenFaturalar() {
        btn_reddedilen_faturalar.setBackgroundColor(Color.GRAY)
        btn_aktarilmayan_faturalar.setBackgroundColor(Color.CYAN)
        btn_iceriaktar_faturalar.setBackgroundColor(Color.CYAN)

        // aracSahibi.setTextColor(R.color.black)


        var bundlem = Bundle()
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kadi", kadi)
        bundlem.putString("sifre", sifre)
        bundlem.putString("kullanici_id", kullanici_id)
        bundlem.putString("dilsecimi", dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("gorev", gorev)

        val fragobj = GelenReddedilenFragment()
        fragobj.arguments = bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger = getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_gelen_faturalar, fragobj)
            .commit()
    }

    private fun onAktarilamayanFaturalar() {
        btn_aktarilmayan_faturalar.setBackgroundColor(Color.GRAY)
        btn_reddedilen_faturalar.setBackgroundColor(Color.CYAN)
        btn_iceriaktar_faturalar.setBackgroundColor(Color.CYAN)

        // aracSahibi.setTextColor(R.color.black)


        var bundlem = Bundle()
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kadi", kadi)
        bundlem.putString("sifre", sifre)
        bundlem.putString("kullanici_id", kullanici_id)
        bundlem.putString("dilsecimi", dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("gorev", gorev)

        val fragobj = AktarilmayanFragment()
        fragobj.arguments = bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger = getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_gelen_faturalar, fragobj)
            .commit()
    }

    private fun onBaslat() {
        btn_aktarilmayan_faturalar = findViewById(R.id.btn_aktarilmayan_faturalar)
        btn_reddedilen_faturalar = findViewById(R.id.btn_reddedilen_faturalar)
        btn_iceriaktar_faturalar = findViewById(R.id.btn_iceriaktar_faturalar)
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki = intent.getStringExtra("yetki").toString()
        firma_id = intent.getStringExtra("firma_id").toString()
        kullanici_id = intent.getStringExtra("kullanici_id").toString()
        kadi = intent.getStringExtra("kadi").toString()
        sifre = intent.getStringExtra("sifre").toString()
        gorev = intent.getStringExtra("gorev").toString()

    }
}