package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.internal.ContextUtils
import com.selpar.pratikhasar.R
import org.json.JSONArray
import java.util.ArrayList
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SigortaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SigortaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var kadi:String
    lateinit var plaka:String
    lateinit var ozel_id:String
    lateinit var firma_id:String
    lateinit var sifre:String
    lateinit var etTunanakProfili:EditText
    lateinit var etSigorta:EditText
    lateinit var policeno:EditText
    lateinit var dosyasorumlusuadi:EditText
    lateinit var dosyasorumlutel:EditText
    lateinit var dosyano:EditText
    lateinit var ihbartarihi:EditText
    lateinit var kontroloradi:EditText
    lateinit var kontrolortel:EditText
    lateinit var sigortasirketi:EditText
    lateinit var adres:EditText
    lateinit var sigortaeksperi:EditText
    lateinit var btn_sigorta_guncelle:Button
    var bundlem= Bundle()
    lateinit var spinner_tutanak_profili:Spinner
    lateinit var spinner_sigorta_profili:Spinner
    lateinit var spinner_police_turu:Spinner


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
        val view=inflater.inflate(R.layout.fragment_sigorta, container, false)
        val args= this.arguments
        kadi=args?.getString(("kadi")).toString()
        sifre=args?.getString("sifre").toString()
        firma_id=args?.getString("firma_id").toString()
        plaka=args?.getString("plaka").toString()
        ozel_id=args?.getString("ozel_id").toString()
       // etTunanakProfili=view.findViewById<EditText>(R.id.ettutanak)
        spinner_tutanak_profili=view.findViewById<Spinner>(R.id.spinner_tutanak_profili)
        spinner_sigorta_profili=view.findViewById<Spinner>(R.id.spinner_sigorta_profili)
        spinner_police_turu=view.findViewById<Spinner>(R.id.spinner_police_turu)

      /*  val alspinner1 = ArrayList<String>()
        val _spvalue1 = this.resources.getStringArray(com.selpar.pratikhasar.R.array.evrak)
        for (i in _spvalue1.indices) {
            alspinner1.add(_spvalue1[i])
        }
        alspinner1.add()
        val adapter1: Any? = ArrayAdapter<Any?>(
            view.getContext(),
            android.R.layout.simple_spinner_item,
            alspinner1 as List<Any?>
        )
        spinner_tutanak_profili.setAdapter(adapter1 as SpinnerAdapter?)*/
        //etSigorta=view.findViewById<EditText>(R.id.etSigorta)
       // policeno=view.findViewById<EditText>(R.id.etpoliceno)
        dosyasorumlusuadi=view.findViewById<EditText>(R.id.etdosyasorumlusuadi)
        dosyasorumlutel=view.findViewById<EditText>(R.id.etdosyasorumlutel)
        dosyano=view.findViewById<EditText>(R.id.etdosyano)
        ihbartarihi=view.findViewById<EditText>(R.id.etihbartarihi)
        kontroloradi=view.findViewById<EditText>(R.id.etkontroloradi)
        kontrolortel=view.findViewById<EditText>(R.id.etkontrolortel)
        sigortasirketi=view.findViewById<EditText>(R.id.etsigortasirketi)
        adres=view.findViewById<EditText>(R.id.etadres)
        sigortaeksperi=view.findViewById<EditText>(R.id.etsigortaeksperi)
        btn_sigorta_guncelle=view.findViewById<Button>(R.id.btn_arac_bilgi_ilerle)
        btn_sigorta_guncelle.setOnClickListener {
            guncelleme_yapti()
        }
        val urlsb = "?kadi="+kadi+"&ozel_id="+ozel_id+"&tur=aracbilgigetir&plaka="+plaka
        var url = "https://pratikhasar.com/netting/mobil.php"+urlsb
        Log.d("gonder",url)
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val json = response["plaka"] as JSONArray
                //println(outputObject["plaka"])
                val item = json.getJSONObject(0)
               // etTunanakProfili.setText(item.getString("tutanakprofil").toString())

                val tutanak_alspinner1 = ArrayList<String>()
                tutanak_alspinner1.add(item.getString("tutanakprofil").toString())

                val tutanak_spvalue1 = this.resources.getStringArray(com.selpar.pratikhasar.R.array.tuttanak_tipi)
                for (i in tutanak_spvalue1.indices) {
                    tutanak_alspinner1.add(tutanak_spvalue1[i])
                }
                val tutanak_adapter1: Any? = ArrayAdapter<Any?>(
                    view.getContext(),
                    android.R.layout.simple_spinner_item,
                    tutanak_alspinner1 as List<Any?>
                )
                spinner_tutanak_profili.setAdapter(tutanak_adapter1 as SpinnerAdapter?)

                val sigorta_alspinner1 = ArrayList<String>()
                sigorta_alspinner1.add(item.getString("sigortaprofil").toString())
                val sigorta_spvalue1 = this.resources.getStringArray(com.selpar.pratikhasar.R.array.sigorta_tipi)
                for (i in sigorta_spvalue1.indices) {
                    sigorta_alspinner1.add(sigorta_spvalue1[i])
                }
                val sigorta_adapter1: Any? = ArrayAdapter<Any?>(
                    view.getContext(),
                    android.R.layout.simple_spinner_item,
                    sigorta_alspinner1 as List<Any?>
                )
                spinner_sigorta_profili.setAdapter(sigorta_adapter1 as SpinnerAdapter?)

                val police_alspinner1=ArrayList<String>()
                police_alspinner1.add(item.getString("policeno").toString())
                val police_value1=this.resources.getStringArray(R.array.police_turu)
                for(i in police_value1.indices){
                    police_alspinner1.add(police_value1[i])
                }
                val police_adapter1:Any?=ArrayAdapter<Any?>(
                    view.context,
                    android.R.layout.simple_spinner_item,
                    police_alspinner1 as List<Any?>
                )
                spinner_police_turu.setAdapter(police_adapter1 as SpinnerAdapter?)

                //policeno.setText(item.getString("policeno").toString())
                dosyasorumlusuadi.setText(item.getString("dosyasorumlusuadi").toString())
                dosyasorumlutel.setText(item.getString("dosyasorumlutel").toString())
                dosyano.setText(item.getString("dosyano").toString())
                ihbartarihi.setText(item.getString("ihbartarihi").toString())
                kontroloradi.setText(item.getString("kontroloradi").toString())
                kontrolortel.setText(item.getString("kontrolortel").toString())
                sigortasirketi.setText(item.getString("sigortasirketi").toString())
                adres.setText(item.getString("adres").toString())
                sigortaeksperi.setText(item.getString("sigortaeksperi").toString())

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
               // Toast.makeText(context, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)



        return view
    }

    private fun guncelleme_yapti() {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(requireContext(),"Güncelleme Başarılı: "+plaka,Toast.LENGTH_LONG).show()
                bundlem.putString("ozel_id",ozel_id)
                bundlem.putString("kadi",kadi)
                bundlem.putString("plaka",plaka)
                bundlem.putString("sifre",sifre)
                bundlem.putString("firma_id",firma_id)
                val fragobj = SigortaFragment()
                fragobj.arguments=bundlem
                // fragobj.resimGetir(kadi,ozel_id)
                val fragmentmaneger= (ContextUtils.getActivity(requireContext()) as FragmentActivity?)!!.supportFragmentManager.beginTransaction()
                    .replace(com.selpar.pratikhasar.R.id.fragment_mekanik,  fragobj)
                    .commit()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["ozel_id"] = ozel_id
                params["plaka"] = plaka
                params["tur"] = "sigorta_guncelle"
                params["etTunanakProfili"]=spinner_tutanak_profili.selectedItem.toString()
                params["etSigorta"]=spinner_sigorta_profili.selectedItem.toString()
                params["policeno"]=spinner_police_turu.selectedItem.toString()
                params["dosyasorumlusuadi"]=dosyasorumlusuadi.getText().toString()
                params["dosyasorumlutel"]=dosyasorumlutel.getText().toString()
                params["dosyano"]=dosyano.getText().toString()
                params["ihbartarihi"]=ihbartarihi.getText().toString()
                params["kontroloradi"]=kontroloradi.getText().toString()
                params["kontrolortel"]=kontrolortel.getText().toString()
                params["sigortasirketi"]=sigortasirketi.getText().toString()
                params["adres"]=adres.getText().toString()
                params["sigortaeksperi"]=sigortaeksperi.getText().toString()



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
         * @return A new instance of fragment SigortaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SigortaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}