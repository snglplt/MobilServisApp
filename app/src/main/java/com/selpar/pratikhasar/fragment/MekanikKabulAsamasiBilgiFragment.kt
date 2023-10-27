package com.selpar.pratikhasar.fragment

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.EvrakAdapter
import com.selpar.pratikhasar.data.ItemModel
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MekanikKabulAsamasiBilgiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MekanikKabulAsamasiBilgiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var image_ruhsat:ImageView
    lateinit var image_km:ImageView
    lateinit var image_sag_on_taraf:ImageView
    lateinit var image_sol_on_taraf:ImageView
    lateinit var image_sag_arka_taraf:ImageView
    lateinit var image_sol_arka_taraf:ImageView
    lateinit var ozel_id:String
    lateinit var kadi:String
    lateinit var firma:String
    var ruhsat:String=""
    var km:String=""
    var sagon:String=""
    var solon:String=""
    var sagarka:String=""
    var solarka:String=""

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
        val view=inflater.inflate(R.layout.fragment_mekanik_kabul_asamasi_bilgi, container, false)
        image_ruhsat=view.findViewById(R.id.image_ruhsat)
        image_km=view.findViewById(R.id.image_km)
        image_sag_on_taraf=view.findViewById(R.id.image_sag_on_taraf)
        image_sol_on_taraf=view.findViewById(R.id.image_sol_on_taraf)
        image_sag_arka_taraf=view.findViewById(R.id.image_sag_arka_taraf)
        image_sol_arka_taraf=view.findViewById(R.id.image_sol_arka_taraf)
        image_ruhsat.setImageDrawable(getResources().getDrawable(com.selpar.pratikhasar.R.drawable.camera))
        image_km.setImageDrawable(getResources().getDrawable(com.selpar.pratikhasar.R.drawable.camera))
        image_sag_on_taraf.setImageDrawable(getResources().getDrawable(com.selpar.pratikhasar.R.drawable.camera))
        image_sol_on_taraf.setImageDrawable(getResources().getDrawable(com.selpar.pratikhasar.R.drawable.camera))
        image_sag_arka_taraf.setImageDrawable(getResources().getDrawable(com.selpar.pratikhasar.R.drawable.camera))
        image_sol_arka_taraf.setImageDrawable(getResources().getDrawable(com.selpar.pratikhasar.R.drawable.camera))
        image_ruhsat.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            Picasso.get().load(ruhsat).into(img)
            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 3000
            params.height=3000
            img.setLayoutParams(params)
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
             View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
             View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
             View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
             View.SYSTEM_UI_FLAG_FULLSCREEN
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
        image_km.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            Picasso.get().load(km).into(img)
            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 3000
            params.height=3000
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
        image_sag_on_taraf.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            Picasso.get().load(sagon).into(img)
            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 3000
            params.height=3000
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
        image_sol_on_taraf.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img = view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            Picasso.get().load(solon).into(img)
            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 3000
            params.height = 3000
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
        image_sag_arka_taraf.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            Picasso.get().load(sagarka).into(img)
            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 3000
            params.height=3000
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
        image_sol_arka_taraf.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            Picasso.get().load(solarka).into(img)
            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 3000
            params.height=3000
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }

        val args = this.arguments
        ozel_id=args?.getString("ozel_id").toString()
        kadi=args?.getString("kadi").toString()
        firma=args?.getString("firma_id").toString()
        resimGetir(kadi,firma,ozel_id)
        firmagetir(kadi,ozel_id)
        return view
    }
    fun firmagetir(kadi: String, ozel_id: String) {

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
                    resimGetir(kadi,firma,ozel_id)
                    //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                    /*yol[i] = "https://pratikhasar.com/evrak/"+firma_id+"/"+ozel_id+"/"+item.getString("yol")
                    Log.d("urllll: ",yol[i] )
                    "https://pratikhasar.com/netting/" +
                                item.getString("yol")
                    // Picasso.get().load(yol[i]).into(image1)
                    val deger="https://pratikhasar.com/netting/" +
                            item.getString("yol")*/





               }}catch (e:Exception){}
        }, { error ->
            Log.e("TAG", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            //Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
        }
    )
    queue.add(request)

}

    private fun resimGetir(kadi: String, firma: String, ozel_id: String) {
        val urlsb ="&kadi=" + kadi +"&firma_id="+firma +"&ozel_id="+ ozel_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kabul_resim"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["resim"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if(item.getString("ruhsat").toString()!="64142df1e5f56.png"){
                        ruhsat ="https://pratikhasar.com/netting/resimler/mekanik/"+firma+"/" +item.getString("ruhsat")
                        Picasso.get().load(ruhsat).into(image_ruhsat)
                        }
                        else{
                            Toast.makeText(requireContext(),"düştü",Toast.LENGTH_LONG).show()
                            image_ruhsat.setImageDrawable(getResources().getDrawable(R.drawable.camera))
                        }
                        if(item.getString("km").toString()!="64142df1e628a.png"){
                         km ="https://pratikhasar.com/netting/resimler/mekanik/"+firma+"/" +item.getString("km")
                            Picasso.get().load(km).into(image_km)

                        }
                        else{
                            image_km.setImageDrawable(getResources().getDrawable(R.drawable.camera))
                        }
                        if(item.getString("sagon").toString()!="64142df1e6451.png"){
                         sagon ="https://pratikhasar.com/netting/resimler/mekanik/"+firma+"/" +item.getString("sagon")
                            Picasso.get().load(sagon).into(image_sag_on_taraf)
                        }
                        else{
                            image_sag_on_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera))
                        }
                        if(item.getString("solon").toString()!="64142df1e665c.png"){
                         solon ="https://pratikhasar.com/netting/resimler/mekanik/"+firma+"/" +item.getString("solon")
                        Picasso.get().load(solon).into(image_sol_on_taraf)
                        }
                        else{
                            image_sol_on_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera))
                        }
                        if(item.getString("sagarka").toString()!="64142df1e684d.png"){
                         sagarka ="https://pratikhasar.com/netting/resimler/mekanik/"+firma+"/" +item.getString("sagarka")
                        Picasso.get().load(sagarka).into(image_sag_arka_taraf)
                        }
                        else{
                            image_sag_arka_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera))
                        }
                        if(item.getString("solarka").toString()!="64142df1e6a4b.png"){
                         solarka ="https://pratikhasar.com/netting/resimler/mekanik/"+firma+"/" +item.getString("solarka")
                        Picasso.get().load(solarka).into(image_sol_arka_taraf)
                        }
                        else{
                            image_sol_arka_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera))
                        }

                        // yol[i] ="https://pratikhasar.com/netting/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        //yol[i] ="https://pratikhasar.com/netting/"+item.getString("yol")


                        }
                }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
            }
        )
        queue.add(request)


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MekanikKabulAsamasiBilgiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MekanikKabulAsamasiBilgiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}