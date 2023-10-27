package com.selpar.pratikhasar.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.fragment.EgzozEmisyonTarihiFragment
import com.selpar.pratikhasar.fragment.KaskoSigortasiFragment
import com.selpar.pratikhasar.fragment.TrafikSigortasiFragment
import org.json.JSONArray
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class SigortaIslemleriActivity : AppCompatActivity() {
    lateinit var btn_trafik_sigortasi:Button
    lateinit var btn_kasko_sigortasi:Button
    var bundlem=Bundle()
    lateinit var dilsecimi:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullanici_id:String
    lateinit var kadi:String
    lateinit var gorev:String
    lateinit var sifre:String
    lateinit var txttoplam_arac:TextView
    var servis_arac=0
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigorta_islemleri)
        onBaslat()
        ontrafikSigortasi()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        btn_trafik_sigortasi.setOnClickListener {
            ontrafikSigortasi()
        }
        btn_kasko_sigortasi.setOnClickListener {
            onKaskoSigortasi()
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
    private fun onKaskoSigortasi() {
        btn_kasko_sigortasi.setBackgroundColor(Color.MAGENTA)
        btn_trafik_sigortasi.setBackgroundColor(Color.GRAY)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        onKayitGetirKasko()
        val fragobj = KaskoSigortasiFragment()
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_sigorta,  fragobj)
            .commit()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun ontrafikSigortasi() {
        btn_kasko_sigortasi.setBackgroundColor(Color.GRAY)
        btn_trafik_sigortasi.setBackgroundColor(Color.MAGENTA)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        onKayitGetirTrafik()
        val fragobj = TrafikSigortasiFragment()
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_sigorta,  fragobj)
            .commit()


    }

    private fun onBaslat() {
        btn_trafik_sigortasi=findViewById(R.id.btn_trafik_sigortasi)
        btn_kasko_sigortasi=findViewById(R.id.btn_kasko_sigortasi)
        txttoplam_arac=findViewById(R.id.txttoplam_arac)
        dilsecimi=intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        kullanici_id=intent.getStringExtra("kullanici_id").toString()
        sifre=intent.getStringExtra("sifre").toString()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
    }
    private fun onKayitGetir() {
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
                    servis_arac+=1
                }
            }catch (e:Exception){}

                servisHesapla(servis_arac)

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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onKayitGetirTrafik() {
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
                    val date = LocalDate.parse(item.getString("trafiksigortasiyaklasan").toString(), formatter)
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
    private fun onKayitGetirKasko() {
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
                    val date = LocalDate.parse(item.getString("kaskosigortasiyaklasan").toString(), formatter)
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

}