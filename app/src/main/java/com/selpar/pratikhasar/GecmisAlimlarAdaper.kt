package com.selpar.pratikhasar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.adapter.Gecmis_Alimlar_List_View
import com.selpar.pratikhasar.adapter.Stok_List_View_Holder
import com.selpar.pratikhasar.data.GecmiaAlimlarModel
import com.selpar.pratikhasar.data.StokItem

class GecmisAlimlarAdaper( val itemList: ArrayList<GecmiaAlimlarModel>) :
    RecyclerView.Adapter<Gecmis_Alimlar_List_View>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Gecmis_Alimlar_List_View {
        return Gecmis_Alimlar_List_View(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_gecmis_alimlar,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Gecmis_Alimlar_List_View, position: Int) {
        holder.bindItems(itemList[position])


    }
}
