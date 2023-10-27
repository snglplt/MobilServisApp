package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
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
import com.selpar.pratikhasar.adapter.StokAdapter
import com.selpar.pratikhasar.data.StokItem
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.HashMap

class StokTanimlamaActivity : AppCompatActivity() {
    lateinit var id:String
    lateinit var txtstoklistesi:TextView
    lateinit var etstokno:EditText
    lateinit var etstokadi:EditText
    lateinit var etlistefiyati:EditText
    lateinit var spinner_kdv:Spinner
    lateinit var spinner_parabirimi:Spinner
    lateinit var spinner_tur:Spinner
    lateinit var btn_kaydet:Button
    lateinit var btn_onarim_guncelle:Button
    val itemList_kdv : ArrayList<String> = ArrayList()
    val spinner_parabirimi_list = ArrayList<String>()
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stok_tanimlama)
        onBaslat()
        kdv_getir()
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
        val spinner_arac_turu_police_alspinner1 = ArrayList<String>()
        val tur_value =this.resources.getStringArray(R.array.tur)
        for (i in tur_value.indices) {
            spinner_arac_turu_police_alspinner1.add(tur_value[i])
        }
        val tur_adapter: Any? = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_arac_turu_police_alspinner1 as List<Any?>
        )
        spinner_tur.setAdapter(tur_adapter as SpinnerAdapter?)
        val spinner_durum_kart_ac_value1 = this.resources.getStringArray(R.array.parabirimi)
        for (i in spinner_durum_kart_ac_value1.indices) {
            spinner_parabirimi_list.add(spinner_durum_kart_ac_value1[i])
        }
        val spinner_durum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_parabirimi_list as List<Any?>
        )
        spinner_parabirimi.setAdapter(spinner_durum_kart_ac_adapter1 as SpinnerAdapter?)

        txtstoklistesi.setOnClickListener {
            val i= Intent(this,StokListesiActivity::class.java)
            i.putExtra("firma_id",intent.getStringExtra("firma_id"))
            startActivity(i)
        }
        btn_kaydet.setOnClickListener {
            if(etstokno.getText().toString().isNotEmpty() &&
                    etstokadi.getText().toString().isNotEmpty() &&
                    etlistefiyati.getText().toString().isNotEmpty()){
                onApi()
            }
            else{
                    Toast.makeText(this,R.string.tumalanlar,Toast.LENGTH_LONG).show()
            }
        }
       // stoktanimlama_bul()
    }

    private fun onApi() {
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
                params["stokno"] = etstokno.getText().toString()
                params["stokadi"] = etstokadi.getText().toString()
                params["fiyat"]= etlistefiyati.getText().toString()
                params["kdv"]=spinner_kdv.selectedItem.toString()
                params["parcatur"]=spinner_tur.selectedItem.toString()
                params["toplam"]=(etlistefiyati.getText().toString().toInt()+etlistefiyati.getText().toString().toInt()*spinner_kdv.selectedItem.toString().toInt()/100).toString()
                params["parabirimi"]=spinner_parabirimi.selectedItem.toString()
                params["tur"] = "stoktanimlama_ekle"
                return params
            }
        }
        queue.add(postRequest)
    }
    private fun kdv_getir() {
        val url ="https://pratikhasar.com/netting/mobil.php?tur=kdv_getir"

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["kdv"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_kdv.add(item.getString("oran").toString())

                    }

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_kdv.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_kdv[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_kdv.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

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


    private fun sifirla() {
        etstokno.setText("")
        etstokadi.setText("")
        etlistefiyati.setText("")
    }

    private fun onBaslat() {
        etstokno=findViewById(R.id.etstokno)
        etstokadi=findViewById(R.id.etstokadi)
        etlistefiyati=findViewById(R.id.etlistefiyati)
        spinner_kdv=findViewById(R.id.spinner_kdv)
        spinner_parabirimi=findViewById(R.id.spinner_parabirimi)
        btn_kaydet=findViewById(R.id.btn_kaydet)
        btn_onarim_guncelle=findViewById(R.id.btn_onarim_guncelle)
        spinner_tur=findViewById(R.id.spinner_tur)
        txtstoklistesi=findViewById(R.id.txtstoklistesi)
    }




}