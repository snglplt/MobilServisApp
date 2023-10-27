package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.OnarimAdapter
import com.selpar.pratikhasar.data.OnarimModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FaturaGetirFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FaturaGetirFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val itemList_vrs : ArrayList<String> = ArrayList()
    lateinit var spinner_kdv:Spinner
    lateinit var etStokNo:EditText
    lateinit var etStokAdi:EditText
    lateinit var etMiktar:EditText
    lateinit var etFiyat:EditText
    lateinit var btn_onarim_ekle:Button
    lateinit var btn_fatura_guncelle:Button
    lateinit var txtToplamFiyat:TextView
    lateinit var stokNo:String
    lateinit var stokAdi:String
    var miktari:Int?=null
    var fiyati:Float?=null
    lateinit var spinner_evrak_turu :Spinner
    var toplam:Float=0.0f
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    lateinit var btncari:Button
    lateinit var btnfatura:Button



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
        val view=inflater.inflate(R.layout.fragment_fatura_getir, container, false)
        // Inflate the layout for this fragment
        spinner_kdv=view.findViewById(R.id.spinner_kdv)
        etStokNo=view.findViewById(R.id.etStokNo)
        etStokAdi=view.findViewById(R.id.etStokAdi)
        etMiktar=view.findViewById(R.id.etMiktar)
        etFiyat=view.findViewById(R.id.etFiyat)
        txtToplamFiyat=view.findViewById(R.id.txtToplamFiyat)
        txtToplamFiyat.setText("0")
        btncari=view.findViewById(R.id.btncari)
        btnfatura=view.findViewById(R.id.btnfatura)
        btncari.setBackgroundColor(Color.GREEN)
        btnfatura.setBackgroundColor(0)
        btn_onarim_ekle=view.findViewById(R.id.btn_onarim_ekle)
        btn_fatura_guncelle=view.findViewById(R.id.btn_fatura_guncelle)
        kdv_getir()
        btn_onarim_ekle.setOnClickListener {
            var miktar=etMiktar.getText().toString().toFloat()
            var fiyat=etFiyat.getText().toString().toFloat()
            //etStokNo1.getText().toString().isNotEmpty() &&
            if( etStokAdi.getText().toString().isNotEmpty()
                && etMiktar.getText().toString().isNotEmpty() && etFiyat.getText().toString().isNotEmpty()  ){
                stokNo=etStokNo.getText().toString()
                stokAdi=etStokAdi.getText().toString()
                val alertadd = AlertDialog.Builder(requireContext())
                alertadd.setTitle("PARÇA TÜRÜ SEÇİNİZ!..")
                val factory = LayoutInflater.from(requireContext())
                val view: View = factory.inflate(R.layout.evrak_turu, null)

                spinner_evrak_turu=view.findViewById<Spinner>(R.id.sp_evrak_turu)
                val alspinner1 = java.util.ArrayList<String>()
                val _spvalue1 = resources.getStringArray(R.array.parca_turu)
                for (i in _spvalue1.indices) {
                    alspinner1.add(_spvalue1[i])
                }
                val adapter1: Any? = ArrayAdapter<Any?>(
                    view.getContext(),
                    android.R.layout.simple_spinner_item,
                    alspinner1 as List<Any?>
                )
                spinner_evrak_turu.setAdapter(adapter1 as SpinnerAdapter?)
                alertadd.setView(view)
                alertadd.setPositiveButton(
                    "EKLE"
                ) { dialogInterface, which ->
                    //evrak_turu=spinner1.selectedItem.toString()
                    onApi(stokNo,stokAdi, miktar.toFloat(),fiyat,spinner_evrak_turu.selectedItem.toString())

                }
                alertadd.show()




                sifirla()

            }
            else{
                Toast.makeText(requireContext(),"Lütfen tüm alanları doldurun!.. ",Toast.LENGTH_LONG).show()
            }

        }
        return view
    }
    private fun onApi(stokNo: String, stokAdi: String, miktar: Float, fiyat: Float, parcaturu: String) {
        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                kayit_getir()
                sifirla()
                //onarimGetir()
                val toast = Toast.makeText(
                    requireContext(),
                    "Onarım Eklendi",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0)
                toast.show()
                /* Toast.makeText(requireContext(),"kadi "+kadi,Toast.LENGTH_LONG).show()
                 Toast.makeText(requireContext(),"ozel_id "+ozel_id,Toast.LENGTH_LONG).show()
                // Toast.makeText(requireContext(),"sifre "+sifre,Toast.LENGTH_LONG).show()
                 Toast.makeText(requireContext(),"plaka "+plaka,Toast.LENGTH_LONG).show()*/
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()

                params["stok_iscilik_adi"] = stokAdi
                params["stok_iscilik_no"] = stokNo
                params["miktar"] = miktar.toString()
                params["fiyat"] = fiyat.toString()
                params["toplam"]=((miktar*fiyat)+miktar*fiyat*spinner_kdv.selectedItem.toString().toInt()/100).toString()
                params["parca_turu"]=parcaturu
                params["kdv"]=spinner_kdv.selectedItem.toString()
                params["tur"] = "fatura_kaydet"
                return params
            }
        }
        queue.add(postRequest)



    }

    private fun sifirla() {
        etStokNo.getText().clear()
        etStokAdi.getText().clear()
        etMiktar.getText().clear()
        etFiyat.getText().clear()
        btn_fatura_guncelle.visibility= View.GONE
    }

    private fun kayit_getir() {
        val urlsb = "&kadi="
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kayit_getir" + urlsb
        Log.d("STOK",url)
        val queue: RequestQueue = Volley.newRequestQueue(context)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["onarim"] as JSONArray
                    //println(outputObject["plaka"])
                    val itemList: ArrayList<OnarimModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val itemModel = OnarimModel("kadi","ozel_id","plaka",
                            item.getString("id") ,item.getString("stok_iscilik_adi"),item.getString("stok_iscilik_no"),
                            item.getString("miktar").toInt(),item.getString("fiyat").toFloat(),
                            item.getString("parca_turu").toString(),item.getString("kdv"),                            item.getString("parcaiscilik").toString()

                        )

                        toplam +=(item.getString("miktar").toInt()*item.getString("fiyat").toFloat()+
                                item.getString("miktar").toInt()*item.getString("fiyat").toFloat()*item.getString("kdv").toInt()/100)

                        itemList.add(itemModel)
                    }
                    txtToplamFiyat.setText(toplam.toString())
                    toplam=0f
                    val adapter =
                        OnarimAdapter(itemList)
                    adapter.notifyItemInserted(itemList.size)

                    newRecyclerviewm.adapter= adapter
                    newRecyclerviewm.layoutManager= LinearLayoutManager(context)
                    val diveyder= DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                    newRecyclerviewm.addItemDecoration(diveyder)
                    newRecyclerviewm.setHasFixedSize(true)
                    newArrayList= arrayListOf<ClipData.Item>()
                    context?.let {
                        getUserData(it)

                    }}catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
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
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(),R.color.blue))
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

            @SuppressLint("ResourceAsColor")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val txt_stokAdi= viewHolder.itemView.findViewById<TextView>(R.id.txt_stokAdi)
                        val txt_StokNo= viewHolder.itemView.findViewById<TextView>(R.id.txt_StokNo)
                        val txtMiktar= viewHolder.itemView.findViewById<TextView>(R.id.txtMiktar)
                        val txtFiyat= viewHolder.itemView.findViewById<TextView>(R.id.txtFiyat)
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
                        val txt_stokAdi= viewHolder.itemView.findViewById<TextView>(R.id.txt_stokAdi)
                        val txt_StokNo= viewHolder.itemView.findViewById<TextView>(R.id.txt_StokNo)
                        val txtMiktar= viewHolder.itemView.findViewById<TextView>(R.id.txtMiktar)
                        val txtFiyat= viewHolder.itemView.findViewById<TextView>(R.id.txtFiyat)
                        val txt_kdv=viewHolder.itemView.findViewById<TextView>(R.id.txt_kdv)
                        /*onarim_guncelle(txt_stokAdi.getText().toString(),
                            txt_StokNo.getText().toString(),txtMiktar.getText().toString().toInt(),txtFiyat.getText().toString().toFloat(),txt_kdv.getText().toString())
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
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["kdv"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_vrs.add(item.getString("oran").toString())

                    }

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_vrs.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_vrs[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_kdv.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "MODEL doldur hatası", Toast.LENGTH_LONG)
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

    private fun getUserData(context: Context) {
        val itemList: java.util.ArrayList<OnarimModel> = java.util.ArrayList()
//        kayit_getir()

        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FaturaGetirFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FaturaGetirFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}