package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.FaturaPdfGosterActivity

class Reddedilen_Fatura_List_Holder (itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var txt_tutar: TextView
    lateinit var txtdurum: TextView
    lateinit var txt_tarih: TextView
    lateinit var txtturu: TextView
    lateinit var txt_tipi: TextView
    lateinit var txt_cari: TextView
    lateinit var txt_fatura_tipi: TextView
    lateinit var txt_id: TextView
    lateinit var txt_faturaid: TextView
    lateinit var imageview_pdf:ImageView
    lateinit var imageview_whatsapp:ImageView



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: GonderilmisFaturaModel) {
        imageview_pdf = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_pdf) as ImageView
        imageview_whatsapp = itemView.findViewById(com.selpar.pratikhasar.R.id.imageview_whatsapp) as ImageView
        txt_tutar = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tutar) as TextView
        txtdurum = itemView.findViewById(com.selpar.pratikhasar.R.id.txtdurum) as TextView
        txt_faturaid = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_fatura_id) as TextView
        txt_id = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_id) as TextView
        txt_tarih = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tarih) as TextView
        txtturu = itemView.findViewById(com.selpar.pratikhasar.R.id.txtturu) as TextView
        txt_tipi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tipi) as TextView
        txt_cari = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_cari) as TextView
        txt_fatura_tipi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_fatura_tipi) as TextView
        txt_tutar.setText("Tutar: "+itemModel.tutar)
        txt_id.setText(itemModel.id)
        txt_faturaid.setText(itemModel.evrakno)
        txtdurum.setText(itemModel.durumu)
        if(itemModel.evrakno=="REJECTED - SUCCEED"){//CANCELLED-REJECTED
            txtdurum.setBackgroundColor(Color.RED)


        }
        txt_fatura_tipi.setText(itemModel.evraktipi)
        //txt_tarih.setText(itemModel.tarih)
        txtturu.setText(itemModel.turu)
        txt_tipi.setText(itemModel.evraktipi)
        txt_cari.setText(itemModel.cari_ad)
        val parts = itemModel.tarih.split("-")
        try{
            txt_tarih.setText("Tarih: "+parts[2].toInt().toString()+"-"+parts[1].toInt().toString()+"-"+parts[0].toInt().toString())

        }catch (e:Exception){
            txt_tarih.setText(itemModel.tarih)
        }
        imageview_pdf.setOnClickListener {onPdfGoster(txtdurum.getText().toString(),txt_id.getText().toString(),txt_faturaid.getText().toString())  }
        imageview_whatsapp.setOnClickListener {
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
        //  val date = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[1].toInt())
    }
    private fun onPdfGoster(durum:String,id: String, fatura_id: String) {
        if(durum=="RED EDİLDİ"){
            val builder = AlertDialog.Builder(itemView.context, R.style.AlertDialogCustom)
            builder.setTitle(itemView.context.getString(R.string.fatura_rededilmistir))
                .setPositiveButton(itemView.context.getString(R.string.tamam )) { dialog, which ->
                    // Positive button click handler
                }

            val dialog = builder.create()
            dialog.show()
        }else {
            val i = Intent(itemView.context, FaturaPdfGosterActivity::class.java)
            i.putExtra(
                "path",
                "https://pratikhasar.com/netting/e_giden_pdf.php?firma_id=1&InvoiceUUID=" + id + "&id=" + fatura_id
            )
            itemView.context.startActivity(i)
        }

    }



}

