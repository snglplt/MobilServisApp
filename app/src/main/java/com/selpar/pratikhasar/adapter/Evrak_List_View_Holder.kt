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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.data.ItemModel
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.System.*
import java.util.*


class Evrak_List_View_Holder(itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var sil_resim:String
    lateinit var spinner1 :Spinner
    lateinit var evrak_turu :String
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var image:ImageView
    lateinit var txt_evrakturu:TextView
    lateinit var txt_yol:TextView
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    init {
        itemView.setOnClickListener {
            val alertadd = AlertDialog.Builder(itemView.context)
            val factory = LayoutInflater.from(itemView.context)
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.card_item_bitmis_hali, null)
            val img = view.findViewById<ImageView>(com.selpar.pratikhasar.R.id.idIVcourse)

            /*val pAttacher: PhotoViewAttacher
             pAttacher = PhotoViewAttacher(img)
             pAttacher.update()*/

            val params: ViewGroup.LayoutParams = img.getLayoutParams() as ViewGroup.LayoutParams
            params.width = 600
            params.height = 800
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
    }
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel: ItemModel) {
        image = itemView.findViewById(com.selpar.pratikhasar.R.id.idIVcourse) as ImageView
        txt_evrakturu = itemView.findViewById(com.selpar.pratikhasar.R.id.idTVCourse) as TextView
        txt_yol = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_yol) as TextView
       //val btn_sil = itemView.findViewById(com.selpar.pratikhasar.R.id.btn_sil_evrak) as Button
       //val btn_guncelle = itemView.findViewById(com.selpar.pratikhasar.R.id.btn_guncelle) as Button
        kadi = itemModel.kadi
        ozel_id = itemModel.ozel_id

        sil_resim=itemModel.image
        sil_resim=sil_resim.subSequence(32, sil_resim.length).toString()
        txt_evrakturu.setText(itemModel.txt_evrakturu)
        txt_yol.setText(itemModel.image.toString())
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
        var imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
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
            params.width = 600
            params.height=800
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }

       /* btn_guncelle.setOnClickListener {
            guncelle()

        }
        btn_sil.setOnClickListener {
            //Toast.makeText(itemView.context,"BUTON CLICK",Toast.LENGTH_LONG).show()
            var alertadd = AlertDialog.Builder(itemView.context)
            var factory = LayoutInflater.from(itemView.context)
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.resim_goster, null)
            val img=view.findViewById<PhotoView>(com.selpar.pratikhasar.R.id.imageView2)
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
                bundlem.putString("ozel_id",itemModel.ozel_id)
                bundlem.putString("kadi",itemModel.kadi)
                val fragobj = EvraklarFragment()
                fragobj.arguments=bundlem
               // fragobj.resimGetir(kadi,ozel_id)
                val fragmentmaneger= (getActivity(itemView.context) as FragmentActivity?)!!.supportFragmentManager.beginTransaction()
                    .replace(com.selpar.pratikhasar.R.id.frame2,  fragobj)
                    .commit()

            }
            alertadd.setNegativeButton("Hayır"){
                    dialog, which -> dialog.dismiss()
            }
            alertadd.show()

        }*/
    }



    fun guncelle(){

        val alertadd = AlertDialog.Builder(itemView.context)
        alertadd.setTitle("EVRAK TÜRÜ GÜNCELLENSİN Mİ?")
        val factory = LayoutInflater.from(itemView.context)
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.evrak_turu, null)

        spinner1=view.findViewById<Spinner>(com.selpar.pratikhasar.R.id.sp_evrak_turu)
        val alspinner1 = ArrayList<String>()
        val _spvalue1 = itemView.resources.getStringArray(com.selpar.pratikhasar.R.array.evrak)
        for (i in _spvalue1.indices) {
            alspinner1.add(_spvalue1[i])
        }
        val adapter1: Any? = ArrayAdapter<Any?>(
            view.getContext(),
            android.R.layout.simple_spinner_item,
            alspinner1 as List<Any?>
        )
        spinner1.setAdapter(adapter1 as SpinnerAdapter?)
        alertadd.setView(view)
        alertadd.setPositiveButton(
            "EVET"
        ) { dialogInterface, which ->
            evrak_turu=spinner1.selectedItem.toString()
            Toast.makeText(itemView.context,"evrak turu "+evrak_turu,Toast.LENGTH_LONG).show()
            guncelleme_yapti()


        }
        alertadd.setNegativeButton("Hayır"){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()

    }

    private fun guncelleme_yapti() {
        val queue = Volley.newRequestQueue(itemView.context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(itemView.context,"Güncelleme Başarılı: "+sil_resim,Toast.LENGTH_LONG).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["yol"] = sil_resim
                params["evrak_turu"] = evrak_turu
                params["tur"] = "evrak_resim_guncelle"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun evrakSil(){
        val queue = Volley.newRequestQueue(itemView.context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(itemView.context,"Silme Başarılı: "+sil_resim,Toast.LENGTH_LONG).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["yol"] = sil_resim
                params["tur"] = "evrak_resim_sil"
                return params
            }
        }
        queue.add(postRequest)


    }



}
