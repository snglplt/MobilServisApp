package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.data.GecmiaAlimlarModel
import com.selpar.pratikhasar.data.StokItem

class Gecmis_Alimlar_List_View (itemView : View): RecyclerView.ViewHolder(itemView){

   lateinit var txt_servisturu:TextView
   lateinit var txt_servissuresi:TextView
   lateinit var txt_kontor:TextView
   lateinit var txt_fiyat:TextView
   lateinit var txt_bitis_tarihi:TextView
    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    fun bindItems(itemModel: GecmiaAlimlarModel) {
        txt_servisturu = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_servisturu) as TextView
        txt_servissuresi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_servissuresi) as TextView
        txt_kontor = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_kontor) as TextView
        txt_fiyat=itemView.findViewById(com.selpar.pratikhasar.R.id.txt_fiyat) as TextView
        txt_bitis_tarihi=itemView.findViewById(com.selpar.pratikhasar.R.id.txt_bitis_tarihi) as TextView

        txt_servisturu.setText(itemModel.servis_turu)
        txt_servissuresi.setText(itemModel.servis_suresi)
        txt_kontor.setText(itemModel.kontor)
        txt_bitis_tarihi.setText(itemModel.bitis_tarihi)
        val hesap=itemModel.fiyat.toString().split(".")
        val binler = hesap[0].toInt() / 1000
        val yuzler = hesap[0].toInt() % 1000
        val ondalik = (hesap[0].toInt() * 100).toInt() % 100
        if(binler==0){
            txt_fiyat.setText(itemModel.fiyat)

        }else{
            if(ondalik!=0){
                txt_fiyat.setText(binler.toString()+"."+yuzler+","+ondalik)

            }else{
                txt_fiyat.setText(binler.toString()+"."+yuzler)

            }
        }






        //bakiye.setText((itemModel.alacak.toString().toFloat()-itemModel.borc.toString().toFloat()).toString())

    }


}
