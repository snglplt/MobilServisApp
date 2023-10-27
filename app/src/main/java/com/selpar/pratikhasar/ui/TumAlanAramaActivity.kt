package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.news
import com.selpar.pratikhasar.data.tumkartlarigetir
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList

class TumAlanAramaActivity : AppCompatActivity() {
    lateinit var kadi: String
    lateinit var sifre: String
    lateinit var firma_id: String
    private lateinit var newRecyclerview: RecyclerView

    private lateinit var newArrayList: ArrayList<news>

    var plaka = ArrayList<String>()
    var resim = ArrayList<String>()
    var marka = ArrayList<String>()
    var model = ArrayList<String>()
    var modelyili = ArrayList<String>()
    var kasatipi = ArrayList<String>()
    var dosyano = ArrayList<String>()
    var unvan = ArrayList<String>()
    var renk = ArrayList<String>()
    var km = ArrayList<String>()
    var baslik = ArrayList<String>()
    var ozel_id = ArrayList<String>()
    var kabulnom = ArrayList<String>()
    var servis_turu = ArrayList<String>()
    var saseno = ArrayList<String>()
    var mua = ArrayList<String>()
    var motorno = ArrayList<String>()
    var modelvers = ArrayList<String>()
    var adres = ArrayList<String>()
    var il = ArrayList<String>()
    var ilce = ArrayList<String>()
    var sigortasirketi = ArrayList<String>()
    var tel = ArrayList<String>()
    var policeno = ArrayList<String>()
    var policeturu = ArrayList<String>()
    var policetarihi = ArrayList<String>()
    var kazatarihi = ArrayList<String>()
    var ihbartarihi = ArrayList<String>()
    var mail = ArrayList<String>()
    var onarim = ArrayList<Float>()
    var onarimbul2 = ArrayList<Float>()
    var markaresim = ArrayList<String>()
    var yakitturu = ArrayList<String>()
    var toplam: Float = 0.0f
    var akadi = ""
    var asifre = ""
    var afirma_id = ""
    var akullanici_id = ""
    var dilsecimi: String = ""
    var sonucToplam: Float = 0.0f
    lateinit var yetki: String
    lateinit var kullnciid: String
    lateinit var sifrem: String
    var servis_hasar = 0
    var servis_mekanik = 0
    lateinit var txt_hasar: TextView
    lateinit var txt_mekanik: TextView
    lateinit var aranan: String
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tum_alan_arama)
        txt_mekanik = findViewById(R.id.txt_mekanik)
        txt_hasar = findViewById(R.id.txt_hasar)
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        if (dilsecimi == "en") {
            SetLocale("en")
            recreate()
        }
        overridePendingTransition(R.anim.sag, R.anim.sol)

        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        kadi = intent.getStringExtra("kadi").toString()
        sifre = intent.getStringExtra("sifre").toString()
        firma_id = intent.getStringExtra("firma_id").toString()
        yetki = intent.getStringExtra("yetki").toString()
        kullnciid = intent.getStringExtra("kullanici_id").toString()
        sifrem = intent.getStringExtra("sifre").toString()
        aranan = intent.getStringExtra("aranan").toString()
        newRecyclerview = findViewById(R.id.recyclerView)
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
        //acikKartGetir()

        if (intent.getStringExtra("bugun") == "bugun") {
            bugunKartGetir()
        } else if (intent.getStringExtra("kayitli") == "kayitli") {
            kayitliKartGetir()
        } else if (intent.getStringExtra("kapali") == "kapali") {
            kapaliKartGetir()
        }else if(intent.getStringExtra("baslik")== "Teslime Hazır"){
            teslimeHazir()
        }
        else {
            acikKartGetir()
        }


    }

    private fun teslimeHazir(){
        val urlek =
            "&kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&aranan=" + aranan
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_aranan_teslime_hazir" + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    servis_mekanik = 0
                    servis_hasar = 0
                    val json = response["plaka"] as JSONArray
                    for (i in 0 until json.length()) {

                        val item = json.getJSONObject(i)

                        plaka.add(item.getString("plaka"))
                        if (item.getString("toplam") != "null") {
                            onarim.add(item.getString("toplam").toFloat())
                        } else {
                            onarim.add(0f)
                        }


                        resim.add(item.getString("resim"))
                        marka.add(item.getString("marka"))
                        model.add(item.getString("model"))
                        modelyili.add(item.getString("modelyili"))
                        kasatipi.add(item.getString("kasatipi"))
                        yakitturu.add(item.getString("yakitturu"))
                        if (item.getString("dosyano").toString() != "") {
                            dosyano.add(item.getString("dosyano"))
                        } else {
                            dosyano.add("12345")
                        }
                        unvan.add(item.getString("unvan"))
                        renk.add(item.getString("renk"))
                        km.add(item.getString("km") + " KM")
                        baslik.add(item.getString("baslik"))
                        ozel_id.add(item.getString("ozel_id"))
                        kabulnom.add(item.getString("kabulnom"))
                        servis_turu.add(item.getString("servis_turu"))
                        if (item.getString("servis_turu") == "Mekanik Servis") {
                            servis_mekanik += 1
                        } else {
                            servis_hasar += 1
                        }

                        saseno.add(item.getString("saseno"))
                        mua.add(item.getString("muabit"))
                        motorno.add(item.getString("motorno"))
                        modelvers.add(item.getString("modelvers"))
                        adres.add(item.getString("adres"))
                        il.add(item.getString("il"))
                        ilce.add(item.getString("ilce"))
                        sigortasirketi.add(item.getString("sigortasirketi"))
                        policeno.add(item.getString("policeturu"))
                        tel.add(item.getString("telefon1"))
                        markaresim.add(
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                                .toString()
                        )
                       // Toast.makeText(this, item.getString("markaresim"), Toast.LENGTH_LONG).show()
                        Log.d(
                            "MARKA",
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                        )
                        if (policeturu.add(item.getString("policeturu")).toString() != "null") {
                            policeturu.add(item.getString("policeturu"))
                        } else {
                            policeturu.add("")
                        }
                        if (policetarihi.add(item.getString("polbas")).toString() != "null") {
                            policetarihi.add(item.getString("polbas"))
                        } else {
                            policetarihi.add("")
                        }
                        if (kazatarihi.add(item.getString("kazatarihi")).toString() != "null") {
                            kazatarihi.add(item.getString("kazatarihi"))
                        } else {
                            kazatarihi.add("")
                        }
                        if (ihbartarihi.add(item.getString("ihbartarihi")).toString() != "null") {
                            ihbartarihi.add(item.getString("ihbartarihi"))
                        } else {
                            ihbartarihi.add("")
                        }
                        if (mail.add(item.getString("mail")).toString() != "null") {
                            mail.add(item.getString("mail"))
                        } else {
                            mail.add("")
                        }
                    }
                } catch (e: Exception) {
                }
                newRecyclerview.layoutManager = LinearLayoutManager(this)
                newRecyclerview.setHasFixedSize(false)
                newArrayList = arrayListOf<news>()
                getUserData(this)
                servisHesapla(servis_hasar, servis_mekanik)

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // If you want to add a background, a text, an icon
                //  as the user swipes, this is where to start decorating
                //  I will link you to a library I created for that below
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.blue
                        )
                    )
                    .addSwipeLeftLabel(getString(R.string.kartikapat))
                    //.addSwipeRightLabel("OYNAT")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        //  val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text


                        val builder = AlertDialog.Builder(
                            this@TumAlanAramaActivity,
                            R.style.AlertDialogCustom
                        )
                        builder.setTitle(getString(R.string.kartikapat))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            val txtozel_id =
                                viewHolder.itemView.findViewById<TextView>(R.id.txtozel_id).text

                            guncelle_baslik("Kapalı Kartlar", txtozel_id.toString())

                            plaka.clear()

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                            acikKartGetir()
                        }
                        builder.show()


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerview)


    }


    private fun kapaliKartGetir() {
        val urlek =
            "&kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&aranan=" + aranan
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_aranan_kapali" + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    servis_mekanik = 0
                    servis_hasar = 0
                    val json = response["plaka"] as JSONArray
                    for (i in 0 until json.length()) {

                        val item = json.getJSONObject(i)

                        plaka.add(item.getString("plaka"))
                        if (item.getString("toplam") != "null") {
                            onarim.add(item.getString("toplam").toFloat())
                        } else {
                            onarim.add(0f)
                        }


                        resim.add(item.getString("resim"))
                        marka.add(item.getString("marka"))
                        model.add(item.getString("model"))
                        modelyili.add(item.getString("modelyili"))
                        kasatipi.add(item.getString("kasatipi"))
                        yakitturu.add(item.getString("yakitturu"))
                        if (item.getString("dosyano").toString() != "") {
                            dosyano.add(item.getString("dosyano"))
                        } else {
                            dosyano.add("12345")
                        }
                        unvan.add(item.getString("unvan"))
                        renk.add(item.getString("renk"))
                        km.add(item.getString("km") + " KM")
                        baslik.add(item.getString("baslik"))
                        ozel_id.add(item.getString("ozel_id"))
                        kabulnom.add(item.getString("kabulnom"))
                        servis_turu.add(item.getString("servis_turu"))
                        if (item.getString("servis_turu") == "Mekanik Servis") {
                            servis_mekanik += 1
                        } else {
                            servis_hasar += 1
                        }

                        saseno.add(item.getString("saseno"))
                        mua.add(item.getString("muabit"))
                        motorno.add(item.getString("motorno"))
                        modelvers.add(item.getString("modelvers"))
                        adres.add(item.getString("adres"))
                        il.add(item.getString("il"))
                        ilce.add(item.getString("ilce"))
                        sigortasirketi.add(item.getString("sigortasirketi"))
                        policeno.add(item.getString("policeturu"))
                        tel.add(item.getString("telefon1"))
                        markaresim.add(
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                                .toString()
                        )
                       // Toast.makeText(this, item.getString("markaresim"), Toast.LENGTH_LONG).show()
                        Log.d(
                            "MARKA",
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                        )
                        if (policeturu.add(item.getString("policeturu")).toString() != "null") {
                            policeturu.add(item.getString("policeturu"))
                        } else {
                            policeturu.add("")
                        }
                        if (policetarihi.add(item.getString("polbas")).toString() != "null") {
                            policetarihi.add(item.getString("polbas"))
                        } else {
                            policetarihi.add("")
                        }
                        if (kazatarihi.add(item.getString("kazatarihi")).toString() != "null") {
                            kazatarihi.add(item.getString("kazatarihi"))
                        } else {
                            kazatarihi.add("")
                        }
                        if (ihbartarihi.add(item.getString("ihbartarihi")).toString() != "null") {
                            ihbartarihi.add(item.getString("ihbartarihi"))
                        } else {
                            ihbartarihi.add("")
                        }
                        if (mail.add(item.getString("mail")).toString() != "null") {
                            mail.add(item.getString("mail"))
                        } else {
                            mail.add("")
                        }
                    }
                } catch (e: Exception) {
                }
                newRecyclerview.layoutManager = LinearLayoutManager(this)
                newRecyclerview.setHasFixedSize(false)
                newArrayList = arrayListOf<news>()
                getUserData(this)
                servisHesapla(servis_hasar, servis_mekanik)

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // If you want to add a background, a text, an icon
                //  as the user swipes, this is where to start decorating
                //  I will link you to a library I created for that below
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.blue
                        )
                    )
                    .addSwipeLeftLabel(getString(R.string.kartikapat))
                    //.addSwipeRightLabel("OYNAT")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        //  val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text


                        val builder = AlertDialog.Builder(
                            this@TumAlanAramaActivity,
                            R.style.AlertDialogCustom
                        )
                        builder.setTitle(getString(R.string.kartikapat))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            val txtozel_id =
                                viewHolder.itemView.findViewById<TextView>(R.id.txtozel_id).text

                            guncelle_baslik("Kapalı Kartlar", txtozel_id.toString())

                            plaka.clear()

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                            acikKartGetir()
                        }
                        builder.show()


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerview)


    }


    private fun kayitliKartGetir() {
        val urlek =
            "&kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&aranan=" + aranan
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_aranan" + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    servis_mekanik = 0
                    servis_hasar = 0
                    val json = response["plaka"] as JSONArray
                    for (i in 0 until json.length()) {

                        val item = json.getJSONObject(i)

                        plaka.add(item.getString("plaka"))
                        if (item.getString("toplam") != "null") {
                            onarim.add(item.getString("toplam").toFloat())
                        } else {
                            onarim.add(0f)
                        }


                        resim.add(item.getString("resim"))
                        marka.add(item.getString("marka"))
                        model.add(item.getString("model"))
                        modelyili.add(item.getString("modelyili"))
                        kasatipi.add(item.getString("kasatipi"))
                        yakitturu.add(item.getString("yakitturu"))
                        if (item.getString("dosyano").toString() != "") {
                            dosyano.add(item.getString("dosyano"))
                        } else {
                            dosyano.add("12345")
                        }
                        unvan.add(item.getString("unvan"))
                        renk.add(item.getString("renk"))
                        km.add(item.getString("km") + " KM")
                        baslik.add(item.getString("baslik"))
                        ozel_id.add(item.getString("ozel_id"))
                        kabulnom.add(item.getString("kabulnom"))
                        servis_turu.add(item.getString("servis_turu"))
                        if (item.getString("servis_turu") == "Mekanik Servis") {
                            servis_mekanik += 1
                        } else {
                            servis_hasar += 1
                        }

                        saseno.add(item.getString("saseno"))
                        mua.add(item.getString("muabit"))
                        motorno.add(item.getString("motorno"))
                        modelvers.add(item.getString("modelvers"))
                        adres.add(item.getString("adres"))
                        il.add(item.getString("il"))
                        ilce.add(item.getString("ilce"))
                        sigortasirketi.add(item.getString("sigortasirketi"))
                        policeno.add(item.getString("policeturu"))
                        tel.add(item.getString("telefon1"))
                        markaresim.add(
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                                .toString()
                        )
                      //  Toast.makeText(this, item.getString("markaresim"), Toast.LENGTH_LONG).show()
                        Log.d(
                            "MARKA",
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                        )
                        if (policeturu.add(item.getString("policeturu")).toString() != "null") {
                            policeturu.add(item.getString("policeturu"))
                        } else {
                            policeturu.add("")
                        }
                        if (policetarihi.add(item.getString("polbas")).toString() != "null") {
                            policetarihi.add(item.getString("polbas"))
                        } else {
                            policetarihi.add("")
                        }
                        if (kazatarihi.add(item.getString("kazatarihi")).toString() != "null") {
                            kazatarihi.add(item.getString("kazatarihi"))
                        } else {
                            kazatarihi.add("")
                        }
                        if (ihbartarihi.add(item.getString("ihbartarihi")).toString() != "null") {
                            ihbartarihi.add(item.getString("ihbartarihi"))
                        } else {
                            ihbartarihi.add("")
                        }
                        if (mail.add(item.getString("mail")).toString() != "null") {
                            mail.add(item.getString("mail"))
                        } else {
                            mail.add("")
                        }
                    }
                } catch (e: Exception) {
                }
                newRecyclerview.layoutManager = LinearLayoutManager(this)
                newRecyclerview.setHasFixedSize(false)
                newArrayList = arrayListOf<news>()
                getUserData(this)
                servisHesapla(servis_hasar, servis_mekanik)

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // If you want to add a background, a text, an icon
                //  as the user swipes, this is where to start decorating
                //  I will link you to a library I created for that below
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.blue
                        )
                    )
                    .addSwipeLeftLabel(getString(R.string.kartikapat))
                    //.addSwipeRightLabel("OYNAT")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        //  val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text


                        val builder = AlertDialog.Builder(
                            this@TumAlanAramaActivity,
                            R.style.AlertDialogCustom
                        )
                        builder.setTitle(getString(R.string.kartikapat))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            val txtozel_id =
                                viewHolder.itemView.findViewById<TextView>(R.id.txtozel_id).text

                            guncelle_baslik("Kapalı Kartlar", txtozel_id.toString())

                            plaka.clear()

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                            acikKartGetir()
                        }
                        builder.show()


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerview)


    }


    private fun bugunKartGetir() {
        val urlek =
            "&kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&aranan=" + aranan
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_aranan_bugun" + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    servis_mekanik = 0
                    servis_hasar = 0
                    val json = response["plaka"] as JSONArray
                    for (i in 0 until json.length()) {

                        val item = json.getJSONObject(i)

                        plaka.add(item.getString("plaka"))
                        if (item.getString("toplam") != "null") {
                            onarim.add(item.getString("toplam").toFloat())
                        } else {
                            onarim.add(0f)
                        }


                        resim.add(item.getString("resim"))
                        marka.add(item.getString("marka"))
                        model.add(item.getString("model"))
                        modelyili.add(item.getString("modelyili"))
                        kasatipi.add(item.getString("kasatipi"))
                        if (item.getString("dosyano").toString() != "") {
                            dosyano.add(item.getString("dosyano"))
                        } else {
                            dosyano.add("12345")
                        }
                        unvan.add(item.getString("unvan"))
                        renk.add(item.getString("renk"))
                        km.add(item.getString("km") + " KM")
                        baslik.add(item.getString("baslik"))
                        ozel_id.add(item.getString("ozel_id"))
                        kabulnom.add(item.getString("kabulnom"))
                        servis_turu.add(item.getString("servis_turu"))
                        if (item.getString("servis_turu") == "Mekanik Servis") {
                            servis_mekanik += 1
                        } else {
                            servis_hasar += 1
                        }

                        saseno.add(item.getString("saseno"))
                        mua.add(item.getString("muabit"))
                        motorno.add(item.getString("motorno"))
                        modelvers.add(item.getString("modelvers"))
                        adres.add(item.getString("adres"))
                        il.add(item.getString("il"))
                        ilce.add(item.getString("ilce"))
                        sigortasirketi.add(item.getString("sigortasirketi"))
                        policeno.add(item.getString("policeturu"))
                        tel.add(item.getString("telefon1"))
                        markaresim.add(
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                                .toString()
                        )
                       // Toast.makeText(this, item.getString("markaresim"), Toast.LENGTH_LONG).show()
                        Log.d(
                            "MARKA",
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                        )
                        if (policeturu.add(item.getString("policeturu")).toString() != "null") {
                            policeturu.add(item.getString("policeturu"))
                        } else {
                            policeturu.add("")
                        }
                        if (policetarihi.add(item.getString("polbas")).toString() != "null") {
                            policetarihi.add(item.getString("polbas"))
                        } else {
                            policetarihi.add("")
                        }
                        if (kazatarihi.add(item.getString("kazatarihi")).toString() != "null") {
                            kazatarihi.add(item.getString("kazatarihi"))
                        } else {
                            kazatarihi.add("")
                        }
                        if (ihbartarihi.add(item.getString("ihbartarihi")).toString() != "null") {
                            ihbartarihi.add(item.getString("ihbartarihi"))
                        } else {
                            ihbartarihi.add("")
                        }
                        if (mail.add(item.getString("mail")).toString() != "null") {
                            mail.add(item.getString("mail"))
                        } else {
                            mail.add("")
                        }
                    }
                } catch (e: Exception) {
                }
                newRecyclerview.layoutManager = LinearLayoutManager(this)
                newRecyclerview.setHasFixedSize(false)
                newArrayList = arrayListOf<news>()
                getUserData(this)
                servisHesapla(servis_hasar, servis_mekanik)

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // If you want to add a background, a text, an icon
                //  as the user swipes, this is where to start decorating
                //  I will link you to a library I created for that below
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.blue
                        )
                    )
                    .addSwipeLeftLabel(getString(R.string.kartikapat))
                    //.addSwipeRightLabel("OYNAT")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        //  val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text


                        val builder = AlertDialog.Builder(
                            this@TumAlanAramaActivity,
                            R.style.AlertDialogCustom
                        )
                        builder.setTitle(getString(R.string.kartikapat))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            val txtozel_id =
                                viewHolder.itemView.findViewById<TextView>(R.id.txtozel_id).text

                            guncelle_baslik("Kapalı Kartlar", txtozel_id.toString())

                            plaka.clear()

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                            acikKartGetir()
                        }
                        builder.show()


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerview)


    }


    private fun SetLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config: Configuration = Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor: SharedPreferences.Editor =
            getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun ara(plaka: String) {
        val urlsb = "&kadi=" + kadi + "&ozel_id=" + ozel_id + "&plaka=" + plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=plaka_bilgi_getir" + urlsb
        Log.d("KABULNOOOO. ", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["sonuc"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val plaka = item.getString("plaka").toString()


                    }
                } catch (e: Exception) {
                }


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun acikKartGetir() {
        val urlek =
            "&kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&aranan=" + aranan
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_aranan" + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim", url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    servis_mekanik = 0
                    servis_hasar = 0
                    val json = response["plaka"] as JSONArray
                    for (i in 0 until json.length()) {

                        val item = json.getJSONObject(i)

                        plaka.add(item.getString("plaka"))
                        if (item.getString("toplam") != "null") {
                            onarim.add(item.getString("toplam").toFloat())
                        } else {
                            onarim.add(0f)
                        }


                        resim.add(item.getString("resim"))
                        marka.add(item.getString("marka"))
                        model.add(item.getString("model"))
                        modelyili.add(item.getString("modelyili"))
                        kasatipi.add(item.getString("kasatipi"))
                        if (item.getString("dosyano").toString() != "") {
                            dosyano.add(item.getString("dosyano"))
                        } else {
                            dosyano.add("12345")
                        }
                        unvan.add(item.getString("unvan"))
                        renk.add(item.getString("renk"))
                        km.add(item.getString("km") + " KM")
                        baslik.add(item.getString("baslik"))
                        ozel_id.add(item.getString("ozel_id"))
                        kabulnom.add(item.getString("kabulnom"))
                        servis_turu.add(item.getString("servis_turu"))
                        if (item.getString("servis_turu") == "Mekanik Servis") {
                            servis_mekanik += 1
                        } else {
                            servis_hasar += 1
                        }

                        saseno.add(item.getString("saseno"))
                        mua.add(item.getString("muabit"))
                        motorno.add(item.getString("motorno"))
                        modelvers.add(item.getString("modelvers"))
                        adres.add(item.getString("adres"))
                        il.add(item.getString("il"))
                        ilce.add(item.getString("ilce"))
                        sigortasirketi.add(item.getString("sigortasirketi"))
                        policeno.add(item.getString("policeturu"))
                        tel.add(item.getString("telefon1"))
                        yakitturu.add(item.getString("yakitturu"))

                        markaresim.add(
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                                .toString()
                        )
                        //Toast.makeText(this, item.getString("markaresim"), Toast.LENGTH_LONG).show()
                        Log.d(
                            "MARKA",
                            "https://selparbulut.com/resim/" + item.getString("markaresim")
                        )
                        if (policeturu.add(item.getString("policeturu")).toString() != "null") {
                            policeturu.add(item.getString("policeturu"))
                        } else {
                            policeturu.add("")
                        }
                        if (policetarihi.add(item.getString("polbas")).toString() != "null") {
                            policetarihi.add(item.getString("polbas"))
                        } else {
                            policetarihi.add("")
                        }
                        if (kazatarihi.add(item.getString("kazatarihi")).toString() != "null") {
                            kazatarihi.add(item.getString("kazatarihi"))
                        } else {
                            kazatarihi.add("")
                        }
                        if (ihbartarihi.add(item.getString("ihbartarihi")).toString() != "null") {
                            ihbartarihi.add(item.getString("ihbartarihi"))
                        } else {
                            ihbartarihi.add("")
                        }
                        if (mail.add(item.getString("mail")).toString() != "null") {
                            mail.add(item.getString("mail"))
                        } else {
                            mail.add("")
                        }
                    }
                } catch (e: Exception) {
                }
                newRecyclerview.layoutManager = LinearLayoutManager(this)
                newRecyclerview.setHasFixedSize(false)
                newArrayList = arrayListOf<news>()
                getUserData(this)
                servisHesapla(servis_hasar, servis_mekanik)

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // If you want to add a background, a text, an icon
                //  as the user swipes, this is where to start decorating
                //  I will link you to a library I created for that below
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.blue
                        )
                    )
                    .addSwipeLeftLabel(getString(R.string.kartikapat))
                    //.addSwipeRightLabel("OYNAT")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        //  val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text


                        val builder = AlertDialog.Builder(
                            this@TumAlanAramaActivity,
                            R.style.AlertDialogCustom
                        )
                        builder.setTitle(getString(R.string.kartikapat))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            val txtozel_id =
                                viewHolder.itemView.findViewById<TextView>(R.id.txtozel_id).text

                            guncelle_baslik("Kapalı Kartlar", txtozel_id.toString())
                            plaka.clear()

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                            acikKartGetir()
                        }
                        builder.show()


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerview)


    }

    private fun servisHesapla(servisHasar: Int, servisMekanik: Int) {

        txt_hasar.setText(servisHasar.toString())
        txt_mekanik.setText(servisMekanik.toString())

    }

    private fun guncelle_baslik(selectedItem: String, ozel_id: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                acikKartGetir()
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

    private fun getUserData(context: Context) {
        for (i in plaka.indices) {
            val news = news(
                plaka[i],
                resim[i],
                marka[i],
                model[i],
                modelyili[i],
                kasatipi[i],
                dosyano[i],
                unvan[i],
                renk[i],
                km[i],
                baslik[i],
                ozel_id[i],
                context,
                kadi,
                sifre,
                firma_id,
                akullanici_id,
                kabulnom[i],
                servis_turu[i],
                saseno[i],
                mua[i],
                motorno[i],
                modelvers[i],
                adres[i],
                il[i],
                ilce[i],
                sigortasirketi[i],
                policeno[i],
                onarim[i],
                tel[i],
                policeturu[i],
                policetarihi[i],
                kazatarihi[i],
                ihbartarihi[i],
                mail[i],
                markaresim[i],
                servis_hasar,
                servis_mekanik,
                yakitturu[i]
            )

            newArrayList.add(news)
        }
        newRecyclerview.adapter = tumkartlarigetir(newArrayList)
    }

}