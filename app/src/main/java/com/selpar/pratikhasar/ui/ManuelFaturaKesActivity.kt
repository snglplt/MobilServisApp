package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.util.HashMap

class ManuelFaturaKesActivity : AppCompatActivity() {
    val itemList_vrs : ArrayList<String> = ArrayList()
    val itemList_stok : ArrayList<String> = ArrayList()
    val itemList_unvan : ArrayList<String> = ArrayList()
    val itemList_vergino : ArrayList<String> = ArrayList()
    lateinit var auto_unvan: AutoCompleteTextView
    lateinit var auto_vergino: AutoCompleteTextView
    lateinit var imageviewFatura:ImageView
    lateinit var btn_unvan_sec:Button
    lateinit var btn_vergino_sec:Button
    lateinit var btn_iscilik_ekle:Button
    lateinit var mBack:ImageView
    lateinit var mForward:ImageView
    lateinit var firma:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var kadi:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var txttoplam_tutar: EditText
    lateinit var txtvergidairesi: EditText
    lateinit var txtadres: EditText
    lateinit var txtil: EditText
    lateinit var txtilce: EditText
    lateinit var txtemail: EditText
    private val CAMERA_REQUEST = 1
    private lateinit var bitmap:Bitmap
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manuel_fatura_kes)
        onBaslat()
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
        spinnerUnvanDoldur(firma_id)
        spinnerVergiNoDoldur(firma_id)
        btn_unvan_sec.setOnClickListener {
            bilgidoldur(auto_unvan.getText().toString(),kadi)
        }
        btn_vergino_sec.setOnClickListener {
            bilgidoldurVergino(auto_vergino.getText().toString(),kadi)

        }
        imageviewFatura.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST)
        }
        btn_iscilik_ekle.setOnClickListener { onApi() }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:  Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data!=null)
        {

            bitmap= data?.extras?.get("data") as Bitmap
            imageviewFatura.setImageBitmap(bitmap)

        }
    }
    private fun onApi() {
        val image=ImageToString()
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this,R.string.eklemebasarili,Toast.LENGTH_LONG).show()
                val i= Intent(this,HomeActivity::class.java)
                i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
                i.putExtra("yetki", intent.getStringExtra("yetki"))
                i.putExtra("firma_id", intent.getStringExtra("firma_id"))
                i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
                i.putExtra("kadi", intent.getStringExtra("kadi"))
                i.putExtra("sifre", intent.getStringExtra("sifre"))
                i.putExtra("gorev",intent.getStringExtra("gorev"))
                startActivity(i)
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["firma"] = firma_id
                params["unvan"] = auto_unvan.getText().toString()
                params["vergino"] = auto_vergino.getText().toString()
                params["adres"] = txtadres.getText().toString()
                params["mail"] = txtemail.getText().toString()
                params["il"] = txtil.getText().toString()
                params["ilce"] = txtilce.getText().toString()
                params["toplam_tutar"] = txttoplam_tutar.getText().toString()
                params["yol"] = image
                params["tur"] = "fatura_resim_kaydet"
                return params
            }
        }
        queue.add(postRequest)
    }
    fun ImageToString() : String {
        val byteArrayOutputsStream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
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


                }catch (e:Exception){
                    Toast.makeText(this,"bilgi doldur hatası", Toast.LENGTH_LONG).show()}

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


                }catch (e:Exception){
                    Toast.makeText(this,"bilgi doldur hatası:${e.message}", Toast.LENGTH_LONG).show()}

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


    private fun onBaslat() {
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        btn_iscilik_ekle=findViewById(R.id.btn_iscilik_ekle)
        imageviewFatura=findViewById(R.id.imageviewFatura)
        auto_unvan=findViewById(R.id.auto_unvan)
        auto_vergino=findViewById(R.id.auto_vergino)
        txtvergidairesi=findViewById(R.id.txtvergidairesi)
        txtadres=findViewById(R.id.txtadres)
        txtil=findViewById(R.id.txtil)
        txtilce=findViewById(R.id.txtilce)
        txtemail=findViewById(R.id.txtemail)
        btn_unvan_sec=findViewById(R.id.btn_unvan_sec)
        btn_vergino_sec=findViewById(R.id.btn_vergino_sec)
        txttoplam_tutar=findViewById(R.id.txttoplam_tutar)




    }
}