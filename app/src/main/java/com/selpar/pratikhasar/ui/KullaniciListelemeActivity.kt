package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
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
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.KullaniciItem
import com.selpar.pratikhasar.adapter.KullaniciStokAdapter
import com.selpar.pratikhasar.adapter.StokAdapter
import com.selpar.pratikhasar.data.StokItem
import com.squareup.picasso.Picasso
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.HashMap

class KullaniciListelemeActivity : AppCompatActivity() {
    lateinit var rc_kullanici_listele:RecyclerView
    lateinit var id:String
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    lateinit var linear_guncelle: LinearLayout
    lateinit var image:ImageView
    lateinit var et_ad:EditText
    lateinit var et_tel:EditText
    lateinit var et_mail:EditText
    lateinit var et_soyad:EditText
    lateinit var et_brans:EditText
    lateinit var et_sifre:EditText
    lateinit var btn_guncelle:Button
    lateinit var spinner_brans:Spinner
    var itemList_brans:ArrayList<String> = ArrayList()
    var CAMERA_REQUEST=1
    var GALERY_REQUEST=2
    private lateinit var bitmap: Bitmap

    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_listeleme)
        onBaslat()
        bransDoldur()
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


        btn_guncelle.setOnClickListener {
            val bitmapDrawable=image.drawable as BitmapDrawable
            bitmap=bitmapDrawable.bitmap as Bitmap
            guncelle_kullanici()
            onKayitGetir()
        }
        image.setOnClickListener {
            //bitmap = image.getDrawable() as Bitmap
           // bitmap = Bitmap.createBitmap(image.getDrawingCache());
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.uyari)
            builder.setMessage("Nereden resim yükleyeceksiniz?")

