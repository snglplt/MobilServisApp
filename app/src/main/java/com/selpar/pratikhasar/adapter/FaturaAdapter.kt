package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R

class FaturaAdapter ( val itemList: ArrayList<FaturaModel>) :
    RecyclerView.Adapter<Fatura_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Fatura_List_View_Holder {
        return Fatura_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_fatura_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Fatura_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}