package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.data.SesModel
import com.squareup.picasso.Picasso
import java.io.File
import java.util.ArrayList
import java.util.HashMap

class Ses_List_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){

    lateinit var ses: TextView
    lateinit var aciklama: TextView
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var btn_play:ImageView
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: SesModel) {
        ses = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_ses) as TextView
        aciklama = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_aciklama) as TextView
        btn_play = itemView.findViewById(com.selpar.pratikhasar.R.id.btn_play) as ImageView
        ses.setText(itemModel.ses)
        aciklama.setText(itemModel.aciklama)
        btn_play.setOnClickListener {
            var mp= MediaPlayer()
            mp.setDataSource(itemModel.yol)
          //  mp.setDataSource(itemModel.ses)
            mp.prepare()
            mp.start()
        }

        }


    }







