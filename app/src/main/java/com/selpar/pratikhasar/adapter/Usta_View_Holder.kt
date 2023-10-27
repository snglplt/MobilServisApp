package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ustalar

class Usta_View_Holder  (itemView : View): RecyclerView.ViewHolder(itemView){

    lateinit var id: TextView
    lateinit var ad: TextView
    lateinit var soyad: TextView
    lateinit var brans: TextView
    lateinit var tel: TextView
    lateinit var btn_ara:ImageView

    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    fun bindItems(itemModel: ustalar) {
        id = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_id) as TextView
        ad = itemView.findViewById(com.selpar.pratikhasar.R.id.txtad) as TextView
        soyad = itemView.findViewById(com.selpar.pratikhasar.R.id.txtsoyad) as TextView
        brans = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_brans) as TextView
        tel = itemView.findViewById(com.selpar.pratikhasar.R.id.txttel) as TextView
        btn_ara = itemView.findViewById(com.selpar.pratikhasar.R.id.btn_ara) as ImageView
        id.setText(itemModel.id)
        ad.setText(itemModel.ad)
        soyad.setText(itemModel.soyad)
        brans.setText(itemModel.brans+ " "+itemView.context.getString(R.string.ustasi))
        tel.setText(itemModel.tel)
        btn_ara.setOnClickListener {

                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + tel.getText().toString())
                itemView.context.startActivity(dialIntent)

        }


        //bakiye.setText((itemModel.alacak.toString().toFloat()-itemModel.borc.toString().toFloat()).toString())

    }


}