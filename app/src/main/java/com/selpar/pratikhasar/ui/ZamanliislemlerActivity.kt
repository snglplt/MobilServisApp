package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.BakimZamaniGelenAdapter
import com.selpar.pratikhasar.data.news
import com.selpar.pratikhasar.fragment.BaklimZamaniGelenlerFragment
import com.selpar.pratikhasar.fragment.BitmisHaliFragment
import com.selpar.pratikhasar.fragment.EgzozEmisyonTarihiFragment
import com.selpar.pratikhasar.fragment.MuayenetarihigelenlerFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ZamanliislemlerActivity : AppCompatActivity() {
    lateinit var btn_bakim_zamani: Button
    lateinit var muayene_yaklasan: Button
    lateinit var btn_egzozemisyon_yaklasan: Button
    lateinit var txttoplam_arac:TextView
    var bundlem=Bundle()
    lateinit var dilsecimi:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullanici_id:String
    lateinit var kadi:String
    lateinit var gorev:String
    lateinit var sifre:String
    var servis_arac=0
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zamanliislemler)
        onBaslat()
        BakimZamaniGelenler()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        btn_bakim_zamani.setOnClickListener {

            BakimZamaniGelenler()
        }
        muayene_yaklasan.setOnClickListener {
            MuayeneTarihiyaklasan()
        }
        btn_egzozemisyon_yaklasan.setOnClickListener {
            EgzozEmisyontarihiYaklasan()
        }
        mBack.setOnClickListener {
            val i= Intent(this,HomeActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kadi", kadi)
            startActivity(i)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun EgzozEmisyontarihiYaklasan() {
        btn_bakim_zamani.setBackgroundColor(Color.GRAY)
        muayene_yaklasan.setBackgroundColor(Color.GRAY)
        btn_egzozemisyon_yaklasan.setBackgroundColor(Color.MAGENTA)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        onKayitGetirEgzozEmisyon()
        val fragobj = EgzozEmisyonTarihiFragment()
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_zamanli,  fragobj)
            .commit()


    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun BakimZamaniGelenler() {
        btn_bakim_zamani.setBackgroundColor(Color.MAGENTA)
        muayene_yaklasan.setBackgroundColor(Color.GRAY)
        btn_egzozemisyon_yaklasan.setBackgroundColor(Color.GRAY)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        onKayitGetirBakim()
        val fragobj = BaklimZamaniGelenlerFragment()
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_zamanli,  fragobj)
            .commit()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onKayitGetirBakim() {
        servis_arac=0

        val urlek="&kadi="+kadi+"&sifre="+sifre+"&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_sirala"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{
                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    val formatter = DateTimeFormatter.ISO_DATE
                    val date = LocalDate.parse(item.getString("kartacilistarihi").toString(), formatter)
                    val bugun = LocalDate.now()
                    val daysBetween = ChronoUnit.DAYS.between(date,bugun)
                    val sonuc = 365 - daysBetween.toString().toInt()
                    if (sonuc <= 30) {
                        servis_arac += 1
                    }
                }
                servisHesapla(servis_arac)
                servis_arac=0
            }catch (e:Exception){}



            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)


    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onKayitGetirMuayene() {
        servis_arac=0

        val urlek="&kadi="+kadi+"&sifre="+sifre+"&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_sirala"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{
                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    val formatter = DateTimeFormatter.ISO_DATE
                    val date = LocalDate.parse(item.getString("muabit").toString(), formatter)
                    val bugun = LocalDate.now()
                    val daysBetween = ChronoUnit.DAYS.between(bugun,date)
                    if(daysBetween<=30) {
                        servis_arac += 1
                    }
                }
                servisHesapla(servis_arac)
                servis_arac=0
            }catch (e:Exception){}



            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)


    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onKayitGetirEgzozEmisyon() {
        servis_arac=0
        val urlek="&kadi="+kadi+"&sifre="+sifre+"&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_sirala"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{
                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    val formatter = DateTimeFormatter.ISO_DATE
                    val date = LocalDate.parse(item.getString("egzozemisyon").toString(), formatter)
                    val bugun = LocalDate.now()
                    val daysBetween = ChronoUnit.DAYS.between(bugun,date)
                    if(daysBetween<=30) {
                        servis_arac += 1
                    }
                }
                servisHesapla(servis_arac)
                servis_arac=0
            }catch (e:Exception){}



            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)


    }



    private fun servisHesapla(servisArac: Int) {
        txttoplam_arac.setText("Toplam Araç Sayısı: "+servisArac.toString())
    }


    private fun onBaslat() {
        dilsecimi=intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        kullanici_id=intent.getStringExtra("kullanici_id").toString()
        sifre=intent.getStringExtra("sifre").toString()
        btn_bakim_zamani=findViewById(R.id.btn_bakim_zamani)
        muayene_yaklasan=findViewById(R.id.muayene_yaklasan)
        btn_egzozemisyon_yaklasan=findViewById(R.id.btn_egzozemisyon_yaklasan)
        txttoplam_arac=findViewById(R.id.txttoplam_arac)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun MuayeneTarihiyaklasan() {
        btn_bakim_zamani.setBackgroundColor(Color.GRAY)
        muayene_yaklasan.setBackgroundColor(Color.MAGENTA)
        btn_egzozemisyon_yaklasan.setBackgroundColor(Color.GRAY)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        onKayitGetirMuayene()
        val fragobj = MuayenetarihigelenlerFragment()
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_zamanli,  fragobj)
            .commit()

    }
}


