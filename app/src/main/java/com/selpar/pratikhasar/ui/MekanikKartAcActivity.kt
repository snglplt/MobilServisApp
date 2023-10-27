package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.fragment.AracBilgiKartAcFragment
import com.selpar.pratikhasar.fragment.MekanikAracBilgiKartAcFragment

class MekanikKartAcActivity : AppCompatActivity() {
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var kadi:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    var bundlem=Bundle()
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mekanik_kart_ac)
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
        fragmentGetir()
    }

    private fun fragmentGetir() {
        val fragobj = MekanikAracBilgiKartAcFragment()
        bundlem.putString("kadi",intent.getStringExtra("kadi"))
        bundlem.putString("dilsecimi",intent.getStringExtra("dilsecimi"))
        bundlem.putString("yetki",intent.getStringExtra("yetki"))
        bundlem.putString("kullanici_id",intent.getStringExtra("kullanici_id"))
        bundlem.putString("sifre",intent.getStringExtra("sifre"))
        bundlem.putString("firma_id",intent.getStringExtra("firma_id"))
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
            .replace(R.id.mekanik_fragment,  fragobj)
            .commit()
    }
}