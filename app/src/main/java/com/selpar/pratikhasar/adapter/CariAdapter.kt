package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.CariModel

class CariAdapter ( val itemList: ArrayList<CariModel>) :
    RecyclerView.Adapter<Cari_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cari_List_View_Holder {
        return Cari_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tum_hesaplar,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Cari_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}