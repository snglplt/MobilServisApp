package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.internal.ContextUtils
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.resim
import com.selpar.pratikhasar.fragment.BitmisHaliFragment
import com.selpar.pratikhasar.fragment.OlayYeriFragment
import com.squareup.picasso.Picasso
import java.io.File


class ItemListViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var sil_resim:String

    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String

    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel : ItemModel) {
        val image = itemView.findViewById(com.selpar.pratikhasar.R.id.img_r) as ImageView
        val txt_bitmis_hali = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_bitmis_hali) as TextView
        sil_resim=itemModel.image
        txt_bitmis_hali.setText(itemModel.image)
        kadi = itemModel.kadi
        ozel_id = itemModel.ozel_id
        sil_resim=sil_resim.subSequence(32, sil_resim.length).toString()
        Picasso.get().load(itemModel.image).into(image)
        val imgFile = File(itemModel.image)
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
        val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        val img: Int = com.selpar.pratikhasar.R.layout.resim_goster
        var filterPopup: PopupWindow? = null
        image.setImageBitmap(imgBitmap)
        Picasso.get().load(itemModel.image).into(image)
        itemView.setOnClickListener {
            val alertadd = AlertDialog.Builder(itemView.context)
            val factory = LayoutInflater.from(itemView.context)
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            img.setImageBitmap(imgBitmap)
            Picasso.get().load(itemModel.image).into(img)
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
    }
    private fun evrakSil(){
        val queue = Volley.newRequestQueue(itemView.context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(itemView.context,"Silme Başarılı: "+sil_resim, Toast.LENGTH_LONG).show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["yol"] = sil_resim
                params["tur"] = "bitmis_hali_resim_sil"
                return params
            }
        }
        queue.add(postRequest)



    }




}