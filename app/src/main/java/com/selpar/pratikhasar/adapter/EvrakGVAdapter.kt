package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.fragment.EvraklarFragment
import com.squareup.picasso.Picasso
import java.util.HashMap

class EvrakGVAdapter (context: Context, courseModelArrayList: ArrayList<BitmisModel>) :
    ArrayAdapter<BitmisModel?>(context, 0, courseModelArrayList!! as List<BitmisModel?>) {
    lateinit var firma_id:String
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var plaka:String
    lateinit var spinner1:Spinner
    lateinit var evrak_turu:String
    var bundlem= Bundle()

    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(com.selpar.pratikhasar.R.layout.card_item_bitmis_hali, parent, false)
        }
        val courseModel: BitmisModel? = getItem(position)
        val txtid = listitemView!!.findViewById<TextView>(com.selpar.pratikhasar.R.id.txtid)
        val courseTV = listitemView!!.findViewById<TextView>(com.selpar.pratikhasar.R.id.idTVCourse)
        val courseIV = listitemView.findViewById<ImageView>(com.selpar.pratikhasar.R.id.idIVcourse)
        courseTV.setText(courseModel!!.tur)
        txtid.setText(courseModel!!.id)
        firma_id=courseModel.firma_id
        kadi=courseModel.kadi
        ozel_id=courseModel.ozel_id
        plaka=courseModel.plaka
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
            alertadd.setNegativeButton("sil"){
                    dialog, which -> dialog.dismiss()
                bitmis_sil(txtid.getText().toString(),courseModel.resim)
            }
            alertadd.setNeutralButton("Guncelle"){
                    dialog, which -> dialog.dismiss()
                evrak_guncelle(txtid.getText().toString(),courseModel.resim)
            }

            alertadd.show()

        }
        //courseIV.setImageResource(courseModel.getImgid())
        return listitemView
    }

    private fun evrak_guncelle(id: String, resim: String) {
        val alertadd = AlertDialog.Builder(context)
        alertadd.setTitle("EVRAK TÜRÜ GÜNCELLENSİN Mİ?")
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.evrak_turu, null)

        spinner1=view.findViewById<Spinner>(com.selpar.pratikhasar.R.id.sp_evrak_turu)
        val alspinner1 = java.util.ArrayList<String>()
        val _spvalue1 = context.resources.getStringArray(com.selpar.pratikhasar.R.array.evrak)
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
            // Toast.makeText(requireContext(),"evrak turu "+evrak_turu,Toast.LENGTH_LONG).show()
            guncelleme_yapti(id,evrak_turu,resim)


        }
        alertadd.setNegativeButton("Hayır"){
                dialog, which -> dialog.dismiss()
        }
        alertadd.show()
    }
    private fun guncelleme_yapti(id:String, evraktur: String,resim:String) {
        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                bundlem.putString("ozel_id",ozel_id)
                bundlem.putString("kadi",kadi)
                bundlem.putString("plaka",plaka)
                bundlem.putString("firma_id",firma_id)
                val fragobj = EvraklarFragment()
                fragobj.arguments=bundlem
                // fragobj.resimGetir(kadi,ozel_id)
                val fragmentmaneger= (context as FragmentActivity?)!!.supportFragmentManager.beginTransaction()
                    .replace(com.selpar.pratikhasar.R.id.frame2,  fragobj)
                    .commit()

                Toast.makeText(context,"Güncelleme Başarılı: "+kadi,Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id"] = id
                params["yol"] = resim
                params["evrak_turu"] = evrak_turu
                params["kadi"] = kadi
                params["evrak_turu"]=evrak_turu
                params["ozel_id"] = ozel_id
                params["firma_id"]=firma_id
                params["plaka"]=plaka
                params["olay"]="Evrak Resim Bilgisi"
                params["tur"] = "evrak_resim_guncelle"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun bitmis_sil(id: String, resim: String) {
        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                //resimGetir(kadi,firma)
                bundlem.putString("ozel_id",ozel_id)
                bundlem.putString("kadi",kadi)
                bundlem.putString("plaka",plaka)
                bundlem.putString("firma_id",firma_id)
                val fragobj = EvraklarFragment()
                fragobj.arguments=bundlem
                // fragobj.resimGetir(kadi,ozel_id)
                val fragmentmaneger= (context as FragmentActivity?)!!.supportFragmentManager.beginTransaction()
                    .replace(com.selpar.pratikhasar.R.id.frame2,  fragobj)
                    .commit()
                Toast.makeText(context,"Silme Başarılı: "+kadi, Toast.LENGTH_SHORT).show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id"]=id
                params["kadi"] = kadi
                params["firma_id"] = firma_id
                params["ozel_id"] = ozel_id
                params["plaka"]=plaka
                params["olay"]="Evrak Resim Bilgisi"
                params["yol"] = resim
                params["tur"] = "evrak_resim_sil"
                return params
            }
        }
        queue.add(postRequest)

    }
}
