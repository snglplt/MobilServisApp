package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.StokListModel
import com.selpar.pratikhasar.data.StokItem

class StokListAdapter_List_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){

    lateinit var txt_stokno: TextView
    lateinit var txt_stokadi: TextView
    lateinit var txt_tutar: TextView
    lateinit var txt_parca: TextView
    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    fun bindItems(itemModel: StokListModel) {
        txt_stokno = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_stokno) as TextView
        txt_stokadi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_stokadi) as TextView
        txt_tutar = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tutar) as TextView
        txt_parca=itemView.findViewById(com.selpar.pratikhasar.R.id.txt_parca)
        txt_stokno.setText(itemModel.stokno)
        txt_stokadi.setText(itemModel.stokAdi)
        txt_tutar.setText(itemModel.tutar)
        txt_parca.setText(itemModel.parca)



        //bakiye.setText((itemModel.alacak.toString().toFloat()-itemModel.borc.toString().toFloat()).toString())

    }


}
