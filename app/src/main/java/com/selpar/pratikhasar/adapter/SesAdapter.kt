package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.SesModel

class SesAdapter( val itemList: ArrayList<SesModel>) :
    RecyclerView.Adapter<Ses_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Ses_List_View_Holder {
        return Ses_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_ses_goster,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Ses_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}