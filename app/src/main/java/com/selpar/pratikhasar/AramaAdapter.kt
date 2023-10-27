package com.selpar.pratikhasar

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.data.AramaModel
import com.selpar.pratikhasar.data.news

class AramaAdapter(private val newsList: ArrayList<AramaModel>) :
    RecyclerView.Adapter<AramaAdapter.MyViewHolder>() {






    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(com.selpar.pratikhasar.R.layout.arama_kayit_getir,parent,false)


        return MyViewHolder(itemView)
    }

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]



        holder.tvplaka.text=currentItem.plaka
        holder.txt_gorusme.text=currentItem.gorusme
        holder.txt_aciklama.text=currentItem.aciklama
        holder.txt_ekbilgi.text=currentItem.ekbilgi
        holder.txt_tarih.text=currentItem.tarih


    }


    override fun getItemCount(): Int {
        return newsList.size
        //newsList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val tvplaka : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_plaka)
        val txt_gorusme : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_gorusme)
        val txt_aciklama : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_aciklama)
        val txt_ekbilgi : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_ekbilgi)
        val txt_tarih : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tarih)

    }
}
