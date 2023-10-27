package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.squareup.picasso.Picasso
import org.json.JSONArray

class SanalPosAyarlariParamActivity : AppCompatActivity() {
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    lateinit var yetki: String
    lateinit var firma_id: String
    lateinit var kullnciid: String
    lateinit var kadi: String
    lateinit var sifrem: String
    lateinit var dilsecimi: String
    lateinit var btn_kaydet:Button
    lateinit var btn_guncelle:Button
    lateinit var txt_username:EditText
    lateinit var txt_password:EditText
    lateinit var txt_guid:EditText
    lateinit var txt_client:EditText
     var kullanici:String=""
     var sifre:String=""
     var guid:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sanal_pos_ayarlari_param)
        onBaslat()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        onKayitGetir()

        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        mBack.setOnClickListener {
            val i = Intent(this, SanalPosAyarlariActivity::class.java)
            i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra(   "firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("gorev", intent.getStringExtra("gorev"))
            i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())

            startActivity(i)
        }
        btn_kaydet.setOnClickListener {
            onApi()
            val i = Intent(this, SanalPosAyarlariActivity::class.java)
            i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra(   "firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("gorev", intent.getStringExtra("gorev"))
            i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())

            startActivity(i)
        }
        btn_guncelle.setOnClickListener {
            onGuncelle()
            val i = Intent(this, SanalPosAyarlariActivity::class.java)
            i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra(   "firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("gorev", intent.getStringExtra("gorev"))
            i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())

            startActivity(i)
        }
    }

    private fun onGuncelle() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response

                   Toast.makeText(this, "Mail Başarılı: ", Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {

                Toast.makeText(this, "hatalı",Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] =firma_id
                params["kullaniciadi"] = txt_username.getText().toString()
                params["sifre"] = txt_password.getText().toString()
                params["guid"] = txt_guid.getText().toString()
                params["client"] = txt_client.getText().toString()
                params["tur"] = "sanal_pos_ayari_guncelle"

                return params
            }
        }
        queue.add(postRequest)
    }

    private fun onKayitGetir() {
        val urlek = "&firma_id=" + intent.getStringExtra("firma_id")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=sanal_pos_bul" + urlek
        Log.d("PROFIL", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
              try{
                val json = response["sanal"] as JSONArray
                //println(outputObject["plaka"])
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    txt_username.setText(item.getString("kullanici").toString())
                    txt_password.setText(item.getString("sifre").toString())
                    txt_guid.setText(item.getString("guid").toString())
                    txt_client.setText(item.getString("client").toString())
                    if(item.getString("kullanici")!="null"){
                        btn_guncelle.visibility= VISIBLE
                        btn_kaydet.visibility=GONE
                    }
                    else{
                        btn_guncelle.visibility= GONE
                        btn_kaydet.visibility= VISIBLE
                    }
                }
            }catch (e:Exception){
                  btn_guncelle.visibility= GONE
                  btn_kaydet.visibility= VISIBLE
            }}, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    private fun onApi() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response

                //   Toast.makeText(this, "Mail Başarılı: ", Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {

              Toast.makeText(this, "hatalı",Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] =firma_id
                params["kullaniciadi"] = txt_username.getText().toString()
                params["sifre"] = txt_password.getText().toString()
                params["guid"] = txt_guid.getText().toString()
                params["client"] = txt_client.getText().toString()
                params["tur"] = "sanal_pos_ayari"

                return params
            }
        }
        queue.add(postRequest)
    }

    private fun onBaslat() {
        txt_username = findViewById(R.id.txt_username)
        txt_password = findViewById(R.id.txt_password)
        txt_guid = findViewById(R.id.txt_guid)
        txt_client = findViewById(R.id.txt_client)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        btn_kaydet = findViewById(R.id.btn_kaydet)
        btn_guncelle = findViewById(R.id.btn_guncelle)
        firma_id=intent.getStringExtra(   "firma_id").toString()

    }
}