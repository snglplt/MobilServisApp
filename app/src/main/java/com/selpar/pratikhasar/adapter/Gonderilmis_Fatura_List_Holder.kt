package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.BuildConfig
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.FaturaPdfGosterActivity
import java.io.File

class Gonderilmis_Fatura_List_Holder(itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var txt_tutar: TextView
    lateinit var txtdurum: TextView
    lateinit var txt_tarih: TextView
    lateinit var txtturu: TextView
    lateinit var txt_tipi: TextView
    lateinit var txt_cari: TextView
    lateinit var txt_id: TextView
    lateinit var txt_faturaid: TextView
    lateinit var imageview_pdf:ImageView
    lateinit var imageview_sil:ImageView
    lateinit var imageview_ice_aktar:ImageView
    lateinit var imageview_whatssapp:ImageView
    lateinit var firma_id:String


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: GonderilmisFaturaModel) {
        imageview_pdf = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_pdf) as ImageView
        imageview_sil = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_sil) as ImageView
        imageview_ice_aktar = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_ice_aktar) as ImageView
        imageview_whatssapp = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_whatssapp) as ImageView
        txt_tutar = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tutar) as TextView
        txtdurum = itemView.findViewById(com.selpar.pratikhasar.R.id.txtdurum) as TextView
        txt_faturaid = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_fatura_id) as TextView
        txt_id = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_id) as TextView
        txt_tarih = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tarih) as TextView
        txtturu = itemView.findViewById(com.selpar.pratikhasar.R.id.txtturu) as TextView
        txt_tipi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tipi) as TextView
        txt_cari = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_cari) as TextView
        txt_id.setText(itemModel.id)
        txt_faturaid.setText(itemModel.evrakno)
        txtdurum.setText(itemModel.durumu)
        txt_tutar.setText("Tutar: "+itemModel.tutar)
        firma_id=itemModel.evrakturu
        if(itemModel.evrakno=="REJECTED - SUCCEED"){//CANCELLED-REJECTED
            txtdurum.setBackgroundColor(Color.RED)


       }
        //txt_tarih.setText(itemModel.tarih)
        txtturu.setText(itemModel.turu)
        txt_tipi.setText(itemModel.evraktipi)
        txt_cari.setText(itemModel.cari_ad)
        val parts = itemModel.tarih.split("-")
        try{
            txt_tarih.setText(parts[2].toInt().toString()+"-"+parts[1].toInt().toString()+"-"+parts[0].toInt().toString())

        }catch (e:Exception){
            txt_tarih.setText(itemModel.tarih)
        }
        imageview_whatssapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)

            // Setting Intent type
            intent.type = "text/plain"

            // Setting whatsapp package name
            intent.setPackage("com.whatsapp")

            // Give your message here
            intent.putExtra(Intent.EXTRA_TEXT, "Gönderen firma: "+itemModel.cari_ad+" Tutar: "+itemModel.tutar)



            // Starting Whatsapp
            itemView.context.startActivity(intent)
        }
        imageview_pdf.setOnClickListener {onPdfGoster(txt_id.getText().toString(),txt_faturaid.getText().toString())  }
        imageview_sil.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(itemView.context,R.style.AlertDialogCustom)

            // Başlık ve mesajı ayarlama
            alertDialogBuilder.setTitle(itemView.context.getString(R.string.fatura_red))

            // Olumlu butonun ayarlanması
            alertDialogBuilder.setPositiveButton(itemView.context.getString(R.string.evet)) { dialog, which ->
                // Olumlu butona tıklanınca yapılacak işlemler
                // Örneğin, bir işlem gerçekleştirilebilir
                onReddet(txt_id.getText().toString(),txt_faturaid.getText().toString())

                dialog.dismiss() // AlertDialog'u kapat
            }

            // Olumsuz butonun ayarlanması
            alertDialogBuilder.setNegativeButton(itemView.context.getString(R.string.hayir)) {
                    dialog, which ->
                // Olumsuz butona tıklanınca yapılacak işlemler
                // Örneğin, başka bir işlem gerçekleştirilebilir
                dialog.dismiss() // AlertDialog'u kapat
            }

            // AlertDialog'u oluşturma ve gösterme
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        imageview_ice_aktar.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(itemView.context,R.style.AlertDialogCustom)

            // Başlık ve mesajı ayarlama
            alertDialogBuilder.setTitle(itemView.context.getString(R.string.fatura_kabul))

            // Olumlu butonun ayarlanması
            alertDialogBuilder.setPositiveButton(itemView.context.getString(R.string.evet)) { dialog, which ->
                // Olumlu butona tıklanınca yapılacak işlemler
                // Örneğin, bir işlem gerçekleştirilebilir
                onKabulEtFatura(txt_id.getText().toString(),txt_faturaid.getText().toString())

                dialog.dismiss() // AlertDialog'u kapat
            }

            // Olumsuz butonun ayarlanması
            alertDialogBuilder.setNegativeButton(itemView.context.getString(R.string.hayir)) {
                    dialog, which ->
                // Olumsuz butona tıklanınca yapılacak işlemler
                // Örneğin, başka bir işlem gerçekleştirilebilir
                dialog.dismiss() // AlertDialog'u kapat
            }

            // AlertDialog'u oluşturma ve gösterme
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


        //  val date = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[1].toInt())
    }

    private fun onKabulEtFatura(guid: String, id: String) {
        val urlsb = "&UID="+guid+"&evrakno="+id+"&redonay=KABUL"
        var url = "https://pratikhasar.com/netting/e_red.php?firma_id="+firma_id + urlsb
        Log.d("STOK",url)
        val queue: RequestQueue = Volley.newRequestQueue(itemView.context)
        //val requestBody = "tur=deneme"
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                try{
                    Toast.makeText(itemView.context,"Sonuc: "+response,Toast.LENGTH_LONG).show()
                    val alertDialogBuilder = AlertDialog.Builder(itemView.context,R.style.AlertDialogCustom)

                    // Başlık ve mesajı ayarlama
                    if(response.toString()==" "){
                        alertDialogBuilder.setTitle(itemView.context.getString(R.string.fatura_rededilmistir))

                    }else{
                        alertDialogBuilder.setTitle(response)

                    }

                    // Olumlu butonun ayarlanması
                    alertDialogBuilder.setPositiveButton(itemView.context.getString(R.string.tamam)) { dialog, which ->
                        // Olumlu butona tıklanınca yapılacak işlemler
                        // Örneğin, bir işlem gerçekleştirilebilir


                        dialog.dismiss() // AlertDialog'u kapat
                    }

                    // Olumsuz butonun ayarlanması


                    // AlertDialog'u oluşturma ve gösterme
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    private fun onReddet(guid: String, id: String) {
        val urlsb = "&UID="+guid+"&evrakno="+id+"&redonay=RED"
        var url = "https://pratikhasar.com/netting/e_red.php?firma_id="+firma_id + urlsb
        Log.d("STOK",url)
        val queue: RequestQueue = Volley.newRequestQueue(itemView.context)
        //val requestBody = "tur=deneme"
                val request = StringRequest(Request.Method.GET, url,
                    Response.Listener<String> { response ->
                try{
                   Toast.makeText(itemView.context,"Sonuc: "+response,Toast.LENGTH_LONG).show()
                    val alertDialogBuilder = AlertDialog.Builder(itemView.context,R.style.AlertDialogCustom)

                    // Başlık ve mesajı ayarlama
                    alertDialogBuilder.setTitle(response)

                    // Olumlu butonun ayarlanması
                    alertDialogBuilder.setPositiveButton(itemView.context.getString(R.string.tamam)) { dialog, which ->
                        // Olumlu butona tıklanınca yapılacak işlemler
                        // Örneğin, bir işlem gerçekleştirilebilir


                        dialog.dismiss() // AlertDialog'u kapat
                    }

                    // Olumsuz butonun ayarlanması


                    // AlertDialog'u oluşturma ve gösterme
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                   }catch (e:Exception){}
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    private fun onPdfGoster(id: String, fatura_id: String) {
        val i= Intent(itemView.context, FaturaPdfGosterActivity::class.java)
        i.putExtra("firma_id",firma_id)
        i.putExtra("path","https://pratikhasar.com/netting/e_gelen_pdf.php?firma_id=1&InvoiceUUID="+id+"&id="+fatura_id)
        itemView.context.startActivity(i)

    }



}

