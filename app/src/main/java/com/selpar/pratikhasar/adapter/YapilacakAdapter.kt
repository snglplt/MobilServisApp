package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.YapilacakModel

class YapilacakAdapter ( val itemList: ArrayList<YapilacakModel>) :
    RecyclerView.Adapter<YapilacakView_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YapilacakView_Holder {
        return YapilacakView_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_yapilacaklar,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: YapilacakView_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}