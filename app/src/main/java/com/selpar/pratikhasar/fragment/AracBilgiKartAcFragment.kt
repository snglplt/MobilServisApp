package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.HomeActivity
import com.selpar.pratikhasar.ui.ScanCode2Activity
import com.selpar.pratikhasar.ui.ScanCode3Activity
import com.squareup.picasso.Picasso
import org.json.JSONArray


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AracBilgiKartAcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AracBilgiKartAcFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var deneme:String
    lateinit var btn_arac_bilgi_ilerle:Button
    lateinit var btnAracBilgi_kart_ac:Button
    lateinit var btnAracSahibi_kart_ac:Button
    lateinit var btnSigorta_kart_ac:Button
    lateinit var btn_istek_sikayet:Button
    lateinit var txt_plaka_no:EditText
    lateinit var spinner_renk_kart_ac:Spinner
    lateinit var spinner_kasa_tipi_kart_ac:Spinner
    lateinit var kadi:String
    var bundlem=Bundle()

    lateinit var txt_model_y_kart_ac: EditText
    lateinit var image_qr:ImageView
    lateinit var spinner_arac_turu: Spinner
    lateinit var spinner_durum_kart_ac: Spinner
    lateinit var spinner_konum_kart_ac: Spinner
    lateinit var spinner_marka_kart_ac: Spinner
    lateinit var spinner_model_kart_ac: Spinner
    lateinit var spinner_modelVrs_kart_ac: Spinner

    lateinit var renk:String
    lateinit var arackasa:String
    lateinit var resimgetir:String
    lateinit var kart_ac_image:ImageView
    lateinit var kmsaat_kart_ac_:EditText
    lateinit var saseno_kart_ac_:EditText
    lateinit var txt_motor_no_:EditText
    lateinit var mua_bitis_tarihi:EditText
    lateinit var txt_saseno_kart_ac_:EditText
    lateinit var spinner_yakit_turu_sp:Spinner
    val itemList_marka: ArrayList<String> = ArrayList()
    val itemList_model: ArrayList<String> = ArrayList()
    val itemList_yakit_turu: ArrayList<String> = ArrayList()
    lateinit var kasa_tipi:String
    lateinit var km:String
    lateinit var renk2:String
    lateinit var saseno:String
    lateinit var motorno:String
    lateinit var konum:String
    lateinit var durum:String
    lateinit var mua:String
    val itemList_kasa: ArrayList<String> = ArrayList()
    val spinner_kasa_tipi_kart_ac_alspinner1= ArrayList<String>()
    val itemList_renk: ArrayList<String> = ArrayList()
    val spinner_durum_kart_ac_alspinner1= ArrayList<String>()
    val spinner_konum_kart_ac_alspinner1= ArrayList<String>()
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
    lateinit var btn_cikis:ImageView
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var firma_id:String
    lateinit var tahmini_teslim_tarihi_s:String
    lateinit var tahmini_teslim_tarihi:EditText
    lateinit var egzoz_bitis_tarihi: EditText
    lateinit var trafik_sigortasi_tarihi: EditText
    lateinit var kasko_sigortasi_tarihi: EditText
    lateinit var egzoz: String
    lateinit var trafik: String
    lateinit var kasko: String
    var marka:String="null"
    var model:String="null"
    var modelvrs:String="null"
    val itenlist_arac_turu= ArrayList<String>()
    val itemList_vrs= ArrayList<String>()
    var dat_mua:String="null"
    var dat_egzoz:String="null"
    var dat_trafik:String="null"
    var dat_kasko:String="null"
    var dat_tahmini_teslim:String="null"
    lateinit var txt_sase_karakter:TextView
    lateinit var policeno: String
    lateinit var policebitiistarihi: String
    lateinit var ekspertel: String
    lateinit var eksperadres: String
    lateinit var ekspermail: String
    lateinit var kazatarihi: String
    lateinit var yakit_turu: String



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
        val view=inflater.inflate(R.layout.fragment_arac_bilgi_kart_ac, container, false)
        image_qr=view.findViewById(R.id.image_qr)
        txt_sase_karakter=view.findViewById(R.id.txt_sase_karakter)
        tahmini_teslim_tarihi=view.findViewById(R.id.tahmini_teslim_tarihi)
        image_qr.setImageDrawable(getResources().getDrawable(R.drawable.qrcode))

        val args= this.arguments

        kadi=args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullnciid = args?.getString("kullanici_id").toString()
        sifrem = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        if (args?.getString("aracturu").toString() != "null") {
            itenlist_arac_turu.clear()
            itenlist_arac_turu.add(args?.getString("aracturu").toString())
        }
        if (args?.getString("marka").toString() != "null") {
            marka=args?.getString("marka").toString()
        }
        if (args?.getString("model").toString() != "null") {
            model=args?.getString("model").toString()
        }
        if (args?.getString("modelvrs").toString() != "null") {
            modelvrs=args?.getString("modelvrs").toString()
        }
        policeno=args?.getString("policeno").toString()
        policebitiistarihi=args?.getString("policebitiistarihi").toString()
        ekspertel=args?.getString("ekspertel").toString()
        eksperadres=args?.getString("eksperadres").toString()
        ekspermail=args?.getString("ekspermail").toString()
        kazatarihi=args?.getString("kazatarihi").toString()
        btn_arac_bilgi_ilerle=view.findViewById(R.id.btn_arac_bilgi_ilerle)
        btnAracSahibi_kart_ac=view.findViewById(R.id.btnAracSahibi_kart_ac_)
        btnSigorta_kart_ac=view.findViewById(R.id.btn_kabul_asamasi)
        btnAracBilgi_kart_ac=view.findViewById(R.id.btnAracBilgi_kart_ac)
        btn_istek_sikayet=view.findViewById(R.id.btn_istek_sikayet)
        btn_istek_sikayet.setBackgroundColor(0)
        btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
        btnAracSahibi_kart_ac.setBackgroundColor(0)
        btnSigorta_kart_ac.setBackgroundColor(0)
        btnAracBilgi_kart_ac=view.findViewById(R.id.btnAracBilgi_kart_ac)
        txt_plaka_no=view.findViewById(R.id.txt_plaka_no)
        if(args?.getString("plaka").toString()!="null"){
            txt_plaka_no.setText(args?.getString("plaka").toString())
        }

        spinner_yakit_turu_sp=view.findViewById(R.id.spinner_yakit_turu)
        spinner_renk_kart_ac=view.findViewById(R.id.spinner_renk_kart_ac)
        spinner_marka_kart_ac=view.findViewById(R.id.spinner_marka_kart_ac)
        spinner_modelVrs_kart_ac=view.findViewById(R.id.spinner_modelVrs_kart_ac)
        kart_ac_image=view.findViewById(R.id.kart_ac_image)
        kmsaat_kart_ac_=view.findViewById(R.id.kmsaat_kart_ac_)
        kmsaat_kart_ac_.inputType= InputType.TYPE_CLASS_NUMBER

        txt_model_y_kart_ac=view.findViewById(R.id.txt_model_y_kart_ac)
        spinner_arac_turu=view.findViewById(R.id.spinner_arac_turu)
        spinner_durum_kart_ac=view.findViewById(R.id.spinner_durum_kart_ac)
        spinner_konum_kart_ac=view.findViewById(R.id.spinner_konum_kart_ac)
        spinner_kasa_tipi_kart_ac=view.findViewById(R.id.spinner_kasa_tipi_kart_ac)
        spinner_model_kart_ac=view.findViewById(R.id.spinner_model_kart_ac)
        saseno_kart_ac_=view.findViewById(R.id.txt_saseno_kart_ac_)
        egzoz_bitis_tarihi = view.findViewById(R.id.egzoz_bitis_tarihi)
        mua_bitis_tarihi = view.findViewById(R.id.mua_bitis_tarihi)
        trafik_sigortasi_tarihi = view.findViewById(R.id.trafik_sigortasi_tarihi)
        kasko_sigortasi_tarihi = view.findViewById(R.id.kasko_sigortasi_tarihi)
       // saseno_kart_ac_=view.findViewById(R.id.txt_saseno_kart_ac_)
        txt_motor_no_=view.findViewById(R.id.txt_motor_no_)
        //txt_plaka_no.setText(args?.getString("plaka").toString())
        image_qr.setOnClickListener {
            val i = Intent(requireContext(), ScanCode3Activity::class.java)
            i.putExtra("kadi", kadi)
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("sifre", sifrem)
            //arac bilgileri
            i.putExtra("aracturu", spinner_arac_turu.selectedItem.toString())
            i.putExtra("plaka", txt_plaka_no.getText().toString())
            if(args?.getString("plaka").toString()!="null"){
                txt_plaka_no.setText(args?.getString("plaka").toString())
            }
            if (spinner_marka_kart_ac.selectedItem != null)
                i.putExtra("marka", spinner_marka_kart_ac.selectedItem.toString())
            if (spinner_model_kart_ac.selectedItem != null)
                i.putExtra("model", spinner_model_kart_ac.selectedItem.toString()!!)
            i.putExtra("model_y", txt_model_y_kart_ac.getText().toString())
            if (spinner_modelVrs_kart_ac.selectedItem != null)
                i.putExtra("modelvrs", spinner_modelVrs_kart_ac.selectedItem.toString())
            if (spinner_kasa_tipi_kart_ac.selectedItem != null)
                i.putExtra("kasa_tipi", spinner_kasa_tipi_kart_ac.selectedItem.toString())
            i.putExtra("km", kmsaat_kart_ac_.getText().toString())
            if (spinner_renk_kart_ac.selectedItem != null)
                bundlem.putString("renk", spinner_renk_kart_ac.selectedItem.toString())
            i.putExtra("saseno", saseno_kart_ac_.getText().toString())
            i.putExtra("motorno", txt_motor_no_.getText().toString())
            if (spinner_konum_kart_ac.selectedItem != null)
                i.putExtra("konum", spinner_konum_kart_ac.selectedItem.toString())
            if (spinner_durum_kart_ac.selectedItem != null)
                i.putExtra("durum", spinner_durum_kart_ac.selectedItem.toString())
            i.putExtra("mua", dat_mua)
            i.putExtra("tahmini_teslim_tarihi", dat_tahmini_teslim)
            i.putExtra("egzoz", dat_egzoz)
            i.putExtra("trafik", dat_trafik)
            i.putExtra("kasko", dat_kasko)
            i.putExtra("unvan", unvan)
            i.putExtra("unvan", unvan)
            //sahibi bilgileri
            i.putExtra("unvan", unvan)
            i.putExtra("vergino", vergino)
            i.putExtra("vergidairesi", vergidairesi)
            i.putExtra("tel2", tel2)
            i.putExtra("adres", adres)
            i.putExtra("il", il)
            i.putExtra("ilce", ilce)
            i.putExtra("tel", tel)
            i.putExtra("stc", stc)
            i.putExtra("sAdSoyad", sAdSoyad)
            i.putExtra("bankaismi", bankaismi)
            i.putExtra("iban", iban)
            i.putExtra("mail", mail)


            startActivity(i)
        }
        if(args?.getString("gelenplaka").toString()!="null" && args?.getString("gelenplaka").toString()!=""){
            val array_plaka=args?.getString("gelenplaka").toString().split("-")

            try{
                txt_plaka_no.setText(array_plaka[1].toString())
            }catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.plaka_bulunamadi),Toast.LENGTH_LONG).show()

            }}
        mua_bitis_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        dat_mua = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        mua_bitis_tarihi.setText(dat_gosterilen)
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
        tahmini_teslim_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        dat_tahmini_teslim = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        tahmini_teslim_tarihi.setText(dat_gosterilen)
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
        egzoz_bitis_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        dat_egzoz = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        egzoz_bitis_tarihi.setText(dat_gosterilen)
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
        trafik_sigortasi_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        dat_trafik = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        trafik_sigortasi_tarihi.setText(dat_gosterilen)
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
        kasko_sigortasi_tarihi.setOnFocusChangeListener { _, hasFocus ->
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
                        dat_kasko = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        kasko_sigortasi_tarihi.setText(dat_gosterilen)

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
        txt_saseno_kart_ac_=view.findViewById(R.id.txt_saseno_kart_ac_)
        saseno_kart_ac_.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Called when the text is changing
                txt_sase_karakter.setText(getString(R.string.saseno) +" "+saseno_kart_ac_.getText().toString().length)
            }

            override fun afterTextChanged(s: Editable?) {
                // Called after the text has been changed
                val modifiedText = s.toString()
                // Perform any desired operations with the modified text
            }
        })
        btn_cikis=view.findViewById(R.id.btn_cikis)
        btn_cikis.setOnClickListener {
            val i= Intent(requireContext(),HomeActivity::class.java)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            startActivity(i)
        }
        deneme="test"
        kadi=args?.getString("kadi").toString()

        if(args?.getString("marka")!=null)
            itemList_marka.add(args?.getString("marka").toString())
        if(args?.getString("model").toString()!="null")
            itemList_model.add(args?.getString("model").toString())
        if(args?.getString("kasa_tipi").toString()!="null")
            spinner_kasa_tipi_kart_ac_alspinner1.add(args?.getString("kasa_tipi").toString())
        kasa_tipi=args?.getString("kasa_tipi").toString()
        if (kasa_tipi!="null")
            itemList_kasa.add(kasa_tipi)
        if(args?.getString("renk").toString()!="null")
            itemList_renk.add(args?.getString("renk").toString())
        if(args?.getString("durum").toString()!="null")
            spinner_durum_kart_ac_alspinner1.add(args?.getString("durum").toString())
        if(args?.getString("konum").toString()!="null")
            spinner_konum_kart_ac_alspinner1.add(args?.getString("konum").toString())
        saseno=args?.getString("saseno").toString()
        km=args?.getString("km").toString()
        motorno=args?.getString("motorno").toString()
        mua=args?.getString("mua").toString()
        yakit_turu=args?.getString("yakit_turu").toString()
        if(yakit_turu!="null"){
            itemList_yakit_turu.clear()
            itemList_yakit_turu.add(yakit_turu)
        }
        tahmini_teslim_tarihi_s=args?.getString("tahmini_teslim_tarihi").toString()
        if(km=="null"){
            kmsaat_kart_ac_.setText("")
        }else
        kmsaat_kart_ac_.setText(km)
        if(saseno=="null"){
            txt_saseno_kart_ac_.setText("")
        }
        else
            txt_saseno_kart_ac_.setText(saseno)
        if (motorno=="null"){
            txt_motor_no_.setText("")
        }
        else
            txt_motor_no_.setText(motorno)
        if (mua=="null"){
            mua_bitis_tarihi.setText("")
        }
        else {
            var mua_array=mua.split("-")
            mua_bitis_tarihi.setText(mua_array[2]+"-"+mua_array[1]+"-"+mua_array[0])
        }
        if (tahmini_teslim_tarihi_s=="null"){
            tahmini_teslim_tarihi.setText("")
        }
        else {
            var array=tahmini_teslim_tarihi_s.split("-")
            tahmini_teslim_tarihi.setText(array[2]+"-"+array[1]+"-"+array[0])
        }
        mua = args?.getString("mua").toString()
        egzoz = args?.getString("egzoz").toString()
        trafik = args?.getString("trafik").toString()
        kasko = args?.getString("kasko").toString()
        tahmini_teslim_tarihi_s = args?.getString("tahmini_teslim_tarihi").toString()
        if (egzoz == "null") {
            egzoz_bitis_tarihi.setText("")
        } else {
            var array=egzoz.split("-")
            egzoz_bitis_tarihi.setText(array[2]+"-"+array[1]+"-"+array[0])

        }
        if (trafik == "null") {
            trafik_sigortasi_tarihi.setText("")
        } else {
            var array=trafik.split("-")
            trafik_sigortasi_tarihi.setText(array[2]+"-"+array[1]+"-"+array[0])
        }
        if (kasko == "null") {
            kasko_sigortasi_tarihi.setText("")
        } else {
            var array=kasko.split("-")
            kasko_sigortasi_tarihi.setText(array[2]+"-"+array[1]+"-"+array[0])
        }

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
       // Toast.makeText(requireContext(),"UNVAN::"+unvan,Toast.LENGTH_LONG).show()
        renk_getir()
        arac_kasa_getir()

        if(args?.getString("model_y").toString()!="null")
            txt_model_y_kart_ac.setText(args?.getString("model_y").toString())
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
        bundlem.putString("eksperadres",eksperadres)
        bundlem.putString("ekspertel",ekspertel)
        bundlem.putString("ekspermail",ekspermail)
        bundlem.putString("policeno",policeno)
        bundlem.putString("policebitiistarihi",policebitiistarihi)
        bundlem.putString("kazatarihi",kazatarihi)
        /*
            val spinner_model_y_kart_ac_alspinner1= ArrayList<Int>()

        for(x in 1960..2024){
            spinner_model_y_kart_ac_alspinner1.add(x)
        }
        val adapter: ArrayAdapter<Int> =
            ArrayAdapter<Int>(requireContext(), android.R.layout.select_dialog_singlechoice, spinner_model_y_kart_ac_alspinner1)
        spinner_model_y_kart_ac.setAdapter(adapter)*/
        val spinner_yakit_turu=this.resources.getStringArray(R.array.yakit_turu)
        for(i in spinner_yakit_turu.indices){
            itemList_yakit_turu.add(spinner_yakit_turu[i])
        }
        val spinner_yakit_turu_:Any?= ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            itemList_yakit_turu as List<Any?>
        )
        spinner_yakit_turu_sp.setAdapter(spinner_yakit_turu_ as SpinnerAdapter?)

        val spinner_durum_kart_ac_value1=this.resources.getStringArray(R.array.durum)
        for(i in spinner_durum_kart_ac_value1.indices){
            spinner_durum_kart_ac_alspinner1.add(spinner_durum_kart_ac_value1[i])
        }
        val spinner_durum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            spinner_durum_kart_ac_alspinner1 as List<Any?>
        )
        spinner_durum_kart_ac.setAdapter(spinner_durum_kart_ac_adapter1 as SpinnerAdapter?)
        val spinner_arac_turu_police_value1=this.resources.getStringArray(R.array.arac_turu)
        for(i in spinner_arac_turu_police_value1.indices){
            itenlist_arac_turu.add(spinner_arac_turu_police_value1[i])
        }
        val spinner_arac_turu_police_adapter1:Any?= ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            itenlist_arac_turu as List<Any?>
        )
        spinner_arac_turu.setAdapter(spinner_arac_turu_police_adapter1 as SpinnerAdapter?)
        val spinner_konum_kart_ac_value1=this.resources.getStringArray(R.array.konum)
        for(i in spinner_konum_kart_ac_value1.indices){
            spinner_konum_kart_ac_alspinner1.add(spinner_konum_kart_ac_value1[i])
        }
        val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            spinner_konum_kart_ac_alspinner1 as List<Any?>
        )
        spinner_konum_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)
       /* val spinner_kasa_tipi_kart_ac_value1=this.resources.getStringArray(R.array.kasa_tipi)
        for(i in spinner_konum_kart_ac_value1.indices){
            spinner_kasa_tipi_kart_ac_alspinner1.add(spinner_kasa_tipi_kart_ac_value1[i])
        }
        val spinner_kasa_tipi_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinner_kasa_tipi_kart_ac_alspinner1 as List<Any?>
        )
        spinner_kasa_tipi_kart_ac.setAdapter(spinner_kasa_tipi_kart_ac_adapter1 as SpinnerAdapter?)
*/

        btn_arac_bilgi_ilerle.setOnClickListener{
           bundlemDoldur()
           // Toast.makeText(requireContext(),"kasa tipi "+kasa_tipi,Toast.LENGTH_LONG).show()

            val fragobj = AracSahibiKartAcFragment()
            fragobj.arguments=bundlem
           // fragobj.bilgigetir(spinner_marka_kart_ac.selectedItem.toString(),spinner_model_kart_ac.selectedItem.toString(),kasa_tipi)


            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
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
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()



        }
        btnAracSahibi_kart_ac.setOnClickListener {
            btnAracSahibi_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(0)
            bundlemDoldur()








            // bundlem.putStringlateinit var km:String
            //    lateinit var renk2:String
            //    lateinit var saseno:String
            //    lateinit var motorno:String
            //    lateinit var konum:String
            //    lateinit var durum:String
            //    lateinit var mua:String("model",spinner_modelVrs_kart_ac.selectedItem.toString())
            val fragobj = AracSahibiKartAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()

        }
        btnSigorta_kart_ac.setOnClickListener {
            btnSigorta_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            bundlemDoldur()




            val fragobj = AracSigortaKartAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac,  fragobj)
                .commit()

        }
        btn_istek_sikayet.setOnClickListener{
            btnSigorta_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnAracBilgi_kart_ac.setBackgroundColor(0)
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
      //  bilgidoldur(spinner_arac_turu.getSelectedItem().toString().replace(" ","%20"),kadi)
        spinner_arac_turu?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()

                bilgidoldur(selectedItem.replace(" ","%20"),kadi)//spinnerUnvanDoldur%
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinner_marka_kart_ac?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                itemList_model.clear()
                model_getir(selectedItem.replace(" ","%20"),spinner_arac_turu.selectedItem.toString().replace(" ","%20"))//spinnerUnvanDoldur%
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        spinner_model_kart_ac?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                resim_getir(selectedItem.replace(" ","%20"),spinner_marka_kart_ac.selectedItem.toString(),spinner_arac_turu.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
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
        //arac bilgileri
        bundlem.putString("aracturu",spinner_arac_turu.selectedItem.toString())
        bundlem.putString("resimyolu",resimyolu)
        bundlem.putString("plaka",txt_plaka_no.getText().toString())
        bundlem.putString("yakit_turu",spinner_yakit_turu_sp.selectedItem.toString())

        bundlem.putString("aracturu", spinner_arac_turu.selectedItem.toString())
        if(spinner_marka_kart_ac.selectedItem!=null)
            bundlem.putString("marka", spinner_marka_kart_ac.selectedItem.toString())
        if(spinner_model_kart_ac.selectedItem!=null)
            bundlem.putString("model", spinner_model_kart_ac.selectedItem.toString()!!)
        bundlem.putString("model_y", txt_model_y_kart_ac.getText().toString())
        if(spinner_modelVrs_kart_ac.selectedItem!=null)
            bundlem.putString("modelvrs", spinner_modelVrs_kart_ac.selectedItem.toString())
        if(spinner_kasa_tipi_kart_ac.selectedItem!=null)
            bundlem.putString("kasa_tipi", spinner_kasa_tipi_kart_ac.selectedItem.toString())
        bundlem.putString("km", kmsaat_kart_ac_.getText().toString())
        if(spinner_renk_kart_ac.selectedItem!=null)
            bundlem.putString("renk", spinner_renk_kart_ac.selectedItem.toString())
        bundlem.putString("saseno", saseno_kart_ac_.getText().toString())
        bundlem.putString("motorno", txt_motor_no_.getText().toString())
        if(spinner_konum_kart_ac.selectedItem!=null)
            bundlem.putString("konum", spinner_konum_kart_ac.selectedItem.toString())
        if(spinner_durum_kart_ac.selectedItem!=null)
            bundlem.putString("durum", spinner_durum_kart_ac.selectedItem.toString())
        bundlem.putString("mua", dat_mua)
        bundlem.putString("tahmini_teslim_tarihi", dat_tahmini_teslim)
        bundlem.putString("egzoz", dat_egzoz)
        bundlem.putString("trafik", dat_trafik)
        bundlem.putString("kasko", dat_kasko)
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
        bundlem.putString("eksperadres",eksperadres)
        bundlem.putString("ekspertel",ekspertel)
        bundlem.putString("ekspermail",ekspermail)
        bundlem.putString("policeno",policeno)
        bundlem.putString("policebitiistarihi",policebitiistarihi)
        bundlem.putString("kazatarihi",kazatarihi)

    }


    private fun resim_getir(model: String, marka: String, aracturu: String)
    {
        if (modelvrs != "null") {
            itemList_vrs.clear()
            itemList_vrs.add(modelvrs)
        }
            //Toast.makeText(requireContext(),"unvan.:"+selectedItem,Toast.LENGTH_LONG).show()
            //  val urlsb = "&tur="+ arac_turu.toUpperCase()+"&marka="+marka
            //  val url = "https://pratikhasar.com/netting/mobil.php?tur=model_bul" +urlsb
            val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+aracturu.toUpperCase()+"&AracMarka="+marka+"&AracUstModel="+model

        Log.d("resimbuldu",url)
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["ModelAl"] as JSONArray
                       // var resimgetir:String
                        val itemList: ArrayList<String> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            resimgetir = item.getString("Resim")
                            resimyolu="https://selparbulut.com/$resimgetir"
                            itemList.add(item.getString("Model"))
                            //itemList.add(model)
                        }
                        Picasso.get().load(/* path = */ "https://selparbulut.com/$resimgetir").into(kart_ac_image);

                        for(i in itemList.indices){
                            itemList_vrs.add(itemList[i])
                        }
                        val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
                            requireContext(),
                            R.layout.spinner_item_text,
                            itemList_vrs as List<Any?>
                        )
                        spinner_modelVrs_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)
                    }catch (e:Exception){
                        //Toast.makeText(requireContext(),"MODEL doldur hatası",Toast.LENGTH_LONG).show()
                    }

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                   // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
                }
            )
            queue.add(request)

        }


        private fun model_getir(marka: String, arac_turu: String) {
            if (model != "null") {
                itemList_model.clear()
                itemList_model.add(model)
            }
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+arac_turu.toUpperCase()+"&AracMarka="+marka

        Log.d("MODEL",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ModelUstAl"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var model = item.getString("UstModel")
                        itemList_model.add(model)
                    }
                    val model_alspinner= ArrayList<String>()
                    for(i in itemList_model.indices){
                        model_alspinner.add(itemList_model[i])
                    }
                    val model_adapter:Any?= ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        model_alspinner as List<Any?>
                    )
                    spinner_model_kart_ac.setAdapter(model_adapter as SpinnerAdapter?)
                }catch (e:Exception){
                    //Toast.makeText(requireContext(),"MODEL doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
               // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }


    private fun bilgidoldur(selectedItem: String, kadi: String)   {
        itemList_marka.clear()
        if (marka != "null") {
            itemList_marka.add(marka)
        }
        val urlsb = "&arac_turu="+ selectedItem.toUpperCase()
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+selectedItem.toUpperCase()

        Log.d("ARAC",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["MarkaAl"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var marka = item.getString("Marka")
                        itemList_marka.add(marka)
                    }
                    val spinner_konum_kart_ac_alspinner1= ArrayList<String>()
                    val spinner_konum_kart_ac_value1=this.resources.getStringArray(R.array.konum)
                    for(i in itemList_marka.indices){
                        spinner_konum_kart_ac_alspinner1.add(itemList_marka[i])
                    }
                    val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
                        requireContext(),
                       R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_marka_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


                }catch (e:Exception){
                    //Toast.makeText(requireContext(),"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun arac_kasa_getir() {


        var url = "https://pratikhasar.com/netting/mobil.php?tur=arac_kasa_bul"
        Log.d("RESİMYOL222",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{
                val json = response["renk"] as JSONArray
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    arackasa = item.getString("adi")
                    itemList_kasa.add(arackasa)
                }
                val spinner_konum_kart_ac_alspinner1= ArrayList<String>()
                for(i in itemList_kasa.indices){
                    spinner_konum_kart_ac_alspinner1.add(itemList_kasa[i])
                }
                val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
                    requireContext(),
                    R.layout.spinner_item_text,
                    spinner_konum_kart_ac_alspinner1 as List<Any?>
                )
                spinner_kasa_tipi_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

            }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }
    private fun renk_getir(){
        var url = "https://pratikhasar.com/netting/mobil.php?tur=renk_bul"
        Log.d("RESİMYOL222",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try{

                val json = response["renk"] as JSONArray

                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    renk = item.getString("adi")
                    itemList_renk.add(renk)
                }
                val spinner_konum_kart_ac_alspinner1= ArrayList<String>()
                for(i in itemList_renk.indices){
                    spinner_konum_kart_ac_alspinner1.add(itemList_renk[i])
                }

                val spinner_konum_kart_ac_adapter1:Any?= ArrayAdapter<Any?>(
                    requireContext(),
                    R.layout.spinner_item_text,
                    spinner_konum_kart_ac_alspinner1 as List<Any?>
                )
                spinner_renk_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

            }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }



}