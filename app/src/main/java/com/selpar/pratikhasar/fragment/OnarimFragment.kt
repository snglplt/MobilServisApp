package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.OnarimAdapter
import com.selpar.pratikhasar.data.OnarimModel
import org.json.JSONArray
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selpar.pratikhasar.BuildConfig
import com.selpar.pratikhasar.StokListModel
import com.selpar.pratikhasar.adapter.OnarimPaketGVAdapter
import com.selpar.pratikhasar.adapter.StokListAdapter
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.ui.eFaturaKontorYuklemeActivity
import com.selpar.pratikhasar.ui.eFaturaOnarimActivity
import com.squareup.picasso.Picasso
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.io.File


class OnarimFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var etStokNo1: EditText
    lateinit var etStokAdi1: EditText
    lateinit var etMiktar: EditText
    lateinit var etFiyat: EditText

    //    lateinit var etToplam:EditText
    lateinit var btn_plaka_ekle: Button
    lateinit var btn_onarim_guncelle: Button
    lateinit var btnstoktanalert: FloatingActionButton
    lateinit var btn_hesapla: Button
    var toplam: Float = 0.0f
    lateinit var kadi: String
    lateinit var ozel_id: String
    lateinit var firma_id: String
    lateinit var sifre: String
    lateinit var kullanici_id: String
    lateinit var plaka: String
    lateinit var plaka_bilgi: String
    var id: Int? = null
    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: java.util.ArrayList<ClipData.Item>
    lateinit var txtToplamFiyat: TextView
    lateinit var stokNo: String
    lateinit var stokAdi: String
    lateinit var unvan: String

    var miktari: Int? = null
    var fiyati: Float? = null
    lateinit var spinner1: Spinner
    lateinit var spinner_kdv: Spinner
    lateinit var tur: Spinner
    lateinit var spinnerparcaturu: Spinner
    lateinit var floating_fatura: FloatingActionButton
    lateinit var floating_pdf: FloatingActionButton
    val itemList_stok: ArrayList<String> = ArrayList()
    val itemList_paket: ArrayList<String> = ArrayList()
    val itemList_vrs: ArrayList<String> = ArrayList()
    lateinit var autostok: AutoCompleteTextView
    lateinit var autopaket: AutoCompleteTextView
    lateinit var btnstoktangelen: Button
    lateinit var btnpakettengelen: FloatingActionButton
    lateinit var parcaturu: String
    val parcaturu_spinner = ArrayList<String>()
    lateinit var txt_stokAdi: TextView
    lateinit var txt_StokNo: TextView
    lateinit var txtMiktar: TextView
    lateinit var txtFiyat: TextView
    lateinit var txt_kdv: TextView
    lateinit var txtid: TextView
    lateinit var paraturu: Spinner
    var stok_no = ArrayList<String>()
    var aciklama = ArrayList<String>()
    var miktar = ArrayList<String>()
    var fiyat = ArrayList<String>()
    var tutar = ArrayList<String>()
    var kdv = ArrayList<String>()
    var iscilik = ArrayList<String>()
    var usta = ArrayList<String>()
    var toplampdf = 0.00
    var toplam_kdv = 0.00
    var tolpam_kdvsiz = 0.00
    var pageHeight = 1120
    var pageWidth = 792
    var PERMISSION_CODE = 101
    lateinit var pdfviewer: PDFView
    lateinit var saseno: String
    lateinit var marka: String
    lateinit var model: String
    lateinit var modelyili: String
    lateinit var resim: String
    lateinit var servisturu: String
    lateinit var image: ImageView



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onarim, container, false)
        tur = view.findViewById(R.id.tur)
        image = view.findViewById(com.selpar.pratikhasar.R.id.img_r)
        spinnerparcaturu = view.findViewById(R.id.spinnerparcaturu)
        btnstoktanalert = view.findViewById(R.id.btnstoktanalert)
        etStokNo1 = view.findViewById(R.id.etStokNo)
        etStokAdi1 = view.findViewById(R.id.etStokAdi)
        etMiktar = view.findViewById(R.id.etMiktar)
        etFiyat = view.findViewById(R.id.etFiyat)
        spinner_kdv = view.findViewById(R.id.spinner_kdv)
        btn_onarim_guncelle = view.findViewById(R.id.onarim_guncelle)
        autostok = view.findViewById(R.id.autostok)
        autopaket = view.findViewById(R.id.autopaket)
        btnstoktangelen = view.findViewById(R.id.btnstoktangelen)
        btnpakettengelen = view.findViewById(R.id.btnpakettengelen)
        //etToplam=view.findViewById(R.id.etToplam)
        btn_plaka_ekle = view.findViewById(R.id.btn_arac_bilgi_ilerle)
        // btn_hesapla=view.findViewById(R.id.btn_hesapla)
        newRecyclerviewm = view.findViewById(R.id.rc_onarim)
        txtToplamFiyat = view.findViewById(R.id.txtToplamFiyat)
        floating_fatura = view.findViewById(R.id.floating_fatura)
        floating_pdf = view.findViewById(R.id.floating_pdf)
        txtToplamFiyat.setText("0")
        val args = this.arguments
        resim = args?.getString(("resim")).toString()
        saseno = args?.getString(("saseno")).toString()
        plaka_bilgi = args?.getString(("plaka")).toString()
        marka = args?.getString(("marka")).toString()
        model = args?.getString(("model")).toString()
        modelyili = args?.getString(("modelyili")).toString()
        kadi = args?.getString(("kadi")).toString()
        ozel_id = args?.getString("ozel_id").toString()
        firma_id = args?.getString("firma_id").toString()
        sifre = args?.getString("sifre").toString()
        kullanici_id = args?.getString("kullanici_id").toString()
        plaka = args?.getString("plaka").toString()
        unvan = args?.getString("unvan").toString()
        servisturu = args?.getString("servisturu").toString()
        Toast.makeText(requireContext(),"Servis Türü "+servisturu,Toast.LENGTH_LONG).show()
        if(servisturu!="Mekanik Servis"){
            btnpakettengelen.visibility= GONE
        }else{
            btnpakettengelen.visibility= VISIBLE
        }
        val spinner_arac_turu_police_alspinner1 = ArrayList<String>()
        val tur_value = this.resources.getStringArray(R.array.tur)
        for (i in tur_value.indices) {
            spinner_arac_turu_police_alspinner1.add(tur_value[i])
        }
        val tur_adapter: Any? = ArrayAdapter<Any?>(
            requireContext(),
            R.layout.spinner_item_text,
            spinner_arac_turu_police_alspinner1 as List<Any?>
        )
        tur.setAdapter(tur_adapter as SpinnerAdapter?)
        parcaDoldur()
        spinnerUnvanDoldur(firma_id)
        spinnerPaketDoldur(firma_id)
        floating_pdf.setOnClickListener {
            pdfOlustur()
            pdfGonder()
        }
        btnstoktanalert.setOnClickListener {
            ///stokm yap
            val alertadd = AlertDialog.Builder(requireContext())
            val courseModelArrayList: java.util.ArrayList<BitmisModel> =
                java.util.ArrayList<BitmisModel>()

            alertadd.setTitle(getString(R.string.stokseciniz))
            val factory = LayoutInflater.from(requireContext())
            val view: View =
                factory.inflate(com.selpar.pratikhasar.R.layout.card_item_stok_onarim, null)
            val rc_stok_listesi: RecyclerView
            rc_stok_listesi = view.findViewById<RecyclerView>(com.selpar.pratikhasar.R.id.rc_stok_listesi)
            val alspinner1 = java.util.ArrayList<String>()

            val urlsb = "&firma_id=" + firma_id
            var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_bilgi_getir_hepsi" + urlsb
            Log.d("PAKETT", url)
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        java.util.ArrayList<BitmisModel>()

                        val json = response["stokbilgi"] as JSONArray

                        val itemList: ArrayList<StokListModel> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            /*etStokNo1.setText(item.getString("stokno"))
                            etStokAdi1.setText(item.getString("stokadi"))
                            etFiyat.setText(item.getString("fiyat"))
                            etMiktar.setText("1")*/
                            val itemModel = StokListModel(
                                firma_id,
                                item.getString("stokno"),
                                item.getString("stokadi"),
                                item.getString("tutar"),
                                item.getString("parca"),
                            )
                            itemList.add(itemModel)


                        }
                        val adapter =
                            StokListAdapter(itemList)

                        rc_stok_listesi.adapter = adapter
                        rc_stok_listesi.layoutManager = LinearLayoutManager(context)
                        rc_stok_listesi.setHasFixedSize(false)
                        newArrayList = arrayListOf<ClipData.Item>()
                        //val kabulnom = item.getString("kabulnom").toString()
                        // Log.d("kabulnom: ", kabulnom)
                        //  evrak_resim_bul(kadi,firma,kabulnom)
                        // evrak_resim_getir(kadi,firma)
                        //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                        context?.let {
                            getUserData(it)

                        }


                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.kayitlistokyok),
                            Toast.LENGTH_LONG
                        ).show()


                    }

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
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
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(),
                            R.color.blue))
                        //.addSwipeLeftLabel("SİL")
                        .addSwipeRightLabel(getString(R.string.ekle))
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
                            val txt_stokno= viewHolder.itemView.findViewById<TextView>(R.id.txt_stokno).text


                            StokDoldur(firma_id,txt_stokno.toString())

                        }
                    }
                }
            }
            val itemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(rc_stok_listesi)

            alertadd.setView(view)
            alertadd.setPositiveButton(
                "EVET"
            ) { dialogInterface, which ->


            }
            alertadd.setNegativeButton("Hayır") { dialog, which ->
                dialog.dismiss()
            }
            alertadd.show()

        }
        btnpakettengelen.setOnClickListener {
            val alertadd = AlertDialog.Builder(requireContext())
            val courseModelArrayList: java.util.ArrayList<BitmisModel> =
                java.util.ArrayList<BitmisModel>()

            alertadd.setTitle("Paket Seçiniz!..")
            val factory = LayoutInflater.from(requireContext())
            val view: View =
                factory.inflate(com.selpar.pratikhasar.R.layout.card_item_paket_onarim, null)
            val idGVcourses: GridView
            idGVcourses = view.findViewById<GridView>(com.selpar.pratikhasar.R.id.idGVcourses)
            val alspinner1 = java.util.ArrayList<String>()

            val urlsb = "&firma_id=" + firma_id + "&plaka=" + plaka
            var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_bilgi_getir" + urlsb
            Log.d("PAKETT", url)
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        java.util.ArrayList<BitmisModel>()

                        val json = response["paketbilgi"] as JSONArray

                        val itemList: ArrayList<String> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            /*etStokNo1.setText(item.getString("stokno"))
                            etStokAdi1.setText(item.getString("stokadi"))
                            etFiyat.setText(item.getString("fiyat"))
                            etMiktar.setText("1")*/
                            alspinner1.add(item.getString("stokadi"))

                            courseModelArrayList.add(
                                BitmisModel(
                                    item.getString("stokadi"),
                                    item.getString("stokadi"),
                                    item.getString("stokadi"),
                                    kadi,
                                    ozel_id,
                                    plaka,
                                    firma_id
                                )
                            )


                        }
                        val adapter2 = OnarimPaketGVAdapter(requireContext(), courseModelArrayList)

                        idGVcourses.adapter = adapter2


                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.kayitlipaketyok),
                            Toast.LENGTH_LONG
                        ).show()


                    }

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                }
            )
            queue.add(request)




            alertadd.setView(view)
            alertadd.setPositiveButton(
                "EVET"
            ) { dialogInterface, which ->


            }
            alertadd.setNegativeButton("Hayır") { dialog, which ->
                dialog.dismiss()
            }
            alertadd.show()

            /*
            val urlsb = "&firma_id="+ firma_id+"&paketadi="+autopaket.text
            var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_bilgi_getir" + urlsb
            Log.d("UNVANNNN",url)
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["paketbilgi"] as JSONArray
                        val itemList: ArrayList<String> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            etStokNo1.setText(item.getString("stokno"))
                            etStokAdi1.setText(item.getString("stokadi"))
                            etFiyat.setText(item.getString("fiyat"))
                            etMiktar.setText("1")



                        }


                    }catch (e:Exception){
                    }

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.

                }
            )
            queue.add(request)*/
        }

        btnstoktangelen.setOnClickListener {
            val urlsb = "&firma_id=" + firma_id + "&aranan=" + autostok.text
            var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_bilgi_getir" + urlsb
            Log.d("UNVANNNN", url)
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["stokbilgi"] as JSONArray
                        val itemList: ArrayList<String> = ArrayList()
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            etStokNo1.setText(item.getString("stokno"))
                            etStokAdi1.setText(item.getString("stokadi"))
                            etFiyat.setText(item.getString("fiyat"))
                            etFiyat.selectAll()
                            etMiktar.setText("1")


                        }


                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "bilgi doldur hatası", Toast.LENGTH_LONG)
                            .show()
                    }

                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                }
            )
            queue.add(request)
        }
        btn_onarim_guncelle.setOnClickListener {

            stokNo = txt_StokNo.getText().toString()
            stokAdi = txt_stokAdi.getText().toString()
            miktari = txtMiktar.getText().toString().toInt()
            fiyati = txtFiyat.getText().toString().toFloat()

            // guncelleme_yap(stokAdi,stokNo, miktari,fiyati)
            if (etStokAdi1.getText().toString().isNotEmpty()
                && etStokNo1.getText().toString().isNotEmpty()
                && etMiktar.getText().toString().isNotEmpty() && etFiyat.getText().toString()
                    .isNotEmpty()
            ) {
                guncelle(
                    etStokNo1.getText().toString(),
                    etStokAdi1.getText().toString(),
                    etMiktar.getText().toString().toInt(),
                    etFiyat.getText().toString().toFloat()
                )

            }
            kayit_getir()
            sifirla()
            btn_plaka_ekle.visibility = VISIBLE
            btn_onarim_guncelle.visibility = GONE
        }

        kdv_getir()
        btn_plaka_ekle.setOnClickListener {
            var miktar = 1
            var fiyat = 0f
            var kdv = 0
            if (etMiktar.getText().toString() != "")
                miktar = etMiktar.getText().toString().toInt()
            if (etFiyat.getText().toString() != "")
                fiyat = etFiyat.getText().toString().toFloat()
            //etStokNo1.getText().toString().isNotEmpty() &&
            if (etStokAdi1.getText().toString().isNotEmpty()) {
                stokNo = autostok.text.toString()
                stokAdi = etStokAdi1.getText().toString()
                val urlsb =
                    "&kadi=" + kadi + "&ozel_id=" + ozel_id + "&firma_id=" + firma_id
                var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_varmi" + urlsb
                val queue: RequestQueue = Volley.newRequestQueue(requireContext())
                //val requestBody = "tur=deneme"
                val request = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        try {
                            val json = response["kabul"] as JSONArray
                            for (i in 0 until json.length()) {
                                val item = json.getJSONObject(i)
                                if (item.getString("toplam").toString() != "1") {
                                    onApi(
                                        stokNo,
                                        stokAdi,
                                        miktar,
                                        fiyat,
                                        spinnerparcaturu.selectedItem.toString(),
                                        kdv
                                    )
                                } else {
                                    alertOlusturamazsin()
                                }

                            }
                        } catch (e: Exception) {
                        }
                    }, { error ->
                        Log.e("TAG", "RESPONSE IS $error")
                        // in this case we are simply displaying a toast message.
                    }
                )
                queue.add(request)
                sifirla()
                /*
                val alertadd = AlertDialog.Builder(requireContext())
                alertadd.setTitle("PARÇA TÜRÜ SEÇİNİZ!..")
                val factory = LayoutInflater.from(requireContext())
                val view: View = factory.inflate(R.layout.evrak_turu, null)

                spinner1=view.findViewById<Spinner>(R.id.sp_evrak_turu)
                val alspinner1 = java.util.ArrayList<String>()
                val _spvalue1 = resources.getStringArray(R.array.parca_turu)
                for (i in _spvalue1.indices) {
                    alspinner1.add(_spvalue1[i])
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
                    parcaturu=spinner1.selectedItem.toString()
                    //evrak_turu=spinner1.selectedItem.toString()
                    onApi(stokNo,stokAdi, miktar.toFloat(),fiyat,spinner1.selectedItem.toString())

                }
                alertadd.show()*/


            } else {
                Toast.makeText(
                    requireContext(),
                    "Lütfen tüm alanları doldurun!.. ",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        try {
            toplam = (etMiktar.getText().toString().toInt() * etFiyat.getText().toString()
                .toInt()).toFloat()
            //etToplam.setText(toplam.toString())
            toplam = 0f
        } catch (e: Exception) {
            toplam = 0f
        }
        kayit_getir()
        txtToplamFiyat.setText(toplam.toString())
        floating_fatura.setOnClickListener {
            val urlsb =
                "&kadi=" + kadi + "&ozel_id=" + ozel_id + "&firma_id=" + firma_id
            var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_varmi" + urlsb
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            //val requestBody = "tur=deneme"
            val request = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val json = response["kabul"] as JSONArray
                        for (i in 0 until json.length()) {
                            val item = json.getJSONObject(i)
                            if (item.getString("toplam").toString() != "1") {
                                kontorHesapla()
                                // alertFaturaOlustur()
                            } else {
                                alertOlusturamazsin()
                            }

                        }
                    } catch (e: Exception) {
                    }
                }, { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                }
            )
            queue.add(request)


        }
        return view
    }

    private fun kontorHesapla() {
        val urlsb = "firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/kalan_kontor.php?" + urlsb
        Log.d("RESİMYOL", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                if (response.toString().toInt() > 0) {
                    alertFaturaOlustur()
                } else {
                    alertkontorYukle()

                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                Toast.makeText(requireContext(), "GETİRME BAŞARISIZ $error", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        val timeout = 10000 // 10 seconds in milliseconds
        request.retryPolicy = DefaultRetryPolicy(
            timeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)
    }
    private fun alertkontorYukle() {
        val alertadd = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
        alertadd.setTitle(getString(R.string.kontor_yukle))
        alertadd.setPositiveButton(
            getString(R.string.tamam)
        ) { dialog, which ->
            val i = Intent(requireContext(), eFaturaKontorYuklemeActivity::class.java)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kadi", kadi)


            startActivity(i)
            dialog.dismiss()

        }

        alertadd.show()
    }
    private fun alertOlusturamazsin() {
        val alertadd = AlertDialog.Builder(requireContext())
        alertadd.setTitle(plaka + " için daha önce fatura oluşturulmuş!..")
        alertadd.setPositiveButton(
            getString(R.string.tamam)
        ) { dialog, which ->
            dialog.dismiss()

        }

        alertadd.show()
    }
    fun alertFaturaOlustur() {
        val alertadd = AlertDialog.Builder(requireContext())
        alertadd.setTitle(plaka + " için seçilenlerin e-Faturası oluşturulsun mu?")
        alertadd.setPositiveButton(
            "Evet"
        ) { dialog, which ->

            val i = Intent(requireContext(), eFaturaOnarimActivity::class.java)

            i.putExtra("plaka", plaka)
            i.putExtra("kadi", kadi)
            i.putExtra("ozel_id", ozel_id)
            i.putExtra("firma_id", firma_id)
            i.putExtra("sifre", sifre)
            i.putExtra("kullanici_id", kullanici_id)
            i.putExtra("unvan", unvan)

            startActivity(i)

        }
        alertadd.setNegativeButton(
            "Hayır"
        ) { dialog, which -> dialog.dismiss() }
        alertadd.show()
    }
    private fun pdfGonder() {
        val path = Environment.getExternalStorageDirectory().path + "/Onarim.pdf"
        // val path= "backup_rules.xml"
        //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/GFG.pdf",
        val file = File(Environment.getExternalStorageDirectory(), "GFG.pdf")
        val dosyaYolu = File("/storage/emulated/0/Onarim.pdf")
        val dosyaUri = FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            dosyaYolu
        )

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_TEXT, "Lütfen Fiyatlandırın.")
        intent.setPackage("com.whatsapp")

        startActivity(intent)

    }
    private fun pdfOlustur() {
        stok_no.clear()
        val urlsb =
            "&kadi=" + kadi + "&ozel_id=" + ozel_id + "&plaka=" + plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kayit_getir" + urlsb
        Log.d("STOK", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["onarim"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        tolpam_kdvsiz += item.getString("fiyat")
                            .toFloat() * item.getString("miktar")
                            .toInt()
                        toplampdf += (item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt() +
                                item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt() * item.getString("kdv").toInt() / 100)
                        toplam_kdv += item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt() * item.getString("kdv").toInt() / 100

                        if (item.getString("stok_iscilik_no") != "null") {
                            stok_no.add(item.getString("stok_iscilik_no").toString())
                        } else {
                            stok_no.add("----")
                        }
                        aciklama.add(item.getString("stok_iscilik_adi"))
                        miktar.add(item.getString("miktar").toString())
                        fiyat.add(item.getString("fiyat").toString())
                        kdv.add("%" + item.getString("kdv").toString())
                        tutar.add(
                            (item.getString("fiyat").toInt() * item.getString("miktar")
                                .toInt()).toString()
                        ).toString()
                        /*(item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt()).toString()*/
                    }
                    generatePDF(
                        stok_no,
                        aciklama,
                        miktar,
                        fiyat,
                        kdv,
                        tutar,
                        toplampdf,
                        toplam_kdv,
                        tolpam_kdvsiz
                    )
                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
            }
        )
        queue.add(request)
    }
    private fun generatePDF(
        stokNo: ArrayList<String>,
        aciklama: ArrayList<String>,
        miktar: ArrayList<String>,
        fiyat: ArrayList<String>,
        kdv: ArrayList<String>,
        tutar: ArrayList<String>,
        toplampdf: Double,
        toplamKdv: Double,
        tolpamKdvsiz: Double
    ) {
        // creating an object variable
        // for our PDF document.
        var pdfDocument: PdfDocument = PdfDocument()

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        var paint: Paint = Paint()
        var title: Paint = Paint()
        var plaka: Paint = Paint()
        var arac_bilgi: Paint = Paint()
        var arac_bilgileri: Paint = Paint()
        var arac_sahibi: Paint = Paint()
        var pdf_baslik: Paint = Paint()
        var pdf_baslik2: Paint = Paint()
        var arac_bilgileri_renk: Paint = Paint()
        var toplubilgi: Paint = Paint()
        var arac_bilgileri2: Paint = Paint()
        var toplu: Paint = Paint()
        var toplu2: Paint = Paint()
        var km: Paint = Paint()
        var textblue: Paint = Paint()
        var usta_bilgi: Paint = Paint()


        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // below line is used for setting
        // start page for our PDF file.
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        var canvas: Canvas = myPage.canvas

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        plaka.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgileri.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        toplubilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgileri2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        toplu.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        km.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        usta_bilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        textblue.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        pdf_baslik2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))


        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F
        plaka.textSize = 36F
        arac_bilgi.textSize = 12F
        km.textSize = 12F
        arac_bilgi.color = Color.RED
        arac_bilgileri.textSize = 12F
        arac_bilgileri_renk.textSize = 12F
        arac_bilgileri_renk.color = Color.GRAY
        arac_sahibi.textSize = 15F
        pdf_baslik.textSize = 20f
        pdf_baslik2.textSize = 20f
        toplubilgi.textSize = 12F
        arac_bilgileri2.textSize = 12F
        toplu.textSize = 12F
        toplu2.textSize = 12F
        textblue.textSize = 12f
        usta_bilgi.textSize = 12f
        lateinit var aracbmp: Bitmap

        Picasso.get().load(resim).into(image)
        val bitmapDrawable = image.drawable as BitmapDrawable
        val arababbitmap = bitmapDrawable.bitmap
        aracbmp = Bitmap.createScaledBitmap(arababbitmap, 100, 50, false)

        canvas.drawBitmap(aracbmp, 510F, 30F, paint)

        canvas.drawText("YEDEK PARÇA LİSTESİ", 195f, 25F, pdf_baslik)
        canvas.drawText(saseno, 440f, 100F, pdf_baslik2)
        canvas.drawText("Plaka         " + plaka_bilgi, 25f, 40F, arac_bilgileri)
        canvas.drawText("Marka         " + marka, 25f, 60F, arac_bilgileri)
        canvas.drawText("Model         " + model, 25f, 80F, arac_bilgileri)
        canvas.drawText("Modelyılı     " + modelyili, 25f, 100F, arac_bilgileri)



        canvas.drawText("Stok No", 25f, 120F, arac_bilgileri)
        canvas.drawText("Açıklama", 120F, 120F, arac_bilgileri)
        canvas.drawText("Miktar", 510f, 120F, arac_bilgileri)
        canvas.drawText("Fiyat", 575f, 120F, arac_bilgileri)
        // canvas.drawText("Kdv",640f,30F,arac_bilgileri)
        // canvas.drawText("Tutar",700f,30F,arac_bilgileri)
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD))

        paint.textSize = 50F


        //toplam tutar

        var x_stok = 25f
        var y_stok = 140f
        var x_aciklama = 120f
        var y_aciklama = 140f
        var x_miktar = 510f
        var y_miktar = 140f
        var x_fiyat = 570f
        var y_fiyat = 140f
        var x_tutar = 680f
        var y_tutar = 140f
        var x_kdv = 640f
        var y_kdv = 140f
        var x_line = 10F
        var y_line = 145f
        //green line
        arac_bilgileri.textAlign = Paint.Align.LEFT
        toplu.textAlign = Paint.Align.RIGHT
        toplu2.textAlign = Paint.Align.RIGHT
        var binler = 1000
        var yuzler = 1000
        var ondalik = 18 % 100

        for (i in 0 until stokNo.size) {

            canvas.drawText(stokNo[i], x_stok, y_stok, arac_bilgileri2)
            canvas.drawText(aciklama[i], x_aciklama, y_aciklama, arac_bilgileri2)
            canvas.drawText(miktar[i], x_miktar + 10, y_miktar, arac_bilgileri)
            /* binler = fiyat[i].toInt() / 1000
             yuzler = fiyat[i].toInt() % 1000
             ondalik = (fiyat[i].toInt() * 100).toInt() % 100
             if(binler==0){
                 canvas.drawText(String.format("%.2f",fiyat[i].toFloat()).replace(".",","),x_fiyat+40,y_fiyat,toplu2)

             }else
                 canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_fiyat+40f,y_fiyat,toplu2)
             Log.d("tutar",tutar[i])
             binler = tutar[i].toInt() / 1000
             yuzler = tutar[i].toInt() % 1000
             ondalik = (tutar[i].toInt() * 100).toInt() % 100
             if(binler==0){
                 canvas.drawText(String.format("%.2f",tutar[i].toFloat()).replace(".",","),x_tutar+100F,y_tutar,toplu2)

             }else
                 canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_tutar+100F,y_tutar,toplu2)

             canvas.drawText(kdv[i],x_kdv,y_kdv,arac_bilgileri2)*/
            canvas.drawLine(x_line, y_line, x_fiyat + 60f, y_line, paint)

            y_stok += 20f
            y_aciklama += 20f
            y_miktar += 20f
            y_fiyat += 20f
            y_kdv += 20f
            y_tutar += 20f
            y_line += 20f
        }
        canvas.drawLine(10f, 125F, x_fiyat + 60f, 125f, paint)

        canvas.drawLine(10f, 110F, x_fiyat + 60f, 110F, paint)
        canvas.drawLine(x_stok + 80, 110F, x_stok + 80, y_line - 20, paint)
        canvas.drawLine(x_miktar - 20, 110F, x_miktar - 20, y_line - 20, paint)
        canvas.drawLine(x_miktar + 40, 110F, x_miktar + 40, y_line - 20, paint)
        canvas.drawLine(x_fiyat + 60, 110F, x_fiyat + 60, y_line - 20, paint)

        canvas.drawText(
            "Yukarıda belirtilen işçilikler için fiyat verin" +
                    " resmini whastaapp üzerinden gönderin.", 10f, y_line + 20, arac_bilgi
        )


        //tablo

        pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.
        val file: File = File(Environment.getExternalStorageDirectory(), "Onarim.pdf")
        try {
            // after creating a file name we will
            // write our PDF file to that location.

            pdfDocument.writeTo(file.outputStream())
            pdfviewer.fromFile(file).enableSwipe(true).swipeHorizontal(false).load()

            // pdfDocument.writeTo(FileOutputStream(file))

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(requireContext(), "PDF dosyası oluşturuldu..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()
            Toast.makeText(requireContext(), "PDF dosyası oluşturulmadı..", Toast.LENGTH_SHORT).show()

        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }

        private fun parcaDoldur() {
            val parca_value = this.resources.getStringArray(R.array.parca_turu)
            for (i in parca_value.indices) {
                parcaturu_spinner.add(parca_value[i])
            }
            val parca_adapter: Any? = ArrayAdapter<Any?>(
                requireContext(),
                R.layout.spinner_item_text,
                parcaturu_spinner as List<Any?>
            )
            spinnerparcaturu.setAdapter(parca_adapter as SpinnerAdapter?)

        }

    private fun spinnerUnvanDoldur(firma_id: String) {
        val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_getir" + urlsb
        Log.d("RESİMYOL", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["stok"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_stok.add(item.getString("stokno"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.select_dialog_singlechoice,
                            itemList_stok
                        )

                    autostok.setAdapter(adapter)

                } catch (e: Exception) {
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }

    private fun spinnerPaketDoldur(firma_id: String) {
        val urlsb = "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=paket_getir_auto" + urlsb
        Log.d("RESİMYOL", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["paket"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_paket.add(item.getString("paketadi"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.select_dialog_singlechoice,
                            itemList_paket
                        )

                    autopaket.setAdapter(adapter)

                } catch (e: Exception) {
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }


    private fun toplambul() {
        if (etFiyat.getText().toString().isNotEmpty()) {
            toplam = (etMiktar.getText().toString().toInt() * etFiyat.getText().toString()
                .toInt()).toFloat()
            //etToplam.setText(toplam.toString())
            toplam = 0f
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun kdv_getir() {
        val url = "https://pratikhasar.com/netting/mobil.php?tur=kdv_getir"

        Log.d("resimbuldu", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["kdv"] as JSONArray
                    // var resimgetir:String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_vrs.add(item.getString("oran").toString())

                    }

                    val spinner_konum_kart_ac_alspinner1 = ArrayList<String>()
                    for (i in itemList_vrs.indices) {
                        spinner_konum_kart_ac_alspinner1.add(itemList_vrs[i])
                    }
                    val spinner_konum_kart_ac_adapter1: Any? = ArrayAdapter<Any?>(
                        requireContext(),
                        R.layout.spinner_item_text,
                        spinner_konum_kart_ac_alspinner1 as List<Any?>
                    )
                    spinner_kdv.setAdapter(spinner_konum_kart_ac_adapter1 as SpinnerAdapter?)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "MODEL doldur hatası", Toast.LENGTH_LONG)
                        .show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
            }
        )
        queue.add(request)

    }

    private fun kayit_getir() {
        val urlsb = "&kadi=" + kadi + "&ozel_id=" + ozel_id + "&plaka=" + plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kayit_getir" + urlsb
        Log.d("STOK", url)
        val queue: RequestQueue = Volley.newRequestQueue(context)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["onarim"] as JSONArray
                    //println(outputObject["plaka"])
                    val itemList: ArrayList<OnarimModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val itemModel = OnarimModel(
                            kadi,
                            ozel_id,
                            plaka,
                            item.getString("id"),
                            item.getString("stok_iscilik_adi"),
                            item.getString("stok_iscilik_no"),
                            item.getString("miktar").toInt(),
                            item.getString("fiyat").toFloat(),
                            item.getString("parca_turu").toString(),
                            item.getString("kdv"),
                            item.getString("parcaiscilik").toString()
                        )

                        toplam += (item.getString("miktar").toInt() * item.getString("fiyat")
                            .toFloat() +
                                item.getString("miktar").toInt() * item.getString("fiyat")
                            .toFloat() * item.getString("kdv").toInt() / 100)

                        itemList.add(itemModel)
                    }
                    txtToplamFiyat.setText(toplam.toString())
                    toplam = 0f
                    val adapter =
                        OnarimAdapter(itemList)
                    adapter.notifyItemInserted(itemList.size)

                    newRecyclerviewm.adapter = adapter
                    newRecyclerviewm.layoutManager = LinearLayoutManager(context)
                    val diveyder =
                        DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                    newRecyclerviewm.addItemDecoration(diveyder)
                    newRecyclerviewm.setHasFixedSize(true)
                    newArrayList = arrayListOf<ClipData.Item>()
                    context?.let {
                        getUserData(it)

                    }
                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .addSwipeLeftLabel("SİL")
                    .addSwipeRightLabel("GÜNCELLE")
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

            @SuppressLint("ResourceAsColor")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val txt_stokAdi =
                            viewHolder.itemView.findViewById<TextView>(R.id.txt_stokAdi)
                        val txt_StokNo = viewHolder.itemView.findViewById<TextView>(R.id.txt_StokNo)
                        val txtMiktar = viewHolder.itemView.findViewById<TextView>(R.id.txtMiktar)
                        val txtFiyat = viewHolder.itemView.findViewById<TextView>(R.id.txtFiyat)
                        sil_alert(
                            txt_stokAdi.getText().toString(),
                            txt_StokNo.getText().toString(),
                            txtMiktar.getText().toString(),
                            txtFiyat.getText().toString()
                        )
                        kayit_getir()
                    }

                    ItemTouchHelper.RIGHT -> {
                        txtid = viewHolder.itemView.findViewById<TextView>(R.id.txtid)
                        txt_stokAdi = viewHolder.itemView.findViewById<TextView>(R.id.txt_stokAdi)
                        txt_StokNo = viewHolder.itemView.findViewById<TextView>(R.id.txt_StokNo)
                        txtMiktar = viewHolder.itemView.findViewById<TextView>(R.id.txtMiktar)
                        txtFiyat = viewHolder.itemView.findViewById<TextView>(R.id.txtFiyat)
                        txt_kdv = viewHolder.itemView.findViewById<TextView>(R.id.txt_kdv)
                        etStokNo1.setText(txt_StokNo.getText().toString())
                        etStokAdi1.setText(txt_stokAdi.getText().toString())
                        etMiktar.setText(txtMiktar.getText().toString())
                        etFiyat.setText(txtFiyat.getText().toString())
                        autostok.setText(txt_StokNo.getText().toString())
                        parcaturu_spinner.clear()
                        val parca = viewHolder.itemView.findViewById<TextView>(R.id.txt_parca_turu)
                        val kdv = viewHolder.itemView.findViewById<TextView>(R.id.txt_kdv)
                        parcaturu_spinner.add(parca.getText().toString())
                        btn_plaka_ekle.visibility = GONE
                        btn_onarim_guncelle.visibility = VISIBLE
                        itemList_vrs.clear()
                        itemList_vrs.add(kdv.getText().toString())
                        kdv_getir()
                        parcaDoldur()
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)
    }


    private fun guncelle(stokNo: String, stokAdi: String, miktari: Int?, fiyati: Float?) {
        val urlsb =
            "&kadi=" + kadi + "&ozel_id=" + ozel_id + "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_varmi" + urlsb
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["kabul"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item.getString("toplam").toString() != "1") {
                            guncelle_kayit(stokNo, stokAdi, miktari, fiyati)
                        } else {
                            alertOlusturamazsin()
                        }

                    }
                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }
    fun guncelle_kayit(stokNo: String, stokAdi: String, miktari: Int?, fiyati: Float?) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(
                    requireContext(),
                    "Güncelleme Başarılı: " + etStokAdi1.getText().toString(),
                    Toast.LENGTH_LONG
                ).show()
                kayit_getir()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()

                params["id"] = txtid.getText().toString()
                params["sAdi"] = stokAdi
                params["sNo"] = autostok.text.toString()
                params["yedekparca"] = tur.selectedItem.toString()
                params["parca_turu"] = spinnerparcaturu.selectedItem.toString()
                params["kdv"] = spinner_kdv.selectedItem.toString()
                params["miktar"] = miktari.toString()
                params["fiyat"] = fiyati.toString()
                params["toplam"] = (miktari.toString().toInt() * fiyati.toString().toFloat() +
                        miktari.toString().toInt() * fiyati.toString()
                    .toFloat() * spinner_kdv.selectedItem.toString().toInt() / 100).toString()
                params["tur"] = "onarim_guncelle"
                return params
            }
        }
        queue.add(postRequest)
    }
    private fun sil_onarim(sAdi: String, sNo: String, miktar: String, fiyat: String) {
        val urlsb =
            "&kadi=" + kadi + "&ozel_id=" + ozel_id + "&firma_id=" + firma_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_varmi" + urlsb
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["kabul"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if (item.getString("toplam").toString() != "1") {
                            onarim_sil(sAdi, sNo, miktar, fiyat)
                        } else {
                            alertOlusturamazsin()
                        }

                    }
                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)


    }
    fun onarim_sil(sAdi: String, sNo: String, miktar: String, fiyat: String) {

        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                kayit_getir()
                Toast.makeText(requireContext(), "Silme Başarılı: " + sAdi, Toast.LENGTH_LONG)
                    .show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["sAdi"] = sAdi
                params["sNo"] = sNo
                params["miktar"] = miktar
                params["fiyat"] = fiyat
                params["tur"] = "onarim_kayit_sil"
                return params
            }
        }
        queue.add(postRequest)
    }
    private fun sil_alert(
        toString: String,
        toString1: String,
        toString2: String,
        toString3: String
    ) {

        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
        builder.setTitle("Kayıt silinsin mi?")
        builder.setPositiveButton("Evet") { dialog, which ->
            sil_onarim(toString, toString1, toString2, toString3)
            kayit_getir()

        }

        builder.setNegativeButton("Hayır") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()

    }
    private fun getUserData(context: Context) {
        val itemList: java.util.ArrayList<OnarimModel> = java.util.ArrayList()
//        kayit_getir()

        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }

    private fun onApi(
        stokNo: String,
        stokAdi: String,
        miktar: Int,
        fiyat: Float,
        parcaturu: String,
        kdv: Int
    ) {
        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                kayit_getir()
                sifirla()
                //onarimGetir()
                val toast = Toast.makeText(
                    requireContext(),
                    "Onarım Eklendi",
                    Toast.LENGTH_LONG
                )
              //  onCariyerEkle(stokNo, stokAdi, miktar, fiyat, parcaturu)
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
                params["ozel_id"] = ozel_id
                params["plaka"] = plaka
                params["stok_iscilik_adi"] = stokAdi
                params["stok_iscilik_no"] = stokNo
                params["yedekparca"] = tur.selectedItem.toString()
                params["miktar"] = miktar.toString()
                params["fiyat"] = fiyat.toString()
                params["toplam"] =
                    ((miktar * fiyat) + miktar * fiyat * spinner_kdv.selectedItem.toString()
                        .toInt() / 100).toString()
                params["parca_turu"] = parcaturu
                params["kdv"] = spinner_kdv.selectedItem.toString()
                params["tur"] = "onarim_kaydet"
                return params
            }
        }
        queue.add(postRequest)


    }

    private fun sifirla() {
        etStokNo1.getText().clear()
        etStokAdi1.getText().clear()
        etMiktar.getText().clear()
        etFiyat.getText().clear()
        autostok.setText("")
        btn_onarim_guncelle.visibility = GONE
    }
    fun StokDoldur(firmaId: String, aranan: String) {
        val urlsb = "&firma_id=" + firmaId + "&aranan=" + aranan
        var url = "https://pratikhasar.com/netting/mobil.php?tur=stok_bilgi_getir" + urlsb
        Log.d("UNVANNNN", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["stokbilgi"] as JSONArray
                    val itemList: ArrayList<String> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        etStokNo1.setText(item.getString("stokno"))
                        etStokAdi1.setText(item.getString("stokadi"))
                        etFiyat.setText(item.getString("fiyat"))
                        etFiyat.selectAll()
                        etMiktar.setText("1")


                    }


                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "bilgi doldur hatası", Toast.LENGTH_LONG)
                        .show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
            }
        )
        queue.add(request)

    }


}


