package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.data.YapilacakModel

class YapilacakView_Holder  (itemView : View): RecyclerView.ViewHolder(itemView){

    lateinit var ses: TextView
    lateinit var aciklama: TextView
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: YapilacakModel) {
        ses = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_ses) as TextView
        aciklama = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_aciklama) as TextView
        aciklama.setText(itemModel.aciklama)

    }


}