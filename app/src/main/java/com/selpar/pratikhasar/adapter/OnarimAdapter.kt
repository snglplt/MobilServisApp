package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.OnarimModel

class OnarimAdapter( val itemList: ArrayList<OnarimModel>) :
    RecyclerView.Adapter<Onarim_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Onarim_List_View_Holder {
        return Onarim_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_onarim_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Onarim_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}