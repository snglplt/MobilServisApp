package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
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
 * Use the [AracSahibiKartAcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AracSahibiKartAcFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btn_arac_sahibi_kart_ac: Button
    lateinit var btnAracBilgi_kart_ac: Button
    lateinit var btnAracSahibi_kart_ac: Button
    lateinit var btnSigorta_kart_ac: Button
    lateinit var btn_istek_sikayet: Button
    lateinit var btn_cari_sec: Button
    lateinit var btn_vergino_sec: Button
    lateinit var btn_ilerle: Button
    lateinit var etauto: AutoCompleteTextView
    lateinit var kadi: String
    lateinit var firma_id: String
    lateinit var unvan: String
    lateinit var etautovergino: AutoCompleteTextView
    lateinit var etVergiDairesi: EditText
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
    lateinit var aracturu: String
    lateinit var marka: String
    lateinit var model: String
    lateinit var modelvers: String
    lateinit var model_y: String
    lateinit var plaka: String
    lateinit var kasa_tipi: String
    lateinit var km: String
    lateinit var renk2: String
    lateinit var saseno: String
    lateinit var motorno: String
    lateinit var konum: String
    lateinit var durum: String
    lateinit var mua: String
    lateinit var tahmini_teslim_tarihi_s: String

    var bundlem = Bundle()
    val itemList_unvan: ArrayList<String> = ArrayList()
    val itemList_vergino: ArrayList<String> = ArrayList()
    val cari_list: ArrayList<String> = ArrayList()
    lateinit var vergino: String
    lateinit var adres: String
    lateinit var il: String
    lateinit var ilce: String
    lateinit var tel: String
    lateinit var sTc: String
    lateinit var sAdSoyad: String
    lateinit var bankIsmi: String
    lateinit var iban: String
    lateinit var mail: String
    lateinit var sigorta: String
    lateinit var tutanak: String
    lateinit var police: String
    lateinit var sorumluad: String
    lateinit var sorumlutel: String
    lateinit var dosyano: String
    lateinit var ihbartarihi: String
    lateinit var kontorolorad: String
    lateinit var kontorolortel: String
    lateinit var sigortasirketi: String
    lateinit var sigortaadres: String
    lateinit var eksper: String
    lateinit var resimyolu: String
    lateinit var btn_cikis: ImageView
    lateinit var yetki: String
    lateinit var kullnciid: String
    lateinit var sifrem: String
    lateinit var dilsecimi: String
    lateinit var spinner_cari: Spinner
    lateinit var egzoz: String
    lateinit var trafik: String
    lateinit var kasko: String


    //sigorta
    lateinit var policeno: String
    lateinit var policebitiistarihi: String
    lateinit var ekspertel: String
    lateinit var eksperadres: String
    lateinit var ekspermail: String
    lateinit var kazatarihi: String
    lateinit var yakitturu: String

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
        val view = inflater.inflate(R.layout.fragment_arac_sahibi_kart_ac, container, false)
        // Inflate the layout for this fragment
        btn_vergino_sec = view.findViewById(R.id.btn_vergino_sec)
        btn_arac_sahibi_kart_ac = view.findViewById(R.id.btn_ilerle)
        btnAracSahibi_kart_ac = view.findViewById(R.id.btnAracSahibi_kart_ac_)
        btnSigorta_kart_ac = view.findViewById(R.id.btn_kabul_asamasi)
        btnAracBilgi_kart_ac = view.findViewById(R.id.btnAracBilgi_kart_ac)
        etauto = view.findViewById(R.id.etauto)
        etautovergino = view.findViewById(R.id.etautovergino)
        etAdres = view.findViewById(R.id.etAdres_kart_ac)
        btn_cari_sec = view.findViewById(R.id.btn_cari_sec)
        etil = view.findViewById(R.id.etIl_kart_ac)
        etVergiDairesi = view.findViewById(R.id.etVergi_dairesi_kart_ac)
        etIlce_kart_ac = view.findViewById(R.id.etIlce_kart_ac)
        ettel_kart_ac = view.findViewById(R.id.ettel_kart_ac)
        ettel2_kart_ac = view.findViewById(R.id.ettel2_kart_ac)
        etsTc_kart_ac = view.findViewById(R.id.etsTc_kart_ac)
        etsAdSoyad_kart_ac = view.findViewById(R.id.etsAdSoyad_kart_ac)
        etBankaİsmi_kart_ac = view.findViewById(R.id.etBankaİsmi_kart_ac)
        etIban_kart_ac = view.findViewById(R.id.etIban_kart_ac)
        etmail_kart_ac = view.findViewById(R.id.etmail_kart_ac)
        btn_ilerle = view.findViewById(R.id.btn_ilerle)
        btn_cikis = view.findViewById(R.id.btn_cikis)
        btn_istek_sikayet = view.findViewById(R.id.btn_istek_sikayet)
        val args = this.arguments
        kadi = args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullnciid = args?.getString("kullanici_id").toString()
        sifrem = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        aracturu = args?.getString("aracturu").toString()
        resimyolu = args?.getString("resimyolu").toString()
        plaka = args?.getString("plaka").toString()
        marka = args?.getString("marka").toString()
        model = args?.getString("model").toString()
        modelvers = args?.getString("modelvrs").toString()
        model_y = args?.getString("model_y").toString()
        kasa_tipi = args?.getString("kasa_tipi").toString()
        km = args?.getString("km").toString()
        renk2 = args?.getString("renk").toString()
        saseno = args?.getString("saseno").toString()
        motorno = args?.getString("motorno").toString()
        konum = args?.getString("konum").toString()
        durum = args?.getString("durum").toString()
        mua = args?.getString("mua").toString()
        yakitturu = args?.getString("yakit_turu").toString()
        tahmini_teslim_tarihi_s = args?.getString("tahmini_teslim_tarihi").toString()
        unvan = args?.getString("unvan").toString()
        //sahib bilgileri
        vergino = args?.getString("vergino").toString()
        //auto.setText(unvan)
        //sigorta
        policeno=args?.getString("policeno").toString()
        policebitiistarihi=args?.getString("policebitiistarihi").toString()
        ekspertel=args?.getString("ekspertel").toString()
        eksperadres=args?.getString("eksperadres").toString()
        ekspermail=args?.getString("ekspermail").toString()
        kazatarihi=args?.getString("kazatarihi").toString()
        sigorta = args?.getString("sigorta").toString()
        tutanak = args?.getString("tutanak").toString()
        police = args?.getString("police").toString()
        sorumluad = args?.getString("sorumluad").toString()
        sorumlutel = args?.getString("sorumlutel").toString()
        dosyano = args?.getString("dosyano").toString()
        ihbartarihi = args?.getString("ihbartarihi").toString()
        kontorolorad = args?.getString("kontrolorad").toString()
        kontorolortel = args?.getString("kontrolortel").toString()
        sigortasirketi = args?.getString("sigortasirketi").toString()
        sigortaadres = args?.getString("sigortaadres").toString()
        eksper = args?.getString("eksper").toString()

        if (args?.getString("unvan").toString() != "null")
            etauto.setText(args?.getString("unvan").toString())
        if (args?.getString("vergino").toString() != "null")
            etautovergino.setText(args?.getString("vergino").toString())
        if (args?.getString("adres").toString() != "null")
            etAdres.setText(args?.getString("adres").toString())
        if (args?.getString("vergidairesi").toString() != "null")
            etVergiDairesi.setText(args?.getString("vergidairesi").toString())
        if (args?.getString("il").toString() != "null")
            etil.setText(args?.getString("il").toString())
        if (args?.getString("ilce").toString() != "null")
            etIlce_kart_ac.setText(args?.getString("ilce").toString())
        if (args?.getString("tel").toString() != "null")
            ettel_kart_ac.setText(args?.getString("tel").toString())
        if (args?.getString("tel2").toString() != "null")
            ettel2_kart_ac.setText(args?.getString("tel2").toString())
        if (args?.getString("stc").toString() != "null")
            etsTc_kart_ac.setText(args?.getString("stc").toString())
        if (args?.getString("sAdSoyad").toString() != "null")
            etsAdSoyad_kart_ac.setText(args?.getString("sAdSoyad").toString())
        if (args?.getString("bankaismi").toString() != "null")
            etBankaİsmi_kart_ac.setText(args?.getString("bankaismi").toString())
        if (args?.getString("iban").toString() != "null")
            etIban_kart_ac.setText(args?.getString("iban").toString())
        if (args?.getString("mail").toString() != "null")
            etmail_kart_ac.setText(args?.getString("mail").toString())
        itemList_unvan.add(unvan)

        btn_cari_sec.setOnClickListener {
            bilgidoldur(etauto.getText().toString(), kadi)
        }
        btn_vergino_sec.setOnClickListener {
            bilgidoldurVergino(etautovergino.getText().toString(), kadi)

        }
        mua = args?.getString("mua").toString()
        tahmini_teslim_tarihi_s = args?.getString("tahmini_teslim_tarihi").toString()
        egzoz = args?.getString("egzoz").toString()
        trafik = args?.getString("trafik").toString()
        kasko = args?.getString("kasko").toString()
        etAdres.setOnClickListener {
            if (etautovergino.getText().length < 10) {
                val alertadd = AlertDialog.Builder(requireContext())
                alertadd.setTitle(R.string.verginogir)
                alertadd.setPositiveButton(
                    "TAMAM"
                ) { dialog, which ->
                    dialog.dismiss()
                }
                alertadd.show()

            }
        }

        btn_istek_sikayet.setBackgroundColor(0)
        btn_cikis.setOnClickListener {
            val i = Intent(requireContext(), HomeActivity::class.java)
            startActivity(i)
        }
        if (args?.getString("adres").toString() != "null")
            etAdres.setText(args?.getString("adres"))
        if (args?.getString("il").toString() != "null")
            etil.setText(args?.getString("il").toString())
        if (args?.getString("ilce").toString() != "null")
            etIlce_kart_ac.setText(args?.getString("ilce").toString())
        btnAracBilgi_kart_ac.setBackgroundColor(0)
        btnAracSahibi_kart_ac.setBackgroundColor(Color.GRAY)
        btnSigorta_kart_ac.setBackgroundColor(0)
        firmaIdGetir()
        if (resimyolu == "") {
            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle(R.string.resimgir)
            alertadd.setPositiveButton(
                "TAMAM"
            ) { dialog, which ->
                dialog.dismiss()
                btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
                btnAracSahibi_kart_ac.setBackgroundColor(0)
                btnSigorta_kart_ac.setBackgroundColor(0)
                bundlemDoldur()



                val fragobj = AracBilgiKartAcFragment()
                fragobj.arguments = bundlem
                // fragobj.arguments=bundlem
                //fragobj.setArguments(bundlem)
                val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_kart_ac, fragobj)
                    .commit()

            }
            alertadd.show()
        }

        btn_istek_sikayet.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            bundlemDoldur()



            val fragobj = IstekSikayetAcFragment()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac, fragobj)
                .commit()
        }
        btnAracBilgi_kart_ac.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(0)
            bundlemDoldur()



            val fragobj = AracBilgiKartAcFragment()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac, fragobj)
                .commit()
        }
        btnAracSahibi_kart_ac.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(Color.GRAY)
            btnSigorta_kart_ac.setBackgroundColor(0)
            bundlemDoldur()



            val fragobj = AracSahibiKartAcFragment()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac, fragobj)
                .commit()
        }
        btn_ilerle.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(Color.GRAY)
            bundlemDoldur()


            val fragobj = AracSigortaKartAcFragment()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac, fragobj)
                .commit()
        }

        btnSigorta_kart_ac.setOnClickListener {
            btnAracBilgi_kart_ac.setBackgroundColor(0)
            btnAracSahibi_kart_ac.setBackgroundColor(0)
            btnSigorta_kart_ac.setBackgroundColor(Color.GRAY)
            bundlem.putString("kadi", kadi)
            bundlemDoldur()


            val fragobj = AracSigortaKartAcFragment()
            fragobj.arguments = bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger = requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_kart_ac, fragobj)
                .commit()
        }

        return view
    }

    private fun bundlemDoldur() {
        bundlem.putString("kadi", kadi)
        bundlem.putString("dilsecimi", dilsecimi)
        bundlem.putString("yetki", yetki)
        bundlem.putString("firma_id", firma_id)
        bundlem.putString("kullanici_id", kullnciid)
        bundlem.putString("sifre", sifrem)

//arac bilgileri
        bundlem.putString("aracturu", aracturu)
        bundlem.putString("resimyolu", resimyolu)
        bundlem.putString("marka", marka)
        bundlem.putString("model", model)
        bundlem.putString("modelvrs", modelvers)
        bundlem.putString("model_y", model_y)
        bundlem.putString("yakit_turu", yakitturu)
        bundlem.putString("kasa_tipi", kasa_tipi)
        bundlem.putString("km", km)
        bundlem.putString("plaka", plaka)
        bundlem.putString("renk", renk2)
        bundlem.putString("saseno", saseno)
        bundlem.putString("motorno", motorno)
        bundlem.putString("konum", konum)
        bundlem.putString("durum", durum)
        bundlem.putString("mua", mua)
        bundlem.putString("egzoz", egzoz)
        bundlem.putString("trafik", trafik)
        bundlem.putString("kasko", kasko)
        bundlem.putString("tahmini_teslim_tarihi", tahmini_teslim_tarihi_s)
        //sahibi bilgileri
        bundlem.putString("unvan", etauto.getText().toString())
        bundlem.putString("vergino", etautovergino.getText().toString())
        if (etautovergino.getText().length < 10) {
            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle(R.string.verginogir)
            alertadd.setPositiveButton(
                "TAMAM"
            ) { dialog, which ->
                dialog.dismiss()
            }
            alertadd.show()

        }
        bundlem.putString("adres", etAdres.getText().toString())
        bundlem.putString("vergidairesi", etVergiDairesi.getText().toString())

        bundlem.putString("il", etil.getText().toString())
        bundlem.putString("ilce", etIlce_kart_ac.getText().toString())
        bundlem.putString("tel", ettel_kart_ac.getText().toString())
        bundlem.putString("tel2", ettel2_kart_ac.getText().toString())

        bundlem.putString("stc", etsTc_kart_ac.getText().toString())
        bundlem.putString("sAdSoyad", etsAdSoyad_kart_ac.getText().toString())
        bundlem.putString("bankaismi", etBankaİsmi_kart_ac.getText().toString())
        bundlem.putString("iban", etIban_kart_ac.getText().toString())
        bundlem.putString("mail", etmail_kart_ac.getText().toString())
        //sigorta
        bundlem.putString("sigorta", sigorta)
        bundlem.putString("tutanak", tutanak)
        bundlem.putString("police", police)
        bundlem.putString("sorumluad", sorumluad)
        bundlem.putString("sorumlutel", sorumlutel)
        bundlem.putString("dosyano", dosyano)
        bundlem.putString("ihbartarihi", ihbartarihi)
        bundlem.putString("kontrolorad", kontorolorad)
        bundlem.putString("kontrolortel", kontorolortel)
        bundlem.putString("sigortasirketi", sigortasirketi)
        bundlem.putString("sigortaadres", sigortaadres)
        bundlem.putString("eksper", eksper)
        bundlem.putString("eksperadres",eksperadres)
        bundlem.putString("ekspertel",ekspertel)
        bundlem.putString("ekspermail",ekspermail)
        bundlem.putString("policeno",policeno)
        bundlem.putString("policebitiistarihi",policebitiistarihi)
        bundlem.putString("kazatarihi",kazatarihi)
    }

    private fun bilgidoldurVergino(etvergi: String, kadi: String) {
        val urlsb = "&kadi=" + kadi + "&vergino=" + etvergi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=unvan_bilgi_vergino_getir" + urlsb
        Log.d("UNVANNNN", url)
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
                        etVergiDairesi.setText(item.getString("vergidairesi"))
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


                } catch (e: Exception) {

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }




    private fun bilgidoldur(selectedItem: String, kadi: String) {
        val urlsb = "&kadi=" + kadi + "&unvan=" + selectedItem.replace(" ", "%20")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=unvan_bilgi_cari_getir" + urlsb
        Log.d("UNVANNNN", url)
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
                        etVergiDairesi.setText(item.getString("vergidairesi"))
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


                } catch (e: Exception) {

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun firmaIdGetir() {

        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=firma_id_getir" + urlsb
        Log.d("RESİMYOL222", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["firma_id"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        firma_id = item.getString("firma_id")
                        Log.d("FIRMA22: ", firma_id)
                        spinnerUnvanDoldur(firma_id)
                        spinnerVergiNoDoldur(firma_id)


                    }
                } catch (e: Exception) {
                }


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun spinnerVergiNoDoldur(firmaId: String) {
        val urlsb = "&firma_id=" + firmaId
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_vergino_getir" + urlsb
        Log.d("RESİMYOL", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_vergino.add(item.getString("vergino"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.select_dialog_singlechoice,
                            itemList_vergino
                        )

                    etautovergino.setAdapter(adapter)

                } catch (e: Exception) {
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

    private fun spinnerUnvanDoldur(firma_id: String) {

        val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_getir" + urlsb
        Log.d("RESİMYOL", url)
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
                        ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.select_dialog_singlechoice,
                            itemList_unvan
                        )

                    etauto.setAdapter(adapter)

                } catch (e: Exception) {
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