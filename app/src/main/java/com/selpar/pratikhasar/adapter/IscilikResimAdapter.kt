package com.selpar.pratikhasar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.ItemTespitModel
import java.util.ArrayList

class IscilikResimAdapter(val itemList: ArrayList<ItemTespitModel>) :
    RecyclerView.Adapter<İscilik_Resim_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): İscilik_Resim_View_Holder {
        return İscilik_Resim_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_iscilik_image_text,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: İscilik_Resim_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}