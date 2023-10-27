package com.selpar.pratikhasar.ui

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.CariAdapter
import com.selpar.pratikhasar.data.CariModel
import org.json.JSONArray

class eFaturaOlusturActivity : AppCompatActivity() {
    lateinit var txt_stokno:TextView
    lateinit var txt_aciklama:TextView
    lateinit var txt_miktar:TextView
    lateinit var txt_fiyat:TextView
    lateinit var txt_tutar:TextView
    lateinit var txt_toplam:TextView
    lateinit var txt_tutar_toplam:TextView
    lateinit var txt_cari_unvan:TextView
    lateinit var txt_vergi_no:TextView
    lateinit var txt_vergi_dairesi:TextView
    lateinit var txt_yetkiliadi:TextView
    lateinit var txt_il:TextView
    lateinit var txt_ilce:TextView
    lateinit var txt_tel:TextView
    lateinit var txt_gsm:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efatura_olustur)
        onBaslat()
        onCariGetir()
    }

    private fun onCariGetir() {
        val urlek="&cari_unvan="+txt_aciklama.getText().toString()
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bilgi_getir"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("cari",url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try {

                val json = response["cariler"] as JSONArray
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    txt_cari_unvan.setText(item.getString("cari_unvan"))
                    txt_vergi_no.setText(item.getString("vergino"))
                    txt_vergi_dairesi.setText(item.getString("vergidairesi"))
                    txt_yetkiliadi.setText(item.getString("yetkili_isim_soyisim"))
                    txt_il.setText(item.getString("il"))
                    txt_ilce.setText(item.getString("ilce"))
                    txt_tel.setText(item.getString("tel"))
                    txt_gsm.setText(item.getString("gsm"))



                }


            }catch (e:Exception){}


            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    private fun onBaslat() {
        txt_stokno=findViewById(R.id.txt_stokno)
        txt_aciklama=findViewById(R.id.txt_aciklama)
        txt_miktar=findViewById(R.id.txt_miktar)
        txt_fiyat=findViewById(R.id.txt_fiyat)
        txt_tutar=findViewById(R.id.txt_tutar)
        txt_toplam=findViewById(R.id.txt_toplam)
        txt_tutar_toplam=findViewById(R.id.txt_tutar_toplam)
        txt_cari_unvan=findViewById(R.id.txt_cari_unvan)
        txt_vergi_no=findViewById(R.id.txt_vergi_no)
        txt_vergi_dairesi=findViewById(R.id.txt_vergi_dairesi)
        txt_yetkiliadi=findViewById(R.id.txt_yetkiliadi)
        txt_il=findViewById(R.id.txt_il)
        txt_ilce=findViewById(R.id.txt_ilce)
        txt_tel=findViewById(R.id.txt_tel)
        txt_gsm=findViewById(R.id.txt_gsm)
        if(intent.getStringExtra("plaka")!=" "){
        txt_stokno.setText(intent.getStringExtra("plaka"))}
        txt_aciklama.setText(intent.getStringExtra("isim"))
        txt_miktar.setText("1")
        val bakiye=intent.getStringExtra("bakiye").toString().toFloat()
        txt_fiyat.setText(Math.abs(bakiye).toString())
        txt_tutar.setText(Math.abs(bakiye).toString())
        txt_toplam.setText(Math.abs(bakiye).toString())
        txt_tutar_toplam.setText(Math.abs(bakiye).toString())
    }
}