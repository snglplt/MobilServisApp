package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.FaturaModel
import com.selpar.pratikhasar.fragment.EvraklarFragment
import com.squareup.picasso.Picasso
import java.util.HashMap

class FaturaGVAdapter  (context: Context, courseModelArrayList: ArrayList<FaturaModel>) :
    ArrayAdapter<FaturaModel?>(context, 0, courseModelArrayList!! as List<FaturaModel?>) {
    lateinit var firma_id:String
    lateinit var kadi:String
    lateinit var toplam_tutar:String

    lateinit var resim: ImageView

    var bundlem= Bundle()

    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(com.selpar.pratikhasar.R.layout.card_item_bitmis_hali, parent, false)
        }
        val courseModel: FaturaModel? = getItem(position)
        val txtid = listitemView!!.findViewById<TextView>(com.selpar.pratikhasar.R.id.txtid)
        val courseTV = listitemView!!.findViewById<TextView>(com.selpar.pratikhasar.R.id.idTVCourse)
        val courseIV = listitemView.findViewById<ImageView>(com.selpar.pratikhasar.R.id.idIVcourse)
        courseTV.setText("Toplam Tutar "+courseModel!!.toplam_tutar)
        firma_id=courseModel.firma_id
        kadi=courseModel.kadi

        Picasso.get().load(courseModel.yol).into(courseIV)
        listitemView!!.setOnClickListener {

            val alertadd = AlertDialog.Builder(listitemView.context)
            val factory = LayoutInflater.from(listitemView.context)
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            //img.setImageBitmap(imgBitmap)
            Picasso.get().load(courseModel.yol).into(img)
            /*val pAttacher: PhotoViewAttacher
             pAttacher = PhotoViewAttacher(img)
             pAttacher.update()*/

            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 800
            params.height=800
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Tamam"
            ) { dialog, which -> dialog.dismiss() }


            alertadd.show()

        }
        //courseIV.setImageResource(courseModel.getImgid())
        return listitemView
    }

}
