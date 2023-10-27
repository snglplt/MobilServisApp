package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.itextpdf.text.pdf.codec.Base64
import com.selpar.pratikhasar.data.ItemTespitModel
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

class İscilik_Resim_View_Holder  (itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var sil_resim:String
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var checkBoxonay: CheckBox
    lateinit var btn_whatsapp: ImageView
    lateinit var tel:String
    lateinit var plaka:String
    lateinit var miktar:String
    lateinit var fiyat:String
    lateinit var image: ImageView
    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel : ItemTespitModel) {
        image = itemView.findViewById(com.selpar.pratikhasar.R.id.img_r) as ImageView
        val txt_yol_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.image_path) as TextView
        txt_yol_servis_tespit.setText(itemModel.image)
        val txt_aciklama_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_aciklama) as TextView
        val txt_miktar_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_miktar) as TextView
        val txt_fiyat_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_fiyat) as TextView
        val txt_toplam_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_toplam) as TextView
        btn_whatsapp= itemView.findViewById(com.selpar.pratikhasar.R.id.btn_whatsapp) as ImageView
        txt_aciklama_servis_tespit.setText(itemModel.txt_evrakturu)
        txt_miktar_servis_tespit.setText(itemModel.miktar)
        txt_fiyat_servis_tespit.setText(itemModel.fiyat)
        txt_toplam_servis_tespit.setText((itemModel.miktar.toInt()*itemModel.fiyat.toFloat()).toString())
        sil_resim=itemModel.image
        //   txt_yol_servis_tespit.setText(itemModel.image)
        kadi = itemModel.kadi
        ozel_id = itemModel.ozel_id
        miktar=itemModel.miktar
        fiyat=itemModel.fiyat
        Picasso.get().load(itemModel.image).into(image)
        val imgFile = File(itemModel.image)
        val imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        val img: Int = com.selpar.pratikhasar.R.layout.resim_goster
        var filterPopup: PopupWindow? = null
        image.setImageBitmap(imgBitmap)
        Picasso.get().load(itemModel.image).into(image)

        btn_whatsapp.setOnClickListener {
            val bitmapDrawable=image.drawable as BitmapDrawable
            val bitmap=bitmapDrawable.bitmap
            val bitmapPath=MediaStore.Images.Media.insertImage(itemView.context.contentResolver,bitmap,"",null)
            val bitmapUri= Uri.parse(bitmapPath)
            val intent = Intent(Intent.ACTION_SEND)
            //val intent = Intent(Intent.ACTION_VIEW)

            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            intent.setPackage("com.whatsapp")
            intent.putExtra(Intent.EXTRA_TEXT, "" +
                    "aracınız için aşağıda adet ve tutar belirtilen işlem yapılma isteniyor\n"+itemModel.miktar+"*"+
                    itemModel.fiyat +"\nToplam: "+(itemModel.miktar.toInt()*itemModel.fiyat.toFloat())
                    +"\n Onay veriyor musunuz?\n" +
                    "www.pratikhasar.com");
            //val url = "https://api.whatsapp.com/send?phone=$tel"
            //  intent.setData(Uri.parse(url));
            // intent.setPackage("https://api.whatsapp.com/send?phone=$tel")
            try {
//itemView.getContext().startActivity(intent)
                // intent.data = Uri.parse(url)
                itemView.getContext().startActivity(intent)
            } catch (e: PackageManager.NameNotFoundException) {
                Toast.makeText(
                    itemView.context,
                    "Whatsapp app not installed in your phone",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }

            /*
            try {
                //resim indiriliyor
                val url = URL("http://www.sahatek.com/sahatek.jpg")
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                //sdcarda kaydedileceğini belirtiyoruz.
                val f = File(itemView.context.getExternalCacheDir(), "Sahatek")
                //Eğer dosya yoksa oluştur
                if (!f.exists()) {
                    f.mkdirs()
                }
                val sdCardDirectory: File = itemView.context.getExternalCacheDir()!!
                val image = File(sdCardDirectory, "Sahatek/sahatek.jpg")
                // Png resim olduğunu belirtiyoruz
                val outStream: FileOutputStream
                outStream = FileOutputStream(image)
                bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream)
                outStream.flush()
                outStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

*/




        }


        sil_resim=sil_resim.subSequence(32, sil_resim.length).toString()

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
    }







}

