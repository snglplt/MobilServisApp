package com.selpar.pratikhasar.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.selpar.pratikhasar.R
import java.io.IOException
import java.util.*


class CariEkleActivity : AppCompatActivity() {
    lateinit var firma_id:String
    lateinit var txt_cari_unvan:EditText
    lateinit var txt_adres:EditText
    lateinit var txt_tel:EditText
    lateinit var txt_gsm:EditText
    lateinit var txt_vergi_no:EditText
    lateinit var txt_vergi_dairesi:EditText
    lateinit var txt_il:EditText
    lateinit var txt_ilce:EditText
    lateinit var txt_yetkiliadi:EditText
    lateinit var txt_mail:EditText
    lateinit var btn_kaydet:Button
    lateinit var btn_konum_ekle:Button
    lateinit var lattitude:TextView
    lateinit var longitude:TextView
    lateinit var address:TextView
    lateinit var country:TextView
    lateinit var city:TextView
    lateinit var il:EditText
    lateinit var ilce:EditText
    lateinit var adres:EditText
    lateinit var adreslink:EditText
    lateinit var btn_geri:ImageView
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val REQUEST_CODE = 100
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_ekle)
        firma_id=intent.getStringExtra("firma_id").toString()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        onBaslat()
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
        btn_konum_ekle.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.konumsoru))
            builder.setPositiveButton(getString(R.string.suankonum)) { dialog, which ->
                suAnkiKonum()
            }
            builder.setNegativeButton(getString(R.string.konumgir)) { dialog, which ->
                konumGir()
            }
            val alertDialog = builder.create()
            alertDialog.show()

        }
        btn_kaydet.setOnClickListener {
            if(txt_vergi_no.getText().toString().length==11 || txt_vergi_no.getText().toString().length==10){
            val queue = Volley.newRequestQueue(this)
            var url = "https://pratikhasar.com/netting/mobil.php"
            val postRequest: StringRequest = @SuppressLint("RestrictedApi")
            object : StringRequest(
                Method.POST, url,
                com.android.volley.Response.Listener { response -> // response
                    Log.d("Response", response!!)
                    Toast.makeText(this,"Ekleme Başarılı: "+firma_id, Toast.LENGTH_SHORT).show()
                    sifirla()
                },
                com.android.volley.Response.ErrorListener {
                    Log.d("Response", "HATALI")
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = java.util.HashMap()
                    params["firma_id"] = firma_id
                    params["cari_unvan"] = txt_cari_unvan.getText().toString()
                    params["adres"] = txt_adres.getText().toString()
                    params["tel"]= txt_tel.getText().toString()
                    params["gsm"]=txt_gsm.getText().toString()
                    params["vergino"]=txt_vergi_no.getText().toString()
                    params["vergidairesi"]=txt_vergi_dairesi.getText().toString()
                    params["il"]=txt_il.getText().toString()
                    params["ilce"]=txt_ilce.getText().toString()
                    params["yetkili_isim_soyisim"]=txt_yetkiliadi.getText().toString()
                    params["mail"]=txt_mail.getText().toString()
                    params["enlem"]=lattitude.getText().toString()
                    params["boylam"]=longitude.getText().toString()
                    params["acikadres"]=address.getText().toString()
                    params["adreslink"]=adreslink.getText().toString()
                    params["tur"] = "cari_ekle"
                    return params
                }
            }
            queue.add(postRequest)
        }
        else{
            Toast.makeText(this,"Lütfen vergi numarasını 10 ya da 11 karakter giriniz!..",Toast.LENGTH_LONG).show()
        }
    }}

    @SuppressLint("MissingInflatedId")
    private fun konumGir() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle(getString(R.string.konumlink))
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.konum_gir, null)
        lattitude=view.findViewById(R.id.lattitude)
        longitude=view.findViewById(R.id.longitude)
        address=view.findViewById(R.id.address)
        country=view.findViewById(R.id.country)
        adreslink=view.findViewById(R.id.adres)
        //getLastLocation()
        alertadd.setView(view)
        alertadd.setPositiveButton(
            getString(R.string.evet)
        ) { dialogInterface, which ->



        }
        alertadd.setNegativeButton(getString(R.string.hayir)){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun suAnkiKonum() {
        val alertadd = AlertDialog.Builder(this)
        alertadd.setTitle(getString(R.string.konumkaydet))
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.konum_ekle, null)
        lattitude=view.findViewById(R.id.lattitude)
        longitude=view.findViewById(R.id.longitude)
        address=view.findViewById(R.id.address)
        country=view.findViewById(R.id.country)
        adreslink=view.findViewById(R.id.adres)

        getLastLocation()
        alertadd.setView(view)
        alertadd.setPositiveButton(
            getString(R.string.evet)
        ) { dialogInterface, which ->



        }
        alertadd.setNegativeButton(getString(R.string.hayir)){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()
    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient!!.getLastLocation()
                .addOnSuccessListener(OnSuccessListener<Location?> { location ->
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(this@CariEkleActivity, Locale.getDefault())
                            val addresses: List<Address>? =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            lattitude.text = addresses!![0].getLatitude().toString()
                            longitude.text = addresses[0].getLongitude().toString()
                            address.text = addresses[0].getAddressLine(0)
                           // city.setText(getString(R.string.il)+": " + addresses[0].getLocality())
                            country.text = addresses[0].getCountryName()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                })
        } else {
            askPermission()
        }
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this@CariEkleActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(
                    this@CariEkleActivity,
                    "Please provide the required permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private fun sifirla() {
        txt_cari_unvan.setFocusable(true)
        txt_cari_unvan.setText("")
        txt_adres.setText("")
        txt_tel.setText("")
        txt_gsm.setText("")
        txt_vergi_no.setText("")
        txt_vergi_dairesi.setText("")
        txt_il.setText("")
        txt_ilce.setText("")
        txt_yetkiliadi.setText("")
        txt_mail.setText("")
        adreslink.setText("")
        lattitude.setText("")
        longitude.setText("")
        address.setText("")
        country.setText("")
    }

    private fun onBaslat() {
        txt_cari_unvan=findViewById(R.id.txt_cari_unvan)
        txt_adres=findViewById(R.id.txt_adres)
        txt_tel=findViewById(R.id.txt_tel)
        txt_gsm=findViewById(R.id.txt_gsm)
        txt_vergi_no=findViewById(R.id.txt_vergi_no)
        txt_vergi_dairesi=findViewById(R.id.txt_vergi_dairesi)
        txt_il=findViewById(R.id.txt_il)
        txt_ilce=findViewById(R.id.txt_ilce)
        txt_yetkiliadi=findViewById(R.id.txt_yetkiliadi)
        txt_mail=findViewById(R.id.txt_mail)
        btn_kaydet=findViewById(R.id.btn_kaydet)
        btn_konum_ekle=findViewById(R.id.btn_konum_ekle)
        btn_geri=findViewById(R.id.btn_geri)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))



    }
}