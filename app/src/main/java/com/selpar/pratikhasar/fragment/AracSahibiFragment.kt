

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.internal.ContextUtils
import com.selpar.pratikhasar.R
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AracSahibiFragment : Fragment() {
    var marka = ""
    var model = ""
    var modelvers = ""
    var modelyili = ""
    var renk = ""
    var muabit = ""
    var plaka = ""
    var sase = ""
    var motor = ""
    var kmsaat = ""
    var kasatipi = ""
    var durumu = ""
    var konumu = ""
    var resim = ""
    var ozel_id=""
    var kadi=""
    var firma_id=""
    lateinit var btnArcBilgiGuncelle: Button
    lateinit var unvan:EditText
    lateinit var il:EditText
    lateinit var ilce:EditText
    lateinit var vergiNo:EditText
    lateinit var etVergidairesi:EditText
    lateinit var adres:EditText
    lateinit var tel:EditText
    lateinit var stc:EditText
    lateinit var sAdSoyad:EditText
    lateinit var Bankaismi:EditText
    lateinit var iban:EditText
    lateinit var sifre:String
    var bundlem= Bundle()
    lateinit var gonder_firma:ImageView
    lateinit var gonder_surucu:ImageView
    lateinit var gonder_firma_tel:ImageView
    lateinit var gonder_surucu_tel:ImageView



    private var param1: String? = null
    private var param2: String? = null

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
        val view=inflater.inflate(R.layout.fragment_arac_sahibi, container, false)
        btnArcBilgiGuncelle=view.findViewById(R.id.btn_ilerle)
        btnArcBilgiGuncelle.setOnClickListener { guncelleme_yapti() }
        val args= this.arguments
        kadi=args?.getString(("kadi")).toString()
        sifre=args?.getString("sifre").toString()
        firma_id=args?.getString("firma_id").toString()
        plaka=args?.getString("plaka").toString()
        ozel_id= args?.getString("ozel_id").toString()
      //  marka= args?.getString("marka").toString()
        ozel_id= args?.getString("ozel_id").toString()
        unvan=view.findViewById<EditText>(R.id.etUnvan)
        il=view.findViewById<EditText>(R.id.etIlG)
        ilce=view.findViewById<EditText>(R.id.etIlce)
        vergiNo=view.findViewById<EditText>(R.id.etVergi)
        etVergidairesi=view.findViewById<EditText>(R.id.etVergidairesi)
        adres=view.findViewById<EditText>(R.id.etAdres)
        tel=view.findViewById<EditText>(R.id.ettel)
        stc=view.findViewById<EditText>(R.id.etsTc)
        sAdSoyad=view.findViewById<EditText>(R.id.etsAdSoyad)
        Bankaismi=view.findViewById<EditText>(R.id.etBankaİsmi)
        iban=view.findViewById<EditText>(R.id.etIban)
        gonder_firma=view.findViewById(R.id.gonder_firma)
        gonder_surucu=view.findViewById(R.id.gonder_surucu)
        gonder_firma_tel=view.findViewById(R.id.gonder_firma_tel)
        gonder_surucu_tel=view.findViewById(R.id.gonder_surucu_tel)
        unvan.setText(plaka)
        adres.setText(firma_id)
        gonder_firma.setOnClickListener  {

            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle("Mesaj Gönderilsin mi?")
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.firma_mesaj, null)
            var mesaj=view.findViewById<EditText>(R.id.et_mesaj)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "EVET"
            ) { dialogInterface, which ->
                sendSMS(tel.getText().toString(),mesaj.getText().toString())
                Toast.makeText(requireContext(),"Mesaj gönderildi",Toast.LENGTH_LONG).show()

            }
            alertadd.setNegativeButton("Hayır"){
                    dialog, which -> dialog.dismiss()
            }
            alertadd.show()

        }
        gonder_surucu.setOnClickListener  {

            val alertadd = AlertDialog.Builder(requireContext())
            alertadd.setTitle("Mesaj Gönderilsin mi?")
            val factory = LayoutInflater.from(requireContext())
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.firma_mesaj, null)
            var mesaj=view.findViewById<EditText>(R.id.et_mesaj)
            var txt_sayin=view.findViewById<TextView>(R.id.txt_sayin)
            txt_sayin.setText("Sayın "+sAdSoyad.getText().toString())
            mesaj.setFocusableInTouchMode(true)
            mesaj.requestFocus()
            var mesaj_uzunluk=txt_sayin.getText().toString()
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "EVET"
            ) { dialogInterface, which ->
                mesaj_uzunluk += " "+mesaj.getText().toString()
                sendSMS(stc.getText().toString(),mesaj_uzunluk)
                Toast.makeText(requireContext(),"Mesaj gönderildi",Toast.LENGTH_LONG).show()
            }
            alertadd.setNegativeButton("Hayır"){
                    dialog, which -> dialog.dismiss()
            }
            alertadd.show()

        }
        gonder_firma_tel.setOnClickListener { call(tel.getText().toString()) }
        gonder_surucu_tel.setOnClickListener { call(stc.getText().toString()) }





        val kullanici_id=args?.getString("kullanici_id")
        val ozel_id=args?.getString("ozel_id")
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
                    marka = item.getString("marka").toString()
                    if(item.getString("unvan").toString()!="null")
                        unvan.setText(item.getString("unvan").toString())
                    if(item.getString("il").toString()!="null")
                        il.setText(item.getString("il").toString())
                    if(item.getString("ilce").toString()!="null")
                        ilce.setText(item.getString("ilce").toString())
                    if(item.getString("vergino").toString()!="null")
                        vergiNo.setText(item.getString("vergino").toString())
                    if(item.getString("vergidairesi").toString()!="null")
                        etVergidairesi.setText(item.getString("vergidairesi").toString())
                    if(item.getString("adres").toString()!="null")
                        adres.setText(item.getString("adres").toString())
                    if(item.getString("telefon1").toString()!="null")
                        tel.setText(item.getString("telefon1").toString())
                    if(item.getString("tckimlik").toString()!="null")
                        stc.setText(item.getString("tckimlik").toString())
                    if(item.getString("surucu").toString()!="null")
                        sAdSoyad.setText(item.getString("surucu").toString())
                    if(item.getString("sbankaadi").toString()!="null")
                        Bankaismi.setText(item.getString("sbankaadi").toString())
                    if(item.getString("siban").toString()!="null")
                        iban.setText(item.getString("siban").toString())
                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
