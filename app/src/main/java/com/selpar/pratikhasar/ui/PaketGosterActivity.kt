package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
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
import com.selpar.pratikhasar.adapter.PaketAdapter
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.ItemModel
import com.squareup.picasso.Picasso
import org.json.JSONArray


class PaketGosterActivity : AppCompatActivity() {
    lateinit var imagemarka:ImageView
    lateinit var txtpaketadi2:TextView
    lateinit var spinner_arac_turu:Spinner
    lateinit var spinner_marka_kart_ac:Spinner
    lateinit var spinner_model_kart_ac:Spinner
    lateinit var aracSelect: String
    lateinit var markaSelect: String
    lateinit var modelSelect: String
    val itemList_kasa = ArrayList<String>()
    val itemList = ArrayList<String>()
    val itemList2 = ArrayList<String>()
    val itemList_model: ArrayList<String> = ArrayList()
    val itemList_vrs : ArrayList<String> = ArrayList()
    val tabel_text_list : ArrayList<String> = ArrayList()
    val tabel_image_list : ArrayList<String> = ArrayList()
    val tabel_id_list : ArrayList<String> = ArrayList()
    lateinit var resimgetir: String
    var resimyolu: String = ""
    var resimmarka: String = ""
    lateinit var tableLayout:TableLayout
    lateinit var coursesGV:GridView
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket_goster)
        onBaslat()
        onKayitGetir()

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

        //addRow(tableLayout, "Metin 1", drawable1)
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
                    modelSelect = selectedItem.toString().toUpperCase().replace(" ", "%20")

                    //if(selectedItem!=null)
                    tabel_text_list.clear()
                     kayit_paket_getir(spinner_arac_turu.selectedItem.toString().replace(" ","%20"),spinner_marka_kart_ac.selectedItem.toString().replace(" ","%20"),selectedItem.toString().replace(" ","%20"))//spinnerUnvanDoldur%
                    // firmaIdGetir()

                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

    }

    private fun kayit_paket_getir(aracturu: String, marka: String, model: String) {

            val urlsb ="&firma_id="+intent.getStringExtra("firma_id")+"&aracturu="+aracturu+"&marka="+marka+"&model="+model
            var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_kayit_bul_secilen"+urlsb
            Log.d("KABULBULLL: ",url)
            val queue: RequestQueue = Volley.newRequestQueue(this)
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                {  response ->
                    try{
                        val json = response["resim"] as JSONArray

                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            //   Picasso.get().load("https://selparbulut.com/resim/"+item.getString("markaresim")).into(imagemarka)
                            //  txtpaketadi2.setText(item.getString("paketadi"))
                            var yol="https://selparbulut.com/resim/"+item.getString("markaresim")
                            Log.d("GELENYOL",yol)
                            tabel_text_list.add(item.getString("paketadi"))
                            tabel_image_list.add(yol)
                            // "https://selparbulut.com/resim/"+item.getString("markaresim"))
                            tabel_id_list.add(item.getString("id"))

                            ///Toast.makeText(this,"boyut:  "+tabel_text_list.size,Toast.LENGTH_LONG).show()

                            }
                        linearDoldur(tabel_text_list,tabel_image_list)

                        /// tabloDoldur(tabel_text_list,tabel_image_list)


                    }catch (e:Exception){
                       // Toast.makeText(this,"HATA:  "+e.message,Toast.LENGTH_LONG).show()

                    }





                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                    /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                          .show()*/
                }
            )
            queue.add(request)
        }


        @SuppressLint("ResourceAsColor")
    fun addRow(tableLayout: TableLayout, text: String, image: String) {
        val analinear = LinearLayout(this)
        val myll = LinearLayout(this)


// Apply the layout parameters to the LinearLayout
       // myll.layoutParams = layoutParams

// Set the orientation to vertical
        myll.orientation = LinearLayout.VERTICAL
        analinear.orientation = LinearLayout.HORIZONTAL

// Set the background color of the LinearLayout
        myll.setBackgroundColor(R.color.mavi)


// Set padding for the LinearLayout
        val row = TableRow(this)
        row.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        val textView = TextView(this)
        textView.text = text
        textView.setPadding(10, 10, 10, 10)
        myll.addView(textView)
        //row.addView(textView)

        val imageView = ImageView(this)
        imageView.layoutParams = TableRow.LayoutParams(
            200,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        Picasso.get().load(image).into(imageView)
        imageView.setPadding(10, 10, 10, 10)

        myll.addView(imageView)
       // row.addView(imageView)
        analinear.addView(myll)
        row.addView(analinear)

        tableLayout.addView(row)
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

    private fun onKayitGetir() {
        val urlsb ="&firma_id="+intent.getStringExtra("firma_id")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_kayit_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["resim"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                     //   Picasso.get().load("https://selparbulut.com/resim/"+item.getString("markaresim")).into(imagemarka)
                      //  txtpaketadi2.setText(item.getString("paketadi"))
                        var yol="https://selparbulut.com/resim/"+item.getString("markaresim")
                        //Toast.makeText(this,yol,Toast.LENGTH_LONG).show()
                        tabel_text_list.add(item.getString("paketadi"))
                        tabel_image_list.add(yol)
                        tabel_id_list.add(item.getString("id"))

                        //"https://selparbulut.com/resim/"+item.getString("markaresim"))
                        ///Toast.makeText(this,"boyut:  "+tabel_text_list.size,Toast.LENGTH_LONG).show()
                       /*this?.let {
                            getUserData(it)

                        }*/
                    }
                    linearDoldur(tabel_text_list,tabel_image_list)


                    /// tabloDoldur(tabel_text_list,tabel_image_list)


                }catch (e:Exception){
                   // Toast.makeText(this,"HATA:  "+e.message,Toast.LENGTH_LONG).show()
                }





            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
            }
        )
        queue.add(request)
    }

    private fun linearDoldur(tabelTextList: ArrayList<String>, tabelImageList: ArrayList<String>) {
        val courseModelArrayList: ArrayList<BitmisModel> = ArrayList<BitmisModel>()

        for (i in 0 until tabelTextList.size) {

            courseModelArrayList.add(BitmisModel(tabel_id_list[i],tabelTextList[i],tabelImageList[i],"kadi", "ozel_id" ,"plaka", "firma_id"))

        }
        /*
        tabelTextList.forEach {
            courseModelArrayList.add(BitmisModel(tabel_id_list[0],tabelTextList[0],tabelImageList[0],"kadi", "ozel_id" ,"plaka", "firma_id"))

        }*/
       // val items = arrayOf("Öğe 1", "Öğe 2", "Öğe 3", "Öğe 4", "Öğe 5", "Öğe 6")
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        //coursesGV.adapter = adapter
        val adapter2 = PaketAdapter(this,courseModelArrayList)

        coursesGV.adapter = adapter2
    }




    private fun onBaslat() {
        coursesGV = findViewById(com.selpar.pratikhasar.R.id.idGVcourses)
        spinner_arac_turu = findViewById(com.selpar.pratikhasar.R.id.spinner_arac_turu)
        spinner_marka_kart_ac = findViewById(com.selpar.pratikhasar.R.id.spinner_marka_kart_ac)
        spinner_model_kart_ac = findViewById(com.selpar.pratikhasar.R.id.spinner_model_kart_ac)




    }
}