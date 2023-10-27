package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.data.CariModel
import com.selpar.pratikhasar.data.StokItem
import com.selpar.pratikhasar.ui.eFaturaOlusturActivity

class Stok_List_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){

    lateinit var stokno: TextView
    lateinit var stokadi: TextView
    lateinit var fiyat: TextView
    lateinit var kdv: TextView
    lateinit var parabirimi: TextView
    lateinit var id: TextView
    lateinit var btn_fatura: ImageView
    lateinit var odeme: TextView
    lateinit var txt_parca_turu: TextView
    lateinit var textstokno: TextView
    lateinit var textstokadi: TextView
    lateinit var textfiyat: TextView
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var baslik_linnerr:TableRow
    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    fun bindItems(itemModel: StokItem) {
        stokno = itemView.findViewById(com.selpar.pratikhasar.R.id.stokno) as TextView
        stokadi = itemView.findViewById(com.selpar.pratikhasar.R.id.stokadi) as TextView
        fiyat = itemView.findViewById(com.selpar.pratikhasar.R.id.fiyat) as TextView
        kdv=itemView.findViewById(com.selpar.pratikhasar.R.id.kdv)
        id=itemView.findViewById(com.selpar.pratikhasar.R.id.stokid)
        parabirimi=itemView.findViewById(com.selpar.pratikhasar.R.id.parabirimi)
        txt_parca_turu=itemView.findViewById(com.selpar.pratikhasar.R.id.txt_parca_turu)
        baslik_linnerr=itemView.findViewById(com.selpar.pratikhasar.R.id.baslik_linnerr)
        textstokno=itemView.findViewById(com.selpar.pratikhasar.R.id.textstokno)
        textstokadi=itemView.findViewById(com.selpar.pratikhasar.R.id.textstokadi)
        textfiyat=itemView.findViewById(com.selpar.pratikhasar.R.id.textfiyat)
        stokno.setText(itemModel.stokno)
        stokadi.setText(itemModel.stokadi)
        fiyat.setText(itemModel.fiyat)
        kdv.setText(itemModel.kdv)
        parabirimi.setText(itemModel.parabirimi)
        id.setText(itemModel.stokId)
        txt_parca_turu.setText(itemModel.parca_turu)
        if(itemModel.parca_turu=="Parca" || itemModel.parca_turu=="Parts"){
            baslik_linnerr.setBackgroundColor(Color.BLUE)
            stokno.setTextColor(Color.WHITE)
            stokadi.setTextColor(Color.WHITE)
           // fiyat.setTextColor(Color.WHITE)
           // kdv.setTextColor(Color.WHITE)
           // parabirimi.setTextColor(Color.WHITE)
            txt_parca_turu.setTextColor(Color.WHITE)
            textstokno.setTextColor(Color.WHITE)
            textstokadi.setTextColor(Color.WHITE)
           // textfiyat.setTextColor(Color.WHITE)

        }
        else{
            baslik_linnerr.setBackgroundColor(Color.RED)

        }


        //bakiye.setText((itemModel.alacak.toString().toFloat()-itemModel.borc.toString().toFloat()).toString())

    }


}
