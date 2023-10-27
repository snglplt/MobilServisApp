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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.BakimZamaniGelenAdapter
import com.selpar.pratikhasar.adapter.EvrakAdapter
import com.selpar.pratikhasar.adapter.EvrakGVAdapter
import com.selpar.pratikhasar.adapter.KayitliUstalarAdapter
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.news
import com.selpar.pratikhasar.data.ustalar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray

class UstaListelemeActivity : AppCompatActivity() {
    lateinit var rc_kayitli_ustalar:RecyclerView
    private lateinit var newArrayList : ArrayList<ustalar>
    lateinit var dilsecimi:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullanici_id:String
    lateinit var kadi:String
    lateinit var gorev:String
    lateinit var sifre:String
    var id = ArrayList<String>()
    var ad = ArrayList<String>()
    var soyad = ArrayList<String>()
    var brans = ArrayList<String>()
    var tel = ArrayList<String>()
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usta_listeleme)
        onBaslat()
        onKayitGetir()
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
            finish()
        }


    }
    private fun onKayitGetir() {

        val urlek="&kadi="+kadi+"&sifre="+sifre+"&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=usta_tanimlama_bul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{
                val json = response["ustalar"] as JSONArray

                val itemList: java.util.ArrayList<ustalar> = java.util.ArrayList()
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    val itemModel = ustalar(
                        item.getString("id"),
                        item.getString("ad"),
                        item.getString("soyad"),
                        item.getString("brans"),
                        item.getString("tel")

                    )

                    itemList.add(itemModel)
                }
                    val adapter =
                        KayitliUstalarAdapter(itemList)

                    rc_kayitli_ustalar.adapter= adapter
                    rc_kayitli_ustalar.layoutManager= LinearLayoutManager(this)
                    rc_kayitli_ustalar.setHasFixedSize(false)
                    newArrayList= arrayListOf<ustalar>()
                    //val kabulnom = item.getString("kabulnom").toString()
                    // Log.d("kabulnom: ", kabulnom)
                    //  evrak_resim_bul(kadi,firma,kabulnom)
                    // evrak_resim_getir(kadi,firma)
                    //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                    this?.let {
                        getUserData(it)

                    }}catch (e:Exception){}

                // servisHesapla(servis_hasar,servis_mekanik)

            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.gray))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    // .addSwipeLeftLabel(getString(R.string.kartikapat))
                    .addSwipeRightLabel(getString(R.string.guncelle))
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

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        viewHolder.itemId
                        //  val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text



                        val plaka_= viewHolder.itemView.findViewById<TextView>(R.id.Tvplaka).text
                        val tarih= viewHolder.itemView.findViewById<TextView>(R.id.Tvonarim).text

                       // guncelle(plaka_.toString(),tarih.toString())


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rc_kayitli_ustalar)


    }
    private fun getUserData(context: Context) {
        val itemList: java.util.ArrayList<ustalar> = java.util.ArrayList()


        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }
    private fun onBaslat() {
        rc_kayitli_ustalar=findViewById(R.id.rc_kayitli_ustalar)
        kadi=intent.getStringExtra("kadi").toString()
        yetki = intent.getStringExtra("yetki").toString()
        firma_id = intent.getStringExtra("firma_id").toString()
        kullanici_id = intent.getStringExtra("kullanici_id").toString()
        sifre =intent.getStringExtra("sifre").toString()
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
    }
}