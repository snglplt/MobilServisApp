package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.ui.FaturaPdfGosterActivity
import com.selpar.pratikhasar.ui.GidenFaturalarActivity
import com.selpar.pratikhasar.ui.HomeActivity
import com.selpar.pratikhasar.ui.KesilenFaturalarActivity
import com.squareup.picasso.Picasso
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.io.File
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("MissingInflatedId")
class Giden_Fatura_List_Holder (itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var txtfatura_kodu:TextView
    lateinit var txtfatura_tarihi:TextView
    lateinit var txtgonderkontrol:TextView
    lateinit var txt_unvan:TextView
    lateinit var txtid:TextView
    lateinit var txtplaka:TextView
    lateinit var txtozel_id:TextView
    lateinit var txt_tutar:TextView
    lateinit var txt_evrakturu:TextView
    lateinit var btn_gonder:Button

    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: GidenFaturaModel) {
        txt_evrakturu = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_evrakturu) as TextView
        txt_tutar = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tutar) as TextView
        txt_unvan = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_unvan) as TextView
         txtfatura_kodu = itemView.findViewById(com.selpar.pratikhasar.R.id.txtfatura_kodu) as TextView
         txtfatura_tarihi = itemView.findViewById(com.selpar.pratikhasar.R.id.txtfatura_tarihi) as TextView
         txtgonderkontrol = itemView.findViewById(com.selpar.pratikhasar.R.id.txtgonderkontrol) as TextView
        txtid = itemView.findViewById(com.selpar.pratikhasar.R.id.txtid) as TextView
        txtplaka = itemView.findViewById(com.selpar.pratikhasar.R.id.txtplaka) as TextView
        txtozel_id = itemView.findViewById(com.selpar.pratikhasar.R.id.txtozel_id) as TextView
        btn_gonder = itemView.findViewById(com.selpar.pratikhasar.R.id.btn_gonder) as Button
        txtfatura_kodu.setText(itemView.context.getString(com.selpar.pratikhasar.R.string.evrak_kodu)+": "+itemModel.kodu)
        txtfatura_tarihi.setText(itemModel.tarih)
        txtgonderkontrol.setText(itemModel.gonder)
        txt_unvan.setText(itemModel.unvan)
        txtid.setText(itemModel.id)
        txt_evrakturu.setText(itemModel.evrakturu)
        val hesap=itemModel.tutar.toString().split(".")
        val binler = hesap[0].toInt() / 1000
        val yuzler = hesap[0].toInt() % 1000
        val ondalik = (hesap[0].toInt() * 100).toInt() % 100
        if(binler==0){
            txt_tutar.setText("Tutar: "+itemModel.tutar)

        }else
            txt_tutar.setText("Tutar: "+binler+"."+yuzler+","+ondalik)


        if(itemModel.gonder=="GÖNDERİLMEDİ"){
            txtgonderkontrol.setTextColor(Color.RED)
            btn_gonder.visibility=VISIBLE
        }else{
            txtgonderkontrol.setTextColor(Color.BLACK)
        }
        itemView.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(itemView.context)

            // Başlık ve mesajı ayarla
            alertDialogBuilder.setTitle(itemView.context.getString(com.selpar.pratikhasar.R.string.fatura_pdf))
           // alertDialogBuilder.setMessage("Bu bir örnek alert dialog.")

            // Pozitif butonu ayarla
            alertDialogBuilder.setPositiveButton(itemView.context.getString(com.selpar.pratikhasar.R.string.evet)) { dialog, which ->
                // Pozitif butona tıklandığında yapılacak işlemleri burada gerçekleştirin
                val i=Intent(itemView.context, FaturaPdfGosterActivity::class.java)
                i.putExtra("path","https://pratikhasar.com/netting/e_giden_pdf.php?firma_id="+itemModel.firma_id+"&InvoiceUUID="+itemModel.guid+"&id="+itemModel.id)
               itemView.context.startActivity(i)
            }

            // Negatif butonu ayarla
            alertDialogBuilder.setNegativeButton(itemView.context.getString(com.selpar.pratikhasar.R.string.hayir)) { dialog, which ->
                // Negatif butona tıklandığında yapılacak işlemleri burada gerçekleştirin
                dialog.dismiss()
            }

            // Alert dialog nesnesini oluştur ve göster
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }
        btn_gonder.setOnClickListener{
            val istek= Volley.newRequestQueue(itemView.context)
            //10.0.2.2/10.0.2.2:8080/
            val url="https://pratikhasar.com/netting/e_gonder.php?"+
                    "id=" + itemModel.id +"&firma_id="+itemModel.firma_id
            Log.d("test",url)
            val i="\\u0131"
            val o="\\u00f6"
            val c="\\u00e7"
            val s="\\u015f"
            val g="\\u011f"
            val O="\\u00d6"
            val I="\\u0130"
            val u="\\u00fc"
            val stringReq : StringRequest =
                object : StringRequest(
                    Method.GET, url,
                    Response.Listener { response ->

                        // response
                        var strResps = response.toString()
                        Toast.makeText(itemView.context,strResps,Toast.LENGTH_LONG).show()

                        val builder = AlertDialog.Builder(itemView.context)
                        builder.setTitle("Sonuç")

                        builder.setMessage(strResps.replace(i,"ı")
                                                   .replace(o,"ö")
                                                   .replace(c,"ç")
                                                   .replace(s,"ş")
                                                   .replace(g,"ğ")
                                                   .replace(O,"Ö")
                                                   .replace(I,"İ")
                                                   .replace(u,"ü"))
                        builder.setPositiveButton("OK") { dialog, which ->
                            val i=Intent(itemView.context,KesilenFaturalarActivity::class.java)
                            i.putExtra("firma_id",itemModel.firma_id)
                            i.putExtra("kadi",itemModel.unvan)
                            itemView.context.startActivity(i)

                            // Positive button clicked
                        }
                        builder.setNegativeButton("Cancel") { dialog, which ->
                            // Negative button clicked
                        }
                        val dialog = builder.create()
                        dialog.show()

                    }
                    ,

                    Response.ErrorListener { error ->
                        Log.d("API", "error => $error")
                        val builder = AlertDialog.Builder(itemView.context)
                        builder.setTitle("Sonuç")
                        builder.setMessage(error.toString().replace(i,"ı"))
                        builder.setPositiveButton("OK") { dialog, which ->
                            // Positive button clicked
                        }
                        builder.setNegativeButton("Cancel") { dialog, which ->
                            // Negative button clicked
                        }
                        val dialog = builder.create()
                        dialog.show()
                    }
                )

                {
                                    }
            val timeout = 10000 // 10 seconds in milliseconds
            stringReq.retryPolicy = DefaultRetryPolicy(timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            istek.add(stringReq)
        }


    }



}
