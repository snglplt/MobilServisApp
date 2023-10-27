package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.FaturaPdfGosterActivity
import java.util.HashMap

class Iceri_Aktar_Fatura_List_Holder (itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var txt_tutar: TextView
    lateinit var txtdurum: TextView
    lateinit var txt_tarih: TextView
    lateinit var txtturu: TextView
    lateinit var txt_tipi: TextView
    lateinit var txt_cari: TextView
    lateinit var txt_id: TextView
    lateinit var txt_faturaid: TextView
    lateinit var imageview_sil: ImageView
    lateinit var imageview_ice_aktar: ImageView
    lateinit var imageview_pdf:ImageView

    lateinit var firma_id:String
     var tutar:Int=0


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: GonderilmisFaturaModel) {
        imageview_pdf = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_pdf) as ImageView
        imageview_sil = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_sil) as ImageView
        imageview_ice_aktar = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_ice_aktar) as ImageView
        txt_tutar = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tutar) as TextView
        txtdurum = itemView.findViewById(com.selpar.pratikhasar.R.id.txtdurum) as TextView
        txt_faturaid = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_fatura_id) as TextView
        txt_id = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_id) as TextView
        txt_tarih = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tarih) as TextView
        txtturu = itemView.findViewById(com.selpar.pratikhasar.R.id.txtturu) as TextView
        txt_tipi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tipi) as TextView
        txt_cari = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_cari) as TextView
        txt_id.setText(itemModel.id)
        txt_faturaid.setText(itemModel.evrakturu)
        txtdurum.setText(itemModel.durumu)
        txt_tutar.setText("Tutar: "+itemModel.tutar)
        firma_id=itemModel.evrakturu
        val trim=itemModel.tutar.split(".")
        tutar=trim[0].toInt()
        if(itemModel.evrakno=="REJECTED - SUCCEED"){//CANCELLED-REJECTED
            txtdurum.setBackgroundColor(Color.RED)


        }
        //txt_tarih.setText(itemModel.tarih)
        txtturu.setText(itemModel.turu)
        txt_tipi.setText(itemModel.evraktipi)
        txt_cari.setText(itemModel.cari_ad)
        val parts = itemModel.tarih.split("-")
        txt_tarih.setText(parts[2].toInt().toString()+"-"+parts[1].toInt().toString()+"-"+parts[0].toInt().toString())
        imageview_sil.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(itemView.context, R.style.AlertDialogCustom)

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
        imageview_pdf.setOnClickListener {onPdfGoster(txt_id.getText().toString(),"ANY"+itemModel.evrakno)  }

        imageview_ice_aktar.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(itemView.context, R.style.AlertDialogCustom)

            // Başlık ve mesajı ayarlama
            alertDialogBuilder.setTitle(itemView.context.getString(R.string.fatura_kabul))

            // Olumlu butonun ayarlanması
            alertDialogBuilder.setPositiveButton(itemView.context.getString(R.string.evet)) { dialog, which ->
                // Olumlu butona tıklanınca yapılacak işlemler
                // Örneğin, bir işlem gerçekleştirilebilir
                onKabulEtFatura(itemModel.id,txt_id.getText().toString(),itemModel.turu,tutar,itemModel.cari_ad)

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

    private fun onKabulEtFatura(
        id: String,
        guid: String,
        cari: String,
        tutar: Int,
        cariAd: String
    ) {
        //val urlsb = "&InvoiceUUID="+guid+"&vergino="+cari+"&id="+id
        var url = "https://pratikhasar.com/netting/mobil.php"
        Log.d("STOK",url)
        val queue: RequestQueue = Volley.newRequestQueue(itemView.context)
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["InvoiceUUID"] = guid
                params["firma_id"] = firma_id
                params["vergino"] = cari
                params["id"]=id
                params["tutar"]=tutar.toString()
                params["cari"]=cariAd
                params["tur"]="cari_hesap_ekle_kaydet"
                return params
            }
        }
        queue.add(postRequest)

    }

    private fun onReddet(guid: String, id: String) {
        val urlsb = "&UID="+guid+"&evrakno="+id+"&redonay=RED"
        var url = "https://pratikhasar.com/netting/e_red.php?firma_id="+firma_id + urlsb
        Log.d("STOK",url)
        val queue: RequestQueue = Volley.newRequestQueue(itemView.context)
        //val requestBody = "tur=deneme"
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                try{
                    Toast.makeText(itemView.context,"Sonuc: "+response, Toast.LENGTH_LONG).show()
                    val alertDialogBuilder = AlertDialog.Builder(itemView.context, R.style.AlertDialogCustom)

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
        i.putExtra("path","https://pratikhasar.com/netting/e_gelen_pdf.php?firma_id="+firma_id+"&InvoiceUUID="+id+"&id="+fatura_id)
        itemView.context.startActivity(i)

    }



}

