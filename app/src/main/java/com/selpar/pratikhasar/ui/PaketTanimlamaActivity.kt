package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.squareup.picasso.Picasso
import org.json.JSONArray


class PaketTanimlamaActivity : AppCompatActivity() {
    lateinit var id:String
    lateinit var etpaketadi: EditText
    lateinit var etmarka: EditText
    lateinit var etmodel: EditText
    lateinit var etyilaraligi: EditText
    lateinit var etmiktar: EditText
    lateinit var etfiyat: EditText
    lateinit var etstokno: EditText
    lateinit var etstokadi: EditText
    lateinit var spinner_kdv: Spinner
    lateinit var btn_kaydet: Button
    lateinit var autostok: AutoCompleteTextView
    lateinit var btn_onarim_guncelle: Button
    lateinit var btnstoktangelen: Button
    val itemList_kdv : ArrayList<String> = ArrayList()
    val spinner_parabirimi_list = ArrayList<String>()
    private lateinit var newRecyclerviewm: RecyclerView
    val itemList_stok: ArrayList<String> = ArrayList()
    lateinit var spinner_arac_turu: Spinner
    lateinit var spinner_marka_kart_ac: Spinner
    lateinit var spinner_model_kart_ac: Spinner
    lateinit var spinner_modelVrs_kart_ac: Spinner
    lateinit var tur: Spinner
    lateinit var aracSelect: String
    lateinit var markaSelect: String
    lateinit var modelSelect: String
    val itemList_kasa = ArrayList<String>()
    val itemList = ArrayList<String>()
    val itemList2 = ArrayList<String>()
    val itemList_model: ArrayList<String> = ArrayList()
    val itemList_vrs : ArrayList<String> = ArrayList()
    lateinit var resimgetir: String
    var resimyolu: String = ""
    var resimmarka: String = ""
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket_tanimlama)
        onBaslat()
        kdv_getir()
        spinnerUnvanDoldur(intent.getStringExtra("firma_id").toString())
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
        val tur_spinner_list = ArrayList<String>()
        val tur_value =this.resources.getStringArray(R.array.tur)
        for (i in tur_value.indices) {
            tur_spinner_list.add(tur_value[i])
        }
        val tur_adapter: Any? = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            tur_spinner_list as List<Any?>
        )
        tur.setAdapter(tur_adapter as SpinnerAdapter?)

        val spinner_arac_turu_police_alspinner1 = ArrayList<String>()
        val spinner_arac_turu_police_value1 =
            this.resources.getStringArray(R.array.arac_turu_mekanik)
        for (i in spinner_arac_turu_police_value1.indices) {
            spinner_arac_turu_police_alspinner1.add(spinner_arac_turu_police_value1[i])
        }
        val spinner_arac_turu_police_adapter1: Any? = ArrayAdapter<Any?>(
            applicationContext,
            R.layout.spinner_item_text,
            spinner_arac_turu_police_alspinner1 as List<Any?>
        )
        spinner_arac_turu.setAdapter(spinner_arac_turu_police_adapter1 as SpinnerAdapter?)
        spinner_arac_turu?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                //if(selectedItem!=null)
                spinner_marka_kart_ac.setAdapter(null)
                spinner_modelVrs_kart_ac.setAdapter(null)
                spinner_model_kart_ac.setAdapter(null)

                aracSelect = selectedItem.toUpperCase().replace(" ", "%20")
                itemList.clear()

                bilgidoldur(aracSelect)//spinnerUnvanDoldur%sp

                ///bilgiMarka(aracSelect)
                // firmaIdGetir()
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        spinner_marka_kart_ac?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    itemList2.clear()
                    itemList_model.clear()
                    itemList_vrs.clear()
                    //spinner_marka_kart_ac.setAdapter(null)
                    spinner_modelVrs_kart_ac.setAdapter(null)
                    spinner_model_kart_ac.setAdapter(null)
                    markaSelect = selectedItem.toString().toUpperCase().replace(" ", "%20")

                    //if(selectedItem!=null)
                    model_getir(markaSelect, aracSelect)//spinnerUnvanDoldur%
                    // firmaIdGetir()
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        spinner_model_kart_ac?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    itemList_model.clear()
                    itemList_vrs.clear()
                    spinner_modelVrs_kart_ac.setAdapter(null)
                    modelSelect = selectedItem.toString().toUpperCase().replace(" ", "%20")

                    //if(selectedItem!=null)
                    // model_getir(selectedItem.toString().replace(" ","%20"),spinner_arac_turu.selectedItem.toString().replace(" ","%20"))//spinnerUnvanDoldur%
                    // firmaIdGetir()

                    resim_getir(modelSelect, markaSelect, aracSelect)
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        btnstoktangelen.setOnClickListener {
            val urlsb = "&firma_id="+ intent.getStringExtra("firma_id").toString()+"&aranan="+autostok.text
            var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_bilgi_getir" + urlsb
            Log.d("UNVANNNN",url)
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["stokbilgi"] as JSONArray
                        val itemList: ArrayList<String> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            etstokno.setText(item.getString("stokno"))
                            etstokadi.setText(item.getString("stokadi"))
                            etfiyat.setText(item.getString("fiyat"))
                            etmiktar.setText("1")
                            try {
                                val imm =
                                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                            } catch (e: Exception) {
                            }


                        }


                    }catch (e:Exception){
                       // Toast.makeText(applicationContext,"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                    }

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                    //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(request)
        }
        btn_kaydet.setOnClickListener { onApi() }

    }
    private fun resim_getir(model: String, marka: String, aracturu: String) {
        val url =//"https://pratikhasar.com/netting/mobil.php?tur=model_bul&marka="+marka+"&tur="+aracturu
            "https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+aracturu.toUpperCase()+"&AracMarka="+marka+"&AracUstModel="+model

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ModelAl"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if(item.getString("Resim").toString()!="null") {
                            resimgetir = item.getString("Resim")
                            resimyolu = "https://selparbulut.com/$resimgetir"
                            itemList_vrs.add(item.getString("Model").toString())
                        }
                    }


                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_vrs.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_vrs[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        applicationContext,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_modelVrs_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

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

    private fun model_getir(marka: String, arac_turu: String) {
        //Toast.makeText(requireContext(),"unvan.:"+selectedItem,Toast.LENGTH_LONG).show()
        //  val urlsb = "&tur="+ arac_turu.toUpperCase()+"&marka="+marka
        //  val url = "https://pratikhasar.com/netting/mobil.php?tur=model_bul" +urlsb
        val url =
            "https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+arac_turu.toUpperCase()+"&AracMarka="+marka

        Log.d("MODEL", url)
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ModelUstAl"] as JSONArray
                    itemList_model.clear()
                    spinner_model_kart_ac.setAdapter(null)
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if(item.getString("UstModel").toString()!="null") {
                            var model = item.getString("UstModel").toString()
                            itemList_model.add(model)
                        }
                    }
                    val model_alspinner = ArrayList<String>()

                    for (i in itemList_model.indices) {
                        model_alspinner.add(itemList_model[i].toString())
                    }
                    val model_adapter: Any? = ArrayAdapter<Any?>(
                        applicationContext,
                        R.layout.spinner_item_text,
                        model_alspinner as List<Any?>
                    )
                    spinner_model_kart_ac.setAdapter(model_adapter as SpinnerAdapter?)


                } catch (e: Exception) {
                    /* Toast.makeText(requireContext(), "MODEL doldur hatası", Toast.LENGTH_LONG)
                         .show()*/
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                /* in this case we are simply displaying a toast message.
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show()*/
            }
        )
        queue.add(request)

    }

    private fun bilgidoldur(selectedItem: String)   {
        val urlsb = "&arac_turu="+ selectedItem.toString().toUpperCase()
        //var url = "https://pratikhasar.com/netting/mobil.php?tur=marka_bul" +urlsbV
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+selectedItem.toString().toUpperCase()

        Log.d("ARACMARKA",url)
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["MarkaAl"] as JSONArray
                    itemList.clear()
                    spinner_marka_kart_ac.setAdapter(null)
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var marka = item.getString("Marka")
                        resimmarka=item.getString("MarkaResim")

                        itemList.add(marka)
                    }
                    val spinner_konum_kart_ac_alspinner1= ArrayList<String>()
                    val spinner_konum_kart_ac_value1=this.resources.getStringArray(R.array.konum)
                    for(i in itemList.indices){
                        spinner_konum_kart_ac_alspinner1.add(itemList[i])
                    }
                    val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
                        applicationContext,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_marka_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


                }catch (e:Exception){
                    //  Toast.makeText(requireContext(),"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
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
                Log.d("Response", response!!)
                Toast.makeText(this,"Ekleme Başarılı: "+intent.getStringExtra("firma_id"), Toast.LENGTH_SHORT).show()
                sifirla()
                //pakettanimlama_bul()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = intent.getStringExtra("firma_id").toString()
                params["paketadi"] = etpaketadi.getText().toString()
                params["aracturu"] = spinner_arac_turu.selectedItem.toString()
                params["marka"] = spinner_marka_kart_ac.selectedItem.toString()
                params["model"] = spinner_model_kart_ac.selectedItem.toString()
                params["modelvrs"] = spinner_modelVrs_kart_ac.selectedItem.toString()
                params["markaresim"] = resimmarka
                params["resim"] = resimyolu
                params["yilaraligi"] = etyilaraligi.getText().toString()
                params["parcaiscilik"] = tur.selectedItem.toString()
                params["stokno"] = etstokno.getText().toString()
                params["stokadi"] = etstokadi.getText().toString()
                params["miktar"]= etmiktar.getText().toString()
                params["fiyat"]= etfiyat.getText().toString()
                params["kdv"]=spinner_kdv.selectedItem.toString()
                params["toplam"]=(etfiyat.getText().toString().toInt()*etmiktar.getText().toString().toInt()+etfiyat.getText().toString().toInt()*etmiktar.getText().toString().toInt()*spinner_kdv.selectedItem.toString().toInt()/100).toString()
                params["parabirimi"]="TL"
                params["tur"] = "pakettanimlama_ekle"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun sifirla() {
        etstokno.setText("")
        etstokadi.setText("")
        etmiktar.setText("")
        etfiyat.setText("")
        etpaketadi.setText("")

        etyilaraligi.setText("")


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

    private fun onBaslat() {
        etstokno=findViewById(R.id.etstokno)
        etstokadi=findViewById(R.id.etstokadi)
        etpaketadi=findViewById(R.id.etpaketadi)
        etyilaraligi=findViewById(R.id.etyilaraligi)
        etmiktar=findViewById(R.id.etmiktar)
        etfiyat=findViewById(R.id.etfiyat)
        spinner_kdv=findViewById(R.id.spinner_kdv)
        btn_kaydet=findViewById(R.id.btn_kaydet)
        btnstoktangelen=findViewById(R.id.btnstoktangelen)
        newRecyclerviewm=findViewById(R.id.rc_satinal)
        btn_onarim_guncelle=findViewById(R.id.btn_onarim_guncelle)
        autostok=findViewById(R.id.autostok)
        tur=findViewById(R.id.tur)
        spinner_arac_turu = findViewById(R.id.spinner_arac_turu)
        spinner_model_kart_ac = findViewById(R.id.spinner_model_kart_ac)
        spinner_modelVrs_kart_ac = findViewById(R.id.spinner_modelVrs_kart_ac)
        spinner_marka_kart_ac = findViewById(R.id.spinner_marka_kart_ac)

    }
    private fun  spinnerUnvanDoldur(firma_id:String)
    {
        val urlsb = "&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["stok"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_stok.add(item.getString("stokno"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(applicationContext, android.R.layout.select_dialog_singlechoice, itemList_stok)

                    autostok.setAdapter(adapter)

                }catch (e:Exception){
                    // Toast.makeText(requireContext(),"spinner doldurma hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

}