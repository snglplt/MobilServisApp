package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.ItemTespitModel
import com.squareup.picasso.Picasso
import java.io.File

class Gridview_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){

    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel : BitmisModel) {
        val courseTV = itemView.findViewById<TextView>(com.selpar.pratikhasar.R.id.idTVCourse)
        val courseIV = itemView.findViewById<ImageView>(com.selpar.pratikhasar.R.id.idIVcourse)
        courseTV.setText(itemModel!!.tur)
        Toast.makeText(itemView.context,"resim: "+itemModel.resim,Toast.LENGTH_LONG).show()
        Picasso.get().load(itemModel.resim).into(courseIV)


        //val spinner = itemView.findViewById<Spinner>(com.selpar.pratikhasar.R.id.spinner_evrak)
        var adapter = ArrayAdapter.createFromResource(
            itemView.getContext(),
            com.selpar.pratikhasar.R.array.evrak,
            R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_item)
        //   spinner.setAdapter(adapter)
        // on below line we are creating an image bitmap variable
        // and adding a bitmap to it from image file.

    }







}

