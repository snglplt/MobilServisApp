package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.HomeActivity
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AracSigortaKartAcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AracSigortaKartAcFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btnAracBilgi_kart_ac: Button
    lateinit var btnAracSahibi_kart_ac: Button
    lateinit var btnSigorta_kart_ac: Button
    lateinit var btn_istek_sikayet: Button
    lateinit var spinner_tutanak_profili_kart_ac: Spinner
    lateinit var spinner_sigorta_profili_kart_ac: Spinner
    lateinit var spinner_police_turu_kart_ac: Spinner
    lateinit var dosyaNo:EditText
    lateinit var ihbartarihi:EditText
    lateinit var kontroloraAdi:EditText
    lateinit var kontroloraTel:EditText
    lateinit var sigortaSirketi:EditText
    lateinit var eksper:EditText
    var bundlem=Bundle()
    lateinit var kadi:String
    val sigorta_alspinner1 = ArrayList<String>()
    val tutanak_alspinner1 = ArrayList<String>()
    val police_alspinner1=ArrayList<String>()
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

    lateinit var btn_arac_bilgi_ilerle:Button
    lateinit var firma_id:String
    lateinit var resimyolu:String
    lateinit var btn_cikis:ImageView
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var egzoz: String
    lateinit var trafik: String
    lateinit var kasko: String
    lateinit var yakitturu: String
    lateinit var plaka: String
    var dat_ihbar:String="null"
    var dat_kaza_tarihi:String="null"
    var dat_police_bitis_tarihi:String="null"
    lateinit var tutanak_tipi:TableRow
    lateinit var sigorta_tipi:TableRow
    lateinit var etpoliceNo:EditText
    lateinit var etpolicebitistarihi:EditText
    lateinit var etkazatarihi:EditText
    lateinit var eteksperadres:EditText
    lateinit var etekspertel:EditText
    lateinit var etekspermail:EditText





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_arac_sigorta_kart_ac, container, false)
        spinner_tutanak_profili_kart_ac=view.findViewById(R.id.spinner_tutanak_profili_kart_ac)
        spinner_sigorta_profili_kart_ac=view.findViewById(R.id.spinner_sigorta_profili_kart_ac)
        spinner_police_turu_kart_ac=view.findViewById(R.id.spinner_police_turu_kart_ac)
        btnAracSahibi_kart_ac=view.findViewById(R.id.btnAracSahibi_kart_ac_)
        btnSigorta_kart_ac=view.findViewById(R.id.btn_kabul_asamasi)
        btnAracBilgi_kart_ac=view.findViewById(R.id.btnAracBilgi_kart_ac)
        btn_arac_bilgi_ilerle=view.findViewById(R.id.btn_arac_bilgi_ilerle)
        dosyaNo=view.findViewById(R.id.etdosyano)
        ihbartarihi=view.findViewById(R.id.etihbartarihi)
        tutanak_tipi=view.findViewById(R.id.tutanak_tipi)
        sigorta_tipi=view.findViewById(R.id.sigorta_tipi)
        etpoliceNo=view.findViewById(R.id.etpoliceNo)
        etpolicebitistarihi=view.findViewById(R.id.etpolicebitistarihi)
        etkazatarihi=view.findViewById(R.id.etkazatarihi)
        eteksperadres=view.findViewById(R.id.eteksperadres)
        etekspertel=view.findViewById(R.id.etekspertel)
        etekspermail=view.findViewById(R.id.etekspermail)
        etkazatarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        dat_kaza_tarihi = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        etkazatarihi.setText(dat_gosterilen)

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
        etpolicebitistarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        dat_police_bitis_tarihi = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        etpolicebitistarihi.setText(dat_gosterilen)
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

        ihbartarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        dat_ihbar = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        ihbartarihi.setText(dat_gosterilen)
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

        sigortaSirketi=view.findViewById(R.id.etsigortasirketi)
        eksper=view.findViewById(R.id.etsigortaeksperi)
        btn_cikis=view.findViewById(R.id.btn_cikis)
        btn_istek_sikayet=view.findViewById(R.id.btn_istek_sikayet)
        btn_istek_sikayet.setBackgroundColor(0)
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
        btnAracBilgi_kart_ac.setBackgroundColor(0)
        btnAracSahibi_kart_ac.setBackgroundColor(0)
        btnSigorta_kart_ac.setBackgroundColor(Color.GRAY)
        val args= this.arguments
        mua=args?.getString("mua").toString()
        tahmini_teslim_tarihi_s=args?.getString("tahmini_teslim_tarihi").toString()
        egzoz = args?.getString("egzoz").toString()
        trafik = args?.getString("trafik").toString()
        kasko = args?.getString("kasko").toString()
        plaka=args?.getString("plaka").toString()
        kadi = args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullnciid = args?.getString("kullanici_id").toString()
        sifrem = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        yakitturu = args?.getString("yakit_turu").toString()
        var sigorta=args?.getString("sigorta").toString()
        if(sigorta!="null")
        sigorta_alspinner1.add(sigorta)
        var tutanak=args?.getString("tutanak").toString()
        if(tutanak!="null")
            tutanak_alspinner1.add(tutanak)
        var police=args?.getString("police").toString()
        if(police!="null")
            police_alspinner1.add(police)
        val sorumluad=args?.getString("sorumluad").toString()

        if(args?.getString("dosyano").toString()!="null")
            dosyaNo.setText(args?.getString("dosyano").toString())
        dat_ihbar=args?.getString("ihbartarihi").toString()
        dat_kaza_tarihi=args?.getString("kazatarihi").toString()
        dat_police_bitis_tarihi=args?.getString("policebitiistarihi").toString()
        if(dat_police_bitis_tarihi!="null"){
            var mua_array=dat_police_bitis_tarihi.split("-")
            etpolicebitistarihi.setText(mua_array[2]+"-"+mua_array[1]+"-"+mua_array[0])
        }
        if(dat_ihbar!="null"){
            var mua_array=dat_ihbar.split("-")
            ihbartarihi.setText(mua_array[2]+"-"+mua_array[1]+"-"+mua_array[0])
        }
        if(dat_kaza_tarihi!="null"){
            var mua_array=dat_kaza_tarihi.split("-")
            etkazatarihi.setText(mua_array[2]+"-"+mua_array[1]+"-"+mua_array[0])
        }

        if(args?.getString("eksperadres").toString()!="null")
            eteksperadres.setText(args?.getString("eksperadres").toString())
        if(args?.getString("ekspermail").toString()!="null")
            etekspermail.setText(args?.getString("eksperadres").toString())
        if(args?.getString("ekspertel").toString()!="null")
            etekspertel.setText(args?.getString("ekspertel").toString())
        if(args?.getString("policeno").toString()!="null")
            etpoliceNo.setText(args?.getString("policeno").toString())
         if(args?.getString("sigortasirketi").toString()!="null")
             sigortaSirketi.setText(args?.getString("sigortasirketi").toString())

        if(args?.getString("eksper").toString()!="null")
            eksper.setText(args?.getString("eksper").toString())
        //sahibi bilgileri
        unvan=args?.getString("unvan").toString()
        vergino=args?.getString("vergino").toString()
        vergidairesi=args?.getString("vergidairesi").toString()
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
        //arac ilgileri
        resimyolu=args?.getString("resimyolu").toString()
        firma_id=args?.getString("firma_id").toString()
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
        val sigorta_spvalue1 = this.resources.getStringArray(com.selpar.pratikhasar.R.array.sigorta_tipi)

        val tutanak_spvalue1 = this.resources.getStringArray(com.selpar.pratikhasar.R.array.tuttanak_tipi)
        for (i in tutanak_spvalue1.indices) {
            tutanak_alspinner1.add(tutanak_spvalue1[i])
        }
        val tutanak_adapter1: Any? = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            tutanak_alspinner1 as List<Any?>
        )
        spinner_tutanak_profili_kart_ac.setAdapter(tutanak_adapter1 as SpinnerAdapter?)
        for (i in sigorta_spvalue1.indices) {
            sigorta_alspinner1.add(sigorta_spvalue1[i])
        }
        val sigorta_adapter1: Any? = ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sigorta_alspinner1 as List<Any?>
        )
        spinner_sigorta_profili_kart_ac.setAdapter(sigorta_adapter1 as SpinnerAdapter?)
        val police_value1=this.resources.getStringArray(R.array.police_turu)
        for(i in police_value1.indices){
            police_alspinner1.add(police_value1[i])
        }
        val police_adapter1:Any?=ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            police_alspinner1 as List<Any?>
        )
        spinner_police_turu_kart_ac.setAdapter(police_adapter1 as SpinnerAdapter?)
        spinner_police_turu_kart_ac?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if(selectedItem=="Özel Hasar"){
                    tutanak_tipi.visibility=GONE
                    sigorta_tipi.visibility=GONE
                }else{
                    tutanak_tipi.visibility= VISIBLE
                    sigorta_tipi.visibility=VISIBLE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btn_istek_sikayet.setOnClickListener{
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            bundlemDoldur()
            val fragobj = IstekSikayetAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()
        }
        btnAracBilgi_kart_ac.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(0)
            bundlemDoldur()
            val fragobj = AracBilgiKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()
        }
        btnSigorta_kart_ac.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(Color.GRAY)
            bundlemDoldur()
            val fragobj = AracSigortaKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()
        }
        btnAracSahibi_kart_ac.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(Color.GRAY)
            btnSigorta_kart_ac.setBackgroundColor(0)

            bundlemDoldur()

            val fragobj = AracSahibiKartAcFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()
        }
        btn_arac_bilgi_ilerle.setOnClickListener{
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            bundlemDoldur()
            val fragobj = IstekSikayetAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()
        }




        // Inflate the layout for this fragment




        return view

    }

    private fun bundlemDoldur() {
        bundlem.putString("kadi", kadi)
        bundlem.putString("dilsecimi",dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kullanici_id", kullnciid)
        bundlem.putString("sifre", sifrem)
        //arac bilgileri
        bundlem.putString("aracturu",aracturu)
        bundlem.putString("resimyolu",resimyolu)
        bundlem.putString("plaka",plaka)
        bundlem.putString("marka",marka)
        bundlem.putString("model",model)
        bundlem.putString("modelvrs",modelvrs)
        bundlem.putString("model_y",model_y)
        bundlem.putString("yakit_turu",yakitturu)
        bundlem.putString("kasa_tipi",kasa_tipi)
        bundlem.putString("km",km)
        bundlem.putString("renk",renk2)
        bundlem.putString("saseno",saseno)
        bundlem.putString("motorno",motorno)
        bundlem.putString("konum",konum)
        bundlem.putString("durum",durum)
        bundlem.putString("tahmini_teslim_tarihi",tahmini_teslim_tarihi_s)
        bundlem.putString("mua",mua)
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
        bundlem.putString("sigorta",spinner_sigorta_profili_kart_ac.selectedItem.toString())
        bundlem.putString("tutanak",spinner_tutanak_profili_kart_ac.selectedItem.toString())
        bundlem.putString("police",spinner_police_turu_kart_ac.selectedItem.toString())

        bundlem.putString("dosyano",dosyaNo.getText().toString())
        bundlem.putString("ihbartarihi",dat_ihbar)


        bundlem.putString("sigortasirketi",sigortaSirketi.getText().toString())
        bundlem.putString("eksper",eksper.getText().toString())
        bundlem.putString("eksperadres",eteksperadres.getText().toString())
        bundlem.putString("ekspertel",etekspertel.getText().toString())
        bundlem.putString("ekspermail",etekspermail.getText().toString())
        bundlem.putString("policeno",etpoliceNo.getText().toString())
        bundlem.putString("policebitiistarihi",dat_police_bitis_tarihi)
        bundlem.putString("kazatarihi",dat_kaza_tarihi)
    }


}