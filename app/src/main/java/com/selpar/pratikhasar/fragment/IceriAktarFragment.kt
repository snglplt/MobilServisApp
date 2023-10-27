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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.GidenFaturaModel
import com.selpar.pratikhasar.adapter.GonderilmisFaturaAdapter
import com.selpar.pratikhasar.adapter.GonderilmisFaturaModel
import com.selpar.pratikhasar.adapter.IceriAktarAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.ArrayList


class IceriAktarFragment : Fragment() {
    lateinit var rc_iceri_aktar: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    lateinit var firma_id:String



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_iceri_aktar, container, false)
        rc_iceri_aktar=view.findViewById(R.id.rc_iceri_aktar)
        val args= this.arguments
        firma_id=args?.getString("firma_id").toString()
        onFaturaGetir()

        return view
    }
    private fun onFaturaGetir() {
        var url = "https://pratikhasar.com/netting/mobil.php?tur=getir_fatura_var_mi"
        Log.d("FATURA", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["bilgi"] as JSONArray
                    val itemList: ArrayList<GonderilmisFaturaModel> = ArrayList()

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val i="\\u0131"
                        val o="\\u00f6"
                        val c="\\u00e7"
                        val s="\\u015f"
                        val g="\\u011f"
                        val O="\\u00d6"
                        val I="\\u0130"
                        val u="\\u00fc"
                        val itemModel = GonderilmisFaturaModel(
                            item.getString("evrakid"),
                            item.getString("evraktarihi"),
                            item.getString("evraktarihi"),
                            item.getString("vergino"),
                            item.getString("evrakno"),
                            firma_id,
                            item.getString("toplam"),
                            item.getString("cariadi").toString()
                                .replace(i,"ı")
                                .replace(o,"ö")
                                .replace(c,"ç")
                                .replace(s,"ş")
                                .replace(g,"ğ")
                                .replace(O,"Ö")
                                .replace(I,"İ")
                                .replace(u,"ü"),
                            item.getString("toplam")
                        )

                        itemList.add(itemModel)


                    }
                    val adapter =
                        IceriAktarAdapter(itemList)

                    rc_iceri_aktar.adapter = adapter
                    rc_iceri_aktar.layoutManager = LinearLayoutManager(requireContext())
                    rc_iceri_aktar.setHasFixedSize(false)
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
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ $error", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(
            timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)


    }
    private fun getUserData(context: Context) {
        val itemList: ArrayList<GidenFaturaModel> = ArrayList()


        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }



}