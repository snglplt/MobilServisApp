package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.HomeActivity
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MekanikKabulAsamasiKartAcFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MekanikKabulAsamasiKartAcFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btn_arac_bilgi_kart_ac: Button
    lateinit var btn_arac_sahibi_kart_ac: Button
    lateinit var btn_arac_kabul_kart_ac: Button
    lateinit var btn_istek_sikayet: Button
    lateinit var image_ruhsat:ImageView
    lateinit var image_km:ImageView
    lateinit var image_sag_on_taraf:ImageView
    lateinit var image_sol_on_taraf:ImageView
    lateinit var image_sag_arka_taraf:ImageView
    lateinit var image_sol_arka_taraf:ImageView
    private val CAMERA_REQUEST_RUHSAT = 1
    private val CAMERA_REQUEST_KM = 2
    private val CAMERA_REQUEST_SAG_ON = 3
    private val CAMERA_REQUEST_SOL_ON = 4
    private val CAMERA_REQUEST_SAG_ARKA = 5
    private val CAMERA_REQUEST_SOL_ARKA = 6
    private lateinit var bitmap:Bitmap

    //arac bilgileri
    lateinit var yakitturu:String
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


    lateinit var btn_arac_bilgi_ilerle:Button
    lateinit var firma_id:String
    lateinit var resimyolu:String
    lateinit var kadi:String
    lateinit var plaka:String
    val sigorta_alspinner1 = ArrayList<String>()
    val tutanak_alspinner1 = ArrayList<String>()
    val police_alspinner1= ArrayList<String>()
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

    var bundlem=Bundle()
     var resim_ruhsat:String=""
     var resim_km:String=""
     var resim_sagon:String=""
     var resim_solon:String=""
     var resim_sagarka:String=""
     var resim_solarka:String=""
    lateinit var mail_adres:String
    lateinit var mail_sifre:String
    lateinit var smtp_port:String
    lateinit var ssl_port:String
    lateinit var mail_server:String
    lateinit var btn_cikis:ImageView
    lateinit var yetki:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var dosyano:String

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
        var view=inflater.inflate(R.layout.fragment_mekanik_kabul_asamasi_kart_ac, container, false)
        val args= this.arguments
        yakitturu=args?.getString("yakitturu").toString()
        plaka=args?.getString("plaka").toString()
        kadi=args?.getString("kadi").toString()
        yetki = args?.getString("yetki").toString()
        firma_id = args?.getString("firma_id").toString()
        kullnciid = args?.getString("kullanici_id").toString()
        sifrem = args?.getString("sifre").toString()
        dilsecimi = args?.getString("dilsecimi").toString()
        btn_arac_bilgi_kart_ac=view.findViewById(R.id.btnAracBilgi_kart_ac)
        btn_arac_sahibi_kart_ac=view.findViewById(R.id.btnAracSahibi_kart_ac_)
        btn_arac_kabul_kart_ac=view.findViewById(R.id.btn_kabul_asamasi)
        btn_arac_bilgi_ilerle=view.findViewById(R.id.btn_mekanik_ac)
        btn_istek_sikayet=view.findViewById(R.id.btn_istek_sikayet)
        image_ruhsat=view.findViewById(R.id.image_ruhsat)
        image_km=view.findViewById(R.id.image_km)
        image_sag_on_taraf=view.findViewById(R.id.image_sag_on_taraf)
        image_sol_on_taraf=view.findViewById(R.id.image_sol_on_taraf)
        image_sag_arka_taraf=view.findViewById(R.id.image_sag_arka_taraf)
        image_sol_arka_taraf=view.findViewById(R.id.image_sol_arka_taraf)
        image_ruhsat.setImageDrawable(getResources().getDrawable(R.drawable.camera))
        image_km.setImageDrawable(getResources().getDrawable(R.drawable.camera));
        image_sag_on_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera));
        image_sol_on_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera))
        image_sag_arka_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera))
        image_sol_arka_taraf.setImageDrawable(getResources().getDrawable(R.drawable.camera))
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
       // Toast.makeText(requireContext(),"resim yolu "+resimyolu,Toast.LENGTH_LONG).show()
        if(resimyolu==""){
            resim_error()
            //Toast.makeText(requireContext(),"RESİM BULUNMADI",Toast.LENGTH_LONG).show()
        }
        firma_id=args?.getString("firma_id").toString()
      //  Toast.makeText(requireContext(),firma_id, Toast.LENGTH_LONG).show()
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
        dosyano = args?.getString("dosyano").toString()
        konum=args?.getString("konum").toString()
        durum=args?.getString("durum").toString()
        mua=args?.getString("mua").toString()
        tahmini_teslim_tarihi_s=args?.getString("tahmini_teslim_tarihi").toString()
        egzoz = args?.getString("egzoz").toString()
        trafik = args?.getString("trafik").toString()
        kasko = args?.getString("kasko").toString()
        if(args?.getString("ruhsat_resim").toString()!="null" &&args?.getString("ruhsat_resim").toString()!="")
        {
        resim_ruhsat=args?.getString("ruhsat_resim").toString()
        image_ruhsat.setImageBitmap(resim_goster(resim_ruhsat))
        }
        if(args?.getString("km_resim").toString()!="null"  && args?.getString("km_resim").toString()!=""){
            resim_km=args?.getString("km_resim").toString()
            image_km.setImageBitmap(resim_goster(resim_km))
        }
        if(args?.getString("sagon_resim").toString()!="null"  && args?.getString("sagon_resim").toString()!=""){
            resim_sagon=args?.getString("sagon_resim").toString()
            image_sag_on_taraf.setImageBitmap(resim_goster(resim_sagon))
        }
        if(args?.getString("solon_resim").toString()!="null"  && args?.getString("solon_resim").toString()!=""){
            resim_solon=args?.getString("solon_resim").toString()
            image_sol_on_taraf.setImageBitmap(resim_goster(resim_solon))
        }
        if(args?.getString("sagarka_resim").toString()!="null"  && args?.getString("sagarka_resim").toString()!=""){
            resim_sagarka=args?.getString("sagarka_resim").toString()
            image_sag_arka_taraf.setImageBitmap(resim_goster(resim_sagarka))
        }
        if(args?.getString("solarka_resim").toString()!="null"  && args?.getString("solarka_resim").toString()!=""){
            resim_solarka=args?.getString("solarka_resim").toString()
            image_sol_arka_taraf.setImageBitmap(resim_goster(resim_solarka))
        }


        image_ruhsat.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST_RUHSAT)
        }
        image_km.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST_KM)
        }
        image_sag_on_taraf.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST_SAG_ON)
        }
        image_sol_on_taraf.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST_SOL_ON)
        }
        image_sag_arka_taraf.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST_SAG_ARKA)
        }
        image_sol_arka_taraf.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // cameraIntent.setType("image/*")
            startActivityForResult(cameraIntent,CAMERA_REQUEST_SOL_ARKA)
        }
        btn_arac_bilgi_kart_ac.setBackgroundColor(0)
        btn_istek_sikayet.setBackgroundColor(0)
        btn_arac_sahibi_kart_ac.setBackgroundColor(0)
        btn_arac_kabul_kart_ac.setBackgroundColor(Color.GRAY)
        btn_istek_sikayet.setOnClickListener {
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            bundlemDoldur()
            val fragobj = IstekSikayetMekanikFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()

        }
        btn_arac_bilgi_kart_ac.setOnClickListener {
            btn_arac_bilgi_kart_ac.setBackgroundColor(Color.GRAY)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            bundlemDoldur()


            //sahibi bilgileri
            bundlem.putString("unvan",unvan)
            bundlem.putString("vergino",vergino)

            val fragobj = MekanikAracBilgiKartAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
        }
        btn_arac_sahibi_kart_ac.setOnClickListener {
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(Color.GRAY)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(0)
            bundlemDoldur()



            val fragobj = MekanikAracSahibiKartAcFragment()
            fragobj.arguments=bundlem
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
        }
        btn_arac_bilgi_ilerle.setOnClickListener{
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_istek_sikayet.setBackgroundColor(Color.GRAY)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(0)
            bundlemDoldur()
            val fragobj = IstekSikayetMekanikFragment()
            fragobj.arguments=bundlem
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()
            /*

            Toast.makeText(requireContext(),"resim yolu"+resimyolu,Toast.LENGTH_LONG).show()
            val queue = Volley.newRequestQueue(requireContext())
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    Toast.makeText(requireContext(),"Ekleme Başarılı: "+kadi,Toast.LENGTH_SHORT).show()
                    mailGonder()
                   // carikaydet()
                  //  mailAdrese("snglplt.36@gmail.com","")

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
                    params["polno"]=""
                    params["resim"]=resimyolu
                    params["resimtum"]=resimyolu
                    params["ruhsat_resim"]=resim_ruhsat
                    params["km_resim"]=resim_km
                    params["sagon_resim"]=resim_sagon
                    params["solon_resim"]=resim_solon
                    params["sagarka_resim"]=resim_sagarka
                    params["solarka_resim"]=resim_solarka
                    params["mail"]=mail
                    params["tur"] = "kartac_mekanik"

                    return params
                }
            }
            queue.add(postRequest)*/

        }

        btn_arac_kabul_kart_ac.setOnClickListener {
            btn_arac_bilgi_kart_ac.setBackgroundColor(0)
            btn_arac_sahibi_kart_ac.setBackgroundColor(0)
            btn_arac_kabul_kart_ac.setBackgroundColor(Color.GRAY)
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
        bundlem.putString("plaka",plaka)
        bundlem.putString("yakitturu",yakitturu)
        //arac bilgileri
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
        bundlem.putString("tel2",tel2)
        bundlem.putString("adres",adres)
        bundlem.putString("il",il)
        bundlem.putString("ilce",ilce)
        bundlem.putString("tel",tel)
        bundlem.putString("stc",stc)
        bundlem.putString("sAdSoyad",sAdSoyad)
        bundlem.putString("bankaismi",bankaismi)
        bundlem.putString("iban",iban)
        bundlem.putString("mail", mail)
        //resimler
        bundlem.putString("ruhsat_resim",resim_ruhsat)
        bundlem.putString("km_resim",resim_km)
        bundlem.putString("sagon_resim",resim_sagon)
        bundlem.putString("solon_resim",resim_solon)
        bundlem.putString("sagarka_resim",resim_sagarka)
        bundlem.putString("solarka_resim",resim_solarka)
    }

    private fun carikaydet() {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(requireContext(),"Ekleme Başarılı: "+kadi,Toast.LENGTH_SHORT).show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["firma_id"]=firma_id
                params["cari_unvan"]=unvan
                params["vergino"]=vergino
                params["vergidairesi"]=vergidairesi
                params["il"]=il
                params["ilce"]=ilce
                params["adres"]=adres
                params["tel"]=tel
                params["gsm"]=tel2
                params["tur"] = "cari_ekle"

                return params
            }
        }
        queue.add(postRequest)    }

    private fun mailGonder() {
        val urlsb ="&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=giris_bilgi_getir"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->

                    val json = response["giris"] as JSONArray


                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        mail_adres=item.getString("mail")
                        mail_sifre=item.getString("mailsifre")
                        smtp_port=item.getString("smtp_port")
                        ssl_port=item.getString("ssl_port")
                        mail_server=item.getString("mail_server")
                       // Toast.makeText(requireContext(),mail_sifre,Toast.LENGTH_LONG).show()
                         mailAdrese(mail_adres,mail_sifre,smtp_port,ssl_port,mail_server)
                      }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }

    private fun mailAdrese(
        mailAdres: String,
        mailSifre: String,
        smtp_port: String,
        ssl_port: String,
        mail_server: String
    ) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/gonder.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response

              //  Toast.makeText(requireContext(),"Mail Başarılı: "+kadi,Toast.LENGTH_SHORT).show()
               // Toast.makeText(requireContext(),"Mail Başarılı: "+smtp_port,Toast.LENGTH_SHORT).show()
              //  Toast.makeText(requireContext(),"Mail Başarılı: "+ssl_port,Toast.LENGTH_SHORT).show()
             //   Toast.makeText(requireContext(),"Mail Başarılı: "+mail_server,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI2")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["gonderen"] = mailAdres
                params["sifre"] = mailSifre
                params["smtp_port"] = smtp_port
                params["ssl_port"] = ssl_port
                params["mail_server"] = mail_server
                params["alici"] = mail
                params["plaka"]=plaka
                params["arac_turu"] = aracturu
                params["marka"] = marka
                params["model"]=model
                params["modelvers"]=modelvrs
                params["saseno"]=saseno
                params["motorno"]=motorno
                params["kasatipi"]=kasa_tipi
                params["aracrenk"]=renk2
                return params
            }
        }
        queue.add(postRequest)

    }

    private fun mailGonderAdrese(mailAdres: String, mailSifre: String) {
           // Toast.makeText(requireContext(),"mail:"+mail,Toast.LENGTH_LONG).show()
            val stringSenderEmail =mailAdres //"destek@selpar.com.tr"
            val stringReceiverEmail = mail
            val stringPasswordSenderEmail = mailSifre //Destek06**
            val stringHost = "smtp.gmail.com"
            val properties = System.getProperties()
            properties["mail.smtp.host"] = stringHost
            properties["mail.smtp.port"] = "465"
            properties["mail.smtp.ssl.enable"] = "true"
            properties["mail.smtp.auth"] = "true"
            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication? {
                    return PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail)
                }
            })

            val mimeMessage = MimeMessage(session)
            mimeMessage.addRecipient(Message.RecipientType.TO, InternetAddress(stringReceiverEmail))
            mimeMessage.subject = "Subject: Android App email"
            mimeMessage.setText("Hello Programmer, \n\nProgrammer World has sent you this 2nd email. \n\n Cheers!\nProgrammer World")
            val thread = Thread {

                    Transport.send(mimeMessage)

            }
            thread.start()

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
            // fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mekanik_fragment,  fragobj)
                .commit()


        }
        alertadd.show()
    }


    private fun resim_goster(resimRuhsat: String) :Bitmap{
        val imageBytes = Base64.decode(resimRuhsat, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return decodedImage
    }

    fun ImageToString(map:Bitmap) : String {
        val byteArrayOutputsStream= ByteArrayOutputStream()
        map.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputsStream)
        var imageByte=byteArrayOutputsStream.toByteArray()
        return Base64.encodeToString(imageByte, Base64.DEFAULT)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:  Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_RUHSAT && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image_ruhsat.setImageBitmap(bitmap)
            resim_ruhsat=ImageToString(bitmap)

        }
        if (requestCode == CAMERA_REQUEST_KM && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image_km.setImageBitmap(bitmap)
            resim_km=ImageToString(bitmap)

        }
        if (requestCode == CAMERA_REQUEST_SAG_ON && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image_sag_on_taraf.setImageBitmap(bitmap)
            resim_sagon=ImageToString(bitmap)
        }
        if (requestCode == CAMERA_REQUEST_SOL_ON && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image_sol_on_taraf.setImageBitmap(bitmap)
            resim_solon=ImageToString(bitmap)

        }
        if (requestCode == CAMERA_REQUEST_SAG_ARKA && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image_sag_arka_taraf.setImageBitmap(bitmap)
            resim_sagarka=ImageToString(bitmap)

        }
        if (requestCode == CAMERA_REQUEST_SOL_ARKA && resultCode == Activity.RESULT_OK && data!=null)
        {
            val path=data.getData()
            bitmap= data?.extras?.get("data") as Bitmap
            image_sol_arka_taraf.setImageBitmap(bitmap)
            resim_solarka=ImageToString(bitmap)

        }

    }

}