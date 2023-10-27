package com.selpar.pratikhasar.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.mekanikkartlarigetir
import com.selpar.pratikhasar.data.news
import org.json.JSONArray

class MekanikAcikKartlarActivity : AppCompatActivity() {
    lateinit var kadi:String
    lateinit var sifre:String
    lateinit var firma_id:String
    private lateinit var newRecyclerview : RecyclerView

    private lateinit var newArrayList : ArrayList<news>

    var yakitturu = ArrayList<String>()
    var plaka = ArrayList<String>()
    var resim = ArrayList<String>()
    var marka =ArrayList<String>()
    var model =ArrayList<String>()
    var modelyili = ArrayList<String>()
    var kasatipi = ArrayList<String>()
    var dosyano =ArrayList<String>()
    var unvan =ArrayList<String>()
    var renk =ArrayList<String>()
    var km = ArrayList<String>()
    var tel = ArrayList<String>()
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
    var policeno = ArrayList<String>()
    var onarim = ArrayList<Float>()
    var policeturu = ArrayList<String>()
    var policetarihi = ArrayList<String>()
    var kazatarihi = ArrayList<String>()
    var ihbartarihi = ArrayList<String>()
    var mail = ArrayList<String>()
    var markaresim = ArrayList<String>()
    var toplam : Float=0.0F
    var akadi=""
    var asifre=""
    var afirma_id=""
    var akullanici_id=""
    var servis_hasar=0
    var servis_mekanik=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mekanik_acik_kartlar)
        kadi= intent.getStringExtra("kadi").toString()
        sifre= intent.getStringExtra("sifre").toString()
        firma_id= intent.getStringExtra("firma_id").toString()
        acikKartGetir()
    }
    private fun onarimBul(plaka: ArrayList<String>):Float{
        val urlek="&plaka="+ plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_bul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->

                val json = response["plaka"] as JSONArray
                //println(outputObject["plaka"])
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    val miktar=item.getString("miktar")
                    val fiyat=item.getString("fiyat")
                    toplam +=(miktar.toInt()*fiyat.toFloat()).toFloat()
                }
            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        return toplam
    }

    private fun acikKartGetir() {
        val urlek="&kadi="+kadi+"&sifre="+sifre+"&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=deneme"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val json = response["plaka"] as JSONArray
                //println(outputObject["plaka"])
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    plaka.add(item.getString("plaka"))
                    onarim.add(1000f)
                    resim.add(item.getString("resim"))
                    marka.add(item.getString("marka"))
                    model.add(item.getString("model"))
                    modelyili.add(item.getString("modelyili"))
                    kasatipi.add(item.getString("kasatipi"))
                    dosyano.add(item.getString("dosyano"))
                    unvan.add(item.getString("unvan"))
                    renk.add(item.getString("renk"))
                    km.add(item.getString("km")+" KM")
                    baslik.add(item.getString("baslik"))
                    ozel_id.add(item.getString("ozel_id"))
                    kabulnom.add(item.getString("kabulnom"))
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
                    policeturu.add(item.getString("policeturu"))
                    policetarihi.add(item.getString("polbas"))
                    kazatarihi.add(item.getString("kazatarihi"))
                    ihbartarihi.add(item.getString("ihbartarihi"))
                    mail.add(item.getString("mail"))
                    markaresim.add(item.getString("markaresim"))
                    yakitturu.add(item.getString("yakitturu"))

                    if(item.getString("servis_turu")=="Mekanik Servis")
                    {
                        servis_mekanik+=1
                    }else{
                        servis_hasar+=1
                    }
                    //akadi[i]=item.getString("kadi")


                    //Log.d("TAG",  item.getString("plaka"))
                    // Your code her
                }
                newRecyclerview=findViewById(R.id.recyclerView)
                newRecyclerview.layoutManager= LinearLayoutManager(this)
                newRecyclerview.setHasFixedSize(false)
                newArrayList= arrayListOf<news>()
                getUserData(this)

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                /* in this case we are simply displaying a toast message.
                Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT)
                    .show()*/
            }
        )
        queue.add(request)

    }

    private fun getUserData(context: Context) {
        for(i in plaka.indices){
            /* var imageUrl=resim[i]
             var resim : Drawable
                  Picasso.get().load(imageUrl)*/

            val news = news(plaka[i],resim[i],marka[i],model[i],modelyili[i],kasatipi[i],dosyano[i],unvan[i],renk[i],km[i],baslik[i],
                ozel_id[i],context,kadi,asifre,afirma_id,akullanici_id,kabulnom[i], servis_turu[i],saseno[i],mua[i],motorno[i],
                modelvers[i],adres[i],il[i],ilce[i],sigortasirketi[i],policeno[i],onarim[i],tel[i],
                policeturu[i],policetarihi[i],kazatarihi[i],ihbartarihi[i],mail[i],markaresim[i],servis_hasar,servis_mekanik,yakitturu[i])

            newArrayList.add(news)
        }
        newRecyclerview.adapter= mekanikkartlarigetir(newArrayList)
    }

}