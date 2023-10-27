package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.AramaAdapter
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.AramaModel
import com.selpar.pratikhasar.data.news
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.HashMap

class TarfikSigortasiTarihiAdapter (private val newsList : ArrayList<news>) :
    RecyclerView.Adapter<TarfikSigortasiTarihiAdapter.MyViewHolder>() {
    lateinit var rc_yapilan_aramalar:RecyclerView
    private lateinit var newArrayList: java.util.ArrayList<AramaModel>
    var tarih= ArrayList<String>()
    var aciklama= ArrayList<String>()
    var gorusme= ArrayList<String>()
    var ekbilgi= ArrayList<String>()
    var firma_id= ArrayList<String>()
    var plaka2= ArrayList<String>()





    var sayac:Int=0
    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(com.selpar.pratikhasar.R.layout.zamani_gelenler,parent,false)


        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]



        val bul_tarih = currentItem.mua.split("-")
        holder.tvonarim.text=bul_tarih[2]+"-"+bul_tarih[1]+"-"+bul_tarih[0]
        holder.tvplaka.text=currentItem.plaka
        holder.txtisim.text=currentItem.unvan
        val takvim = Calendar.getInstance();
        val yil: Int = takvim.get(Calendar.YEAR)
        val ay: Int = takvim.get(Calendar.MONTH) + 1
        val gun: Int = takvim.get(Calendar.DAY_OF_MONTH)
        var mua = currentItem.mua


        var tire: Int = 0
        for (i in 1 until mua.length) {
            if (mua.substring(i - 1, i) == "-") {
                tire = 1
            } else {

            }
        }
        if (tire == 1) {
            val parts = mua.split("-")
            Log.d("TAG", parts[1])
            val endDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                daysBetween(startDate,endDate)
            } else {
            }
            val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            if(daysBetween.toString().toInt()<=0){
                holder.txt_kalansure.setText((-daysBetween.toString().toInt()).toString()+" Gün Geçmiştir")

            }
            else{
                holder.txt_kalansure.setText(daysBetween.toString()+" Gün Kalmıştır")

            }
        }
        else {
            /*val parts = mua.split("/")
            Log.d("TAG", parts[0])

            val endDate = LocalDate.of(parts[2].toInt(), parts[1].toInt(), parts[0].toInt())
            val  startDate= LocalDate.of(yil, ay, gun)

            val daysBetween = daysBetween(endDate,startDate)

            //val sonuc=365-daysBetween.toString().toInt()
            // mua_bitis_tarihi_kalan.setText((simdikitarih.toInt()-mua.toInt()).toString())
            holder.txt_kalansure.setText(daysBetween.toString()+" Gün Kalmıştır")
        */}
        //holder.tvresim.setImageIcon(currentItem.resim)
        try {
            Picasso.get().load(currentItem.resim).into(holder.tvresim)
        }
        catch (e:Exception){
            //holder.tvresim.
            Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.tvresim);
        }
        tire=0
        // sayac=holder.itemView.scrollBarSize
        // Picasso.get().load(currentItem.resim).into(holder.tvresim)
        // Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.tvresim);

        holder.image_view_tel.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + currentItem.tel)
            holder.itemView.context.startActivity(dialIntent)
            onAlert(holder.itemView.context,currentItem.firma_id,currentItem.plaka,currentItem.tel)

        }
        holder.image_view_whatsapp.setOnClickListener {
            val bitmapDrawable=holder.tvresim.drawable as BitmapDrawable
            val bitmap=bitmapDrawable.bitmap
            val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val imageDetails = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "displayName")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val imageUri = holder.itemView.context.contentResolver.insert(imageCollection, imageDetails)


            if (imageUri != null) {
                holder.itemView.context.contentResolver.openOutputStream(imageUri).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream?.flush()
                }
                val bitmapPath=MediaStore.Images.Media.insertImage(holder.itemView.context.contentResolver,bitmap,"",null)
                val bitmapUri= Uri.parse(imageUri.toString())
                val intent = Intent(Intent.ACTION_SEND)
                //val intent = Intent(Intent.ACTION_VIEW)

                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
                intent.setPackage("com.whatsapp")
                intent.putExtra(Intent.EXTRA_TEXT, currentItem.plaka+" plakalı aracınızın trafik sigortasının bitmesine \n"+holder.txt_kalansure.getText().toString()+

                       // "\n Muayene için bizi arayınız 0850 304 26 06\n" +
                        "\nwww.pratikhasar.com")
                //  intent.setData(Uri.parse(url));
                // intent.setPackage("https://api.whatsapp.com/send?phone=$tel")
                try {
//itemView.getContext().startActivity(intent)
                    // intent.data = Uri.parse(url)
                    holder.itemView.getContext().startActivity(intent)
                } catch (e: PackageManager.NameNotFoundException) {
                    Toast.makeText(
                        holder.itemView.context,
                        "Whatsapp app not installed in your phone",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }

            }}
        holder.image_view_soru.setOnClickListener {
            val alertadd = AlertDialog.Builder(holder.itemView.context)
            alertadd.setTitle(holder.itemView.context.getString(R.string.gorusmesonuc))
            val factory = LayoutInflater.from(holder.itemView.context)
            val view: View = factory.inflate(R.layout.yapilan_aramalar, null)

            rc_yapilan_aramalar=view.findViewById<RecyclerView>(R.id.rc_yapilan_aramalar)
            kayitGetir(holder.itemView.context,currentItem.firma_id,currentItem.plaka)

            alertadd.setView(view)
            alertadd.setPositiveButton(
                "TAMAM"
            ) { dialogInterface, which ->
                //kayitGetir(holder.itemView.context,currentItem.firma_id,currentItem.plaka)

            }
            alertadd.show()

        }


    }
    private fun kayitGetir(context: Context, firmaId: String, plaka: String) {
        firma_id.clear()
        plaka2.clear()
        tarih.clear()
        aciklama.clear()
        gorusme.clear()
        ekbilgi.clear()
        val urlsb ="&firma_id="+firmaId+"&plaka="+plaka
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_kayit_bul_trafik"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["aramalar"] as JSONArray

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        firma_id.add(item.getString("firma_id"))
                        plaka2.add(item.getString("plaka"))
                        tarih.add(item.getString("tarih"))
                        aciklama.add(item.getString("aciklama"))
                        gorusme.add(item.getString("gorusme"))
                        ekbilgi.add(item.getString("ekbilgi"))
                    }


                    rc_yapilan_aramalar.layoutManager= LinearLayoutManager(context)
                    rc_yapilan_aramalar.setHasFixedSize(false)
                    newArrayList= arrayListOf<AramaModel>()
                    getUserData(context)
                }catch (e:Exception){}





            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
            }
        )
        queue.add(request)


    }
    private fun getUserData(context: Context) {
        for(i in aciklama.indices){
            val news = AramaModel(firma_id[i],plaka2[i],tarih[i],aciklama[i],gorusme[i],ekbilgi[i])

            newArrayList.add(news)
        }
        rc_yapilan_aramalar.adapter= AramaAdapter(newArrayList)
        aciklama.clear()
    }

    @SuppressLint("MissingInflatedId")
    private fun onAlert(context: Context, firmaId: String, plaka:String, tel:String) {
        val alertadd = AlertDialog.Builder(context)
        alertadd.setTitle("Arama Sonucu")
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.arama_detay, null)

        val spinner1=view.findViewById<Spinner>(R.id.spinner_gorusme)
        val txtaciklama=view.findViewById<EditText>(R.id.txtaciklama)
        val txteknot=view.findViewById<EditText>(R.id.txteknot)
        val alspinner1 = java.util.ArrayList<String>()
        val _spvalue1 = context.resources.getStringArray(R.array.gorusme)
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
            "EKLE"
        ) { dialogInterface, which ->
            onApi(context,firmaId,plaka,tel,spinner1.selectedItem.toString(),txtaciklama.getText().toString(),txteknot.getText().toString())

        }
        alertadd.show()
    }
    private fun onApi(context: Context, firma_id: String, plaka:String, tel:String, gorusme: String, aciklama: String, eknot:String) {
        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["firma_id"] = firma_id
                params["gorusme"] = gorusme
                params["aciklama"]=aciklama
                params["plaka"] = plaka
                params["aranan"] = tel
                params["gorusmesonucu"] = eknot
                params["tur"] = "arama_detay_kaydet_trafik"
                return params
            }
        }
        queue.add(postRequest)

    }

    override fun getItemCount(): Int {
        return newsList.size
        //newsList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun daysBetween(startDate: LocalDate, endDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(startDate, endDate)
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val tvplaka : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvplaka)
        val tvresim : ImageView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvaracresim)
        val tvonarim : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvonarim)
        val txt_kalansure : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_kalansure)
        val txtisim : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txtisim)
        val image_view_tel : ImageView = itemView.findViewById(com.selpar.pratikhasar.R.id.image_view_tel)
        val image_view_whatsapp : ImageView = itemView.findViewById(com.selpar.pratikhasar.R.id.image_view_whatsapp)
        val image_view_soru : ImageView = itemView.findViewById(com.selpar.pratikhasar.R.id.image_view_soru)

    }
}
