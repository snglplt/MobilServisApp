package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray

class TahsilatActivity : AppCompatActivity() {
    lateinit var txt_tarih:EditText
    lateinit var txt_aciklama:EditText
    lateinit var txt_tutar:EditText
    lateinit var btn_kaydet:Button
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var kadi:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    val itemList_cari: ArrayList<String> = ArrayList()
    val cari_listesi: ArrayList<String> = ArrayList()
    lateinit var auto_cari: AutoCompleteTextView
    var cari_var:Boolean=false
    lateinit var spinner1:Spinner
    lateinit var secilen_cari:String
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tahsilat)
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki = intent.getStringExtra("yetki").toString()
        firma_id = intent.getStringExtra("firma_id").toString()
        kullnciid = intent.getStringExtra("kullanici_id").toString()
        kadi = intent.getStringExtra("kadi").toString()
        sifrem = intent.getStringExtra("sifre").toString()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        txt_tarih = findViewById(R.id.txt_tarih)
        txt_aciklama = findViewById(R.id.txt_aciklama)
        txt_tutar = findViewById(R.id.txt_tutar)
        btn_kaydet = findViewById(R.id.btn_kaydet)
        auto_cari = findViewById(R.id.auto_cari)
        spinnerUnvanDoldur(firma_id)
        cari_var_mi()
        tumCariler(firma_id)
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
        txt_tarih.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val dat =
                            (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
                        txt_tarih.setText(dat)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }
        spinner1=findViewById<Spinner>(R.id.spinner)
        val alspinner1 = java.util.ArrayList<String>()
        val _spvalue1 = resources.getStringArray(R.array.tahsilat_turu)
        for (i in _spvalue1.indices) {
            alspinner1.add(_spvalue1[i])
        }
        val adapter1: Any? = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            alspinner1 as List<Any?>
        )
        spinner1.setAdapter(adapter1 as SpinnerAdapter?)
        spinner1?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if(selectedItem.toString()=="Virman"){
                    btn_kaydet.visibility=GONE
                    val alertadd = AlertDialog.Builder(this@TahsilatActivity)
                    alertadd.setTitle("CARİ  SEÇİNİZ!..")
                    val factory = LayoutInflater.from(this@TahsilatActivity)
                    val view: View = factory.inflate(R.layout.virman_cari_sec, null)

                    val spinner_gelen_cari=view.findViewById<Spinner>(R.id.spinner_gelen_cari)
                    val alspinner1 = java.util.ArrayList<String>()

                    for (i in cari_listesi.indices) {
                        alspinner1.add(cari_listesi[i])
                    }
                    val adapter1: Any? = ArrayAdapter<Any?>(
                        view.getContext(),
                        android.R.layout.simple_spinner_item,
                        alspinner1 as List<Any?>
                    )
                    spinner_gelen_cari.setAdapter(adapter1 as SpinnerAdapter?)
                    spinner_gelen_cari?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            secilen_cari = parent.getItemAtPosition(position).toString()


                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                    alertadd.setView(view)
                    alertadd.setPositiveButton(
                        "EKLE"
                    ) { dialogInterface, which ->VirmaEkle(secilen_cari)
                    }
                    alertadd.show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        btn_kaydet.setOnClickListener {

            val queue = Volley.newRequestQueue(this)
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    Toast.makeText(this, "Ekleme Başarılı: " + kadi, Toast.LENGTH_SHORT).show()
                    val i=Intent(this,HomeActivity::class.java)
                    i.putExtra("dilsecimi",dilsecimi)
                    i.putExtra("yetki", yetki)
                    i.putExtra("firma_id", firma_id)
                    i.putExtra("kullanici_id", kullnciid)
                    i.putExtra("kadi", kadi)
                    i.putExtra("sifre", sifrem)
                    i.putExtra("kadi",kadi)
                    startActivity(i)
                    sifirla()
                },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = java.util.HashMap()
                    params["firma_id"] = firma_id
                    params["kadi"] = kadi
                    params["cari_unvan"] = auto_cari.getText().toString()
                    params["tarih"] = txt_tarih.getText().toString()
                    params["tutar"] = txt_tutar.getText().toString()
                    params["aciklama"] = txt_aciklama.getText().toString()
                    params["odeme_turu"]=spinner1.selectedItem.toString()
                    params["tur"] = "tahsilat_ekle"
                    return params
                }
            }
            queue.add(postRequest)

            //  guncelle(stokNo,stokAdi,miktari,fiyati)


        }
           // Toast.makeText(this,"Böyle bir cari kaydı yok!...",Toast.LENGTH_LONG).show()



    }
    fun VirmaEkle(secilen_cari: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this, "Ekleme Başarılı: " + kadi, Toast.LENGTH_SHORT).show()

                sifirla()
                btn_kaydet.visibility= VISIBLE
                val i= Intent(this,HomeActivity::class.java)
                i.putExtra("dilsecimi",dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi",kadi)
                startActivity(i)
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = firma_id
                params["kadi"] = kadi
                params["cari_unvan"] = auto_cari.getText().toString()
                params["tarih"] = txt_tarih.getText().toString()
                params["tutar"] = txt_tutar.getText().toString()
                params["aciklama"] = txt_aciklama.getText().toString()
                params["odeme_turu"]=spinner1.selectedItem.toString()
                params["secilen_cari"]=secilen_cari
                params["tur"] = "tahsilat_ekle"
                return params
            }
        }
        queue.add(postRequest)


    }
    private fun tumCariler(firmaId: String) {
        val urlsb = "&firma=" + firmaId
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bilgi_getir_gonder" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var tel = item.getString("gsm")
                        var cari_unvan=item.getString("cari_unvan")
                        cari_listesi.add(cari_unvan)
                        //sendSMS(tel,etmesaj.getText().toString())
                    }
                }catch (e:Exception){

                }}, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun cari_var_mi() {
        val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_unvan_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val cari = item.getString("cari")
                        if(auto_cari.getText().toString()==item.getString("cari")){
                           cari_var=true

                        }

                    }



                }catch (e:Exception) {//Toast.makeText(this,"spinner doldurma hatası",Toast.LENGTH_LONG).show()}
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
            }
        )
        queue.add(request)    }

    private fun spinnerUnvanDoldur(firma_id:String)
    {   val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_unvan_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val cari = item.getString("cari")
                        itemList_cari.add(cari)
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, itemList_cari)

                    auto_cari.setAdapter(adapter)

                }catch (e:Exception) {//Toast.makeText(this,"spinner doldurma hatası",Toast.LENGTH_LONG).show()}
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
            }
        )
        queue.add(request)

    }
    private fun sifirla() {

        auto_cari.setText("")
        txt_tarih.setText("")
        txt_aciklama.setText("")
        txt_tutar.setText("")
    }
}