package com.selpar.pratikhasar.adapter

import android.R
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
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
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.itextpdf.text.pdf.codec.Base64
import com.selpar.pratikhasar.data.ItemTespitModel
import com.selpar.pratikhasar.ui.MainActivity
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL


class TespitServis_View_Holder (itemView : View): RecyclerView.ViewHolder(itemView){
    lateinit var sil_resim:String
    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var checkBoxonay:CheckBox
    lateinit var btn_whatsapp:ImageView
    lateinit var tel:String
    lateinit var plaka:String
    lateinit var miktar:String
    lateinit var fiyat:String
    lateinit var image:ImageView
    @SuppressLint("RestrictedApi", "SuspiciousIndentation")
    fun bindItems(itemModel : ItemTespitModel) {
        image = itemView.findViewById(com.selpar.pratikhasar.R.id.img_r) as ImageView
        val txt_yol_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.image_path) as TextView
        checkBoxonay=itemView.findViewById(com.selpar.pratikhasar.R.id.checkBoxonay)
        btn_whatsapp=itemView.findViewById(com.selpar.pratikhasar.R.id.btn_whatsapp)
         txt_yol_servis_tespit.setText(itemModel.image)
        val txt_aciklama_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_aciklama) as TextView
        val txt_miktar_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_miktar) as TextView
        val txt_fiyat_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_fiyat) as TextView
        val txt_toplam_servis_tespit = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_toplam) as TextView
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
        checkBoxonay?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (checkBoxonay!!.isChecked) {
                    onApi2(txt_aciklama_servis_tespit.getText().toString(),"tespit resim",txt_miktar_servis_tespit.getText().toString(),txt_fiyat_servis_tespit.getText().toString(),"tespit resimleri")


                }
                //bilgi_getir(kadi,firma)                                }
                else{
                    // bilgi_getir_bakiye(kadi,firma)
                }

            }
        })

        bul_tel()

        btn_whatsapp.setOnClickListener {
            val bitmapDrawable=image.drawable as BitmapDrawable
            val bitmap=bitmapDrawable.bitmap
            val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val imageDetails = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "displayName")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val imageUri = itemView.context.contentResolver.insert(imageCollection, imageDetails)


                if (imageUri != null) {
                    itemView.context.contentResolver.openOutputStream(imageUri).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream?.flush()
                    }
            val bitmapPath=MediaStore.Images.Media.insertImage(itemView.context.contentResolver,bitmap,"",null)
            val bitmapUri= Uri.parse(imageUri.toString())
            val intent = Intent(Intent.ACTION_SEND)
            //val intent = Intent(Intent.ACTION_VIEW)

            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
            intent.setPackage("com.whatsapp")
            intent.putExtra(Intent.EXTRA_TEXT, plaka+" plakalı aracınız için aşağıda adet ve tutar belirtilen işlem yapılma isteniyor\n"+itemModel.miktar+"*"+
                    itemModel.fiyat +"\nToplam: "+(itemModel.miktar.toInt()*itemModel.fiyat.toFloat())
                    +"\n Onay veriyor musunuz?\n" +
                    "www.pratikhasar.com");
            val url = "https://api.whatsapp.com/send?phone=$tel"
          //  intent.setData(Uri.parse(url));
           // intent.setPackage("https://api.whatsapp.com/send?phone=$tel")
            try {
//itemView.getContext().startActivity(intent)
               // intent.data = Uri.parse(url)
                itemView.getContext().startActivity(intent)
            } catch (e: NameNotFoundException) {
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

/*
    fun saveImageInGallery(urlAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = downloadImageFromUrl(urlAddress)
            if (bitmap == null) {
                withContext(Dispatchers.Main) {
                    _imageSaveStatus.value = false
                }
            } else {
                val imageSaved = saveImageToDownloadFolder(bitmap) ?: false
                withContext(Dispatchers.Main) {
                    _imageSaveStatus.value = imageSaved
                }
            }
        }
    }
*/
    suspend fun downloadImageFromUrl(urlAddress: String): Bitmap? {
        return try {
            val url = URL(urlAddress)
            val inputStream: InputStream = url.openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveImageToDownloadFolder(ibitmap: Bitmap): Boolean {
        try {
            val filePath = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/profile.png"
            )
            val outputStream: OutputStream = FileOutputStream(filePath)
            ibitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            return true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
    }
    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }}
    private fun onarim_check(plaka: String) {
        val urlsb = "?kadi="+kadi+"&ozel_id="+ozel_id+"&plaka="+ plaka +"&miktar="+miktar+"&fiyat="+fiyat+"&tur=onarim_check"
        var url = "https://pratikhasar.com/netting/mobil.php"+urlsb
        Log.d("gonder",url)
        val queue: RequestQueue = Volley.newRequestQueue(itemView.context)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val json = response["plaka"] as JSONArray
                //println(outputObject["plaka"])
                val item = json.getJSONObject(0)

                if(item.getString("toplam").toString()!="null") {
                   // checkBoxonay.isChecked = true
                    checkBoxonay.visibility=GONE
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
//                    Toast.makeText(requireContext(), "Fail to get response", Toast.LENGTH_SHORT)
                //  .show()
            }
        )
        queue.add(request)
    }

    private fun bul_tel(){
        val urlsb = "?kadi="+kadi+"&ozel_id="+ozel_id+"&tur=aracbilgigetir_tel"
        var url = "https://pratikhasar.com/netting/mobil.php"+urlsb
        Log.d("gonder",url)
        val queue: RequestQueue = Volley.newRequestQueue(itemView.context)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val json = response["plaka"] as JSONArray
                //println(outputObject["plaka"])
                val item = json.getJSONObject(0)

                if(item.getString("telefon1").toString()!="null")
                    tel=item.getString("telefon1").toString()
                plaka=item.getString("plaka").toString()
                onarim_check(plaka)

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
//                    Toast.makeText(requireContext(), "Fail to get response", Toast.LENGTH_SHORT)
                //  .show()
            }
        )
        queue.add(request)
    }
    private fun onApi2(stokNo: String, stokAdi: String, miktar: String, fiyat: String, parcaturu: String) {
        val queue = Volley.newRequestQueue(itemView.context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
               // checkBoxonay.isChecked = true
                //onarimGetir()
                onarim_check(plaka)

                /* Toast.makeText(requireContext(),"kadi "+kadi,Toast.LENGTH_LONG).show()
                 Toast.makeText(requireContext(),"ozel_id "+ozel_id,Toast.LENGTH_LONG).show()
                // Toast.makeText(requireContext(),"sifre "+sifre,Toast.LENGTH_LONG).show()
                 Toast.makeText(requireContext(),"plaka "+plaka,Toast.LENGTH_LONG).show()*/
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["kadi"] = kadi
                params["ozel_id"] = ozel_id
                params["plaka"] = plaka
                params["stok_iscilik_adi"] = stokAdi
                params["stok_iscilik_no"] = stokNo
                params["miktar"] = miktar.toString()
                params["fiyat"] = fiyat.toString()
                params["toplam"]=(miktar.toInt()*fiyat.toFloat()).toString()
                params["parca_turu"]=parcaturu
                params["kdv"]="18"
                params["tur"] = "onarim_kaydet"
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
                //Toast.makeText(itemView.context,"Silme Başarılı: "+sil_resim, Toast.LENGTH_LONG).show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["yol"] = sil_resim
                params["tur"] = "servis_tespit_resim_sil"
                return params
            }
        }
        queue.add(postRequest)



    }


}
