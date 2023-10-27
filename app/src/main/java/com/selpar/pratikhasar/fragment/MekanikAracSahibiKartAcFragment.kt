package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.HomeActivity
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MekanikAracSahibiKartAcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MekanikAracSahibiKartAcFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btn_arac_bilgi_kart_ac: Button
    lateinit var btn_arac_sahibi_kart_ac: Button
    lateinit var btn_arac_kabul_kart_ac: Button
    lateinit var btn_istek_sikayet: Button
    lateinit var btn_ilerle: Button
    var bundlem=Bundle()
    lateinit var kadi:String
    lateinit var firma_id:String
    lateinit var unvan:String
    val itemList_unvan: ArrayList<String> = ArrayList()
    val cari_list: ArrayList<String> = ArrayList()
    val itemList_vergino: ArrayList<String> = ArrayList()
    lateinit var etauto: AutoCompleteTextView
    lateinit var etautovergino: AutoCompleteTextView
    lateinit var etVergidairesi: EditText
    lateinit var etAdres: EditText
    lateinit var etil: EditText
    lateinit var etIlce_kart_ac: EditText
    lateinit var ettel_kart_ac: EditText
    lateinit var ettel2_kart_ac: EditText
    lateinit var etsTc_kart_ac: EditText
    lateinit var etsAdSoyad_kart_ac: EditText
    lateinit var etBankaİsmi_kart_ac: EditText
    lateinit var etIban_kart_ac: EditText
    lateinit var etmail_kart_ac: EditText
    //arac bilgileri
    lateinit var aracturu:String
    lateinit var marka:String
    lateinit var model:String
    lateinit var modelvers:String
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

    lateinit var resimyolu:String
    lateinit var vergino:String
    lateinit var vergidairesi:String
    lateinit var tel2:String
    lateinit var adres:String
    lateinit var il:String
    lateinit var ilce:String
    lateinit var tel:String
    lateinit var sTc:String
    lateinit var sAdSoyad:String
    lateinit var bankIsmi:String
    lateinit var mail:String
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    //resimler
    lateinit var resim_ruhsat:Bitmap

    var resim_ruhsat2: String = ""
    var resim_km: String = ""
    var resim_sagon: String = ""
    var resim_solon: String = ""
    var resim_sagarka: String = ""
    var resim_solarka: String = ""
    lateinit var btn_cikis:ImageView
    lateinit var btn_cari_sec:Button
    lateinit var btn_vergino_sec:Button
    lateinit var spinner_cari:Spinner
    lateinit var plaka:String
    lateinit var dosyano:String
    lateinit var yakitturu:String

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
        val view =inflater.inflate(R.layout.fragment_mekanik_arac_sahibi_kart_ac, container, false)
        val args= this.arguments
        plaka=args?.getString("plaka").toString()
        yakitturu=args?.getString("yakitturu").toString()

        kadi = args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullnciid = args?.getString("kullanici_id").toString()
        sifrem = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        dosyano = args?.getString("dosyano").toString()
        btn_vergino_sec=view.findViewById(R.id.btn_vergino_sec)
        btn_arac_bilgi_kart_ac=view.findViewById(R.id.btnAracBilgi_kart_ac)
        btn_arac_sahibi_kart_ac=view.findViewById(R.id.btnAracSahibi_kart_ac_)
        btn_arac_kabul_kart_ac=view.findViewById(R.id.btn_kabul_asamasi)
        btn_istek_sikayet=view.findViewById(R.id.btn_istek_sikayet)
        btn_ilerle=view.findViewById(R.id.btn_ilerle)
        btn_cari_sec=view.findViewById(R.id.btn_cari_sec)
        aracturu=args?.getString("aracturu").toString()
        resimyolu=args?.getString("resimyolu").toString()
        btn_cari_sec.setOnClickListener {
            bilgidoldur(etauto.getText().toString(),kadi)
          /*  val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle(getString(R.string.cari_sec))
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.cari_sec_layout, null)
            spinner_cari=view.findViewById(R.id.spinner_cari)
            val url ="https://pratikhasar.com/netting/mobil.php?tur=cari_bul_getir&firma_id="+firma_id

            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["cariler"] as JSONArray
                        // var resimgetir:String
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)

                            cari_list.add(item.getString("cari"))

                        }
                        val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                        for (i in cari_list.indices) {
                            spinner_konum_kart_ac_alspinner1.add(cari_list[i])
                        }
                        val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                            requireContext(),
                            R.layout.item_spinner_yag,
                            spinner_konum_kart_ac_alspinner1 as List<Any?>
                        )
                        spinner_cari.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)



                    } catch (e: Exception) {
                        /*
                        Toast.makeText(requireContext(), "MODEL doldur hatası", Toast.LENGTH_LONG)
                            .show()*/
                    }

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    /* in this case we are simply displaying a toast message.
                    Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                        .show()*/
                }
            )
            queue.add(request)





            //getLastLocation()
            alertadd.setView(view)
            alertadd.setPositiveButton(
                getString(R.string.evet)
            ) { dialogInterface, which ->
                veriDoldur(spinner_cari.selectedItem.toString())


            }
            alertadd.setNegativeButton(getString(R.string.hayir)){
                    dialog, which -> dialog.dismiss()
            }
            alertadd.show()*/
        }
        btn_vergino_sec.setOnClickListener {
            bilgidoldurVergino(etautovergino.getText().toString(),kadi)
        }
        mua=args?.getString("mua").toString()
        tahmini_teslim_tarihi_s=args?.getString("tahmini_teslim_tarihi").toString()
        egzoz = args?.getString("egzoz").toString()
        trafik = args?.getString("trafik").toString()
        kasko = args?.getString("kasko").toString()

        if(resimyolu=="null"){
            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle(R.string.resimgir)
            alertadd.setPositiveButton(
                "TAMAM"
            ) { dialog, which -> dialog.dismiss()
                btn_arac_bilgi_kart_ac.setBackgroundColor(Color.GRAY)
                btn_arac_sahibi_kart_ac.setBackgroundColor(0)
                btn_arac_kabul_kart_ac.setBackgroundColor(0)
                btn_istek_sikayet.setBackgroundColor(0)
                bundlem.putString("kadi", kadi)
                bundlem.putString("dilsecimi",dilsecimi)
                bundlem.putString("yetki", yetki)
                bundlem.putString("firma_id", firma_id)
                bundlem.putString("kullanici_id", kullnciid)
                bundlem.putString("sifre", sifrem)
                bundlem.putString("firma_id",firma_id)
                //arac bilgileri
                bundlem.putString("aracturu",aracturu)
                bundlem.putString("resimyolu",resimyolu)
                bundlem.putString("marka",marka)
                bundlem.putString("model",model)
                bundlem.putString("modelvrs",modelvers)
                bundlem.putString("model_y",model_y)
                bundlem.putString("yakitturu",yakitturu)
                bundlem.putString("kasa_tipi",kasa_tipi)
                bundlem.putString("km",km)
                bundlem.putString("plaka",plaka)
                bundlem.putString("renk",renk2)
                bundlem.putString("saseno",saseno)
                bundlem.putString("motorno",motorno)
                bundlem.putString("dosyano",dosyano)
                bundlem.putString("konum",konum)
                bundlem.putString("durum",durum)
                bundlem.putString("mua",mua)
                bundlem.putString("egzoz",egzoz)
                bundlem.putString("trafik",trafik)
                bundlem.putString("kasko",kasko)
                bundlem.putString("tahmini_teslim_tarihi", tahmini_teslim_tarihi_s)

                //sahibi bilgileri
                bundlem.putString("unvan",unvan)
                bundlem.putString("vergino",etautovergino.getText().toString())
                bundlem.putString("vergidairesi",etVergidairesi.getText().toString())
                if(etautovergino.getText().length<10){
                    val alertadd = AlertDialog.Builder(requireContext())
                    alertadd.setTitle(R.string.verginogir)
                    alertadd.setPositiveButton(
                        "TAMAM"
                    ) { dialog, which -> dialog.dismiss()
                    }
                    alertadd.show()

                }
                bundlem.putString("adres",etAdres.getText().toString() )
                bundlem.putString("il",etil.getText().toString())
                bundlem.putString("ilce",etIlce_kart_ac.getText().toString())
                bundlem.putString("tel",ettel_kart_ac.getText().toString())
                bundlem.putString("tel2",ettel2_kart_ac.getText().toString())
                bundlem.putString("stc",etsTc_kart_ac.getText().toString())
                bundlem.putString("sAdSoyad",etsAdSoyad_kart_ac.getText().toString())
                bundlem.putString("bankaismi",etBankaİsmi_kart_ac.getText().toString())
                bundlem.putString("iban",etIban_kart_ac.getText().toString())
                bundlem.putString("mail",etmail_kart_ac.getText().toString())
                //resimler
                bundlem.putString("ruhsat_resim", resim_ruhsat2)
                bundlem.putString("km_resim", resim_km)
                bundlem.putString("sagon_resim", resim_sagon)
                bundlem.putString("solon_resim", resim_solon)
                bundlem.putString("sagarka_resim", resim_sagarka)
                bundlem.putString("solarka_resim", resim_solarka)
                val fragobj = MekanikAracBilgiKartAcFragment()
                fragobj.arguments=bundlem
                // fragobj.arguments=bundlem
                //fragobj.setArguments(bundlem)
                val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mekanik_fragment,  fragobj)
                    .commit()

            }
            alertadd.show() }
        if(resimyolu==""){
            resim_error()
           // Toast.makeText(requireContext(),"RESİM BULUNMADI",Toast.LENGTH_LONG).show()
        }
        marka=args?.getString("marka").toString()
        model=args?.getString("model").toString()
        modelvers=args?.getString("modelvrs").toString()
        model_y=args?.getString("model_y").toString()
        kasa_tipi=args?.getString("kasa_tipi").toString()
        km=args?.getString("km").toString()
        renk2=args?.getString("renk").toString()
        saseno=args?.getString("saseno").toString()
        motorno=args?.getString("motorno").toString()
        bundlem.putString("dosyano",dosyano)
        konum=args?.getString("konum").toString()
        durum=args?.getString("durum").toString()

        etauto=view.findViewById(R.id.etauto)
        etautovergino=view.findViewById(R.id.etautovergino)
        etVergidairesi=view.findViewById(R.id.etVergi_dairesi_kart_ac)
        etAdres=view.findViewById(R.id.etAdres_kart_ac)
        etAdres.setOnClickListener {
            if(etautovergino.getText().length<10){
                val alertadd = AlertDialog.Builder(requireContext())
                alertadd.setTitle(R.string.verginogir)
                alertadd.setPositiveButton(
                    "TAMAM"
                ) { dialog, which -> dialog.dismiss()
                }
                alertadd.show()

            }
        }
        etil=view.findViewById(R.id.etIl_kart_ac)
        etIlce_kart_ac=view.findViewById(R.id.etIlce_kart_ac)
        ettel_kart_ac=view.findViewById(R.id.ettel_kart_ac)
        ettel2_kart_ac=view.findViewById(R.id.ettel2_kart_ac)
        etsTc_kart_ac=view.findViewById(R.id.etsTc_kart_ac)
        etsAdSoyad_kart_ac=view.findViewById(R.id.etsAdSoyad_kart_ac)
        etBankaİsmi_kart_ac=view.findViewById(R.id.etBankaİsmi_kart_ac)
        etIban_kart_ac=view.findViewById(R.id.etIban_kart_ac)
        etmail_kart_ac=view.findViewById(R.id.etmail_kart_ac)
        btn_cikis=view.findViewById(R.id.btn_cikis)
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
        unvan=args?.getString("unvan").toString()
        if(unvan!="null"){
           // bilgidoldur(unvan,kadi)
           // auto.setText(unvan)

        }
        //sahib bilgileri
        vergino=args?.getString("vergino").toString()

        if(args?.getString("adres").toString()!="null")
            etAdres.setText(args?.getString("adres"))
        if(args?.getString("il").toString()!="null")
            etil.setText(args?.getString("il").toString())
        if(args?.getString("ilce").toString()!="null")
            etIlce_kart_ac.setText(args?.getString("ilce").toString())
        if(args?.getString("tel").toString()!="null")
            ettel_kart_ac.setText(args?.getString("tel").toString())
        if(args?.getString("stc").toString()!="null")
            etsTc_kart_ac.setText(args?.getString("stc").toString())
       /* auto.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem: String =
                auto.getAdapter().getItem(position).toString()
            bilgidoldur(selectedItem, kadi)
            unvan = selectedItem
            Toast.makeText(
                requireContext(),
                unvan,
                Toast.LENGTH_SHORT
            ).show()
        })*/
        if (args?.getString("ruhsat_resim").toString() != "null") {
            resim_ruhsat2 = args?.getString("ruhsat_resim").toString()
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
        if(args?.getString("unvan").toString()!="null")
            etauto.setText(args?.getString("unvan").toString())
        if(args?.getString("vergino").toString()!="null")
            etautovergino.setText(args?.getString("vergino").toString())
        if(args?.getString("adres").toString()!="null")
            etAdres.setText(args?.getString("adres").toString())
        if(args?.getString("vergidairesi").toString()!="null")
            etVergidairesi.setText(args?.getString("vergidairesi").toString())
        if(args?.getString("il").toString()!="null")
            etil.setText(args?.getString("il").toString())
        if(args?.getString("ilce").toString()!="null")
            etIlce_kart_ac.setText(args?.getString("ilce").toString())
        if(args?.getString("tel").toString()!="null")
            ettel_kart_ac.setText(args?.getString("tel").toString())
        if(args?.getString("tel2").toString()!="null")
            ettel2_kart_ac.setText(args?.getString("tel2").toString())
        if(args?.getString("stc").toString()!="null")
            etsTc_kart_ac.setText(args?.getString("stc").toString())
        if(args?.getString("sAdSoyad").toString()!="null")
            etsAdSoyad_kart_ac.setText(args?.getString("sAdSoyad").toString())
        if(args?.getString("bankaismi").toString()!="null")
            etBankaİsmi_kart_ac.setText(args?.getString("bankaismi").toString())
        if(args?.getString("iban").toString()!="null")
            etIban_kart_ac.setText(args?.getString("iban").toString())
        if(args?.getString("mail").toString()!="null")
            etmail_kart_ac.setText(args?.getString("mail").toString())

        firmaIdGetir()

        btn_arac_bilgi_kart_ac.setBackgroundColor(0)
        btn_arac_sahibi_kart_ac.setBackgroundColor(Color.GRAY)
        btn_arac_kabul_kart_ac.setBackgroundColor(0)
        btn_istek_sikayet.setBackgroundColor(0)

        btn_istek_sikayet.setOnClickListener {
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            bundlemDoldur()
            val fragobj = IstekSikayetMekanikFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
        }
        btn_arac_bilgi_kart_ac.setOnClickListener {
            vergiNoTest()
            btn_arac_bilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            bundlemDoldur()
            val fragobj = MekanikAracBilgiKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
        }
        btn_ilerle.setOnClickListener {
            vergiNoTest()

            btn_istek_sikayet.setBackgroundColor(0)
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(Color.GRAY)
            bundlemDoldur()
            val fragobj = MekanikKabulAsamasiKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
        }
        btn_arac_sahibi_kart_ac.setOnClickListener {
            vergiNoTest()
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(Color.GRAY)
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
            vergiNoTest()
            btn_istek_sikayet.setBackgroundColor(0)
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(Color.GRAY)
            bundlemDoldur()
            val fragobj = MekanikKabulAsamasiKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
        }
        return view
    }

    private fun bundlemDoldur() {
        bundlem.putString("kadi", kadi)
        bundlem.putString("dilsecimi",dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kullanici_id", kullnciid)
        bundlem.putString("sifre", sifrem)
        bundlem.putString("firma_id",firma_id)
        //arac bilgileri
        bundlem.putString("yakitturu",yakitturu)
        bundlem.putString("aracturu",aracturu)
        bundlem.putString("resimyolu",resimyolu)
        bundlem.putString("marka",marka)
        bundlem.putString("model",model)
        bundlem.putString("modelvrs",modelvers)
        bundlem.putString("model_y",model_y)
        bundlem.putString("kasa_tipi",kasa_tipi)
        bundlem.putString("km",km)
        bundlem.putString("plaka",plaka)
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
        bundlem.putString("unvan",etauto.getText().toString())
        bundlem.putString("vergino",etautovergino.getText().toString())
        bundlem.putString("vergidairesi",etVergidairesi.getText().toString())
        if(etautovergino.getText().length<10){
            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle(R.string.verginogir)
            alertadd.setPositiveButton(
                "TAMAM"
            ) { dialog, which -> dialog.dismiss()
            }
            alertadd.show()

        }
        bundlem.putString("adres",etAdres.getText().toString() )
        bundlem.putString("il",etil.getText().toString())
        bundlem.putString("ilce",etIlce_kart_ac.getText().toString())
        bundlem.putString("tel",ettel_kart_ac.getText().toString())
        bundlem.putString("tel2",ettel2_kart_ac.getText().toString())
        bundlem.putString("stc",etsTc_kart_ac.getText().toString())
        bundlem.putString("sAdSoyad",etsAdSoyad_kart_ac.getText().toString())
        bundlem.putString("bankaismi",etBankaİsmi_kart_ac.getText().toString())
        bundlem.putString("iban",etIban_kart_ac.getText().toString())
        bundlem.putString("mail",etmail_kart_ac.getText().toString())
        //resimler
        bundlem.putString("ruhsat_resim", resim_ruhsat2)
        bundlem.putString("km_resim", resim_km)
        bundlem.putString("sagon_resim", resim_sagon)
        bundlem.putString("solon_resim", resim_solon)
        bundlem.putString("sagarka_resim", resim_sagarka)
        bundlem.putString("solarka_resim", resim_solarka)
    }

    private fun bilgidoldurVergino(etvergi: String, kadi: String) {
        val urlsb = "&kadi="+ kadi +"&vergino="+etvergi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=unvan_bilgi_vergino_getir" + urlsb
        Log.d("UNVANNNN",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["unvan_bilgi"] as JSONArray
                    val itemList: ArrayList<String> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        etauto.setText(item.getString("cari_unvan"))
                        etautovergino.setText(item.getString("vergino"))
                        etVergidairesi.setText(item.getString("vergidairesi"))
                        etAdres.setText(item.getString("adres"))
                        etil.setText(item.getString("il"))
                        etIlce_kart_ac.setText(item.getString("ilce"))
                        ettel_kart_ac.setText(item.getString("tel"))
                        ettel2_kart_ac.setText(item.getString("gsm"))
                        etsAdSoyad_kart_ac.setText(item.getString("yetkili_isim_soyisim"))
                        etIban_kart_ac.setText(item.getString("ibanno"))
                        etmail_kart_ac.setText(item.getString("mail"))
                        //itemList.add(unvan)
                    }


                }catch (e:Exception){
                   // Toast.makeText(requireContext(),"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun veriDoldur(spinnerCari: String)  {
        val urlsb = "&kadi="+ kadi +"&cari="+spinnerCari
        var url = "https://pratikhasar.com/netting/mobil.php?tur=bilgi_cari_getir" + urlsb
        Log.d("UNVANNNN",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["unvan_bilgi"] as JSONArray
                    val itemList: ArrayList<String> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        etauto.setText(spinnerCari)
                        etautovergino.setText(item.getString("vergino"))
                        etVergidairesi.setText(item.getString("vergidairesi"))
                        etAdres.setText(item.getString("adres"))
                        etil.setText(item.getString("il"))
                        etIlce_kart_ac.setText(item.getString("ilce"))
                        ettel_kart_ac.setText(item.getString("tel"))
                        ettel2_kart_ac.setText(item.getString("gsm"))
                        etsTc_kart_ac.setText(item.getString("tckimlik"))
                        etsAdSoyad_kart_ac.setText(item.getString("yetkili_isim_soyisim"))
                        etBankaİsmi_kart_ac.setText(item.getString("sbankaadi"))
                        etIban_kart_ac.setText(item.getString("siban"))
                        etmail_kart_ac.setText(item.getString("mail"))
                        //itemList.add(unvan)
                    }


                }catch (e:Exception){
              //      Toast.makeText(requireContext(),"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun vergiNoTest() {
        if(etautovergino.getText().length<10){
            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle(R.string.verginogir)
            alertadd.setPositiveButton(
                "TAMAM"
            ) { dialog, which -> dialog.dismiss()
            }
            alertadd.show()

        }
    }

    private fun resim_error() {
        val alertadd = AlertDialog.Builder(requireContext())
        alertadd.setTitle("Lütfen arac seçiniz!!!")

        alertadd.setPositiveButton(
            "Tamam"
        ) { dialogInterface, which ->
            btn_arac_bilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            bundlemDoldur()
            val fragobj = MekanikAracBilgiKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()


        }
        alertadd.show()
    }

    private fun firmaIdGetir(){

        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=firma_id_getir" + urlsb
        Log.d("RESİMYOL222",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{

                val json = response["firma_id"] as JSONArray
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    firma_id=item.getString("firma_id")
                    Log.d("FIRMA22: ", firma_id)
                    spinnerUnvanDoldur(firma_id)
                    spinnerVergiNoDoldur(firma_id)

                }}catch (e:Exception){}


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }

    private fun spinnerVergiNoDoldur(firmaId: String) {
        val urlsb = "&firma_id=" + firmaId
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_vergino_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_vergino.add( item.getString("vergino"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice, itemList_vergino)

                    etautovergino.setAdapter(adapter)

                }catch (e:Exception){
                    // Toast.makeText(requireContext(),"spinner doldurma hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }

    @SuppressLint("SuspiciousIndentation")
    private fun bilgidoldur(selectedItem: String, kadi: String)   {
     //   Toast.makeText(requireContext(),"unvan.:"+selectedItem,Toast.LENGTH_LONG).show()
        val urlsb = "&kadi="+ kadi +"&unvan="+selectedItem.replace(" ","%20")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=unvan_bilgi_cari_getir" + urlsb
        Log.d("UNVANNNN",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["unvan_bilgi"] as JSONArray
                    val itemList: ArrayList<String> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        etautovergino.setText(item.getString("vergino"))
                        etVergidairesi.setText(item.getString("vergidairesi"))
                        etAdres.setText(item.getString("adres"))
                        etil.setText(item.getString("il"))
                        etIlce_kart_ac.setText(item.getString("ilce"))
                        ettel_kart_ac.setText(item.getString("tel"))
                        ettel2_kart_ac.setText(item.getString("gsm"))
                        etsTc_kart_ac.setText(item.getString("tckimlik"))
                        etsAdSoyad_kart_ac.setText(item.getString("yetkili_isim_soyisim"))
                        etBankaİsmi_kart_ac.setText(item.getString("sbankaadi"))
                        etIban_kart_ac.setText(item.getString("siban"))
                        etmail_kart_ac.setText(item.getString("mail"))
                        //itemList.add(unvan)
                    }


                }catch (e:Exception){
                    //Toast.makeText(requireContext(),"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }
    private fun spinnerUnvanDoldur(firma_id:String)
    {
        val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        unvan = item.getString("cari")
                        itemList_unvan.add(unvan)
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice, itemList_unvan)

                    etauto.setAdapter(adapter)

                }catch (e:Exception){
                    // Toast.makeText(requireContext(),"spinner doldurma hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }



}