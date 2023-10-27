package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R

class GidenFaturaAdapter( val itemList: ArrayList<GidenFaturaModel>) :
    RecyclerView.Adapter<Giden_Fatura_List_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Giden_Fatura_List_Holder {
        return Giden_Fatura_List_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_giden_fatura_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Giden_Fatura_List_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}
