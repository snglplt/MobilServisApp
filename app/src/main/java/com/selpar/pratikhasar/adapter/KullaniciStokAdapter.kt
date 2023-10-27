package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ItemModel

class KullaniciStokAdapter (val itemList: ArrayList<KullaniciItem>) :
    RecyclerView.Adapter<Kullanici_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Kullanici_List_View_Holder {
        return Kullanici_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_kullanici_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Kullanici_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}
