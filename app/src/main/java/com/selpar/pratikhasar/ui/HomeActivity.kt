package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import com.selpar.navigotionview.ExpandableListAdapter
import com.selpar.navigotionview.ExpandedMenuModel
import com.selpar.pratikhasar.R
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var progressBar:ProgressBar
    lateinit var bugunacilan: TextView
    lateinit var acikkart: TextView
    lateinit var onaybekleyen: TextView
    lateinit var teslimigeciken: TextView
   // lateinit var tedarikbekleyen: TextView
   // lateinit var disbekleyen: TextView
    lateinit var kapalikart: TextView
    lateinit var teslimehazir: TextView
    lateinit var btn_barkod: ImageView
    lateinit var plaka_bilgi: ImageView
    lateinit var plaka_no: EditText
    //lateinit var image_cikis: ImageView
    lateinit var acikkartgoster: TextView
    lateinit var btn_kart_ac: Button
    lateinit var btn_mekanik_kabul_karti_ac: Button
    lateinit var kayitliaraclar: Button
    lateinit var image_setting: ImageView
    lateinit var id_bugun_acilan: RelativeLayout
    lateinit var check_bugun_acilan: CheckBox
    lateinit var check_acik_kart: CheckBox
    lateinit var check_teslimi_geciken: CheckBox
    lateinit var check_onay_bekleyen: CheckBox
    lateinit var check_parca_tedarik: CheckBox
    lateinit var check_parca_iscilik: CheckBox
    lateinit var check_teslime_hazir: CheckBox
    lateinit var check_kapali_kartlar: CheckBox
    lateinit var id_acik_kartlar: RelativeLayout
    lateinit var id_teslimi_geciken: RelativeLayout
    lateinit var id_onay_bekleyen: RelativeLayout
   // lateinit var id_parca_tedarik: RelativeLayout
    //lateinit var id_parca_iscilik: RelativeLayout
    lateinit var id_teslime_hazir: RelativeLayout
    lateinit var id_kapali_kartlar: RelativeLayout
    lateinit var id_1: LinearLayout
    lateinit var id_2: LinearLayout
    var bugun_acilan: Int = 0
    var acik_karlar: Int = 0
    var teslimi_geciken: Int = 0
    var onay_bekleyen: Int = 0
    var parca_tedarik_bekleyen: Int = 0
    var parca_iscilik_bekleyen: Int = 0
    var teslime_hazir: Int = 0
    var kapali_kartlar: Int = 0
    lateinit var drawlayout: DrawerLayout
    lateinit var navi: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var spinner_nav: Spinner
    var bar: ActionBar? = null
    var viewpager: ViewPager? = null
    private var mDrawerLayout: DrawerLayout? = null
    var mMenuAdapter: ExpandableListAdapter? = null
    var expandableList: ExpandableListView? = null
    var listDataHeader: List<ExpandedMenuModel>? = null
    var listDataChild: HashMap<ExpandedMenuModel, List<String>>? = null
    var PERMISSION_CODE = 1
    lateinit var mail: String
    lateinit var yetki: String
    lateinit var firma_id: String
    lateinit var kullnciid: String
    lateinit var kadi: String
    lateinit var sifrem: String
    lateinit var dilsecimi: String
    lateinit var gorev: String
    lateinit var imageprofil: ImageView
    lateinit var txtad_soyad: TextView


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType", "MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider) // init imageSlider
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.tn_r1))
        imageList.add(SlideModel(R.drawable.tn_r2))
        imageList.add(SlideModel(R.drawable.tn_r3))
        imageList.add(SlideModel(R.drawable.tn_r4))
        imageprofil = findViewById(R.id.imageprofil)
        txtad_soyad = findViewById(R.id.txtad_soyad)
        Picasso.get().load("https://pratikhasar.com/firmalar/1/6447af9078222.png").into(imageprofil)
        txtad_soyad.setText("Songül Polat")
        onProfil()
        plaka_no = findViewById(R.id.plaka_no)

        if(intent.getStringExtra("gelenplaka").toString()!="null" && intent.getStringExtra("gelenplaka").toString()!=" "){
            val array_plaka=intent.getStringExtra("gelenplaka").toString().split("-")
            try{
                plaka_no.setText(array_plaka[1].toString())
            }catch (e:Exception){
                Toast.makeText(this,getString(R.string.plaka_bulunamadi),Toast.LENGTH_LONG).show()
            }

        }
        mail=intent.getStringExtra("mail").toString()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        drawlayout = findViewById(R.id.drawelayout);
       // startLoadingAnimation();
        progressBar = findViewById(R.id.progressBar)
        startLoadingAnimation()
        //.makeText(this,"kullnici resim:"+intent.getStringExtra("kullanici_resim").toString(),Toast.LENGTH_LONG).show()

        //Toast.makeText(this,"GOREV:"+intent.getStringExtra("gorev"),Toast.LENGTH_LONG).show()
        imageprofil.setOnClickListener {
            val i = Intent(this, KullaniciGuncelleActivity::class.java)
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi", kadi)
            i.putExtra("gorev", gorev)

            startActivity(i)
        }

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

        //imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                if (position == 0) {
                    //Toast.makeText(applicationContext,"1.inci tıklandı...",Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext, WebviewActivity::class.java)
                    i.putExtra("path", "https://pratikhasar.com/")
                    startActivity(i)
                } else if (position == 1) {
                    val i = Intent(applicationContext, WebviewActivity::class.java)
                    i.putExtra("path", "https://selparbulut.com/")
                    startActivity(i)

                } else if (position == 2) {
                    val i = Intent(applicationContext, WebviewActivity::class.java)
                    i.putExtra("path", "https://pratikhasar.com/")
                    startActivity(i)

                }
                // You can listen here.
                println("normal")
            }

            fun doubleClick(position: Int) {
                // Do not use onItemSelected if you are using a double click listener at the same time.
                // Its just added for specific cases.
                // Listen for clicks under 250 milliseconds.
                println("its double")
            }
        })

        imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                //println("Pos: " + position)
            }
        })

        imageSlider.setTouchListener(object : TouchListener {
            fun onTouched(touched: ActionTypes, position: Int) {
                if (touched == ActionTypes.DOWN) {
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP) {
                    imageSlider.startSliding(1000)
                }
            }

            override fun onTouched(touched: ActionTypes) {

            }
        })
        onBaslat()
        val sharedPreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        kadi = sharedPreferens.getString("kadi", "").toString()
        if (kadi == "") {
            kadi = intent.getStringExtra("kadi").toString()
        }
        dilsecimi = sharedPreferens.getString("dilsecimi", "").toString()
        if (dilsecimi == "") {
            dilsecimi = intent.getStringExtra("dilsecimi").toString()
        }
        yetki = sharedPreferens.getString("yetki", "").toString()
        if (yetki == "") {
            yetki = intent.getStringExtra("yetki").toString()

        }
        firma_id = sharedPreferens.getString("firma_id", "").toString()
        if (firma_id == "") {
            firma_id = intent.getStringExtra("firma_id").toString()

        }
        kullnciid = sharedPreferens.getString("kullanici_id", "").toString()
        if (kullnciid == "") {
            kullnciid = intent.getStringExtra("kullanici_id").toString()

        }
        sifrem = sharedPreferens.getString("sifre", "").toString()
        if (sifrem == "") {
            sifrem = intent.getStringExtra("sifre").toString()

        }
        gorev = sharedPreferens.getString("gorev", "").toString()
        if (gorev == "") {
            gorev = intent.getStringExtra("gorev").toString()

        }


        if (dilsecimi == "en") {
            SetLocale("en")
            recreate()
        }
        olustur()
        onAktive()
        if (intent.getStringExtra("servis_turu").toString() == "Mekanik Servis") {
            btn_kart_ac.visibility = GONE
        }
        if (intent.getStringExtra("kullanici_resim").toString() == "YOK") {
            val i = Intent(this, KullaniciGuncelleActivity::class.java)
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("sifre", sifrem)
            i.putExtra("gorev", gorev)
            i.putExtra("kullanici_resim", "yok")
            Toast.makeText(applicationContext,getString(R.string.kullaniciguncelleme),Toast.LENGTH_LONG).show()
            startActivity(i)

        }

        if (intent.getStringExtra("bitistarihi").toString() != ""     && intent.getStringExtra("bitistarihi").toString() != "null")
            onHesapla(intent.getStringExtra("bitistarihi").toString())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(
                    android.Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.MANAGE_DOCUMENTS
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.Manifest.permission_group.STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
                ) == PackageManager.PERMISSION_DENIED
                ||
                checkSelfPermission(
                    ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.MANAGE_DOCUMENTS,
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.CALL_PHONE,
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    ACTION_MANAGE_STORAGE,
                    android.Manifest.permission_group.STORAGE,
                    ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                )

                requestPermissions(permission, PERMISSION_CODE)
            } else {
                // openCamera()
            }

        } else {
            //api<23
            // openCamera()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
            && !Environment.isExternalStorageManager()
        ) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        drawlayout = findViewById(R.id.drawelayout)
        navi = findViewById(R.id.navigationview)
        toolbar = findViewById(R.id.toolbar)
        id_acik_kartlar = findViewById(R.id.id_bugun_acilan)
        id_teslimi_geciken = findViewById(R.id.id_teslimi_geciken)
        id_onay_bekleyen = findViewById(R.id.id_onay_bekleyen)
        //id_parca_tedarik = findViewById(R.id.id_parca_tedarik)
        //id_parca_iscilik = findViewById(R.id.id_parca_iscilik)
        id_teslime_hazir = findViewById(R.id.id_teslime_hazir)
        id_teslime_hazir = findViewById(R.id.id_teslime_hazir)
        val factory = LayoutInflater.from(this)

        val view: View = factory.inflate(R.layout.menu_layout_efatura, null)

        val kulanciAdi = view.findViewById<TextView>(R.id.kullaniciAdi)
        kulanciAdi.setText(kadi)

       // setSupportActionBar(toolbar)

        var toogle = ActionBarDrawerToggle(this, drawlayout, toolbar, R.string.Logo, R.string.Logo)
        //setSupportActionBar(toolbar)

        // getSupportActionBar()?.hide()
        drawlayout.addDrawerListener(toogle)
        toogle.syncState()
        navi.bringToFront()
        navi.setNavigationItemSelectedListener(this)
        val ab: ActionBar? = supportActionBar
        /* to set the menu icon image*/
        /* to set the menu icon image*/ab?.setHomeAsUpIndicator(android.R.drawable.ic_menu_add)
        ab?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout = findViewById<View>(com.selpar.pratikhasar.R.id.drawelayout) as DrawerLayout
        expandableList =
            findViewById<View>(com.selpar.pratikhasar.R.id.navigationmenu) as ExpandableListView
        val navigationView =
            findViewById<View>(com.selpar.pratikhasar.R.id.navigationview) as NavigationView

        navigationView?.let { setupDrawerContent(it) }
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        prepareListData()
        mMenuAdapter = ExpandableListAdapter(
            this,
            listDataHeader!!, listDataChild!!,
            expandableList!!
        )
        expandableList!!.setAdapter(mMenuAdapter)


        expandableList!!.setOnGroupClickListener { expandableListView, view, i, l ->
            false
        }
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.menu_animation)
        navigationView.startAnimation(animation)
        expandableList!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id -> //Nothing here ever fires
            System.err.println("child clicked")
            if (groupPosition == 0 && childPosition == 0) {
                val i = Intent(this, TumHesaplarActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())

                startActivity(i)
            }
            if (groupPosition == 0 && childPosition == 1) {
                val i = Intent(this, CariEkleActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 0 && childPosition == 2) {
                val i = Intent(this, TahsilatActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 0 && childPosition == 3) {
                val i = Intent(this, OdemeActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 1 && childPosition == 3) {
                val i = Intent(this, KesilenFaturalarActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }

            if (groupPosition == 1 && childPosition == 1) {
                val i = Intent(this, GonderilmisManuelFaturalarActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }/*
            if (groupPosition == 1 && childPosition == 2) {
                val i = Intent(this, GonderilmisManuelFaturalarActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }*/
            if (groupPosition == 1 && childPosition == 0) {
                val i = Intent(this, ManuelFaturaKesActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 1 && childPosition == 4) {
                val i = Intent(this, GelenFaturalarActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 1 && childPosition == 5) {
                val i = Intent(this, GidenFaturalarActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 1 && childPosition == 7) {
                val i = Intent(this, eFaturaKontorYuklemeActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }

            if (groupPosition == 2 && childPosition == 0) {
                temagetir()
                mDrawerLayout!!.closeDrawers()
            }
            if (groupPosition == 2 && childPosition == 3) {
                val i = Intent(this, MailAyarlariActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 2 && childPosition == 4) {
                val i = Intent(this, SanalPosAyarlariActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 2 && childPosition == 5) {
                val i = Intent(this, IzinAyarlariActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 3 && childPosition == 0) {
                val i = Intent(this, SmsKampanyaActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())



                startActivity(i)

            }
            if (groupPosition == 3 && childPosition == 1) {
                val i = Intent(this, MailKampanyaActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 6 && childPosition == 0) {
                val i = Intent(this, SatinAlmaEkraniActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)
                i.putExtra("kullanici_resim", intent.getStringExtra("kullanici_resim").toString())


                startActivity(i)

            }
            if (groupPosition == 6 && childPosition == 1) {
                val i = Intent(this, GecmisAlimlarEkraniActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 2 && childPosition == 1) {
                if (gorev != "kullanici") {
                    val i = Intent(this, KullaniciTanimlamaActivity::class.java)
                    i.putExtra("dilsecimi", dilsecimi)
                    i.putExtra("yetki", yetki)
                    i.putExtra("firma_id", firma_id)
                    i.putExtra("kullanici_id", kullnciid)
                    i.putExtra("kadi", kadi)
                    i.putExtra("sifre", sifrem)
                    i.putExtra("kadi", kadi)
                    i.putExtra("gorev", gorev)

                    startActivity(i)
                }

            }
            if (groupPosition == 4 && childPosition == 0) {
                val i = Intent(this, ZamanliislemlerActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)


            }
            if (groupPosition == 4 && childPosition == 1) {
                val i = Intent(this, SigortaIslemleriActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)


            }
            if (groupPosition == 1 && childPosition == 6) {
                val i = Intent(this, EFaturaSistemAyarlariActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 1 && childPosition == 2) {
                val i = Intent(this, FaturaKesActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 5 && childPosition == 0) {
                val i = Intent(this, StokTanimlamaActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 5 && childPosition == 1) {
                val i = Intent(this, StokListesiActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 5 && childPosition == 2) {
                val i = Intent(this, PaketTanimlamaActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 5 && childPosition == 3) {
                val i = Intent(this, PaketGosterActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 5 && childPosition == 4) {
                val i = Intent(this, UstaTanimlamaActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 5 && childPosition == 5) {
                val i = Intent(this, UstaListelemeActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 7 && childPosition == 0) {
                val i = Intent(this, HakkimizdaActivity::class.java)
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("kadi", kadi)
                i.putExtra("sifre", sifrem)
                i.putExtra("kadi", kadi)
                i.putExtra("gorev", gorev)

                startActivity(i)

            }
            if (groupPosition == 8 && childPosition == 0) {
                val sheredpreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
                sheredpreferens.edit().remove("kadi").commit();
                sheredpreferens.edit().remove("sifre").commit();
                val editor = sheredpreferens.edit()
                editor.commit()
                editor.clear()
                editor.remove("kadi")
                editor.remove("sifre")
                cikis()

            }
            if (groupPosition == 2 && childPosition == 2) {
                if (gorev != "kullanici") {
                    val i = Intent(this, KullaniciListelemeActivity::class.java)
                    i.putExtra("dilsecimi", dilsecimi)
                    i.putExtra("yetki", yetki)
                    i.putExtra("firma_id", firma_id)
                    i.putExtra("kullanici_id", kullnciid)
                    i.putExtra("kadi", kadi)
                    i.putExtra("sifre", sifrem)
                    i.putExtra("kadi", kadi)
                    i.putExtra("gorev", gorev)

                    startActivity(i)
                }
            }
            //Toast.makeText(applicationContext, groupPosition.toString(), Toast.LENGTH_SHORT).show()
            true
        }

        btn_kart_ac.setOnClickListener {

            val i = Intent(this, KartAcActivity::class.java)
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("sifre", sifrem)
            i.putExtra("gorev", gorev)

            startActivity(i)
        }
        plaka_bilgi.setOnClickListener {
            plakaGetir()
        }
        btn_barkod.setOnClickListener {
            val i = Intent(this, ScanCodeActivity::class.java)
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi", kadi)
            i.putExtra("gorev", gorev)

            startActivity(i)

        }
        /*
        image_cikis.setOnClickListener {
            //Firebase.auth.signOut()
            val sheredpreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
            sheredpreferens.edit().remove("kadi").commit();
            sheredpreferens.edit().remove("sifre").commit();
            val editor = sheredpreferens.edit()
            editor.commit()
            editor.clear()
            editor.remove("kadi")
            editor.remove("sifre")
            cikis()
        }*/

        acikkartgoster.setOnClickListener {
            val i = Intent(this, AcikKartlarActivity::class.java)
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi", kadi)
            i.putExtra("gorev", gorev)

            startActivity(i)
        }
        btn_mekanik_kabul_karti_ac.setOnClickListener {
            val i = Intent(this, MekanikKartAcActivity::class.java)
            // Toast.makeText(this,firma_id,Toast.LENGTH_LONG).show()
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("sifre", sifrem)
            i.putExtra("gorev", gorev)


            startActivity(i)
        }
        id_onay_bekleyen.setOnClickListener {
            // Toast.makeText(this,"tıklandı",Toast.LENGTH_LONG).show()
        }
        id_teslimi_geciken.setOnClickListener {
            //Toast.makeText(this,"tıklandı",Toast.LENGTH_LONG).show()

        }
        kayitliaraclar.setOnClickListener {
            val i = Intent(this, KayitliAraclarAcActivity::class.java)
            // Toast.makeText(this,firma_id,Toast.LENGTH_LONG).show()
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("dilsecimi", dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("sifre", sifrem)
            i.putExtra("gorev", gorev)


            startActivity(i)
        }

    }
    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        progressBar.setVisibility(View.VISIBLE)
        progressBar.startAnimation(animation)

        // Simulate loading delay
        // Remove this code in your actual implementation
        Handler().postDelayed(Runnable {
            progressBar.clearAnimation()
            progressBar.setVisibility(View.GONE)
        }, 3000) // Replace 3000 with your desired loading time
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onHesapla(bitistarihi: String) {
      //  Toast.makeText(this, "Date: " + bitistarihi, Toast.LENGTH_LONG).show()
        val formatter2 = SimpleDateFormat("yyyy-MM-dd")
        val formatter = DateTimeFormatter.ISO_DATE
        val date = LocalDate.parse(bitistarihi, formatter)
        val bugun = LocalDate.now()

        val daysBetween = ChronoUnit.DAYS.between(bugun, date)
        // Toast.makeText(this,"FARK: "+daysBetween,Toast.LENGTH_LONG).show()
        if (daysBetween <= 5 && daysBetween>0) {
            mailAt(daysBetween)
            val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            builder.setTitle(getString(R.string.uyari))
            builder.setMessage(
                getString(R.string.uygulama1)+" " + daysBetween +" "+getString(R.string.uygulama2)+"\n" +
                        getString(R.string.uygulama1)+"\n" +
                        "www.pratikhasar.com"
            )
            builder.setPositiveButton(getString(R.string.evet)) { dialog, which ->
                val i = Intent(this, SatinAlmaEkraniActivity::class.java)
                i.putExtra("kadi", intent.getStringExtra("kadi"))
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("sifre", sifrem)
                i.putExtra("gorev", gorev)
                startActivity(i)
            }
            builder.setNegativeButton(getString(R.string.hayir)) { dialog, which ->
                // Do something when the "Cancel" button is clicked
            }
            val dialog = builder.create()
            dialog.show()

        }
        else if(daysBetween<=0){

            val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
            builder.setTitle(getString(R.string.uyari))
            builder.setMessage(
                getString(R.string.uygulamason)+
                        "\n" +
                        "www.pratikhasar.com"
            )
            builder.setPositiveButton(getString(R.string.tamam)) { dialog, which ->
                mailAtSureBitti(daysBetween)
                onCikisYap()
                val i = Intent(this, WebviewActivity::class.java)
                i.putExtra("kadi", intent.getStringExtra("kadi"))
                i.putExtra("dilsecimi", dilsecimi)
                i.putExtra("yetki", yetki)
                i.putExtra("firma_id", firma_id)
                i.putExtra("kullanici_id", kullnciid)
                i.putExtra("sifre", sifrem)
                i.putExtra("gorev", gorev)
                i.putExtra("path",
                    "https://pos.param.com.tr/Tahsilat/Default.aspx?k=1582893d-9f14-48b2-aa30-0ff5a9073ac8")
                val sheredpreferens = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
                sheredpreferens.edit().remove("kadi").commit()
                sheredpreferens.edit().remove("sifre").commit()
                val editor = sheredpreferens.edit()
                editor.commit()
                editor.clear()
                editor.remove("kadi")
                editor.remove("sifre")
                startActivity(i)
                finish()
            }

            val dialog = builder.create()
            dialog.show()


        }

    }

    private fun mailAtSureBitti(daysBetween: Long) {
       // Toast.makeText(this,"Mail Adresi: "+mail,Toast.LENGTH_LONG).show()
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mail_firma_uyarisi_sure_bitti.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response

               // Toast.makeText(this, "Mail Başarılı: ", Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI2")
               // Toast.makeText(this, "Mail Başarılı Değil : ", Toast.LENGTH_SHORT).show()

            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["gonderen"] = "destek@selpar.com.tr"
                params["sifre"] = "Destek06**"
                params["smtp_port"] = "587"
                params["ssl_port"] = "evet"
                params["mail_server"] = "smtp.yandex.com"
                params["alici"] = mail
                params["sure"] = daysBetween.toString()

                return params
            }
        }
        queue.add(postRequest)
    }

    private fun mailAt(daysBetween: Long) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mail_firma_uyarisi.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response

              //  Toast.makeText(this, "Mail Başarılı: ", Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI2")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["gonderen"] = "destek@selpar.com.tr"
                params["sifre"] = "Destek06**"
                params["smtp_port"] = "587"
                params["ssl_port"] = "evet"
                params["mail_server"] = "smtp.yandex.com"
                params["alici"] = mail
                params["sure"] = daysBetween.toString()

                return params
            }
        }
        queue.add(postRequest)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //  Toast.makeText(this,"verildi",Toast.LENGTH_LONG).show()
                } else {
                    //  Toast.makeText(this,"verilmedi ${permissions[i]}",Toast.LENGTH_LONG).show()
                    Log.d("IZIN", " ${permissions[i]}")
                }
            }
        }
    }

    private fun onProfil() {
        val urlek =
            "&kadi=" + intent.getStringExtra("kadi") + "&firma_id=" + intent.getStringExtra("firma_id")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=profil_bul" + urlek
        Log.d("PROFIL", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->

                val json = response["profil"] as JSONArray
                //println(outputObject["plaka"])
                for (i in 0 until json.length()) {
                    val item = json.getJSONObject(i)
                    val resim = item.getString("resim")
                    val ad = item.getString("ad")
                    val soyad = item.getString("soyad")
                    val brans = item.getString("brans")
                    var yedek = "https://pratikhasar.com/firmalar/" +
                            intent.getStringExtra(intent.getStringExtra("firma_id")) + "/" + resim
                    Picasso.get().load(
                        "https://pratikhasar.com/firmalar/" +
                                intent.getStringExtra("firma_id") + "/" + resim
                    ).into(imageprofil)
                    txtad_soyad.setText(ad + " " + soyad + "\n " + brans + "  " + getString(R.string.ustasi))
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    private fun SetLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config: Configuration = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor: SharedPreferences.Editor =
            getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {

            2 -> {
                temagetir()
            }


        }

        drawlayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun prepareListData() {
        listDataHeader = ArrayList<ExpandedMenuModel>()
        listDataChild = HashMap<ExpandedMenuModel, List<String>>()

        val item1 = ExpandedMenuModel()
        item1.setIconName(getString(R.string.carislemleri))
        item1.setIconImg(R.drawable.ic_baseline_person_24)
        // Adding data header
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item1)
        val item2 = ExpandedMenuModel()
        item2.setIconName(getString(R.string.efaturaislemleri))
        item2.setIconImg(R.drawable.ic_baseline_add_card_24)

        (listDataHeader as ArrayList<ExpandedMenuModel>)?.add(item2)


        val item3 = ExpandedMenuModel()
        item3.setIconName(getString(R.string.ayarlar))
        item3.setIconImg(R.drawable.ic_baseline_settings_24)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item3)
        val item4 = ExpandedMenuModel()
        item4.setIconName(getString(R.string.kampanyaduyuru))
        item4.setIconImg(R.drawable.ic_baseline_notifications_24)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item4)
        val item5 = ExpandedMenuModel()
        item5.setIconName(getString(R.string.zamanliislemler))
        item5.setIconImg(R.drawable.ic_baseline_av_timer_24)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item5)
        val item6 = ExpandedMenuModel()
        item6.setIconName(getString(R.string.tanimlamlamalar))
        item6.setIconImg(R.drawable.ic_baseline_text_snippet_24)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item6)
        val item7 = ExpandedMenuModel()
        item7.setIconName(getString(R.string.satinal))
        item7.setIconImg(R.drawable.ic_baseline_attach_money_24)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item7)
        val item8 = ExpandedMenuModel()
        item8.setIconName(getString(R.string.kurumsal))
        item8.setIconImg(R.drawable.ic_baseline_business_24)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item8)
        val item9 = ExpandedMenuModel()
        item9.setIconName(getString(R.string.cikis))
        item9.setIconImg(R.drawable.logout)
        (listDataHeader as ArrayList<ExpandedMenuModel>).add(item9)

        // Adding child data
        val heading1: MutableList<String> = ArrayList()
        heading1.add(getString(R.string.tumhesaplar))
        heading1.add(getString(R.string.cariekleduzenle))
        heading1.add(getString(R.string.tahsilatgirisi))
        heading1.add(getString(R.string.odemegirisi))
        val heading2: MutableList<String> = ArrayList()
        heading2.add(getString(R.string.manuelfaturakes))
        heading2.add(getString(R.string.gelenmanuelfaturalar))
        heading2.add(getString(R.string.faturakes))

        heading2.add(getString(R.string.kesilenfaturalar))
        heading2.add(getString(R.string.gelenfaturalar))
        heading2.add(getString(R.string.gonderilmisfaturalar))
        heading2.add(getString(R.string.faturasistemayarlari))
        heading2.add(getString(R.string.faturakontoryukleme))
        val heading3: MutableList<String> = ArrayList()
        heading3.add(getString(R.string.menuayari))
        heading3.add(getString(R.string.kullanicitanimlamalari))
        heading3.add(getString(R.string.kullanicilistele))
        heading3.add(getString(R.string.mailayarlari))
        heading3.add(getString(R.string.sanalposayarlari))
        heading3.add(getString(R.string.izinler))

        val heading4: MutableList<String> = ArrayList()

        val heading6: MutableList<String> = ArrayList()
        heading6.add(getString(R.string.zamanli))
        heading6.add(getString(R.string.sigorta1))
        /*heading6.add(getString(R.string.trafiksigortasiyaklasan))
        heading6.add(getString(R.string.kaskosigortasiyaklasan))*/
        val heading7: MutableList<String> = ArrayList()
        heading7.add(getString(R.string.smsgonder))
        heading7.add(getString(R.string.mailgonder))
        val heading8: MutableList<String> = ArrayList()

        heading8.add(getString(R.string.satinal))
        heading8.add(getString(R.string.gecmisalimlar))
        val heading9: MutableList<String> = ArrayList()
        heading9.add(getString(R.string.stoktanimlama))
        heading9.add(getString(R.string.stoklistesi))
        heading9.add(getString(R.string.pakettanimlama))
        heading9.add(getString(R.string.paketgoster))
        heading9.add(getString(R.string.ustatanimlama))
        heading9.add(getString(R.string.ustalisteleme))
        val heading10: MutableList<String> = ArrayList()
        heading10.add(getString(R.string.hakkimizda))
        val heading11: MutableList<String> = ArrayList()
        heading11.add(getString(R.string.cikis))




        listDataChild!!.put(
            (listDataHeader as ArrayList<ExpandedMenuModel>).get(0),
            heading1
        ) // Header, Child data
        listDataChild!!.put(
            (listDataHeader as ArrayList<ExpandedMenuModel>).get(1),
            heading2
        ) // Header, Child data
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(2), heading3)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(3), heading7)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(4), heading6)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(5), heading9)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(6), heading8)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(7), heading10)
        listDataChild!!.put((listDataHeader as ArrayList<ExpandedMenuModel>).get(8), heading11)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            1 -> {
                temagetir()
            }
            android.R.id.home -> {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    menuItem.setChecked(true)
                    if (menuItem.itemId == 0)
                        temagetir()
                    mDrawerLayout!!.closeDrawers()
                    //  Toast.makeText(this@HomeActivity,menuItem.itemId,Toast.LENGTH_LONG).show()
                    return true
                }
            })
    }

    private fun temagetir() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle(getString(R.string.menusecin))
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.popup_setting, null)
        check_bugun_acilan = view.findViewById(R.id.bugun_acilan)
        check_acik_kart = view.findViewById(R.id.acik_kartlar)
        check_teslimi_geciken = view.findViewById(R.id.teslimi_geciken)
        check_onay_bekleyen = view.findViewById(R.id.onay_bekleyen)
       // check_parca_tedarik = view.findViewById(R.id.parca_tedarik)
       // check_parca_iscilik = view.findViewById(R.id.parca_iscilik)
        check_teslime_hazir = view.findViewById(R.id.teslime_hazir)
        check_kapali_kartlar = view.findViewById(R.id.kapali_kartlar)


        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EVET"
        ) { dialogInterface, which ->
            if (check_bugun_acilan.isChecked) {
                id_bugun_acilan.visibility = VISIBLE
            } else {
                id_bugun_acilan.visibility = GONE
            }
            if (check_acik_kart.isChecked) {
                id_acik_kartlar.visibility = VISIBLE
            } else {
                id_acik_kartlar.visibility = GONE
            }
            if (check_teslimi_geciken.isChecked) {
                id_teslimi_geciken.visibility = VISIBLE
            } else {
                id_teslimi_geciken.visibility = GONE
            }
            if (check_onay_bekleyen.isChecked) {
                id_onay_bekleyen.visibility = VISIBLE
            } else {
                id_onay_bekleyen.visibility = GONE
            }
          /*  if (check_parca_tedarik.isChecked) {
                id_parca_tedarik.visibility = VISIBLE
            } else {
                id_parca_tedarik.visibility = GONE
            }
            if (check_parca_iscilik.isChecked) {
                id_parca_iscilik.visibility = VISIBLE
            } else {
                id_parca_iscilik.visibility = GONE
            }*/
            if (check_teslime_hazir.isChecked) {
                id_teslime_hazir.visibility = VISIBLE
            } else {
                id_teslime_hazir.visibility = GONE
            }
            if (check_kapali_kartlar.isChecked) {
                id_kapali_kartlar.visibility = VISIBLE
            } else {
                id_kapali_kartlar.visibility = GONE
            }


        }

        alertadd.show()
    }

    private fun plakaGetir() {
        val i = Intent(this, TumAlanAramaActivity::class.java)
        i.putExtra("yetki", intent.getStringExtra("yetki"))
        i.putExtra("firma_id", intent.getStringExtra("firma_id"))
        i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
        i.putExtra("kadi", intent.getStringExtra("kadi"))
        i.putExtra("sifre", intent.getStringExtra("sifre"))
        plaka_no.setSelection(3)
        plaka_no.setFocusableInTouchMode(true);
        plaka_no.requestFocus()
        if (plaka_no.getText().toString().isNotEmpty()) {
            i.putExtra("aranan", plaka_no.getText().toString().trim())
            startActivity(i)
        } else
            Toast.makeText(this, "Lütfen bir arana bilgiyi girin", Toast.LENGTH_SHORT).show()

    }

    private fun cikis() {
        onCikisYap()
        val cikis = Intent(this, MainActivity::class.java)
        startActivity(cikis)
        finish()

    }

    private fun onCikisYap() {
        val queue = Volley.newRequestQueue(this)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response -> // response
                Log.d("Response", response!!)

            },
            Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["kadi"] = kadi
                params["sifre"] = sifrem
                params["firma_id"] = firma_id
                params["tur"] = "cikis_log_ekle"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun onBaslat() {

        bugunacilan = findViewById<TextView>(R.id.bugunacilan)
        acikkart = findViewById<TextView>(R.id.acikkart)
        onaybekleyen = findViewById<TextView>(R.id.onaybekleyen)
        teslimigeciken = findViewById<TextView>(R.id.teslimigeciken)
        // tedarikbekleyen = findViewById<TextView>(R.id.tedarikbekleyen)
        //disbekleyen = findViewById<TextView>(R.id.disbekleyen)
        kapalikart = findViewById<TextView>(R.id.kapalikart)
        teslimehazir = findViewById<TextView>(R.id.teslimehazir)
        btn_barkod = findViewById<ImageView>(R.id.btn_barkod)
        plaka_bilgi = findViewById<ImageView>(R.id.btn_bilgi_ara)
        //image_cikis = findViewById<ImageView>(R.id.image_cikis)
        acikkartgoster = findViewById<TextView>(R.id.acikkartgoster)
        btn_kart_ac = findViewById<Button>(R.id.btn_kart_ac)
        btn_mekanik_kabul_karti_ac = findViewById<Button>(R.id.btn_mekanik_kabul_karti_ac)
        id_bugun_acilan = findViewById(R.id.id_bugun_acilan)
        id_acik_kartlar = findViewById(R.id.id_acik_kartlar)
        id_onay_bekleyen = findViewById(R.id.id_onay_bekleyen)
       // id_parca_tedarik = findViewById(R.id.id_parca_tedarik)
       // id_parca_iscilik = findViewById(R.id.id_parca_iscilik)
        id_teslime_hazir = findViewById(R.id.id_teslime_hazir)
        id_kapali_kartlar = findViewById(R.id.id_kapali_kartlar)
        id_teslimi_geciken = findViewById(R.id.id_teslimi_geciken)
        kayitliaraclar = findViewById(R.id.kayitliaraclar)
        kadi = intent.getStringExtra("kadi").toString()


    }

    private fun olustur() {
        val istek = Volley.newRequestQueue(this)
        val firma_id = intent.getStringExtra("firma_id")
        val url = "https://pratikhasar.com/netting/mobil.php"
        val requestBody = "tur=anasayfadakiler" + "&firma_id=" + firma_id
        Log.d("acikkart", url + requestBody)
        val stringReq: StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    // response
                    var strResps = response.toString()
                    var strResp = strResps.substring(1, strResps.length - 1)

                    val arr = strResp.split("%%Tt%%%")
                    bugunacilan.setText(arr[0])
                    acikkart.setText(arr[1])
                    onaybekleyen.setText(arr[2])
                    teslimigeciken.setText(arr[3])
                   // tedarikbekleyen.setText(arr[4])
                    //disbekleyen.setText(arr[5])
                    kapalikart.setText(arr[6])
                    teslimehazir.setText(arr[7])
                },

                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        istek.add(stringReq)

    }

    private fun onAktive() {

        var url =
            "https://pratikhasar.com/netting/mobil.php?tur=kart_active&kadi=" + intent.getStringExtra(
                "kadi"
            ).toString()
        Log.d("ACTiVE", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["servis_turu"] as JSONArray

                    var servis_turu: String
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        servis_turu = item.getString("servis").toString()
                        if (item.getString("servis").toString() == "Mekanik Servis") {
                            btn_kart_ac.visibility = View.GONE
                        } else if (item.getString("servis").toString() == "Kaporta-Boya Servisi") {
                            btn_mekanik_kabul_karti_ac.visibility = View.GONE
                        } else if (item.getString("servis")
                                .toString() == R.string.hasar_onarim_merkezi.toString()
                        ) {
                            btn_kart_ac.visibility = View.VISIBLE
                            btn_mekanik_kabul_karti_ac.visibility = View.VISIBLE
                        }

                    }


                } catch (e: Exception) {
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(this, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }


    fun goster(v: View) {
        val i = Intent(this, AcikKartlarActivity::class.java)
        i.putExtra("yetki", intent.getStringExtra("yetki"))
        i.putExtra("firma_id", intent.getStringExtra("firma_id"))
        i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
        i.putExtra("kadi", intent.getStringExtra("kadi"))
        i.putExtra("sifre", intent.getStringExtra("sifre"))
        startActivity(i)

    }

    fun bugunAcilan(v: View) {
        val i = Intent(this, BugunAcilanActivity::class.java)
        i.putExtra("yetki", intent.getStringExtra("yetki"))
        i.putExtra("firma_id", intent.getStringExtra("firma_id"))
        i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
        i.putExtra("kadi", intent.getStringExtra("kadi"))
        i.putExtra("sifre", intent.getStringExtra("sifre"))
        startActivity(i)

    }

    fun teslime_hazir(v: View) {
        val i = Intent(this, TeslimeHazirActivity::class.java)
        i.putExtra("yetki", intent.getStringExtra("yetki"))
        i.putExtra("firma_id", intent.getStringExtra("firma_id"))
        i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
        i.putExtra("kadi", intent.getStringExtra("kadi"))
        i.putExtra("sifre", intent.getStringExtra("sifre"))
        startActivity(i)

    }

    fun kapali_kartlar(v: View) {
        val i = Intent(this, KapaliKartlarActivity::class.java)
        i.putExtra("yetki", intent.getStringExtra("yetki"))
        i.putExtra("firma_id", intent.getStringExtra("firma_id"))
        i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
        i.putExtra("kadi", intent.getStringExtra("kadi"))
        i.putExtra("sifre", intent.getStringExtra("sifre"))
        startActivity(i)

    }

    private fun kartAcikA() {
        val acik = Intent(this, MekanikAcikKartlarActivity::class.java)
        startActivity(acik)
        /*
        Toast.makeText(this,
            "The favorite list would appear on clicking this icon",
            Toast.LENGTH_LONG).show();*/
    }


}



