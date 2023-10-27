package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray

class SatinAlmaEkraniActivity : AppCompatActivity() {
    lateinit var btn_satin_al:Button
    lateinit var tum_radio_grup:RadioGroup
    lateinit var radio_kontor:RadioGroup
    lateinit var txt_sepet:TextView
     var mekanik:Int=0
     var hasar:Int=0
     var kontor:Int=0
    var tumu:Int=0
    var toplam:Int=0
    var mekanik_fiyat:ArrayList<Int> = ArrayList()
    var hasar_fiyat:ArrayList<Int> = ArrayList()
    var hasar_mekanik_fiyat:ArrayList<Int> = ArrayList()
    var kontor_fiyat:ArrayList<Int> = ArrayList()
    var servis_turu:String=""
    var servis_suresi:String=""
    var kontor_sayisi:String=""
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satin_alma_ekrani)
        btn_satin_al=findViewById(R.id.btn_satin_al)

        tum_radio_grup=findViewById(R.id.tum_radio_grup)
        radio_kontor=findViewById(R.id.radio_kontor)
        txt_sepet=findViewById(R.id.txt_sepet)
        txt_sepet.setText("0")
        onMekanikFiyat()
        onHasarFiyat()
        onMekanikHasarFiyat()
        onKontor()
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
            finish()
        }
        tum_radio_grup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { arg0, selectedId ->
            var selectedId = selectedId
            selectedId = tum_radio_grup.getCheckedRadioButtonId()
            val genderchoosed = findViewById<View>(selectedId) as RadioButton
            val gender = genderchoosed.text.toString()
            val mekanikselected=tum_radio_grup.checkedRadioButtonId
            val btn_mekanik=findViewById<RadioButton>(mekanikselected)
            try{
            when(genderchoosed.text){

                "Aylık 120 TL"-> {
                    tumu=mekanik_fiyat[0]
                    servis_turu="Mekanik"
                    servis_suresi="Aylık"
                }
                "3 Ay 330 TL"->{
                    tumu=mekanik_fiyat[1]
                    servis_turu="Mekanik"
                    servis_suresi="3ay"

                }
                "6 Ay 600 TL"->{
                    tumu=mekanik_fiyat[2]
                    servis_turu="Mekanik"
                    servis_suresi="6ay"

                }
                "12 Ay 1080 TL"->{
                    tumu=mekanik_fiyat[3]
                    servis_turu="Mekanik"
                    servis_suresi="12ay"


                }
                "Aylık 200 TL"-> {
                    tumu=hasar_fiyat[0]
                    servis_turu="Hasar"
                    servis_suresi="Aylık"
                }
                "3 Ay 570 TL"->{
                    tumu=hasar_fiyat[1]
                    servis_turu="Hasar"
                    servis_suresi="3ay"
                }
                "6 Ay 1500 TL"->{
                    tumu=hasar_fiyat[2]
                    servis_turu="Hasar"
                    servis_suresi="6ay"
                }
                "12 Ay 1920 TL"->{
                    tumu=hasar_fiyat[3]
                    servis_turu="Hasar"
                    servis_suresi="12ay"
                }
                "Aylık 330 TL"-> {
                    tumu=hasar_mekanik_fiyat[0]
                    servis_turu="Hasar ve Mekanik"
                    servis_suresi="Aylık"
                }
                "3 Ay 930TL"->{
                    tumu=hasar_mekanik_fiyat[1]
                    servis_turu="Hasar ve Mekanik"
                    servis_suresi="3ay"
                }
                "6 Ay 1740 TL"->{
                    tumu=hasar_mekanik_fiyat[2]
                    servis_turu="Hasar ve Mekanik"
                    servis_suresi="6ay"
                }
                "12 Ay 3300 TL"->{
                    tumu=hasar_mekanik_fiyat[3]
                    servis_turu="Hasar ve Mekanik"
                    servis_suresi="12ay"
                }}
            }catch (e:Exception){}
            txt_sepet.setText((tumu+kontor).toString())
        })
        radio_kontor.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { arg0, selectedId ->
            var selectedId = selectedId
            selectedId = radio_kontor.getCheckedRadioButtonId()
            val genderchoosed = findViewById<View>(selectedId) as RadioButton
            val mekanikselected=radio_kontor.checkedRadioButtonId
            when(genderchoosed.text){
                "100 Kontör 85 TL"-> {
                    kontor=kontor_fiyat[0]
                    kontor_sayisi="100"
                }
                "250 Kontör 175 TL"->{
                    kontor=kontor_fiyat[1]
                    kontor_sayisi="250"
                }
                "500 Kontör 300 TL"->{
                    kontor=kontor_fiyat[2]
                    kontor_sayisi="500"
                }
                "1000 Kontör 550 TL"->{
                    kontor=kontor_fiyat[3]
                    kontor_sayisi="1000"
                }
            }
            txt_sepet.setText((tumu+kontor).toString())

        })
        btn_satin_al.setOnClickListener {
            toplam=tumu+kontor
         //   Toast.makeText(this,toplam.toString(),Toast.LENGTH_SHORT).show()
            val i= Intent(this,TaksitSecenekleriActivity::class.java)
            i.putExtra("firma_id",intent.getStringExtra("firma_id"))
            i.putExtra("toplam",toplam.toString())
            i.putExtra("servis",servis_turu)
            i.putExtra("kontor",kontor_sayisi)
            i.putExtra("servis_suresi",servis_suresi)
            startActivity(i)
        }
    }


    private fun onMekanikFiyat() {
        mekanik_fiyat.clear()
        val url ="https://pratikhasar.com/netting/mobil.php?tur=fiyat_getir&servis_turu=mekanik"
        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["fiyat"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        mekanik_fiyat.add(item.getString("aylik").toString().toInt())
                        mekanik_fiyat.add(item.getString("ucay").toString().toInt())
                        mekanik_fiyat.add(item.getString("altiay").toString().toInt())
                        mekanik_fiyat.add(item.getString("onikiay").toString().toInt())
                    }

                } catch (e: Exception) {

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                /* in this case we are simply displaying a toast message.
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show() */
            }
        )
        queue.add(request)

    }
    private fun onHasarFiyat() {
        mekanik_fiyat.clear()
        val url ="https://pratikhasar.com/netting/mobil.php?tur=fiyat_getir&servis_turu=hasar"
        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["fiyat"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        hasar_fiyat.add(item.getString("aylik").toString().toInt())
                        hasar_fiyat.add(item.getString("ucay").toString().toInt())
                        hasar_fiyat.add(item.getString("altiay").toString().toInt())
                        hasar_fiyat.add(item.getString("onikiay").toString().toInt())
                    }

                } catch (e: Exception) {

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                /* in this case we are simply displaying a toast message.
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show() */
            }
        )
        queue.add(request)

    }
    private fun onMekanikHasarFiyat() {
        mekanik_fiyat.clear()
        val url ="https://pratikhasar.com/netting/mobil.php?tur=fiyat_getir&servis_turu=hasar_mekanik"
        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["fiyat"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        hasar_mekanik_fiyat.add(item.getString("aylik").toString().toInt())
                        hasar_mekanik_fiyat.add(item.getString("ucay").toString().toInt())
                        hasar_mekanik_fiyat.add(item.getString("altiay").toString().toInt())
                        hasar_mekanik_fiyat.add(item.getString("onikiay").toString().toInt())
                    }

                } catch (e: Exception) {

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                /* in this case we are simply displaying a toast message.
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show() */
            }
        )
        queue.add(request)

    }
    private fun onKontor() {
        mekanik_fiyat.clear()
        val url ="https://pratikhasar.com/netting/mobil.php?tur=fiyat_getir&servis_turu=kontor"
        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["fiyat"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        kontor_fiyat.add(item.getString("aylik").toString().toInt())
                        kontor_fiyat.add(item.getString("ucay").toString().toInt())
                        kontor_fiyat.add(item.getString("altiay").toString().toInt())
                        kontor_fiyat.add(item.getString("onikiay").toString().toInt())
                    }

                } catch (e: Exception) {

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                /* in this case we are simply displaying a toast message.
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show() */
            }
        )
        queue.add(request)

    }




}