package com.selpar.pratikhasar.ui

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.GonderilmisFaturaAdapter
import com.selpar.pratikhasar.adapter.GonderilmisFaturaModel
import org.json.JSONArray
import java.util.ArrayList

class eFaturaKontorYuklemeActivity : AppCompatActivity() {
    lateinit var btn_satin_al_kontor:Button
    lateinit var id100kontor:LinearLayout
    lateinit var id250kontor:LinearLayout
    lateinit var id500kontor:LinearLayout
    lateinit var id1000kontor:LinearLayout
    lateinit var txt_toplam_tutar:TextView
    lateinit var txt_kalan_kontor:TextView
    lateinit var firma_id:String
    var toplam=0
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efatura_kontor_yukleme)
        onBaslat()
        onKalanKontorGetir()
        id100kontor.setOnClickListener {
            toplam=102
            txt_toplam_tutar.setText("Toplam "+toplam+" tl kontör bedeli ödemeniz gerekiyor")
        }
        id250kontor.setOnClickListener {
            toplam = 210
            txt_toplam_tutar.setText("Toplam " + toplam + " tl kontör bedeli ödemeniz gerekiyor")
        }
        id500kontor.setOnClickListener {
            toplam=360
            txt_toplam_tutar.setText("Toplam "+toplam+" tl kontör bedeli ödemeniz gerekiyor")
        }
        id1000kontor.setOnClickListener {
            toplam=660
            txt_toplam_tutar.setText("Toplam "+toplam+" tl kontör bedeli ödemeniz gerekiyor")
        }

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
        btn_satin_al_kontor.setOnClickListener {
            val i= Intent(this,SatinAlMainActivity::class.java)
            i.putExtra("toplam",toplam.toString())
            startActivity(i)
        }
    }

    private fun onKalanKontorGetir() {
        val urlsb = "firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/kalan_kontor.php?" + urlsb
        Log.d("RESİMYOL", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request =  StringRequest(Request.Method.GET, url,
        Response.Listener<String> { response ->
            txt_kalan_kontor.setText(response.toString())

        }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(
            timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)
    }

    private fun onBaslat() {
        id100kontor=findViewById(R.id.id100kontor)
        id250kontor=findViewById(R.id.id250kontor)
        id500kontor=findViewById(R.id.id500kontor)
        id1000kontor=findViewById(R.id.id1000kontor)
        btn_satin_al_kontor=findViewById(R.id.btn_satin_al_kontor)
        txt_toplam_tutar=findViewById(R.id.txt_toplam_tutar)
        txt_kalan_kontor=findViewById(R.id.txt_kalan_kontor)
        firma_id=intent.getStringExtra("firma_id").toString()

    }
}