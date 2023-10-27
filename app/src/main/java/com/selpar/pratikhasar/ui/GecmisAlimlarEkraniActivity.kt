package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.GecmisAlimlarAdaper
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.StokAdapter
import com.selpar.pratikhasar.data.GecmiaAlimlarModel
import com.selpar.pratikhasar.data.StokItem
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray

class GecmisAlimlarEkraniActivity : AppCompatActivity() {
    lateinit var rc_gecmis_alimlar:RecyclerView
    var newArrayList:ArrayList<String> = ArrayList()
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gecmis_alimlar_ekrani)
        onBaslat()
        onGecmisAlimlar()
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

    private fun onGecmisAlimlar(){
        val urlsb ="&firma_id="+intent.getStringExtra("firma_id").toString()
        var url = "https://pratikhasar.com/netting/mobil.php?tur=gecmis_alimlar_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["gecmis"] as JSONArray

                    val itemList: ArrayList<GecmiaAlimlarModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val itemModel = GecmiaAlimlarModel(
                            intent.getStringExtra("firma_id").toString(),
                            item.getString("servis_turu"),item.getString("servis_suresi"),
                            item.getString("kontor"),item.getString("fiyat").toString(),item.getString("bitis_tarihi")

                        )
                        itemList.add(itemModel)
                        val adapter =
                            GecmisAlimlarAdaper(itemList)

                        rc_gecmis_alimlar.adapter= adapter
                        rc_gecmis_alimlar.layoutManager= LinearLayoutManager(this)
                        rc_gecmis_alimlar.setHasFixedSize(false)
                        newArrayList= arrayListOf<String>()
                        //val kabulnom = item.getString("kabulnom").toString()
                        // Log.d("kabulnom: ", kabulnom)
                        //  evrak_resim_bul(kadi,firma,kabulnom)
                        // evrak_resim_getir(kadi,firma)
                        //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                        this?.let {
                            getUserData(it)

                        }}}catch (e:Exception){
                        }





            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
            }
        )
        queue.add(request)


    }
    private fun getUserData(context: Context) {

        val itemList: java.util.ArrayList<StokItem> = java.util.ArrayList()

        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }


    private fun onBaslat() {
        rc_gecmis_alimlar=findViewById(R.id.rc_gecmis_alimlar)
    }
}