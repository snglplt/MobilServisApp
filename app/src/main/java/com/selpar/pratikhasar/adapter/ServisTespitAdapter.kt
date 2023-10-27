package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ItemModel

class ServisTespitAdapter( val itemList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<Servis_Tespit_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Servis_Tespit_View_Holder {
        return Servis_Tespit_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_servis_image_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Servis_Tespit_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}