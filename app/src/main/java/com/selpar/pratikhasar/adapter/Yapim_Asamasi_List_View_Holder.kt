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
import com.selpar.pratikhasar.fragment.EvraklarFragment
import com.selpar.pratikhasar.fragment.YapimAsamasiFragment
import com.squareup.picasso.Picasso
import java.io.File

class Yapim_Asamasi_List_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var sil_resim:String
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel : ItemModel) {
        val image = itemView.findViewById(com.selpar.pratikhasar.R.id.img_yapim_asamasi) as ImageView
        val txt_yol_yapim_asamasi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_yol_yapim_asamasi) as TextView
        sil_resim=itemModel.image
        txt_yol_yapim_asamasi.setText(itemModel.image)
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
       /* btn_sil.setOnClickListener {
            //Toast.makeText(itemView.context,"BUTON CLICK",Toast.LENGTH_LONG).show()
            var alertadd = AlertDialog.Builder(itemView.context)
            var factory = LayoutInflater.from(itemView.context)
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.imageView2)
            img.setImageBitmap(imgBitmap)
            Picasso.get().load(itemModel.image).into(img)
            /*val pAttacher: PhotoViewAttacher
             pAttacher = PhotoViewAttacher(img)
             pAttacher.update()*/

            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 500
            params.height=500
            alertadd.setTitle("Resim Silinsin mi?")
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Evet"
            ) { dialog, which -> evrakSil()
                val fragobj = YapimAsamasiFragment()
                bundlem.putString("ozel_id",itemModel.ozel_id)
                bundlem.putString("kadi",itemModel.kadi)
                fragobj.arguments=bundlem
                // fragobj.resimGetir(kadi,ozel_id)
                val fragmentmaneger= (ContextUtils.getActivity(itemView.context) as FragmentActivity?)!!.supportFragmentManager.beginTransaction()
                    .replace(com.selpar.pratikhasar.R.id.frame2,  fragobj)
                    .commit()
            }
            alertadd.setNegativeButton("Hayır"){
                    dialog, which -> dialog.dismiss()
            }
            alertadd.show()
        }*/
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
                params["tur"] = "yapim_asamasi_resim_sil"
                return params
            }
        }
        queue.add(postRequest)



    }


}