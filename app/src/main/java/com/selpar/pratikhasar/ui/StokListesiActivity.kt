package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
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
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.StokAdapter
import com.selpar.pratikhasar.data.StokItem
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.HashMap

class StokListesiActivity : AppCompatActivity() {
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    lateinit var btn_guncelle:Button
    lateinit var id:String
    lateinit var guncelle_linear:LinearLayout
    lateinit var etstokno: EditText
    lateinit var etstokadi: EditText
    lateinit var etlistefiyati: EditText
    val itemList_kdv:ArrayList<String> = ArrayList()
    lateinit var spinner_kdv:Spinner
    lateinit var spinner_parabirimi:Spinner
    lateinit var spinner_tur:Spinner
    val spinner_parabirimi_list = ArrayList<String>()



    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stok_listesi)
        onBaslat()
        kdv_getir()
        stoktanimlama_bul()
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
        val spinner_arac_turu_police_alspinner1 = ArrayList<String>()
        val tur_value =this.resources.getStringArray(R.array.tur)
        for (i in tur_value.indices) {
            spinner_arac_turu_police_alspinner1.add(tur_value[i])
        }
        val tur_adapter: Any? = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_arac_turu_police_alspinner1 as List<Any?>
        )
        spinner_tur.setAdapter(tur_adapter as SpinnerAdapter?)

        btn_guncelle.setOnClickListener {
            val queue = Volley.newRequestQueue(applicationContext)
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    guncelle_linear.visibility=GONE
                     stoktanimlama_bul()
                    Toast.makeText(applicationContext,"Güncelleme Başarılı: "+intent.getStringExtra("firma_id"),Toast.LENGTH_SHORT).show() },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["id"] = id
                    params["firma_id"] = intent.getStringExtra("firma_id").toString()
                    params["stokno"] = etstokno.getText().toString()
                    params["stokadi"] = etstokadi.getText().toString()
                    params["fiyat"]= etlistefiyati.getText().toString()
                    params["kdv"]=spinner_kdv.selectedItem.toString()
                    params["parcatur"]=spinner_tur.selectedItem.toString()
                    params["toplam"]=(etlistefiyati.getText().toString().toInt()+etlistefiyati.getText().toString().toInt()*spinner_kdv.selectedItem.toString().toInt()/100).toString()
                    params["parabirimi"]=spinner_parabirimi.selectedItem.toString()
                    params["tur"] = "stok_kayit_guncelle"
                    return params
                }
            }
            queue.add(postRequest)
        }

    }

    private fun onBaslat() {
        newRecyclerviewm=findViewById(R.id.rc_stok_listesi)
        btn_guncelle=findViewById(R.id.btn_guncelle)
        guncelle_linear=findViewById(R.id.guncelle_linear)
        etstokno=findViewById(R.id.etstokno)
        etstokadi=findViewById(R.id.etstokadi)
        etlistefiyati=findViewById(R.id.etlistefiyati)
        spinner_kdv=findViewById(R.id.spinner_kdv)
        spinner_parabirimi=findViewById(R.id.spinner_parabirimi)
        spinner_tur=findViewById(R.id.spinner_tur)

    }
    private fun getUserData(context: Context) {

        val itemList: java.util.ArrayList<StokItem> = java.util.ArrayList()

        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }
    private fun stok_sil(id: String) {
        val queue = Volley.newRequestQueue(applicationContext)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                //  stoktanimlama_bul()
                //Toast.makeText(this@StokTanimlamaActivity,"Stokno2: "+stokno+ "--"+stokadi,Toast.LENGTH_LONG).show()

                Toast.makeText(applicationContext,"Silme Başarılı: "+intent.getStringExtra("firma_id"),Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id"] = id
                params["firma_id"] = intent.getStringExtra("firma_id").toString()
                params["tur"] = "stok_kayit_sil"
                return params
            }
        }
        queue.add(postRequest)

    }


    private fun stoktanimlama_bul()
    {
        val urlsb ="&firma_id="+intent.getStringExtra("firma_id").toString()
        var url = "https://pratikhasar.com/netting/mobil.php?tur=stoktanimlama_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["stok"] as JSONArray

                    val itemList: ArrayList<StokItem> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)


                        val itemModel = StokItem(
                            item.getString("id"),
                            item.getString("stokno"),item.getString("stokadi"),
                            item.getString("fiyat"),item.getString("kdv"),
                            item.getString("parabirimi"),item.getString("parcatur")

                        )


                        itemList.add(itemModel)
                        val adapter =
                            StokAdapter(itemList)

                        newRecyclerviewm.adapter= adapter
                        newRecyclerviewm.layoutManager= LinearLayoutManager(this)
                        newRecyclerviewm.setHasFixedSize(false)
                        newArrayList= arrayListOf<ClipData.Item>()
                        //val kabulnom = item.getString("kabulnom").toString()
                        // Log.d("kabulnom: ", kabulnom)
                        //  evrak_resim_bul(kadi,firma,kabulnom)
                        // evrak_resim_getir(kadi,firma)
                        //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                        this?.let {
                            getUserData(it)

                        }}}catch (e:Exception){}





            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
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
                    .addSwipeLeftLabel("SİL")
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
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val stokno= viewHolder.itemView.findViewById<TextView>(R.id.stokno).text
                        val stokadi= viewHolder.itemView.findViewById<TextView>(R.id.stokadi).text
                        val id= viewHolder.itemView.findViewById<TextView>(R.id.stokid).text


                        val builder = AlertDialog.Builder(this@StokListesiActivity, R.style.AlertDialogCustom)
                        builder.setTitle("Kayıt silinsin mi?")
                        builder.setPositiveButton("Evet") { dialog, which ->
                            Toast.makeText(this@StokListesiActivity,"Stokno: "+stokno.toString()+ "--"+stokadi.toString(),
                                Toast.LENGTH_LONG).show()
                            stok_sil(id.toString())
                            stoktanimlama_bul()
                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            stoktanimlama_bul()
                        }
                        builder.show()


                        // sil_alert(txt_stokAdi.getText().toString(),
                        //     txt_StokNo.getText().toString(),txtMiktar.getText().toString(),txtFiyat.getText().toString())
                        //   kayit_getir()
                        // newRecyclerviewm.removeItemDecorationAt(position)
                        // newRecyclerviewm.adapter= adapter
                        //  viewHolder.itemView.setBackgroundColor(Color.parseColor("#cc0000"))
                        // viewHolder.itemView.setBackgroundColor(R.color.red)
                        //exampleAdapter.notifyDataSetChanged();
                        // Do something when a user swept left

                    }
                    ItemTouchHelper.RIGHT -> {
                        id= viewHolder.itemView.findViewById<TextView>(R.id.stokid).text.toString()
                        val id2= viewHolder.itemView.findViewById<TextView>(R.id.stokid).text
                        val stokno= viewHolder.itemView.findViewById<TextView>(R.id.stokno).text
                        val stokadi= viewHolder.itemView.findViewById<TextView>(R.id.stokadi).text
                        val fiyat= viewHolder.itemView.findViewById<TextView>(R.id.fiyat).text
                        val kdv= viewHolder.itemView.findViewById<TextView>(R.id.kdv).text
                        val parabirimi= viewHolder.itemView.findViewById<TextView>(R.id.parabirimi).text

                        guncelle_stok_kaydi(id2.toString(),stokno.toString(),stokadi.toString(),fiyat.toString(),kdv.toString(),parabirimi.toString())
                        // resimGetir(kadi,ozel_id)
                        //  Toast.makeText(requireContext(),"dONUSTU:"+yol,Toast.LENGTH_SHORT).show()

                        //   Toast.makeText(requireContext(),"txt_evrakturu: "+txt_evrakturu,Toast.LENGTH_SHORT).show()
                        //   Toast.makeText(requireContext(),"txt_image: "+yol,Toast.LENGTH_SHORT).show()

                        /* onarim_guncelle(txt_stokAdi.getText().toString(),
                             txt_StokNo.getText().toString(),txtMiktar.getText().toString(),txtFiyat.getText().toString())
                         // Do something when a user swept right*/
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }
    private fun kdv_getir() {
        val url ="https://pratikhasar.com/netting/mobil.php?tur=kdv_getir"

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["kdv"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_kdv.add(item.getString("oran").toString())

                    }

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_kdv.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_kdv[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_kdv.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {
                    Toast.makeText(this, "MODEL doldur hatası", Toast.LENGTH_LONG)
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

    private fun guncelle_stok_kaydi(
        id: String,
        stokno: String,
        stokadi: String,
        fiyat: String,
        kdv: String,
        parabirimi: String
    ) {
        guncelle_linear.visibility= View.VISIBLE
        etstokno.setText(stokno)
        etstokadi.setText(stokadi)
        etlistefiyati.setText(fiyat)
        itemList_kdv.clear()
        itemList_kdv.add(kdv)
        spinner_parabirimi_list.clear()
        spinner_parabirimi_list.add(parabirimi)
        parabirimi_getir()
        kdv_getir()


    }

    private fun parabirimi_getir() {
        val spinner_durum_kart_ac_value1 = this.resources.getStringArray(R.array.parabirimi)
        for (i in spinner_durum_kart_ac_value1.indices) {
            spinner_parabirimi_list.add(spinner_durum_kart_ac_value1[i])
        }
        val spinner_durum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_parabirimi_list as List<Any?>
        )
        spinner_parabirimi.setAdapter(spinner_durum_kart_ac_adapter1 as SpinnerAdapter?)

    }

}