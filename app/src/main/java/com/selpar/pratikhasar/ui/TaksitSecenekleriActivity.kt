package com.selpar.pratikhasar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.TaksitAdapter
import com.selpar.pratikhasar.data.TaksitModel
import org.json.JSONArray

class TaksitSecenekleriActivity : AppCompatActivity() {
    val kartbanka : ArrayList<String> = ArrayList()
    val kartgorsel : ArrayList<String> = ArrayList()
    val taksit01 : ArrayList<Float> = ArrayList()
    val taksit02 : ArrayList<Float> = ArrayList()
    val taksit03 : ArrayList<Float> = ArrayList()
    val taksit04 : ArrayList<Float> = ArrayList()
    val taksit05 : ArrayList<Float> = ArrayList()
    val taksit06 : ArrayList<Float> = ArrayList()
    val taksit07 : ArrayList<Float> = ArrayList()
    val taksit08 : ArrayList<Float> = ArrayList()
    val taksit09 : ArrayList<Float> = ArrayList()
    val taksit10 : ArrayList<Float> = ArrayList()
    val taksit11 : ArrayList<Float> = ArrayList()
    val taksit12 : ArrayList<Float> = ArrayList()
    val sanal_pos : ArrayList<String> = ArrayList()
    lateinit var coursesGV: GridView
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taksit_secenekleri)
        onBaslat()
        onKayitGetir()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack.setOnClickListener {
            val i= Intent(this,SatinAlmaEkraniActivity::class.java)
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

    }

    private fun onBaslat() {
        coursesGV = findViewById(com.selpar.pratikhasar.R.id.idGVcourses)

    }

    private fun onKayitGetir() {
        val urlsb = "firma_id="+intent.getStringExtra("firma_id")
        var url = "https://pratikhasar.com/netting/param_taksit.php?"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["taksit"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if(item.getString("kartgorsel").toString()!="null" && item.getString("kartgorsel").toString()!="N"){
                            kartbanka.add(item.getString("kartbanka"))
                            kartgorsel.add(item.getString("kartgorsel"))
                            taksit01.add(item.getString("taksit01").toFloat())
                            taksit02.add(item.getString("taksit02").toFloat())
                            taksit03.add(item.getString("taksit03").toFloat())
                            taksit04.add(item.getString("taksit04").toFloat())
                            taksit05.add(item.getString("taksit05").toFloat())
                            taksit06.add(item.getString("taksit06").toFloat())
                            taksit07.add(item.getString("taksit07").toFloat())
                            taksit08.add(item.getString("taksit08").toFloat())
                            taksit09.add(item.getString("taksit09").toFloat())
                            taksit10.add(item.getString("taksit10").toFloat())
                            taksit11.add(item.getString("taksit11").toFloat())
                            taksit12.add(item.getString("taksit12").toFloat())
                            sanal_pos.add(item.getString("sanal_pos"))
                        }
                    }
                    linearDoldur(kartbanka,kartgorsel,taksit01,taksit02,taksit03,taksit04,taksit05,taksit06,taksit07,taksit08,taksit09,taksit10,taksit11,taksit12,sanal_pos)


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

    private fun linearDoldur(
        tabelTextList: ArrayList<String>,
        tabelImageList: ArrayList<String>,
        taksit01: ArrayList<Float>,
        taksit02: ArrayList<Float>,
        taksit03: ArrayList<Float>,
        taksit04: ArrayList<Float>,
        taksit05: ArrayList<Float>,
        taksit06: ArrayList<Float>,
        taksit07: ArrayList<Float>,
        taksit08: ArrayList<Float>,
        taksit09: ArrayList<Float>,
        taksit10: ArrayList<Float>,
        taksit11: ArrayList<Float>,
        taksit12: ArrayList<Float>,
        sanal_pos: ArrayList<String>
    ) {
        val courseModelArrayList: ArrayList<TaksitModel> = ArrayList<TaksitModel>()

        for (i in 0 until tabelTextList.size) {

            courseModelArrayList.add(TaksitModel(
                intent.getStringExtra("firma_id").toString(),
                tabelTextList[i],tabelImageList[i],
                taksit01[i],taksit02[i],taksit03[i],
                taksit04[i],taksit05[i],taksit06[i],
                taksit07[i],taksit08[i],taksit09[i],
                taksit10[i],taksit11[i],taksit12[i],
                intent.getStringExtra("toplam").toString(),
                sanal_pos[i]
                ))

        }
        /*
        tabelTextList.forEach {
            courseModelArrayList.add(BitmisModel(tabel_id_list[0],tabelTextList[0],tabelImageList[0],"kadi", "ozel_id" ,"plaka", "firma_id"))

        }*/
        // val items = arrayOf("Öğe 1", "Öğe 2", "Öğe 3", "Öğe 4", "Öğe 5", "Öğe 6")
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        //coursesGV.adapter = adapter
        val adapter2 = TaksitAdapter(this,courseModelArrayList)

        coursesGV.adapter = adapter2
    }

}