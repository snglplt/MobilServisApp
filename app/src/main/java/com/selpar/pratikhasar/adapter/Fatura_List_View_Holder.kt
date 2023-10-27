package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.File

class Fatura_List_View_Holder(itemView : View): RecyclerView.ViewHolder(itemView) {
    lateinit var sil_resim: String
    var bundlem = Bundle()
    lateinit var kadi: String
    lateinit var ozel_id: String
    lateinit var txtkodu:TextView
    lateinit var txtaciklama:TextView
    lateinit var txtmiktar:TextView
    lateinit var txtfiyat:TextView
    lateinit var txtkdvorani:TextView
    lateinit var txtkdvtutar:TextView
    lateinit var txttutar:TextView

    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: FaturaModel) {
        txtkodu = itemView.findViewById(com.selpar.pratikhasar.R.id.txtkodu) as TextView
        txtaciklama = itemView.findViewById(com.selpar.pratikhasar.R.id.txtaciklama) as TextView
        txtmiktar = itemView.findViewById(com.selpar.pratikhasar.R.id.txtmiktar) as TextView
        txtfiyat = itemView.findViewById(com.selpar.pratikhasar.R.id.txtfiyat) as TextView
        txtkdvorani = itemView.findViewById(com.selpar.pratikhasar.R.id.txtkdvorani) as TextView
        txtkdvtutar = itemView.findViewById(com.selpar.pratikhasar.R.id.txtkdvtutar) as TextView
        txttutar = itemView.findViewById(com.selpar.pratikhasar.R.id.txttutar) as TextView
        txtkodu.setText(itemModel.kodu)
        txtaciklama.setText(itemModel.aciklama)
        txtmiktar.setText(String.format("%.2f",itemModel.miktar.toFloat()))
        txtfiyat.setText(String.format("%.2f",itemModel.fiyat.toFloat()))
        txtkdvorani.setText("%"+itemModel.kdv_oran)
        txtkdvtutar.setText(String.format("%.2f",itemModel.kdv_tutar.toFloat()))
        txttutar.setText(String.format("%.2f",itemModel.tutar.toFloat()))



        }

}