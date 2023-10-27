package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.HashMap


class KayitOlActivity : AppCompatActivity() {
    lateinit var userName: EditText
    lateinit var sifre: EditText
    lateinit var firmaUnvan: EditText
    lateinit var firmaTel: EditText
    lateinit var yetkili_isim: EditText
    lateinit var yetkili_tel: EditText
    lateinit var mail: EditText
    lateinit var kayitYap: Button
    lateinit var hesap: TextView
    lateinit var image1: ImageView
    var kullaniciAdi = ""
    var pass = ""
    var firmaunvan = ""
    var firmaTelefonu = ""
    var yetkiliTel = ""
    var islem_turu = ""
    private val CAMERA_REQUEST = 1
    private var bitmap: Bitmap? = null
    var spinnerSelect: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit_ol)
        val dilsecimi = intent.getStringExtra("dilsecimi").toString()
        if (dilsecimi == "en") {
            SetLocale("en")
            recreate()
        }

        val spinner = findViewById<Spinner>(R.id.spinner_languages)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.is_tani,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.setAdapter(adapter)

        userName = findViewById<EditText>(R.id.username)
        sifre = findViewById<EditText>(R.id.password)
        firmaUnvan = findViewById<EditText>(R.id.firma_unvan)
        firmaTel = findViewById<EditText>(R.id.firma_tel)
        yetkili_isim = findViewById<EditText>(R.id.yetkili_isim)
        yetkili_tel = findViewById<EditText>(R.id.yetkili_tel)
        mail = findViewById<EditText>(R.id.mail)
        kayitYap = findViewById<Button>(R.id.btnRegister)
        hesap = findViewById<TextView>(R.id.Already)
        image1 = findViewById(R.id.image_logo)
        image1.setImageDrawable(getResources().getDrawable(R.drawable.arac))
        kullaniciAdi = userName.getText().toString()
        pass = sifre.getText().toString()
        firmaunvan = firmaUnvan.getText().toString()
        firmaTelefonu = firmaTel.getText().toString()
        yetkiliTel = yetkili_tel.getText().toString()
        islem_turu = spinnerSelect
        image1.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent, CAMERA_REQUEST)

        }
        /*  sifre.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                if(isEmailValid(kullaniciAdi)!=false) {
                  //  Toast.makeText(applicationContext, "Got the focus", Toast.LENGTH_LONG).show()
                }else{
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
                }
            } else {
                Toast.makeText(applicationContext, "Lost the focus", Toast.LENGTH_LONG).show()
            }
        })*/
        hesap.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                spinnerSelect = parent.getItemAtPosition(position).toString()

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinnerSelect = ""
            }
        }
        kayitYap.setOnClickListener {
            if (userName.getText().toString().isNotEmpty() &&
                sifre.getText().toString().isNotEmpty() &&
                firmaUnvan.getText().toString().isNotEmpty() &&
                firmaTel.getText().toString().isNotEmpty() &&
                mail.getText().toString().isNotEmpty() &&
                spinnerSelect != R.string.seciniz.toString()
            ) {
                // onApi()
                giris_yap(userName.getText().toString())

            } else {
                //onApi()
                Toast.makeText(this, R.string.kayit_basarisiz, Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {

            bitmap = data?.extras?.get("data") as Bitmap
            image1.setImageBitmap(bitmap)

        }
    }

    fun ImageToString(): String {
        val byteArrayOutputsStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputsStream)
        var imageByte = byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun mailAdrese() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mail_kullanici.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response

             //   Toast.makeText(this, "Mail Başarılı: ", Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI2")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["gonderen"] = "destek@selpar.com.tr"
                params["sifre"] = "Destek06**"
                params["smtp_port"] = "587"
                params["ssl_port"] = "evet"
                params["mail_server"] = "smtp.yandex.com"
                params["alici"] = "snglplt.36@gmail.com"
                params["isim"] = firmaUnvan.getText().toString()
                params["firmaadi"] = firmaUnvan.getText().toString()
                params["telefon1"] = firmaTel.getText().toString()
                params["telefon2"] = yetkili_tel.getText().toString()
                params["kullanici"] = userName.getText().toString()
                params["mail"] = mail.getText().toString()
                params["servis_turu"] = spinnerSelect

                return params
            }
        }
        queue.add(postRequest)

    }

    private fun onApi() {
        if (isEmailValid(userName.getText().toString())) {
            // if (bitmap != null) {
            //   val image = ImageToString()


            val queue = Volley.newRequestQueue(this)
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    Toast.makeText(this, R.string.kayit_basarili, Toast.LENGTH_SHORT).show()
                    sifirla()
                    mailAdrese()

                },
                Response.ErrorListener {
                    Log.d("Response", "HATALI")
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["kadi"] = userName.getText().toString()
                    params["sifre"] = sifre.getText().toString()
                    params["isim"] = firmaUnvan.getText().toString()
                    params["firmaadi"] = firmaUnvan.getText().toString()
                    params["telefon1"] = firmaTel.getText().toString()
                    params["telefon2"] = yetkili_tel.getText().toString()
                    params["mail"] = mail.getText().toString()
                    params["logo"] = "image"
                    params["tur"] = "firma_ekle"
                    params["servis_turu"] = spinnerSelect
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
            /* } else {
                 Toast.makeText(this, R.string.logo_secin, Toast.LENGTH_SHORT).show()
                 val builder = AlertDialog.Builder(this)
                 builder.setTitle(R.string.uyari)
                 builder.setMessage(R.string.logo_secin)

     //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                 builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                     Toast.makeText(
                         applicationContext,
                         android.R.string.yes, Toast.LENGTH_SHORT
                     ).show()
                 }

                 builder.setNegativeButton(android.R.string.no) { dialog, which ->
                     Toast.makeText(
                         applicationContext,
                         android.R.string.no, Toast.LENGTH_SHORT
                     ).show()
                 }


                 builder.show()
             }*/
        } else {
            Toast.makeText(this, R.string.mail_format, Toast.LENGTH_SHORT).show()

        }
    }

    private fun sifirla() {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("kullanici", userName.getText().toString())
        i.putExtra("sifre", sifre.getText().toString())
        //Toast.makeText(this,R.string.kayitdinizyapidi,Toast.LENGTH_LONG).show()
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.kayitdinizyapidi)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            startActivity(i)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

            startActivity(i)
        }


        builder.show()


    }

    private fun giris_yap(kadi: String) {
        val istek = Volley.newRequestQueue(this)
        val url = "https://pratikhasar.com/netting/mobil.php"
        val requestBody = "tur=giriskotrol" + "&kadi=" + kadi
        var data = null
        val stringReq: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    // response
                    var strResps = response.toString()
                    var strResp = strResps.substring(1, strResps.length - 1)

                    val arr = strResp.split("%%Tt%%%")
                    var kadim = ""
                    var sifrem = ""
                    var yetkim = ""
                    var kullnciid = ""
                    var firma_id = ""
                    var durum = ""
                    var ozel_id = ""
                    try {
                        durum = arr[5]

                    } catch (e: Exception) {
                        //Toast.makeText(this,"Kullanıcı bulunamadı!!!",Toast.LENGTH_SHORT).show()
                    }
                    if (durum == "1") {
                        Toast.makeText(this, R.string.kullanici_var, Toast.LENGTH_SHORT).show()
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle(R.string.uyari)
                        builder.setMessage(R.string.kullanici_var)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                            Toast.makeText(
                                applicationContext,
                                android.R.string.yes, Toast.LENGTH_SHORT
                            ).show()
                        }

                        builder.setNegativeButton(android.R.string.no) { dialog, which ->
                            Toast.makeText(
                                applicationContext,
                                android.R.string.no, Toast.LENGTH_SHORT
                            ).show()
                        }


                        builder.show()
                        //ProgressDialog.show(this,"",
                        //   "Lütfen Bekleyiniz", true);

                        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

                    } else {
                        onApi()
                        Log.d("API", "başarısız")

                    }

                },

                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        istek.add(stringReq)
    }

    private fun SetLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config: Configuration = Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor: SharedPreferences.Editor =
            getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }
}