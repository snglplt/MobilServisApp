package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.StokListModel
import com.selpar.pratikhasar.data.StokItem

class StokListAdapter ( val itemList: ArrayList<StokListModel>) :
    RecyclerView.Adapter<StokListAdapter_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StokListAdapter_List_View_Holder {
        return StokListAdapter_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_stok_liste_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: StokListAdapter_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}
