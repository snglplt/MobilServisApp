package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.selpar.pratikhasar.adapter.OlayYeriAdapter
import com.selpar.pratikhasar.adapter.YapiAsamasiGVAdapter
import com.selpar.pratikhasar.adapter.YapimAsamasiAdapter
import com.selpar.pratikhasar.data.BitmisGVAdapter
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.ItemModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.io.ByteArrayOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YapimAsamasiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YapimAsamasiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var image1: ImageView
    lateinit var firma:String
    lateinit var btn_resim_cek: Button
    private val CAMERA_REQUEST = 1
    private lateinit var bitmap: Bitmap
    lateinit var kadi: String
    lateinit var ozel_id: String
    lateinit var plaka: String
    lateinit var firma_id: String
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    var yol = Array<String>(10) { "" }
    lateinit var coursesGV: GridView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_yapim_asamasi, container, false)
        image1 = view.findViewById(R.id.image_yapi_asamasi)
        image1.setImageDrawable(getResources().getDrawable(R.drawable.camera))
        coursesGV = view.findViewById(com.selpar.pratikhasar.R.id.idGVcourses)

        newRecyclerviewm=view.findViewById(R.id.rc_yapim_asamasi)
        val args = this.arguments
        kadi = args?.getString(("kadi")).toString()
        ozel_id = args?.getString("ozel_id").toString()
        plaka = args?.getString("plaka").toString()
        firma_id = args?.getString("firma_id").toString()
        image1.setOnClickListener {
            cekResim()
            image1.setImageDrawable(getResources().getDrawable(R.drawable.camera))

        }
        resimGetir(kadi,ozel_id)
        // Inflate the layout for this fragment
        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:  Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image1.setImageBitmap(bitmap)
            onApi()
            cekResim()
            image1.setImageDrawable(getResources().getDrawable(R.drawable.camera))



        }
    }

    private fun cekResim() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // cameraIntent.setType("image/*")
        startActivityForResult(cameraIntent,CAMERA_REQUEST)
    }

    fun ImageToString() : String {
        val byteArrayOutputsStream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }
    private fun resimGetir(kadi: String, ozel_id: String) {

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
                    val itemList: java.util.ArrayList<ItemModel> = java.util.ArrayList()
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
                        OlayYeriAdapter(itemList)

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
                        yapim_resim_bul(kadi, firma, kabulnom, ozel_id)

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

    private fun yapim_resim_bul(kadi: String, firma: String, kabulnom: String, ozelId: String) {
        val urlsb ="&kadi=" + kadi +"&firma_id="+firma+"&kabulnom="+kabulnom+"&ozel_id="+ this.ozel_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=yapim_resim_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["resim"] as JSONArray
                    val courseModelArrayList: ArrayList<BitmisModel> = ArrayList<BitmisModel>()

                    val itemList: java.util.ArrayList<ItemModel> = java.util.ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        yol[i] ="https://pratikhasar.com/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        // yol[i] ="https://pratikhasar.com/netting/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        //yol[i] ="https://pratikhasar.com/netting/"+item.getString("yol")

                        Log.d("YOLLL: ",yol[i])


                        val itemModel = ItemModel(
                            yol[i],item.getString("tur"),kadi, this.ozel_id
                        )

                        courseModelArrayList.add(BitmisModel(item.getString("id"),"",yol[i],kadi,ozel_id, plaka , firma_id ))

                        val adapter2 = YapiAsamasiGVAdapter(requireContext(), courseModelArrayList)
                        coursesGV.adapter = adapter2
                        itemList.add(itemModel)
                        val adapter =
                            YapimAsamasiAdapter(itemList)

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
                ItemTouchHelper.LEFT
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
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(requireContext(),
                            com.selpar.pratikhasar.R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(requireContext(),
                            com.selpar.pratikhasar.R.color.green))
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
                        //  val txt_evrakturu= viewHolder.itemView.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_evrakturu).text
                        val txt_image= viewHolder.itemView.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_yol_yapim_asamasi).text
                        val donustur_yol=txt_image.subSequence(45, txt_image.length).toString()
                        var yol=txt_image.toString().replace("https://pratikhasar.com/evrak/$firma/$kabulnom/","")

                        //val donustur_yol=txt_image.subSequence(32, txt_image.length).toString()
                        Log.d("DONUSTUROLAY: ",donustur_yol)
                    //    Toast.makeText(requireContext(),"dONUSTU:"+donustur_yol,Toast.LENGTH_SHORT).show()


                        val builder = AlertDialog.Builder(requireContext(),R.style.AlertDialogCustom)
                        builder.setTitle("Kayıt silinsin mi?")
                        builder.setPositiveButton("Evet") { dialog, which ->
                            yapim_asamasi_sil(yol)
                            resimGetir(kadi,ozel_id)
                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            resimGetir(kadi,ozel_id)
                        }
                        builder.show()



                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }

    private fun yapim_asamasi_sil(donusturYol: String){
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                resimGetir(kadi,firma)
                //Toast.makeText(requireContext(),"Silme Başarılı: "+kadi,Toast.LENGTH_SHORT).show()
                resimGetir(kadi,firma)

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["yol"] = donusturYol
                params["tur"] = "yapim_resim_sil"
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
        val postRequest: StringRequest = @SuppressLint("ResourceType")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                resimGetir(kadi, ozel_id)
                val toast = Toast.makeText(
                    requireContext(),
                    "Resim Eklendi",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0)
                toast.show()

                /*    Toast.makeText(requireContext(), "Kayıt Başarılıdır", Toast.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(), "KADI "+kadi, Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(), "IMAGE "+image, Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(), "OZEL ID "+ozel_id, Toast.LENGTH_LONG).show()*/

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["yol"] = image
                params["ozel_id"] = ozel_id
                params["plaka"] = plaka
                params["firma_id"] = firma_id
                params["olay"] = "Yapım Aşaması Resim"
                params["tur"] = "yapim_resim_kaydet"
                return params
            }
        }
        queue.add(postRequest)



    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YapimAsamasiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YapimAsamasiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}