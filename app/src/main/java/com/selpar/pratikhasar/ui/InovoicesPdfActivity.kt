package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.barteksc.pdfviewer.PDFView
import com.selpar.pratikhasar.BuildConfig
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.PdfDocumentInvoiceAdapter
import org.json.JSONArray
import java.io.File
import java.util.Calendar

class InovoicesPdfActivity : AppCompatActivity() {
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    val stok_adi : ArrayList<String> = ArrayList()
    val stok_no : ArrayList<String> = ArrayList()
    val fiyat : ArrayList<String> = ArrayList()
    val miktar : ArrayList<String> = ArrayList()
    val toplam_ : ArrayList<Float> = ArrayList()
    val tutar : ArrayList<Float> = ArrayList()
    val toplamkdvli : ArrayList<String> = ArrayList()
    val toplamkdvsiz : ArrayList<String> = ArrayList()
    var pageHeight = 1120
    var pageWidth = 792
    var PERMISSION_CODE = 101
    lateinit var scaledbmp: Bitmap
    lateinit var Pdfviewer: PDFView
    lateinit var myImageView:ImageView
    lateinit var imagePrint:ImageView
    lateinit var imgeWhatsapp:ImageView
    var unvan:String=""
    var vergino:String=""
    var vergidairesi:String=""
    var tel:String=""
    var adres:String=""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inovoices_pdf)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        Pdfviewer = findViewById(R.id.pdfView)
        myImageView = findViewById(R.id.myImageView)
        imagePrint = findViewById(R.id.imagePrint)
        imgeWhatsapp = findViewById(R.id.imgeWhatsapp)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        overridePendingTransition(R.anim.sag, R.anim.sol)
        kayit_getir()
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
        imgeWhatsapp.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/INVOICE.pdf"
            // val path= "backup_rules.xml"
            //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/HASAR.pdf",
            val file = File(Environment.getExternalStorageDirectory(), "INVOICE.pdf")
            val dosyaYolu = File("/storage/emulated/0/INVOICE.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setPackage("com.whatsapp")

            startActivity(intent)

        }
        imagePrint.setOnClickListener {
            val printmanager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

            val printDocumentAdapter= PdfDocumentInvoiceAdapter()
            printmanager.print("Document",printDocumentAdapter, PrintAttributes.Builder().build())


        }

    }
    private fun kayit_getir() {
        val urlsb =
            "&unvan=" + intent.getStringExtra("unvan").toString().replace(" ","%20")+"&vergino="+intent.getStringExtra("vergino")+"&random="+intent.getStringExtra("random")

        var url = "https://pratikhasar.com/netting/mobil.php?tur=fatura_iscilik_getir" + urlsb
        Log.d("STOK", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["fatura"] as JSONArray
                    //println(outputObject["plaka"])
                    var toplam=0.00
                    var toplam_kdv=0.00
                    var tolpam_kdvsiz=0.00

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        fiyat.add(item.getString("fiyat"))
                        miktar.add(item.getString("miktar"))
                        stok_no.add(item.getString("stok_iscilik_no"))
                        stok_adi.add(item.getString("stok_iscilik_adi"))
                        tolpam_kdvsiz+=item.getString("fiyat").toFloat()*item.getString("miktar").toInt()
                        toplamkdvsiz.add(tolpam_kdvsiz.toString())

                        toplam+=(item.getString("fiyat").toFloat()*item.getString("miktar").toInt()+
                                item.getString("fiyat").toFloat()*item.getString("miktar").toInt()*item.getString("kdv").toInt()/100)
                        toplam_.add(toplam.toFloat())
                        tutar.add((item.getString("fiyat").toFloat()*item.getString("miktar").toInt()+
                                item.getString("fiyat").toFloat()*item.getString("miktar").toInt()*item.getString("kdv").toInt()/100))
                        toplam_kdv+=item.getString("fiyat").toFloat()*item.getString("miktar").toInt()*item.getString("kdv").toInt()/100
                        toplamkdvli.add(toplam_kdv.toString())




                    }
                    firma_bul(fiyat,miktar,stok_no,stok_adi,toplam_,toplamkdvli,toplamkdvsiz,tutar)
                    //generateToPdf(fiyat,miktar,stok_no,stok_adi,toplam_,toplamkdvli,toplamkdvsiz)

                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

    private fun firma_bul(
        fiyat: ArrayList<String>,
        miktar: ArrayList<String>,
        stokNo: ArrayList<String>,
        stokAdi: ArrayList<String>,
        toplam_: ArrayList<Float>,
        toplamkdvli: ArrayList<String>,
        toplamkdvsiz: ArrayList<String>,
        tutar: ArrayList<Float>
    ) {
        val urlsb =
            "&firma_id=" + intent.getStringExtra("firma_id").toString()

        var url = "https://pratikhasar.com/netting/mobil.php?tur=firma_getir" + urlsb
        Log.d("STOK", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {

                    val json = response["bilgi"] as JSONArray
                    //println(outputObject["plaka"])
                    var toplam=0.00
                    var toplam_kdv=0.00
                    var tolpam_kdvsiz=0.00

                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        if(item.getString("unvan")!="null")
                        unvan=item.getString("unvan")
                        if(item.getString("vergino")!="null")
                        vergino=item.getString("vergino")
                        if(item.getString("vergidairesi")!="null")
                        vergidairesi=item.getString("vergidairesi")
                        if(item.getString("tel")!="null")
                        tel=item.getString("tel")
                        if(item.getString("adres")!="null")
                        adres=item.getString("adres")






                    }

                    generateToPdf(fiyat,miktar,stok_no,stok_adi,toplam_,toplamkdvli,toplamkdvsiz,unvan,vergino,vergidairesi,tel,adres,tutar)

                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //  Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun generateToPdf(
        fiyat: ArrayList<String>,
        miktar: ArrayList<String>,
        stokNo: ArrayList<String>,
        stokAdi: ArrayList<String>,
        toplam_: ArrayList<Float>,
        toplamkdvli: ArrayList<String>,
        toplamkdvsiz: ArrayList<String>,
        unvan: String,
        vergino: String,
        vergidairesi: String,
        tel: String,
        adres: String,
        tutar: ArrayList<Float>
    ) {
        myImageView.setImageResource(R.drawable.invo)
        val bitmapDrawable=myImageView.drawable as BitmapDrawable
        val arababbitmap=bitmapDrawable.bitmap
        scaledbmp = Bitmap.createScaledBitmap(arababbitmap, 250, 100, false)


        // creating an object variable
        // for our PDF document.
        var pdfDocument: PdfDocument = PdfDocument()

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        var paint: Paint = Paint()
        var title: Paint = Paint()
        var plaka: Paint = Paint()
        var arac_bilgi: Paint = Paint()
        var arac_bilgileri: Paint = Paint()
        var arac_sahibi: Paint = Paint()
        var pdf_baslik: Paint = Paint()
        var arac_bilgileri_renk: Paint = Paint()
        var toplubilgi: Paint = Paint()
        var arac_bilgileri2: Paint = Paint()
        var toplu: Paint = Paint()
        var toplu2: Paint = Paint()
        var km: Paint = Paint()
        var textblue: Paint = Paint()
        var paint_blue: Paint = Paint()
        var paint_red: Paint = Paint()
        var paint_bold: Paint = Paint()
        textblue.color= Color.BLUE
        paint_blue.color= Color.BLUE
        paint_red.color= Color.RED
        paint_bold.color= Color.BLACK
        textblue.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        paint_red.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        paint_bold.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        paint_red.textSize=30F
        textblue.textSize=40F
        paint_blue.textSize=20f
        paint_bold.textSize=20f



        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // below line is used for setting
        // start page for our PDF file.
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        var canvas: Canvas = myPage.canvas

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.

        canvas.drawBitmap(scaledbmp, 10F, 10F, paint)

        canvas.drawText(intent.getStringExtra("unvan").toString(),10f,200f,paint)
        canvas.drawText(intent.getStringExtra("vergino").toString(),10f,220f,paint)
        canvas.drawText(intent.getStringExtra("vergidairesi").toString(),10f,240f,paint)
        canvas.drawText(intent.getStringExtra("adres").toString(),10f,260f,paint)
        canvas.drawText(unvan,550f,50f,paint)
        canvas.drawText(vergino,550f,70f,paint)
        canvas.drawText(vergidairesi,550f,90f,paint)
        canvas.drawText(tel,550f,100f,paint)
        canvas.drawText(adres,550f,120f,paint)

        val currentDateTime = getCurrentDateTimeUsingCalendar()

        // Get individual components (year, month, day, hour, minute, second)
        val year = currentDateTime.get(Calendar.YEAR)
        val month = currentDateTime.get(Calendar.MONTH) + 1 // Months start from 0 (January) in Calendar, so add 1.
        val day = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val hour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentDateTime.get(Calendar.MINUTE)
        val second = currentDateTime.get(Calendar.SECOND)
        canvas.drawText(getString(R.string.tarih),550f,180f,paint_blue)
        canvas.drawText(day.toString()+"-"+month+"-"+year,550f,200f,paint)
        canvas.drawText(getString(R.string.saat),620f,180f,paint_blue)
        canvas.drawText(hour.toString()+":"+minute+":"+second,620f,200f,paint)
        canvas.drawLine(10F,300f,780F,300f,textblue)
        canvas.drawText(getString(R.string.numara),10f,320f,paint_blue)
        canvas.drawText(getString(R.string.desc),200f,320f,paint_blue)
        canvas.drawText(getString(R.string.rate),500f,320f,paint_blue)
        canvas.drawText(getString(R.string.qty),600f,320f,paint_blue)
        canvas.drawText(getString(R.string.tutar),700f,320f,paint_blue)

        var x_numara=10f
        var y_numara=340f
        var x_aciklama=200f
        var y_aciklama=340f

        var x_fiyat=500f
        var y_fiyat=340f
        var x_miktar=600f
        var y_miktar=340f
        var x_tutar=700f
        var y_tutar=340f
        var x_kdv=640f
        var y_kdv=570f
        var x_line=10F
        var y_line=575F
        //green line
        arac_bilgileri.textAlign = Paint.Align.LEFT
        toplu.textAlign = Paint.Align.RIGHT
        toplu2.textAlign = Paint.Align.RIGHT
        var binler = 1000
        var yuzler = 1000
        var ondalik = 18 % 100
        var topla:Float=0f
Toast.makeText(this,"Stok no: "+this.stok_no.size,Toast.LENGTH_LONG).show()
        for (i in 0 until this.stok_no.size) {

            try{
                canvas.drawText(this.stok_no[i],x_numara,y_numara,arac_bilgileri2)
                canvas.drawText(this.stok_adi[i],x_aciklama,y_aciklama,arac_bilgileri2)
                canvas.drawText(this.miktar[i],x_miktar+20,y_miktar,arac_bilgileri)
                binler = this.fiyat[i].toInt() / 1000
                yuzler = this.fiyat[i].toInt() % 1000
                ondalik = (this.fiyat[i].toInt() * 100).toInt() % 100
                if(binler==0){
                    canvas.drawText(String.format("%.2f",this.fiyat[i].toFloat()).replace(".",","),x_fiyat+40,y_fiyat,toplu2)

                }else
                    canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_fiyat+40f,y_fiyat,toplu2)
                //Log.d("tutar",toplam_[i])
                topla+=this.tutar[i]
                binler = this.tutar[i].toInt() / 1000
                yuzler = this.tutar[i].toInt() % 1000
                ondalik = (this.tutar[i].toInt() * 100).toInt() % 100
                if(binler==0){
                    canvas.drawText(String.format("%.2f",this.tutar[i].toFloat()).replace(".",","),x_tutar+40f,y_tutar,toplu2)

                }else
                    canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_tutar+40f,y_tutar,toplu2)
                y_aciklama+=20f
                y_miktar+=20f
                y_fiyat+=20f
                y_kdv+=20f
                y_tutar+=20f
                y_numara+=20f
            }catch (e:Exception){
                Toast.makeText(this,"HATA:"+ e.message,Toast.LENGTH_LONG).show()
            }

        }
        canvas.drawText(getString(R.string.toplam),600f,y_tutar+80,paint_blue)
        binler = topla.toInt() / 1000
        yuzler = topla.toInt() % 1000
        ondalik = (topla.toInt() * 100).toInt() % 100
        if(binler==0){
            canvas.drawText(String.format("%.2f",topla.toFloat()).replace(".",","),670f,y_tutar+80,paint_bold)

        }else
            canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),670f,y_tutar+80,paint_bold)

        ///en üste
        canvas.drawText(getString(R.string.fatura_tutar),540f,240f,paint_blue)

        if(binler==0){
            canvas.drawText(String.format("%.2f",topla.toFloat()).replace(".",",")+getString(R.string.para),540f,280f,paint_red)

        }else
            canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik)+getString(R.string.para),540f,280f,paint_red)
       // canvas.drawText(topla.toString(),630f,y_tutar+80,paint_red)
            pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.
        val file: File = File(Environment.getExternalStorageDirectory(), "INVOICE.pdf")
        try {
            pdfDocument.writeTo(file.outputStream())
            Pdfviewer.fromFile(file).enableSwipe(true).swipeHorizontal(false).load()

            //pdfDocument.writeTo(FileOutputStream(file))

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(applicationContext, "PDF dosyası oluşturuldu..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()
            Log.d("TAG",e.message.toString())

            /* on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()*/
        }
    }
    fun getCurrentDateTimeUsingCalendar(): Calendar {
        return Calendar.getInstance()
    }

}