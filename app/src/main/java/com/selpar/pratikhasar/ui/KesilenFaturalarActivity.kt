package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
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
import com.selpar.pratikhasar.adapter.GidenFaturaAdapter
import com.selpar.pratikhasar.adapter.GidenFaturaModel
import com.selpar.pratikhasar.data.ItemModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.ArrayList

class KesilenFaturalarActivity : AppCompatActivity() {
    lateinit var rc_giden_faturalar: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    lateinit var kadi:String
    lateinit var firma_id:String
    lateinit var spinner_evrak_turu: Spinner
    lateinit var spinner_kdv_turu: Spinner
    lateinit var spinner_e_fatura_tipi: Spinner
    lateinit var spinner_evrak_tipi: Spinner
    lateinit var spinner_e_kontrol: Spinner
    lateinit var spinner_plaka_yazdir: Spinner
    lateinit var spinner_km_yazdir: Spinner
    lateinit var spinner_no_yazdir: Spinner
    lateinit var et_sevk_tarihi: EditText
    lateinit var et_odeme_tarihi: EditText
    lateinit var et_evrak_tarihi: EditText
    lateinit var txttumu: EditText
    lateinit var btn_arama_imageview:ImageView
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    lateinit var progressBar:ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kesilen_faturalar)
        onBaslat()
        onFaturaGetir()
        progressBar = findViewById(R.id.progressBar)
        startLoadingAnimation()
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
        btn_arama_imageview.setOnClickListener {
            onFaturaGetirAranan()
        }
    }
    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        progressBar.setVisibility(View.VISIBLE)
        progressBar.startAnimation(animation)

        // Simulate loading delay
        // Remove this code in your actual implementation
        Handler().postDelayed(Runnable {
            progressBar.clearAnimation()
            progressBar.setVisibility(View.GONE)
        }, 5000) // Replace 3000 with your desired loading time
    }
    private fun onFaturaGetirAranan() {
        val urlsb = "&firma_id=" + firma_id+"&tumu="+txttumu.getText().toString().replace(" ","%20")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kayitli_fatura_getir_aranan" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray
                    val itemList: ArrayList<GidenFaturaModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        val itemModel = GidenFaturaModel(
                            item.getString("guid"),
                            item.getString("id"),
                            firma_id,
                            item.getString("unvan"),
                            item.getString("kodu"),
                            item.getString("tarih"),
                            item.getString("gonder"),
                            item.getString("geneltoplam"),
                            item.getString("evrakturu")

                        )
                        itemList.add(itemModel)


                    }
                    val adapter =
                        GidenFaturaAdapter(itemList)

                    rc_giden_faturalar.adapter= adapter
                    rc_giden_faturalar.layoutManager= LinearLayoutManager(this)
                    rc_giden_faturalar.setHasFixedSize(false)
                    newArrayList= arrayListOf<ClipData.Item>()
                    this?.let {
                        getUserData(it)

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
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
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(applicationContext,R.color.blue))
                    .addSwipeLeftLabel(getString(R.string.sil))
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

            @SuppressLint("ResourceAsColor")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid)
                        val txtgonderkontrol= viewHolder.itemView.findViewById<TextView>(R.id.txtgonderkontrol)
                        if(txtgonderkontrol.getText().toString()=="GÖNDERİLDİ")
                        {
                            onFaturaGetir()
                        }else{
                            val builder = AlertDialog.Builder(this@KesilenFaturalarActivity)
                            builder.setTitle(getString(R.string.silinsinmi))
                            builder.setMessage(getString(R.string.sil))
                            builder.setPositiveButton(R.string.evet) { dialog, which ->
                                fatura_sil(txtid.getText().toString())
                                // Do something when the "OK" button is clicked
                            }
                            builder.setNegativeButton(R.string.hayir) { dialog, which ->
                                onFaturaGetir()
                                // Do something when the "Cancel" button is clicked
                            }
                            val dialog = builder.create()
                            dialog.show()
                            onFaturaGetir()


                        }
                        /*sil_alert(txt_stokAdi.getText().toString(),
                            txt_StokNo.getText().toString(),txtMiktar.getText().toString(),txtFiyat.getText().toString())
                        kayit_getir()*/
                        // newRecyclerviewm.removeItemDecorationAt(position)
                        // newRecyclerviewm.adapter= adapter
                        //  viewHolder.itemView.setBackgroundColor(Color.parseColor("#cc0000"))
                        // viewHolder.itemView.setBackgroundColor(R.color.red)
                        //exampleAdapter.notifyDataSetChanged();
                        // Do something when a user swept left
                    }
                    ItemTouchHelper.RIGHT -> {
                        val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid)
                        val txtgonderkontrol= viewHolder.itemView.findViewById<TextView>(R.id.txtgonderkontrol)
                        if(txtgonderkontrol.getText().toString()=="GÖNDERİLDİ")
                        {
                            onPdfGoster()
                            onFaturaGetir()
                        }else{
                            fatura_guncelle(txtid.getText().toString())
                        }
                        /*onarim_guncelle(txt_stokAdi.getText().toString(),
                                txt_StokNo.getText().toString(),txtMiktar.getText().toString().toInt(),txtFiyat.getText().toString().toFloat(),txt_kdv.getText().toString())
                            // Do something when a user swept right*/
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rc_giden_faturalar)


    }

    private fun onPdfGoster() {
        val i=Intent(applicationContext,FaturaPdfGosterActivity::class.java)
        i.putExtra("path","https://pratikhasar.com/netting/e_giden_pdf.php?firma_id=1&InvoiceUUID=4B478099-3F5E-044F-9D75-EC8803FD9C90")
        startActivity(i)

    }

    private fun onFaturaGetir() {
        val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kayitli_fatura_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray
                    val itemList: ArrayList<GidenFaturaModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        val itemModel = GidenFaturaModel(
                            item.getString("guid"),
                            item.getString("id"),
                            firma_id,
                            item.getString("unvan"),
                            item.getString("kodu"),
                            item.getString("tarih"),
                            item.getString("gonder"),
                            item.getString("geneltoplam"),
                            item.getString("evrakturu")

                        )
                        itemList.add(itemModel)


                    }
                    val adapter =
                        GidenFaturaAdapter(itemList)

                    rc_giden_faturalar.adapter= adapter
                    rc_giden_faturalar.layoutManager= LinearLayoutManager(this)
                    rc_giden_faturalar.setHasFixedSize(false)
                    newArrayList= arrayListOf<ClipData.Item>()
                    this?.let {
                        getUserData(it)

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
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
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(applicationContext,R.color.blue))
                    .addSwipeLeftLabel(getString(R.string.sil))
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

            @SuppressLint("ResourceAsColor")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid)
                        val txtgonderkontrol= viewHolder.itemView.findViewById<TextView>(R.id.txtgonderkontrol)
                        if(txtgonderkontrol.getText().toString()=="GÖNDERİLDİ")
                        {
                            onFaturaGetir()
                        }else{
                            val builder = AlertDialog.Builder(this@KesilenFaturalarActivity)
                            builder.setTitle(getString(R.string.silinsinmi))
                            builder.setMessage(getString(R.string.sil))
                            builder.setPositiveButton(R.string.evet) { dialog, which ->
                                fatura_sil(txtid.getText().toString())
                                // Do something when the "OK" button is clicked
                            }
                            builder.setNegativeButton(R.string.hayir) { dialog, which ->
                                // Do something when the "Cancel" button is clicked
                            }
                            val dialog = builder.create()
                            dialog.show()

                        }
                        /*sil_alert(txt_stokAdi.getText().toString(),
                            txt_StokNo.getText().toString(),txtMiktar.getText().toString(),txtFiyat.getText().toString())
                        kayit_getir()*/
                        // newRecyclerviewm.removeItemDecorationAt(position)
                        // newRecyclerviewm.adapter= adapter
                        //  viewHolder.itemView.setBackgroundColor(Color.parseColor("#cc0000"))
                        // viewHolder.itemView.setBackgroundColor(R.color.red)
                        //exampleAdapter.notifyDataSetChanged();
                        // Do something when a user swept left
                    }
                    ItemTouchHelper.RIGHT -> {
                        val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid)
                        val txtgonderkontrol= viewHolder.itemView.findViewById<TextView>(R.id.txtgonderkontrol)
                        if(txtgonderkontrol.getText().toString()=="GÖNDERİLDİ")
                        {
                           // onPdfGoster()
                            onFaturaGetir()
                        }else{
                            fatura_guncelle(txtid.getText().toString())
                        }
                        /*onarim_guncelle(txt_stokAdi.getText().toString(),
                                txt_StokNo.getText().toString(),txtMiktar.getText().toString().toInt(),txtFiyat.getText().toString().toFloat(),txt_kdv.getText().toString())
                            // Do something when a user swept right*/
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rc_giden_faturalar)


    }

    private fun fatura_sil(id: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                onFaturaGetir()
                Toast.makeText(this, getString(R.string.silmebasarili), Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI2")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["id"] = id
                params["kadi"] = kadi
                params["firma_id"] = firma_id
                params["tur"] = "e_fatura_sil"
                return params
            }
        }
        queue.add(postRequest)


    }

    private fun fatura_guncelle(id: String) {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle(getString(R.string.fatura_ayari))
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.fatura_ayarlari, null)
        spinner_evrak_turu=view.findViewById(R.id.spinner_evrak_turu)
        spinner_e_fatura_tipi=view.findViewById(R.id.spinner_e_fatura_tipi)
        et_sevk_tarihi=view.findViewById(R.id.et_sevk_tarihi)
        et_evrak_tarihi=view.findViewById(R.id.et_evrak_tarihi)
        et_odeme_tarihi=view.findViewById(R.id.et_odeme_tarihi)
        spinner_kdv_turu=view.findViewById(R.id.spinner_kdv_turu)
        spinner_evrak_tipi=view.findViewById(R.id.spinner_evrak_tipi)
        spinner_e_kontrol=view.findViewById(R.id.spinner_e_kontrol)
        spinner_plaka_yazdir=view.findViewById(R.id.spinner_plaka_yazdir)
        spinner_km_yazdir=view.findViewById(R.id.spinner_km_yazdir)
        spinner_no_yazdir=view.findViewById(R.id.spinner_no_yazdir)
        val takvim = Calendar.getInstance();
        val yil: Int = takvim.get(Calendar.YEAR)
        val ay: Int = takvim.get(Calendar.MONTH) + 1
        val gun: Int = takvim.get(Calendar.DAY_OF_MONTH)
        et_sevk_tarihi.setText(yil.toString() + "-" + ay + "-" + gun)
        et_evrak_tarihi.setText(yil.toString() + "-" + ay + "-" + gun)
        et_odeme_tarihi.setText(yil.toString() + "-" + ay + "-" + gun)


        val spinner_evrak_turu_value1=this.resources.getStringArray(R.array.efatura_turu)
        val spinner_evrak_turu_alspinner1= ArrayList<String>()

        for(i in spinner_evrak_turu_value1.indices){
            spinner_evrak_turu_alspinner1.add(spinner_evrak_turu_value1[i])
        }
        val spinner_evrak_turu_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_evrak_turu_alspinner1 as List<Any?>
        )
        spinner_evrak_turu.setAdapter(spinner_evrak_turu_adapter1 as SpinnerAdapter?)
        val spinner_kdv_turu_value1=this.resources.getStringArray(R.array.kdv_tip)
        val spinner_kdv_turu_alspinner1= ArrayList<String>()

        for(i in spinner_kdv_turu_value1.indices){
            spinner_kdv_turu_alspinner1.add(spinner_kdv_turu_value1[i])
        }
        val spinner_kdv_turu_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_kdv_turu_alspinner1 as List<Any?>
        )
        spinner_kdv_turu.setAdapter(spinner_kdv_turu_adapter1 as SpinnerAdapter?)
        val spinner_e_fatura_tipi_value1=this.resources.getStringArray(R.array.efatura_turu)
        val spinner_e_fatura_tipi_alspinner1= ArrayList<String>()

        for(i in spinner_e_fatura_tipi_value1.indices){
            spinner_e_fatura_tipi_alspinner1.add(spinner_e_fatura_tipi_value1[i])
        }
        val spinner_e_fatura_tipi_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_e_fatura_tipi_alspinner1 as List<Any?>
        )
        spinner_e_fatura_tipi.setAdapter(spinner_e_fatura_tipi_adapter1 as SpinnerAdapter?)

        val spinner_evrak_tipi_value1=this.resources.getStringArray(R.array.evrak_tipi)
        val spinner_evrak_tipi_alspinner1= ArrayList<String>()

        for(i in spinner_evrak_tipi_value1.indices){
            spinner_evrak_tipi_alspinner1.add(spinner_evrak_tipi_value1[i])
        }
        val spinner_evrak_tipi_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_evrak_tipi_alspinner1 as List<Any?>
        )
        spinner_evrak_tipi.setAdapter(spinner_evrak_tipi_adapter1 as SpinnerAdapter?)
        val spinner_e_kontrol_value1=this.resources.getStringArray(R.array.e_kontrol)
        val spinner_e_kontrol_alspinner1= ArrayList<String>()

        for(i in spinner_evrak_tipi_value1.indices){
            spinner_e_kontrol_alspinner1.add(spinner_e_kontrol_value1[i])
        }
        val spinner_e_kontrol_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_e_kontrol_alspinner1 as List<Any?>
        )
        spinner_e_kontrol.setAdapter(spinner_e_kontrol_adapter1 as SpinnerAdapter?)
        val spinner_plaka_yazdir_value1=this.resources.getStringArray(R.array.ssl)
        val spinner_plaka_yazdir_alspinner1= ArrayList<String>()

        for(i in spinner_plaka_yazdir_value1.indices){
            spinner_plaka_yazdir_alspinner1.add(spinner_plaka_yazdir_value1[i])
        }
        val spinner_plaka_yazdir_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_plaka_yazdir_alspinner1 as List<Any?>
        )
        spinner_plaka_yazdir.setAdapter(spinner_plaka_yazdir_adapter1 as SpinnerAdapter?)
        val spinner_km_yazdir_value1=this.resources.getStringArray(R.array.ssl)
        val spinner_km_yazdir_alspinner1= ArrayList<String>()

        for(i in spinner_km_yazdir_value1.indices){
            spinner_km_yazdir_alspinner1.add(spinner_km_yazdir_value1[i])
        }
        val spinner_km_yazdir_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_km_yazdir_alspinner1 as List<Any?>
        )
        spinner_km_yazdir.setAdapter(spinner_km_yazdir_adapter1 as SpinnerAdapter?)
        val spinner_no_yazdir_value1=this.resources.getStringArray(R.array.ssl)
        val spinner_no_yazdir_alspinner1= ArrayList<String>()

        for(i in spinner_no_yazdir_value1.indices){
            spinner_no_yazdir_alspinner1.add(spinner_no_yazdir_value1[i])
        }
        val spinner_no_yazdir_adapter1:Any?= ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item_text,
            spinner_no_yazdir_alspinner1 as List<Any?>
        )
        spinner_no_yazdir.setAdapter(spinner_no_yazdir_adapter1 as SpinnerAdapter?)

        et_evrak_tarihi.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val dat = (year.toString()+ "-" + (monthOfYear + 1) + "-" +dayOfMonth.toString()  )
                        et_evrak_tarihi.setText(dat)


                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }

        et_sevk_tarihi.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val dat = (year.toString()+ "-" + (monthOfYear + 1) + "-" +dayOfMonth.toString()  )
                        et_sevk_tarihi.setText(dat)


                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }
        et_odeme_tarihi.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val dat = (year.toString()+ "-" + (monthOfYear + 1) + "-" +dayOfMonth.toString()  )
                        et_odeme_tarihi.setText(dat)


                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }


        //getLastLocation()
        alertadd.setView(view)
        alertadd.setPositiveButton(
            getString(R.string.evet)
        ) { dialogInterface, which ->
            e_FaturaOlustur(id,spinner_evrak_turu.selectedItem.toString(),
                et_evrak_tarihi.getText().toString(),
                et_sevk_tarihi.getText().toString(),
                et_odeme_tarihi.getText().toString(),
                spinner_kdv_turu.selectedItem.toString(),
                spinner_e_fatura_tipi.selectedItem.toString(),
                spinner_evrak_tipi.selectedItem.toString(),
                spinner_e_kontrol.selectedItem.toString(),
                spinner_plaka_yazdir.selectedItem.toString(),
                spinner_km_yazdir.selectedItem.toString(),
                spinner_no_yazdir.selectedItem.toString()






            )


        }
        alertadd.setNegativeButton(getString(R.string.hayir)){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()
    }
    fun e_FaturaOlustur(
        id: String,
        evrak_turu: String,
        evrak_tarihi: String,
        sevk_tarihi: String,
        odeme_tarihi: String,
        kdv_turu: String,
        fatura_tipi: String,
        evrak_tipi: String,
        e_kontrol: String,
        plakayazdir: String,
        kmyazdir: String,
        noyazdir: String

    ) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                val i= Intent(this,GidenFaturalarActivity::class.java)
                i.putExtra("firma_id",firma_id)
                i.putExtra("kadi",kadi)
                startActivity(i)

                Toast.makeText(this, getString(R.string.guncelemebasarili), Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI2")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["id"] = id
                params["kadi"] = kadi
                params["evrak_turu"] = evrak_turu
                params["evrak_tarihi"] = evrak_tarihi
                params["sevk_tarihi"] = sevk_tarihi
                params["odeme_tarihi"] = odeme_tarihi
                params["kdv_turu"] = kdv_turu
                params["fatura_tipi"] = fatura_tipi
                params["evrak_tipi"] = evrak_tipi
                params["e_kontrol"] = e_kontrol
                params["plakayazdir"] = plakayazdir
                params["kmyazdir"] = kmyazdir
                params["noyazdir"] = noyazdir
                params["tur"] = "e_fatura_guncelle"


                return params
            }
        }
        queue.add(postRequest)

    }
    private fun getUserData(context: Context) {
        val itemList: ArrayList<ItemModel> = ArrayList()


        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }
    private fun onBaslat() {
        rc_giden_faturalar=findViewById(R.id.rc_gelen_faturalar)
        btn_arama_imageview=findViewById(R.id.btn_arama_imageview)
        txttumu=findViewById(R.id.txttumu)
        kadi=intent.getStringExtra("kadi").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
    }
}
