package com.selpar.pratikhasar.fragment

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.GonderilmisFaturaModel
import com.selpar.pratikhasar.adapter.Gonderilmis_Fatura_List_Holder
import com.selpar.pratikhasar.adapter.Reddedilen_Fatura_List_Holder

class ReddedilenFaturaAdapter ( val itemList: ArrayList<GonderilmisFaturaModel>) :
    RecyclerView.Adapter<Reddedilen_Fatura_List_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Reddedilen_Fatura_List_Holder {
        return Reddedilen_Fatura_List_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_reddedilen_fatura,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Reddedilen_Fatura_List_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}