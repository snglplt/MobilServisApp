package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.ItemTespitModel


class TespitResimAdapter( val itemList: ArrayList<ItemTespitModel>) :
    RecyclerView.Adapter<TespitServis_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TespitServis_View_Holder {
        return TespitServis_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_servis_tespit,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TespitServis_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}
