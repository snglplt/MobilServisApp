package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils
import com.selpar.pratikhasar.data.OnarimModel
import com.selpar.pratikhasar.fragment.OnarimFragment
import com.selpar.pratikhasar.ui.eFaturaOlusturActivity
import com.selpar.pratikhasar.ui.eFaturaOnarimActivity
import java.security.SignatureSpi

class Onarim_List_View_Holder(itemView : View): RecyclerView.ViewHolder(itemView){

    var bundlem= Bundle()
    lateinit var kadi:String
    lateinit var ozel_id:String
    lateinit var stok_iscilik_adi: TextView
    lateinit var stok_iscilik_no:TextView
    lateinit var miktar:TextView
    lateinit var fiyat:TextView
    lateinit var toplam:TextView
    lateinit var parca_turu:TextView
    lateinit var btn_guncelle:Button
    lateinit var stok_iscilik_adi_id: TextView
    lateinit var stok_iscilik_no_id:TextView
    lateinit var miktar_id:TextView
    lateinit var fiyat_id:TextView
    lateinit var btn_fatura:ImageView
    lateinit var sAdi:String
    lateinit var sNo:String
    var miktari:Int?=null
    var fiyati:Float?=null
    lateinit var txt_kdv:TextView
    lateinit var stokadiliner:LinearLayout
    lateinit var txtid:TextView



    @SuppressLint("RestrictedApi")
    fun bindItems(itemModel : OnarimModel) {
        txtid = itemView.findViewById(com.selpar.pratikhasar.R.id.txtid) as TextView
        stok_iscilik_adi = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_stokAdi) as TextView
        stok_iscilik_no = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_StokNo) as TextView
        miktar = itemView.findViewById(com.selpar.pratikhasar.R.id.txtMiktar) as TextView
        fiyat = itemView.findViewById(com.selpar.pratikhasar.R.id.txtFiyat) as TextView
        toplam = itemView.findViewById(com.selpar.pratikhasar.R.id.txtToplam) as TextView
        txt_kdv = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_kdv) as TextView
        parca_turu = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_parca_turu) as TextView
        stokadiliner = itemView.findViewById(com.selpar.pratikhasar.R.id.stokadiliner) as LinearLayout
        //btn_fatura = itemView.findViewById(com.selpar.pratikhasar.R.id.btn_fatura) as ImageView
       // btn_guncelle=itemView.findViewById(com.selpar.pratikhasar.R.id.btn_guncelle_onarim) as Button
//       fiyat_id = itemView.findViewById(com.selpar.pratikhasar.R.id.etFiyat) as TextView
        stok_iscilik_adi.setText(itemModel.stok_iscilik_adi)
        stok_iscilik_no.setText(itemModel.stok_iscilik_no)
        miktar.setText(itemModel.miktar.toString())
        fiyat.setText(itemModel.fiyat.toString())
        toplam.setText(((itemModel.miktar*itemModel.fiyat)+itemModel.miktar*itemModel.fiyat.toFloat()*itemModel.kdv.toInt()/100.0).toString())
        txt_kdv.setText(itemModel.kdv)
        sAdi=itemModel.stok_iscilik_adi
        sNo=itemModel.stok_iscilik_no
        miktari=itemModel.miktar
        fiyati=itemModel.fiyat
        ozel_id=itemModel.ozel_id
        txtid.setText(itemModel.id)
        parca_turu.setText(" " +itemModel.parca_turu)
        if(itemModel.parcaiscilik=="Parca" || itemModel.parcaiscilik=="Parts"){
            stokadiliner.setBackgroundColor(Color.BLUE)
            stok_iscilik_adi.setTextColor(Color.WHITE)
        }
        else{
            stokadiliner.setBackgroundColor(Color.RED)
            stok_iscilik_adi.setTextColor(Color.WHITE)
        }
        itemView.setOnClickListener{
            var bundlem=Bundle()
            bundlem.putString("kadi",itemModel.kadi)
            bundlem.putString("ozel_id",itemModel.ozel_id)
            bundlem.putString("plaka",itemModel.plaka)
            bundlem.putString("sAdi",sAdi)
            bundlem.putString("sNo",sNo)
            bundlem.putString("miktar",miktari.toString())
            bundlem.putString("fiyat",fiyati.toString())
            val fragobj = OnarimFragment()
            fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger= (ContextUtils.getActivity(itemView.context) as FragmentActivity?)!!.supportFragmentManager.beginTransaction()
                .replace(com.selpar.pratikhasar.R.id.fragment_mekanik,  fragobj)
                .commit()
        }
        /*
        btn_fatura.setOnClickListener {
            val alertadd = AlertDialog.Builder(itemView.context)
            alertadd.setTitle(itemModel.plaka+" için e-Fatura oluşturulsun mu?")
            alertadd.setPositiveButton(
                "Evet"
            ) { dialog, which ->
            val i= Intent(itemView.context, eFaturaOnarimActivity::class.java)
            i.putExtra("stokno",itemModel.stok_iscilik_no)
            i.putExtra("stokadi",itemModel.stok_iscilik_adi)
            i.putExtra("plaka",itemModel.plaka)
            i.putExtra("miktar",itemModel.miktar.toString())
            i.putExtra("fiyat",itemModel.fiyat.toString())
            i.putExtra("tutar",(itemModel.miktar*itemModel.fiyat).toString())
            i.putExtra("kdv",itemModel.kdv)
                i.putExtra("plaka",itemModel.plaka)
                i.putExtra("kadi",itemModel.kadi)
                i.putExtra("ozel_id",ozel_id)
                i.putExtra("size","2")
            itemView.getContext().startActivity(i)

            }
            alertadd.setNegativeButton(
                "Hayır"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()
        }
    *//*
        btn_guncelle.setOnClickListener {
            var bundlem=Bundle()
            bundlem.putString("kadi",itemModel.kadi)
            bundlem.putString("ozel_id",itemModel.ozel_id)
            bundlem.putString("plaka",itemModel.plaka)
            bundlem.putString("sAdi",sAdi)
            bundlem.putString("sNo",sNo)
            bundlem.putString("miktar",miktari.toString())
            bundlem.putString("fiyat",fiyati.toString())
            bundlem.putString("toplam",(miktari!! * fiyati!!).toString())
            val fragobj = OnarimFragment()
            fragobj.arguments=bundlem
            //fragobj.setArguments(bundlem)
            val fragmentmaneger= (ContextUtils.getActivity(itemView.context) as FragmentActivity?)!!.supportFragmentManager.beginTransaction()
                .replace(com.selpar.pratikhasar.R.id.fragment,  fragobj)
                .commit()
        }
*/
    }
    @SuppressLint("RestrictedApi")
    fun guncelle(){

    }


}