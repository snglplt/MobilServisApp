package com.selpar.pratikhasar.ui

import AracSahibiFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.fragment.*
import com.squareup.picasso.Picasso
import java.util.HashMap

class MekanikKartBilgiActivity : AppCompatActivity() {
    lateinit var Tvplaka: TextView
    lateinit var Tvmarka: TextView
    lateinit var Tvmodel: TextView
    lateinit var Tvmodelyili: TextView
    lateinit var Tvkasatipi: TextView
    lateinit var Tvdosya: TextView
    lateinit var TvUnvan: TextView
    lateinit var Tvrenk: TextView
    lateinit var Tvkm: TextView
    lateinit var currentFragment : Fragment
    lateinit var aracBilgi: Button
    lateinit var aracSahibi: Button
    lateinit var kabulasamasi: Button
    lateinit var tespitresimleri: Button
    lateinit var btn_istek_sikayet: Button
    lateinit var btn_guncelle_plaka: Button
    lateinit var onarim: Button
    val frags1=AracSahibiFragment()
    val frags2= SigortaFragment()
    lateinit var plakaGoster:String
    lateinit var plaka:String
    lateinit var btn_geri:ImageView
    lateinit var spinner_baslik:Spinner
    lateinit var kadi:String
    lateinit var firma_id:String
    lateinit var sifre:String
    lateinit var kullanici_id:String
    lateinit var kabulnom:String
    lateinit var unvan:String
    lateinit var vergino:String
    lateinit var ozel_id:String
    lateinit var btn_guncelle_baslik:Button
    lateinit var mBack:ImageView
    lateinit var mForward:ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mekanik_kart_bilgi)
         onBaslat()
        // Toast.makeText(this,"ŞİFRE:"+intent.getStringExtra("ozel_id"), Toast.LENGTH_SHORT).show()
        overridePendingTransition(R.anim.sag, R.anim.sol)

        ozel_id=intent.getStringExtra("ozel_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifre=intent.getStringExtra("sifre").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullanici_id=intent.getStringExtra("kullanici_id").toString()
        kabulnom=intent.getStringExtra("kabulnom").toString()
        unvan=intent.getStringExtra("unvan").toString()
        vergino=intent.getStringExtra("vergino").toString()
        plaka=intent.getStringExtra("plaka").toString()
                btn_geri=findViewById(R.id.btn_geri)
                btn_guncelle_plaka=findViewById(R.id.btn_guncelle_plaka)
                btn_guncelle_plaka.setOnClickListener {
                    val i= Intent(this,PlakaBilgiActivity::class.java)
                    i.putExtra("plaka", Tvplaka.getText().toString())
                    i.putExtra("yetki", intent.getStringExtra("yetki"))
                    i.putExtra("firma_id", intent.getStringExtra("firma_id"))
                    i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
                    i.putExtra("kadi", intent.getStringExtra("kadi"))
                    i.putExtra("sifre", intent.getStringExtra("sifre"))
                    i.putExtra("ozel_id", ozel_id)
                    startActivity(i)
                }
                btn_geri.setOnClickListener {
                    val i= Intent(this,HomeActivity::class.java)
                    i.putExtra("yetki", intent.getStringExtra("yetki"))
                    i.putExtra("firma_id", intent.getStringExtra("firma_id"))
                    i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
                    i.putExtra("kadi", intent.getStringExtra("kadi"))
                    i.putExtra("sifre", intent.getStringExtra("sifre"))
                    startActivity(i)

                }
                plaka=intent.getStringExtra("plaka").toString()
                val resim=findViewById<ImageView>(R.id.Tvaracresim)
                onarim.setBackgroundColor(0)
                tespitresimleri.setBackgroundColor(0)
                kabulasamasi.setBackgroundColor(0)
                var bundlem=Bundle()
                bundlem.putString("firma_id",intent.getStringExtra("firma_id"))
                bundlem.putString("kadi",intent.getStringExtra("kadi"))
                bundlem.putString("sifre",intent.getStringExtra("sifre"))
                bundlem.putString("kullanici_id",kullanici_id)
                bundlem.putString("ozel_id",ozel_id)
                bundlem.putString("kabulnom",kabulnom)
                bundlem.putString("plaka",plakaGoster)
                Log.d("buldu",plakaGoster)
//        frags1.arguments= bundlem.getBundle("plaka")
                Picasso.get().load(intent.getStringExtra("resim")).into(resim)
                //istekSikayet()
        OnarimAc()
        val spinner_list = ArrayList<String>()
        spinner_list.add(intent.getStringExtra("baslik").toString())

        val spinner_value =
            this.resources.getStringArray(R.array.kart_durum)
        for (i in spinner_value.indices) {
            spinner_list.add(spinner_value[i])
        }
        val spinner_arac_turu_police_adapter1: Any? = ArrayAdapter<Any?>(
            this,
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            spinner_list as List<Any?>
        )
        spinner_baslik.setAdapter(spinner_arac_turu_police_adapter1 as SpinnerAdapter?)
        btn_guncelle_baslik.setOnClickListener {
            guncelle_baslik(spinner_baslik.selectedItem.toString())
        }
        /*
        spinner_baslik?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                guncelle_baslik(selectedItem)

                ///bilgiMarka(aracSelect)
                // firmaIdGetir()
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
*/
        aracSahibi.setBackgroundColor(0)
                //val String tag = fragment.getClass().toString();
                aracBilgi.setOnClickListener{
                    aracBilgi.setBackgroundColor(0)
                    val fragmentmaneger=getSupportFragmentManager()
                    fragmentmaneger.beginTransaction()
                        .replace(R.id.fragment_mekanik,  AracBilgileriFragment.newInstance(intent.getStringExtra("kadi").toString(),"tag") )
                        .addToBackStack(intent.getStringExtra("kadi"))
                        .commit()

                }
               btn_istek_sikayet.setOnClickListener { istekSikayet() }
                aracSahibi.setOnClickListener{
                    aracSahibi.setBackgroundColor(Color.GRAY)
                    kabulasamasi.setBackgroundColor(0)
                    tespitresimleri.setBackgroundColor(0)
                    onarim.setBackgroundColor(0)
                    btn_istek_sikayet.setBackgroundColor(0)

                    aracBilgiGetir()
                }
                   kabulasamasi.setOnClickListener{
                    aracSahibi.setBackgroundColor(0)
                    tespitresimleri.setBackgroundColor(0)
                    onarim.setBackgroundColor(0)
                       btn_istek_sikayet.setBackgroundColor(0)

                       // aracSahibi.setTextColor(R.color.black)
                    kabulasamasi.setBackgroundColor(Color.GRAY)
                    var bundlem=Bundle()
                    bundlem.putString("plaka",plaka)
                    bundlem.putString("ozel_id",intent.getStringExtra("ozel_id"))
                    bundlem.putString("kadi",intent.getStringExtra("kadi"))
                    bundlem.putString("sifre",intent.getStringExtra("sifre"))
                    bundlem.putString("firma_id",intent.getStringExtra("firma_id"))
                    val fragobj = MekanikKabulAsamasiBilgiFragment()
                    fragobj.arguments=bundlem
                    //fragobj.setArguments(bundlem)
                    val fragmentmaneger=getSupportFragmentManager()
                    fragmentmaneger.beginTransaction()
                        .replace(R.id.fragment_mekanik,  fragobj)
                        .commit()
                }
               tespitresimleri.setOnClickListener{


                    tespitresimleri.setBackgroundColor(Color.GRAY)
                    onarim.setBackgroundColor(0)
                    kabulasamasi.setBackgroundColor(0)
                    aracSahibi.setBackgroundColor(0)
                   btn_istek_sikayet.setBackgroundColor(0)

                   //aracSahibi.setTextColor(R.color.black)
                    bundlem.putString("firma_id",intent.getStringExtra("firma_id"))
                    Log.d("firma",intent.getStringExtra("firma_id").toString())
                    bundlem.putString("kadi",intent.getStringExtra("kadi"))
                    bundlem.putString("sifre",intent.getStringExtra("sifre"))
                    bundlem.putString("kullanici_id",kullanici_id)
                    bundlem.putString("ozel_id",ozel_id)
                    bundlem.putString("kabulnom",kabulnom)
                   bundlem.putString("plaka",plaka)

                   // val fragobj = EvraklarFragment()
                    val fragobj = MekanikTespitResimleriFragment()
                    fragobj.arguments=bundlem
                    //fragobj.setArguments(bundlem)
                    val fragmentmaneger=getSupportFragmentManager()
                    fragmentmaneger.beginTransaction()
                        .replace(R.id.fragment_mekanik,  fragobj)
                        .commit()
                }
                onarim.setOnClickListener{
                  OnarimAc()
                }
        mBack.setOnClickListener {
            val i=Intent(this,AcikKartlarActivity::class.java)
            i.putExtra("plaka",plaka)
            i.putExtra("ozel_id",intent.getStringExtra("ozel_id"))
            i.putExtra("kadi",intent.getStringExtra("kadi"))
            i.putExtra("sifre",intent.getStringExtra("sifre"))
            i.putExtra("firma_id",intent.getStringExtra("firma_id"))
            startActivity(i)
        }

            }
    fun OnarimAc(){
        onarim.setBackgroundColor(Color.GRAY)
        tespitresimleri.setBackgroundColor(0)
        kabulasamasi.setBackgroundColor(0)
        aracSahibi.setBackgroundColor(0)
        btn_istek_sikayet.setBackgroundColor(0)
        // aracSahibi.setTextColor(R.color.black)


        var bundlem=Bundle()
        bundlem.putString("firma_id",intent.getStringExtra("firma_id"))
        bundlem.putString("kadi",intent.getStringExtra("kadi"))
        bundlem.putString("sifre",intent.getStringExtra("sifre"))
        bundlem.putString("kullanici_id",kullanici_id)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("plaka",plaka)
        bundlem.putString("unvan",unvan)
        bundlem.putString("marka",intent.getStringExtra("marka"))
        bundlem.putString("model",intent.getStringExtra("model"))
        bundlem.putString("modelyili",intent.getStringExtra("modelyili"))
        bundlem.putString("resim",intent.getStringExtra("resim"))
        bundlem.putString("servisturu",intent.getStringExtra("servisturu"))
        bundlem.putString("saseno",intent.getStringExtra("saseno"))

        val fragobj = OnarimFragment()
        fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_mekanik,  fragobj)
            .commit()
    }
    private fun guncelle_baslik(selectedItem: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this,"Başlık Guncelle Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"]=kadi
                params["firma_id"]=firma_id
                params["ozel_id"]=ozel_id
                params["baslik"] = selectedItem
                params["tur"] = "baslik_guncelle"

                return params
            }
        }
        queue.add(postRequest)

    }

    private fun istekSikayet() {
        onarim.setBackgroundColor(0)
        tespitresimleri.setBackgroundColor(0)
        kabulasamasi.setBackgroundColor(0)
        aracSahibi.setBackgroundColor(0)
        btn_istek_sikayet.setBackgroundColor(Color.GRAY)
        var bundlem=Bundle()
        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",intent.getStringExtra("ozel_id"))
        bundlem.putString("kadi",intent.getStringExtra("kadi"))
        bundlem.putString("sifre",intent.getStringExtra("sifre"))
        bundlem.putString("firma_id",intent.getStringExtra("firma_id"))
        val fragobj = IstekSikayetFragment()
        fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=getSupportFragmentManager()
        fragmentmaneger.beginTransaction()
            .replace(R.id.fragment_mekanik,  fragobj)
            .commit()
    }

    private fun aracBilgiGetir() {
                var bundlem=Bundle()
                bundlem.putString("plaka",plaka)
                bundlem.putString("ozel_id",intent.getStringExtra("ozel_id"))
                bundlem.putString("kadi",intent.getStringExtra("kadi"))
                bundlem.putString("sifre",intent.getStringExtra("sifre"))
                bundlem.putString("firma_id",intent.getStringExtra("firma_id"))
                val fragobj = MekanikAracSahibiFragment()
                fragobj.arguments=bundlem
                //fragobj.setArguments(bundlem)
                val fragmentmaneger=getSupportFragmentManager()
                fragmentmaneger.beginTransaction()
                    .replace(R.id.fragment_mekanik,  fragobj)
                    .commit()
            }

            @SuppressLint("SuspiciousIndentation")
            private fun onBaslat() {
                aracBilgi=findViewById<Button>(R.id.btnOnarim)
                aracSahibi=findViewById<Button>(R.id.btnAracSahibi)
                kabulasamasi=findViewById<Button>(R.id.btnKabulAsamasi)
                tespitresimleri=findViewById(R.id.btnEvrak)
                onarim=findViewById(R.id.btnOnarim)
                Tvplaka=findViewById(R.id.Tvplaka)
                btn_istek_sikayet=findViewById(R.id.btn_istek_sikayet)
                Tvplaka.setText(intent.getStringExtra("plaka"))
                plakaGoster=intent.getStringExtra("plaka").toString()
                Log.d("plaka",intent.getStringExtra("plaka").toString())
                Tvmarka=findViewById(R.id.Tvmarka)
                Tvmarka.setText(intent.getStringExtra("marka"))
                Tvmodel=findViewById(R.id.Tvmodel)
                Tvmodel.setText(intent.getStringExtra(("model")))
                Tvmodelyili=findViewById(R.id.Tvmodelyili)
                Tvmodelyili.setText(intent.getStringExtra( "modelyili"))
                Tvkasatipi=findViewById(R.id.Tvkasatipi)
                Tvkasatipi.setText(intent.getStringExtra("kasatipi"))
                Tvdosya=findViewById(R.id.Tvdosyano)
                if(intent.getStringExtra("dosyano")!="null")
                Tvdosya.setText(intent.getStringExtra("dosyano"))
                TvUnvan=findViewById(R.id.Tvunvan)
                TvUnvan.setText(intent.getStringExtra("unvan"))
                Tvrenk=findViewById(R.id.Tvrenk)
                Tvrenk.setText(intent.getStringExtra("renk"))
                Tvkm=findViewById(R.id.Tvbaslik)
                spinner_baslik=findViewById(R.id.spinner_baslik)
                btn_guncelle_baslik=findViewById(R.id.btn_guncelle_baslik)
                Tvkm.setText(intent.getStringExtra("km"))
                mBack = findViewById(R.id.back)
                mForward = findViewById(R.id.forward)
                mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
                mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
                /*
                    im.putExtra("unvan",currentItem.unvan)
                    im.putExtra("renk",currentItem.renk)
                    im.putExtra("km",currentItem.km)*/

            }


        }
