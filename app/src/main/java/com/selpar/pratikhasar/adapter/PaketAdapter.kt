package com.selpar.pratikhasar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import com.selpar.pratikhasar.data.BitmisModel
import com.squareup.picasso.Picasso

class PaketAdapter (context: Context, courseModelArrayList: ArrayList<BitmisModel>) :
    ArrayAdapter<BitmisModel?>(context, 0, courseModelArrayList!! as List<BitmisModel?>) {
    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(com.selpar.pratikhasar.R.layout.card_paket_item, parent, false)
        }
        val courseModel: BitmisModel? = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(com.selpar.pratikhasar.R.id.idTVCourse)
        val courseIV = listitemView.findViewById<ImageView>(com.selpar.pratikhasar.R.id.idIVcourse)
        courseTV.setText(courseModel!!.tur)
        Toast.makeText(listitemView.context,"resim: "+courseModel.resim,Toast.LENGTH_LONG).show()
        Picasso.get().load(courseModel.resim).into(courseIV)
        listitemView!!.setOnClickListener {
            val alertadd = AlertDialog.Builder(listitemView.context)
            val factory = LayoutInflater.from(listitemView.context)
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            //img.setImageBitmap(imgBitmap)
            Picasso.get().load(courseModel.resim).into(img)
            /*val pAttacher: PhotoViewAttacher
             pAttacher = PhotoViewAttacher(img)
             pAttacher.update()*/

            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 800
            params.height=800
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()

        }
        //courseIV.setImageResource(courseModel.getImgid())
        return listitemView
    }
}
