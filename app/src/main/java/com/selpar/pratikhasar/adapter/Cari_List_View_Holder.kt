package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.CariModel
import com.selpar.pratikhasar.ui.eFaturaOlusturActivity

class Cari_List_View_Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var txt_cari_ad_tum: TextView
    lateinit var unvan: TextView
    lateinit var alacak: TextView
    lateinit var bakiye: TextView
    lateinit var tahsilat: TextView
    var bundlem = Bundle()
    lateinit var kadi: String
    lateinit var ozel_id: String

    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    fun bindItems(itemModel: CariModel) {
        unvan = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_cari) as TextView
        alacak = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_alacak) as TextView
        tahsilat = itemView.findViewById(com.selpar.pratikhasar.R.id.txttahsilat)
        bakiye = itemView.findViewById(com.selpar.pratikhasar.R.id.txtbakiye)
        txt_cari_ad_tum = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_cari_ad_tum)
        //bakiye = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_bakiye) as TextView
        txt_cari_ad_tum.setText(itemModel.isim)
        try{
            if(itemModel.isim.length>=10) {
                unvan.setText(itemModel.isim.substring(0, 10))
            }else
                unvan.setText(itemModel.isim.substring(0, 4))
        }catch (e:Exception){
            unvan.setText(itemModel.isim)
        }
        if (itemModel.odeme.isNotEmpty()) {
            var binler = itemModel.odeme.toFloat().toInt() / 1000
            var yuzler = itemModel.odeme.toFloat().toInt() % 1000
            var ondalik = (itemModel.odeme.toFloat().toInt() * 100).toInt() % 100
            if (binler == 0) {
                alacak.setText(String.format("%.2f", itemModel.odeme.toFloat()).replace(".", ","))

            } else
                alacak.setText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik))
        }
        //  alacak.setText(itemModel.odeme)
        if (itemModel.tahsilat.isNotEmpty()) {
            var binler = itemModel.tahsilat.toFloat().toInt() / 1000
            var yuzler = itemModel.tahsilat.toFloat().toInt() % 1000
            var ondalik = (itemModel.tahsilat.toFloat().toInt() * 100).toInt() % 100
            if (binler == 0) {
                tahsilat.setText(
                    String.format("%.2f", itemModel.tahsilat.toFloat()).replace(".", ",")
                )

            } else
                tahsilat.setText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik))
        }
        //tahsilat.setText(itemModel.tahsilat)
        if (itemModel.bakiye.isNotEmpty()) {
            if(itemModel.bakiye.toFloat()>0) {
                var binler = itemModel.bakiye.toFloat().toInt() / 1000
                var yuzler = itemModel.bakiye.toFloat().toInt() % 1000
                var ondalik = (itemModel.bakiye.toFloat().toInt() * 100).toInt() % 100
                if (binler == 0) {
                    bakiye.setText(
                        String.format("%.2f", itemModel.bakiye.toFloat()).replace(".", ",")
                    )

                } else
                    bakiye.setText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik))
            }else{
                var binler = itemModel.bakiye.toFloat().toInt() / 1000
                var yuzler = itemModel.bakiye.toFloat().toInt() % 1000
                var ondalik = (itemModel.bakiye.toFloat().toInt() * 100).toInt() % 100
                if (binler == 0) {
                    bakiye.setText(
                        String.format("%.2f", itemModel.bakiye.toFloat()).replace(".", ",")
                    )

                } else
                    bakiye.setText(String.format("%,d.%03d,%02d", binler, yuzler*-1, ondalik))

            }
        }
        //bakiye.setText(itemModel.bakiye)

        //bakiye.setText((itemModel.alacak.toString().toFloat()-itemModel.borc.toString().toFloat()).toString())

    }


}