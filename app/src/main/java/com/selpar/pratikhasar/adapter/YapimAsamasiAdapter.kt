package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ItemModel

class YapimAsamasiAdapter ( val itemList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<Yapim_Asamasi_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Yapim_Asamasi_List_View_Holder {
        return Yapim_Asamasi_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_yapim_asamasi_image_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Yapim_Asamasi_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}