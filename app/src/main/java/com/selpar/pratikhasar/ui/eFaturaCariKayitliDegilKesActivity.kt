package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.FaturaAdapter
import com.selpar.pratikhasar.adapter.FaturaModel
import com.selpar.pratikhasar.data.OnarimModel
import com.selpar.pratikhasar.data.sayidanYaziya
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class eFaturaCariKayitliDegilKesActivity : AppCompatActivity() {
    lateinit var txt_cari_unvan:TextView
    lateinit var txt_adres:TextView
    lateinit var txt_il:TextView
    lateinit var txt_ilce:TextView
    lateinit var txt_vergi_dairesi:TextView
    lateinit var txt_vergi_no:TextView
    lateinit var gridView:GridView
    lateinit var gridViewbaslik:GridView
    lateinit var txtmalhizmettoplamtutar:TextView
    lateinit var txthesaplanankdv:TextView
    lateinit var txtbeyanedilenkdv:TextView
    lateinit var txtvergilerdahiltoplamtutar:TextView
    lateinit var txtodenecektutar:TextView
    lateinit var sayitoyazi:TextView
    lateinit var txttarih:TextView
    lateinit var e_fatura_eklefloating:FloatingActionButton
    var grid_baslik = ArrayList<String>(9)
    var grid_baslik_2 = ArrayList<String>(9)
    private lateinit var newRecyclerviewm: RecyclerView
    lateinit var spinner_evrak_turu:Spinner
    lateinit var spinner_kdv_turu:Spinner
    lateinit var spinner_e_fatura_tipi:Spinner
    lateinit var spinner_evrak_tipi:Spinner
    lateinit var spinner_e_kontrol:Spinner
    lateinit var spinner_plaka_yazdir:Spinner
    lateinit var spinner_km_yazdir:Spinner
    lateinit var spinner_no_yazdir:Spinner
    lateinit var et_sevk_tarihi:EditText
    lateinit var et_odeme_tarihi:EditText
    lateinit var et_evrak_tarihi:EditText
    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efatura_cari_kayitli_degil_kes)
        onBaslat()
        kayit_getir()
        val sekil = SimpleDateFormat()
        val tarih = Date()
        txttarih.setText(sekil.format(tarih))
       // Toast.makeText(this,intent.getStringExtra("vergino").toString(),Toast.LENGTH_LONG).show()
        e_fatura_eklefloating.setOnClickListener {
            e_fatura_oluştur()
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
    }
    @SuppressLint("MissingInflatedId")
    private fun e_fatura_oluştur() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle(getString(R.string.fatura_ayari))
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.fatura_ayarlari, null)
        spinner_evrak_turu=view.findViewById(R.id.spinner_evrak_turu)
        spinner_e_fatura_tipi=view.findViewById(R.id.spinner_e_fatura_tipi)
        et_sevk_tarihi=view.findViewById(R.id.et_sevk_tarihi)
        et_evrak_tarihi=view.findViewById(R.id.et_evrak_tarihi)
        et_odeme_tarihi=view.findViewById(R.id.et_odeme_tarihi)
        spinner_kdv_turu=view.findViewById(R.id.spinner_kdv_turu)
        spinner_evrak_tipi=view.findViewById(R.id.spinner_evrak_tipi)
        spinner_e_kontrol=view.findViewById(R.id.spinner_e_kontrol)
        spinner_plaka_yazdir=view.findViewById(R.id.spinner_plaka_yazdir)
        spinner_km_yazdir=view.findViewById(R.id.spinner_km_yazdir)
        spinner_no_yazdir=view.findViewById(R.id.spinner_no_yazdir)
        val takvim = Calendar.getInstance();
        val yil: Int = takvim.get(Calendar.YEAR)
        val ay: Int = takvim.get(Calendar.MONTH) + 1
        val gun: Int = takvim.get(Calendar.DAY_OF_MONTH)
        et_sevk_tarihi.setText(yil.toString() + "-" + ay + "-" + gun)
        et_evrak_tarihi.setText(yil.toString() + "-" + ay + "-" + gun)
        et_odeme_tarihi.setText(yil.toString() + "-" + ay + "-" + gun)

        val spinner_evrak_turu_value1=this.resources.getStringArray(R.array.efatura_turu)
        val spinner_evrak_turu_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_evrak_turu_value1.indices){
            spinner_evrak_turu_alspinner1.add(spinner_evrak_turu_value1[i])
        }
        val spinner_evrak_turu_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_evrak_turu_alspinner1 as List<Any?>
        )
        spinner_evrak_turu.setAdapter(spinner_evrak_turu_adapter1 as SpinnerAdapter?)
        val spinner_kdv_turu_value1=this.resources.getStringArray(R.array.kdv_tip)
        val spinner_kdv_turu_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_kdv_turu_value1.indices){
            spinner_kdv_turu_alspinner1.add(spinner_kdv_turu_value1[i])
        }
        val spinner_kdv_turu_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_kdv_turu_alspinner1 as List<Any?>
        )
        spinner_kdv_turu.setAdapter(spinner_kdv_turu_adapter1 as SpinnerAdapter?)
        val spinner_e_fatura_tipi_value1=this.resources.getStringArray(R.array.efatura_turu)
        val spinner_e_fatura_tipi_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_e_fatura_tipi_value1.indices){
            spinner_e_fatura_tipi_alspinner1.add(spinner_e_fatura_tipi_value1[i])
        }
        val spinner_e_fatura_tipi_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_e_fatura_tipi_alspinner1 as List<Any?>
        )
        spinner_e_fatura_tipi.setAdapter(spinner_e_fatura_tipi_adapter1 as SpinnerAdapter?)

        val spinner_evrak_tipi_value1=this.resources.getStringArray(R.array.evrak_tipi)
        val spinner_evrak_tipi_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_evrak_tipi_value1.indices){
            spinner_evrak_tipi_alspinner1.add(spinner_evrak_tipi_value1[i])
        }
        val spinner_evrak_tipi_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_evrak_tipi_alspinner1 as List<Any?>
        )
        spinner_evrak_tipi.setAdapter(spinner_evrak_tipi_adapter1 as SpinnerAdapter?)
        val spinner_e_kontrol_value1=this.resources.getStringArray(R.array.e_kontrol)
        val spinner_e_kontrol_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_evrak_tipi_value1.indices){
            spinner_e_kontrol_alspinner1.add(spinner_e_kontrol_value1[i])
        }
        val spinner_e_kontrol_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_e_kontrol_alspinner1 as List<Any?>
        )
        spinner_e_kontrol.setAdapter(spinner_e_kontrol_adapter1 as SpinnerAdapter?)
        val spinner_plaka_yazdir_value1=this.resources.getStringArray(R.array.ssl)
        val spinner_plaka_yazdir_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_plaka_yazdir_value1.indices){
            spinner_plaka_yazdir_alspinner1.add(spinner_plaka_yazdir_value1[i])
        }
        val spinner_plaka_yazdir_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_plaka_yazdir_alspinner1 as List<Any?>
        )
        spinner_plaka_yazdir.setAdapter(spinner_plaka_yazdir_adapter1 as SpinnerAdapter?)
        val spinner_km_yazdir_value1=this.resources.getStringArray(R.array.ssl)
        val spinner_km_yazdir_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_km_yazdir_value1.indices){
            spinner_km_yazdir_alspinner1.add(spinner_km_yazdir_value1[i])
        }
        val spinner_km_yazdir_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_km_yazdir_alspinner1 as List<Any?>
        )
        spinner_km_yazdir.setAdapter(spinner_km_yazdir_adapter1 as SpinnerAdapter?)
        val spinner_no_yazdir_value1=this.resources.getStringArray(R.array.ssl)
        val spinner_no_yazdir_alspinner1= java.util.ArrayList<String>()

        for(i in spinner_no_yazdir_value1.indices){
            spinner_no_yazdir_alspinner1.add(spinner_no_yazdir_value1[i])
        }
        val spinner_no_yazdir_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_no_yazdir_alspinner1 as List<Any?>
        )
        spinner_no_yazdir.setAdapter(spinner_no_yazdir_adapter1 as SpinnerAdapter?)

        et_evrak_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString()+ "-" + (monthOfYear + 1) + "-" +dayOfMonth.toString()  )
                        et_evrak_tarihi.setText(dat)


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

        et_sevk_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString()+ "-" + (monthOfYear + 1) + "-" +dayOfMonth.toString()  )
                        et_sevk_tarihi.setText(dat)


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
        et_odeme_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString()+ "-" + (monthOfYear + 1) + "-" +dayOfMonth.toString()  )
                        et_odeme_tarihi.setText(dat)


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


        //getLastLocation()
        alertadd.setView(view)
        alertadd.setPositiveButton(
            getString(R.string.evet)
        ) { dialogInterface, which ->
            e_FaturaOlustur(spinner_evrak_turu.selectedItem.toString(),
                et_evrak_tarihi.getText().toString(),
                et_sevk_tarihi.getText().toString(),
                et_odeme_tarihi.getText().toString(),
                spinner_kdv_turu.selectedItem.toString(),
                spinner_e_fatura_tipi.selectedItem.toString(),
                spinner_evrak_tipi.selectedItem.toString(),
                spinner_e_kontrol.selectedItem.toString(),
                spinner_plaka_yazdir.selectedItem.toString(),
                spinner_km_yazdir.selectedItem.toString(),
                spinner_no_yazdir.selectedItem.toString()






            )


        }
        alertadd.setNegativeButton(getString(R.string.hayir)){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()
    }
    fun e_FaturaOlustur(
        evrak_turu: String,
        evrak_tarihi: String,
        sevk_tarihi: String,
        odeme_tarihi: String,
        kdv_turu: String,
        fatura_tipi: String,
        evrak_tipi: String,
        e_kontrol: String,
        plakayazdir: String,
        kmyazdir: String,
        noyazdir: String
    ) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response

                Toast.makeText(this, getString(R.string.faturaolusturdu), Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI2")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = intent.getStringExtra("firma_id").toString()
                params["unvan"] = intent.getStringExtra("unvan").toString()
                params["vergino"] = intent.getStringExtra("vergino").toString()
                params["vergidairesi"] = intent.getStringExtra("vergidairesi").toString()
                params["evrak_turu"] = evrak_turu
                params["evrak_tarihi"] = evrak_tarihi
                params["sevk_tarihi"] = sevk_tarihi
                params["odeme_tarihi"] = odeme_tarihi
                params["kdv_turu"] = kdv_turu
                params["fatura_tipi"] = fatura_tipi
                params["evrak_tipi"] = evrak_tipi
                params["e_kontrol"] = e_kontrol
                params["plakayazdir"] = plakayazdir
                params["kmyazdir"] = kmyazdir
                params["noyazdir"] = noyazdir
                params["tur"] = "e_fatura_olustur_kayitli_degil"


                return params
            }
        }
        queue.add(postRequest)
    }
    private fun kayit_getir() {
        val urlsb =
            "&unvan=" + intent.getStringExtra("unvan").toString().replace(" ","%20")+"&vergino="+intent.getStringExtra("vergino")+"&random="+intent.getStringExtra("random")

        var url = "https://pratikhasar.com/netting/mobil.php?tur=fatura_iscilik_getir" + urlsb
        Log.d("STOK", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["fatura"] as JSONArray
                    //println(outputObject["plaka"])
                    var toplam=0.00
                    var toplam_kdv=0.00
                    var tolpam_kdvsiz=0.00
                    grid_baslik_2.add("Kodu")
                    grid_baslik_2.add("Açıklama")
                    grid_baslik_2.add("Miktar")
                    grid_baslik_2.add("Fiyat")
                    grid_baslik_2.add("KDV Oranı")
                    grid_baslik_2.add("KDV Tutarı")
                    grid_baslik_2.add("Mal Hizmet Tutarı")
                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, R.layout.grid_baslik,R.id.textView,grid_baslik_2)
                    val itemList: java.util.ArrayList<FaturaModel> = java.util.ArrayList()

                    gridViewbaslik.setAdapter(adapter)
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        tolpam_kdvsiz+=item.getString("fiyat").toFloat()*item.getString("miktar").toInt()
                        toplam+=(item.getString("fiyat").toFloat()*item.getString("miktar").toInt()+
                                item.getString("fiyat").toFloat()*item.getString("miktar").toInt()*item.getString("kdv").toInt()/100)
                        toplam_kdv+=item.getString("fiyat").toFloat()*item.getString("miktar").toInt()*item.getString("kdv").toInt()/100

                        val itemModel = FaturaModel(
                            item.getString("stok_iscilik_no").toString() ,item.getString("stok_iscilik_adi"),
                            item.getString("miktar").toString(),item.getString("fiyat").toString(),
                            item.getString("kdv").toString(),(item.getString("fiyat").toFloat()*item.getString("miktar").toInt()*item.getString("kdv").toInt()/100
                                    ).toString(),(item.getString("fiyat").toFloat()*item.getString("miktar").toInt()).toString()
                        )
                        itemList.add(itemModel)
                        gridView.setBackgroundColor(Color.WHITE);

                        grid_baslik.add(item.getString("stok_iscilik_no"))
                        grid_baslik.add(item.getString("stok_iscilik_adi"))
                        val miktar=item.getString("miktar")
                        grid_baslik.add(String.format("%.2f",miktar.toFloat()))
                        val fiyat=item.getString("fiyat")
                        grid_baslik.add(String.format("%.2f",fiyat.toFloat()))
                        val kdv=item.getString("kdv")
                        grid_baslik.add("%"+String.format("%.2f",kdv.toFloat()))
                        val kdvorani=(item.getString("fiyat").toFloat()*item.getString("miktar").toInt()*item.getString("kdv").toInt()/100).toString()
                        grid_baslik.add(String.format("%.2f",kdvorani.toFloat()))
                        val oran=(item.getString("fiyat").toFloat()*item.getString("miktar").toInt()).toString()
                        grid_baslik.add(String.format("%.2f",oran.toFloat()))
                        /*val adapter: ArrayAdapter<String> =
                            ArrayAdapter<String>(this, R.layout.grid_items,R.id.textView,grid_baslik)

                        gridView.setAdapter(adapter)*/
                        if((i+1)==json.length()){

                            txtmalhizmettoplamtutar.setText(String.format("%.2f", tolpam_kdvsiz))
                            txthesaplanankdv.setText(String.format("%.2f", toplam_kdv))
                            txtbeyanedilenkdv.setText(String.format("%.2f", toplam_kdv))
                            txtvergilerdahiltoplamtutar.setText(String.format("%.2f", toplam))
                            txtodenecektutar.setText(String.format("%.2f", toplam))
                            val adapter5: ArrayAdapter<String> =
                                ArrayAdapter<String>(this, R.layout.grid_items,R.id.textView,grid_baslik)
                            val yazi=sayidanYaziya()
                            gridView.setAdapter(adapter5)
                            sayitoyazi.setText("Yazı ile Yalnız: "+yazi.convert(toplam.toLong())+" Lira")

                            //val yazi=sayidanYaziya()
                         //   Toast.makeText(this,yazi.convert(toplam.toLong()), Toast.LENGTH_LONG).show()

                        }


                    }
                    val adapter2 =
                        FaturaAdapter(itemList)
                    adapter2.notifyItemInserted(itemList.size)

                    newRecyclerviewm.adapter= adapter2
                    newRecyclerviewm.layoutManager= LinearLayoutManager(this)
                    val diveyder= DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                    newRecyclerviewm.addItemDecoration(diveyder)
                    newRecyclerviewm.setHasFixedSize(true)
                    newArrayList= arrayListOf<ClipData.Item>()
                    this?.let {
                        getUserData(it)

                    }
                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }
    private fun getUserData(context: Context) {
        val itemList: java.util.ArrayList<OnarimModel> = java.util.ArrayList()
//        kayit_getir()

        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }
    private fun onfatura() {
        var toplam=0.00
        var toplam_kdv=0.00
        var tolpam_kdvsiz=0.00
        grid_baslik_2.add("No")
        grid_baslik_2.add("Kodu")
        grid_baslik_2.add("Açıklama")
        grid_baslik_2.add("Miktar")
        grid_baslik_2.add("Fiyat")
        grid_baslik_2.add("KDV Oranı")
        grid_baslik_2.add("KDV Tutarı")
        grid_baslik_2.add("Mal Hizmet Tutarı")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.grid_baslik,R.id.textView,grid_baslik_2)

        gridViewbaslik.setAdapter(adapter)
        grid_baslik.add("1")
        grid_baslik.add(intent.getStringExtra("stokno").toString())
        grid_baslik.add(intent.getStringExtra("aciklama").toString())
        val miktar=intent.getStringExtra("miktar").toString()
        grid_baslik.add(String.format("%.2f",miktar.toFloat()))
        val fiyat=intent.getStringExtra("fiyat").toString()
        grid_baslik.add(String.format("%.2f",fiyat.toFloat()))
        val kdv=intent.getStringExtra("kdv").toString()
        grid_baslik.add("%"+String.format("%.2f",kdv.toFloat()))
        toplam_kdv=(intent.getStringExtra("fiyat").toString().toFloat()*intent.getStringExtra("miktar").toString().toFloat()*intent.getStringExtra("kdv").toString().toFloat()/100).toDouble()

        grid_baslik.add(String.format("%.2f",toplam_kdv.toFloat()))
        val malbedeli=(intent.getStringExtra("fiyat").toString().toFloat()*intent.getStringExtra("miktar").toString().toInt()).toString()
        grid_baslik.add(String.format("%.2f",malbedeli.toFloat()))
        val adapter5: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.grid_items,R.id.textView,grid_baslik)
        val yazi= sayidanYaziya()
        gridView.setAdapter(adapter5)
        tolpam_kdvsiz=(intent.getStringExtra("fiyat").toString().toFloat()*intent.getStringExtra("miktar").toString().toFloat()).toDouble()
        toplam=tolpam_kdvsiz+toplam_kdv
        txtmalhizmettoplamtutar.setText(String.format("%.2f", tolpam_kdvsiz))
        txthesaplanankdv.setText(String.format("%.2f", toplam_kdv))
        txtbeyanedilenkdv.setText(String.format("%.2f", toplam_kdv))
        txtvergilerdahiltoplamtutar.setText(String.format("%.2f", toplam))
        txtodenecektutar.setText(String.format("%.2f", toplam))
        sayitoyazi.setText("Yazı ile Yalnız: "+yazi.convert(toplam.toLong()) +" Lira")

    }

    private fun onBaslat() {
        e_fatura_eklefloating=findViewById(R.id.e_fatura_eklefloating)
        txt_cari_unvan=findViewById(R.id.txt_cari_unvan)
        txt_adres=findViewById(R.id.txt_adres)
        txt_il=findViewById(R.id.txt_il)
        txt_ilce=findViewById(R.id.txt_ilce)
        txt_vergi_dairesi=findViewById(R.id.txt_vergi_dairesi)
        txt_vergi_no=findViewById(R.id.txt_vergi_no)
        gridViewbaslik=findViewById(R.id.gridView_baslik)
        gridView=findViewById(R.id.gridView)
        sayitoyazi=findViewById(com.selpar.pratikhasar.R.id.sayitoyazi2)
        txtmalhizmettoplamtutar=findViewById(com.selpar.pratikhasar.R.id.txtmalhizmettoplamtutar2)
        txthesaplanankdv=findViewById(com.selpar.pratikhasar.R.id.txthesaplanankdv2)
        txtbeyanedilenkdv=findViewById(com.selpar.pratikhasar.R.id.txtbeyanedilenkdv2)
        txtvergilerdahiltoplamtutar=findViewById(com.selpar.pratikhasar.R.id.txtvergilerdahiltoplamtutar2)
        txtodenecektutar=findViewById(com.selpar.pratikhasar.R.id.txtodenecektutar2)
        gridView = findViewById(R.id.gridView)
        gridViewbaslik = findViewById(R.id.gridView_baslik)
        txttarih = findViewById(R.id.txttarih)
        newRecyclerviewm = findViewById(R.id.rc_fatura)
        txt_cari_unvan.setText(intent.getStringExtra("unvan"))
        txt_adres.setText(intent.getStringExtra("adres"))
        txt_il.setText(intent.getStringExtra("il"))
        txt_ilce.setText(intent.getStringExtra("ilce"))
        txt_vergi_dairesi.setText(intent.getStringExtra("vergidairesi"))
        txt_vergi_no.setText(intent.getStringExtra("vergino"))
        gridView = findViewById(R.id.gridView)
        gridViewbaslik = findViewById(R.id.gridView_baslik)
        txttarih = findViewById(R.id.txttarih)

    }
}