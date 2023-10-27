package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray

class UstaTanimlamaActivity : AppCompatActivity() {
    lateinit var txtustatc:EditText
    lateinit var txtustaad:EditText
    lateinit var txtustasoyad:EditText
    lateinit var txtustail:EditText
    lateinit var txtustailce:EditText
    lateinit var txtustatel:EditText
    lateinit var txtustamail:EditText
    lateinit var txtustaadres:EditText
    lateinit var spinner_brans:Spinner
    lateinit var btn_kaydet:Button
    val itemList_brans:ArrayList<String> = ArrayList()
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usta_tanimlama)
        onBaslat()
        bransDoldur()
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
        btn_kaydet.setOnClickListener{onApi()}
    }

    private fun onApi() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
               // kayit_getir()
                sifirla()
                //onarimGetir()
                val toast = Toast.makeText(
                    this,
                    "Usta Eklendi",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0)
                toast.show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["firma_id"] = intent.getStringExtra("firma_id").toString()
                params["tc"] = txtustatc.getText().toString()
                params["ad"] = txtustaad.getText().toString()
                params["soyad"] = txtustasoyad.getText().toString()
                params["brans"] = spinner_brans.selectedItem.toString()
                params["il"] = txtustail.getText().toString()
                params["ilce"] =txtustailce.getText().toString()
                params["adres"] = txtustaadres.getText().toString()
                params["tel"] = txtustatel.getText().toString()
                params["mail"]=txtustamail.getText().toString()
                params["tur"] = "ustatanimlama_kaydet"
                return params
            }
        }
        queue.add(postRequest)



    }

    private fun sifirla() {
        txtustatc.setText("")
        txtustaad.setText("")
        txtustasoyad.setText("")
        txtustail.setText("")
        txtustailce.setText("")
        txtustatel.setText("")
        txtustamail.setText("")
    }



    private fun bransDoldur(){
        val url ="https://pratikhasar.com/netting/mobil.php?tur=brans_getir"

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["brans"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_brans.add(item.getString("brans").toString())

                    }

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_brans.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_brans[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_brans.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

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

    private fun onBaslat() {
        txtustatc=findViewById(R.id.txtustatc)
        txtustaad=findViewById(R.id.txtustaad)
        txtustasoyad=findViewById(R.id.txtustasoyad)
        spinner_brans=findViewById(R.id.spinner_brans)
        txtustail=findViewById(R.id.txtustail)
        txtustailce=findViewById(R.id.txtustailce)
        txtustatel=findViewById(R.id.txtustatel)
        txtustamail=findViewById(R.id.txtustamail)
        btn_kaydet=findViewById(R.id.btn_kaydet)
        txtustaadres=findViewById(R.id.txtustaadres)
    }
}