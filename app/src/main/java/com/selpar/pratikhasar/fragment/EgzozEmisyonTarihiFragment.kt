package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.selpar.pratikhasar.adapter.EgzozEmisyontarihiAdapter
import com.selpar.pratikhasar.data.news
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class EgzozEmisyonTarihiFragment : Fragment() {
    lateinit var rc_bakim_zamani_gelenler: RecyclerView
    lateinit var dilsecimi:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullanici_id:String
    lateinit var kadi:String
    lateinit var gorev:String
    lateinit var sifre:String
    var plaka = ArrayList<String>()
    var resim = ArrayList<String>()
    var marka = ArrayList<String>()
    var model = ArrayList<String>()
    var modelyili = ArrayList<String>()
    var kasatipi =ArrayList<String>()
    var dosyano = ArrayList<String>()
    var unvan = ArrayList<String>()
    var renk = ArrayList<String>()
    var km = ArrayList<String>()
    var baslik = ArrayList<String>()
    var ozel_id = ArrayList<String>()
    var kabulnom = ArrayList<String>()
    var servis_turu = ArrayList<String>()
    var saseno = ArrayList<String>()
    var mua = ArrayList<String>()
    var motorno = ArrayList<String>()
    var modelvers = ArrayList<String>()
    var adres = ArrayList<String>()
    var il = ArrayList<String>()
    var ilce = ArrayList<String>()
    var sigortasirketi = ArrayList<String>()
    var tel = ArrayList<String>()
    var policeno = ArrayList<String>()
    var policeturu = ArrayList<String>()
    var policetarihi = ArrayList<String>()
    var kazatarihi = ArrayList<String>()
    var ihbartarihi = ArrayList<String>()
    var mail = ArrayList<String>()
    var onarim = ArrayList<Float>()
    var onarimbul2= ArrayList<Float>()
    var markaresim= ArrayList<String>()
    var yakitturu= ArrayList<String>()
    private lateinit var newArrayList : ArrayList<news>
    lateinit var ettarih:EditText
    var girilen_tarih:String=""
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_egzoz_emisyon_tarihi, container, false)
        rc_bakim_zamani_gelenler=view.findViewById(R.id.rc_bakim_zamani_gelenler)
        val args= this.arguments
        kadi=args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullanici_id = args?.getString("kullanici_id").toString()
        sifre = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        firma_id=args?.getString("firma_id").toString()
        // Inflate the layout for this fragment
        onKayitGetir()
        return view
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onKayitGetir() {
        plaka.clear()
        mua.clear()
        val urlek="&kadi="+kadi+"&sifre="+sifre+"&firma_id="+firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kabul_sirala_egzozemisyon"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("onarim",url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{
                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    val formatter = DateTimeFormatter.ISO_DATE
                    val date = LocalDate.parse(item.getString("egzozemisyon").toString(), formatter)
                    val bugun = LocalDate.now()
                    val daysBetween = ChronoUnit.DAYS.between(bugun,date)
                    if(daysBetween<=30){

                    plaka.add(item.getString("plaka"))
                    if(item.getString("toplam")!="null") {
                        onarim.add(item.getString("toplam").toFloat())
                    }
                    else{
                        onarim.add(0f)
                    }


                    resim.add(item.getString("resim"))
                    marka.add(item.getString("marka"))
                    model.add(item.getString("model"))
                    modelyili.add(item.getString("modelyili"))
                    kasatipi.add(item.getString("kasatipi"))
                    if(item.getString("dosyano").toString()!=""){
                        dosyano.add(item.getString("dosyano"))
                    }
                    else{
                        dosyano.add("12345")
                    }
                    unvan.add(item.getString("unvan"))
                    renk.add(item.getString("renk"))
                    km.add(item.getString("km")+" KM")
                    baslik.add(item.getString("baslik"))
                    ozel_id.add(item.getString("ozel_id"))
                    kabulnom.add(item.getString("kabulnom"))
                    servis_turu.add(item.getString("servis_turu"))

                        yakitturu.add(item.getString("yakitturu"))


                    saseno.add(item.getString("saseno"))
                    mua.add(item.getString("egzozemisyon"))//
                    motorno.add(item.getString("motorno"))
                    modelvers.add(item.getString("modelvers"))
                    adres.add(item.getString("adres"))
                    il.add(item.getString("il"))
                    ilce.add(item.getString("ilce"))
                    sigortasirketi.add(item.getString("sigortasirketi"))
                    policeno.add(item.getString("policeturu"))
                    tel.add(item.getString("telefon1"))
                    markaresim.add("https://selparbulut.com/resim/"+item.getString("markaresim").toString())
                //    Toast.makeText(requireContext(),item.getString("markaresim"), Toast.LENGTH_LONG).show()
                    Log.d("MARKA","https://selparbulut.com/resim/"+item.getString("markaresim"))
                    if(policeturu.add(item.getString("policeturu")).toString()!="null"){
                        policeturu.add(item.getString("policeturu"))
                    }
                    else {
                        policeturu.add("")
                    }
                    if(policetarihi.add(item.getString("polbas")).toString()!="null"){
                        policetarihi.add(item.getString("polbas"))
                    }
                    else {
                        policetarihi.add("")
                    }
                    if(kazatarihi.add(item.getString("kazatarihi")).toString()!="null"){
                        kazatarihi.add(item.getString("kazatarihi"))
                    }else {
                        kazatarihi.add("")
                    }
                    if(ihbartarihi.add(item.getString("ihbartarihi")).toString()!="null"){
                        ihbartarihi.add(item.getString("ihbartarihi"))
                    }else {
                        ihbartarihi.add("")
                    }
                    if(mail.add(item.getString("mail")).toString()!="null"){
                        mail.add(item.getString("mail"))
                    }else {
                        mail.add("")
                    }
                }}
            }catch (e:Exception){}
                rc_bakim_zamani_gelenler.layoutManager= LinearLayoutManager(requireContext())
                rc_bakim_zamani_gelenler.setHasFixedSize(false)
                newArrayList= arrayListOf<news>()
                getUserData(requireContext())
                // servisHesapla(servis_hasar,servis_mekanik)

            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
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
                RecyclerViewSwipeDecorator.Builder( c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(),R.color.gray))
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(requireContext(),
                            R.color.blue))
                    // .addSwipeLeftLabel(getString(R.string.kartikapat))
                    .addSwipeRightLabel(getString(R.string.guncelle))
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
                    ItemTouchHelper.RIGHT -> {
                        viewHolder.itemId
                        val plaka_ = viewHolder.itemView.findViewById<TextView>(R.id.Tvplaka).text
                        val tarih =
                            viewHolder.itemView.findViewById<TextView>(R.id.Tvonarim).text

                        guncelle(plaka_.toString(), tarih.toString())

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rc_bakim_zamani_gelenler)


    }
    fun isStringInDateFormat(input: String, format: String): Boolean {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.isLenient = false

        try {
            dateFormat.parse(input)
            return true
        } catch (e: Exception) {
            return false
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun guncelle_tarih(plaka: String,tarih:String) {
        //Toast.makeText(requireContext(),"TARİH: "+tarih,Toast.LENGTH_LONG).show()
        val inputString = ettarih.getText().toString()
        val dateFormat = "dd-MM-yyyy"
        val isInDateFormat = isStringInDateFormat(inputString, dateFormat)

        if (isInDateFormat) {
            val queue = Volley.newRequestQueue(requireContext())
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    onKayitGetir()
                    Toast.makeText(
                        requireContext(),
                        "Plaka Tarihi Guncelleme Başarılı: " + kadi,
                        Toast.LENGTH_SHORT
                    ).show()

                },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                    Toast.makeText(
                        requireContext(),
                        "Plaka Tarihi Guncelleme HATALI: " + kadi,
                        Toast.LENGTH_SHORT
                    ).show()
                    onKayitGetir()

                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["kadi"] = kadi
                    params["firma_id"] = firma_id
                    params["plaka"] = plaka
                    params["tarih"] = tarih
                    params["tur"] = "tarih_guncelle_egzozemisyon"

                    return params
                }
            }
            queue.add(postRequest)
        }else {
            Toast.makeText(
                requireContext(),
                "Lütfen tarih fromatında giriniz...",
                Toast.LENGTH_LONG
            ).show()
            onKayitGetir()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun guncelle(plaka:String,tarih:String) {
        val alertadd = AlertDialog.Builder(requireContext())
        alertadd.setTitle(getString(R.string.fatura_ayari))
        val factory = LayoutInflater.from(requireContext())
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.tarih_ayarla, null)
        ettarih=view.findViewById<EditText>(com.selpar.pratikhasar.R.id.ettarih)
        ettarih.setText(tarih)

        ettarih.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val dat = (dayOfMonth.toString()+ "-" + (monthOfYear + 1) + "-" +year.toString()  )
                        girilen_tarih = (year.toString()+ "-" + (monthOfYear + 1) + "-" +dayOfMonth.toString()  )
                        ettarih.setText(dat)


                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }




        //getLastLocation()
        alertadd.setView(view)
        alertadd.setPositiveButton(
            getString(R.string.evet)
        ) { dialogInterface, which ->

            guncelle_tarih(plaka,girilen_tarih)


        }
        alertadd.setNegativeButton(getString(R.string.hayir)){
                dialog, which -> dialog.dismiss()
            onKayitGetir()
        }
        alertadd.show()
    }

    private fun getUserData(context: Context) {
        for(i in plaka.indices){
            val news = news(plaka[i],resim[i],marka[i],model[i],modelyili[i],kasatipi[i],dosyano[i],unvan[i],renk[i],km[i],baslik[i],ozel_id[i],context,kadi,sifre,firma_id,kullanici_id,kabulnom[i],
                servis_turu[i],saseno[i],mua[i],motorno[i],modelvers[i],adres[i],il[i],ilce[i], sigortasirketi[i],policeno[i],onarim[i],tel[i],
                policeturu[i],policetarihi[i],kazatarihi[i],ihbartarihi[i],mail[i],markaresim[i],1,1,yakitturu[i])

            newArrayList.add(news)
        }
        rc_bakim_zamani_gelenler.adapter= EgzozEmisyontarihiAdapter(newArrayList)
    }


}