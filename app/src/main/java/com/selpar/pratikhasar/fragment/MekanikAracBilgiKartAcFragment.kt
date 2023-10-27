package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.zxing.integration.android.IntentIntegrator
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.api.*
import com.selpar.pratikhasar.ui.HomeActivity
import com.selpar.pratikhasar.ui.ScanCode2Activity
import com.selpar.pratikhasar.ui.ScanCodeActivity
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result.response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MekanikAracBilgiKartAcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MekanikAracBilgiKartAcFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btn_arac_bilgi_kart_ac: Button
    lateinit var btn_arac_sahibi_kart_ac: Button
    lateinit var btn_arac_bilgi_ilerle: Button
    lateinit var btn_istek_sikayet: Button
    lateinit var btn_arac_kabul_kart_ac: Button
    lateinit var yagtablosu: Button
    var bundlem = Bundle()
    lateinit var renk: String
    val itemList_renk: ArrayList<String> = ArrayList()
    lateinit var arackasa: String
    lateinit var resimgetir: String
    var resimyolu: String = ""
    lateinit var kart_ac_image: ImageView
    lateinit var kmsaat_kart_ac_: EditText
    lateinit var saseno_kart_ac_: EditText
    lateinit var txt_motor_no_: EditText
    lateinit var mua_bitis_tarihi: EditText
    lateinit var egzoz_bitis_tarihi: EditText
    lateinit var trafik_sigortasi_tarihi: EditText
    lateinit var kasko_sigortasi_tarihi: EditText
    lateinit var txt_saseno_kart_ac_: EditText
    lateinit var tahmini_teslim_tarihi: EditText

    lateinit var txt_yag: TextView
    lateinit var kasa_tipi: String
    lateinit var km: String
    lateinit var renk2: String
    lateinit var saseno: String
    lateinit var motorno: String
    lateinit var konum: String
    lateinit var durum: String
    lateinit var mua: String
    lateinit var spinner_renk_kart_ac: Spinner
    lateinit var spinner_kasa_tipi_kart_ac: Spinner
    lateinit var spinner_arac_turu: Spinner
    lateinit var spinner_marka_kart_ac: Spinner
    lateinit var spinner_model_kart_ac: Spinner
    lateinit var spinner_modelVrs_kart_ac: Spinner
    lateinit var spinner_durum_kart_ac: Spinner
    lateinit var spinner_konum_kart_ac: Spinner
    lateinit var spinner_yakit_turu_sp: Spinner
    val itemList_yakit_turu = ArrayList<String>()
    val spinner_kasa_tipi_kart_ac_alspinner1 = ArrayList<String>()
    val itemList_kasa = ArrayList<String>()
    val itemList = ArrayList<String>()
    val itemList2 = ArrayList<String>()
    val spinner_durum_kart_ac_alspinner1 = ArrayList<String>()
    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
    lateinit var image_qr: ImageView

    //sahibi bilgileri
    lateinit var unvan: String
    lateinit var vergino: String
    lateinit var vergidairesi: String
    lateinit var adres: String
    lateinit var il: String
    lateinit var ilce: String
    lateinit var tel: String
    lateinit var tel2: String
    lateinit var stc: String
    lateinit var sAdSoyad: String
    lateinit var bankaismi: String
    lateinit var iban: String
    lateinit var mail: String

    lateinit var txt_plaka_no: EditText

    lateinit var txt_model_y_kart_ac: EditText
    lateinit var txt_dosyano: EditText
    var resim_ruhsat: String = ""
    var resim_km: String = ""
    var resim_sagon: String = ""
    var resim_solon: String = ""
    var resim_sagarka: String = ""
    var resim_solarka: String = ""
    lateinit var aracSelect: String
    lateinit var markaSelect: String
    lateinit var modelSelect: String
    val itemList_model: ArrayList<String> = ArrayList()
    val itemList_vrs: ArrayList<String> = ArrayList()
    lateinit var btn_cikis: ImageView
    lateinit var yetki: String
    lateinit var firma_id: String
    lateinit var kullnciid: String
    lateinit var kadi: String
    lateinit var sifrem: String
    lateinit var dilsecimi: String
    lateinit var tahmini_teslim_tarihi_s: String
    lateinit var egzoz: String
    lateinit var trafik: String
    lateinit var kasko: String
    lateinit var plaka: String
    lateinit var yakitturu: String
    var itenlist_arac_turu = ArrayList<String>()
    lateinit var tur_arac:String
     var marka:String="null"
     var model:String="null"
     var modelvrs:String="null"
    lateinit var txt_sase_karakter:TextView

    var dat_mua:String="null"
    var dat_egzoz:String="null"
    var dat_trafik:String="null"
    var dat_kasko:String="null"
    var dat_tahmini_teslim:String="null"


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
        val view = inflater.inflate(R.layout.fragment_mekanik_arac_bilgi_kart_ac, container, false)
        txt_sase_karakter = view.findViewById(R.id.txt_sase_karakter)
        spinner_yakit_turu_sp = view.findViewById(R.id.spinner_yakit_turu)
        image_qr = view.findViewById(R.id.image_qr)
        yagtablosu = view.findViewById(R.id.btn_yagtablosu)
        tahmini_teslim_tarihi = view.findViewById(R.id.tahmini_teslim_tarihi)
        egzoz_bitis_tarihi = view.findViewById(R.id.egzoz_bitis_tarihi)
        trafik_sigortasi_tarihi = view.findViewById(R.id.trafik_sigortasi_tarihi)
        kasko_sigortasi_tarihi = view.findViewById(R.id.kasko_sigortasi_tarihi)
        txt_yag = view.findViewById(R.id.txt_yag)
        image_qr.setImageDrawable(getResources().getDrawable(R.drawable.qrcode))

        image_qr.setOnClickListener {
            val i = Intent(requireContext(), ScanCode2Activity::class.java)
            i.putExtra("kadi", kadi)
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("sifre", sifrem)
            //arac bilgileri
            i.putExtra("aracturu", spinner_arac_turu.selectedItem.toString())
            i.putExtra("plaka", txt_plaka_no.getText().toString())
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
            i.putExtra("dosyano", txt_dosyano.getText().toString())
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

            //resimler
            i.putExtra("ruhsat_resim", resim_ruhsat)
            i.putExtra("km_resim", resim_km)
            i.putExtra("sagon_resim", resim_sagon)
            i.putExtra("solon_resim", resim_solon)
            i.putExtra("sagarka_resim", resim_sagarka)
            i.putExtra("solarka_resim", resim_solarka)
            startActivity(i)
        }
        val args = this.arguments
        kadi = args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullnciid = args?.getString("kullanici_id").toString()
        sifrem = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        yakitturu = args?.getString("yakitturu").toString()
        if(yakitturu!="null"){
            itemList_yakit_turu.clear()
            itemList_yakit_turu.add(yakitturu)
        }
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
        /*itemList.add(args?.getString("marka").toString())
        itemList_model.add(args?.getString("model").toString())
        itemList_vrs.add(
            args?.getString("modelvrs").toString()
        )*/
        spinner_marka_kart_ac = view.findViewById(R.id.spinner_marka_kart_ac)
        val spinner_yakit_turu=this.resources.getStringArray(R.array.yakit_turu)
        if(yakitturu!="null"){
            itemList_yakit_turu.clear()
            itemList_yakit_turu.add(yakitturu)
        }
        for(i in spinner_yakit_turu.indices){
            itemList_yakit_turu.add(spinner_yakit_turu[i])
        }
        val spinner_yakit_turu_:Any?= ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            itemList_yakit_turu as List<Any?>
        )
        spinner_yakit_turu_sp.setAdapter(spinner_yakit_turu_ as SpinnerAdapter?)

        val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
        for (i in itemList.indices) {
            spinner_konum_kart_ac_alspinner1.add(itemList[i])
        }
        val spinner_konum_kart_ac_adapterm: Any? = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            spinner_konum_kart_ac_alspinner1 as List<Any?>
        )
        spinner_marka_kart_ac.setAdapter(spinner_konum_kart_ac_adapterm as SpinnerAdapter?)

        btn_arac_bilgi_kart_ac = view.findViewById(R.id.btnAracBilgi_kart_ac)
        txt_dosyano = view.findViewById(R.id.txt_dosyano)
        btn_arac_sahibi_kart_ac = view.findViewById(R.id.btnAracSahibi_kart_ac_)
        btn_arac_kabul_kart_ac = view.findViewById(R.id.btn_kabul_asamasi)
        btn_arac_bilgi_ilerle = view.findViewById(R.id.btn_arac_bilgi_ilerle)
        btn_istek_sikayet = view.findViewById(R.id.btn_istek_sikayet)
        spinner_renk_kart_ac = view.findViewById(R.id.spinner_renk_kart_ac)
        spinner_kasa_tipi_kart_ac = view.findViewById(R.id.spinner_kasa_tipi_kart_ac)
        spinner_arac_turu = view.findViewById(R.id.spinner_arac_turu)
        spinner_model_kart_ac = view.findViewById(R.id.spinner_model_kart_ac)
        spinner_modelVrs_kart_ac = view.findViewById(R.id.spinner_modelVrs_kart_ac)
        kart_ac_image = view.findViewById(R.id.kart_ac_image)
        spinner_konum_kart_ac = view.findViewById(R.id.spinner_konum_kart_ac)
        spinner_durum_kart_ac = view.findViewById(R.id.spinner_durum_kart_ac)
        txt_plaka_no = view.findViewById(R.id.txt_plaka_no)
        txt_plaka_no.setText("")
        txt_model_y_kart_ac = view.findViewById(R.id.txt_model_y_kart_ac)
        spinner_arac_turu = view.findViewById(R.id.spinner_arac_turu)
        spinner_durum_kart_ac = view.findViewById(R.id.spinner_durum_kart_ac)
        spinner_konum_kart_ac = view.findViewById(R.id.spinner_konum_kart_ac)
        spinner_kasa_tipi_kart_ac = view.findViewById(R.id.spinner_kasa_tipi_kart_ac)
        spinner_model_kart_ac = view.findViewById(R.id.spinner_model_kart_ac)
        saseno_kart_ac_ = view.findViewById(R.id.txt_saseno_kart_ac_)
        saseno_kart_ac_.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Called when the text is changing
                txt_sase_karakter.setText(getString(R.string.saseno)+"   "+saseno_kart_ac_.getText().toString().length)
            }

            override fun afterTextChanged(s: Editable?) {
                // Called after the text has been changed
                val modifiedText = s.toString()
                // Perform any desired operations with the modified text
            }
        })
        // saseno_kart_ac_=view.findViewById(R.id.txt_saseno_kart_ac_)
        txt_motor_no_ = view.findViewById(R.id.txt_motor_no_)
        mua_bitis_tarihi = view.findViewById(R.id.mua_bitis_tarihi)
        yagtablosu.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)

            alertadd.setTitle("YAĞ TABLOSU")
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(R.layout.yag_tablosu, null)
            val spinner1 = view.findViewById<Spinner>(R.id.yag_spinner)
            val yag_text_marka = view.findViewById<TextView>(R.id.yag_text_marka)
            val yag_spinner_model = view.findViewById<Spinner>(R.id.yag_spinner_model)
            val yag_spinner = view.findViewById<Spinner>(R.id.yag_spinner)
            if (spinner_marka_kart_ac.selectedItem != null)
                yag_text_marka.setText(spinner_marka_kart_ac.selectedItem.toString())
            var model_list: ArrayList<String> = ArrayList()
            var yag_list: ArrayList<String> = ArrayList()

            // model bul api

            //
            // yag getir
            val url =
                "https://pratikhasar.com/netting/mobil.php?tur=marka_model_bul&marka=" + yag_text_marka.getText()
                    .toString()

            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["model"] as JSONArray
                        // var resimgetir:String
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            if (item.getString("model").toString() != "null") {
                                model_list.add(item.getString("model"))
                            }
                        }
                        val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                        for (i in model_list.indices) {
                            spinner_konum_kart_ac_alspinner1.add(model_list[i])
                        }
                        val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                            requireContext(),
                            R.layout.item_spinner_yag,
                            spinner_konum_kart_ac_alspinner1 as List<Any?>
                        )
                        yag_spinner_model.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


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
            //yag
            yag_spinner_model?.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        val selectedItem = parent.getItemAtPosition(position).toString()
                        val url =
                            "https://pratikhasar.com/netting/mobil.php?tur=marka_model_yag_bul&marka=" + yag_text_marka.getText()
                                .toString() + "&model=" + selectedItem
                        Log.d("YAG", url)
                        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
                        val request = JsonObjectRequest(
                            Request.Method.GET, url, null,
                            { response ->
                                try {
                                    val json = response["yag"] as JSONArray
                                    // var resimgetir:String
                                    yag_list.clear()
                                    for (i in 0 until json.length()) {
                                        val item = json.getJSONObject(i)
                                        if (item.getString("isim").toString() != "null") {
                                            yag_list.add(item.getString("isim"))
                                        }
                                    }
                                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                                    for (i in yag_list.indices) {
                                        spinner_konum_kart_ac_alspinner1.add(yag_list[i])
                                    }
                                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                                        requireContext(),
                                        R.layout.item_spinner_yag,
                                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                                    )
                                    yag_spinner.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


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
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }

            alertadd.setView(view)
            alertadd.setPositiveButton(
                "KAPAT"
            ) { dialogInterface, which ->
                if (yag_spinner.selectedItem != null)
                    txt_yag.setText(yag_spinner.selectedItem.toString())


            }
            alertadd.show()

        }
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

        txt_saseno_kart_ac_ = view.findViewById(R.id.txt_saseno_kart_ac_)
        spinner_renk_kart_ac = view.findViewById(R.id.spinner_renk_kart_ac)
        spinner_marka_kart_ac = view.findViewById(R.id.spinner_marka_kart_ac)
        spinner_modelVrs_kart_ac = view.findViewById(R.id.spinner_modelVrs_kart_ac)
        kmsaat_kart_ac_ = view.findViewById(R.id.kmsaat_kart_ac_)
        kmsaat_kart_ac_.inputType = InputType.TYPE_CLASS_NUMBER

        txt_model_y_kart_ac = view.findViewById(R.id.txt_model_y_kart_ac)
        spinner_arac_turu = view.findViewById(R.id.spinner_arac_turu)
        spinner_durum_kart_ac = view.findViewById(R.id.spinner_durum_kart_ac)
        spinner_konum_kart_ac = view.findViewById(R.id.spinner_konum_kart_ac)
        spinner_kasa_tipi_kart_ac = view.findViewById(R.id.spinner_kasa_tipi_kart_ac)
        spinner_model_kart_ac = view.findViewById(R.id.spinner_model_kart_ac)
        saseno_kart_ac_ = view.findViewById(R.id.txt_saseno_kart_ac_)
        btn_cikis = view.findViewById(R.id.btn_cikis)
        btn_cikis.setOnClickListener {
            val i = Intent(requireContext(), HomeActivity::class.java)
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            startActivity(i)
        }
        btn_arac_bilgi_kart_ac.setBackgroundColor(Color.GRAY)
        btn_arac_sahibi_kart_ac.setBackgroundColor(0)
        btn_arac_kabul_kart_ac.setBackgroundColor(0)
        btn_istek_sikayet.setBackgroundColor(0)
        plaka = args?.getString("plaka").toString()
        txt_plaka_no.setText(args?.getString("plaka").toString())
        if (plaka == "null") {
            txt_plaka_no.setText("")
        } else
            txt_plaka_no.setText(plaka)
        if(args?.getString("gelenplaka").toString()!="null" && args?.getString("gelenplaka").toString()!=""){
            val array_plaka=args?.getString("gelenplaka").toString().split("-")

            try{
                txt_plaka_no.setText(array_plaka[1].toString())
            }catch (e:Exception){
                Toast.makeText(requireContext(),getString(R.string.plaka_bulunamadi),Toast.LENGTH_LONG).show()

            }
        }

        if (args?.getString("marka") != null)
            itemList.add(args?.getString("marka").toString())
        if (args?.getString("model").toString() != "null")
            itemList2.add(args?.getString("model").toString())
        if (args?.getString("kasa_tipi").toString() != "null")
            spinner_kasa_tipi_kart_ac_alspinner1.add(args?.getString("kasa_tipi").toString())
        kasa_tipi = args?.getString("kasa_tipi").toString()
        if (kasa_tipi != "null")
            itemList_kasa.add(kasa_tipi)
        if (args?.getString("renk").toString() != "null")
            itemList_renk.add(args?.getString("renk").toString())
        if (args?.getString("durum").toString() != "null")
            spinner_durum_kart_ac_alspinner1.add(args?.getString("durum").toString())
        if (args?.getString("konum").toString() != "null")
            spinner_konum_kart_ac_alspinner1.add(args?.getString("konum").toString())
        saseno = args?.getString("saseno").toString()
        km = args?.getString("km").toString()
        motorno = args?.getString("motorno").toString()
        mua = args?.getString("mua").toString()
        egzoz = args?.getString("egzoz").toString()
        trafik = args?.getString("trafik").toString()
        kasko = args?.getString("kasko").toString()
        tahmini_teslim_tarihi_s = args?.getString("tahmini_teslim_tarihi").toString()
        //sahibi bilgileri
        unvan = args?.getString("unvan").toString()
        vergino = args?.getString("vergino").toString()
        vergidairesi = args?.getString("vergidairesi").toString()
        unvan = args?.getString("unvan").toString()
        adres = args?.getString("adres").toString()
        il = args?.getString("il").toString()
        ilce = args?.getString("ilce").toString()
        tel = args?.getString("tel").toString()
        tel2 = args?.getString("tel2").toString()
        stc = args?.getString("stc").toString()
        sAdSoyad = args?.getString("sAdSoyad").toString()
        bankaismi = args?.getString("bankaismi").toString()
        iban = args?.getString("iban").toString()
        mail = args?.getString("mail").toString()

        if (km == "null") {
            kmsaat_kart_ac_.setText("")
        } else
            kmsaat_kart_ac_.setText(km)
        if (saseno == "null") {
            txt_saseno_kart_ac_.setText("")
        } else
            txt_saseno_kart_ac_.setText(saseno)
        if (motorno == "null") {
            txt_motor_no_.setText("")
        } else
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

        if (args?.getString("model_y").toString() != "null")
            txt_model_y_kart_ac.setText(args?.getString("model_y").toString())
        if (args?.getString("dosyano").toString() != "null")
            txt_dosyano.setText(args?.getString("dosyano").toString())
        arac_kasa_getir()
        renk_getir()
        /*
        Toast.makeText(
            requireContext(),
            "resim::" + args?.getString("ruhsat_resim").toString(),
            Toast.LENGTH_LONG
        ).show()*/
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
        //giris()
        val spinner_durum_kart_ac_value1 = this.resources.getStringArray(R.array.durum)
        for (i in spinner_durum_kart_ac_value1.indices) {
            spinner_durum_kart_ac_alspinner1.add(spinner_durum_kart_ac_value1[i])
        }
        val spinner_durum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            spinner_durum_kart_ac_alspinner1 as List<Any?>
        )
        spinner_durum_kart_ac.setAdapter(spinner_durum_kart_ac_adapter1 as SpinnerAdapter?)
        val spinner_konum_kart_ac_value1 = this.resources.getStringArray(R.array.konum)
        for (i in spinner_konum_kart_ac_value1.indices) {
            spinner_konum_kart_ac_alspinner1.add(spinner_konum_kart_ac_value1[i])
        }
        val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            spinner_konum_kart_ac_alspinner1 as List<Any?>
        )
        spinner_konum_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

        val spinner_arac_turu_police_value1 =
            this.resources.getStringArray(R.array.arac_turu_mekanik)
        for (i in spinner_arac_turu_police_value1.indices) {
            itenlist_arac_turu.add(spinner_arac_turu_police_value1[i])
        }
        val spinner_arac_turu_police_adapter1: Any? = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            itenlist_arac_turu as List<Any?>
        )
        spinner_arac_turu.setAdapter(spinner_arac_turu_police_adapter1 as SpinnerAdapter?)
        spinner_arac_turu?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                //if(selectedItem!=null)


                aracSelect = selectedItem.toUpperCase().replace(" ", "%20")


                bilgidoldur(aracSelect)//spinnerUnvanDoldur%sp

                ///bilgiMarka(aracSelect)
                // firmaIdGetir()
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        spinner_marka_kart_ac?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    /*itemList2.clear()
                    itemList_model.clear()
                    itemList_vrs.clear()*/
                    //spinner_marka_kart_ac.setAdapter(null)
                    spinner_modelVrs_kart_ac.setAdapter(null)
                    spinner_model_kart_ac.setAdapter(null)
                    markaSelect = selectedItem.toString().toUpperCase().replace(" ", "%20")

                    //if(selectedItem!=null)
                    model_getir(markaSelect, aracSelect)//spinnerUnvanDoldur%
                    // firmaIdGetir()
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        spinner_model_kart_ac?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    //itemList_model.clear()
                    //itemList_vrs.clear()
                    spinner_modelVrs_kart_ac.setAdapter(null)
                    modelSelect = selectedItem.toString().toUpperCase().replace(" ", "%20")

                    //if(selectedItem!=null)
                    // model_getir(selectedItem.toString().replace(" ","%20"),spinner_arac_turu.selectedItem.toString().replace(" ","%20"))//spinnerUnvanDoldur%
                    // firmaIdGetir()

                    resim_getir(modelSelect, markaSelect, aracSelect)
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        spinner_modelVrs_kart_ac?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    //itemList_model.clear()
                    //itemList_vrs.clear()
                    // spinner_modelVrs_kart_ac.setAdapter(null)
                    var model = selectedItem.toString().toUpperCase().replace(" ", "%20")

                    //if(selectedItem!=null)
                    // model_getir(selectedItem.toString().replace(" ","%20"),spinner_arac_turu.selectedItem.toString().replace(" ","%20"))//spinnerUnvanDoldur%
                    // firmaIdGetir()

                    resim_bul(
                        spinner_arac_turu.selectedItem.toString().replace(" ", "%20"),
                        modelSelect,
                        markaSelect,
                        aracSelect,
                        model
                    )
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        btn_istek_sikayet.setOnClickListener {
            resimTest()
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            bundlemDoldur()


            val fragobj = IstekSikayetMekanikFragment()
            fragobj.arguments = bundlem
            // fragobj.bilgigetir(spinner_marka_kart_ac.selectedItem.toString(),spinner_model_kart_ac.selectedItem.toString(),kasa_tipi)


            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment, fragobj)
                .commit()
        }
        btn_arac_bilgi_ilerle.setOnClickListener {
            resimTest()
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(Color.GRAY)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            val fragobj = MekanikAracSahibiKartAcFragment()
            bundlemDoldur()


            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment, fragobj)
                .commit()
        }

        btn_arac_bilgi_kart_ac.setOnClickListener {
            resimTest()
            btn_arac_bilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
           bundlemDoldur()
            val fragobj = MekanikAracBilgiKartAcFragment()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment, fragobj)
                .commit()
        }
        btn_arac_sahibi_kart_ac.setOnClickListener {
            resimTest()
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(Color.GRAY)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            val fragobj = MekanikAracSahibiKartAcFragment()
            bundlemDoldur()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment, fragobj)
                .commit()
        }
        btn_arac_kabul_kart_ac.setOnClickListener {
            resimTest()
            btn_istek_sikayet.setBackgroundColor(0)
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(Color.GRAY)
            bundlemDoldur()




            val fragobj = MekanikKabulAsamasiKartAcFragment()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment, fragobj)
                .commit()
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun bundlemDoldur() {
        bundlem.putString("kadi", kadi)
        bundlem.putString("dilsecimi", dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kullanici_id", kullnciid)
        bundlem.putString("sifre", sifrem)
        bundlem.putString("aracturu", spinner_arac_turu.selectedItem.toString())
        bundlem.putString("resimyolu", resimyolu)
        bundlem.putString("yakitturu", spinner_yakit_turu_sp.selectedItem.toString())

        bundlem.putString("plaka", txt_plaka_no.getText().toString())
        if (spinner_marka_kart_ac.selectedItem != null)
            bundlem.putString("marka", spinner_marka_kart_ac.selectedItem.toString())
        if (spinner_model_kart_ac.selectedItem != null)
            bundlem.putString("model", spinner_model_kart_ac.selectedItem.toString()!!)
        bundlem.putString("model_y", txt_model_y_kart_ac.getText().toString())
        if (spinner_modelVrs_kart_ac.selectedItem != null)
            bundlem.putString("modelvrs", spinner_modelVrs_kart_ac.selectedItem.toString())
        if (spinner_kasa_tipi_kart_ac.selectedItem != null)
            bundlem.putString("kasa_tipi", spinner_kasa_tipi_kart_ac.selectedItem.toString())
        bundlem.putString("km", kmsaat_kart_ac_.getText().toString())
        if (spinner_renk_kart_ac.selectedItem != null)
            bundlem.putString("renk", spinner_renk_kart_ac.selectedItem.toString())
        bundlem.putString("saseno", saseno_kart_ac_.getText().toString())
        bundlem.putString("motorno", txt_motor_no_.getText().toString())
        bundlem.putString("dosyano", txt_dosyano.getText().toString())
        if (spinner_konum_kart_ac.selectedItem != null)
            bundlem.putString("konum", spinner_konum_kart_ac.selectedItem.toString())
        if (spinner_durum_kart_ac.selectedItem != null)
            bundlem.putString("durum", spinner_durum_kart_ac.selectedItem.toString())
        bundlem.putString("mua", dat_mua)
        bundlem.putString("tahmini_teslim_tarihi", dat_tahmini_teslim)
        bundlem.putString("egzoz", dat_egzoz)
        bundlem.putString("trafik", dat_trafik)
        bundlem.putString("kasko", dat_kasko)
        bundlem.putString("unvan", unvan)

        bundlem.putString("unvan", unvan)
        //sahibi bilgileri
        bundlem.putString("unvan", unvan)
        bundlem.putString("vergino", vergino)
        bundlem.putString("vergidairesi", vergidairesi)
        bundlem.putString("tel2", tel2)
        bundlem.putString("adres", adres)
        bundlem.putString("il", il)
        bundlem.putString("ilce", ilce)
        bundlem.putString("tel", tel)
        bundlem.putString("stc", stc)
        bundlem.putString("sAdSoyad", sAdSoyad)
        bundlem.putString("bankaismi", bankaismi)
        bundlem.putString("iban", iban)
        bundlem.putString("mail", mail)

        //resimler
        bundlem.putString("ruhsat_resim", resim_ruhsat)
        bundlem.putString("km_resim", resim_km)
        bundlem.putString("sagon_resim", resim_sagon)
        bundlem.putString("solon_resim", resim_solon)
        bundlem.putString("sagarka_resim", resim_sagarka)
        bundlem.putString("solarka_resim", resim_solarka)
    }

    private fun resimTest() {
        if (resimyolu == "null") {
            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle(R.string.resimgir)
            alertadd.setPositiveButton(
                "TAMAM"
            ) { dialog, which ->
                dialog.dismiss()
            }
            alertadd.show()
        }
    }

    private fun resim_bul(
        arac_turu: String,
        modelSelect: String,
        markaSelect: String,
        aracSelect: String,
        model: String
    ) {
        val url =
//"https://pratikhasar.com/netting/mobil.php?tur=model_bul&marka="+marka+"&tur="+aracturu
            "https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru=" + arac_turu.toUpperCase() + "&AracMarka=" + markaSelect + "&AracUstModel=" + model + "&Model=" + model

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ModelAl"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item.getString("Resim").toString() != "null") {
                            resimgetir = item.getString("Resim")
                            resimyolu = "https://selparbulut.com/$resimgetir"
                        }
                    }
                    Picasso.get().load(/* path = */ "https://selparbulut.com/$resimgetir")
                        .into(kart_ac_image);


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

    }

    private fun scanCode() {
        val integrator = IntentIntegrator(requireContext() as Activity?) // use this instead
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Qr Okutunuz");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

        /* intentIntegrator.setBeepEnabled(true)
         intentIntegrator.setCameraId(0)
        // intentIntegrator.setTorchEnabled(true) flash için
         intentIntegrator.setBarcodeImageEnabled(true)
         //intentIntegrator.setOrientationLocked(true)
         intentIntegrator.setPrompt("Barkotunuzu Okutun")
         //intentIntegrator.setBarcodeImageEnabled(true)
         //intentIntegrator.setOrientationLocked(false)
        // intentIntegrator.setOrientationLocked(false)
         //intentIntegrator.setScanningRectangle(450, 450);//size
         intentIntegrator.initiateScan(*/
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                //Toast.makeText(requireContext(), "İptal Edildi", Toast.LENGTH_SHORT).show()
            } else {
                /* val i_gelen = Intent(this, BarkodActivity::class.java)
                 i_gelen.putExtra("gelen",result.contents)
                 startActivity(i_gelen)
                 //Log.d("Fragment", "Scanned from Fragment")
                Toast.makeText(requireContext(), "Qr -> " + result.contents, Toast.LENGTH_SHORT)
                    .show()*/
                txt_plaka_no.setText(result.contents)
                //Log.d("Fragment", "$result")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @SuppressLint("NewApi")
    private fun giris() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pratikhasar.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(ApiGiris::class.java)
        var post = service.giris("giris_getir", kadi)
        lateinit var postList: MutableList<Giris>

        post.enqueue(object : retrofit2.Callback<List<Giris>> {
            override fun onResponse(
                call: retrofit2.Call<List<Giris>>,
                response: retrofit2.Response<List<Giris>>
            ) {
                // Toast.makeText(requireContext(), "BAŞARILI"+response.message(), Toast.LENGTH_LONG).show()
                if (response.isSuccessful) {
                    postList = (response.body() as MutableList<Giris>?)!!
                    txt_motor_no_.setText(postList.get(0).id.toString())
                }

            }

            override fun onFailure(call: retrofit2.Call<List<Giris>>, t: Throwable) {

                // Toast.makeText(requireContext(), "BAŞARISIZ", Toast.LENGTH_LONG).show()

            }

        })
    }

    private fun resim_getir(model: String, marka: String, aracturu: String) {
        if (modelvrs != "null") {
            itemList_vrs.clear()
            itemList_vrs.add(modelvrs)
        }
        val url =
//"https://pratikhasar.com/netting/mobil.php?tur=model_bul&marka="+marka+"&tur="+aracturu
            "https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru=" + aracturu.toUpperCase() + "&AracMarka=" + marka + "&AracUstModel=" + model

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ModelAl"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item.getString("Resim").toString() != "null") {
                            resimgetir = item.getString("Resim")
                            resimyolu = "https://selparbulut.com/$resimgetir"
                            itemList_vrs.add(item.getString("Model").toString())
                        }
                    }
                    Picasso.get().load(/* path = */ "https://selparbulut.com/$resimgetir")
                        .into(kart_ac_image);

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_vrs.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_vrs[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_modelVrs_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {

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

    private fun model_getir(marka: String, arac_turu: String) {
        //itemList_model.clear()
        if (model != "null") {
            itemList_model.clear()
            itemList_model.add(model)
        }
        //Toast.makeText(requireContext(),"unvan.:"+selectedItem,Toast.LENGTH_LONG).show()
        //  val urlsb = "&tur="+ arac_turu.toUpperCase()+"&marka="+marka
        //  val url = "https://pratikhasar.com/netting/mobil.php?tur=model_bul" +urlsb
        val url =
            "https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru=" + arac_turu.toUpperCase() + "&AracMarka=" + marka

        Log.d("MODEL", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ModelUstAl"] as JSONArray
                    //itemList_model.clear()
                    spinner_model_kart_ac.setAdapter(null)
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item.getString("UstModel").toString() != "null") {
                            var model = item.getString("UstModel").toString()
                            itemList_model.add(model)
                        }
                    }
                    val model_alspinner = ArrayList<String>()

                    for (i in itemList_model.indices) {
                        model_alspinner.add(itemList_model[i].toString())
                    }
                    val model_adapter: Any? = ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        model_alspinner as List<Any?>
                    )
                    spinner_model_kart_ac.setAdapter(model_adapter as SpinnerAdapter?)


                } catch (e: Exception) {
                    /* Toast.makeText(requireContext(), "MODEL doldur hatası", Toast.LENGTH_LONG)
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

    }


    private fun renk_getir() {


        var url = "https://pratikhasar.com/netting/mobil.php?tur=renk_bul"
        Log.d("RESİMYOL222", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["renk"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        renk = item.getString("adi")
                        itemList_renk.add(renk)
                    }
                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_renk.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_renk[i])
                    }

                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_renk_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {
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

    private fun arac_kasa_getir() {


        var url = "https://pratikhasar.com/netting/mobil.php?tur=arac_kasa_bul"
        Log.d("RESİMYOL222", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["renk"] as JSONArray
                    var arackasa: String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        arackasa = item.getString("adi")
                        itemList_kasa.add(arackasa)
                    }
                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_kasa.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_kasa[i])
                    }

                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_kasa_tipi_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {
                }


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                /* in this case we are simply displaying a toast message.
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show()*/
            }
        )
        queue.add(request)

    }

    private fun bilgidoldur(selectedItem: String) {
        itemList.clear()
        if (marka != "null") {
            itemList.clear()
            itemList.add(marka)
        }
        val urlsb = "&arac_turu=" + selectedItem.toString().toUpperCase()
        //var url = "https://pratikhasar.com/netting/mobil.php?tur=marka_bul" +urlsbV
        val url =
            "https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru=" + selectedItem.toString()
                .toUpperCase()

        Log.d("ARACMARKA", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["MarkaAl"] as JSONArray
                    spinner_marka_kart_ac.setAdapter(null)
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var marka = item.getString("Marka")
                        var resim = item.getString("MarkaResim")

                        itemList.add(marka)
                    }
                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    val spinner_konum_kart_ac_value1 = this.resources.getStringArray(R.array.konum)
                    for (i in itemList.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_marka_kart_ac.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


                } catch (e: Exception) {
                    //  Toast.makeText(requireContext(),"bilgi doldur hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun bilgiMarka(selectedItem: String) {

        val url = "https://selparbulut.com/api/api.php" +
                "?username=pratikhasar" +
                "&password=Ankara2017.Selpar" +
                "&kaynak=stok&AracTuru=" + selectedItem.toString().toUpperCase()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://selparbulut.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(ApiServis::class.java)
        //var post=service.getMarka(selectedItem)


        var post = service.getMarka(
            "pratikhasar",
            "Ankara2017.Selpar",
            "stok",
            selectedItem
        )


        lateinit var postList: MutableList<Marka>


        post.enqueue(object : retrofit2.Callback<List<Marka>> {
            override fun onResponse(call: Call<List<Marka>>, response: Response<List<Marka>>) {

                txt_motor_no_.setText(postList.get(0).message)
                //      Toast.makeText(requireContext(), "BAŞARILI MARKA"+response.message(), Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: Call<List<Marka>>, t: Throwable) {
                //Toast.makeText(requireContext(), "BAŞARIsız ", Toast.LENGTH_LONG).show()
            }


        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MekanikAracBilgiKartAcFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MekanikAracBilgiKartAcFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}