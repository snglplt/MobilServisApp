package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray


class MailAyarlariActivity : AppCompatActivity() {
    lateinit var mail_adres:EditText
    lateinit var mail_sifre:EditText
    lateinit var smtp_port:EditText
    lateinit var mail_server:EditText
    lateinit var btn_kaydet:Button
    lateinit var kadi:String
    val spinner_durum_ssl= ArrayList<String>()
    lateinit var spinner_durum_kart_ac: Spinner
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail_ayarlari)
        onBaslat()
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
        val spinner_durum_kart_ac_value1=this.resources.getStringArray(R.array.ssl)
        for(i in spinner_durum_kart_ac_value1.indices){
            spinner_durum_ssl.add(spinner_durum_kart_ac_value1[i])
        }
        val spinner_durum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_durum_ssl as List<Any?>
        )
        spinner_durum_kart_ac.setAdapter(spinner_durum_kart_ac_adapter1 as SpinnerAdapter?)
        onBilgiGetir()
        mail_sifre.setOnClickListener {
            mail_sifre.inputType =
                InputType.TYPE_CLASS_TEXT
        }
        btn_kaydet.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response

                    Toast.makeText(this,"Ekleme Başarılı: "+mail_adres.getText().toString(), Toast.LENGTH_SHORT).show() },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["kadi"]=kadi
                    params["mail"] = mail_adres.getText().toString()
                    params["mailsifre"] = mail_sifre.getText().toString()
                    params["smtp_port"] = smtp_port.getText().toString()
                    params["ssl_port"] = spinner_durum_kart_ac.selectedItem.toString()
                    params["mail_sever"] = mail_server.getText().toString()
                    params["tur"] = "mail_guncelle"
                    return params
                }
            }
            queue.add(postRequest)

        }

    }

    private fun onBilgiGetir()
    {
        var url = "https://pratikhasar.com/netting/mobil.php?tur=mail_bilgi_getir&kadi="+intent.getStringExtra("kadi").toString()
        Log.d("ACTiVE",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{

                val json = response["giris"] as JSONArray
                var servis_turu:String
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    mail_adres.setText(item.getString("mail").toString())
                    mail_sifre.setText(item.getString("mailsifre"))
                    smtp_port.setText(item.getString("smtp_port"))
                    mail_server.setText(item.getString("mail_server"))
                }
            }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }
    private fun onBaslat() {
        mail_adres=findViewById(R.id.etmail_adres)
        mail_sifre=findViewById(R.id.etmail_sifre)
        btn_kaydet=findViewById(R.id.btn_kaydet)
        spinner_durum_kart_ac=findViewById(R.id.spinner_etssl_port)
        smtp_port=findViewById(R.id.etsmtp_port)
        mail_server=findViewById(R.id.etmail_server)
        kadi=intent.getStringExtra("kadi").toString()
    }
}