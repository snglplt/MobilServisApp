package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.IscilikResimAdapter
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.ItemTespitModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.util.ArrayList
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FiyatlandirFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiyatlandirFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var image_olay_yeri: ImageView
    var CAMERA_REQUEST=1
    var GALERY_REQUEST=2
    private lateinit var bitmap: Bitmap
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var firma_id:String
    lateinit var plaka:String
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    var yol = Array<String>(10) { "" }
    lateinit var txt_bilgi: EditText
    lateinit var txt_miktar: EditText
    lateinit var txt_fiyat: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_fiyatlandir, container, false)
        val args=this.arguments
        kadi=args?.getString("kadi").toString()
        ozel_id=args?.getString("ozel_id").toString()
        firma_id=args?.getString("firma_id").toString()
        plaka=args?.getString("plaka").toString()
        image_olay_yeri=view.findViewById(R.id.image_olay_yeri)
        image_olay_yeri.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.uyari)
            builder.setMessage("Nereden resim yükleyeceksiniz?")

//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(R.string.kamera) { dialog, which ->
                cekResim()
                Toast.makeText(
                    requireContext(),
                    R.string.kamera, Toast.LENGTH_SHORT
                ).show()
            }

            builder.setNegativeButton(R.string.galeri) { dialog, which ->
                galeridensec()
                Toast.makeText(
                    requireContext(),
                    R.string.galeri, Toast.LENGTH_SHORT
                ).show()
            }


            builder.show()


        }
        newRecyclerviewm = view.findViewById(R.id.rc_olay_yeri)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        newRecyclerviewm.setLayoutManager(layoutManager);
        newRecyclerviewm.itemAnimator= DefaultItemAnimator()
        resimGetir(kadi,ozel_id)
        image_olay_yeri.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        // Inflate the layout for this fragment
        return view
    }
    private fun galeridensec() {
        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*" //allow any image file type.

        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(gallery, GALERY_REQUEST)
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
            image_olay_yeri.setImageBitmap(bitmap)
            deneme()
            image_olay_yeri.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        }
        if (requestCode == GALERY_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val inputStream = requireContext().contentResolver.openInputStream(imageUri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            image_olay_yeri.setImageBitmap(bitmap)
            deneme()
            image_olay_yeri.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        }
    }
    @SuppressLint("MissingInflatedId")
    fun deneme(){

        val alertadd = AlertDialog.Builder(requireContext())
        alertadd.setTitle("OLAY TESPİT AÇIKMASI GİRİNİZ!..")
        val factory = LayoutInflater.from(requireContext())
        val view: View = factory.inflate(R.layout.tespit_resim_aciklamasi, null)
        txt_bilgi=view.findViewById<EditText>(R.id.txtaciklama)
        txt_miktar=view.findViewById<EditText>(R.id.etMiktar)
        txt_fiyat=view.findViewById<EditText>(R.id.etFiyat)


        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EKLE"
        ) { dialogInterface, which ->
            onApi(txt_bilgi.getText().toString(),txt_miktar.getText().toString(),txt_fiyat.getText().toString())

        }
        alertadd.show()

    }
    private fun onApi(aciklama: String, miktar: String, fiyat: String) {
        val image=ImageToString()
        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                resimGetir(kadi,ozel_id)


            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["yol"] = image
                params["evrak_turu"]=aciklama
                params["ozel_id"] = ozel_id
                params["plaka"] = plaka
                params["miktar"]=miktar
                params["fiyat"]=fiyat
                params["olay"]="Evrak Bilgileri"
                params["tur"] = "tespit_resim_iscilik_kaydet"
                return params
            }
        }
        queue.add(postRequest)
    }
    fun ImageToString() : String {
        val byteArrayOutputsStream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }

    fun resimGetir(kadi:String,ozel_id:String) {

        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=firma_id_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this.requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["firma_id"] as JSONArray
                    val itemList: ArrayList<ItemTespitModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        firma_id=item.getString("firma_id")
                        evrak_resim_getir(kadi,firma_id)
                        //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                        /*yol[i] = "https://pratikhasar.com/evrak/"+firma_id+"/"+ozel_id+"/"+item.getString("yol")
                        Log.d("urllll: ",yol[i] )
                        "https://pratikhasar.com/netting/" +
                                    item.getString("yol")
                        // Picasso.get().load(yol[i]).into(image1)
                        val deger="https://pratikhasar.com/netting/" +
                                item.getString("yol")*/
                        Log.d("RESIMGETTTT", yol[i])
                        val itemModel = ItemTespitModel(
                            yol[i],item.getString("tur"),
                            kadi,
                            this.ozel_id,
                            item.getString("miktar"),
                            item.getString("fiyat"))



                        itemList.add(itemModel)


                    }
                    val adapter =
                        IscilikResimAdapter(itemList)

                    newRecyclerviewm.adapter= adapter
                    newRecyclerviewm.layoutManager= LinearLayoutManager(context)
                    newRecyclerviewm.setHasFixedSize(false)
                    newArrayList= arrayListOf<ClipData.Item>()
                    context?.let {
                        getUserData(it)

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        queue.add(request)

    }
    private fun tespit_sil(donusturYol: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                resimGetir(kadi,firma_id)
                Toast.makeText(requireContext(),"Silme Başarılı: "+kadi, Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["yol"] = donusturYol
                params["tur"] = "tespit_resim_iscilik_sil"
                return params
            }
        }
        queue.add(postRequest)

    }

    private fun guncelle_tespit_alert(
        donusturYol: String,
        miktar2: CharSequence,
        fiyat2: CharSequence,
        aciklama2: CharSequence
    ) {

        val alertadd = AlertDialog.Builder(requireContext())
        alertadd.setTitle("OLAY TESPİT GÜNCELLENSİN Mİ?")
        val factory = LayoutInflater.from(requireContext())
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.tespit_resim_aciklamasi, null)
        val miktar=view.findViewById<EditText>(R.id.etMiktar)
        val fiyat=view.findViewById<EditText>(R.id.etFiyat)
        val aciklama=view.findViewById<EditText>(R.id.txtaciklama)
        if(miktar2.toString()==""){
            miktar.setText("1")
        }else{
            miktar.setText(miktar2.toString())
        }
        if(fiyat2.toString()==""){
            fiyat.setText("0")
        }else{
            fiyat.setText(fiyat2.toString())

        }
        if(aciklama2.toString()==""){
            aciklama.setText("")
        }else{
            aciklama.setText(aciklama2.toString())

        }
        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EVET"
        ) { dialogInterface, which ->
            //   Toast.makeText(requireContext(),"evrak turu "+aciklama.getText().toString(),Toast.LENGTH_LONG).show()
            guncelle_tespit(miktar.getText().toString(),fiyat.getText().toString(),aciklama.getText().toString(),donusturYol)


        }
        alertadd.setNegativeButton("Hayır"){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()

    }

    private fun guncelle_tespit(
        miktar: String,
        fiyat: String,
        aciklama: String,
        yol: String
    ){

        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                resimGetir(kadi,firma_id)
                Toast.makeText(requireContext(),"Güncelleme Başarılı: "+kadi, Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["yol"] = yol
                params["evrak_turu"] = aciklama
                params["miktar"]=miktar
                params["fiyat"]=fiyat
                params["tur"] = "tespit_resim_iscilik_guncelle"
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
    private fun evrak_resim_getir(kadi: String, firma: String) {
        val urlsb ="&kadi=" + kadi+ "&ozel_id="+ozel_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kabul_no_getir"+urlsb
        Log.d("KABULNOOOO. ",url)
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                    response ->
                try{
                    val json = response["bilgi"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val kabulnom = item.getString("kabulnom").toString()
                        Log.d("kabulnom: ", kabulnom)
                        evrak_resim_bul(kadi, firma, kabulnom, ozel_id)

                    }
                }catch (e:Exception){}


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(context, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun evrak_resim_bul(kadi: String, firma: String, kabulnom: String, ozel_id: String) {
        val urlsb ="&kadi=" + kadi +"&firma_id="+firma+"&kabulnom="+kabulnom+"&ozel_id="+ this.ozel_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=tespit_resim_iscilik_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["resim"] as JSONArray

                    val itemList: ArrayList<ItemTespitModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        yol[i] ="https://pratikhasar.com/tespit/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        // yol[i] ="https://pratikhasar.com/netting/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        //yol[i] ="https://pratikhasar.com/netting/"+item.getString("yol")

                        Log.d("YOLLL: ",yol[i])


                        val itemModel = ItemTespitModel(
                            yol[i],item.getString("tur"),
                            kadi,
                            this.ozel_id,
                            item.getString("miktar"),
                            item.getString("fiyat"))

                        //  Picasso.get().load(yol[i]).into(image_olay_yeri)
                        itemList.add(itemModel)
                        val adapter =
                            IscilikResimAdapter(itemList)

                        newRecyclerviewm.adapter= adapter
                        newRecyclerviewm.layoutManager= LinearLayoutManager(context)
                        newRecyclerviewm.setHasFixedSize(false)
                        newArrayList= arrayListOf<ClipData.Item>()
                        //val kabulnom = item.getString("kabulnom").toString()
                        // Log.d("kabulnom: ", kabulnom)
                        //  evrak_resim_bul(kadi,firma,kabulnom)
                        // evrak_resim_getir(kadi,firma)
                        //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                        context?.let {
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(requireContext(),
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
                        val txt_image= viewHolder.itemView.findViewById<TextView>(R.id.image_path).text
                        val donustur_yol=txt_image.subSequence(46, txt_image.length).toString()
                        var yol=txt_image.toString().replace("https://pratikhasar.com/tespit/$firma/$kabulnom/","")

                        //val donustur_yol=txt_image.subSequence(32, txt_image.length).toString()
                        Log.d("DONUSTUR: ",yol)
                        // Toast.makeText(requireContext(),"dONUSTU:"+yol,Toast.LENGTH_SHORT).show()


                        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                        builder.setTitle("Kayıt silinsin mi?")
                        builder.setPositiveButton("Evet") { dialog, which ->
                            tespit_sil(yol)
                            resimGetir(kadi,ozel_id)
                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            resimGetir(kadi,ozel_id)
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
                        //     val txt_evrakturu= viewHolder.itemView.findViewById<TextView>(R.id.txt_evrakturu).text
                        val txt_image= viewHolder.itemView.findViewById<TextView>(R.id.image_path).text
                        val donustur_yol=txt_image.subSequence(46, txt_image.length).toString()
                        var yol=txt_image.toString().replace("https://pratikhasar.com/tespit/$firma/$kabulnom/","")
                        // val donustur_yol=txt_image.subSequence(32, txt_image.length).toString()
                        val miktar=viewHolder.itemView.findViewById<TextView>(R.id.txt_miktar).text
                        val fiyat=viewHolder.itemView.findViewById<TextView>(R.id.txt_fiyat).text
                        val aciklama=viewHolder.itemView.findViewById<TextView>(R.id.txt_aciklama).text
                        Log.d("DONUSTUR: ",donustur_yol)
                        guncelle_tespit_alert(yol,miktar,fiyat,aciklama)
                        resimGetir(kadi,ozel_id)
                        // Toast.makeText(requireContext(),"dONUSTU:"+yol,Toast.LENGTH_SHORT).show()

                        // Toast.makeText(requireContext(),"txt_evrakturu: "+txt_evrakturu,Toast.LENGTH_SHORT).show()
                        //  Toast.makeText(requireContext(),"txt_image: "+yol,Toast.LENGTH_SHORT).show()

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


}