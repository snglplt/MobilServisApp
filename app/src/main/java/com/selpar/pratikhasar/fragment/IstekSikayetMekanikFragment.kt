package com.selpar.pratikhasar.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.TextAdapter
import com.selpar.pratikhasar.adapter.TextModel
import com.selpar.pratikhasar.adapter.YapilacakAdapter
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.YapilacakModel
import com.selpar.pratikhasar.data.news
import com.selpar.pratikhasar.ui.AcikKartBilgiActivity
import com.selpar.pratikhasar.ui.HomeActivity
import com.selpar.pratikhasar.ui.MekanikKartBilgiActivity
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.io.*
import kotlin.reflect.jvm.internal.impl.types.checker.TypeRefinementSupport.Enabled

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IstekSikayetMekanikFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IstekSikayetMekanikFragment : Fragment() {
    //lateinit var btn_arac_bilgi_ilerle: Button
    lateinit var btnAracBilgi_kart_ac: Button
    lateinit var btnAracSahibi_kart_ac: Button
    lateinit var btn_arac_kabul_kart_ac: Button
    lateinit var btn_istek_sikayet: Button
    lateinit var btn_yapilacaklar: Button
    lateinit var btn_kaydet_: Button
    lateinit var btn_cikis: ImageView
    //arac bilgileri
    lateinit var aracturu:String
    lateinit var marka:String
    lateinit var model:String
    lateinit var modelvrs:String
    lateinit var model_y:String
    lateinit var kasa_tipi:String
    lateinit var km:String
    lateinit var renk2:String
    lateinit var saseno:String
    lateinit var motorno:String
    lateinit var konum:String
    lateinit var durum:String
    lateinit var mua:String
    lateinit var tahmini_teslim_tarihi_s:String
    lateinit var egzoz: String
    lateinit var trafik: String
    lateinit var kasko: String
    //sahibi bilgileri
    lateinit var unvan:String
    lateinit var vergino:String
    lateinit var vergidairesi:String
    lateinit var adres:String
    lateinit var il:String
    lateinit var ilce:String
    lateinit var tel:String
    lateinit var tel2:String
    lateinit var stc:String
    lateinit var sAdSoyad:String
    lateinit var bankaismi:String
    lateinit var iban:String
    lateinit var mail:String
    var resimyolu:String=""
    //sigorta
    lateinit var sigorta:String
    lateinit var tutanak:String
    lateinit var police:String
    lateinit var sorumluad:String
    lateinit var sorumlutel:String
    lateinit var dosyano:String
    lateinit var ihbartarihi:String
    lateinit var kontorolorad:String
    lateinit var kontorolortel:String
    lateinit var sigortasirketi:String
    lateinit var sigortaadres:String
    lateinit var eksper:String
    lateinit var kadi:String
    var bundlem=Bundle()
    lateinit var firma_id:String

    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    private lateinit var output: String
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    lateinit var plaka:String
    var resim_ruhsat: String = ""
    var resim_km: String = ""
    var resim_sagon: String = ""
    var resim_solon: String = ""
    var resim_sagarka: String = ""
    var resim_solarka: String = ""
    var yakitturu: String = ""
    var onarim: String = ""
    var itemList_usta:ArrayList<String> = ArrayList()



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_istek_sikayet_mekanik, container, false)
        //  btn_arac_bilgi_ilerle=view.findViewById(R.id.btn_arac_bilgi_ilerle)
        btnAracSahibi_kart_ac=view.findViewById(R.id.btnAracSahibi_kart_ac_)
        btn_arac_kabul_kart_ac=view.findViewById(R.id.btn_kabul_asamasi)
        btnAracBilgi_kart_ac=view.findViewById(R.id.btnAracBilgi_kart_ac)
        btn_istek_sikayet=view.findViewById(R.id.btn_istek_sikayet)
        btn_kaydet_=view.findViewById(R.id.btn_kaydet_)
        btn_yapilacaklar=view.findViewById(R.id.btn_yapilacaklar)
        newRecyclerviewm=view.findViewById(R.id.rc_yapilacaklar)
        btn_cikis=view.findViewById(R.id.btn_cikis)
        btn_arac_kabul_kart_ac.setBackgroundColor(0)
        btnAracSahibi_kart_ac.setBackgroundColor(0)
        btnAracBilgi_kart_ac.setBackgroundColor(0)
        btn_istek_sikayet.setBackgroundColor(Color.GRAY)
        val args= this.arguments
        yakitturu=args?.getString("yakitturu").toString()
       // Toast.makeText(requireContext(),"yakitturu: "+yakitturu,Toast.LENGTH_LONG).show()
        kadi=args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullnciid = args?.getString("kullanici_id").toString()
        sifrem = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        plaka=args?.getString("plaka").toString()
        dosyano=args?.getString("dosyano").toString()
        if(plaka==""){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.plakasec)
        builder.setPositiveButton(R.string.evet) { dialog, which ->
            btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            bundlemDoldur()
            val fragobj = MekanikAracBilgiKartAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
            // Do something when the user clicks the positive button
        }

        val dialog = builder.create()
        dialog.show()
        }
        resimyolu=args?.getString("resimyolu").toString()

        saseno=args?.getString("saseno").toString()
        km=args?.getString("km").toString()
        motorno=args?.getString("motorno").toString()
        mua=args?.getString("mua").toString()

        renk2=args?.getString("renk").toString()
        saseno=args?.getString("saseno").toString()
        motorno=args?.getString("motorno").toString()
        konum=args?.getString("konum").toString()
        durum=args?.getString("durum").toString()
        mua=args?.getString("mua").toString()
        //sahibi bilgileri
        unvan=args?.getString("unvan").toString()
        vergino=args?.getString("vergino").toString()
        vergidairesi=args?.getString("vergidairesi").toString()
        unvan=args?.getString("unvan").toString()
        adres=args?.getString("adres").toString()
        il=args?.getString("il").toString()
        ilce=args?.getString("ilce").toString()
        tel=args?.getString("tel").toString()
        tel2=args?.getString("tel2").toString()
        stc=args?.getString("stc").toString()
        sAdSoyad=args?.getString("sAdSoyad").toString()
        bankaismi=args?.getString("bankaismi").toString()
        iban=args?.getString("iban").toString()
        mail=args?.getString("mail").toString()
       // Toast.makeText(requireContext(),"UNVAN::"+unvan, Toast.LENGTH_LONG).show()
            //resim bilgileri
        if (args?.getString("ruhsat_resim").toString() != "null") {
            resim_ruhsat = args?.getString("ruhsat_resim").toString()
        }
        if (args?.getString("km_resim").toString() != "null") {
            resim_km = args?.getString("km_resim").toString()
        }
        if (args?.getString("sagon_resim").toString() != "null") {
            resim_sagon = args?.getString("sagon_resim").toString()
        }
        if (args?.getString("solon_resim").toString() != "null") {
            resim_solon = args?.getString("solon_resim").toString()
        }
        if (args?.getString("sagarka_resim").toString() != "null") {
            resim_sagarka = args?.getString("sagarka_resim").toString()
        }
        if (args?.getString("solarka_resim").toString() != "null") {
            resim_solarka = args?.getString("solarka_resim").toString()
        }
        //sigorta
        sigorta=args?.getString("sigorta").toString()
        tutanak=args?.getString("tutanak").toString()
        police=args?.getString("police").toString()
        sorumluad=args?.getString("sorumluad").toString()
        sorumlutel=args?.getString("sorumlutel").toString()
        dosyano=args?.getString("dosyano").toString()
        ihbartarihi=args?.getString("ihbartarihi").toString()
        kontorolorad=args?.getString("kontrolorad").toString()
        kontorolortel=args?.getString("kontrolortel").toString()
        sigortasirketi=args?.getString("sigortasirketi").toString()
        sigortaadres=args?.getString("sigortaadres").toString()
        eksper=args?.getString("eksper").toString()
        aracturu=args?.getString("aracturu").toString()
        marka=args?.getString("marka").toString()
        model=args?.getString("model").toString()
        model_y=args?.getString("model_y").toString()
        modelvrs=args?.getString("modelvrs").toString()
        kasa_tipi=args?.getString("kasa_tipi").toString()
        km=args?.getString("km").toString()
        renk2=args?.getString("renk").toString()
        saseno=args?.getString("saseno").toString()
        motorno=args?.getString("motorno").toString()
        konum=args?.getString("konum").toString()
        durum=args?.getString("durum").toString()
        mua=args?.getString("mua").toString()
        tahmini_teslim_tarihi_s=args?.getString("tahmini_teslim_tarihi").toString()
        egzoz = args?.getString("egzoz").toString()
        trafik = args?.getString("trafik").toString()
        kasko = args?.getString("kasko").toString()
        text_kayit_bul(kadi,firma_id,plaka)

        btn_yapilacaklar.setOnClickListener{
            itemList_usta.clear()
            itemList_usta.add(getString(R.string.ustatanimlidegil))

            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle("İşçilik Giriniz!..")
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(R.layout.usta_sec_aciklama, null)
            val ediscilik=view.findViewById<EditText>(R.id.ediscilik)
            val spinner_usta=view.findViewById<Spinner>(R.id.spinner_usta)
            val urlsb = "&kadi=" + kadi
            var url = "https://pratikhasar.com/netting/mobil.php?tur=usta_getir_iscilik" + urlsb
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
                        val adapter: ArrayAdapter<String> =
                            ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice, itemList_usta)

                        spinner_usta.setAdapter(adapter)

                       // Toast.makeText(requireContext(),"spinner doldurma hatası",Toast.LENGTH_LONG).show()
                                        }

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
                if(ediscilik.getText().toString().isEmpty()){
                    Toast.makeText(requireContext(),getString(R.string.yapilacaklarigirin),Toast.LENGTH_LONG).show()
                }else{
                    istekEkle(firma_id,plaka,"ozel_id",ediscilik.getText().toString(),spinner_usta.selectedItem.toString())

                }

            }
            alertadd.show()
        }

        btn_cikis.setOnClickListener {
            val i= Intent(requireContext(), HomeActivity::class.java)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            startActivity(i)
        }
        btn_kaydet_.setOnClickListener{
            btn_kaydet_.isEnabled=false
            val imservis=Intent(requireContext(), HomeActivity::class.java)

            val queue = Volley.newRequestQueue(requireContext())
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                   // kartaGit()
                 //   cariVarMi(vergino)
                    imservis.putExtra("kadi", kadi)
                    imservis.putExtra("akadi", kadi)
                    imservis.putExtra("resim", resimyolu)

                    imservis.putExtra("vergino", vergino)
                    imservis.putExtra("unvan", unvan)
                    imservis.putExtra("dosyano", dosyano)
                    imservis.putExtra("kasatipi", kasa_tipi)
                    imservis.putExtra("modelyili", model_y)
                    imservis.putExtra("model", model)
                    imservis.putExtra("firma_id", firma_id)
                    imservis.putExtra("plaka", plaka)
                    imservis.putExtra("marka", marka)
                    imservis.putExtra("kullanici_id", kullnciid)
                    startActivity(imservis)
                    Toast.makeText(requireContext(),"Kayıt Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

                },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["kadi"]=kadi
                    params["firma_id"]=firma_id
                    params["plaka"]=plaka
                    params["arac_turu"] = aracturu
                    params["marka"] = marka
                    params["model"]=model
                    params["modelvers"]=modelvrs
                    params["modely"]=model_y
                    params["saseno"]=saseno
                    params["motorno"]=motorno
                    params["dosyano"]=dosyano
                    params["kasatipi"]=kasa_tipi
                    params["aracrenk"]=renk2
                    params["kmsaat"]=km
                    params["aracdurum"]=durum
                    params["arackonum"]=konum
                    params["unvan"]=unvan
                    params["vergino"]=vergino
                    params["surucutc"]=stc
                    params["vergidairesi"]=vergidairesi
                    params["il"]=il
                    params["ilce"]=ilce
                    params["adres"]=adres
                    params["tel1"]=tel
                    params["tel2"]=tel2
                    params["tel3"]=""
                    params["surucu"]=sAdSoyad
                    params["ehliyet"]=""
                    params["eserino"]=""
                    params["tutanakprofili"]=tutanak
                    params["sigortaprofili"]=sigorta
                    params["poltur"]=police
                    params["polno"]=""
                    params["dosyasorumlusu"]=sorumluad
                    params["sorumlutel"]=sorumlutel
                    params["kontrolor"]=kontorolorad
                    params["kontrolortel"]=kontorolortel
                    params["dosyano"]=dosyano
                    params["resim"]=resimyolu
                    params["resimtum"]=resimyolu
                    params["mail"]=mail
                    params["ruhsat_resim"]=resim_ruhsat
                    params["km_resim"]=resim_km
                    params["sagon_resim"]=resim_sagon
                    params["solon_resim"]=resim_solon
                    params["sagarka_resim"]=resim_sagarka
                    params["solarka_resim"]=resim_solarka
                    params["tahmini_teslim_tarihi"]=tahmini_teslim_tarihi_s
                    params["mua"]=mua
                    params["egzoz"]=egzoz
                    params["trafik"]=trafik
                    params["kasko"]=kasko
                    params["yakitturu"]=yakitturu
                    params["tur"] = "kartac_mekanik"

                    return params
                }
            }
            queue.add(postRequest)

        }

        btnAracBilgi_kart_ac.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            bundlemDoldur()
            val fragobj = MekanikAracBilgiKartAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
        }
        btnAracSahibi_kart_ac.setOnClickListener {
            btnAracSahibi_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)

            bundlemDoldur()

            val fragobj = MekanikAracSahibiKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()

        }
        btn_arac_kabul_kart_ac.setOnClickListener {
            btn_arac_kabul_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)

            bundlemDoldur()
            val fragobj = MekanikKabulAsamasiKartAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()

        }
        btn_istek_sikayet.setOnClickListener{
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            bundlem.putString("kadi",kadi)
            bundlemDoldur()
            val fragobj = IstekSikayetMekanikFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()

        }

        return view
    }

    private fun bundlemDoldur() {
        bundlem.putString("kadi",kadi)
        //arac bilgileri
        bundlem.putString("yakitturu",yakitturu)
        bundlem.putString("aracturu",aracturu)
        bundlem.putString("resimyolu",resimyolu)
        bundlem.putString("plaka",plaka)
        bundlem.putString("marka",marka)
        bundlem.putString("model",model)
        bundlem.putString("modelvrs",modelvrs)
        bundlem.putString("model_y",model_y)
        bundlem.putString("kasa_tipi",kasa_tipi)
        bundlem.putString("km",km)
        bundlem.putString("renk",renk2)
        bundlem.putString("saseno",saseno)
        bundlem.putString("motorno",motorno)
        bundlem.putString("dosyano",dosyano)
        bundlem.putString("konum",konum)
        bundlem.putString("durum",durum)
        bundlem.putString("mua",mua)
        bundlem.putString("tahmini_teslim_tarihi",tahmini_teslim_tarihi_s)
        bundlem.putString("egzoz",egzoz)
        bundlem.putString("trafik",trafik)
        bundlem.putString("kasko",kasko)


        //sahibi bilgileri
        bundlem.putString("unvan",unvan)
        bundlem.putString("vergino",vergino)
        bundlem.putString("vergidairesi",vergidairesi)
        bundlem.putString("adres",adres)
        bundlem.putString("il",il)
        bundlem.putString("ilce",ilce)
        bundlem.putString("tel",tel)
        bundlem.putString("tel2",tel2)
        bundlem.putString("stc",stc)
        bundlem.putString("sAdSoyad",sAdSoyad)
        bundlem.putString("bankaismi",bankaismi)
        bundlem.putString("iban",iban)
        bundlem.putString("mail",mail)



        //sigorta
        bundlem.putString("sigorta",sigorta)
        bundlem.putString("tutanak",tutanak)
        bundlem.putString("police",police)
        bundlem.putString("sorumluad",sorumluad)
        bundlem.putString("sorumlutel",sorumlutel)
        bundlem.putString("dosyano",dosyano)
        bundlem.putString("ihbartarihi",ihbartarihi)
        bundlem.putString("kontrolorad",kontorolorad)
        bundlem.putString("kontrolortel",kontorolortel)
        bundlem.putString("sigortasirketi",sigortasirketi)
        bundlem.putString("sigortaadres",sigortaadres)
        bundlem.putString("eksper",eksper)
        //resimler
        bundlem.putString("ruhsat_resim", resim_ruhsat)
        bundlem.putString("km_resim", resim_km)
        bundlem.putString("sagon_resim", resim_sagon)
        bundlem.putString("solon_resim", resim_solon)
        bundlem.putString("sagarka_resim", resim_sagarka)
        bundlem.putString("solarka_resim", resim_solarka)
    }

    private fun text_kayit_bul(kadi: String, firma: String,  plaka: String){
        val urlsb ="&kadi=" + kadi +"&firma_id="+firma+"&plaka="+ plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=istek_text_bul_iscilik"+urlsb
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
                        builder.setPositiveButton(R.string.evet) { dialog, which ->
                            text_kayit_sil(txtid.toString())
                            //  resimGetir(kadi,ozel_id)
                            //

                        }

                        builder.setNegativeButton(R.string.hayir) { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                            text_kayit_bul(kadi,firma_id,plaka)
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
                text_kayit_bul(kadi,firma_id,plaka)
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

    private fun istekEkle(firmaId: String, plaka: String, ozelId: String, iscilik: String, usta: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                text_kayit_bul(kadi,firma_id,plaka)
                Toast.makeText(requireContext(),"Ekleme başarılı",Toast.LENGTH_LONG).show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["kadi"] = kadi
                params["firma_id"] = firmaId
                params["ozel_id"] = ozelId
                params["plaka"] = plaka
                params["iscilik"] = iscilik
                params["usta"] = usta
                params["tur"] = "istek_iscilik_kaydet_mekanik"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun cariVarMi(vergino: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                kartaGit()
                cariVarMi(vergino)
                Toast.makeText(requireContext(),"Kayıt Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"]=kadi
                params["firma_id"]=firma_id
                params["unvan"]=unvan
                params["vergino"]=vergino
                params["surucutc"]=stc
                params["vergidairesi"]=vergidairesi
                params["il"]=il
                params["ilce"]=ilce
                params["adres"]=adres
                params["tel1"]=tel
                params["tel2"]=tel2
                params["tel3"]=""
                params["surucu"]=sAdSoyad
                params["ehliyet"]=""
                params["eserino"]=""
                params["tutanakprofili"]=tutanak
                params["sigortaprofili"]=sigorta
                params["poltur"]=police
                params["polno"]=""
                params["dosyasorumlusu"]=sorumluad
                params["sorumlutel"]=sorumlutel
                params["kontrolor"]=kontorolorad
                params["kontrolortel"]=kontorolortel
                params["dosyano"]=dosyano
                params["mail"]=mail
                params["tur"] = "kartac_cari_ekle"

                return params
            }
        }
        queue.add(postRequest)

    }

    private fun kartaGit() {
        val imservis=Intent(requireContext(),HomeActivity::class.java)

        val urlek="&kadi="+kadi+"&firma_id="+firma_id+"&plaka="+plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=plaka_bilgi_getir"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        Log.d("onarim",url)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->try{
                val json = response["plaka"] as JSONArray
                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)

                    imservis.putExtra("ozel_id", item.getString("ozel_id"))
                    imservis.putExtra("kadi", kadi)
                    imservis.putExtra("akadi", kadi)
                    imservis.putExtra("vergino", item.getString("vergino"))
                    imservis.putExtra("resim", item.getString("resim"))
                    imservis.putExtra("unvan", item.getString("unvan"))
                    imservis.putExtra("dosyano", item.getString("dosyano"))
                    imservis.putExtra("kasatipi", item.getString("kasatipi"))
                    imservis.putExtra("modelyili", item.getString("modelyili"))
                    imservis.putExtra("model", item.getString("model"))
                    imservis.putExtra("firma_id", firma_id)
                    imservis.putExtra("plaka", plaka)
                    imservis.putExtra("marka", marka)
                    imservis.putExtra("kullanici_id", kullnciid)
                    imservis.putExtra("kabulnom", item.getString("kabulnom"))

                }


            }catch (e:Exception) {

                imservis.putExtra("kadi", kadi)
                imservis.putExtra("akadi", kadi)
                imservis.putExtra("resim", resimyolu)

                imservis.putExtra("vergino", vergino)
                imservis.putExtra("unvan", unvan)
                imservis.putExtra("dosyano", dosyano)
                imservis.putExtra("kasatipi", kasa_tipi)
                imservis.putExtra("modelyili", model_y)
                imservis.putExtra("model", model)
                imservis.putExtra("firma_id", firma_id)
                imservis.putExtra("plaka", plaka)
                imservis.putExtra("marka", marka)
                imservis.putExtra("kullanici_id", kullnciid)
                startActivity(imservis)

            }
            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)


    }    private fun startRecording(output: String) {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(requireContext(), "Kayıt Başladı!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun readFileAsString(filePath: String?): String? {
        val reader = BufferedReader(FileReader(filePath))
        var line: String?
        var results: String? = ""
        while (reader.readLine().also { line = it } != null) {
            results += line
        }
        reader.close()
        return results
    }
    private fun onApiSes(output: String) {

        val ses = readFileAsString(output)
        // ...


       // Toast.makeText(requireContext(),output,Toast.LENGTH_LONG).show()

        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(requireContext(),"kayıt başarılı",Toast.LENGTH_LONG).show()
                //resimGetir(kadi,ozel_id)
                // firmaGetir(kadi,ozel_id)

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["firma_id"]=firma_id
                params["plaka"]=plaka
                params["ses"]= ses.toString()
                params["tur"] = "istek_ses_kaydet"
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
    private fun onApi(yapilacaklar: String, plaka: String) {
        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("ResourceType")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                //yapilacakGetir(kadi, ozel_id)
                text_kayit_bul(kadi,firma_id,plaka)
                val toast = Toast.makeText(
                    requireContext(),
                    "İş emri Eklendi",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0)
                toast.show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["plaka"] = plaka
                params["yapilacaklar"] = yapilacaklar
                params["tur"] = "yapilacaklar_kaydet"
                return params
            }
        }
        queue.add(postRequest)



    }



}