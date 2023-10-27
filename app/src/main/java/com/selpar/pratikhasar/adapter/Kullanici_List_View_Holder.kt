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
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.data.ItemTespitModel
import com.squareup.picasso.Picasso
import java.io.File

class Kullanici_List_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var checkBoxonay: CheckBox
    lateinit var btn_ara: ImageView
    lateinit var tel:String
    lateinit var plaka:String
    lateinit var miktar:String
    lateinit var fiyat:String
    lateinit var image: ImageView
    lateinit var txtid: TextView
    lateinit var txtbrans: TextView
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel : KullaniciItem) {
        image = itemView.findViewById(com.selpar.pratikhasar.R.id.img_r) as ImageView
        txtid = itemView.findViewById(com.selpar.pratikhasar.R.id.txtid) as TextView
        txtbrans = itemView.findViewById(com.selpar.pratikhasar.R.id.txtbrans) as TextView
        btn_ara = itemView.findViewById(com.selpar.pratikhasar.R.id.btn_ara) as ImageView
        val txt_yol_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.image_path) as TextView
        txt_yol_servis_tespit.setText(itemModel.image)
        val txtad_soyad = itemView.findViewById(com.selpar.pratikhasar.R.id.txtad_soyad) as TextView
        val txt_tel = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_tel) as TextView
        val txt_mail = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_mail) as TextView
        txtid.setText(itemModel.id)
        txtad_soyad.setText(itemModel.ad +"  "+ itemModel.soyad)
        txt_tel.setText(itemModel.tel)
        tel=itemModel.tel
        txtbrans.setText("  "+itemModel.brans +" " + itemView.resources.getString(com.selpar.pratikhasar.R.string.ustasi))
        txt_mail.setText("  "+itemModel.mail)
        Picasso.get().load(itemModel.image).into(image)
        val imgFile = File(itemModel.image)
        val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        val img: Int = com.selpar.pratikhasar.R.layout.resim_goster
        var filterPopup: PopupWindow? = null
        image.setImageBitmap(imgBitmap)
        Picasso.get().load(itemModel.image).into(image)
        btn_ara.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + tel)
            itemView.getContext().startActivity(dialIntent)
        }




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
            params.width = 1000
            params.height=1000
            img.setLayoutParams(params)
            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
    }}

