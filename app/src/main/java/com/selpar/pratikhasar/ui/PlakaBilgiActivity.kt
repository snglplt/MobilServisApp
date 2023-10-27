package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.squareup.picasso.Picasso
import org.json.JSONArray

class PlakaBilgiActivity : AppCompatActivity() {
    lateinit var marka:TextView
    lateinit var img_resim:ImageView
    lateinit var txt_model:TextView
    lateinit var txt_modelvers:TextView
    lateinit var txt_modelyili:TextView
    lateinit var txt_kasatipi:EditText
    lateinit var txt_kmsaat:TextView
    lateinit var txt_renk:EditText
    lateinit var txt_sase:EditText
    lateinit var txt_konumu:TextView
    lateinit var txt_durumu:TextView
    lateinit var txt_muabi:TextView
    lateinit var txt_plaka_no:EditText
    lateinit var btn_plaka_guncelle:Button
    lateinit var firma_id:String
    lateinit var kullanici_adi:String
    lateinit var resimgetir:String

    lateinit var kadi:String
    lateinit var sifre:String
    lateinit var plaka:String
    lateinit var spinner_renk_kart_ac:Spinner
    lateinit var spinner_kasa_tipi_kart_ac:Spinner
    lateinit var spinner_arac_turu:Spinner
    lateinit var spinner_marka_kart_ac:Spinner
    lateinit var spinner_model_kart_ac:Spinner
    lateinit var spinner_modelVrs_kart_ac:Spinner
    lateinit var spinner_durum_kart_ac:Spinner
    lateinit var spinner_konum_kart_ac:Spinner
    val spinner_kasa_tipi_kart_ac_alspinner1= ArrayList<String>()
    val itemList_kasa= ArrayList<String>()
    val itemList_marka= ArrayList<String>()
    val itemList_model= ArrayList<String>()
    val spinner_durum_kart_ac_alspinner1= ArrayList<String>()
    val spinner_konum_kart_ac_alspinner1= ArrayList<String>()
    val spinner_arac_turu_police_alspinner1= ArrayList<String>()
    lateinit var mua_bitis_tarihi:EditText
    lateinit var txt_motor_no_:EditText
    val spinner_konum_kart_ac_modelvers_alspinner1= ArrayList<String>()
    var resimyolu:String= null.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plaka_bilgi)

        onBaslat()
        btn_plaka_guncelle=findViewById(R.id.btn_guncelle)

        //val yetki=intent.getStringExtra("yetki")
        onApi()
        bilgidoldur("binek")
        btn_plaka_guncelle.setOnClickListener { guncelle_plaka() }
        //
    // Toast.makeText(this,intent.getStringExtra("plaka"),Toast.LENGTH_LONG).show()


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


                }catch (e:Exception){
                   // Toast.makeText(this,"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        queue.add(request)

    }

    private fun onBaslat() {

        // spinner_renk_kart_ac=findViewById(R.id.spinner_renk_kart_ac)
//        spinner_kasa_tipi_kart_ac=findViewById(R.id.spinner_kasa_tipi_kart_ac)
        spinner_marka_kart_ac=findViewById(R.id.spinner_marka_kart_ac)
        spinner_model_kart_ac=findViewById(R.id.spinner_model_kart_ac)
        spinner_modelVrs_kart_ac=findViewById(R.id.spinner_modelVrs_kart_ac)
        spinner_konum_kart_ac=findViewById(R.id.spinner_konum_kart_ac)
        spinner_durum_kart_ac=findViewById(R.id.spinner_durum_kart_ac)
        txt_muabi=findViewById(R.id.mua_bitis_tarihi)
        txt_kasatipi=findViewById(R.id.txt_kasatipi)
        txt_kmsaat=findViewById(R.id.txt_kmsaat)
        txt_renk=findViewById(R.id.txt_renk)
        txt_sase=findViewById(R.id.txt_sase)
        txt_motor_no_=findViewById(R.id.txt_motor_no_)
        mua_bitis_tarihi=findViewById(R.id.mua_bitis_tarihi)
        txt_modelyili=findViewById(R.id.txt_model_y_kart_ac)
        img_resim=findViewById(R.id.image)
        txt_muabi.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (String.format("%2",dayOfMonth) + "-" + String.format("%2",(monthOfYear + 1)) + "-" + year)
                        txt_muabi.setText(dat)
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

        val spinner_durum_kart_ac_value1=this.resources.getStringArray(R.array.durum)
        for(i in spinner_durum_kart_ac_value1.indices){
            spinner_durum_kart_ac_alspinner1.add(spinner_durum_kart_ac_value1[i])
        }
        val spinner_durum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            spinner_durum_kart_ac_alspinner1 as List<Any?>
        )
        spinner_durum_kart_ac.setAdapter(spinner_durum_kart_ac_adapter1 as SpinnerAdapter?)
        val spinner_konum_kart_ac_value1=this.resources.getStringArray(R.array.konum)
        for(i in spinner_konum_kart_ac_value1.indices){
            spinner_konum_kart_ac_alspinner1.add(spinner_konum_kart_ac_value1[i])
        }
        val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            spinner_konum_kart_ac_alspinner1 as List<Any?>
        )
        spinner_konum_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


        firma_id=intent.getStringExtra("firma_id").toString()
        kullanici_adi=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifre=intent.getStringExtra("sifre").toString()
        plaka=intent.getStringExtra("plaka").toString()
       /* Toast.makeText(this,kadi,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,kullanici_adi,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,sifre,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,firma_id,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,plaka,Toast.LENGTH_SHORT).show()*/
        spinner_marka_kart_ac?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                spinner_konum_kart_ac_modelvers_alspinner1.clear()
                itemList_model.clear()
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




                }catch (e:Exception) {//Toast.makeText(this,"MODEL doldur hatası",Toast.LENGTH_LONG).show()}
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        queue.add(request)

    }

    private fun guncelle_plaka()
    {
            val queue = Volley.newRequestQueue(this)
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                   // Toast.makeText(this,"resim yolu: "+resimyolu,Toast.LENGTH_LONG).show()

                   // Toast.makeText(this,"Güncelleme Başarılı: "+plaka,Toast.LENGTH_LONG).show()
                    var i= Intent(this,PlakaBilgiActivity::class.java)
                    i.putExtra("yetki", intent.getStringExtra("yetki"))
                    i.putExtra("firma_id", intent.getStringExtra("firma_id"))
                    i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
                    i.putExtra("kadi", intent.getStringExtra("kadi"))
                    i.putExtra("sifre", intent.getStringExtra("sifre"))
                    i.putExtra("plaka", intent.getStringExtra("plaka"))
                    startActivity(i)

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
                    params["tur"] = "plaka_guncelle"
                    params["marka"] = spinner_marka_kart_ac.selectedItem.toString()
                    params["model"] = spinner_model_kart_ac.selectedItem.toString()
                    if(spinner_modelVrs_kart_ac.selectedItem.toString()!="")
                    params["modelvers"] = spinner_modelVrs_kart_ac.selectedItem.toString()
                    params["modelyili"] = txt_modelyili.getText().toString()
                    params["kasatipi"] = txt_kasatipi.getText().toString()
                    params["kmsaat"] = txt_kmsaat.getText().toString()
                    params["renk"] = txt_renk.getText().toString()
                    params["motorno"]=txt_motor_no_.getText().toString()
                    params["sase"] = txt_sase.getText().toString()
                    params["konumu"] = spinner_konum_kart_ac.selectedItem.toString()
                    params["durumu"] = spinner_durum_kart_ac.selectedItem.toString()
                    params["muabi"] = txt_muabi.getText().toString()
                    params["resim"]=resimyolu.toString()
                    return params
                }
            }
            queue.add(postRequest)
        }


    private fun onApi() {
        val urlsb = "?kadi=" + kadi + "&sifre=" + sifre + "&firma_id=" + firma_id + "&kullanici_id=" + kullanici_adi + "&tur=plaka_getir&plaka="+intent.getStringExtra("plaka").toString()
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

                itemList_marka.add(item.getString("marka"))
                var resim = item.getString("resim")
                Picasso.get().load(resim.toString()).into(img_resim)
                itemList_model.add(item.getString("model"))
                txt_kasatipi.setText(item.getString("kasatipi"))
                txt_kmsaat.setText(item.getString("kmsaat"))
                txt_renk.setText(item.getString("renk"))
                txt_sase.setText(item.getString("saseno"))
                txt_motor_no_.setText(item.getString("motorno"))
                spinner_konum_kart_ac_alspinner1.add(item.getString("arackonum"))
                spinner_durum_kart_ac_alspinner1.add(item.getString("durum"))
                txt_muabi.setText(item.getString("muabit"))
                spinner_konum_kart_ac_modelvers_alspinner1.add(item.getString("modelvers"))
                txt_modelyili.setText(item.getString("modelyili"))
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


}