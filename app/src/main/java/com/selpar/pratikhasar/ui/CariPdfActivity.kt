package com.selpar.pratikhasar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.barteksc.pdfviewer.PDFView
import com.selpar.pratikhasar.BuildConfig
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.PdfCariAdapter
import com.selpar.pratikhasar.data.PdfDocumentAdapter
import org.json.JSONArray
import java.io.File

class CariPdfActivity : AppCompatActivity() {
    lateinit var kadi:String
    lateinit var yetki:String
    lateinit var firma_id:String
    lateinit var kullnciid:String
    lateinit var sifrem:String
    lateinit var dilsecimi:String
    var pageHeight = 1120
    var pageWidth = 792
    lateinit var pdfviewer: PDFView
     var cari:ArrayList<String> = ArrayList()
     var odeme:ArrayList<String> = ArrayList()
     var tahsilat:ArrayList<String> = ArrayList()
     var virman:ArrayList<String> = ArrayList()
     var tarih:ArrayList<String> = ArrayList()
     var not:ArrayList<String> = ArrayList()
     var odeme_turu:ArrayList<String> = ArrayList()
    lateinit var btn_whatsapp: ImageView
    lateinit var imageyazdir: ImageView
    lateinit var btn_mail_gonder: ImageView
    lateinit var btn_sms_gonder: ImageView
    lateinit var gsm:String
     var bakiye:Int=0
     var bakiye_anlik:Int=0
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_pdf)
        onBaslat()
        cariGetirBul(firma_id)
       // generatePDF("cari","tahsilat","odeme","bakiye")
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        mBack.setOnClickListener {
            val i= Intent(this,TumHesaplarActivity::class.java)
            i.putExtra("kadi",kadi)
            i.putExtra("dilsecimi",dilsecimi)
            i.putExtra("yetki", yetki)
            i.putExtra("firma_id", firma_id)
            i.putExtra("kullanici_id", kullnciid)
            i.putExtra("kadi", kadi)
            i.putExtra("sifre", sifrem)
            i.putExtra("kadi",kadi)
            startActivity(i)

        }

        btn_sms_gonder.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/Cari.pdf"
            // val path= "backup_rules.xml"
            //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/GFG.pdf",
            val file = File(Environment.getExternalStorageDirectory(), "Cari.pdf")
            val dosyaYolu = File("/storage/emulated/0/Cari.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // intent.setPackage("com.whatsapp")

            startActivity(intent)
        }
        btn_mail_gonder.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/Cari.pdf"
            // val path= "backup_rules.xml"
            //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/GFG.pdf",
            val file = File(Environment.getExternalStorageDirectory(), "Cari.pdf")
            val dosyaYolu = File("/storage/emulated/0/Cari.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // intent.setPackage("com.whatsapp")

            startActivity(intent)
        }
        btn_whatsapp.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/Cari.pdf"
            // val path= "backup_rules.xml"
            //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/GFG.pdf",
            val file = File(Environment.getExternalStorageDirectory(), "Cari.pdf")
            val dosyaYolu = File("/storage/emulated/0/Cari.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setPackage("com.whatsapp")

            startActivity(intent)

        }
        imageyazdir.setOnClickListener {
            val printmanager: PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

            val printDocumentAdapter= PdfCariAdapter()
            printmanager.print("cari",printDocumentAdapter, PrintAttributes.Builder().build())


        }
    }

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun generatePDF(
        cari: ArrayList<String>,
        tahsilat: ArrayList<String>,
        odeme: ArrayList<String>,
        virman: ArrayList<String>,
        bakiye: Int
    ) {
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
        var usta_bilgi: Paint = Paint()
        var paint_h: Paint = Paint()



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



        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        plaka.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgileri.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        toplubilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        arac_bilgileri2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        toplu.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        km.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        usta_bilgi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        textblue.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))




        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F
        plaka.textSize=36F
        arac_bilgi.textSize=12F
        km.textSize=12F
        arac_bilgi.color= Color.RED
        arac_bilgileri.textSize=12F
        arac_bilgileri_renk.textSize=12F
        arac_bilgileri_renk.color= Color.GRAY
        arac_sahibi.textSize=15F
        pdf_baslik.textSize=20f
        toplubilgi.textSize=12F
        arac_bilgileri2.textSize=12F
        toplu.textSize=12F
        toplu2.textSize=12F
        textblue.textSize=12f
        usta_bilgi.textSize=12f
        // qrOlustur()






        canvas.drawText("CARİ KARTI",330F,30F,pdf_baslik)
        canvas.drawText("HESAP DÖKÜMÜ",310F,60F,pdf_baslik)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.textSize = 15F






        paint.textSize=50F
        paint_h.textSize=12F
        paint_h.textAlign = Paint.Align.RIGHT
        toplu2.textAlign = Paint.Align.RIGHT

        // canvas.drawLine(670f,530F,670f,740f,paint)


        //toplam tutar


        //green line

       canvas.drawText("CARİ BİLGİLERİ",100F,100f,arac_bilgi)
        canvas.drawLine(20f,120f,800f,120f,paint)
        canvas.drawText("Cari Unvan",140f,140f,arac_bilgileri_renk)
        canvas.drawText(cari[0],275f,140f,arac_bilgileri2)

        canvas.drawLine(20f,150f,800f,150f,paint)
        canvas.drawText("Tarih",20f,180f,arac_bilgileri_renk)
        canvas.drawText("Türü",120f,180f,arac_bilgileri_renk)
        canvas.drawText("Aciklama",170f,180f,arac_bilgileri_renk)

        canvas.drawText("Borç",300f,180f,arac_bilgileri_renk)


        canvas.drawLine(20f,190F,800f,190F,paint)

        canvas.drawText("Alacak",410F,180F,arac_bilgileri_renk)
        canvas.drawText("Bakiye",470F,180F,arac_bilgileri_renk)
        canvas.drawText("Virman",570F,180F,arac_bilgileri_renk)
        canvas.drawText("Not",670F,180F,arac_bilgileri_renk)




        var x_tahsilat=320f
        var x_tarih=20f
        var y_tahsilat=210f
        var y_tarih=210f
        val x_odeme_turu=120f
        var y_odeme_turu=210f
        val x_aciklama=170f
        var y_aciklama=210f
        val x_bakiye=470f
        var y_bakiye=210f
        var x_not=670f
        var y_not=210f

        var x_odeme=430f
        var y_odeme=210f
        var x_virman=570f
        var y_virman=210f
        var binler = 1000
        var yuzler = 1000
        var ondalik = 18 % 100
        for (i in 0 until cari.size) {
            if(virman[i]=="null" || virman[i]==""){
                canvas.drawText("", x_virman, y_virman, arac_bilgileri2)

            }else{
                canvas.drawText(virman[i], x_virman, y_virman, arac_bilgileri2)

            }
            binler = odeme[i].toInt() / 1000
            yuzler = odeme[i].toInt() % 1000
            ondalik = (odeme[i].toInt() * 100).toInt() % 100
            if(binler==0){            canvas.drawText(tarih[i], x_tarih, y_tarih, arac_bilgileri2)

                canvas.drawText(String.format("%.2f",this.odeme[i].toFloat()).replace(".",","),x_tahsilat+60,y_tahsilat,toplu2)

            }else
                canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_tahsilat+60,y_tahsilat,toplu2)

         //   canvas.drawText(odeme[i].toString(), x_tahsilat+60, y_tahsilat, paint_h)
            binler = tahsilat[i].toInt() / 1000
            yuzler = tahsilat[i].toInt() % 1000
            ondalik = (tahsilat[i].toInt() * 100).toInt() % 100
            if(binler==0){
                canvas.drawText(String.format("%.2f",this.tahsilat[i].toFloat()).replace(".",","),x_odeme+20,y_tahsilat,toplu2)

            }else
                canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_odeme+20,y_tahsilat,toplu2)

        //    canvas.drawText(tahsilat[i], x_odeme+50, y_odeme, paint_h)
            canvas.drawText(odeme_turu[i], x_odeme_turu-10, y_odeme_turu, arac_bilgileri2)
            var tah=0
            var ode=0
            for( j in 0 until i+1){
                 tah+=tahsilat[j].toInt()
                 ode+=odeme[j].toInt()
            }
            bakiye_anlik=tah-ode
           /* binler = bakiye_anlik / 1000
            yuzler = bakiye_anlik% 1000
            ondalik = (bakiye_anlik* 100).toInt() % 100
            if(binler==0){
                canvas.drawText(String.format("%.2f",this.bakiye_anlik.toFloat()).replace(".",","),x_bakiye,y_bakiye,toplu2)

            }else
                canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_bakiye,y_bakiye,toplu2)
*/
                if(bakiye_anlik.toFloat()>0) {
                    var binler = bakiye_anlik.toFloat().toInt() / 1000
                    var yuzler = bakiye_anlik.toFloat().toInt() % 1000
                    var ondalik = (bakiye_anlik.toFloat().toInt() * 100).toInt() % 100
                    if (binler == 0) {
                        canvas.drawText(
                            String.format("%.2f", bakiye_anlik.toFloat()).replace(".", ","),x_bakiye+50, y_bakiye, paint_h
                        )

                    } else
                        canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_bakiye+50, y_bakiye, paint_h)
                }else{
                    var binler = bakiye_anlik.toFloat().toInt() / 1000
                    var yuzler = bakiye_anlik.toFloat().toInt() % 1000
                    var ondalik = (bakiye_anlik.toFloat().toInt() * 100).toInt() % 100
                    if (binler == 0) {
                        canvas.drawText(
                            String.format("%.2f",bakiye_anlik.toFloat()).replace(".", ","),x_bakiye+50, y_bakiye, paint_h
                        )

                    } else
                        canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler*-1, ondalik),x_bakiye+50, y_bakiye, paint_h)

                }


           // canvas.drawText((bakiye_anlik).toString(), x_bakiye+50, y_bakiye, paint_h)
            canvas.drawText(not[i], x_not, y_not, arac_bilgileri)
            if(odeme[i].toString().toInt()>0){
                canvas.drawText("Ödeme",x_aciklama,y_aciklama,arac_bilgileri)

            }else{
                canvas.drawText("Tahsilat",x_aciklama,y_aciklama,arac_bilgileri)

            }
            canvas.drawLine(20f,y_virman+5,800f,y_virman+5,paint)

            y_virman+=20f
            y_tahsilat+=20f
            y_odeme+=20f
            y_tarih+=20f
            y_aciklama+=20f
            y_odeme_turu+=20f
            y_not+=20f
            y_bakiye+=20f

        }
        canvas.drawLine(x_tarih+70,150f,x_tarih+70,y_virman-15,paint)

        canvas.drawLine(x_odeme_turu+35,150f,x_odeme_turu+35,y_virman-15,paint_h)
        canvas.drawLine(x_aciklama+70,150f,x_aciklama+70,y_virman-15,paint)
        canvas.drawLine(x_tahsilat+60,150f,x_tahsilat+60,y_virman-15,paint_h)
        canvas.drawLine(x_odeme+20,150f,x_odeme+20,y_virman-15,paint_h)
        canvas.drawLine(x_bakiye+50,150f,x_bakiye+50,y_virman-15,paint_h)
        canvas.drawLine(x_not-10,150f,x_not-10,y_virman-15,paint)


        canvas.drawText("Bakiye",x_not,y_odeme,arac_bilgi)

        canvas.drawText(bakiye.toString(),x_not+50,y_odeme,arac_bilgi)

        canvas.drawText("İş bu rapor ",70f,800F,arac_bilgileri)
        textblue.color= Color.BLUE
        canvas.drawText("www.pratikhasar.com",140f,800f,textblue)
        canvas.drawText("yazılımından bilgi amaçlı olarak alınmıştır. Kanunen bir değeri yoktur.",280f,800F,arac_bilgileri)


        pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.
        val file: File = File(Environment.getExternalStorageDirectory(), "Cari.pdf")
        try {
            // after creating a file name we will
            // write our PDF file to that location.

            pdfDocument.writeTo(file.outputStream())
            pdfviewer.fromFile(file).enableSwipe(true).swipeHorizontal(false).load()

            // pdfDocument.writeTo(FileOutputStream(file))

            // on below line we are displaying a toast message as PDF file generated..
            Toast.makeText(applicationContext, "PDF dosyası oluşturuldu..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()

            /* on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()*/
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }

    private fun onBaslat() {
        pdfviewer= findViewById(R.id.pdfviewer)
        btn_whatsapp=findViewById(R.id.btn_whatsapp)
        imageyazdir=findViewById(R.id.imageyazdir)
        btn_mail_gonder=findViewById(R.id.btn_mail_gonder)
        btn_sms_gonder=findViewById(R.id.btn_sms_gonder)
        dilsecimi = intent.getStringExtra("dilsecimi").toString()
        yetki=intent.getStringExtra("yetki").toString()
        firma_id=intent.getStringExtra("firma_id").toString()
        kullnciid=intent.getStringExtra("kullanici_id").toString()
        kadi=intent.getStringExtra("kadi").toString()
        sifrem=intent.getStringExtra("sifre").toString()
    }
    private fun cariGetirBul(firma: String) {
        val urlek="&kadi="+kadi+"&firma_id="+firma+"&cari="+intent.getStringExtra("cari").toString().replace(" ","%20")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=cari_bul_getir_cari"+urlek
        val queue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("onarim",url)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->try {

                val json = response["cariler"] as JSONArray

                for (i in 0 until json.length()) {

                    val item = json.getJSONObject(i)
                    cari.add(item.getString("cari"))
                    if(item.getString("virman").toString()!="")
                    {
                        virman.add(item.getString("virman").toString())
                    }else{
                        virman.add(" ")
                    }

                    tahsilat.add(item.getString("tahsilat").toString())
                    odeme.add(item.getString("odeme").toString())
                    tarih.add(item.getString("tarih").toString())
                    not.add(item.getString("aciklama").toString())
                    odeme_turu.add(item.getString("odeme_turu").toString())
                    gsm=item.getString("gsm").toString()
                    bakiye+=item.getString("tahsilat").toString().toInt()-item.getString("odeme").toString().toInt()
                    //bakiye=item.getString("odeme")



                }


            generatePDF(cari,tahsilat,odeme,virman,bakiye)
            }catch (e:Exception){
               Toast.makeText(this,"HATAAAAA:"+e.message,Toast.LENGTH_LONG).show()
            }


            },{ error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)



    }

}