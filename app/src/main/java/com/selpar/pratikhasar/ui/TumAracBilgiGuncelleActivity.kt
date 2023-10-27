package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.Instant.now
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class TumAracBilgiGuncelleActivity : AppCompatActivity() {
    lateinit var firma_id:String
    lateinit var kullanici_adi:String
    lateinit var resimgetir:String
    lateinit var kadi:String
    lateinit var sifre:String
    lateinit var plaka:String
    lateinit var mua_bitis_tarihi:EditText
    lateinit var muayaklasan:EditText
    lateinit var egzozyaklasan:EditText
    lateinit var trafiksigortasiyaklasan:EditText
    lateinit var kaskosigortasiyaklasan:EditText
    lateinit var txt_model_y_kart_ac:EditText
    lateinit var txt_kasatipi:EditText
    lateinit var txt_kmsaat:EditText
    lateinit var txt_renk:EditText
    lateinit var txt_sase:EditText
    lateinit var txt_motor_no_:EditText
    lateinit var txt_plaka_no:TextView
    lateinit var mua_bitis_tarihi_kalan:TextView
    lateinit var egzozyaklasan_tarihi_kalan:TextView
    lateinit var trafiksigortasiyaklasan_tarihi_kalan:TextView
    lateinit var kaskosigortasiyaklasan_tarihi_kalan:TextView
    lateinit var spinner_marka_kart_ac:Spinner
    lateinit var spinner_model_kart_ac:Spinner
    lateinit var spinner_modelVrs_kart_ac:Spinner
    lateinit var btn_guncelle:Button

    lateinit var img_resim:ImageView
    lateinit var btn_geri:ImageView
    val itemList_marka= ArrayList<String>()
    val itemList_model= ArrayList<String>()
    val spinner_konum_kart_ac_modelvers_alspinner1= ArrayList<String>()
    var resimyolu:String= null.toString()

    val takvim = Calendar.getInstance();
    val yil: Int = takvim.get(Calendar.YEAR)
    val ay: Int = takvim.get(Calendar.MONTH)+1
    val gun: Int = takvim.get(Calendar.DAY_OF_MONTH)
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var mBack:ImageView
    lateinit var mForward:ImageView

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tum_arac_bilgi_guncelle)
        onBaslat()
        overridePendingTransition(R.anim.sag, R.anim.sol)

        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        mBack.setOnClickListener {
            val i=Intent(this,AcikKartlarActivity::class.java)
            i.putExtra("plaka",plaka)
            i.putExtra("ozel_id",intent.getStringExtra("ozel_id"))
            i.putExtra("kadi",intent.getStringExtra("kadi"))
            i.putExtra("sifre",intent.getStringExtra("sifre"))
            i.putExtra("firma_id",intent.getStringExtra("firma_id"))
            startActivity(i)
        }
        val takvim = Calendar.getInstance();
        val yil: Int = takvim.get(Calendar.YEAR)
        val ay: Int = takvim.get(Calendar.MONTH) + 1
        val gun: Int = takvim.get(Calendar.DAY_OF_MONTH)
        itemList_marka.add(intent.getStringExtra("marka").toString())
        itemList_model.add(intent.getStringExtra("model").toString())
        spinner_konum_kart_ac_modelvers_alspinner1.add(
            intent.getStringExtra("modelvers").toString()
        )
        mua_bitis_tarihi.setText(yil.toString() + "-" + ay + "-" + gun)
        muayaklasan.setText(yil.toString() + "-" + ay + "-" + gun)
        egzozyaklasan.setText(yil.toString() + "-" + ay + "-" + gun)
        trafiksigortasiyaklasan.setText(yil.toString() + "-" + ay + "-" + gun)
        kaskosigortasiyaklasan.setText(yil.toString() + "-" + ay + "-" + gun)
        val simpleDateFormat = SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss"
        )
        // val localDate = LocalDate.parse(takvim.toString())
        //val simdikitarih = LocalDate.now()



        mua_bitis_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                        mua_bitis_tarihi.setText(dat)
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
        muayaklasan.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                        muayaklasan.setText(dat)
                        muaGunHesapla()
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
        egzozyaklasan.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                        egzozyaklasan.setText(dat)
                        egzozGunHesapla()
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
        trafiksigortasiyaklasan.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                        trafiksigortasiyaklasan.setText(dat)
                        trafikSigortasiGunHesapla()
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
        kaskosigortasiyaklasan.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                        kaskosigortasiyaklasan.setText(dat)
                        kaskoSigortasiGunHesapla()

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
        btn_guncelle.setOnClickListener { guncelle()
            onApi()
            itemList_marka.clear()
            itemList_marka.add(intent.getStringExtra("marka").toString())
            bilgidoldur("binek")

        }

        bilgidoldur("binek")
        onApi()
        btn_geri.setOnClickListener {
            val i=Intent(this,HomeActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", intent.getStringExtra("kullnciid"))
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", intent.getStringExtra("sifrem"))
            startActivity(i)
        }
        spinner_marka_kart_ac?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                itemList_model.clear()
                spinner_konum_kart_ac_modelvers_alspinner1.clear()
                val selectedItem = parent.getItemAtPosition(position).toString()
                //if(selectedItem!=null)
                model_getir(selectedItem.toString().replace(" ","%20"),"BINEK")//spinnerUnvanDoldur%
                // firmaIdGetir()
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        spinner_model_kart_ac?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                spinner_konum_kart_ac_modelvers_alspinner1.clear()
                val selectedItem = parent.getItemAtPosition(position).toString()
                //if(selectedItem!=null)
                // model_getir(selectedItem.toString().replace(" ","%20"),spinner_arac_turu.selectedItem.toString().replace(" ","%20"))//spinnerUnvanDoldur%
                // firmaIdGetir()
                resim_getir(selectedItem.toString().replace(" ","%20"),spinner_marka_kart_ac.selectedItem.toString(),"BINEK")
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun daysBetween(startDate: LocalDate, endDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }
    private fun guncelle() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
               // Toast.makeText(this,"resim yolu: "+resimyolu,Toast.LENGTH_LONG).show()


                Toast.makeText(this,"Güncelleme Başarılı: "+plaka,Toast.LENGTH_LONG).show()
              //  onApi()
               var i=Intent(this,AcikKartlarActivity::class.java)
                i.putExtra("kadi",kadi)
                i.putExtra("firma_id",firma_id)
                i.putExtra("sifre",sifre)
                i.putExtra("yetki",yetki)
                i.putExtra("kullnciid",kullanici_adi)
                startActivity(i)
                                                 /*
                var i= Intent(this,TumAracBilgiGuncelleActivity::class.java)
                i.putExtra("yetki", intent.getStringExtra("yetki"))
                i.putExtra("firma_id", intent.getStringExtra("firma_id"))
                i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
                i.putExtra("kadi", intent.getStringExtra("kadi"))
                i.putExtra("sifre", intent.getStringExtra("sifre"))
                i.putExtra("plaka", intent.getStringExtra("plaka"))
                i.putExtra("marka",spinner_marka_kart_ac.selectedItem.toString())
                i.putExtra("model",spinner_model_kart_ac.selectedItem.toString())
                if(spinner_modelVrs_kart_ac.selectedItem.toString()!="null")
                i.putExtra("modelvers",spinner_modelVrs_kart_ac.selectedItem.toString())
                i.putExtra("modelyili",txt_model_y_kart_ac.getText().toString())
                i.putExtra("kasatipi",txt_kasatipi.getText().toString())

                startActivity(i)*/


            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            @SuppressLint("SuspiciousIndentation")
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["plaka"] = plaka
                params["tur"] = "plaka_guncelle_bilgi"
                params["marka"] = spinner_marka_kart_ac.selectedItem.toString()
                params["model"] = spinner_model_kart_ac.selectedItem.toString()
                if(spinner_modelVrs_kart_ac.selectedItem.toString()!="")
                    params["modelvers"] = spinner_modelVrs_kart_ac.selectedItem.toString()
                params["modelyili"] = txt_model_y_kart_ac.getText().toString()
                params["kasatipi"] = txt_kasatipi.getText().toString()
                params["kmsaat"] = txt_kmsaat.getText().toString()
                params["renk"] = txt_renk.getText().toString()
                params["motorno"]=txt_motor_no_.getText().toString()
                params["sase"] = txt_sase.getText().toString()
                params["muabi"] = muayaklasan.getText().toString()
                params["resim"]=resimyolu.toString()
                params["egzozemisyon"]=egzozyaklasan.getText().toString()
                params["trafiksigortasiyaklasan"]=trafiksigortasiyaklasan.getText().toString()
                params["kaskosigortasiyaklasan"]=kaskosigortasiyaklasan.getText().toString()
                return params
            }
        }
        queue.add(postRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun muaGunHesapla() {
        var mua = muayaklasan.getText().toString()
        var tire: Int = 0
        for (i in 1 until mua.length) {
            if (mua.substring(i - 1, i) == "-") {
                tire = 1
            } else {

            }
        }
        if (tire == 1) {
            val parts = mua.split("-")
            Log.d("TAG", parts[1])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daysBetween(startDate, endDate)
            } else {
            }
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            mua_bitis_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        } else {
            val parts = mua.split("/")
            Log.d("TAG", parts[0])

            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = daysBetween(startDate, endDate)
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            mua_bitis_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        }
    }
    private fun bilgidoldur(selectedItem: String)   {
        // Toast.makeText(this,"unvan.:"+selectedItem,Toast.LENGTH_LONG).show()
        val urlsb = "&arac_turu="+ selectedItem.toUpperCase()
        //var url = "https://pratikhasar.com/netting/mobil.php?tur=marka_bul" +urlsbV
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+selectedItem.toUpperCase()

        Log.d("ARAC",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["MarkaAl"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var marka = item.getString("Marka")
                        itemList_marka.add(marka)
                    }
                    val spinner_konum_kart_ac_alspinner1= ArrayList<String>()
                    val spinner_konum_kart_ac_value1=this.resources.getStringArray(R.array.konum)
                    for(i in itemList_marka.indices){
                        spinner_konum_kart_ac_alspinner1.add(itemList_marka[i])
                    }
                    val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
                        this,
                        android.R.layout.simple_spinner_item,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_marka_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


                }catch (e:Exception) {//Toast.makeText(this,"bilgi doldur hatası",Toast.LENGTH_LONG).show()}
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        queue.add(request)

    }

    private fun resim_getir(model: String, marka: String, aracturu: String)
    {
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+aracturu.toUpperCase()+"&AracMarka="+marka+"&AracUstModel="+model

        Log.d("resimbuldu",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ModelAl"] as JSONArray
                    spinner_konum_kart_ac_modelvers_alspinner1.add(intent.getStringExtra("modelvers").toString())

                    val itemList: ArrayList<String> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        resimgetir = item.getString("Resim")
                        resimyolu="https://selparbulut.com/$resimgetir"
                        itemList.add(item.getString("Model").toString())
                        Picasso.get().load(/* path = */ "https://selparbulut.com/$resimgetir").into(img_resim);
                    }

                    for(i in itemList.indices){
                        spinner_konum_kart_ac_modelvers_alspinner1.add(itemList[i])
                    }
                    val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
                        this,
                        android.R.layout.simple_spinner_item,
                        spinner_konum_kart_ac_modelvers_alspinner1 as List<Any?>
                    )
                    spinner_modelVrs_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                }catch (e:Exception) {//Toast.makeText(this,"MODEL doldur hatası",Toast.LENGTH_LONG).show()}
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        queue.add(request)

    }
    private fun model_getir(marka: String, arac_turu: String) {
        //Toast.makeText(requireContext(),"unvan.:"+selectedItem,Toast.LENGTH_LONG).show()
        //  val urlsb = "&tur="+ arac_turu.toUpperCase()+"&marka="+marka
        //  val url = "https://pratikhasar.com/netting/mobil.php?tur=model_bul" +urlsb
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+arac_turu.toUpperCase()+"&AracMarka="+marka

        Log.d("MODEL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    itemList_model.clear()
                    itemList_model.add(intent.getStringExtra("model").toString())

                    val json = response["ModelUstAl"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var model = item.getString("UstModel").toString()
                        itemList_model.add(model)
                    }
                    val model_alspinner= ArrayList<String>()
                    for(i in itemList_model.indices){
                        model_alspinner.add(itemList_model[i].toString())
                    }
                    val model_adapter:Any?= ArrayAdapter<Any?>(
                        this,
                        android.R.layout.simple_spinner_item,
                        model_alspinner as List<Any?>
                    )
                    spinner_model_kart_ac.setAdapter(model_adapter as SpinnerAdapter?)




                }catch (e:Exception){Toast.makeText(this,"MODEL doldur hatası",Toast.LENGTH_LONG).show()}

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        queue.add(request)

    }

    private fun onBaslat() {
        firma_id=intent.getStringExtra("firma_id").toString()
        yetki=intent.getStringExtra("yetki").toString()
        kullanici_adi=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifre=intent.getStringExtra("sifre").toString()
        plaka=intent.getStringExtra("plaka").toString()
        egzozyaklasan_tarihi_kalan=findViewById(R.id.egzozyaklasan_tarihi_kalan)
        mua_bitis_tarihi=findViewById(R.id.mua_bitis_tarihi)
        muayaklasan=findViewById(R.id.muayaklasan)
        egzozyaklasan=findViewById(R.id.egzozyaklasan)
        trafiksigortasiyaklasan=findViewById(R.id.trafiksigortasiyaklasan)
        trafiksigortasiyaklasan_tarihi_kalan=findViewById(R.id.trafiksigortasiyaklasan_tarihi_kalan)
        kaskosigortasiyaklasan=findViewById(R.id.kaskosigortasiyaklasan)
        kaskosigortasiyaklasan_tarihi_kalan=findViewById(R.id.kaskosigortasiyaklasan_tarihi_kalan)
        txt_plaka_no=findViewById(R.id.txt_plaka_no)
        spinner_marka_kart_ac=findViewById(R.id.spinner_marka_kart_ac)
        spinner_model_kart_ac=findViewById(R.id.spinner_model_kart_ac)
        spinner_modelVrs_kart_ac=findViewById(R.id.spinner_modelVrs_kart_ac)
        txt_model_y_kart_ac=findViewById(R.id.txt_model_y_kart_ac)
        txt_kasatipi=findViewById(R.id.txt_kasatipi)
        txt_kmsaat=findViewById(R.id.txt_kmsaat)
        txt_renk=findViewById(R.id.txt_renk)
        txt_sase=findViewById(R.id.txt_sase)
        txt_motor_no_=findViewById(R.id.txt_motor_no_)
        img_resim=findViewById(R.id.img_resim)
        btn_guncelle=findViewById(R.id.btn_guncelle)
        btn_geri=findViewById(R.id.btn_geri)
        mua_bitis_tarihi_kalan=findViewById(R.id.mua_bitis_tarihi_kalan)
        txt_plaka_no.setText(plaka)
        txt_model_y_kart_ac.setText(intent.getStringExtra("modelyili"))
        Picasso.get().load(intent.getStringExtra("resim")).into(img_resim)
        itemList_marka.add(intent.getStringExtra("marka").toString())
        itemList_model.add(intent.getStringExtra("model").toString())
        spinner_konum_kart_ac_modelvers_alspinner1.add(intent.getStringExtra("modelvers").toString())
        txt_kasatipi.setText(intent.getStringExtra("kasatipi"))
    }
    private fun onApi() {
        val urlsb = "?kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&kullanici_id=" + kullanici_adi + "&tur=plaka_getir_bilgi&plaka="+intent.getStringExtra("plaka").toString()
        // val urlsb = "?kadi=" + kadi + "&sifre=" + sifre +"&firma_id=" + firma_id + "&tur=plaka_getir&plaka="+"06TC0001"
        //val urlsb = "?kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&kullanici_id=" + kullanici_adi + "&tur=aracbilgi&ozel_id="+39
        //val urlsb = "?kadi=" + kadi tur=aracbilgi
        //val urlsb = "?firma_id=" + firma_id + "&tur=aracbilgi&ozel_id="+39
        var url = "https://pratikhasar.com/netting/mobil.php"+urlsb
        Log.d("TAG",url)
        Log.d("PLAKA",intent.getStringExtra("plaka").toString())
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val json = response["plaka"] as JSONArray
                //println(outputObject["plaka"])
                val item = json.getJSONObject(0)
                itemList_marka.clear()
                itemList_marka.add(item.getString("marka"))
                //var resim = item.getString("resim")
                //Picasso.get().load(resim.toString()).into(img_resim)
                itemList_model.clear()
                itemList_model.add(item.getString("model"))
                spinner_konum_kart_ac_modelvers_alspinner1.clear()
                spinner_konum_kart_ac_modelvers_alspinner1.add(item.getString("modelvers"))
                txt_kasatipi.setText(item.getString("kasatipi"))
                txt_kmsaat.setText(item.getString("kmsaat"))
                txt_renk.setText(item.getString("renk"))
                txt_sase.setText(item.getString("saseno"))
                txt_motor_no_.setText(item.getString("motorno"))
                if(item.getString("muabit")!="null"){
                    muayaklasan.setText(item.getString("muabit"))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        muaGunHesapla()
                    }
                }
                else{
                  //  muayaklasan.setText(String.format("%2",gun.toInt()) + "/" + String.format("%2",ay.toInt()) + "/" + yil.toInt())
                }

                txt_model_y_kart_ac.setText(item.getString("modelyili"))
                if(item.getString("egzozemisyon")!="null"){
                    egzozyaklasan.setText(item.getString("egzozemisyon"))

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        egzozGunHesapla()
                    }
                }
                else{
                  //  egzozyaklasan.setText(String.format("%2",gun) + "/" + String.format("%2",ay) + "/" + yil)

                }
                if(item.getString("trafiksigortasiyaklasan")!="null"){
                    trafiksigortasiyaklasan.setText(item.getString("trafiksigortasiyaklasan"))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        trafikSigortasiGunHesapla()
                    }
                }
                else{

                  //  trafiksigortasiyaklasan.setText(String.format("%2",gun) + "/" + String.format("%2",ay) + "/" + yil)

                }
                if(item.getString("kaskosigortasiyaklasan")!="null"){
                    kaskosigortasiyaklasan.setText(item.getString("kaskosigortasiyaklasan"))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        kaskoSigortasiGunHesapla()
                    }
                }
                else{
                   // kaskosigortasiyaklasan.setText(String.format("%2",gun) + "/" + String.format("%2",ay) + "/" + yil)

                }
                /*
                marka.setText(item.getString("marka").toString())
                var resim = item.getString("resim")
                Picasso.get().load(resim.toString()).into(img_resim)
                txt_model.setText(item.getString("model"))
                txt_kasatipi.setText(item.getString("kasatipi"))
                txt_kmsaat.setText(item.getString("kmsaat"))
                txt_renk.setText(item.getString("renk"))
                txt_sase.setText(item.getString("saseno"))
                txt_konumu.setText(item.getString("arackonum"))
                txt_durumu.setText(item.getString("durum"))
                txt_muabi.setText(item.getString("muabit"))
                txt_modelvers.setText(item.getString("modelvers"))
                txt_modelyili.setText(item.getString("modelyili"))
*/

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        queue.add(request)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun kaskoSigortasiGunHesapla()  {
        var kasko = kaskosigortasiyaklasan.getText().toString()
        var tire: Int = 0
        for (i in 1 until kasko.length) {
            if (kasko.substring(i - 1, i) == "-") {
                tire = 1
            } else {

            }
        }
        if (tire == 1) {
            val parts = kasko.split("-")
            Log.d("TAG", parts[0])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daysBetween(startDate, endDate)
            } else {
            }
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            kaskosigortasiyaklasan_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        } else {
            val parts = kasko.split("/")
            Log.d("TAG", parts[0])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daysBetween(startDate, endDate)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            kaskosigortasiyaklasan_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        }    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun trafikSigortasiGunHesapla() {
        var trafik = trafiksigortasiyaklasan.getText().toString()
        var tire: Int = 0
        for (i in 1 until trafik.length) {
            if (trafik.substring(i - 1, i) == "-") {
                tire = 1
            } else {

            }
        }
        if (tire == 1) {
            val parts = trafik.split("-")
            Log.d("TAG", parts[0])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daysBetween(startDate, endDate)
            } else {
            }
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            trafiksigortasiyaklasan_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        } else {
            val parts = trafik.split("/")
            Log.d("TAG", parts[0])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daysBetween(startDate, endDate)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            trafiksigortasiyaklasan_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        }    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun egzozGunHesapla() {
        var egzoz = egzozyaklasan.getText().toString()
        var tire: Int = 0
        for (i in 1 until egzoz.length) {
            if (egzoz.substring(i - 1, i) == "-") {
                tire = 1
            } else {

            }
        }
        if (tire == 1) {
            val parts = egzoz.split("-")
            Log.d("TAG", parts[0])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daysBetween(startDate, endDate)
            } else {
            }
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            egzozyaklasan_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        } else {
            val parts = egzoz.split("/")
            Log.d("TAG", parts[0])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = daysBetween(startDate, endDate)
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            egzozyaklasan_tarihi_kalan.setText(daysBetween.toString()+" Gün Kalmıştır")
        }    }

}