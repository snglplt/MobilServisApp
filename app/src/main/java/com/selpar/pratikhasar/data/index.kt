package com.selpar.pratikhasar.data

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.selpar.pratikhasar.R
import java.nio.charset.Charset

public val bundle=Bundle()

class index : AppCompatActivity() {
    private lateinit var currentFragment : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        val firma_id=intent.getStringExtra("firma_id")
        val kadi=intent.getStringExtra("kadi")
        val sifre=intent.getStringExtra("sifre")
        val yetki=intent.getStringExtra("yetki")
        val kullanici_id=intent.getStringExtra("kullanici_id")
        val istek= Volley.newRequestQueue(this)
        val url="https://pratikhasar.com/netting/mobil.php"
        val requestBody = "tur=anasayfadakiler" + "&firma_id="+firma_id
        val stringReq : StringRequest =
            object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    // response
                    var strResps = response.toString()
                    var strResp = strResps.substring(1, strResps.length -1)

                    val arr = strResp.split("%%Tt%%%")

                    bundle.putString("txt_bugunacilan",arr[0].toString())
                    bundle.putString("txt_acikkar",arr[1].toString())
                    bundle.putString("txt_onaybekleyen",arr[2].toString())
                    bundle.putString("txt_teslimigeciken",arr[3].toString())
                    bundle.putString("txt_tedarikbekleyen",arr[4].toString())
                    bundle.putString("txt_disbekleyen",arr[5].toString())
                    bundle.putString("txt_kapalikart",arr[6].toString())
                    bundle.putString("txt_teslimehazir",arr[7].toString())
                    bundle.putString("txt_dis",arr[8].toString())
                    bundle.putString("txt_ted",arr[9].toString())
                    bundle.putString("kadi",kadi)
                    bundle.putString("sifre",sifre)
                    bundle.putString("firma_id",firma_id)
                    bundle.putString("kullanici_id",kullanici_id)




                    //Log.d("API", strResps)
                },
                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ){
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        istek.add(stringReq)




    }





}