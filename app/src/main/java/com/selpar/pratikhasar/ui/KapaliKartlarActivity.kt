package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
import com.selpar.pratikhasar.data.tumkartlarigetir
import com.selpar.pratikhasar.data.news
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList

class KapaliKartlarActivity : AppCompatActivity() {
    lateinit var kadi:String
    lateinit var sifre:String
    lateinit var firma_id:String
    private lateinit var newRecyclerview : RecyclerView

    private lateinit var newArrayList : ArrayList<news>

    var yakitturu = ArrayList<String>()
    var plaka = ArrayList<String>()
    var resim = ArrayList<String>()
    var marka = ArrayList<String>()
    var model = ArrayList<String>()
    var modelyili = ArrayList<String>()
    var kasatipi =ArrayList<String>()
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
    var tel = ArrayList<String>()
    var il = ArrayList<String>()
    var ilce = ArrayList<String>()
    var sigortasirketi = ArrayList<String>()
    var policeno = ArrayList<String>()
    var markaresim = ArrayList<String>()
    var onarim = ArrayList<Float>()
    var onarimbul2= ArrayList<Float>()
    var policeturu = ArrayList<String>()
    var policetarihi = ArrayList<String>()
    var kazatarihi = ArrayList<String>()
    var ihbartarihi = ArrayList<String>()
    var mail = ArrayList<String>()
    var toplam:Float=0.0f
    var akadi=""
    var asifre=""
    var afirma_id=""
    var akullanici_id=""
    lateinit var btn_bilgi_ara:ImageView
    lateinit var plaka_no:EditText
    var dilsecimi:String=""
    var sonucToplam:Float=0.0f
    lateinit var btn_geri:ImageView
    lateinit var btn_barkod:ImageView
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    var servis_hasar=0
    var servis_mekanik=0
    lateinit var mBack:ImageView
    lateinit var mForward:ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kapali_kartlar)
        btn_geri=findViewById(R.id.btn_geri)
        btn_barkod=findViewById(R.id.btn_barkod)
        newRecyclerview=findViewById(R.id.recyclerView)
        dilsecimi= intent.getStringExtra("dilsecimi").toString()
        if(dilsecimi=="en"){
            SetLocale("en")
            recreate()
        }
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        btn_bilgi_ara=findViewById(R.id.btn_bilgi_ara)
        plaka_no=findViewById(R.id.plaka_no)
        kadi= intent.getStringExtra("kadi").toString()
        sifre= intent.getStringExtra("sifre").toString()
        firma_id= intent.getStringExtra("firma_id").toString()
        yetki=intent.getStringExtra("yetki").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        acikKartGetir()
        btn_bilgi_ara.setOnClickListener { ara(plaka_no.getText().toString()) }
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
        btn_barkod.setOnClickListener {
            val i=Intent(this,ScanCodeActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("sifre",sifre)
            i.putExtra("firma_id",firma_id)

            startActivity(i)
        }
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

    private fun ara(plaka: String)
    {
        val i = Intent(this, TumAlanAramaActivity::class.java)
        i.putExtra("yetki", intent.getStringExtra("yetki"))
        i.putExtra("firma_id", intent.getStringExtra("firma_id"))
        i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
        i.putExtra("kadi", intent.getStringExtra("kadi"))
        i.putExtra("sifre", intent.getStringExtra("sifre"))
        i.putExtra("kapali", "kapali")
        if (plaka_no.getText().toString().isNotEmpty()) {
            i.putExtra("aranan", plaka_no.getText().toString().trim())
            startActivity(i)
        } else
            Toast.makeText(this, "Lütfen bir  bilgi girin", Toast.LENGTH_SHORT).show()


    }
    private fun onarimBul(plaka: String):Float{
        val urlek="&plaka="+ plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_bul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("plaka",url)
        var sonucuBuldu=0.0f
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try{

                    val json  = response["plaka"] as JSONArray
                    var miktar=0
                    var fiyat=0f
                    toplam=0f
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        miktar = item.getString("miktar").toInt()
                        fiyat = item.getString("fiyat").toFloat()
                        toplam += (miktar*fiyat).toFloat()

                        // getUserData(this,toplam)
                        // Toast.makeText(this,"Toplam "+toplam,Toast.LENGTH_LONG).show()

                    }
                    sonucToplam=toplam
                    sonucuBuldu=toplam
                    // onarim.add(sonucToplam)
                    /// Toast.makeText(this,"toplam sonuc2 "+sonucToplam,Toast.LENGTH_LONG).show()
                }catch (e:Exception){} } ,{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                //Toast.makeText(this,"HATALIIIII",Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)

        //Toast.makeText(this,"toplam sonuc "+sonucToplam,Toast.LENGTH_LONG).show()
        return 1000.0f+sonucuBuldu
    }



    private fun onarimhesapla() {
        val urlek = "&kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=deneme" + urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim", url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["plaka"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val gelenplaka=item.getString("plaka")
                        val urlek="&plaka="+ gelenplaka
                        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_bul"+urlek
                        val queue: RequestQueue = Volley.newRequestQueue(this)
                        Log.d("plaka",url)
                        var sonucuBuldu=0.0f
                        val request = JsonObjectRequest(Request.Method.GET, url, null,
                            { response ->
                                try{
                                    val json  = response["plaka"] as JSONArray
                                    var miktar=0
                                    var fiyat=0f
                                    toplam=0f
                                    for (i in 0 until json.length()) {
                                        val item = json.getJSONObject(i)
                                        //miktar = item.getString("miktar").toInt()
                                        // fiyat = item.getString("fiyat").toFloat()
                                        toplam += (item.getString("toplam")).toFloat()
                                        // onarim.add(toplam)
                                        onarimbul2.add(sonucToplam)
                                        // getUserData(this,toplam)
                                        // Toast.makeText(this,"Toplam "+toplam,Toast.LENGTH_LONG).show()

                                    }
                                    // onarim.add(sonucToplam)
                                    onarimbul2.add(sonucToplam)
                                    // Toast.makeText(this,"toplam sonuc2 "+sonucToplam,Toast.LENGTH_LONG).show()
                                }catch (e:Exception){} } ,{ error ->
                                Log.e("TAG", "RESPONSE IS $error")
                                ///Toast.makeText(this,"HATALIIIII",Toast.LENGTH_LONG).show()
                            }
                        )
                        queue.add(request)


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
        val urlek="&kadi="+kadi+"&sifre="+sifre+"&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kapali_araclar"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try{

                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)

                    plaka.add(item.getString("plaka"))
                    if(item.getString("toplam")!="null") {
                        onarim.add(item.getString("toplam").toFloat())
                    }
                    else{
                        onarim.add(0f)
                    }


                    resim.add(item.getString("resim"))
                    marka.add(item.getString("marka"))
                    model.add(item.getString("model"))
                    modelyili.add(item.getString("modelyili"))
                    kasatipi.add(item.getString("kasatipi"))
                    if(item.getString("dosyano").toString()!=""){
                        dosyano.add(item.getString("dosyano"))
                    }
                    else{
                        dosyano.add("12345")
                    }
                    unvan.add(item.getString("unvan"))
                    renk.add(item.getString("renk"))
                    km.add(item.getString("km")+" KM")
                    baslik.add(item.getString("baslik"))
                    ozel_id.add(item.getString("ozel_id"))
                    kabulnom.add(item.getString("kabulnom"))
                    servis_turu.add(item.getString("servis_turu"))
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

                    markaresim.add(item.getString("markaresim"))
                    if(item.getString("servis_turu")=="Mekanik Servis")
                    {
                        servis_mekanik+=1
                    }else{
                        servis_hasar+=1
                    }
                    if(policeturu.add(item.getString("policeturu")).toString()!="null"){
                        policeturu.add(item.getString("policeturu"))
                    }
                    else {
                        policeturu.add("")
                    }
                    if(policetarihi.add(item.getString("polbas")).toString()!="null"){
                        policetarihi.add(item.getString("polbas"))
                    }
                    else {
                        policetarihi.add("")
                    }
                    if(kazatarihi.add(item.getString("kazatarihi")).toString()!="null"){
                        kazatarihi.add(item.getString("kazatarihi"))
                    }else {
                        kazatarihi.add("")
                    }
                    if(ihbartarihi.add(item.getString("ihbartarihi")).toString()!="null"){
                        ihbartarihi.add(item.getString("ihbartarihi"))
                    }else {
                        ihbartarihi.add("")
                    }
                    if(mail.add(item.getString("mail")).toString()!="null"){
                        mail.add(item.getString("mail"))
                    }else {
                        mail.add("")
                    }

                }}catch (e:Exception){}

                newRecyclerview.layoutManager= LinearLayoutManager(this)
                newRecyclerview.setHasFixedSize(false)
                newArrayList= arrayListOf<news>()
                getUserData(this)

            },{ error ->
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
                RecyclerViewSwipeDecorator.Builder( c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel(getString(R.string.acik_kartlar))
                    //.addSwipeRightLabel(getString(R.string.kartikaldir))
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



                        val builder = AlertDialog.Builder(this@KapaliKartlarActivity, R.style.AlertDialogCustom)
                        builder.setTitle(getString(R.string.acik_kartlar))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            val txtozel_id =
                                viewHolder.itemView.findViewById<TextView>(R.id.txtozel_id).text

                            guncelle_baslik("Kart Açıldı", txtozel_id.toString())

                            plaka.clear()

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                        }
                        builder.show()
                    }
                    ItemTouchHelper.RIGHT ->{
                        viewHolder.itemId
                        val builder = AlertDialog.Builder(this@KapaliKartlarActivity, R.style.AlertDialogCustom)
                        builder.setTitle(getString(R.string.kartikapat))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            val plaka_= viewHolder.itemView.findViewById<TextView>(R.id.Tvplaka).text

                            karti_kaldir(plaka_.toString())
                            plaka.clear()
                            acikKartGetir()

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                        }
                        builder.show()

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerview)

    }

    private fun karti_kaldir(plaka: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this,"Başlık Guncelle Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"]=kadi
                params["firma_id"]=firma_id
                params["plaka"]= plaka
                params["tur"] = "karti_kapat"

                return params
            }
        }
        queue.add(postRequest)

    }

    private fun guncelle_baslik(selectedItem: String, ozel_id: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                plaka.clear()
                acikKartGetir()
                Toast.makeText(this,"Başlık Guncelle Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"]=kadi
                params["firma_id"]=firma_id
                params["ozel_id"]= ozel_id
                params["baslik"] = selectedItem
                params["tur"] = "baslik_guncelle"

                return params
            }
        }
        queue.add(postRequest)

    }

    private fun getUserData(context: Context) {
        for(i in plaka.indices){
            val news = news(plaka[i],resim[i],marka[i],model[i],modelyili[i],kasatipi[i],dosyano[i],unvan[i],renk[i],km[i],baslik[i],ozel_id[i],context,kadi,sifre,firma_id,akullanici_id,kabulnom[i],
                servis_turu[i],saseno[i],mua[i],motorno[i],modelvers[i],adres[i],il[i],ilce[i], sigortasirketi[i],policeno[i],onarim[i],
                tel[i],
                policeturu[i],
                policetarihi[i],
                kazatarihi[i],
                ihbartarihi[i],
                mail[i],
                markaresim[i],servis_hasar,servis_mekanik,yakitturu[i])

            newArrayList.add(news)
        }
        newRecyclerview.adapter= tumkartlarigetir(newArrayList)
    }

}