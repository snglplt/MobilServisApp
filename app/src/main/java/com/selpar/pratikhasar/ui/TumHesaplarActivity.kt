package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.CariAdapter
import com.selpar.pratikhasar.data.CariModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.ArrayList
import java.util.HashMap

class TumHesaplarActivity : AppCompatActivity() {
    lateinit var firma:String
    lateinit var yol:ArrayList<String>
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    lateinit var kadi:String
    lateinit var checkbox:CheckBox
    lateinit var checkBakiye:CheckBox
    lateinit var checkAlacak:CheckBox
    lateinit var checkboxodeme:CheckBox
    lateinit var floating_tahsilat:FloatingActionButton
    lateinit var floating_odeme:FloatingActionButton
    val itemList: ArrayList<CariModel> = ArrayList()
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var btn_geri:ImageView
    lateinit var btn_arama:ImageView
    lateinit var edarama:EditText
    lateinit var lyt_cari_guncelle:LinearLayout
    lateinit var txt_cari_unvan:EditText
    lateinit var txt_adres:EditText
    lateinit var txt_tel:EditText
    lateinit var txt_gsm:EditText
    lateinit var txt_vergi_no:EditText
    lateinit var txt_vergi_dairesi:EditText
    lateinit var txt_il:EditText
    lateinit var txt_ilce:EditText
    lateinit var txt_yetkiliadi:EditText
    lateinit var txt_mail:EditText
    lateinit var btn_cari_guncelle:Button
    lateinit var txtid:TextView
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tum_hesaplar)
        newRecyclerviewm=findViewById(R.id.rc_tum_cariler)
        checkbox=findViewById(R.id.checkBox)
        checkBakiye=findViewById(R.id.checkBakiye)
        checkAlacak=findViewById(R.id.checkAlacak)
        checkboxodeme=findViewById(R.id.checkboxodeme)
        floating_tahsilat=findViewById(R.id.floating_tahsilat)
        floating_odeme=findViewById(R.id.floating_odeme)
        btn_geri=findViewById(R.id.btn_geri)
        btn_arama=findViewById(R.id.btn_arama)
        edarama=findViewById(R.id.edarama)
        txt_cari_unvan=findViewById(R.id.txt_cari_unvan)
        txt_adres=findViewById(R.id.txt_adres)
        txt_tel=findViewById(R.id.txt_tel)
        txt_gsm=findViewById(R.id.txt_gsm)
        txt_vergi_no=findViewById(R.id.txt_vergi_no)
        txt_vergi_dairesi=findViewById(R.id.txt_vergi_dairesi)
        txt_il=findViewById(R.id.txt_il)
        txt_ilce=findViewById(R.id.txt_ilce)
        txt_yetkiliadi=findViewById(R.id.txt_yetkiliadi)
        txt_mail=findViewById(R.id.txt_mail)
        btn_cari_guncelle=findViewById(R.id.btn_cari_guncelle)
        lyt_cari_guncelle=findViewById(R.id.lyt_cari_guncelle)
        txtid=findViewById(R.id.txtid)
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        firmaGetir2(kadi)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        btn_cari_guncelle.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    lyt_cari_guncelle.visibility=GONE
                    Toast.makeText(this,"Güncelleme Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

                },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["firma_id"] = firma_id
                    params["id"] = txtid.getText().toString()
                    params["cari_unvan"] = txt_cari_unvan.getText().toString()
                    params["vergino"] = txt_vergi_no.getText().toString()
                    params["vergidairesi"] = txt_vergi_dairesi.getText().toString()
                    params["adres"] = txt_adres.getText().toString()
                    params["il"]=txt_il.getText().toString()
                    params["ilce"] = txt_ilce.getText().toString()
                    params["tel"]=txt_tel.getText().toString()
                    params["gsm"]=txt_gsm.getText().toString()
                    params["yetkili_isim_soyisim"]=txt_yetkiliadi.getText().toString()
                    params["mail"]=txt_mail.getText().toString()
                    params["tur"] = "cari_bilgi_guncelle"
                    return params
                }
            }
            queue.add(postRequest)
        }
        btn_arama.setOnClickListener {
            itemList.clear()
            val urlsb = "&firma_id=" + firma+"&aranan="+edarama.getText().toString()
            var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_getir" + urlsb
            Log.d("ALACAK",url)
            val queue: RequestQueue = Volley.newRequestQueue(this)
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try{
                        var tahsilat=0f
                        var odeme=0f
                        val json = response["cariler"] as JSONArray
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            if(item.getString("tahsilat")!="null"){
                                tahsilat=item.getString("tahsilat").toFloat()
                            }
                            else{
                                tahsilat=0f
                            }
                            if(item.getString("odeme")!="null"){
                                odeme=item.getString("odeme").toFloat()
                            }
                            else{
                                odeme=0f
                            }
                            val itemModel = CariModel(
                                    item.getString("cari"),
                                    "",
                                    odeme.toString(),tahsilat.toString(),(tahsilat-odeme).toString()
                                )
                                itemList.add(itemModel)
                            }
                            val adapter =
                                CariAdapter(itemList)
                            newRecyclerviewm.adapter = adapter
                            newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                            newRecyclerviewm.setHasFixedSize(false)
                            newArrayList = arrayListOf<ClipData.Item>()
                            this?.let {
                                getUserData(it)
                            }

                        }catch (e:Exception){}
                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                    //Toast.makeText(this, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
                }
            )
            queue.add(request)
            val simpleCallback = object :
                ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(applicationContext,
                            R.color.blue))
                        .addSwipeLeftLabel("PDF")
                        .addSwipeRightLabel("GÜNCELLE")
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
                            val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                            guncelle_cari(txt_cari.toString())
                      }
                        ItemTouchHelper.LEFT -> {
                            val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                            val i=Intent(applicationContext,CariPdfActivity::class.java)
                            i.putExtra("cari_unvan",txt_cari.toString())
                            i.putExtra("dilsecimi",dilsecimi)
                            i.putExtra("yetki",yetki)
                            i.putExtra("firma_id",firma_id)
                            i.putExtra("kullanici_id",kullnciid)
                            i.putExtra("kadi",kadi)
                            i.putExtra("sifre",sifrem)
                            startActivity(i)
                            firmaGetir2(kadi)

                        }
                    }
                }
            }
            val itemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


        }

        mBack.setOnClickListener {
            val i= Intent(this,HomeActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi",kadi)
            startActivity(i)

        }
        floating_tahsilat.setOnClickListener {
            val i= Intent(this,TahsilatActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi",kadi)
            startActivity(i)
        }
        floating_odeme.setOnClickListener {
            val i= Intent(this,OdemeActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi",kadi)
            startActivity(i)
        }
        checkBakiye.isChecked=true

        //acikKartGetir(firma)
        checkbox?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (checkbox!!.isChecked) {
                    itemList.clear()
                    checkBakiye.isChecked=false
                    checkAlacak.isChecked=false
                    checkboxodeme.isChecked=false
                    firmaGetir(kadi)

                }
                    //bilgi_getir(kadi,firma)                                }
                else{
                   // bilgi_getir_bakiye(kadi,firma)
                }

            }
        })
        checkBakiye?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (checkBakiye!!.isChecked) {
                    itemList.clear()
                    checkbox.isChecked=false
                    checkAlacak.isChecked=false
                    checkboxodeme.isChecked=false
                    firmaGetir2(kadi)
                }
                else{
                   // bilgi_getir(kadi,firma)
                }

            }
        })
        checkAlacak.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (checkAlacak!!.isChecked) {
                    itemList.clear()
                    checkbox.isChecked=false
                    checkBakiye.isChecked=false
                    checkboxodeme.isChecked=false
                    newRecyclerviewm.adapter=null

                    alacakGetir(kadi)
                }
                else{
                    // bilgi_getir(kadi,firma)
                }

            }
        })
        checkboxodeme.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (checkboxodeme!!.isChecked) {
                    itemList.clear()
                    newRecyclerviewm.adapter=null
                    checkbox.isChecked=false
                    checkAlacak.isChecked=false
                    checkBakiye.isChecked=false
                    borcGetir(kadi)
                }
                else{
                    // bilgi_getir(kadi,firma)
                }

            }
        })
    }

    private fun guncelle_cari(cari_unvan: String) {
        lyt_cari_guncelle.visibility=VISIBLE
        val urlsb = "&kadi=" + kadi+"&cari_unvan="+cari_unvan
        var url = "https://pratikhasar.com/netting/mobil.php?tur=bilgi_cari_getir" + urlsb
        Log.d("ALACAK",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["cari"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item != null) {
                            txtid.setText(item.getString("id"))
                            txt_cari_unvan.setText(item.getString("cari_unvan"))
                            txt_adres.setText(item.getString("adres"))
                            txt_tel.setText(item.getString("tel"))
                            txt_gsm.setText(item.getString("gsm"))
                            txt_vergi_no.setText(item.getString("vergino"))
                            txt_vergi_dairesi.setText(item.getString("vergidairesi"))
                            txt_il.setText(item.getString("il"))
                            txt_ilce.setText(item.getString("ilce"))
                            txt_yetkiliadi.setText(item.getString("yetkili_isim_soyisim"))
                            txt_mail.setText(item.getString("mail"))

                        }


                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
            }
        )
        queue.add(request)


    }

    fun borcGetir(kadi:String) {
        itemList.clear()
        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=borc_getir" + urlsb
        Log.d("ALACAK",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["cari"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item != null) {

                            val itemModel = CariModel(
                                item.getString("cari_unvan"),
                                item.getString("aciklama"),
                                "",item.getString("tutar"),"-"+item.getString("tutar")
                            )
                            itemList.add(itemModel)
                        }
                        val adapter =
                            CariAdapter(itemList)
                        newRecyclerviewm.adapter = adapter
                        newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                        newRecyclerviewm.setHasFixedSize(false)
                        newArrayList = arrayListOf<ClipData.Item>()
                        this?.let {
                            getUserData(it)
                        }

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)



    }

    fun alacakGetir(kadi:String) {
        itemList.clear()
        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=alacak_getir" + urlsb
        Log.d("ALACAK",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["cari"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item != null) {
                           if(item.getString("tutar").toString()!="0") {
                               val itemModel = CariModel(
                                   item.getString("cari_unvan"),
                                   item.getString("aciklama"),
                                   item.getString("tutar"),
                                   "",
                                   item.getString("tutar")
                               )
                               itemList.add(itemModel)
                           }
                        }
                        val adapter =
                            CariAdapter(itemList)
                        newRecyclerviewm.adapter = adapter
                        newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                        newRecyclerviewm.setHasFixedSize(false)
                        newArrayList = arrayListOf<ClipData.Item>()
                        this?.let {
                            getUserData(it)
                        }

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }

    fun firmaGetir(kadi:String) {
        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=firma_id_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["firma_id"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        firma=item.getString("firma_id")
                      //  acikKartGetir(firma)
                        // tumCariGetir(firma)
                        cariGetirBul(firma)
                        Log.d("FIRMA22: ", firma)

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
            }
        )
        queue.add(request)

    }

    private fun tumCariGetir(firma: String) {
        itemList.clear()

        val urlek="&kadi="+kadi+"&firma_id="+firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try {

                val json = response["cariler"] as JSONArray
                var tahsilat=0f
                var odeme=0f
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    if(item.getString("tahsilat").toString()=="null")
                    {
                        tahsilat=0f
                    }
                    else{
                        tahsilat=item.getString("tahsilat").toFloat()
                    }
                    if(item.getString("odeme").toString()=="null")
                    {
                        odeme=0f
                    }
                    else{
                        odeme=item.getString("odeme").toFloat()
                    }
                        val itemModel = CariModel(
                            item.getString("cari"),
                            "",tahsilat.toString(),odeme.toString(),(tahsilat-odeme).toString()
                        )
                        itemList.add(itemModel)


                }
                val adapter =
                    CariAdapter(itemList)
                newRecyclerviewm.adapter = adapter
                newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                newRecyclerviewm.setHasFixedSize(false)
                newArrayList = arrayListOf<ClipData.Item>()
                this?.let {
                    getUserData(it)
                }
            }catch (e:Exception){}


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
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)


                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }

    fun firmaGetir2(kadi:String) {
        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=firma_id_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["firma_id"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        firma=item.getString("firma_id")
                        //acikKartGetir2(firma)
                        //tum_bakiye(firma)
                        BakiyeGetir(firma)
                        Log.d("FIRMA22: ", firma)

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
            }
        )
        queue.add(request)

    }



    private fun BakiyeGetir(firma: String) {
        itemList.clear()
        val urlek="&kadi="+kadi+"&firma_id="+firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try {

                val json = response["cariler"] as JSONArray
                var tahsilat=0f
                var odeme=0f
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    if(item.getString("tahsilat").toString()!="0" || item.getString("odeme").toString()!="0"){


                    if(item.getString("tahsilat").toString()!="null" ) {
                        if(item.getString("odeme").toString()!="null"){
                            tahsilat=item.getString("tahsilat").toFloat()
                            odeme=item.getString("odeme").toFloat()

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)
                        }
                        else{
                            tahsilat=item.getString("tahsilat").toFloat()
                            odeme=0f

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)
                        }

                    }
                    else{
                        if(item.getString("odeme")!="null"){
                            tahsilat=0f
                            odeme=item.getString("odeme").toFloat()

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)

                        }
                    }

                }}
                val adapter =
                    CariAdapter(itemList)
                newRecyclerviewm.adapter = adapter
                newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                newRecyclerviewm.setHasFixedSize(false)
                newArrayList = arrayListOf<ClipData.Item>()
                this?.let {
                    getUserData(it)
                }
            }catch (e:Exception){}


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
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        i.putExtra("cari",txt_cari.toString())
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)



    }

    private fun cariGetirBul(firma: String) {
        itemList.clear()
        val urlek="&kadi="+kadi+"&firma_id="+firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try {

                val json = response["cariler"] as JSONArray
                var tahsilat=0f
                var odeme=0f
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    if(item.getString("tahsilat").toString()=="null" && item.getString("odeme").toString()=="null"){
                        val itemModel = CariModel(
                            item.getString("cari"),
                            "", "0", "0", "0"
                        )
                        itemList.add(itemModel)

                    }
                    if(item.getString("tahsilat").toString()!="null" ) {
                        if(item.getString("odeme").toString()!="null"){
                            tahsilat=item.getString("tahsilat").toFloat()
                            odeme=item.getString("odeme").toFloat()

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)
                        }
                        else{
                            tahsilat=item.getString("tahsilat").toFloat()
                            odeme=0f

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)
                        }

                    }
                    else{
                        if(item.getString("odeme")!="null"){
                            tahsilat=0f
                            odeme=item.getString("odeme").toFloat()

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)

                        }
                    }

                }
                val adapter =
                    CariAdapter(itemList)
                newRecyclerviewm.adapter = adapter
                newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                newRecyclerviewm.setHasFixedSize(false)
                newArrayList = arrayListOf<ClipData.Item>()
                this?.let {
                    getUserData(it)
                }
            }catch (e:Exception){}


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
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        i.putExtra("cari",txt_cari.toString())
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)



    }

    private fun tum_bakiye(firma: String) {
        itemList.clear()

        val urlek="&kadi="+kadi+"&firma_id="+firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try {

                val json = response["cariler"] as JSONArray
                var tahsilat=0f
                var odeme=0f
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    if(item.getString("tahsilat").toString()!="null" ) {
                        if(item.getString("odeme").toString()!="null"){
                            tahsilat=item.getString("tahsilat").toFloat()
                            odeme=item.getString("odeme").toFloat()

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)
                        }
                        else{
                            tahsilat=item.getString("tahsilat").toFloat()
                            odeme=0f

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)
                        }

                    }
                    else{
                        if(item.getString("odeme")!="null"){
                            tahsilat=0f
                            odeme=item.getString("odeme").toFloat()

                            val itemModel = CariModel(
                                item.getString("cari"),
                                "", tahsilat.toString(), odeme.toString(), (tahsilat - odeme).toString()
                            )
                            itemList.add(itemModel)

                        }
                    }

                }
                val adapter =
                    CariAdapter(itemList)
                newRecyclerviewm.adapter = adapter
                newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                newRecyclerviewm.setHasFixedSize(false)
                newArrayList = arrayListOf<ClipData.Item>()
                this?.let {
                    getUserData(it)
                }
            }catch (e:Exception){}


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
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }

    private fun acikKartGetir(firma: String) {
        itemList.clear()

        val urlek="&kadi="+kadi+"&firma_id="+ firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try {

                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    if (item != null) {
                        var toplam=item.getString("toplam")
                        if(toplam!="null"){
                            toplam=item.getString("toplam")
                        }else{
                            toplam= 0.toString()
                        }
                        val itemModel = CariModel(
                            item.getString("unvan"),
                            item.getString("plaka"),
                            toplam,"",toplam
                        )
                        itemList.add(itemModel)
                    }

                }
                val adapter =
                    CariAdapter(itemList)
                newRecyclerviewm.adapter = adapter
                newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                newRecyclerviewm.setHasFixedSize(false)
                newArrayList = arrayListOf<ClipData.Item>()
                this?.let {
                    getUserData(it)
                }
            }catch (e:Exception){}


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
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }
    private fun acikKartGetir2(firma: String) {
        itemList.clear()

        val urlek="&kadi="+kadi+"&firma_id="+ firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try {

                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    if (item != null) {
                        var toplam=item.getString("toplam")
                        if(toplam!="null"){

                        val itemModel = CariModel(
                            item.getString("unvan"),
                            item.getString("plaka"),
                            toplam,"",toplam
                        )
                        itemList.add(itemModel)
                    }}

                }
                val adapter =
                    CariAdapter(itemList)
                newRecyclerviewm.adapter = adapter
                newRecyclerviewm.layoutManager = LinearLayoutManager(this)
                newRecyclerviewm.setHasFixedSize(false)
                newArrayList = arrayListOf<ClipData.Item>()
                this?.let {
                    getUserData(it)
                }
            }catch (e:Exception){}


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
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }

    private fun bilgi_getir_bakiye(kadi: String, firma: String) {
        itemList.clear()

        val urlsb ="&kadi=" + kadi+ "&firma_id="+firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_getir_bakiye"+urlsb
        Log.d("KABULNOOOO. ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                    response ->
                try{
                    itemList.clear()
                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if(item!=null){
                        val itemModel = CariModel(
                            item.getString("unvan"),item.getString("borc"),item.getString("alacak"),"",""
                        )
                        itemList.add(itemModel)}

                    }
                    val adapter =
                        CariAdapter(itemList)
                    newRecyclerviewm.adapter= adapter
                    newRecyclerviewm.layoutManager= LinearLayoutManager(this)
                    newRecyclerviewm.setHasFixedSize(false)
                    newArrayList= arrayListOf<ClipData.Item>()
                    this?.let {
                        getUserData(it)
                    }
                }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }


    private fun getUserData(context: Context) {
        val itemList: ArrayList<CariModel> = ArrayList()
    }
    private fun bilgi_getir(kadi: String, firma: String)  {
        itemList.clear()

        val urlsb ="&kadi=" + kadi+ "&firma_id="+firma
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_getir"+urlsb
        Log.d("KABULNOOOO. ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                    response ->
                try{
                    itemList.clear()

                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val itemModel = CariModel(
                            item.getString("isim"),item.getString("adres"),item.getString("vergino"),"",""
                        )
                        itemList.add(itemModel)
                    }
                    val adapter =
                        CariAdapter(itemList)
                    newRecyclerviewm.adapter= adapter
                    newRecyclerviewm.layoutManager= LinearLayoutManager(this)
                    newRecyclerviewm.setHasFixedSize(false)
                    newArrayList= arrayListOf<ClipData.Item>()
                    this?.let {
                        getUserData(it)
                    }
                }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
               // Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(applicationContext,
                            R.color.blue))
                    .addSwipeLeftLabel("PDF")
                    .addSwipeRightLabel("GÜNCELLE")
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
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        guncelle_cari(txt_cari.toString())
                    }
                    ItemTouchHelper.LEFT -> {
                        val txt_cari= viewHolder.itemView.findViewById<TextView>(R.id.txt_cari_ad_tum).text
                        val i=Intent(applicationContext,CariPdfActivity::class.java)
                        i.putExtra("cari_unvan",txt_cari.toString())
                        i.putExtra("dilsecimi",dilsecimi)
                        i.putExtra("yetki",yetki)
                        i.putExtra("firma_id",firma_id)
                        i.putExtra("kullanici_id",kullnciid)
                        i.putExtra("kadi",kadi)
                        i.putExtra("sifre",sifrem)
                        startActivity(i)
                        firmaGetir2(kadi)

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)

    }
}