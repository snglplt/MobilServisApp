package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.StokItem

class TextAdapter ( val itemList: ArrayList<TextModel>) :
    RecyclerView.Adapter<Text_List_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Text_List_View_Holder {
        return Text_List_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_text_istek_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Text_List_View_Holder, position: Int) {
        holder.bindItems(itemList[position])


    }
}
