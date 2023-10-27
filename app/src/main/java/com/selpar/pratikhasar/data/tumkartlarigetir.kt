package com.selpar.pratikhasar.data

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.*
import com.squareup.picasso.Picasso
import javax.mail.Quota.Resource


class tumkartlarigetir(private val newsList : ArrayList<news>) :
    RecyclerView.Adapter<tumkartlarigetir.MyViewHolder>() {
    lateinit var btnyazdir:ImageView
    lateinit var btn_guncelle:Button




    var sayac:Int=0
    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(com.selpar.pratikhasar.R.layout.tum_kayitli_araclar,parent,false)
        btnyazdir=itemView.findViewById(com.selpar.pratikhasar.R.id.btnHesaplaPdf)
        btn_guncelle=itemView.findViewById(com.selpar.pratikhasar.R.id.btn_guncelle)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]

        if(currentItem.ozel_id!="null")
            holder.txtozel_id.text=currentItem.ozel_id
        if(currentItem.plaka!="null")
            holder.tvplaka.text=currentItem.plaka
        if(currentItem.marka!="null")
            holder.tvmarka.text=currentItem.marka
        if(currentItem.model!="null")
            holder.tvmodel.text=currentItem.model
        if(currentItem.modelyili!="null")
            holder.tvmodelyili.text=currentItem.modelyili
        if(currentItem.kasatipi!="null")
            holder.tvkasatipi.text=currentItem.kasatipi
        if(currentItem.dosyano!="null")
            holder.tvdosyano.text=currentItem.dosyano
        if(currentItem.unvan!="null")
            holder.tvunvan.text=currentItem.unvan
        if(currentItem.renk!="null")
            holder.tvrenk.text=currentItem.renk
        if(currentItem.km!="null")
            holder.tvkm.text=currentItem.km
        //  holder.tvayrinti.setOnClickListener{onItemClick?.invoke(acikkartlarigor())}
        if(currentItem.baslik!="null")
            holder.tvbaslik.text=currentItem.baslik
        if(currentItem.kadi!="null")
            holder.vergino=currentItem.kadi
        if(currentItem.onarim.toString()!="null")
            holder.tvonarim.text=currentItem.onarim.toString()+" ₺"
        if(currentItem.servis_turu!="Mekanik Servis"){
           // btnyazdir.setBackgroundColor(Color.BLACK)

        }

        if(currentItem.servis_turu.toString()!="null" || currentItem.servis_turu=="Mekanik Servis"){
        holder.txt_servisturu.text="Mekanik"
            val res: Resources=holder.itemView.resources
            val colors: TypedArray = res.obtainTypedArray(com.selpar.pratikhasar.R.array.colors)
            val color = colors.getColor(0, 0)
            holder.txt_servisturu.setTextColor(color)

        }
        else{
            holder.txt_servisturu.text="Hasar"

        }
        //holder.tvbaslik.text=currentItem.resim
        //holder.tvresim.setImageIcon(currentItem.resim)
        try {
            Picasso.get().load(currentItem.resim).into(holder.tvresim)
        }
        catch (e:Exception){
            //holder.tvresim.
            Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.tvresim);
        }
        holder.servis_hasar.text= currentItem.servis_hasar.toString()
        holder.servis_mekanik.text= currentItem.servis_mekanik.toString()
        // sayac=holder.itemView.scrollBarSize
        // Picasso.get().load(currentItem.resim).into(holder.tvresim)
        // Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.tvresim);

        //holder.tvresim.setImageDrawable(currentItem.resim)
        holder.btnyazdir.setOnClickListener {
            if(currentItem.servis_turu!="Mekanik Servis") {
                val imservis = Intent(currentItem.context, PdfOlusturActivity::class.java)
                imservis.putExtra("ozel_id", currentItem.ozel_id)
                imservis.putExtra("kadi", currentItem.kadi)
                imservis.putExtra("sifre", currentItem.sifre)
                imservis.putExtra("firma_id", currentItem.firma_id)
                imservis.putExtra("kullanici_id", currentItem.kullanici_id)
                imservis.putExtra("kabulnom", currentItem.kabulnom)
                imservis.putExtra("resim", currentItem.resim)
                imservis.putExtra("plaka", currentItem.plaka)
                imservis.putExtra("marka", currentItem.marka)
                imservis.putExtra("model", currentItem.model)
                imservis.putExtra("modelyili", currentItem.modelyili)
                imservis.putExtra("kasatipi", currentItem.kasatipi)
                imservis.putExtra("dosyano", currentItem.dosyano)
                imservis.putExtra("unvan", currentItem.unvan)
                imservis.putExtra("renk", currentItem.renk)
                imservis.putExtra("km", currentItem.km)
                imservis.putExtra("akadi", currentItem.kadi)
                imservis.putExtra("saseno", currentItem.saseno)
                imservis.putExtra("muabit", currentItem.mua)
                imservis.putExtra("motorno", currentItem.motorno)
                imservis.putExtra("modelvers", currentItem.modelvers)
                imservis.putExtra("adres", currentItem.adres)
                imservis.putExtra("il", currentItem.il)
                imservis.putExtra("ilce", currentItem.ilce)
                imservis.putExtra("sigortasirketi", currentItem.sigortasirketi)
                imservis.putExtra("policeno", currentItem.policeno)
                imservis.putExtra("policeturu",currentItem.policeturu)
                imservis.putExtra("policetarihi",currentItem.policetarihi)
                imservis.putExtra("kazatarihi",currentItem.kazatarihi)
                imservis.putExtra("ihbartarihi",currentItem.ihbartarihi)
                imservis.putExtra("tel", currentItem.tel)
                imservis.putExtra("markaresim",currentItem.markaresim)
                imservis.putExtra("yakitturu",currentItem.yakit_turu)
                imservis.putExtra("mail",currentItem.mail)
                imservis.putExtra("telefon",currentItem.tel)
                imservis.putExtra("adres",currentItem.adres)
                imservis.putExtra("sigortasirketi",currentItem.sigortasirketi)


                currentItem.context.startActivity(imservis)
            }
            else{
                val imservis = Intent(currentItem.context, PdfOlusturMekanikActivity::class.java)
                imservis.putExtra("ozel_id", currentItem.ozel_id)
                imservis.putExtra("kadi", currentItem.kadi)
                imservis.putExtra("sifre", currentItem.sifre)
                imservis.putExtra("firma_id", currentItem.firma_id)
                imservis.putExtra("kullanici_id", currentItem.kullanici_id)
                imservis.putExtra("kabulnom", currentItem.kabulnom)
                imservis.putExtra("resim", currentItem.resim)
                imservis.putExtra("plaka", currentItem.plaka)
                imservis.putExtra("marka", currentItem.marka)
                imservis.putExtra("model", currentItem.model)
                imservis.putExtra("modelyili", currentItem.modelyili)
                imservis.putExtra("kasatipi", currentItem.kasatipi)
                imservis.putExtra("dosyano", currentItem.dosyano)
                imservis.putExtra("unvan", currentItem.unvan)
                imservis.putExtra("renk", currentItem.renk)
                imservis.putExtra("km", currentItem.km)
                imservis.putExtra("akadi", currentItem.kadi)
                imservis.putExtra("saseno", currentItem.saseno)
                imservis.putExtra("muabit", currentItem.mua)
                imservis.putExtra("motorno", currentItem.motorno)
                imservis.putExtra("modelvers", currentItem.modelvers)
                imservis.putExtra("adres", currentItem.adres)
                imservis.putExtra("il", currentItem.il)
                imservis.putExtra("ilce", currentItem.ilce)
                imservis.putExtra("sigortasirketi", currentItem.sigortasirketi)
                imservis.putExtra("policeno", currentItem.policeno)
                imservis.putExtra("tel", currentItem.tel)
                imservis.putExtra("policeturu",currentItem.policeturu)
                imservis.putExtra("policetarihi",currentItem.policetarihi)
                imservis.putExtra("kazatarihi",currentItem.kazatarihi)
                imservis.putExtra("kazatarihi",currentItem.kazatarihi)
                imservis.putExtra("ihbartarihi",currentItem.ihbartarihi)
                imservis.putExtra("mail",currentItem.mail)
                imservis.putExtra("markaresim",currentItem.markaresim)
                imservis.putExtra("yakitturu",currentItem.yakit_turu)
                imservis.putExtra("mail",currentItem.mail)
                imservis.putExtra("telefon",currentItem.tel)
                imservis.putExtra("adres",currentItem.adres)
                imservis.putExtra("sigortasirketi",currentItem.sigortasirketi)





                currentItem.context.startActivity(imservis)

            }
        }
        holder.btn_guncelle.setOnClickListener {
            if(currentItem.servis_turu!="Mekanik Servis") {
                val imservis = Intent(currentItem.context, TumAracBilgiGuncelleActivity::class.java)
                imservis.putExtra("ozel_id", currentItem.ozel_id)
                imservis.putExtra("kadi", currentItem.kadi)
                imservis.putExtra("sifre", currentItem.sifre)
                imservis.putExtra("firma_id", currentItem.firma_id)
                imservis.putExtra("kullanici_id", currentItem.kullanici_id)
                imservis.putExtra("kabulnom", currentItem.kabulnom)
                imservis.putExtra("resim", currentItem.resim)
                imservis.putExtra("plaka", currentItem.plaka)
                imservis.putExtra("marka", currentItem.marka)
                imservis.putExtra("model", currentItem.model)
                imservis.putExtra("modelyili", currentItem.modelyili)
                imservis.putExtra("kasatipi", currentItem.kasatipi)
                imservis.putExtra("dosyano", currentItem.dosyano)
                imservis.putExtra("unvan", currentItem.unvan)
                imservis.putExtra("renk", currentItem.renk)
                imservis.putExtra("km", currentItem.km)
                imservis.putExtra("akadi", currentItem.kadi)
                imservis.putExtra("saseno", currentItem.saseno)
                imservis.putExtra("muabit", currentItem.mua)
                imservis.putExtra("motorno", currentItem.motorno)
                imservis.putExtra("modelvers", currentItem.modelvers)
                imservis.putExtra("adres", currentItem.adres)
                imservis.putExtra("il", currentItem.il)
                imservis.putExtra("ilce", currentItem.ilce)
                imservis.putExtra("sigortasirketi", currentItem.sigortasirketi)
                imservis.putExtra("policeno", currentItem.policeno)
                imservis.putExtra("policeturu",currentItem.policeturu)
                imservis.putExtra("policetarihi",currentItem.policetarihi)
                imservis.putExtra("kazatarihi",currentItem.kazatarihi)
                imservis.putExtra("ihbartarihi",currentItem.ihbartarihi)
                imservis.putExtra("mail",currentItem.mail)

                currentItem.context.startActivity(imservis)
            }
            else{
                val imservis = Intent(currentItem.context, TumAracBilgiGuncelleActivity::class.java)
                imservis.putExtra("ozel_id", currentItem.ozel_id)
                imservis.putExtra("kadi", currentItem.kadi)
                imservis.putExtra("sifre", currentItem.sifre)
                imservis.putExtra("firma_id", currentItem.firma_id)
                imservis.putExtra("kullanici_id", currentItem.kullanici_id)
                imservis.putExtra("kabulnom", currentItem.kabulnom)
                imservis.putExtra("resim", currentItem.resim)
                imservis.putExtra("plaka", currentItem.plaka)
                imservis.putExtra("marka", currentItem.marka)
                imservis.putExtra("model", currentItem.model)
                imservis.putExtra("modelyili", currentItem.modelyili)
                imservis.putExtra("kasatipi", currentItem.kasatipi)
                imservis.putExtra("dosyano", currentItem.dosyano)
                imservis.putExtra("unvan", currentItem.unvan)
                imservis.putExtra("renk", currentItem.renk)
                imservis.putExtra("km", currentItem.km)
                imservis.putExtra("akadi", currentItem.kadi)
                imservis.putExtra("saseno", currentItem.saseno)
                imservis.putExtra("muabit", currentItem.mua)
                imservis.putExtra("motorno", currentItem.motorno)
                imservis.putExtra("modelvers", currentItem.modelvers)
                imservis.putExtra("adres", currentItem.adres)
                imservis.putExtra("il", currentItem.il)
                imservis.putExtra("ilce", currentItem.ilce)
                imservis.putExtra("sigortasirketi", currentItem.sigortasirketi)
                imservis.putExtra("policeno", currentItem.policeno)

                currentItem.context.startActivity(imservis)

            }
        }
        holder.itemView.setOnClickListener {

            if(currentItem.servis_turu=="Mekanik Servis"){
                val imservis = Intent(currentItem.context, MekanikKartBilgiActivity::class.java)
                imservis.putExtra("ozel_id", currentItem.ozel_id)
                imservis.putExtra("kadi", currentItem.kadi)
                imservis.putExtra("sifre", currentItem.sifre)
                imservis.putExtra("firma_id", currentItem.firma_id)
                imservis.putExtra("kullanici_id", currentItem.kullanici_id)
                imservis.putExtra("kabulnom", currentItem.kabulnom)
                imservis.putExtra("resim", currentItem.resim)
                imservis.putExtra("plaka", currentItem.plaka)
                imservis.putExtra("marka", currentItem.marka)
                imservis.putExtra("model", currentItem.model)
                imservis.putExtra("modelyili", currentItem.modelyili)
                imservis.putExtra("kasatipi", currentItem.kasatipi)
                imservis.putExtra("dosyano", currentItem.dosyano)
                imservis.putExtra("unvan", currentItem.unvan)
                imservis.putExtra("renk", currentItem.renk)
                imservis.putExtra("km", currentItem.km)
                imservis.putExtra("akadi", currentItem.kadi)
                imservis.putExtra("baslik", currentItem.baslik)
                imservis.putExtra("servisturu", currentItem.servis_turu)
                imservis.putExtra("saseno", currentItem.saseno)


                currentItem.context.startActivity(imservis)
            }
            else {
                val im = Intent(currentItem.context, AcikKartBilgiActivity::class.java)
                im.putExtra("ozel_id", currentItem.ozel_id)
                im.putExtra("kadi", currentItem.kadi)
                im.putExtra("sifre", currentItem.sifre)
                im.putExtra("firma_id", currentItem.firma_id)
                im.putExtra("kullanici_id", currentItem.kullanici_id)
                im.putExtra("kabulnom", currentItem.kabulnom)
                im.putExtra("resim", currentItem.resim)
                im.putExtra("plaka", currentItem.plaka)
                im.putExtra("marka", currentItem.marka)
                im.putExtra("model", currentItem.model)
                im.putExtra("modelyili", currentItem.modelyili)
                im.putExtra("kasatipi", currentItem.kasatipi)
                im.putExtra("dosyano", currentItem.dosyano)
                im.putExtra("unvan", currentItem.unvan)
                im.putExtra("renk", currentItem.renk)
                im.putExtra("km", currentItem.km)
                im.putExtra("akadi", currentItem.kadi)
                im.putExtra("baslik", currentItem.baslik)
                im.putExtra("servisturu", currentItem.servis_turu)
                im.putExtra("saseno", currentItem.saseno)





                currentItem.context.startActivity(im)
            }
        }

    }

    override fun getItemCount(): Int {
        return newsList.size
        //newsList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val tvplaka : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvplaka)
        val tvresim : ImageView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvaracresim)
        val tvmarka : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvmarka)
        val tvmodel : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvmodel)
        val tvmodelyili : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvmodelyili)
        val tvkasatipi : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvkasatipi)
        val tvdosyano : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvdosyano)
        val tvunvan : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvunvan)
        val tvrenk : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvrenk)
        val tvkm : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvkm)
        val tvonarim : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvonarim)
        val tvbaslik : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.Tvbaslik)
        val txt_servisturu : TextView = itemView.findViewById(com.selpar.pratikhasar.R.id.txt_servisturu)
        val btnyazdir:ImageView=itemView.findViewById(com.selpar.pratikhasar.R.id.btnHesaplaPdf)
        val btn_guncelle:Button=itemView.findViewById(com.selpar.pratikhasar.R.id.btn_guncelle)
        lateinit var vergino:String
        val servis_hasar:TextView=itemView.findViewById(com.selpar.pratikhasar.R.id.txt_hasar)
        val servis_mekanik:TextView=itemView.findViewById(com.selpar.pratikhasar.R.id.txt_mekanik)
        val txtozel_id:TextView=itemView.findViewById(com.selpar.pratikhasar.R.id.txtozel_id)

    }
}
