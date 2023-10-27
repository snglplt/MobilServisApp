package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.*
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern

class KullaniciTanimlamaActivity : AppCompatActivity() {
    lateinit var btn_satin_al:Button
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var kadi:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var txttc:EditText
    lateinit var txtkullaniciadi:EditText
    lateinit var txtkullanicisoyad:EditText
    lateinit var txtkullanici:EditText
    lateinit var txtparola:EditText
    lateinit var txtil:EditText
    lateinit var txtilce:EditText
    lateinit var txtadres:EditText
    lateinit var txttel:EditText
    lateinit var image: ImageView
    var CAMERA_REQUEST=1
    var GALERY_REQUEST=2
    lateinit var spinner_brans:Spinner
    private  var bitmap: Bitmap? =null
    var itemList_brans:ArrayList<String> = ArrayList()


    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_tanimlama)
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        btn_satin_al=findViewById(R.id.btn_satin_al)
        txttc=findViewById(R.id.txttc)
        txtkullaniciadi=findViewById(R.id.txtkullaniciadi)
        txtkullanicisoyad=findViewById(R.id.txtkullanicisoyad)
        txtkullanici=findViewById(R.id.txtkullanici)
        txtparola=findViewById(R.id.txtparola)
        txtil=findViewById(R.id.txtil)
        txtilce=findViewById(R.id.txtilce)
        txtadres=findViewById(R.id.txtadres)
        txttel=findViewById(R.id.txttel)
        image = findViewById(R.id.image)
        spinner_brans = findViewById(R.id.spinner_brans)
        image.setImageDrawable(getResources().getDrawable(R.drawable.camera))
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
        }
        image.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.uyari)
            builder.setMessage("Nereden resim yükleyeceksiniz?")

//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(R.string.kamera) { dialog, which ->
                cekResim()

            }

            builder.setNegativeButton(R.string.galeri) { dialog, which ->
                galeridensec()

            }


            builder.show()


        }

        btn_satin_al.setOnClickListener {
            onApi()

        }
    }
    private fun galeridensec() {
        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*" //allow any image file type.

        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(gallery, GALERY_REQUEST)
    }

    private fun cekResim() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // cameraIntent.setType("image/*")
        startActivityForResult(cameraIntent,CAMERA_REQUEST)
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
                    makeText(this, "MODEL doldur hatası", Toast.LENGTH_LONG)
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

    fun ImageToString() : String {
        val byteArrayOutputsStream= ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:  Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image.setImageBitmap(bitmap)
            onApi()
           // image.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        }
        if (requestCode == GALERY_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            val inputStream = this.contentResolver.openInputStream(imageUri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            image.setImageBitmap(bitmap)
            onApi()
           // image.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        }
    }

    private fun onApi() {
        var gorev=""
        if(txtkullanici.getText().toString()==intent.getStringExtra("kadi"))
        {
            gorev="admin"
        }else{
            gorev="kullanici"
        }
        if( txtkullanici.getText().toString().isNotEmpty()
            && txtparola.getText().toString().isNotEmpty()
            && txttel.getText().toString().isNotEmpty()){
        if (isEmailValid(txtkullanici.getText().toString()) ) {
            if (bitmap != null) {
                val image = ImageToString()
                val queue = Volley.newRequestQueue(this)
                var url = "https://pratikhasar.com/netting/mobil.php"
                val postRequest: StringRequest = object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response -> // response
                        Log.d("Response", response!!)
                        if(txtkullanici.getText().toString()!=intent.getStringExtra("kadi"))
                        {
                            val i= Intent(this,SatinAlMainActivity::class.java)

                            i.putExtra("dilsecimi",dilsecimi)
                            i.putExtra("yetki", yetki)
                            i.putExtra("firma_id", firma_id)
                            i.putExtra("kullanici_id", kullnciid)
                            i.putExtra("kadi", kadi)
                            i.putExtra("sifre", sifrem)
                            i.putExtra("kadi",kadi)
                            i.putExtra("toplam","500")

                            startActivity(i)
                        }
                        makeText(this, R.string.kayit_basarili, Toast.LENGTH_SHORT).show()
                       // mailAdrese()

                    },
                    Response.ErrorListener {
                        Log.d("Response", "HATALI")
                    }
                ) {
                    override fun getParams(): Map<String, String>? {
                        val params: MutableMap<String, String> = HashMap()
                        params["kadi"] = txtkullanici.getText().toString()
                        params["sifre"] = txtparola.getText().toString()
                        params["isim"] = txtkullaniciadi.getText().toString()
                        params["firmaadi"] = intent.getStringExtra("firma_id").toString()
                        params["tc"] = txttc.getText().toString()
                        params["il"] = txtil.getText().toString()
                        params["ilce"] = txtilce.getText().toString()
                        params["adres"] = txtadres.getText().toString()
                        params["telefon"] = txttel.getText().toString()
                        params["kullanici_ad"] = txtkullaniciadi.getText().toString()
                        params["kullanici_soyad"] = txtkullanicisoyad.getText().toString()
                        params["kullanici_resim"] = image
                        params["gorev"] = gorev
                        params["brans"] = spinner_brans.selectedItem.toString()
                        params["tur"] = "firma_kullanici_ekle"
                        return params
                    }
                }
                queue.add(postRequest)

                /*  else{
            Toast.makeText(this,"Lütfen kulanıcı adını email formatında giriniz!!!",Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("UYARI")
            builder.setMessage("Lütfen kulanıcı adını email formatında giriniz!!!")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }


            builder.show()
        }*/
            } else {
                makeText(this, R.string.logo_secin, Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.uyari)
                builder.setMessage(R.string.logo_secin)

//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    makeText(
                        applicationContext,
                        android.R.string.yes, Toast.LENGTH_SHORT
                    ).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    makeText(
                        applicationContext,
                        android.R.string.no, Toast.LENGTH_SHORT
                    ).show()
                }


                builder.show()
            }
        }

        else{
            makeText(this, R.string.mail_format, Toast.LENGTH_SHORT).show()

        }}
    else{
        makeText(this,R.string.tumalanlar,Toast.LENGTH_LONG).show()
    }


}}