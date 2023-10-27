package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray

class EFaturaSistemAyarlariActivity : AppCompatActivity() {
    val spinner_firma = ArrayList<String>()
    val spinner_fatura_turu_ = ArrayList<String>()
    lateinit var spinner_efirma:Spinner
    lateinit var spinner_fatura_turu:Spinner
    lateinit var edusername:EditText
    lateinit var entegratorsifre:EditText
    lateinit var efaturaseribaslangic:EditText
    lateinit var earsivseribaslangic:EditText
    lateinit var eirsaliyeseribaslangic:EditText
    lateinit var efaturaetiketmaili:EditText
    lateinit var btn_kaydet:Button
    lateinit var btn_guncelle:Button
    lateinit var kadi:String
    lateinit var firma_id:String
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efatura_sistem_ayarlari)
        spinner_efirma=findViewById(R.id.spinner_efirma)
        spinner_fatura_turu=findViewById(R.id.spinner_fatura_turu)
        edusername=findViewById(R.id.edusername)
        entegratorsifre=findViewById(R.id.entegratorsifre)
        efaturaseribaslangic=findViewById(R.id.efaturaseribaslangic)
        earsivseribaslangic=findViewById(R.id.earsivseribaslangic)
        eirsaliyeseribaslangic=findViewById(R.id.eirsaliyeseribaslangic)
        efaturaetiketmaili=findViewById(R.id.efaturaetiketmaili)
        btn_kaydet=findViewById(R.id.btn_kaydet)
        btn_guncelle=findViewById(R.id.btn_guncelle)
        kadi=intent.getStringExtra("kadi").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        spinner_firma.clear()
        spinner_fatura_turu_.clear()
        onBilgiGetir()
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




        btn_kaydet.setOnClickListener {
            if(edusername.getText().toString().isNotEmpty() &&
                entegratorsifre.getText().toString().isNotEmpty() &&
                efaturaseribaslangic.getText().toString().isNotEmpty() &&
                earsivseribaslangic.getText().toString().isNotEmpty() &&
                eirsaliyeseribaslangic.getText().toString().isNotEmpty() &&
                efaturaetiketmaili.getText().toString().isNotEmpty())
            {
                kaydet()
            }
            else{
                Toast.makeText(this,"Lütfen gerekli alanları doldurun!..",Toast.LENGTH_LONG).show()
            }
        }
        btn_guncelle.setOnClickListener {
            if(edusername.getText().toString().isNotEmpty() &&
                entegratorsifre.getText().toString().isNotEmpty() &&
                efaturaseribaslangic.getText().toString().isNotEmpty() &&
                earsivseribaslangic.getText().toString().isNotEmpty() &&
                eirsaliyeseribaslangic.getText().toString().isNotEmpty() &&
                efaturaetiketmaili.getText().toString().isNotEmpty())
            {
                guncelle()
            }
            else{
                Toast.makeText(this,"Lütfen gerekli alanları doldurun!..",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun guncelle() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this, "Güncelleme Başarılı: " + kadi, Toast.LENGTH_SHORT).show()

                sifirla()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = firma_id
                params["kulaniciadi"] = edusername.getText().toString()
                params["sifre"] = entegratorsifre.getText().toString()
                params["efaturaseribaslangic"] = efaturaseribaslangic.getText().toString()
                params["earsivseribaslangic"] = earsivseribaslangic.getText().toString()
                params["eirsaliyeseribaslangic"] = eirsaliyeseribaslangic.getText().toString()
                params["efaturaetiketmaili"] = efaturaetiketmaili.getText().toString()
                params["spinner_firma"]=spinner_fatura_turu.selectedItem.toString()
                params["spinner_efirma"]=spinner_efirma.selectedItem.toString()
                params["tur"] = "fatura_ayari_guncelle"
                return params
            }
        }
        queue.add(postRequest)

    }

    private fun onBilgiGetir()
    {
        var url = "https://pratikhasar.com/netting/mobil.php?tur=fatura_bilgi_getir&firma_id="+intent.getStringExtra("firma_id").toString()+"&kadi="+intent.getStringExtra("kadi").toString()
        Log.d("ACTiVE",url)
        var deger:Boolean=false
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{

                val json = response["fatura"] as JSONArray
                var servis_turu:String
                spinner_fatura_turu_.clear()
                spinner_firma.clear()
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    spinner_firma.add(item.getString("entegrator_firmasi").toString())
                    edusername.setText(item.getString("entegrator_kullanici_kodu"))
                    if(item.getString("entegrator_kullanici_kodu")!="null"){
                        btn_guncelle.visibility= VISIBLE
                        btn_kaydet.visibility=GONE
                    }
                    else{
                        btn_guncelle.visibility= GONE
                        btn_kaydet.visibility= VISIBLE
                    }
                    entegratorsifre.setText(item.getString("entegrator_kullanici_sifresi"))
                    efaturaseribaslangic.setText(item.getString("e_fatura_seri_baslangic"))
                    earsivseribaslangic.setText(item.getString("e_arsiv_seri_baslangic"))
                    eirsaliyeseribaslangic.setText(item.getString("e_irsaliye_seri_baslangic"))
                    efaturaetiketmaili.setText(item.getString("e_fatura_etiket_maili"))
                    spinner_fatura_turu_.add(item.getString("e_fatura_turu"))



                }
                val spinner_konum_kart_ac_value1 = this.resources.getStringArray(R.array.entagrator_firmasi)
                for (i in spinner_konum_kart_ac_value1.indices) {
                    spinner_firma.add(spinner_konum_kart_ac_value1[i])
                }
                val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                    this,
                    R.layout.spinner_item_text,
                    spinner_firma as List<Any?>
                )
                spinner_efirma.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)
                val spinner_konum_kart_ac_value2 = this.resources.getStringArray(R.array.efatura_turu)
                for (i in spinner_konum_kart_ac_value2.indices) {
                    spinner_fatura_turu_.add(spinner_konum_kart_ac_value2[i])
                }
                val spinner_konum_kart_ac_adapter2: Any? = ArrayAdapter<Any?>(
                    this,
                    R.layout.spinner_item_text,
                    spinner_fatura_turu_ as List<Any?>
                )
                spinner_fatura_turu.setAdapter(spinner_konum_kart_ac_adapter2 as SpinnerAdapter?)


            }catch (e:Exception){
                val spinner_konum_kart_ac_value1 = this.resources.getStringArray(R.array.entagrator_firmasi)
                for (i in spinner_konum_kart_ac_value1.indices) {
                    spinner_firma.add(spinner_konum_kart_ac_value1[i])
                }
                val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                    this,
                    R.layout.spinner_item_text,
                    spinner_firma as List<Any?>
                )
                spinner_efirma.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)
                val spinner_konum_kart_ac_value2 = this.resources.getStringArray(R.array.efatura_turu)
                for (i in spinner_konum_kart_ac_value2.indices) {
                    spinner_fatura_turu_.add(spinner_konum_kart_ac_value2[i])
                }
                val spinner_konum_kart_ac_adapter2: Any? = ArrayAdapter<Any?>(
                    this,
                    R.layout.spinner_item_text,
                    spinner_fatura_turu_ as List<Any?>
                )
                spinner_fatura_turu.setAdapter(spinner_konum_kart_ac_adapter2 as SpinnerAdapter?)
            }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }

    private fun kaydet() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(this, "Ekleme Başarılı: " + kadi, Toast.LENGTH_SHORT).show()

                sifirla()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = firma_id
                params["kulaniciadi"] = edusername.getText().toString()
                params["sifre"] = entegratorsifre.getText().toString()
                params["efaturaseribaslangic"] = efaturaseribaslangic.getText().toString()
                params["earsivseribaslangic"] = earsivseribaslangic.getText().toString()
                params["eirsaliyeseribaslangic"] = eirsaliyeseribaslangic.getText().toString()
                params["efaturaetiketmaili"] = efaturaetiketmaili.getText().toString()
                params["spinner_firma"]=spinner_fatura_turu.selectedItem.toString()
                params["spinner_efirma"]=spinner_efirma.selectedItem.toString()
                params["tur"] = "fatura_ayari_ekle"
                return params
            }
        }
        queue.add(postRequest)
        }

    private fun sifirla() {

    }
}
