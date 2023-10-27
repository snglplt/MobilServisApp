package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.GidenFaturaAdapter
import com.selpar.pratikhasar.adapter.GidenFaturaModel
import com.selpar.pratikhasar.adapter.GonderilmisFaturaAdapter
import com.selpar.pratikhasar.adapter.GonderilmisFaturaModel
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.fragment.GelenOnaylananFragment
import com.selpar.pratikhasar.fragment.KaskoSigortasiFragment
import com.selpar.pratikhasar.fragment.ReddedilenFaturaAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.ArrayList

class GidenFaturalarActivity : AppCompatActivity() {
    lateinit var rc_gonderilmis_faturalar:RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    lateinit var kadi:String
    lateinit var firma_id:String
    lateinit var spinner_evrak_turu: Spinner
    lateinit var spinner_kdv_turu: Spinner
    lateinit var spinner_e_fatura_tipi: Spinner
    lateinit var spinner_evrak_tipi: Spinner
    lateinit var spinner_e_kontrol: Spinner
    lateinit var spinner_plaka_yazdir: Spinner
    lateinit var spinner_km_yazdir: Spinner
    lateinit var spinner_no_yazdir: Spinner
    lateinit var et_sevk_tarihi: EditText
    lateinit var et_odeme_tarihi: EditText
    lateinit var et_evrak_tarihi: EditText
    lateinit var btn_onay_bekleyen:Button
    lateinit var btn_reddedilen:Button
    var bundlem=Bundle()
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giden_faturalar)
        onBaslat()
        onFaturaGetirOnay()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack.setOnClickListener {
            val i= Intent(this,HomeActivity::class.java)
            i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("gorev",intent.getStringExtra("gorev"))
            startActivity(i)
        }
        btn_onay_bekleyen.setOnClickListener { onFaturaGetirOnay() }
        btn_reddedilen.setOnClickListener {// onFaturaGetirRed()
         }

    }

    private fun onFaturaGetirOnay() {
        btn_onay_bekleyen.setBackgroundColor(Color.MAGENTA)
        btn_reddedilen.setBackgroundColor(Color.GRAY)
        bundlem.putString("kadi",kadi)
        bundlem.putString("firma_id",firma_id)
        val fragobj = GelenOnaylananFragment()
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=this.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_giden_faturalar,  fragobj)
            .commit()
    }





    private fun onBaslat() {
        btn_onay_bekleyen=findViewById(R.id.btn_onay_bekleyen)
        btn_reddedilen=findViewById(R.id.btn_reddedilen)
        kadi=intent.getStringExtra("kadi").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
    }
}
