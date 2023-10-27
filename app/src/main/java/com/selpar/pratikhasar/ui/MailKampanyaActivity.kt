package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import org.json.JSONArray
import java.util.ArrayList
import java.util.HashMap

class MailKampanyaActivity : AppCompatActivity() {
    lateinit var btn_geri:ImageView
    lateinit var txt_kac_kisi:TextView
    lateinit var kadi:String
    lateinit var mail_adres:String
    lateinit var mail_sifre:String
    lateinit var smtp_port:String
    lateinit var ssl_port:String
    lateinit var mail_server:String
    lateinit var btn_mesaj_gonder:Button
    lateinit var firma:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    val mail_listesi: ArrayList<String> = ArrayList()
    val cari_listesi: ArrayList<String> = ArrayList()

    lateinit var check_tumu:CheckBox
    lateinit var check_secilen:CheckBox
    lateinit var check_marka:CheckBox
    lateinit var check_model:CheckBox
    lateinit var spinner1:Spinner
     var cari:String="null"
    lateinit var etmesaj:EditText
    lateinit var spinner_arac_turu:Spinner
    lateinit var spinner_marka:Spinner
    lateinit var spinner_model:Spinner
    val itemList_marka: ArrayList<String> = ArrayList()
    val itemList_model: ArrayList<String> = ArrayList()
    lateinit var mBack:ImageView
    lateinit var mForward:ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail_kampanya)
        overridePendingTransition(R.anim.sag, R.anim.sol)

        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        btn_geri=findViewById(R.id.btn_geri)
        btn_mesaj_gonder=findViewById(R.id.btn_mesaj_gonder)
        txt_kac_kisi=findViewById(R.id.txt_kac_kisi)
        check_tumu=findViewById(R.id.check_tumu)
        check_secilen=findViewById(R.id.check_secilen)
        check_marka=findViewById(R.id.check_marka)
        check_model=findViewById(R.id.check_model)
        etmesaj=findViewById(R.id.editTextTextPersonName)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        mBack.setOnClickListener {
            val i= Intent(this,HomeActivity::class.java)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi",kadi)
            startActivity(i)
        }
        btn_mesaj_gonder.setOnClickListener {
            //cariMailBul()
          //  Toast.makeText(this,"boyut: "+mail_listesi.size,Toast.LENGTH_LONG).show()
            if(cari!="null" && etmesaj.getText().toString().isNotEmpty()){
                secilen_cari_mesaj_gonder(cari)
            }
            else{
                if(etmesaj.getText().toString().isNotEmpty()) {
                    for (i in 0 until mail_listesi.size) {
                       // sendSMS(tel_listesi[i], etmesaj.getText().toString())
                        mailAdrese(mail_adres,mail_sifre,smtp_port,ssl_port,mail_server,mail_listesi[i])
                    }
                }
                else{
                    Toast.makeText(this,"Lütfen mesaj giriniz!..",Toast.LENGTH_LONG).show()
                }
            }

        }
        check_tumu?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (check_tumu!!.isChecked) {
                    //itemList.clear()
                    cari="null"
                    check_secilen.isChecked=false
                    check_marka.isChecked=false
                    check_model.isChecked=false
                    firmaGetir(firma_id)

                }
                //bilgi_getir(kadi,firma)                                }
                else{
                    // bilgi_getir_bakiye(kadi,firma)
                }

            }
        })
        check_secilen?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (check_secilen!!.isChecked) {
                    //itemList.clear()
                    check_tumu.isChecked=false
                    check_marka.isChecked=false
                    check_model.isChecked=false
                    txt_kac_kisi.setText("")
                    secilen_alert()
                    //firmaGetir(kadi)

                }
                //bilgi_getir(kadi,firma)                                }
                else{
                    // bilgi_getir_bakiye(kadi,firma)
                }

            }
        })
        check_marka?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (check_marka!!.isChecked) {
                    //itemList.clear()
                    check_tumu.isChecked=false
                    check_secilen.isChecked=false
                    check_model.isChecked=false
                    txt_kac_kisi.setText("")
                    markaDoldur()



                    //firmaGetir(kadi)

                }
                //bilgi_getir(kadi,firma)                                }
                else{
                    // bilgi_getir_bakiye(kadi,firma)
                }

            }
        })
        check_model?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (check_model!!.isChecked) {
                    //itemList.clear()
                    check_tumu.isChecked=false
                    check_secilen.isChecked=false
                    check_marka.isChecked=false
                    txt_kac_kisi.setText("")
                    modelDoldur()


                    //firmaGetir(kadi)

                }
                //bilgi_getir(kadi,firma)                                }
                else{
                    // bilgi_getir_bakiye(kadi,firma)
                }

            }
        })
        tumCariler(firma_id)
        mailGonder()
    }
    private fun secilen_cari_mesaj_gonder(cari: String) {
        mail_listesi.clear()
        val urlsb = "&firma=" + firma_id+"&cari_unvan="+ cari
        var url = "https://pratikhasar.com/netting/mobil.php?tur=tek_cari_bilgi_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var mail = item.getString("mail")
                        mail_listesi.add(mail)
                        //Toast.makeText(this,item.getString("mail"),Toast.LENGTH_LONG).show()
                       // mailAdrese(mail_adres,mail_sifre,smtp_port,ssl_port,mail_server,mail)
                        txt_kac_kisi.setText(item.getString("cari_unvan")+" kişisine mail gönderilecek")

                    }
                }catch (e:Exception){
                    txt_kac_kisi.setText("Toplam 0 kişiye mesaj gönderilecek")

                }}, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)    }

    private fun markaDoldur() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle("MARKA TÜRÜ SEÇİNİZ!..")
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.marka_sec, null)

        spinner_arac_turu=view.findViewById<Spinner>(R.id.spinner_arac_turu)
        spinner_marka=view.findViewById<Spinner>(R.id.spinner_marka)
        val alspinner1 = ArrayList<String>()
        val _spvalue1 = resources.getStringArray(R.array.arac_turu)
        for (i in _spvalue1.indices) {
            alspinner1.add(_spvalue1[i])
        }
        val adapter1: Any? = ArrayAdapter<Any?>(
            view.getContext(),
            android.R.layout.simple_spinner_item,
            alspinner1 as List<Any?>
        )
        spinner_arac_turu.setAdapter(adapter1 as SpinnerAdapter?)
        spinner_arac_turu?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                itemList_marka.clear()
                bilgidoldur(selectedItem.replace(" ","%20"),kadi)//spinnerUnvanDoldur%
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EKLE"
        ) { dialogInterface, which ->
            val marka=spinner_marka.selectedItem.toString().replace(" ","%20")
            onMarkaGetir(marka)
            //  Toast.makeText(requireContext(),"evrak turu "+evrak_turu,Toast.LENGTH_LONG).show()


        }
        alertadd.show()
    }
    private fun modelDoldur() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle("MARKA TÜRÜ SEÇİNİZ!..")
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.model_sec, null)

        spinner_arac_turu=view.findViewById<Spinner>(R.id.spinner_arac_turu)
        spinner_marka=view.findViewById<Spinner>(R.id.spinner_marka)
        spinner_model=view.findViewById<Spinner>(R.id.spinner_model)
        val alspinner1 = ArrayList<String>()
        val _spvalue1 = resources.getStringArray(R.array.arac_turu)
        for (i in _spvalue1.indices) {
            alspinner1.add(_spvalue1[i])
        }
        val adapter1: Any? = ArrayAdapter<Any?>(
            view.getContext(),
            android.R.layout.simple_spinner_item,
            alspinner1 as List<Any?>
        )
        spinner_arac_turu.setAdapter(adapter1 as SpinnerAdapter?)
        spinner_arac_turu?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                itemList_marka.clear()
                bilgidoldur(selectedItem.replace(" ","%20"),kadi)//spinnerUnvanDoldur%
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        spinner_marka?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EKLE"
        ) { dialogInterface, which ->
            val model=spinner_model.selectedItem.toString().replace(" ","%20")
            val marka=spinner_marka.selectedItem.toString().replace(" ","%20")
            onModelGetir(marka,model)
            //  Toast.makeText(requireContext(),"evrak turu "+evrak_turu,Toast.LENGTH_LONG).show()


        }
        alertadd.show()
    }

    private fun onModelGetir(marka: String, model: String) {
        cari_listesi.clear()
        mail_listesi.clear()
        val urlsb = "&firma=" + firma_id+"&marka="+marka+"&model="+model
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_model_getir" + urlsb
        Log.d("MODEL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var tel = item.getString("gsm")
                        var cari_unvan = item.getString("cari_unvan")
                        cari_listesi.add(cari_unvan)
                        mail_listesi.add(item.getString("mail"))
                        //Toast.makeText(this,item.getString("mail"),Toast.LENGTH_SHORT).show()
                       // sendSMS(tel,etmesaj.getText().toString())
                    }
                    txt_kac_kisi.setText("Toplam "+cari_listesi.size+" kişiye mesaj gönderilecek")

                }catch (e:Exception){
                    txt_kac_kisi.setText("Toplam 0 kişiye mesaj gönderilecek")

                }}, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }

    private fun model_getir(marka: String, arac_turu: String) {
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+arac_turu.toUpperCase()+"&AracMarka="+marka

        Log.d("MODEL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
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
                        this,
                        R.layout.spinner_item_text,
                        model_alspinner as List<Any?>
                    )
                    spinner_model.setAdapter(model_adapter as SpinnerAdapter?)
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
      //  Toast.makeText(this,"unvan.:"+selectedItem,Toast.LENGTH_LONG).show()
        val urlsb = "&arac_turu="+ selectedItem.toUpperCase()
        val url="https://selparbulut.com/api/api.php?username=pratikhasar&password=Ankara2017.Selpar&kaynak=stok&AracTuru="+selectedItem.toUpperCase()

        Log.d("ARAC",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
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
                        this,
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_marka.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)


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

    private fun onMarkaGetir(marka: String) {
        cari_listesi.clear()
        mail_listesi.clear()
        val urlsb = "&firma=" + firma_id+"&marka="+marka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_marka_getir" + urlsb
        Log.d("MARKA",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var cari_unvan = item.getString("cari_unvan")
                        cari_listesi.add(cari_unvan)
                        mail_listesi.add(item.getString("mail"))
                       // Toast.makeText(this,item.getString("mail"),Toast.LENGTH_SHORT).show()

                        //sendSMS(tel,etmesaj.getText().toString())
                    }
                    txt_kac_kisi.setText("Toplam "+cari_listesi.size+" kişiye mesaj gönderilecek")

                }catch (e:Exception){
                    txt_kac_kisi.setText("Toplam 0 kişiye mesaj gönderilecek")

                }}, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)     }


    private fun secilen_alert() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle("Mail Gönderilecek Kişiyi Seçin")
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.evrak_turu, null)

        spinner1=view.findViewById<Spinner>(R.id.sp_evrak_turu)
        val alspinner1 = ArrayList<String>()

        for (i in cari_listesi.indices) {
            alspinner1.add(cari_listesi[i])
        }
        val adapter1: Any? = ArrayAdapter<Any?>(
            view.getContext(),
            android.R.layout.simple_spinner_item,
            alspinner1 as List<Any?>
        )
        spinner1.setAdapter(adapter1 as SpinnerAdapter?)
        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EKLE"
        ) { dialogInterface, which ->
            cari=spinner1.selectedItem.toString()
            txt_kac_kisi.setText(cari+" carisine mail gönderilecek!..")
            secilen_cari_mesaj_gonder(cari)

        }
        alertadd.show()
    }

    private fun firmaGetir(firmaId: String) {
        mail_listesi.clear()
        val urlsb = "&firma=" + firmaId
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bilgi_getir_gonder" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray
                    if(json.length()>=1){ txt_kac_kisi.setText("Toplam "+json.length()+" kişiye mail gönderilecek")}
                    mail_listesi.clear()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var mail = item.getString("mail")
                       mail_listesi.add(mail)
                        //sendSMS(tel,etmesaj.getText().toString())
                    }
                    txt_kac_kisi.setText("Toplam "+mail_listesi.size+" kişiye mail gönderilecek")

                }catch (e:Exception){
                    txt_kac_kisi.setText("Toplam 0 kişiye mail gönderilecek")

                }}, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun tumCariler(firmaId: String) {
        mail_listesi.clear()
        cari_listesi.clear()
        val urlsb = "&firma=" + firmaId
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bilgi_getir_gonder" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var mail = item.getString("mail")
                        var cari_unvan=item.getString("cari_unvan")

                        mail_listesi.add(mail)
                        cari_listesi.add(cari_unvan)
                        //sendSMS(tel,etmesaj.getText().toString())
                    }
                    txt_kac_kisi.setText("Toplam "+cari_listesi.size+" kişiye mail gönderilecek")

                }catch (e:Exception){
                    txt_kac_kisi.setText("Toplam 0 kişiye mail gönderilecek")

                }}, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun mailGonder() {
        val urlsb ="&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=giris_bilgi_getir"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
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
                   // Toast.makeText(this,mail_sifre, Toast.LENGTH_LONG).show()
                    //cariMailBul()
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)
    }

    private fun cariMailBul() {
        mail_listesi.clear()
        cari_listesi.clear()
        val urlsb = "&firma=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bilgi_getir_gonder" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try{
                    val json = response["bilgi"] as JSONArray
                    mail_listesi.clear()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        var mail = item.getString("mail")
                        var cari_unvan=item.getString("cari_unvan")
                        mail_listesi.add(mail)
                        cari_listesi.add(cari_unvan)

                        //sendSMS(tel,etmesaj.getText().toString())
                    }
                    txt_kac_kisi.setText("Toplam "+mail_listesi.size+" kişiye mail gönderilecek")

                }catch (e:Exception){
                    txt_kac_kisi.setText("Toplam 0 kişiye mail gönderilecek")

                }}, { error ->
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
        mail_server: String,
        mail: String
    ) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mail_cari_gonder.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
               /* Toast.makeText(this,"Mail Başarılı: "+mailAdres, Toast.LENGTH_SHORT).show()
                Toast.makeText(this,"Mail Başarılı: "+smtp_port, Toast.LENGTH_SHORT).show()
                Toast.makeText(this,"Mail Başarılı: "+ssl_port, Toast.LENGTH_SHORT).show()
                Toast.makeText(this,"Mail Başarılı: "+mail_server, Toast.LENGTH_SHORT).show()*/
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
                params["mesaj"]=etmesaj.getText().toString()
                return params
            }
        }
        queue.add(postRequest)
    }
}