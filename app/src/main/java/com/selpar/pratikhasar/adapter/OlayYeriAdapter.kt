package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ItemModel

class OlayYeriAdapter ( val itemList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<Olay_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Olay_List_View_Holder {
        return Olay_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_olay_yeri_image_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Olay_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}