package com.selpar.pratikhasar.ui

import AracSahibiFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.MyViewPagerAdapter
import com.selpar.pratikhasar.data.PageAdapter
import com.selpar.pratikhasar.fragment.*
import com.squareup.picasso.Picasso
import java.util.HashMap

class AcikKartBilgiActivity : AppCompatActivity() {
    lateinit var Tvplaka: TextView
    lateinit var Tvmarka: TextView
    lateinit var Tvmodel: TextView
    lateinit var Tvmodelyili: TextView
    lateinit var Tvkasatipi: TextView
    lateinit var Tvdosya: TextView
    lateinit var TvUnvan: TextView
    lateinit var Tvrenk: TextView
    lateinit var Tvkm: TextView
    lateinit var aracBilgi: Button
    lateinit var aracSahibi: Button
    lateinit var sigorta: Button
    lateinit var evraklar: Button
    lateinit var onarim: Button
    lateinit var istekSikayet: Button
    lateinit var btn_guncelle: Button
    lateinit var plakaGoster: String
    lateinit var plaka: String
    lateinit var kadi: String
    lateinit var firma_id: String
    lateinit var sifre: String
    lateinit var kullanici_id: String
    lateinit var kabulnom: String
    lateinit var unvan: String
    lateinit var vergino: String
    lateinit var ozel_id: String
    lateinit var spinner_baslik: Spinner
    lateinit var btn_guncelle_baslik: Button
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    @SuppressLint("ResourceAsColor", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acik_kart_bilgi)
        onBaslat()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        ozel_id = intent.getStringExtra("ozel_id").toString()
        kadi = intent.getStringExtra("kadi").toString()
        sifre = intent.getStringExtra("sifre").toString()
        firma_id = intent.getStringExtra("firma_id").toString()
        kullanici_id = intent.getStringExtra("kullanici_id").toString()
        kabulnom = intent.getStringExtra("kabulnom").toString()
        unvan = intent.getStringExtra("unvan").toString()
        vergino = intent.getStringExtra("vergino").toString()
        plaka = intent.getStringExtra("plaka").toString()
        val resim = findViewById<ImageView>(R.id.Tvaracresim)
        istekSikayet.setBackgroundColor(Color.GRAY)
        aracSahibi.setBackgroundColor(0)

        onarim.setBackgroundColor(0)
        evraklar.setBackgroundColor(0)
        sigorta.setBackgroundColor(0)
        var bundlem = Bundle()
        bundlem.putString("firma_id", intent.getStringExtra("firma_id"))
        bundlem.putString("kadi", intent.getStringExtra("kadi"))
        bundlem.putString("sifre", intent.getStringExtra("sifre"))
        bundlem.putString("kullanici_id", kullanici_id)
        bundlem.putString("ozel_id", ozel_id)
        bundlem.putString("kabulnom", kabulnom)
        bundlem.putString("plaka", plakaGoster)
        Log.d("buldu", plakaGoster)
        Picasso.get().load(intent.getStringExtra("resim")).into(resim)
        //IstekSikayet()
        OnarimAc()
        val spinner_list = ArrayList<String>()
        spinner_list.add(intent.getStringExtra("baslik").toString())
        val spinner_value =
            this.resources.getStringArray(R.array.kart_durum)
        for (i in spinner_value.indices) {
            spinner_list.add(spinner_value[i])
        }
        val spinner_arac_turu_police_adapter1: Any? = ArrayAdapter<Any?>(
            this,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            spinner_list as List<Any?>
        )
        spinner_baslik.setAdapter(spinner_arac_turu_police_adapter1 as SpinnerAdapter?)
        btn_guncelle_baslik.setOnClickListener {
            guncelle_baslik(spinner_baslik.selectedItem.toString())
        }
        btn_guncelle.setOnClickListener {
            val i = Intent(this, PlakaBilgiActivity::class.java)
            i.putExtra("plaka", Tvplaka.getText().toString())
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("ozel_id", ozel_id)
            startActivity(i)
        }

