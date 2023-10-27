package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.CariModel
import com.selpar.pratikhasar.data.StokItem

class StokAdapter( val itemList: ArrayList<StokItem>) :
    RecyclerView.Adapter<Stok_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Stok_List_View_Holder {
        return Stok_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_stok_tanimlama,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Stok_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}