//                    Toast.makeText(requireContext(), "Fail to get response", Toast.LENGTH_SHORT)
                      //  .show()
                }
            )
            queue.add(request)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun call(tel:String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + tel)
        startActivity(dialIntent)
    }
    private fun sendSMS(telefonNo: String, mesaj: String) {
        /*
        val pi = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), 0
        )*/
        val sms: SmsManager = SmsManager.getDefault()
        sms.sendTextMessage(telefonNo, null, mesaj, null, null)
    }

    private fun guncelleme_yapti(){
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
                val fragobj = AracSahibiFragment()
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
                params["tur"] = "arac_sahibi_guncelle"
                params["unvan"]=unvan.getText().toString()
                params["il"]=il.getText().toString()
                params["ilce"]=ilce.getText().toString()
                params["vergiNo"]=vergiNo.getText().toString()
                params["vergidairesi"]=etVergidairesi.getText().toString()
                params["adres"]=adres.getText().toString()
                params["tel"]=tel.getText().toString()
                params["stc"]=stc.getText().toString()
                params["sAdSoyad"]=sAdSoyad.getText().toString()
                params["Bankaismi"]=Bankaismi.getText().toString()
                params["iban"]=iban.getText().toString()

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
         * @return A new instance of fragment duzen_aracsahibi.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AracSahibiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
