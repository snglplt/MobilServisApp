package com.selpar.pratikhasar.ui


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.selpar.pratikhasar.R
import okhttp3.OkHttpClient
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    lateinit var kullanici: EditText
    lateinit var sifre: EditText
    lateinit var btn_giris: Button
    lateinit var btn_language: ImageView
    lateinit var kayitOl: TextView
    var dil_secimi: String = ""
    lateinit var checkTurkce: CheckBox
    lateinit var checkEnglish: CheckBox
    val auth = FirebaseAuth.getInstance()
    var kadim = ""
    var sifrem = ""
    var yetkim = ""
    var kullnciid = ""
    var firma_id = ""
    var durum = ""
    var ozel_id = ""
    var servis_turu = ""
    var tarih = ""
    var gorev = ""
    var bitistarihi = ""
    var kullanici_resim = ""
    var mail = ""


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)

        kullanici = findViewById<EditText>(R.id.username)
        sifre = findViewById<EditText>(R.id.password)


        btn_giris = findViewById<Button>(R.id.loginButton)
        kayitOl = findViewById<TextView>(R.id.signupText)
        btn_language = findViewById(R.id.image_language)
        kullanici.setText(intent.getStringExtra("kullanici"))
        sifre.setText(intent.getStringExtra("sifre"))
        // Sayfayı yüklemeden önce default locale alıyoruz ve sayfayı ona göre yüklüyoruz.

        //Firebase.auth.signOut()

        val getUsername = sharedPreferens.getString("kadi", "")
        val getPassword = sharedPreferens.getString("sifre", "")
        if (getUsername != "" && getPassword != "") {
            val i = Intent(this, HomeActivity::class.java)
            i.putExtra("dilsecimi", sharedPreferens.getString("dilsecimi", ""))
            i.putExtra("yetki", sharedPreferens.getString("yetki", "").toString())
            i.putExtra("firma_id", sharedPreferens.getString("firma_id", "").toString())
            i.putExtra("kullanici_id", sharedPreferens.getString("kullanici_id", "").toString())
            i.putExtra("kadi", sharedPreferens.getString("kadi", "").toString())
            i.putExtra("sifre", sharedPreferens.getString("sifre", "").toString())
            i.putExtra("gorev", sharedPreferens.getString("gorev", "").toString())
            i.putExtra("bitistarihi", sharedPreferens.getString("bitistarihi", "").toString())
            i.putExtra("mail", sharedPreferens.getString("mail", "").toString())

            startActivity(i)
            finish()
        }
        overridePendingTransition(R.anim.sag, R.anim.sol)

        btn_giris.setOnClickListener {

            // Yeni kullanıcı oturum açabilir.
            giris_yap(kullanici.getText().toString(), sifre.getText().toString())


        }
        btn_language.setOnClickListener {
            ShowLanguageDialog()
        }

    }


    private fun ShowLanguageDialog() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle("Dil Seçiniz")
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.dilsecimi, null)
        checkTurkce = view.findViewById<CheckBox>(R.id.checkTurkce)
        checkEnglish = view.findViewById<CheckBox>(R.id.checkEnglish)
        val locale =
            Locale.getDefault()
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
        if (locale.language == "en") {
            checkEnglish.isChecked = true
            checkTurkce.isChecked = false
            // Toast.makeText(this,"Türkçe değil",Toast.LENGTH_LONG).show()
        } else {
            checkTurkce.isChecked = true
            checkEnglish.isChecked = false
        }
        checkTurkce.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (checkTurkce!!.isChecked) {
                    checkEnglish.isChecked = false

                }


            }
        })
        checkEnglish.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (checkEnglish!!.isChecked) {
                    checkTurkce.isChecked = false

                }


            }
        })

        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EKLE"
        ) { dialogInterface, which ->
            if (checkTurkce.isChecked) {
                SetLocale("tr")
                recreate()
                dil_secimi = "tr"
            } else if (checkEnglish.isChecked) {
                SetLocale("en")
                recreate()
                dil_secimi = "en"
            }


        }
        alertadd.show()
