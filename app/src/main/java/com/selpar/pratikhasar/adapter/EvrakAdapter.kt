package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ItemModel

class EvrakAdapter( val itemList: ArrayList<ItemModel>) :
RecyclerView.Adapter<Evrak_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Evrak_List_View_Holder {
        return Evrak_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_item_bitmis_hali,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Evrak_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}