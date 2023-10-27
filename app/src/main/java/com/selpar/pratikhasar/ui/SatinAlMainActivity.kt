package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.craftman.cardform.CardForm
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.CariAdapter
import com.selpar.pratikhasar.data.CariModel
import org.json.JSONArray

class SatinAlMainActivity : AppCompatActivity() {
    lateinit var binkontor:CheckBox
    lateinit var ikibinkontor:CheckBox
    lateinit var besbinkontor:CheckBox
    lateinit var  des:TextView
    lateinit var btn_pay:Button
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    lateinit var expiry:TextView
    lateinit var isimsoy:TextView
    lateinit var numara:TextView
    lateinit var ccv:TextView
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satin_al_main)
        binkontor=findViewById(R.id.binkontor)
        ikibinkontor=findViewById(R.id.ikibinkontor)
        besbinkontor=findViewById(R.id.besbinkontor)
        val cardform=findViewById<CardForm>(R.id.carform)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack.setOnClickListener {
            val i= Intent(this,SatinAlmaEkraniActivity::class.java)
            i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("gorev",intent.getStringExtra("gorev"))
            startActivity(i)
            finish()
        }
        des=findViewById<TextView>(com.craftman.cardform.R.id.payment_amount)
        btn_pay=findViewById<Button>(com.craftman.cardform.R.id.btn_pay)
        val isim=findViewById<TextView>(com.craftman.cardform.R.id.card_name)
        numara=findViewById(com.craftman.cardform.R.id.card_number)
        expiry=findViewById(com.craftman.cardform.R.id.card_preview_expiry)
        isimsoy=findViewById(com.craftman.cardform.R.id.card_preview_name)
        val exipydate=findViewById<TextView>(com.craftman.cardform.R.id.payment_amount_holder)
        val date=findViewById<TextView>(com.craftman.cardform.R.id.expiry_date)
        ccv=findViewById(com.craftman.cardform.R.id.card_preview_cvc)
        date.hint="Son Kullanma T."
        exipydate.setText("Ödenecek tutar")
        isim.hint="Kart üstündeki isim"
        numara.hint="Kart numarası"
        expiry.hint="Son Kullanma T."
        isimsoy.hint="İsim Soyisim"
        btn_pay.setText(String.format("Ödeme Yap "))
       // Toast.makeText(this,"toplam: "+intent.getStringExtra("toplam"),Toast.LENGTH_LONG).show()
        if(intent.getStringExtra("toplam")!="null")
        des.setText(intent.getStringExtra("toplam").toString()+"tl")
        btn_pay.setOnClickListener {
            onKayitEkle()
            OdemeYap()

        }

        binkontor?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (binkontor!!.isChecked) {

                    binkontorekle()
                    ikibinkontor.isChecked=false
                    besbinkontor.isChecked=false

                  }
            }
        })
        ikibinkontor?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (ikibinkontor!!.isChecked) {
                    ikibinkontorekle()
                    binkontor.isChecked=false
                    besbinkontor.isChecked=false

                }


            }
        })
        besbinkontor?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (besbinkontor!!.isChecked) {
                    besbinkontorekle()
                    binkontor.isChecked=false
                    ikibinkontor.isChecked=false
                }
            }
        })

    }

    private fun onKayitEkle() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this,"Ekleme Başarılı: "+intent.getStringExtra("firma_id"), Toast.LENGTH_SHORT).show()
                sifirla()
                // stoktanimlama_bul()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = intent.getStringExtra("firma_id").toString()
                params["servis"] = intent.getStringExtra("servis").toString()
                params["kontor"] = intent.getStringExtra("kontor").toString()
                params["fiyat"]= intent.getStringExtra("toplam").toString()
                params["servis_suresi"]= intent.getStringExtra("servis_suresi").toString()
                params["tur"] = "firma_servis_kontor_yukle"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun sifirla() {
        des.setText("0")

    }

    private fun besbinkontorekle() {
        val urlsb = "&paket=besbin"
        var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_getir" + urlsb
        Log.d("bin",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["tutar"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        des.setText(item.getString("tutar"))
                        btn_pay.setText(String.format("Ödeme Yap (%s)",des.getText()))

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    fun OdemeYap() {
        val queue = Volley.newRequestQueue(this)

// GET isteği yapma
        val urlsb = "firma_id="+intent.getStringExtra("firma_id")+
                "&tutar="+intent.getStringExtra("tutar").toString().toFloat().toString().replace(".",",")+
                "&toplam="+intent.getStringExtra("toplam").toString().replace(".",",")+
                "&sonkullanma="+expiry.getText().toString()+
                "&kartisim="+isimsoy.getText().toString().replace(" ","%20")+
                "&kartnumarasi="+numara.getText().toString().replace(" ","%20")+
                "&taksitsayisi="+intent.getStringExtra("taksitsayisi")+
                "&ccv="+ccv.getText().toString()+
                "&sanal_pos="+intent.getStringExtra("sanal_pos")
        var url = "https://pratikhasar.com/netting/soap.php?" + urlsb
        Log.d("taksit",url)
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // PHP tarafından dönen metni işleyebilirsiniz
                println(response)
                Toast.makeText(this,response,  Toast.LENGTH_LONG).show()
                val alertDialog = AlertDialog.Builder(this).create()
                alertDialog.setTitle(getString(R.string.uyari))
                alertDialog.setMessage(response)
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.tamam)) { dialog, _ ->
                    // Tamam butonuna basıldığında yapılacak işlemler
                    dialog.dismiss()
                }
                alertDialog.show()
            },
            Response.ErrorListener { error ->
                // Hata durumunda hata mesajını işleyebilirsiniz
                println("Hata: " + error.message)
                Toast.makeText(this,error.message,  Toast.LENGTH_LONG).show()

            })

// İsteği kuyruğa ekleme
        queue.add(stringRequest)

    }
    private fun ikibinkontorekle() {
        val urlsb = "&paket=ikibin"
        var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_getir" + urlsb
        Log.d("bin",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["tutar"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        des.setText(item.getString("tutar"))
                        btn_pay.setText(String.format("Ödeme Yap (%s)",des.getText()))

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)    }

    private fun binkontorekle() {
        val urlsb = "&paket=bin"
        var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_getir" + urlsb
        Log.d("bin",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["tutar"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        des.setText(item.getString("tutar"))
                        btn_pay.setText(String.format("Ödeme Yap (%s)",des.getText()))

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }
}