/*
        val ListItems = arrayOf("English", "Türkçe")
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Dil Seçiniz")
        builder.setItems(ListItems) { dialog, which ->

            when (which) {
                0 -> {
                    SetLocale("en")
                    recreate()
                    dil_secimi="en"
                }
                1 -> {
                    SetLocale("tr")
                    recreate()
                    dil_secimi="tr"
                }

            }
            dialog.dismiss()

        }

        val mDialog: AlertDialog =builder.create()
        mDialog.show()*/

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

    private fun LoadLanguage() {
        val pref: SharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language: String = pref.getString("My_Lang", "")!!
        SetLocale(language)
    }

    fun KayitOl(view: View) {
        var i = Intent(this, KayitOlActivity::class.java)
        i.putExtra("dilsecimi", dil_secimi)
        startActivity(i)
        finish()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun giris_yapin(view: View) {
        // Do something in response to button
        if (kullanici.getText().toString().isNotEmpty() && sifre.getText().toString()
                .isNotEmpty()
        ) {
            giris_yap(kullanici.getText().toString(), sifre.getText().toString())

        } else {
            Toast.makeText(this, "Lütfen gerekli alanları doldurunuz!..", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun giris_yap(kadi: String, sifre: String) {
        /*FirebaseAuth.getInstance().createUserWithEmailAndPassword(kadi, sifre)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Kullanıcı başarıyla kaydedildi
                    Toast.makeText(this,"Kullanıcı başarıyla kaydedildi",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"Hata Oluştu",Toast.LENGTH_SHORT).show()
                }
            }
*/
        val urlsb = "&kadi=" + kadi + "&sifre=" + sifre
        var url = "https://pratikhasar.com/netting/mobil.php?tur=giris_yap" + urlsb
        Log.d("KABULBULLL: ", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["giris"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        kadim = item.getString("kadi").toString()
                        sifrem = item.getString("sifre").toString()
                        yetkim = item.getString("yetki").toString()
                        //kullnciid = item.getString(ku)
                        firma_id = item.getString("firmaadi").toString()
                        durum = item.getString("durum").toString()
                        kullanici_resim = item.getString("kullanici_resim").toString()
                        bitistarihi = item.getString("bitistarihi").toString()
                        servis_turu = item.getString("servis_turu").toString()
                        gorev = item.getString("gorev").toString()
                        mail = item.getString("mail").toString()


                    }
                } catch (e: Exception) {
                }
                if (durum == "1") {
                    val kalangun=onHesapla(bitistarihi)

                    //task.isSuccessful if
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(kadi, sifre)
                        .addOnCompleteListener { task ->
                            if (kalangun >0) {
                                // Kullanıcı başarıyla oturum açtı
                                val sharedPreferens =
                                    getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)

                                val editor = sharedPreferens.edit()
                                editor.putString("kadi", kadi)
                                editor.putString("sifre", sifre)
                                editor.putString("dilsecimi", sifre)
                                editor.putString("firma_id", firma_id)
                                editor.putString("kullanici_id", kullnciid)
                                editor.putString("gorev", gorev)
                                editor.putString("yetki", yetkim)
                                editor.putString("servis_turu", servis_turu)
                                editor.putString("bitistarihi", bitistarihi)
                                editor.putString("mail", mail)

                                editor.apply()
                                if (servis_turu.equals("Mekanik Servis")) {
                                    val i = Intent(this, HomeActivity::class.java)
                                    i.putExtra("dilsecimi", dil_secimi)
                                    i.putExtra("yetki", yetkim)
                                    i.putExtra("firma_id", firma_id)
                                    i.putExtra("kullanici_id", kullnciid)
                                    i.putExtra("kadi", kadim)
                                    i.putExtra("sifre", sifrem)
                                    i.putExtra("gorev", gorev)
                                    i.putExtra("servis_turu", servis_turu)
                                    i.putExtra("kullanici_resim", kullanici_resim)
                                    i.putExtra("bitistarihi", bitistarihi)
                                    i.putExtra("mail", mail)

                                    startActivity(i)

                                    finish()

                                } else {
                                    val i = Intent(this, HomeActivity::class.java)
                                    i.putExtra("dilsecimi", dil_secimi)
                                    i.putExtra("yetki", yetkim)
                                    i.putExtra("firma_id", firma_id)
                                    i.putExtra("kullanici_id", kullnciid)
                                    i.putExtra("kadi", kadim)
                                    i.putExtra("sifre", sifrem)
                                    i.putExtra("gorev", gorev)
                                    i.putExtra("kullanici_resim", kullanici_resim)
                                    i.putExtra("bitistarihi", bitistarihi)
                                    i.putExtra("mail", mail)

                                    startActivity(i)
                                    finish()

                                }

                            } else {
                                val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
                                builder.setTitle(getString(R.string.uyari))
                                builder.setMessage(
                                    "Uygulamayı kullanım süresi bitmistir\n" +
                                            "Süreyi uzatmak için 0 850 304 26 06 numarayı arayınız\n" +
                                            "www.pratikhasar.com"
                                )
                                builder.setPositiveButton(getString(R.string.tamam)) { dialog, which ->
                                    onCikisYap()
                                    val i = Intent(this, WebviewActivity::class.java)
                                    i.putExtra("kadi", intent.getStringExtra("kadi"))
                                    i.putExtra("dilsecimi", dil_secimi)
                                    i.putExtra("yetki", yetkim)
                                    i.putExtra("firma_id", firma_id)
                                    i.putExtra("kullanici_id", kullnciid)
                                    i.putExtra("sifre", sifrem)
                                    i.putExtra("gorev", gorev)
                                    i.putExtra("path","https://pos.param.com.tr/Tahsilat/Default.aspx?k=1582893d-9f14-48b2-aa30-0ff5a9073ac8")
                                    val sheredpreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
                                    sheredpreferens.edit().remove("kadi").commit()
                                    sheredpreferens.edit().remove("sifre").commit()
                                    val editor = sheredpreferens.edit()
                                    editor.commit()
                                    editor.clear()
                                    editor.remove("kadi")
                                    editor.remove("sifre")
                                    startActivity(i)
                                    finish()
                                }

                                val dialog = builder.create()
                                dialog.show()

                                // Hata oluştu
                            }
                        }
                    //ProgressDialog.show(this,"",
                    //   "Lütfen Bekleyiniz", true);

                    // overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

                } else {
                    Log.d("API", "başarısız")
                    Toast.makeText(this, R.string.kullanici_oturum_acmis, Toast.LENGTH_SHORT).show()


                }


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                Toast.makeText(
                    this,
                    "Sonuc: " + { error.message + error.networkResponse },
                    Toast.LENGTH_SHORT
                ).show()

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
    private fun onCikisYap() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response -> // response
                Log.d("Response", response!!)

            },
            Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadim
                params["sifre"] = sifrem
                params["firma_id"] = firma_id
                params["tur"] = "cikis_log_ekle"
                return params
            }
        }
        queue.add(postRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onHesapla(bitistarihi: String):Long {
      //  Toast.makeText(this, "Date: " + bitistarihi, Toast.LENGTH_LONG).show()
        val formatter2 = SimpleDateFormat("yyyy-MM-dd")
        val bitis = formatter2.parse(bitistarihi)
        val formatter = DateTimeFormatter.ISO_DATE
        val date = LocalDate.parse(bitistarihi, formatter)
        val bugun = LocalDate.now()

        val daysBetween = ChronoUnit.DAYS.between(bugun, date)
        return daysBetween
        // Toast.makeText(this,"FARK: "+daysBetween,Toast.LENGTH_LONG).show()

    }

}











































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































