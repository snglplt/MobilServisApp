package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.TaksitModel
import com.selpar.pratikhasar.ui.SatinAlMainActivity
import com.selpar.pratikhasar.ui.SatinAlmaEkraniActivity
import com.squareup.picasso.Picasso

class TaksitAdapter (context: Context, courseModelArrayList: ArrayList<TaksitModel>) :
    ArrayAdapter<TaksitModel?>(context, 0, courseModelArrayList!! as List<TaksitModel?>) {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(com.selpar.pratikhasar.R.layout.card_paket_item, parent, false)
        }
        val courseModel: TaksitModel? = getItem(position)
        val courseTV = listitemView!!.findViewById<TextView>(com.selpar.pratikhasar.R.id.idTVCourse)
        val courseIV = listitemView.findViewById<ImageView>(com.selpar.pratikhasar.R.id.idIVcourse)
        courseTV.setText(courseModel!!.kartbanka)

        //Toast.makeText(listitemView.context,"resim: "+courseModel.kartgorsel,Toast.LENGTH_LONG).show()
        Picasso.get().load(courseModel.kartgorsel).into(courseIV)
        listitemView!!.setOnClickListener {
            val alertadd = AlertDialog.Builder(listitemView.context)
            val factory = LayoutInflater.from(listitemView.context)
            val view: View = factory.inflate(com.selpar.pratikhasar.R.layout.taksit_goster, null)
            val txtkart_ismi=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txtkart_ismi)
            txtkart_ismi.setText(courseModel.kartbanka)
            val btn_birtaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_birtaksit)
            if(courseModel.taksit1<0)
                btn_birtaksit.isEnabled=false
            val txt_birtaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_birtaksit)
            val txt_tutar1=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar1)
            val txt_komisyon1=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon1)
            txt_tutar1.setText(courseModel.toplam)
            txt_komisyon1.setText(((courseModel.toplam.toInt()*courseModel.taksit1)/100).toString())
            val bir_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit1)/100).toString()
            txt_birtaksit.setText(bir_taksit_tutar)
            btn_birtaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_birtaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","1")
                i.putExtra("sanal_pos",courseModel.sanal_pos)

                listitemView.context.startActivity(i)
            }
            //
            val btn_ikitaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_ikitaksit)
            if(courseModel.taksit2<0)
                btn_ikitaksit.isEnabled=false
            val txt_ikitaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_ikitaksit)
            val txt_tutar2=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar2)
            val txt_komisyon2=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon2)
            txt_tutar2.setText(courseModel.toplam)
            txt_komisyon2.setText(((courseModel.toplam.toInt()*courseModel.taksit2)/100).toString())
            val iki_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit2)/100).toString()
            txt_ikitaksit.setText(iki_taksit_tutar)
            btn_ikitaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_ikitaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","2")
                i.putExtra("sanal_pos",courseModel.sanal_pos)


                listitemView.context.startActivity(i)
            }
            //
            val btn_uctaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_uctaksit)
            if(courseModel.taksit3<0)
                btn_uctaksit.isEnabled=false
            val txt_uctaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_uctaksit)
            val txt_tutar3=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar3)
            val txt_komisyon3=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon3)
            txt_tutar3.setText(courseModel.toplam)
            txt_komisyon3.setText(((courseModel.toplam.toInt()*courseModel.taksit3)/100).toString())
            val uc_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit3)/100).toString()
            txt_uctaksit.setText(uc_taksit_tutar)
            btn_uctaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_uctaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","3")
                i.putExtra("sanal_pos",courseModel.sanal_pos)

                listitemView.context.startActivity(i)
            }
            //
            val btn_dorttaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_dorttaksit)
            if(courseModel.taksit4<0)
                btn_dorttaksit.isEnabled=false
            val txt_dorttaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_dorttaksit)
            val txt_tutar4=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar4)
            val txt_komisyon4=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon4)
            txt_tutar4.setText(courseModel.toplam)
            txt_komisyon4.setText(((courseModel.toplam.toInt()*courseModel.taksit4)/100).toString())
            val dort_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit4)/100).toString()
            txt_dorttaksit.setText(dort_taksit_tutar)
            btn_dorttaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_dorttaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","4")
                i.putExtra("sanal_pos",courseModel.sanal_pos)


                listitemView.context.startActivity(i)
            }
            //
            val btn_bestaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_bestaksit)
            if(courseModel.taksit5<0)
                btn_bestaksit.isEnabled=false
            val txt_bestaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_bestaksit)
            val txt_tutar5=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar5)
            val txt_komisyon5=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon5)
            txt_tutar5.setText(courseModel.toplam)
            txt_komisyon5.setText(((courseModel.toplam.toInt()*courseModel.taksit5)/100).toString())
            val bes_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit5)/100).toString()
            txt_bestaksit.setText(bes_taksit_tutar)
            btn_bestaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_bestaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","5")
                i.putExtra("sanal_pos",courseModel.sanal_pos)


                listitemView.context.startActivity(i)
            }
            //
            val btn_altitaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_altitaksit)
            if(courseModel.taksit6<0)
                btn_altitaksit.isEnabled=false
            val txt_altitaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_altitaksit)
            val txt_tutar6=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar6)
            val txt_komisyon6=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon6)
            txt_tutar6.setText(courseModel.toplam)
            txt_komisyon6.setText(((courseModel.toplam.toInt()*courseModel.taksit6)/100).toString())
            val alti_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit6)/100).toString()
            txt_altitaksit.setText(alti_taksit_tutar)
            btn_altitaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_altitaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","6")
                i.putExtra("sanal_pos",courseModel.sanal_pos)


                listitemView.context.startActivity(i)
            }
            //
            val btn_yeditaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_yeditaksit)
            if(courseModel.taksit7<0)
                btn_yeditaksit.isEnabled=false
            val txt_yeditaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_yeditaksit)
            val txt_tutar7=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar7)
            val txt_komisyon7=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon7)
            txt_tutar7.setText(courseModel.toplam)
            txt_komisyon7.setText(((courseModel.toplam.toInt()*courseModel.taksit7)/100).toString())
            val yedi_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit7)/100).toString()
            txt_yeditaksit.setText(yedi_taksit_tutar)
            btn_yeditaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_yeditaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","7")
                i.putExtra("sanal_pos",courseModel.sanal_pos)

                listitemView.context.startActivity(i)
            }
            //
            val btn_sekiztaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_sekiztaksit)
            if(courseModel.taksit8<0)
                btn_sekiztaksit.isEnabled=false
            val txt_sekiztaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_sekiztaksit)
            val txt_tutar8=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar8)
            val txt_komisyon8=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon8)
            txt_tutar8.setText(courseModel.toplam)
            txt_komisyon8.setText(((courseModel.toplam.toInt()*courseModel.taksit8)/100).toString())
            val sekiz_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit8)/100).toString()
            txt_sekiztaksit.setText(sekiz_taksit_tutar)
            btn_sekiztaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_sekiztaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","8")
                i.putExtra("sanal_pos",courseModel.sanal_pos)


                listitemView.context.startActivity(i)
            }
            //
            val btn_dokuztaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_dokuztaksit)
            if(courseModel.taksit9<0)
                btn_dokuztaksit.isEnabled=false
            val txt_dokuztaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_dokuztaksit)
            val txt_tutar9=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar9)
            val txt_komisyon9=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon9)
            txt_tutar9.setText(courseModel.toplam)
            txt_komisyon9.setText(((courseModel.toplam.toInt()*courseModel.taksit9)/100).toString())
            val dokuz_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit9)/100).toString()
            txt_dokuztaksit.setText(dokuz_taksit_tutar)
            btn_dokuztaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_dokuztaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","9")
                i.putExtra("sanal_pos",courseModel.sanal_pos)


                listitemView.context.startActivity(i)
            }
            //
            val btn_ontaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_ontaksit)
            if(courseModel.taksit10<0)
                btn_ontaksit.isEnabled=false
            val txt_ontaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_ontaksit)
            val txt_tutar10=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar10)
            val txt_komisyon10=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon10)
            txt_tutar10.setText(courseModel.toplam)
            txt_komisyon10.setText(((courseModel.toplam.toInt()*courseModel.taksit10)/100).toString())
            val on_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit10)/100).toString()
            txt_ontaksit.setText(on_taksit_tutar)
            btn_ontaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_ontaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","10")
                i.putExtra("sanal_pos",courseModel.sanal_pos)

                listitemView.context.startActivity(i)
            }
            //
            val btn_onbirtaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_onbirtaksit)
            if(courseModel.taksit11<0)
                btn_onbirtaksit.isEnabled=false
            val txt_onbirtaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_onbirtaksit)
            val txt_tutar11=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar11)
            val txt_komisyon11=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon11)
            txt_tutar11.setText(courseModel.toplam)
            txt_komisyon11.setText(((courseModel.toplam.toInt()*courseModel.taksit11)/100).toString())
            val onbir_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit11)/100).toString()
            txt_onbirtaksit.setText(onbir_taksit_tutar)
            btn_onbirtaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_onbirtaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","11")
                listitemView.context.startActivity(i)
            }
            //
            val btn_onikitaksit=view.findViewById<Button>(com.selpar.pratikhasar.R.id.btn_onikitaksit)
            if(courseModel.taksit12<0)
                btn_onikitaksit.isEnabled=false
            val txt_onikitaksit=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_onikitaksit)
            val txt_tutar12=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_tutar12)
            val txt_komisyon12=view.findViewById<TextView>(com.selpar.pratikhasar.R.id.txt_komisyon12)
            txt_tutar12.setText(courseModel.toplam)
            txt_komisyon12.setText(((courseModel.toplam.toInt()*courseModel.taksit12)/100).toString())
            val oniki_taksit_tutar=(courseModel.toplam.toInt()+(courseModel.toplam.toInt()*courseModel.taksit12)/100).toString()
            txt_onikitaksit.setText(oniki_taksit_tutar)
            btn_onikitaksit.setOnClickListener {
                val i= Intent(listitemView.context,SatinAlMainActivity::class.java)
                i.putExtra("tutar",courseModel.toplam)
                i.putExtra("toplam",txt_onikitaksit.getText().toString())
                i.putExtra("firma_id",courseModel.firma_id)
                i.putExtra("taksitsayisi","12")
                i.putExtra("sanal_pos",courseModel.sanal_pos)

                listitemView.context.startActivity(i)
            }

            alertadd.setView(view)
            alertadd.setPositiveButton(
                "Kapat"
            ) { dialog, which -> dialog.dismiss() }
            alertadd.show()

        }
        //courseIV.setImageResource(courseModel.getImgid())
        return listitemView
    }
}
