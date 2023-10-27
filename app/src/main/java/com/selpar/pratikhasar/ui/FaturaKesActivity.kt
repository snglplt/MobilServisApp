package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.OnarimAdapter
import com.selpar.pratikhasar.data.OnarimModel
import com.selpar.pratikhasar.fragment.CariGetirFragment
import com.selpar.pratikhasar.fragment.FaturaGetirFragment
import com.selpar.pratikhasar.fragment.IstekSikayetFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray

class FaturaKesActivity : AppCompatActivity() {
    val itemList_vrs : ArrayList<String> = ArrayList()
    val itemList_stok : ArrayList<String> = ArrayList()
    val itemList_unvan : ArrayList<String> = ArrayList()
    val itemList_vergino : ArrayList<String> = ArrayList()
    lateinit var spinner_kdv:Spinner
    lateinit var auto_unvan:AutoCompleteTextView
    lateinit var auto_vergino:AutoCompleteTextView
    lateinit var txtvergidairesi:EditText
    lateinit var txtadres:EditText
    lateinit var txtil:EditText
    lateinit var txtilce:EditText
    lateinit var txtemail:EditText
    lateinit var autostokno:AutoCompleteTextView
    lateinit var etStokAdi:EditText
    lateinit var txtaciklama:EditText
    lateinit var txtmiktar:EditText
    lateinit var txtfiyat:EditText
    lateinit var btn_iscilik_ekle:Button
    lateinit var btnfatura:Button
    lateinit var btn_stok_sec:Button
    lateinit var floating_fatura:FloatingActionButton
    lateinit var floating_fatura_pdf:FloatingActionButton
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    lateinit var txtToplamFiyat:TextView
    var toplam:Float=0.0f
    lateinit var firma:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var kadi:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var btn_unvan_sec:Button
    lateinit var btn_vergino_sec:Button
     var random:Int=0
     var miktar:Int=0
     var satir_sayisi:Int=0
    lateinit var txtsatir_sayisi:TextView
    lateinit var txt_topla_miktar:TextView
    lateinit var lt_satir:LinearLayout
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fatura_kes)
        onBaslat()
        kdv_getir()
        spinneStokDoldur(firma_id)
        spinnerUnvanDoldur(firma_id)
        spinnerVergiNoDoldur(firma_id)
        random=(1..100).random()*(1..100).random()*(1..100).random()
        txtToplamFiyat.setText("0.00")
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
        btn_unvan_sec.setOnClickListener {
            bilgidoldur(auto_unvan.getText().toString(),kadi)
        }
        btn_vergino_sec.setOnClickListener {
            bilgidoldurVergino(auto_vergino.getText().toString(),kadi)

        }
        btn_iscilik_ekle.setOnClickListener {
            if(auto_unvan.getText().toString().isNotEmpty() &&
                auto_vergino.getText().toString().isNotEmpty() &&
                txtvergidairesi.getText().toString().isNotEmpty() &&
                txtadres.getText().toString().isNotEmpty() &&
                txtil.getText().toString().isNotEmpty() &&
                txtilce.getText().toString().isNotEmpty()

            ){
                if(auto_vergino.getText().toString().length==11 || auto_vergino.getText().toString().length==10){
                    onApi()
                }
                else{
                    Toast.makeText(this,"Lütfen vergi numarasını 10 ya da 11 karakter giriniz",Toast.LENGTH_LONG).show()

                }
            }
            else{
                Toast.makeText(this,R.string.tumalanlar,Toast.LENGTH_LONG).show()

            }
        }
        btn_stok_sec.setOnClickListener {
            val urlsb = "&firma_id="+ firma_id+"&aranan="+autostokno.text
            var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_bilgi_getir" + urlsb
            Log.d("UNVANNNN",url)
            val queue: RequestQueue = Volley.newRequestQueue(this)
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["stokbilgi"] as JSONArray
                        val itemList: ArrayList<String> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            autostokno.setText(item.getString("stokno"))
                            etStokAdi.setText(item.getString("stokadi"))
                            txtfiyat.setText(item.getString("fiyat"))
                            txtmiktar.setText("1")



                        }


                    }catch (e:Exception){Toast.makeText(this,"bilgi doldur hatası",Toast.LENGTH_LONG).show()}

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                    //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(request)
        }
        floating_fatura_pdf.setOnClickListener { kontrolPdf() }
        floating_fatura.setOnClickListener {
            kontorHesapla()

        }
