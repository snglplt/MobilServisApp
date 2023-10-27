package com.selpar.pratikhasar.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R

class GonderilmisFaturaAdapter ( val itemList: ArrayList<GonderilmisFaturaModel>) :
    RecyclerView.Adapter<Gonderilmis_Fatura_List_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Gonderilmis_Fatura_List_Holder {
        return Gonderilmis_Fatura_List_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_gonderilmis_faturalar,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Gonderilmis_Fatura_List_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}
