package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.Intent
import android.graphics.Canvas
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.EvrakAdapter
import com.selpar.pratikhasar.adapter.EvrakGVAdapter
import com.selpar.pratikhasar.adapter.FaturaGVAdapter
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.FaturaModel
import com.selpar.pratikhasar.data.ItemModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.util.ArrayList

class GonderilmisManuelFaturalarActivity : AppCompatActivity() {
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    lateinit var firma:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var kadi:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    lateinit var autoCompleteTextView:AutoCompleteTextView
    lateinit var idGVcourses:GridView
    lateinit var btn_getir_fatura:Button
    val itemList_vergino : ArrayList<String> = ArrayList()
    lateinit var bitisTarih:EditText
    lateinit var baslangictarih:EditText
    lateinit var baslangic_date:String
    lateinit var bitis_date:String
    lateinit var idsonuc:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gonderilmis_manuel_faturalar)
        onBaslat()

        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack.setOnClickListener {
            val i= Intent(this,HomeActivity::class.java)
            i.putExtra("dilsecimi",intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("gorev",intent.getStringExtra("gorev"))
            startActivity(i)
        }
        spinnerVergiNoDoldur(firma_id)
        baslangictarih.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        baslangic_date = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        baslangictarih.setText(dat_gosterilen)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }
        bitisTarih.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val c = Calendar.getInstance()

                // on below line we are getting
                // our day, month and year.
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                // on below line we are creating a
                // variable for date picker dialog.
                val datePickerDialog = DatePickerDialog(
                    // on below line we are passing context.
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        bitis_date = (year.toString() + "-" + (monthOfYear + 1) + "-" +dayOfMonth )
                        val dat_gosterilen = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" +year )

                        bitisTarih.setText(dat_gosterilen)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
                datePickerDialog.show()
            } else {
            }
        }

        btn_getir_fatura.setOnClickListener {
            idGVcourses.adapter=null
            faturagetir()
        }

    }
    private fun onBaslat() {
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        idGVcourses = findViewById(R.id.idGVcourses)
        btn_getir_fatura = findViewById(R.id.btn_getir_fatura)
        bitisTarih = findViewById(R.id.bitisTarih)
        baslangictarih = findViewById(R.id.baslangictarih)
        idsonuc = findViewById(R.id.idsonuc)






    }
    private fun spinnerVergiNoDoldur(firmaId: String) {
        val urlsb = "&firma_id=" + firmaId
        var url = "https://pratikhasar.com/netting/mobil.php?tur=arama_vergino_getir" + urlsb
        Log.d("RESİMYOL",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["cariler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        itemList_vergino.add( item.getString("vergino"))
                    }

                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, itemList_vergino)

                    autoCompleteTextView.setAdapter(adapter)

                }catch (e:Exception){
                    // Toast.makeText(requireContext(),"spinner doldurma hatası",Toast.LENGTH_LONG).show()
                }

            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                // Toast.makeText(requireContext(), "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }


    private fun faturagetir()
    {
        val urlsb ="&kadi=" + kadi +"&firma_id="+firma_id+"&vergino="+autoCompleteTextView.getText().toString()+
                "&baslangicTarihi="+baslangic_date+"&bitisTarihi="+bitis_date
        var url = "https://pratikhasar.com/netting/mobil.php?tur=fatura_getir"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["fatura"] as JSONArray
                    val courseModelArrayList: ArrayList<FaturaModel> = ArrayList<FaturaModel>()

                    val itemList: ArrayList<ItemModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        var yol ="https://pratikhasar.com/netting/resimler/fatura/" +firma_id+"/"+autoCompleteTextView.getText().toString()+"/"+item.getString("yol")
                        // yol[i] ="https://pratikhasar.com/netting/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        //yol[i] ="https://pratikhasar.com/netting/"+item.getString("yol")
                        Log.d("RESIMM",yol)


                        val itemModel = FaturaModel(
                            kadi,firma_id,item.getString("toplam_tutar").toString(),yol
                        )

                        courseModelArrayList.add(itemModel)

                        val adapter2 = FaturaGVAdapter(this, courseModelArrayList)
                        idGVcourses.adapter = adapter2
                        idsonuc.visibility=GONE


                       }}catch (e:Exception){
                    idsonuc.visibility=VISIBLE
                       }





            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
            }
        )
        queue.add(request)


    }

}