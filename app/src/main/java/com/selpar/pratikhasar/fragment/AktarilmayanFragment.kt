package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.GidenFaturaModel
import com.selpar.pratikhasar.adapter.GonderilmisFaturaAdapter
import com.selpar.pratikhasar.adapter.GonderilmisFaturaModel
import com.selpar.pratikhasar.ui.FaturaPdfGosterActivity
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.ArrayList

class AktarilmayanFragment : Fragment() {
    lateinit var rc_aktarilmayan_faturalar: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>

    lateinit var firma_id: String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_aktarilmayan, container, false)
        rc_aktarilmayan_faturalar = view.findViewById(R.id.rc_aktarilmayan_faturalar)
        val args = this.arguments
        firma_id = args?.getString("firma_id").toString()
        onFaturaGetir()

        return view
    }

    private fun onFaturaGetir() {
        val urlsb = "firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/e_gelen_listesi.php?" + urlsb
        Log.d("gelen_fatura_listesi", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["aktarilmayan_faturalar"] as JSONArray
                    val itemList: ArrayList<GonderilmisFaturaModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        val itemModel = GonderilmisFaturaModel(
                            item.getString("id"),
                            item.getString("durumu"),
                            item.getString("tarih"),
                            item.getString("evrakno"),
                            item.getString("fatura_id"),
                            firma_id,
                            item.getString("fatura_tipi"),
                            item.getString("cari"),
                            item.getString("tutar")
                        )

                        itemList.add(itemModel)


                    }
                    val adapter =
                        GonderilmisFaturaAdapter(itemList)

                    rc_aktarilmayan_faturalar.adapter = adapter
                    rc_aktarilmayan_faturalar.layoutManager = LinearLayoutManager(requireContext())
                    rc_aktarilmayan_faturalar.setHasFixedSize(false)
                    newArrayList = arrayListOf<ClipData.Item>()
                    requireContext()?.let {
                        getUserData(it)

                    }
                } catch (e: Exception) {
                  //  Toast.makeText(requireContext(), "HATA: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.

            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(
            timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
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
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    //.addSwipeLeftLabel(getString(R.string.sil))
                    .addSwipeRightLabel("PDF")
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
                    ItemTouchHelper.RIGHT -> {
                        val txt_faturaid =
                            viewHolder.itemView.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_fatura_id)
                        val txt_id =
                            viewHolder.itemView.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_id)

                        onPdfGoster(txt_id.getText().toString(), txt_faturaid.getText().toString())
                        onFaturaGetir()

                        /*onarim_guncelle(txt_stokAdi.getText().toString(),
                                txt_StokNo.getText().toString(),txtMiktar.getText().toString().toInt(),txtFiyat.getText().toString().toFloat(),txt_kdv.getText().toString())
                            // Do something when a user swept right*/
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rc_aktarilmayan_faturalar)


    }

    private fun onPdfGoster(id: String, fatura_id: String) {
        val i = Intent(requireContext(), FaturaPdfGosterActivity::class.java)
        i.putExtra(
            "path",
            "https://pratikhasar.com/netting/e_gelen_pdf.php?firma_id=1&InvoiceUUID=" + id + "&id=" + fatura_id
        )
        startActivity(i)

    }

    private fun getUserData(context: Context) {
        val itemList: ArrayList<GidenFaturaModel> = ArrayList()


        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }

}