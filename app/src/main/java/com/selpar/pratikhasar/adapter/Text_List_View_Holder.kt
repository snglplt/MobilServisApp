package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.data.StokItem

class Text_List_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){

    lateinit var id: TextView
    lateinit var usta: TextView
    lateinit var aciklama: TextView
    lateinit var txtsira: TextView

    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    fun bindItems(itemModel: TextModel) {
        id = itemView.findViewById(com.selpar.pratikhasar.R.id.txtid) as TextView
        usta = itemView.findViewById(com.selpar.pratikhasar.R.id.txtusta) as TextView
        aciklama = itemView.findViewById(com.selpar.pratikhasar.R.id.txtaciklama) as TextView
        txtsira = itemView.findViewById(com.selpar.pratikhasar.R.id.txtsira) as TextView
        txtsira.setText(itemModel.sira.toString() +"- ")
        id.setText(itemModel.id)
        usta.setText(itemModel.usta)
        aciklama.setText(itemModel.aciklama)


        //bakiye.setText((itemModel.alacak.toString().toFloat()-itemModel.borc.toString().toFloat()).toString())

    }


}