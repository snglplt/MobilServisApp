package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
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
import com.selpar.pratikhasar.adapter.SesAdapter
import com.selpar.pratikhasar.adapter.TextAdapter
import com.selpar.pratikhasar.adapter.TextModel
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.SesModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IstekTextKayitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IstekTextKayitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btn_istek_yazi:Button
    lateinit var kadi: String
    lateinit var sifre: String
    lateinit var firma_id: String
    lateinit var plaka: String
    lateinit var ozel_id: String
    var itemList_usta:ArrayList<String> = ArrayList()
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>

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
        val view=inflater.inflate(R.layout.fragment_istek_text_kayit, container, false)
        btn_istek_yazi=view.findViewById(R.id.btn_istek_yazi)
        newRecyclerviewm=view.findViewById(R.id.rc_istekyazi)
        val args = this.arguments
        kadi = args?.getString(("kadi")).toString()
        ozel_id = args?.getString("ozel_id").toString()
        sifre = args?.getString("sifre").toString()
        firma_id = args?.getString("firma_id").toString()
        plaka = args?.getString("plaka").toString()
        Toast.makeText(requireContext(),kadi+ firma_id + ozel_id,Toast.LENGTH_LONG).show()
        text_kayit_bul(kadi,firma_id,ozel_id)
        // Inflate the layout for this fragment

        btn_istek_yazi.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle("İşçilik Giriniz!..")
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(R.layout.usta_sec_aciklama, null)
            val ediscilik=view.findViewById<EditText>(R.id.ediscilik)
            val spinner_usta=view.findViewById<Spinner>(R.id.spinner_usta)
            val urlsb = "&firma_id=" + firma_id
            var url = "https://pratikhasar.com/netting/mobil.php?tur=usta_getir" + urlsb
            Log.d("RESİMYOL",url)
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {

                        val json = response["ustalar"] as JSONArray
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            val cari = item.getString("usta")
                            itemList_usta.add(cari)
                        }

                        val adapter: ArrayAdapter<String> =
                            ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice, itemList_usta)

                        spinner_usta.setAdapter(adapter)

                    }catch (e:Exception){
                        Toast.makeText(requireContext(),"spinner doldurma hatası",Toast.LENGTH_LONG).show()}

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                    // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT) .show()
                }
            )
            queue.add(request)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "EKLE"
            ) { dialogInterface, which ->
                istekEkle(firma_id,plaka,ozel_id,ediscilik.getText().toString(),spinner_usta.selectedItem.toString())

            }
            alertadd.show()
        }
        return view
    }
    private fun istekEkle(firmaId: String, plaka: String, ozelId: String, iscilik: String, usta: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                text_kayit_bul(kadi,firma_id,ozel_id)
                Toast.makeText(requireContext(),"Ekleme başarılı",Toast.LENGTH_LONG).show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = firmaId
                params["ozel_id"] = ozelId
                params["plaka"] = plaka
                params["iscilik"] = iscilik
                params["usta"] = usta
                params["tur"] = "istek_iscilik_kaydet"
                return params
            }
        }
        queue.add(postRequest)
    }
    private fun text_kayit_bul(kadi: String, firma: String,  ozelId: String){
        val urlsb ="&kadi=" + kadi +"&firma_id="+firma+"&plaka="+ plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=istek_text_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["text"] as JSONArray

                    val itemList: ArrayList<TextModel> = ArrayList()
                    var sira=1
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)



                        val itemModel = TextModel(sira,
                            item.getString("id"),item.getString("usta"), item.getString("aciklama")
                        )
                        sira+=1

                        itemList.add(itemModel)
                    }
                    val adapter =
                        TextAdapter(itemList)

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

                    }}catch (e:Exception){}





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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(requireContext(),
                            R.color.blue))
                    .addSwipeLeftLabel("SİL")
                    //.addSwipeRightLabel("OYNAT")
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
                        val txtid= viewHolder.itemView.findViewById<TextView>(R.id.txtid).text



                        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                        builder.setTitle(getString(R.string.kayitsilinsinmi))
                        builder.setPositiveButton("Evet") { dialog, which ->
                            text_kayit_sil(txtid.toString())
                            //  resimGetir(kadi,ozel_id)
                            //

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                            text_kayit_bul(kadi,firma_id,ozel_id)
                        }
                        builder.show()

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }
    private fun text_kayit_sil(id: String)  {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                text_kayit_bul(kadi,firma_id,ozel_id)
                Toast.makeText(requireContext(),"Silme Başarılı: "+id, Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["id"] = id
                params["tur"] = "text_istek_sil"
                return params
            }
        }
        queue.add(postRequest)

    }
    private fun getUserData(context: Context) {
        val itemList: java.util.ArrayList<ItemModel> = java.util.ArrayList()

    }
}