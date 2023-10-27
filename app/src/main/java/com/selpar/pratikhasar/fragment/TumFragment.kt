package com.selpar.pratikhasar.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.selpar.pratikhasar.R


class TumFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btnResim:Button
    lateinit var btnolay:Button
    lateinit var btnservis:Button
    lateinit var btnyapim:Button
    lateinit var btnbitmis:Button
    var bundlem=Bundle()

    lateinit var kadi:String
    lateinit var sifre:String
    lateinit var firma_id:String
    lateinit var plaka:String
    lateinit var kabulnom:String
    lateinit var kullanici_id:String
    lateinit var ozel_id:String



    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_tum, container, false)

        btnResim=view.findViewById(R.id.btnResim)
        btnolay=view.findViewById(R.id.btnolay)
        btnservis=view.findViewById(R.id.btnservis)
        btnyapim=view.findViewById(R.id.btnyapim)
        btnbitmis=view.findViewById(R.id.btnbitmis)

        btnResim.setBackgroundColor(0xff60a0e0.toInt())
        btnolay.setBackgroundColor(0)
        btnservis.setBackgroundColor(0)
        btnyapim.setBackgroundColor(0)
        btnbitmis.setBackgroundColor(0)

        val args= this.arguments
        kadi=args?.getString(("kadi")).toString()
        sifre= args?.getString("sifre").toString()
        firma_id= args?.getString("firma_id").toString()
        plaka= args?.getString("plaka").toString()
        kabulnom= args?.getString("kabulnom").toString()
        kullanici_id= args?.getString("kullanici_id").toString()
        ozel_id= args?.getString("ozel_id").toString()
        Resim()
        btnResim.setOnClickListener {
            Resim()
        }
        btnolay.setOnClickListener {
            btnResim.setBackgroundColor(0)
            btnolay.setBackgroundColor(Color.RED)
            btnservis.setBackgroundColor(0)
            btnyapim.setBackgroundColor(0)
            btnbitmis.setBackgroundColor(0)
            Olay()

        }
        btnservis.setOnClickListener {
            btnResim.setBackgroundColor(0)
            btnolay.setBackgroundColor(0)
            btnservis.setBackgroundColor(Color.RED)
            btnyapim.setBackgroundColor(0)
            btnbitmis.setBackgroundColor(0)
            Servis()
        }
        btnyapim.setOnClickListener {
            btnResim.setBackgroundColor(0)
            btnolay.setBackgroundColor(0)
            btnservis.setBackgroundColor(0)
            btnyapim.setBackgroundColor(Color.RED)
            btnbitmis.setBackgroundColor(0)
            YapimAsamasi()
        }
        btnbitmis.setOnClickListener {
            btnResim.setBackgroundColor(0)
            btnolay.setBackgroundColor(0)
            btnservis.setBackgroundColor(0)
            btnyapim.setBackgroundColor(0)
            btnbitmis.setBackgroundColor(Color.RED)
            BitmisHali()
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun BitmisHali() {
        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        val fragobj = BitmisHaliFragment()
        fragobj.arguments=bundlem

        // fragobje.arguments=bundlem
        //fragobj.stArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame2,  fragobj)
            .commit()
    }

    private fun YapimAsamasi() {

        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        val fragobj = YapimAsamasiFragment()
        fragobj.arguments=bundlem
        // fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame2,  fragobj)
            .commit()
    }

    private fun Servis() {

        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        val fragobj = ServisTespitFragment()
        fragobj.arguments=bundlem
        // fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame2,  fragobj)
            .commit()
    }

    private fun Olay() {
        var bundlem=Bundle()
        val args= this.arguments
        bundlem.putString("kadi",args?.getString(("kadi")).toString())
        bundlem.putString("ozel_id",args?.getString(("ozel_id")).toString())
        bundlem.putString("plaka",args?.getString(("plaka")).toString())
        bundlem.putString("firma_id",args?.getString(("firma_id")).toString())
        val fragobj = OlayYeriFragment()
        fragobj.arguments=bundlem

       // fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame2,  fragobj)
            .commit()
    }


    @SuppressLint("ResourceAsColor")
    private fun Resim() {
        btnResim.setBackgroundColor(Color.RED)
        btnolay.setBackgroundColor(0)
        btnservis.setBackgroundColor(0)
        btnyapim.setBackgroundColor(0)
        btnbitmis.setBackgroundColor(0)
        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        val fragobj = EvraklarFragment()
        fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame2,  fragobj)
            .commit()
    }


}