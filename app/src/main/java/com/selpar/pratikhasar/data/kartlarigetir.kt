package com.selpar.pratikhasar.data

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.selpar.pratikhasar.ui.AcikKartBilgiActivity
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.ui.MekanikKartBilgiActivity
import com.selpar.pratikhasar.ui.PdfOlusturActivity
import com.selpar.pratikhasar.ui.PdfOlusturMekanikActivity
import com.squareup.picasso.Picasso

class kartlarigetir(private val newsList : ArrayList<news>) :
    RecyclerView.Adapter<kartlarigetir.MyViewHolder>() {
    lateinit var btnyazdir:ImageView



     var sayac:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.card_kayitli_araclar,parent,false)
       btnyazdir=itemView.findViewById(com.selpar.pratikhasar.R.id.btnHesaplaPdf)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]

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
        holder.txt_servisturu.text=currentItem.servis_turu
        //holder.tvbaslik.text=currentItem.resim
        //holder.tvresim.setImageIcon(currentItem.resim)
        try {
                Picasso.get().load(currentItem.resim).into(holder.tvresim)
        }
        catch (e:Exception){
            //holder.tvresim.
            Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.tvresim);
        }
       // sayac=holder.itemView.scrollBarSize
       // Picasso.get().load(currentItem.resim).into(holder.tvresim)
       // Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.tvresim);

        //holder.tvresim.setImageDrawable(currentItem.resim)
        holder.btnyazdir.setOnClickListener {
            if(currentItem.servis_turu!="Mekanik Servis") {
                //Toast.makeText(currentItem.context,currentItem.resim,Toast.LENGTH_LONG).show()

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

                currentItem.context.startActivity(imservis)
            }
            else{
               // Toast.makeText(currentItem.context,currentItem.resim,Toast.LENGTH_LONG).show()
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


                currentItem.context.startActivity(im)
            }
        }

    }

    override fun getItemCount(): Int {
        return newsList.size
        //newsList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val tvplaka : TextView = itemView.findViewById(R.id.Tvplaka)
        val tvresim : ImageView = itemView.findViewById(R.id.Tvaracresim)
        val tvmarka : TextView = itemView.findViewById(R.id.Tvmarka)
        val tvmodel : TextView = itemView.findViewById(R.id.Tvmodel)
        val tvmodelyili : TextView = itemView.findViewById(R.id.Tvmodelyili)
        val tvkasatipi : TextView = itemView.findViewById(R.id.Tvkasatipi)
        val tvdosyano : TextView = itemView.findViewById(R.id.Tvdosyano)
        val tvunvan : TextView = itemView.findViewById(R.id.Tvunvan)
        val tvrenk : TextView = itemView.findViewById(R.id.Tvrenk)
        val tvkm : TextView = itemView.findViewById(R.id.Tvkm)
        val tvonarim : TextView = itemView.findViewById(R.id.Tvonarim)
        val tvbaslik : TextView = itemView.findViewById(R.id.Tvbaslik)
        val txt_servisturu : TextView = itemView.findViewById(R.id.txt_servisturu)
        val btnyazdir:ImageView=itemView.findViewById(R.id.btnHesaplaPdf)
        lateinit var vergino:String

    }
}