/*
        btn_fatura_olustur.setOnClickListener {
            if(txtunvan.getText().toString().isNotEmpty() &&
                txtvergino.getText().toString().isNotEmpty() &&
                txtvergidairesi.getText().toString().isNotEmpty() &&
                txtaciklama.getText().toString().isNotEmpty() &&
                txtstokno.getText().toString().isNotEmpty() &&
                txtadres.getText().toString().isNotEmpty() &&
                txtil.getText().toString().isNotEmpty() &&
                txtilce.getText().toString().isNotEmpty() &&
                txtmiktar.getText().toString().isNotEmpty()
                    ){
            val i=Intent(this,eFaturaCariKayitliDegilKesActivity::class.java)
            i.putExtra("unvan",txtunvan.getText().toString())
            i.putExtra("vergino",txtvergino.getText().toString())
            i.putExtra("vergidairesi",txtvergidairesi.getText().toString())
            i.putExtra("aciklama",txtaciklama.getText().toString())
            i.putExtra("stokno",txtstokno.getText().toString())
            i.putExtra("adres",txtadres.getText().toString())
            i.putExtra("il",txtil.getText().toString())
            i.putExtra("ilce",txtilce.getText().toString())
            i.putExtra("miktar",txtmiktar.getText().toString())
            i.putExtra("fiyat",txtfiyat.getText().toString())
            i.putExtra("kdv",spinner_kdv.selectedItem.toString())
            startActivity(i)}
            else{
                Toast.makeText(this,R.string.tumalanlar,Toast.LENGTH_LONG).show()
            }
        }*/

    }

    private fun kontrolPdf() {
        if(auto_unvan.getText().toString().isNotEmpty() &&
            auto_vergino.getText().toString().isNotEmpty() &&
            txtvergidairesi.getText().toString().isNotEmpty() &&
            txtadres.getText().toString().isNotEmpty() &&
            txtil.getText().toString().isNotEmpty() &&
            txtilce.getText().toString().isNotEmpty()

        ){
            if(auto_vergino.getText().toString().length==11 || auto_vergino.getText().toString().length==10){
                val alertadd = AlertDialog.Builder(this)
                alertadd.setTitle(auto_unvan.getText().toString()+" için seçilenlerin pdf oluşturulsun mu?")
                alertadd.setPositiveButton(
                    "Evet"
                ) { dialog, which ->
                    val i= Intent(this, InovoicesPdfActivity::class.java)
                    i.putExtra("firma_id",intent.getStringExtra("firma_id"))
                    i.putExtra("unvan",auto_unvan.getText().toString())
                    i.putExtra("vergino",auto_vergino.getText().toString())
                    i.putExtra("vergidairesi",txtvergidairesi.getText().toString())
                    i.putExtra("aciklama",txtaciklama.getText().toString())
                    i.putExtra("stokno",autostokno.getText().toString())
                    i.putExtra("adres",txtadres.getText().toString())
                    i.putExtra("il",txtil.getText().toString())
                    i.putExtra("ilce",txtilce.getText().toString())
                    i.putExtra("miktar",txtmiktar.getText().toString())
                    i.putExtra("fiyat",txtfiyat.getText().toString())
                    i.putExtra("kdv",spinner_kdv.selectedItem.toString())
                    i.putExtra("random",random.toString())
                    startActivity(i)

                }
                alertadd.setNegativeButton(
                    "Hayır"
                ) { dialog, which -> dialog.dismiss() }
                alertadd.show()
            }
            else{
                Toast.makeText(this,"Lütfen vergi numarasını 10 ya da 11 karakter giriniz",Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,R.string.tumalanlar,Toast.LENGTH_LONG).show()

        }

    }


    private fun kontorHesapla() {
        val urlsb = "firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/kalan_kontor.php?" + urlsb
        Log.d("RESİMYOL", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request =  StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                if(response.toString().toInt()>0){
                    alertFaturaOlustur()
                }else{
                    alertkontorYukle()

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                Toast.makeText(this, "GETİRME BAŞARISIZ $error", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(
            timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)
    }
    private fun alertkontorYukle() {
        val alertadd = AlertDialog.Builder(this,R.style.AlertDialogCustom)
        alertadd.setTitle(getString(R.string.kontor_yukle))
        alertadd.setPositiveButton(
            getString(R.string.tamam)
        ) { dialog, which ->
            val i = Intent(this, eFaturaKontorYuklemeActivity::class.java)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kadi", kadi)


            startActivity(i)
            dialog.dismiss()

        }

        alertadd.show()    }

    private fun alertFaturaOlustur() {
        if(auto_unvan.getText().toString().isNotEmpty() &&
            auto_vergino.getText().toString().isNotEmpty() &&
            txtvergidairesi.getText().toString().isNotEmpty() &&
            txtadres.getText().toString().isNotEmpty() &&
            txtil.getText().toString().isNotEmpty() &&
            txtilce.getText().toString().isNotEmpty()

        ){
            if(auto_vergino.getText().toString().length==11 || auto_vergino.getText().toString().length==10){
                val alertadd = AlertDialog.Builder(this)
                alertadd.setTitle(auto_unvan.getText().toString()+" için seçilenlerin e-Faturası oluşturulsun mu?")
                alertadd.setPositiveButton(
                    "Evet"
                ) { dialog, which ->
                    val i= Intent(this, eFaturaCariKayitliDegilKesActivity::class.java)
                    i.putExtra("firma_id",intent.getStringExtra("firma_id"))
                    i.putExtra("unvan",auto_unvan.getText().toString())
                    i.putExtra("vergino",auto_vergino.getText().toString())
                    i.putExtra("vergidairesi",txtvergidairesi.getText().toString())
                    i.putExtra("aciklama",txtaciklama.getText().toString())
                    i.putExtra("stokno",autostokno.getText().toString())
                    i.putExtra("adres",txtadres.getText().toString())
                    i.putExtra("il",txtil.getText().toString())
                    i.putExtra("ilce",txtilce.getText().toString())
                    i.putExtra("miktar",txtmiktar.getText().toString())
                    i.putExtra("fiyat",txtfiyat.getText().toString())
                    i.putExtra("kdv",spinner_kdv.selectedItem.toString())
                    i.putExtra("random",random.toString())
                    startActivity(i)

                }
                alertadd.setNegativeButton(
                    "Hayır"
                ) { dialog, which -> dialog.dismiss() }
                alertadd.show()
            }
            else{
                Toast.makeText(this,"Lütfen vergi numarasını 10 ya da 11 karakter giriniz",Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,R.string.tumalanlar,Toast.LENGTH_LONG).show()

        }

    }

    private fun spinnerVergiNoDoldur(firmaId: String) {
        val urlsb = "&firma_id=" + firmaId
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_vergino_getir" + urlsb
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
                        itemList_vergino.add( item.getString("vergino"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, itemList_vergino)

                    auto_vergino.setAdapter(adapter)

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
    private fun bilgidoldur(selectedItem: String, kadi: String)   {
        val urlsb ="&firma_id="+firma_id+ "&kadi="+ kadi +"&unvan="+selectedItem.replace(" ","%20")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=unvan_bilgi_cari_getir" + urlsb
        Log.d("UNVANNNN",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["unvan_bilgi"] as JSONArray
                    val itemList: ArrayList<String> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        auto_vergino.setText(item.getString("vergino"))
                        txtvergidairesi.setText(item.getString("vergidairesi"))
                        txtadres.setText(item.getString("adres"))
                        txtil.setText(item.getString("il"))
                        txtilce.setText(item.getString("ilce"))


                        //itemList.add(unvan)
                    }


                }catch (e:Exception){Toast.makeText(this,"bilgi doldur hatası",Toast.LENGTH_LONG).show()}

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun bilgidoldurVergino(etvergi: String, kadi: String) {
        val urlsb = "&kadi="+ kadi +"&vergino="+etvergi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=unvan_bilgi_vergino_getir" + urlsb
        Log.d("UNVANNNN",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["unvan_bilgi"] as JSONArray
                    val itemList: ArrayList<String> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        auto_unvan.setText(item.getString("cari_unvan"))
                        txtvergidairesi.setText(item.getString("vergidairesi"))
                        txtadres.setText(item.getString("adres"))
                        txtil.setText(item.getString("il"))
                        txtilce.setText(item.getString("ilce"))
                        //itemList.add(unvan)
                    }


                }catch (e:Exception){Toast.makeText(this,"bilgi doldur hatası:${e.message}",Toast.LENGTH_LONG).show()}

                }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }

            )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)

    }

    private fun onApi(){
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                kayit_getir()
                sifirla()
                //onarimGetir()
                val toast = Toast.makeText(
                    this,
                    "Onarım Eklendi",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0)
                toast.show()
                /* Toast.makeText(requireContext(),"kadi "+kadi,Toast.LENGTH_LONG).show()
                 Toast.makeText(requireContext(),"ozel_id "+ozel_id,Toast.LENGTH_LONG).show()
                // Toast.makeText(requireContext(),"sifre "+sifre,Toast.LENGTH_LONG).show()
                 Toast.makeText(requireContext(),"plaka "+plaka,Toast.LENGTH_LONG).show()*/
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"]=intent.getStringExtra("kadi").toString()
                params["unvan"] = auto_unvan.getText().toString()
                params["vergino"] = auto_vergino.getText().toString()
                params["vergidairesi"] = txtvergidairesi.getText().toString()
                params["adres"]=txtadres.getText().toString()
                params["il"]=txtil.getText().toString()
                params["ilce"]=txtilce.getText().toString()
                params["stok_iscilik_adi"] = etStokAdi.getText().toString()
                params["stok_iscilik_no"] = autostokno.getText().toString()
                params["miktar"] = txtmiktar.getText().toString()
                params["fiyat"] = txtfiyat.getText().toString()
                params["toplam"]=((txtmiktar.getText().toString().toFloat()*txtfiyat.getText().toString().toFloat())+txtmiktar.getText().toString().toFloat()*txtfiyat.getText().toString().toFloat()*spinner_kdv.selectedItem.toString().toInt()/100).toString()
                params["kdv"]=spinner_kdv.selectedItem.toString()
                params["random"]=random.toString()
                params["tur"] = "fatura_iscilik_kaydet"
                return params
            }
        }
        queue.add(postRequest)



    }

    private fun sifirla() {
        autostokno.getText().clear()
        etStokAdi.getText().clear()
        txtmiktar.getText().clear()
        txtfiyat.getText().clear()
        //btn_fatura_guncelle.visibility= View.GONE
    }
    private fun spinnerUnvanDoldur(firma_id:String)
    {

        val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_getir" + urlsb
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
                        val unvan = item.getString("cari")
                        itemList_unvan.add(unvan)
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, itemList_unvan)

                    auto_unvan.setAdapter(adapter)

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

    private fun  spinneStokDoldur(firma_id:String)
    {
        val urlsb = "&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
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
                        ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, itemList_stok)

                    autostokno.setAdapter(adapter)

                }catch (e:Exception){
                    // Toast.makeText(requireContext(),"spinner doldurma hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }
    private fun onBaslat() {
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        spinner_kdv=findViewById(R.id.spinner_kdv)
        auto_unvan=findViewById(R.id.auto_unvan)
        auto_vergino=findViewById(R.id.auto_vergino)
        txtvergidairesi=findViewById(R.id.txtvergidairesi)
        txtadres=findViewById(R.id.txtadres)
        txtil=findViewById(R.id.txtil)
        txtilce=findViewById(R.id.txtilce)
        txtemail=findViewById(R.id.txtemail)
        autostokno=findViewById(R.id.etStokNo)
        etStokAdi=findViewById(R.id.etStokAdi)
        txtaciklama=findViewById(R.id.etStokAdi)
        txtmiktar=findViewById(R.id.etMiktar)
        txtfiyat=findViewById(R.id.etFiyat)
        floating_fatura=findViewById(R.id.floating_fatura)
        btn_iscilik_ekle=findViewById(R.id.btn_iscilik_ekle)
        txtToplamFiyat=findViewById(R.id.txtToplamFiyat)
        newRecyclerviewm=findViewById(R.id.rc_onarim)
        btn_stok_sec=findViewById(R.id.btn_stok_sec)
        btn_unvan_sec=findViewById(R.id.btn_unvan_sec)
        btn_vergino_sec=findViewById(R.id.btn_vergino_sec)
        txtsatir_sayisi=findViewById(R.id.txtsatir_sayisi)
        txt_topla_miktar=findViewById(R.id.txt_topla_miktar)
        lt_satir=findViewById(R.id.lt_satir)
        floating_fatura_pdf=findViewById(R.id.floating_fatura_pdf)



    }
    private fun kayit_getir() {
        lt_satir.visibility=VISIBLE
        satir_sayisi=0
        miktar=0
        val urlsb = "&unvan=" + auto_unvan.getText().toString().replace(" ","%20")+"&vergino="+auto_vergino.getText().toString()+"&random="+random
        var url = "https://pratikhasar.com/netting/mobil.php?tur=fatura_iscilik_getir" + urlsb
        Log.d("STOK",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["fatura"] as JSONArray
                    //println(outputObject["plaka"])
                    val itemList: ArrayList<OnarimModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val itemModel = OnarimModel("kadi","ozel_id","plaka",
                            item.getString("id") ,item.getString("stok_iscilik_adi"),item.getString("stok_iscilik_no"),
                            item.getString("miktar").toInt(),item.getString("fiyat").toFloat(),
                            item.getString("parca_turu").toString(),item.getString("kdv"),"parcaiscilik"
                        )
                        toplam +=(item.getString("miktar").toInt()*item.getString("fiyat").toFloat()+
                                item.getString("miktar").toInt()*item.getString("fiyat").toFloat()*item.getString("kdv").toInt()/100)
                        miktar+=item.getString("miktar").toInt()
                        satir_sayisi+=1
                        itemList.add(itemModel)
                    }
                    txtToplamFiyat.setText(toplam.toString())
                    txtsatir_sayisi.setText("Satır sayısı: "+satir_sayisi.toString())
                    txt_topla_miktar.setText("Miktar Toplamı: "+miktar.toString())

                    toplam=0f
                    val adapter =
                        OnarimAdapter(itemList)
                    adapter.notifyItemInserted(itemList.size)

                    newRecyclerviewm.adapter= adapter
                    newRecyclerviewm.layoutManager= LinearLayoutManager(this)
                    val diveyder= DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                    newRecyclerviewm.addItemDecoration(diveyder)
                    newRecyclerviewm.setHasFixedSize(true)
                    newArrayList= arrayListOf<ClipData.Item>()
                    this?.let {
                        getUserData(it)

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)

        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // If you want to add a background, a text, an icon
                //  as the user swipes, this is where to start decorating
                //  I will link you to a library I created for that below
                RecyclerViewSwipeDecorator.Builder( c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(applicationContext,R.color.blue))
                    .addSwipeLeftLabel("SİL")
                    .addSwipeRightLabel("GÜNCELLE")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val txt_stokAdi= viewHolder.itemView.findViewById<TextView>(R.id.txt_stokAdi)
                        val txt_StokNo= viewHolder.itemView.findViewById<TextView>(R.id.txt_StokNo)
                        val txtMiktar= viewHolder.itemView.findViewById<TextView>(R.id.txtMiktar)
                        val txtFiyat= viewHolder.itemView.findViewById<TextView>(R.id.txtFiyat)
                       /* sil_alert(txt_stokAdi.getText().toString(),
                            txt_StokNo.getText().toString(),txtMiktar.getText().toString(),txtFiyat.getText().toString())
                        kayit_getir()*/
                        // newRecyclerviewm.removeItemDecorationAt(position)
                        // newRecyclerviewm.adapter= adapter
                        //  viewHolder.itemView.setBackgroundColor(Color.parseColor("#cc0000"))
                        // viewHolder.itemView.setBackgroundColor(R.color.red)
                        //exampleAdapter.notifyDataSetChanged();
                        // Do something when a user swept left
                    }
                    ItemTouchHelper.RIGHT -> {
                        val txt_stokAdi= viewHolder.itemView.findViewById<TextView>(R.id.txt_stokAdi)
                        val txt_StokNo= viewHolder.itemView.findViewById<TextView>(R.id.txt_StokNo)
                        val txtMiktar= viewHolder.itemView.findViewById<TextView>(R.id.txtMiktar)
                        val txtFiyat= viewHolder.itemView.findViewById<TextView>(R.id.txtFiyat)
                        val txt_kdv=viewHolder.itemView.findViewById<TextView>(R.id.txt_kdv)
                       /*onarim_guncelle(txt_stokAdi.getText().toString(),
                            txt_StokNo.getText().toString(),txtMiktar.getText().toString().toInt(),txtFiyat.getText().toString().toFloat(),txt_kdv.getText().toString())
                        // Do something when a user swept right*/
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)
    }
    private fun getUserData(context: Context) {
        val itemList: java.util.ArrayList<OnarimModel> = java.util.ArrayList()
//        kayit_getir()

        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
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
                        itemList_vrs.add(item.getString("oran").toString())

                    }

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_vrs.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_vrs[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )

                    spinner_kdv.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {
                    Toast.makeText(this, "KDV doldur hatası", Toast.LENGTH_LONG)
                        .show()
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