//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(R.string.kamera) { dialog, which ->
                cekResim()

            }

            builder.setNegativeButton(R.string.galeri) { dialog, which ->
                galeridensec()

            }


            builder.show()


        }

    }
    private fun galeridensec() {
        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*" //allow any image file type.

        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(gallery, GALERY_REQUEST)
    }
    // Load an image file into a Bitmap object
    fun loadImage(file: String): Bitmap {
        // Decode the image file to a Bitmap object
        val bitmap = BitmapFactory.decodeFile(file)

        // Return the Bitmap object
        return bitmap
    }
    private fun cekResim() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // cameraIntent.setType("image/*")
        startActivityForResult(cameraIntent,CAMERA_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:  Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image.setImageBitmap(bitmap)
            //deneme()
           // image.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        }
        if (requestCode == GALERY_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val inputStream = applicationContext.contentResolver.openInputStream(imageUri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            image.setImageBitmap(bitmap)
            //deneme()
            //image.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        }
    }

    private fun bransDoldur(){
        val url ="https://pratikhasar.com/netting/mobil.php?tur=brans_getir"

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["brans"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_brans.add(item.getString("brans").toString())

                    }

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_brans.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_brans[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        this,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_brans.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {
                   /* Toast.makeText(this, "MODEL doldur hatası", Toast.LENGTH_LONG)
                        .show()*/
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

    fun ImageToString() : String {
        val byteArrayOutputsStream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }
    private fun guncelle_kullanici() {

        val image=ImageToString()

        val queue = Volley.newRequestQueue(applicationContext)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                linear_guncelle.visibility=GONE
                rc_kullanici_listele.visibility= VISIBLE
                onKayitGetir()
                Toast.makeText(applicationContext,"Güncelleme Başarılı: "+intent.getStringExtra("firma_id"),Toast.LENGTH_SHORT).show() },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id"] = id
                params["firma_id"] = intent.getStringExtra("firma_id").toString()
                params["ad"] = et_ad.getText().toString()
                params["soyad"] = et_soyad.getText().toString()
                params["tel"]= et_tel.getText().toString()
                params["kadi"]=et_mail.getText().toString()
                params["sifre"]=et_sifre.getText().toString()
                params["resim"]=image
                params["brans"]=spinner_brans.selectedItem.toString()
                params["tur"] = "kullanici_kayit_guncelle"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun getUserData(context: Context) {

        val itemList: java.util.ArrayList<StokItem> = java.util.ArrayList()

        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }
    private fun onKayitGetir()  {
        rc_kullanici_listele.visibility= VISIBLE
        val urlsb ="&firma_id="+intent.getStringExtra("firma_id").toString()
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kullanici_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["kullanici"] as JSONArray

                    val itemList: ArrayList<KullaniciItem> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        val yol="https://pratikhasar.com/firmalar/"+intent.getStringExtra("firma_id")+"/"+item.getString("resim")
                       // bitmap=loadImage(yol)
                        val itemModel = KullaniciItem(
                            item.getString("id"),
                            item.getString("kullanici_ad"),item.getString("kullanici_soyad"),
                            item.getString("tel"),item.getString("mail"),
                            item.getString("brans"),yol

                        )


                        itemList.add(itemModel)
                        val adapter =
                            KullaniciStokAdapter(itemList)

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
                        val id= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text


                        val builder = AlertDialog.Builder(this@KullaniciListelemeActivity, R.style.AlertDialogCustom)
                        builder.setTitle("Kayıt silinsin mi?")
                        builder.setPositiveButton("Evet") { dialog, which ->

                            kullanici_sil(id.toString())
                            onKayitGetir()
                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            onKayitGetir()
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
                        rc_kullanici_listele.visibility=GONE
                        id= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text.toString()
                        val adsoyad= viewHolder.itemView.findViewById<TextView>(R.id.txtad_soyad).text
                        val tel= viewHolder.itemView.findViewById<TextView>(R.id.txt_tel).text
                        val mail= viewHolder.itemView.findViewById<TextView>(R.id.txt_mail).text
                        val brans= viewHolder.itemView.findViewById<TextView>(R.id.txt_mail).text
                        val resim= viewHolder.itemView.findViewById<TextView>(R.id.image_path).text

                        guncelle_kullanici_kaydi(id)
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

    private fun guncelle_kullanici_kaydi(id: String) {
        linear_guncelle.visibility=VISIBLE
        val urlsb ="&firma_id="+intent.getStringExtra("firma_id").toString()+"&id="+id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kullanici_bul_id"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["kullanici"] as JSONArray
                     itemList_brans.clear()
                    val itemList: ArrayList<KullaniciItem> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        val yol="https://pratikhasar.com/firmalar/"+intent.getStringExtra("firma_id")+"/"+item.getString("resim")
                        Picasso.get().load(yol).into(image)
                        et_ad.setText(item.getString("kullanici_ad"))
                        et_soyad.setText(item.getString("kullanici_soyad"))
                        et_tel.setText(item.getString("tel"))
                        et_mail.setText(item.getString("mail"))
                        //et_brans.setText(item.getString("brans"))
                        itemList_brans.add(item.getString("brans"))
                        et_sifre.setText(item.getString("sifre"))
                        bransDoldur()



                     }}catch (e:Exception){}





            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
            }
        )
        queue.add(request)

    }

    private fun kullanici_sil(id: String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                onKayitGetir()
                Toast.makeText(this,"Silme Başarılı: ", Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id"] = id
                params["tur"] = "kullanici_kayit_sil"
                return params
            }
        }
        queue.add(postRequest)

    }


    private fun onBaslat() {
        rc_kullanici_listele=findViewById(R.id.rc_kullanici_listele)
        newRecyclerviewm=findViewById(R.id.rc_kullanici_listele)
        linear_guncelle=findViewById(R.id.linear_guncelle)
        btn_guncelle=findViewById(R.id.btn_guncelle)
        image=findViewById(R.id.image)
        et_ad=findViewById(R.id.et_ad)
        et_soyad=findViewById(R.id.et_soyad)
        et_tel=findViewById(R.id.et_tel)
        et_mail=findViewById(R.id.et_mail)
        spinner_brans=findViewById(R.id.spinner_brans)
        et_sifre=findViewById(R.id.et_sifre)
    }
}