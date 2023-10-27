package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.BLUE
import android.graphics.Color.blue
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.EvrakAdapter
import com.selpar.pratikhasar.adapter.EvrakGVAdapter
import com.selpar.pratikhasar.data.*
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.util.*
import android.os.Bundle as Bundle1


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EvraklarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
public class EvraklarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var SELECT_IMAGE = 12
    lateinit var imageUri: Uri
    lateinit var etdosya_yolu: EditText
    lateinit var image1: ImageView
    lateinit var linearForImage: LinearLayout
    lateinit var img1: ImageView
    lateinit var img2: ImageView
    lateinit var img3: ImageView
    lateinit var img4: ImageView
    lateinit var img5: ImageView
    lateinit var btnEvrakYukle: Button
    lateinit var btnCamera: Button
    lateinit var btnimgSil1: Button
    lateinit var btnimgSil2: Button
    lateinit var btnimgSil3: Button
    lateinit var btnimgSil4: Button
    lateinit var btnimgSil5: Button
    var GELEN_IMAGE = 1
    private val CAMERA_REQUEST = 1
    private val CEKILEN_IMAGE = 1
    lateinit var cekilen: Bitmap
    private val CHECK_BOX_STATE = "string"
    lateinit var kadi: String
    lateinit var sifre: String
    lateinit var firma_id: String
    lateinit var plaka: String
    lateinit var kabulnom: String
    lateinit var kullanici_id: String
    lateinit var ozel_id: String
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: ArrayList<Item>
    var yol = Array<String>(10) { "" }
    private var images: MutableList<Uri> = java.util.ArrayList()
    private var selectedImageUri: Uri? = null
    private var selectedImage: String? = null
    private lateinit var bitmap:Bitmap
    val args = this.arguments
    lateinit var spinner1 :Spinner
    lateinit var btn_ekle_evrak :Button
    lateinit var txt_evrakturu :TextView
    lateinit var evrak_turu :String
    lateinit var firma :String

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var coursesGV:GridView


    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle1?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_evraklar, container, false)
        coursesGV = view.findViewById(com.selpar.pratikhasar.R.id.idGVcourses)

        val args = this.arguments
        kadi = args?.getString(("kadi")).toString()
        ozel_id = args?.getString("ozel_id").toString()
        sifre = args?.getString("sifre").toString()
        firma_id = args?.getString("firma_id").toString()
        plaka = args?.getString("plaka").toString()
        kabulnom = args?.getString("kabulnom").toString()
        kullanici_id = args?.getString("kullanici_id").toString()
       //spinner1 = view.findViewById(R.id.spinner_evrak)
        image1 = view.findViewById(R.id.image1)
        image1.setImageDrawable(getResources().getDrawable(R.drawable.camera))
        img1 = view.findViewById(R.id.img1)
        img2 = view.findViewById(R.id.img2)
        img3 = view.findViewById(R.id.img3)
        img4 = view.findViewById(R.id.img4)
        img5 = view.findViewById(R.id.img5)
        btnimgSil1 = view.findViewById(R.id.btnimgSil1)
        btnimgSil2 = view.findViewById(R.id.btnimgSil2)
        btnimgSil3 = view.findViewById(R.id.btnimgSil3)
        btnimgSil4 = view.findViewById(R.id.btnimgSil4)
        btnimgSil5 = view.findViewById(R.id.btnimgSil5)

        btnCamera = view.findViewById(R.id.btnCamera)
        linearForImage = view.findViewById(R.id.linearForImage)
        /*swipeRefreshLayout =view.findViewById(R.id.container)
        swipeRefreshLayout.setOnRefreshListener {
            resimGetir()
            swipeRefreshLayout.isRefreshing = false
            resimGetir()
        }*/

        onBaslat()

        try{
            resimGetir(kadi,ozel_id)
        }
        catch (e:Exception){}
        image1.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
           // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST)

        }

        newRecyclerviewm = view.findViewById(R.id.resimcik)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        newRecyclerviewm.setLayoutManager(layoutManager);
        newRecyclerviewm.itemAnimator=DefaultItemAnimator()
        return view
    }


    fun deneme(){

            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle("EVRAK TÜRÜ SEÇİNİZ!..")
            val factory = LayoutInflater.from(requireContext())
           val view: View = factory.inflate(R.layout.evrak_turu, null)

          spinner1=view.findViewById<Spinner>(R.id.sp_evrak_turu)
        val alspinner1 = ArrayList<String>()
        val _spvalue1 = resources.getStringArray(R.array.evrak)
        for (i in _spvalue1.indices) {
            alspinner1.add(_spvalue1[i])
        }
        val adapter1: Any? = ArrayAdapter<Any?>(
            view.getContext(),
            android.R.layout.simple_spinner_item,
            alspinner1 as List<Any?>
        )
        spinner1.setAdapter(adapter1 as SpinnerAdapter?)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                getString(R.string.evet)
            ) { dialogInterface, which ->
                evrak_turu=spinner1.selectedItem.toString()
              //  Toast.makeText(requireContext(),"evrak turu "+evrak_turu,Toast.LENGTH_LONG).show()
                onApi()

            }
            alertadd.show()

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
                    val itemList: ArrayList<ItemModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        firma=item.getString("firma_id")
                        Log.d("FIRMA22: ", firma)
                        evrak_resim_getir(kadi,firma)
                        //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                        /*yol[i] = "https://pratikhasar.com/evrak/"+firma_id+"/"+ozel_id+"/"+item.getString("yol")
                        Log.d("urllll: ",yol[i] )
                        "https://pratikhasar.com/netting/" +
                                    item.getString("yol")
                        // Picasso.get().load(yol[i]).into(image1)
                        val deger="https://pratikhasar.com/netting/" +
                                item.getString("yol")*/
                        Log.d("RESIMGETTTT", yol[i])
                        val itemModel = ItemModel(
                            yol[i],item.getString("tur"),kadi,ozel_id
                        )


                        itemList.add(itemModel)


                    }
                    val adapter =
                        EvrakAdapter(itemList)

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
                //Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

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
               // Toast.makeText(context, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun evrak_resim_bul(kadi: String, firma: String, kabulnom: String, ozel_id: String)
 {
        val urlsb ="&kadi=" + kadi +"&firma_id="+firma+"&kabulnom="+kabulnom+"&ozel_id="+ this.ozel_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=evrak_resim_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                val json = response["resim"] as JSONArray
                    val courseModelArrayList: ArrayList<BitmisModel> = ArrayList<BitmisModel>()

                val itemList: ArrayList<ItemModel> = ArrayList()
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)

                    yol[i] ="https://pratikhasar.com/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                   // yol[i] ="https://pratikhasar.com/netting/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                    //yol[i] ="https://pratikhasar.com/netting/"+item.getString("yol")

                    Log.d("YOLLL: ",yol[i])


                    val itemModel = ItemModel(
                        yol[i],item.getString("tur"),kadi, this.ozel_id
                    )

                    courseModelArrayList.add(BitmisModel(item.getString("id"),item.getString("tur"),yol[i],kadi,ozel_id,plaka,firma_id))

                    val adapter2 = EvrakGVAdapter(requireContext(), courseModelArrayList)
                    coursesGV.adapter = adapter2
                    itemList.add(itemModel)
                    val adapter =
                        EvrakAdapter(itemList)

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
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(),
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
                        val txt_evrakturu= viewHolder.itemView.findViewById<TextView>(R.id.txt_evrakturu).text
                        val txt_image= viewHolder.itemView.findViewById<TextView>(R.id.txt_yol).text
                        var yol=txt_image.toString().replace("https://pratikhasar.com/evrak/$firma/$kabulnom/","")

                        val donustur_yol=txt_image.subSequence(45, txt_image.length).toString()
                        //val donustur_yol=txt_image.subSequence(32, txt_image.length).toString()
                        Log.d("DONUSTUR: ",yol)
                      //  Toast.makeText(requireContext(),"dONUSTU:"+yol,Toast.LENGTH_SHORT).show()


                        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                                                builder.setTitle("Kayıt silinsin mi?")
                        builder.setPositiveButton("Evet") { dialog, which ->
                            evrak_sil(txt_evrakturu.toString(),yol)
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
                        val txt_evrakturu= viewHolder.itemView.findViewById<TextView>(R.id.txt_evrakturu).text
                        val txt_image= viewHolder.itemView.findViewById<TextView>(R.id.txt_yol).text
                        var yol=txt_image.toString().replace("https://pratikhasar.com/evrak/$firma/$kabulnom/","")

                        val donustur_yol=txt_image.subSequence(45, txt_image.length).toString()

                       // val donustur_yol=txt_image.subSequence(32, txt_image.length).toString()
                        Log.d("DONUSTUR: ",donustur_yol)
                        guncelle_evrak_turu(txt_evrakturu.toString(),yol)
                        resimGetir(kadi,ozel_id)
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
    private fun evrak_sil(txtEvrakturu: String, donusturYol: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                resimGetir(kadi,firma)
                Toast.makeText(requireContext(),"Silme Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["firma_id"] = firma_id
                params["ozel_id"] = ozel_id
                params["plaka"]=plaka
                params["olay"]="Evrak Resim Bilgisi"
                params["yol"] = donusturYol
                params["evrak_turu"] = txtEvrakturu
                params["tur"] = "evrak_resim_sil"
                return params
            }
        }
        queue.add(postRequest)

    }

    private fun guncelle_evrak_turu(txtEvrakturu: String, donusturYol: String) {

        val alertadd = AlertDialog.Builder(requireContext())
        alertadd.setTitle("EVRAK TÜRÜ GÜNCELLENSİN Mİ?")
        val factory = LayoutInflater.from(requireContext())
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.evrak_turu, null)

        spinner1=view.findViewById<Spinner>(com.selpar.pratikhasar.R.id.sp_evrak_turu)
        val alspinner1 = ArrayList<String>()
        val _spvalue1 = this.resources.getStringArray(com.selpar.pratikhasar.R.array.evrak)
        for (i in _spvalue1.indices) {
            alspinner1.add(_spvalue1[i])
        }
        val adapter1: Any? = ArrayAdapter<Any?>(
            view.getContext(),
            android.R.layout.simple_spinner_item,
            alspinner1 as List<Any?>
        )
        spinner1.setAdapter(adapter1 as SpinnerAdapter?)
        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EVET"
        ) { dialogInterface, which ->
            evrak_turu=spinner1.selectedItem.toString()
           // Toast.makeText(requireContext(),"evrak turu "+evrak_turu,Toast.LENGTH_LONG).show()
            guncelleme_yapti(txtEvrakturu,donusturYol)


        }
        alertadd.setNegativeButton("Hayır"){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()

    }

    private fun guncelleme_yapti(txtEvrakturu: CharSequence?, donusturYol: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                resimGetir(kadi,firma)
                Toast.makeText(requireContext(),"Güncelleme Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["yol"] = donusturYol
                params["evrak_turu"] = evrak_turu
                params["kadi"] = kadi
                params["evrak_turu"]=evrak_turu
                params["ozel_id"] = ozel_id
                params["firma_id"]=firma_id
                params["plaka"]=plaka
                params["olay"]="Evrak Resim Bilgisi"
                params["tur"] = "evrak_resim_guncelle"
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
    private fun onApi() {
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
                params["evrak_turu"]=evrak_turu
                params["ozel_id"] = ozel_id
                params["plaka"]=plaka
                params["olay"]="Evrak Resim Bilgisi"
                params["tur"] = "evrak_resim_kaydet"
                return params
            }
        }
        queue.add(postRequest)
    }
    fun ImageToString() : String {
        val byteArrayOutputsStream=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte,Base64.DEFAULT)
    }
    private fun onBaslat() {
        img1.visibility = View.INVISIBLE
        img2.visibility = View.INVISIBLE
        img3.visibility = View.INVISIBLE
        img4.visibility = View.INVISIBLE
        img5.visibility = View.INVISIBLE
        btnimgSil1.visibility = View.INVISIBLE
        btnimgSil2.visibility = View.INVISIBLE
        btnimgSil3.visibility = View.INVISIBLE
        btnimgSil4.visibility = View.INVISIBLE
        btnimgSil5.visibility = View.INVISIBLE

    }
    fun getImageUri(context: Context, bitmap: Bitmap?): Uri? {
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "myImage", "")
        return Uri.parse(path)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:  Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (resultCode == RESULT_OK && requestCode == SELECT_IMAGE) {
            imageUri = data?.data!!
           val inputStream = requireActivity().contentResolver.openInputStream(imageUri)

           bitmap = BitmapFactory.decodeStream(inputStream)

           image1.setImageURI(imageUri)
           SELECT_IMAGE += 1
           deneme()
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data!=null)
        {

            bitmap= data?.extras?.get("data") as Bitmap
            image1.setImageBitmap(bitmap)
            alert()
        }
    }

    private fun alert() {
        deneme()

        image1.setImageDrawable(getResources().getDrawable(R.drawable.camera));

    }
    private fun resimSec() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, SELECT_IMAGE)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EvraklarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EvraklarFragment().apply {
                arguments = Bundle1().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