        istekSikayet.setOnClickListener {
            aracSahibi.setBackgroundColor(0)
            sigorta.setBackgroundColor(0)
            evraklar.setBackgroundColor(0)
            onarim.setBackgroundColor(0)
            istekSikayet.setBackgroundColor(Color.GRAY)

            IstekSikayet()
        }
        aracSahibi.setOnClickListener {
            aracSahibi.setBackgroundColor(Color.GRAY)
            sigorta.setBackgroundColor(0)
            evraklar.setBackgroundColor(0)
            onarim.setBackgroundColor(0)
            istekSikayet.setBackgroundColor(0)

            aracBilgiGetir()
        }
        sigorta.setOnClickListener {
            aracSahibi.setBackgroundColor(0)
            evraklar.setBackgroundColor(0)
            onarim.setBackgroundColor(0)
            istekSikayet.setBackgroundColor(0)

            // aracSahibi.setTextColor(R.color.black)
            sigorta.setBackgroundColor(Color.GRAY)
            var bundlem = Bundle()
            bundlem.putString("plaka", plaka)
            bundlem.putString("ozel_id", intent.getStringExtra("ozel_id"))
            bundlem.putString("kadi", intent.getStringExtra("kadi"))
            bundlem.putString("sifre", intent.getStringExtra("sifre"))
            bundlem.putString("firma_id", intent.getStringExtra("firma_id"))
            val fragobj = SigortaFragment()
            fragobj.arguments = bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = getSupportFragmentManager()
            fragmentmaneger.beginTransaction()
                .replace(R.id.fragment_mekanik, fragobj)
                .commit()
        }
        evraklar.setOnClickListener {
            evraklar.setBackgroundColor(Color.GRAY)
            onarim.setBackgroundColor(0)
            sigorta.setBackgroundColor(0)
            aracSahibi.setBackgroundColor(0)
            istekSikayet.setBackgroundColor(0)

            bundlem.putString("firma_id", intent.getStringExtra("firma_id"))
            Log.d("firma", intent.getStringExtra("firma_id").toString())
            bundlem.putString("kadi", intent.getStringExtra("kadi"))
            bundlem.putString("sifre", intent.getStringExtra("sifre"))
            bundlem.putString("kullanici_id", kullanici_id)
            bundlem.putString("ozel_id", ozel_id)
            bundlem.putString("kabulnom", kabulnom)
            // val fragobj = EvraklarFragment()
            val fragobj = TumFragment()
            fragobj.arguments = bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = getSupportFragmentManager()
            fragmentmaneger.beginTransaction()
                .replace(R.id.fragment_mekanik, fragobj)
                .commit()
        }
        onarim.setOnClickListener {
            OnarimAc()
        }
        mBack.setOnClickListener {
            val i = Intent(this, AcikKartlarActivity::class.java)
            i.putExtra("plaka", plaka)
            i.putExtra("ozel_id", intent.getStringExtra("ozel_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            startActivity(i)
        }

    }

    private fun guncelle_baslik(selectedItem: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this, "Başlık Guncelle Başarılı: " + kadi, Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["firma_id"] = firma_id
                params["ozel_id"] = ozel_id
                params["baslik"] = selectedItem
                params["tur"] = "baslik_guncelle"

                return params
            }
        }
        queue.add(postRequest)

    }


    fun aracBilgiGetir() {
        var bundlem = Bundle()
        bundlem.putString("plaka", plaka)
        bundlem.putString("ozel_id", intent.getStringExtra("ozel_id"))
        bundlem.putString("kadi", intent.getStringExtra("kadi"))
        bundlem.putString("sifre", intent.getStringExtra("sifre"))
        bundlem.putString("firma_id", intent.getStringExtra("firma_id"))
        val fragobj = AracSahibiFragment()
        fragobj.arguments = bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger = getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_mekanik, fragobj)
            .commit()
    }

    fun IstekSikayet() {
        var bundlem = Bundle()
        bundlem.putString("plaka", plaka)
        bundlem.putString("ozel_id", intent.getStringExtra("ozel_id"))
        bundlem.putString("kadi", intent.getStringExtra("kadi"))
        bundlem.putString("sifre", intent.getStringExtra("sifre"))
        bundlem.putString("firma_id", intent.getStringExtra("firma_id"))
        val fragobj = IstekSikayetFragment()
        fragobj.arguments = bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger = getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_mekanik, fragobj)
            .commit()
    }

    private fun onBaslat() {
        aracBilgi = findViewById<Button>(R.id.btnOnarim)
        aracSahibi = findViewById<Button>(R.id.btnAracSahibi)
        sigorta = findViewById<Button>(R.id.btnKabulAsamasi)
        evraklar = findViewById(R.id.btnEvrak)
        onarim = findViewById(R.id.btnOnarim)
        Tvplaka = findViewById(R.id.Tvplaka)
        istekSikayet = findViewById(R.id.btnisteksikayet)
        Tvplaka.setText(intent.getStringExtra("plaka"))
        plakaGoster = intent.getStringExtra("plaka").toString()
        Log.d("plaka", intent.getStringExtra("plaka").toString())
        Tvmarka = findViewById(R.id.Tvmarka)
        Tvmarka.setText(intent.getStringExtra("marka"))
        Tvmodel = findViewById(R.id.Tvmodel)
        Tvmodel.setText(intent.getStringExtra(("model")))
        Tvmodelyili = findViewById(R.id.Tvmodelyili)
        Tvmodelyili.setText(intent.getStringExtra("modelyili"))
        Tvkasatipi = findViewById(R.id.Tvkasatipi)
        Tvkasatipi.setText(intent.getStringExtra("kasatipi"))
        Tvdosya = findViewById(R.id.Tvdosyano)
        Tvdosya.setText(intent.getStringExtra("dosyano"))
        TvUnvan = findViewById(R.id.Tvunvan)
        TvUnvan.setText(intent.getStringExtra("unvan"))
        Tvrenk = findViewById(R.id.Tvrenk)
        Tvrenk.setText(intent.getStringExtra("renk"))
        Tvkm = findViewById(R.id.Tvbaslik)
        Tvkm.setText(intent.getStringExtra("km"))
        btn_guncelle = findViewById(R.id.btn_guncelle_plaka)
        spinner_baslik = findViewById(R.id.spinner_baslik)
        btn_guncelle_baslik = findViewById(R.id.btn_guncelle_baslik)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))


    }

    fun OnarimAc() {
        onarim.setBackgroundColor(Color.GRAY)
        evraklar.setBackgroundColor(0)
        sigorta.setBackgroundColor(0)
        aracSahibi.setBackgroundColor(0)
        istekSikayet.setBackgroundColor(0)


        var bundlem = Bundle()
        bundlem.putString("firma_id", intent.getStringExtra("firma_id"))
        bundlem.putString("kadi", intent.getStringExtra("kadi"))
        bundlem.putString("sifre", intent.getStringExtra("sifre"))
        bundlem.putString("kullanici_id", kullanici_id)
        bundlem.putString("ozel_id", ozel_id)
        bundlem.putString("plaka", plaka)
        bundlem.putString("unvan", unvan)
        bundlem.putString("marka", intent.getStringExtra("marka"))
        bundlem.putString("model", intent.getStringExtra("model"))
        bundlem.putString("modelyili", intent.getStringExtra("modelyili"))
        bundlem.putString("resim", intent.getStringExtra("resim"))
        bundlem.putString("sevisturu", intent.getStringExtra("sevisturu"))
        bundlem.putString("saseno", intent.getStringExtra("saseno"))
        val fragobj = OnarimFragment()
        fragobj.arguments = bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger = getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_mekanik, fragobj)
            .commit()
    }

}


