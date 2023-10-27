package com.selpar.pratikhasar.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.provider.Settings
import android.util.Log
import android.widget.*

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.barteksc.pdfviewer.PDFView
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.selpar.pratikhasar.BuildConfig
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.OlayYeriAdapter
import com.selpar.pratikhasar.adapter.PdfDocumentAdapterMekanik
import com.selpar.pratikhasar.adapter.ServisTespitAdapter
import com.selpar.pratikhasar.adapter.ServisTespitGVAdapter
import com.selpar.pratikhasar.data.BitmisModel
import com.selpar.pratikhasar.data.ItemModel
import com.selpar.pratikhasar.data.PdfDocumentAdapter
import com.squareup.picasso.Picasso

import org.json.JSONArray
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL



class PdfOlusturMekanikActivity : AppCompatActivity() {

    // on below line we are creating
    // a variable for our image view.
    lateinit var generatePDFBtn: Button
    lateinit var btn_whatsapp:ImageView
    lateinit var imageyazdir:ImageView
    lateinit var btn_telegram_gonder:ImageView
    lateinit var btn_mail_gonder:ImageView

    // declaring width and height
    // for our PDF file.
    var pageHeight = 1120
    var pageWidth = 792

    // creating a bitmap variable
    // for storing our images
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap
    lateinit var aracbmp: Bitmap
    lateinit var marka: Bitmap
    lateinit var bitmap: Bitmap
    lateinit var qrcode: Bitmap
    lateinit var tespitResim:Bitmap

    // on below line we are creating a
    // constant code for runtime permissions.
    var PERMISSION_CODE = 101
    lateinit var pdfviewer: PDFView
    var stok_no = ArrayList<String>()
    var aciklama = ArrayList<String>()
    var miktar = ArrayList<String>()
    var fiyat = ArrayList<String>()
    var tutar = ArrayList<String>()
    var kdv = ArrayList<String>()
    var toplam=0.00
    var toplam_kdv=0.00
    var tolpam_kdvsiz=0.00
    var qrEncoder: QRCodeWriter= QRCodeWriter()
    var iscilik = ArrayList<String>()
    var usta = ArrayList<String>()
    lateinit var mBack:ImageView
    lateinit var mForward:ImageView

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_olustur_mekanik)
        btn_whatsapp=findViewById(R.id.btn_whatsapp)
        imageyazdir=findViewById(R.id.imageyazdir)
        btn_telegram_gonder=findViewById(R.id.btn_telegram_gonder)
        btn_mail_gonder=findViewById(R.id.btn_mail_gonder)

        // on below line we are initializing our button with its id.
        var image = findViewById(com.selpar.pratikhasar.R.id.img_r) as ImageView
        val markaimage=findViewById(com.selpar.pratikhasar.R.id.img_marka) as ImageView

        Picasso.get().load(intent.getStringExtra("resim")).into(image)
        val bitmapDrawable=image.drawable as BitmapDrawable
        val arababbitmap=bitmapDrawable.bitmap
       // Toast.makeText(this,"yol:  "+"https://selparbulut.com/resim/"+intent.getStringExtra("markaresim"),Toast.LENGTH_LONG).show()
        overridePendingTransition(R.anim.sag, R.anim.sol)
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
        mBack.setColorFilter(ContextCompat.getColor(this, R.color.sitecolor))
        mForward.setColorFilter(ContextCompat.getColor(this, R.color.gray))
        mBack.setOnClickListener {
            val i = Intent(this, AcikKartlarActivity::class.java)
            i.putExtra("plaka", intent.getStringExtra("plaka"))
            i.putExtra("ozel_id", intent.getStringExtra("ozel_id"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            startActivity(i)
        }
        Log.d("YOLLL: ",intent.getStringExtra("markaresim").toString())
        var path=(intent.getStringExtra("markaresim").toString()).replace(".png",".jpg")
        try {
            Picasso.get().load(path).into(image)
        } catch (e: Exception) {
            Log.e("TAG", "Error loading image: ${e.message}")
        }
        Log.d("PATH: ",path)
        Picasso.get().load(intent.getStringExtra("resim").toString()).into(markaimage)
//        val bitmapDrawable2=image.drawable as BitmapDrawable
       // val arababbitmap2=bitmapDrawable2.bitmap
        val imageView = findViewById<ImageView>(R.id.img_r)
        Picasso.get().load(intent.getStringExtra("resim").toString()).into(imageView)

// Get the drawable from the ImageView
        val drawable = imageView.drawable as BitmapDrawable

// Convert the drawable to a bitmap
        val arababbitmap2 = drawable.bitmap


       // Toast.makeText(this,"resim "+intent.getStringExtra("resim").toString(),Toast.LENGTH_LONG).show()
        pdfviewer= findViewById(R.id.pdfviewer)
        // on below line we are initializing our bitmap and scaled bitmap.
        bmp = BitmapFactory.decodeResource(resources, R.drawable.selpar)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 250, 100, false)
        aracbmp= Bitmap.createScaledBitmap(arababbitmap, 100, 50, false)
       // marka= Bitmap.createScaledBitmap(arababbitmap2, 100, 50, false)
        val bitMatrix=qrEncoder.encode("https://pratikhasar.com/raporal.php?pdf=1&ff="+intent.getStringExtra("firma_id")+"ozelnum="+intent.getStringExtra("ozel_id"),BarcodeFormat.QR_CODE,512,512)
        val width=bitMatrix.width
        val height=bitMatrix.height
        val bmp=Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
        for(x in 0 until width){
            for(y in 0 until  height){
                bmp.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
            }

        }
        qrcode= Bitmap.createScaledBitmap(bmp, 100, 100, false)




        // alertSor()
        iscilik_getir()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
            && !Environment.isExternalStorageManager()
        ) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        btn_telegram_gonder.setOnClickListener {
            val dosyaYolu = File("/storage/emulated/0/MEKANIK.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // intent.setPackage("com.whatsapp")

            startActivity(intent)
        }
        btn_mail_gonder.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/MEKANIK.pdf"
            // val path= "backup_rules.xml"
            //  val path= getExternalFilesDir(null)!!.absolutePath.toString()+"/MEKANIK.pdf",
            val file = File(Environment.getExternalStorageDirectory(), "MEKANIK.pdf")
            val dosyaYolu = File("/storage/emulated/0/MEKANIK.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // intent.setPackage("com.whatsapp")

            startActivity(intent)
        }
        btn_whatsapp.setOnClickListener {
            val path= Environment.getExternalStorageDirectory().path+"/MEKANIK.pdf"
            val dosyaYolu = File("/storage/emulated/0/MEKANIK.pdf")
            val dosyaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", dosyaYolu)

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, dosyaUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setPackage("com.whatsapp")

            startActivity(intent)

        }
        imageyazdir.setOnClickListener {
            val printmanager:PrintManager= getSystemService(Context.PRINT_SERVICE) as PrintManager

            val printDocumentAdapter= PdfDocumentAdapterMekanik()
            printmanager.print("Document",printDocumentAdapter,PrintAttributes.Builder().build())


        }




        // on below line we are checking permission


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // on below line we are calling generate
            // PDF method to generate our PDF file.
            // generatePDF(stok_no, aciklama, miktar, fiyat, kdv, tutar)
            // createDocument("selpar")
        }
        // on below line we are adding on click listener for our generate button.

    }

    private fun alertSor() {
        val builder = AlertDialog.Builder(this)
        //builder.setTitle("Dialog Title")
        builder.setMessage("Tespit Resimleri Yazdırılsın Mı?")
        builder.setPositiveButton(getString(R.string.evet)) {
                dialog, which ->
            val resimBoolen=true

            iscilik_getir()
            // Do something when OK button is clicked
        }
        builder.setNegativeButton(getString(R.string.hayir)) { dialog, which ->
            // Do something when Cancel button is clicked

            val resimBoolen=false
            iscilik_getir()
        }
        val dialog = builder.create()
        dialog.show()
    }



    var fileName: String? = null
    private  fun iscilik_getir() {
        val urlsb =
            "&firma_id=" + intent.getStringExtra("firma_id") + "&ozel_id=" + intent.getStringExtra("ozel_id") + "&plaka=" + intent.getStringExtra(
                "plaka"
            )
        var url = "https://pratikhasar.com/netting/mobil.php?tur=iscilik_getir" + urlsb
        Log.d("STOK", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["iscilikler"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        iscilik.add(item.getString("iscilik").toString())
                        usta.add(item.getString("usta").toString())
                        /*(item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt()).toString()*/
                    }
                    kayit_getir(iscilik,usta)
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

    // on below line we are creating a generate PDF method
    // which is use to generate our PDF file.
    @SuppressLint("SuspiciousIndentation")
    private fun kayit_getir(
        iscilik: ArrayList<String>,
        usta: ArrayList<String>

    ) {
        val urlsb =
            "&kadi=" + intent.getStringExtra("kadi") + "&ozel_id=" + intent.getStringExtra("ozel_id") + "&plaka=" + intent.getStringExtra(
                "plaka"
            )
        var url = "https://pratikhasar.com/netting/mobil.php?tur=onarim_kayit_getir" + urlsb
        Log.d("STOK", url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["onarim"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        tolpam_kdvsiz += item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt()
                        toplam += (item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt() +
                                item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt() * item.getString("kdv").toInt() / 100)
                        toplam_kdv += item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt() * item.getString("kdv").toInt() / 100


                        stok_no.add(item.getString("stok_iscilik_no").toString())
                        aciklama.add(item.getString("stok_iscilik_adi"))
                        miktar.add(item.getString("miktar").toString())
                        fiyat.add(item.getString("fiyat").toString())
                        kdv.add("%"+item.getString("kdv").toString())
                        tutar.add(
                            (item.getString("fiyat").toInt() * item.getString("miktar")
                                .toInt()).toString()
                        ).toString()
                        /*(item.getString("fiyat").toFloat() * item.getString("miktar")
                            .toInt()).toString()*/
                    }
                    generatePDF(stok_no,aciklama,miktar,fiyat,kdv,tutar,toplam,toplam_kdv,tolpam_kdvsiz,iscilik,usta)
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



    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun generatePDF(
        stok_no: ArrayList<String>,
        aciklama: ArrayList<String>,
        miktar: ArrayList<String>,
        fiyat: ArrayList<String>,
        kdv: ArrayList<String>,
        tutar: ArrayList<String>,
        toplam: Double,
        toplam_kdv: Double,
        tolpam_kdvsiz: Double,
        iscilik: ArrayList<String>,
        usta: ArrayList<String>
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
        var pdf_baslik:Paint= Paint()
        var arac_bilgileri_renk:Paint= Paint()
        var toplubilgi: Paint = Paint()
        var arac_bilgileri2: Paint = Paint()
        var toplu: Paint = Paint()
        var toplu2: Paint = Paint()
        var km: Paint = Paint()
        var textblue: Paint = Paint()
        var usta_bilgi: Paint = Paint()
        var yapildi: Paint = Paint()



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
        canvas.drawBitmap(scaledbmp, 50F, 0F, paint)
        canvas.drawBitmap(aracbmp, 540F, 0F, paint)
        //canvas.drawBitmap(marka, 500F, 0F, paint)
        canvas.drawBitmap(qrcode, 700F, 0F, paint)
        // canvas.drawBitmap(bitmap, 600F, 0F, paint)

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
        yapildi.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        textblue.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))




        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F
        plaka.textSize=36F
        arac_bilgi.textSize=12F
        km.textSize=12F
        arac_bilgi.color=Color.RED
        arac_bilgileri.textSize=12F
        arac_bilgileri_renk.textSize=12F
        arac_bilgileri_renk.color=Color.GRAY
        arac_sahibi.textSize=15F
        pdf_baslik.textSize=20f
        toplubilgi.textSize=12F
        arac_bilgileri2.textSize=12F
        toplu.textSize=12F
        toplu2.textSize=12F
        textblue.textSize=12f
        usta_bilgi.textSize=12f
        yapildi.textSize=12f
        // qrOlustur()





        // below line is sued for setting color
        // of our text inside our PDF file.
        //title.setColor(ContextCompat.getColor(this, R.color.green))

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.

        canvas.drawText(intent.getStringExtra("plaka").toString(),520F,100F,plaka)
        canvas.drawText("KABUL KARTI",330F,30F,pdf_baslik)
        canvas.drawText("HESAP DÖKÜMÜ",310F,60F,pdf_baslik)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
//        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        title.textSize = 15F

        // below line is used for setting
        // our text to center of PDF.
        val url = URL(intent.getStringExtra("resim").toString())
       // Toast.makeText(this,url.toString(),Toast.LENGTH_LONG).show()

        /* val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
         val stream = ByteArrayOutputStream()
         image.compress(Bitmap.CompressFormat.PNG, 100, stream)
         val byteArray = stream.toByteArray()

         val logo: Image = Image.getInstance(byteArray)

         // Logonun konumunu sağ yapıyoruz

         // Logonun konumunu sağ yapıyoruz
         logo.setAlignment(Element.ALIGN_RIGHT)*/

        // Son olarak logoyu pdf ekliyoruz

        // Son olarak logoyu pdf ekliyoruz
        /*var bitmap2: Bitmap? = null
        val inputStream: InputStream
        try {
            inputStream = URL(url.toString()).openStream()
            bitmap2 = BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
       canvas.drawBitmap(bitmap2!!, 600F, 20F, paint)*/


        // Toast.makeText(this,intent.getStringExtra("resim"), Toast.LENGTH_LONG).show()
/*
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
//        connection.connect()
        val input: InputStream = connection.inputStream
        val myBitmap = BitmapFactory.decodeStream(input)
        canvas.drawBitmap(myBitmap, 600F, 20F, paint)*/


        canvas.drawText("ARAÇ BİLGİLERİ",450F,150F,arac_bilgi)
        canvas.drawText("SAHİBİ BİLGİLERİ",15f,150F,arac_bilgi)
        canvas.drawLine(310f,165f,780f,165f,paint)
        canvas.drawText("Kabul No",315F,180f,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("kabulnom").toString(),380f,180f,arac_bilgileri)
        canvas.drawLine(310f,185f,780f,185f,paint)
        canvas.drawText("Gir. Tarihi",315F,200F,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("muabit").toString(),380f,200F,arac_bilgileri)
        canvas.drawLine(310f,205f,780f,205f,paint)

        canvas.drawText("Çık. Tarihi",315F,220F,arac_bilgileri)
        canvas.drawLine(310f,225f,780f,225f,paint)

        canvas.drawText("Sase No",315F,240F,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("saseno").toString(),380f,240F,arac_bilgileri)
        canvas.drawLine(310f,245F,780f,245F,paint)

        canvas.drawText("Motor No",315F,260F,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("motorno").toString(),380f,260F,arac_bilgileri)
        canvas.drawLine(310f,265f,780f,265f,paint)
        canvas.drawText("KM:",315f,280f,km)
        canvas.drawText(intent.getStringExtra("km").toString(),380f,280f,km)

        canvas.drawLine(375f,165f,375f,285f,paint)


        canvas.drawText("Markası",550f,180f,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("marka").toString(),600f,180f,arac_bilgileri)
        canvas.drawText("Modeli",550F,200f,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("model").toString(),600f,200f,arac_bilgileri)
        canvas.drawText("Mod.Yıl",550F,220f,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("modelyili").toString(),600f,220f,arac_bilgileri)
        canvas.drawText("Mod.Ver",550F,240f,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("modelvers").toString(),600f,240f,arac_bilgileri)
        canvas.drawText("Rengi",550F,260f,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("renk").toString(),600f,260f,arac_bilgileri)
        canvas.drawLine(310f,285f,780f,285f,paint)
        canvas.drawText("Ykt. Tur",550F,280f,arac_bilgileri)
        canvas.drawText(intent.getStringExtra("yakitturu").toString(),600f,280f,arac_bilgileri)

        canvas.drawLine(598f,165f,598f,285f,paint)
        //canvas.drawLine(545f,165f,545f,285f,paint)
       usta_bilgi.textAlign=Paint.Align.LEFT
       yapildi.textAlign=Paint.Align.RIGHT
        if(intent.getStringExtra("unvan").toString().length>35){
            canvas.drawText(intent.getStringExtra("unvan").toString().substring(0,35),25f,170F,km)

        }else {
            canvas.drawText(intent.getStringExtra("unvan").toString(), 25f, 170F, km)
        }
        if(intent.getStringExtra("adres").toString().length>150) {
            var adres=intent.getStringExtra("adres").toString().substring(0,35)
            var adres2=intent.getStringExtra("adres").toString().substring(35,70)
            var adres3=intent.getStringExtra("adres").toString().substring(70,105)
            var adres4=intent.getStringExtra("adres").toString().substring(105,140)
            var adres5=intent.getStringExtra("adres").toString().substring(140,intent.getStringExtra("adres").toString().length)
            canvas.drawText(adres, 25f, 190f, arac_bilgileri)
            canvas.drawText(adres2, 25f, 210f, arac_bilgileri)
            canvas.drawText(adres3, 25f, 230f, arac_bilgileri)
            canvas.drawText(adres4, 25f, 250f, arac_bilgileri)
            canvas.drawText(adres3, 25f, 270f, arac_bilgileri)
            canvas.drawText(intent.getStringExtra("il").toString()+"/"+intent.getStringExtra("ilce"),25f,290F,arac_bilgileri)

        }
        else if(intent.getStringExtra("adres").toString().length>100){
            var adres=intent.getStringExtra("adres").toString().substring(0,35)
            var adres2=intent.getStringExtra("adres").toString().substring(35,70)
            var adres3=intent.getStringExtra("adres").toString().substring(70,intent.getStringExtra("adres").toString().length)
            canvas.drawText(adres, 25f, 190f, arac_bilgileri)
            canvas.drawText(adres2, 25f, 210f, arac_bilgileri)
            canvas.drawText(adres3, 25f, 230f, arac_bilgileri)
            canvas.drawText(intent.getStringExtra("il").toString()+"/"+intent.getStringExtra("ilce"),25f,250f,arac_bilgileri)

        }
        else if(intent.getStringExtra("adres").toString().length>50){
            var adres=intent.getStringExtra("adres").toString().substring(0,35)
            var adres2=intent.getStringExtra("adres").toString().substring(35,intent.getStringExtra("adres").toString().length)
            canvas.drawText(adres, 25f, 190f, arac_bilgileri)
            canvas.drawText(adres2, 25f, 220f, arac_bilgileri)
            canvas.drawText(intent.getStringExtra("il").toString()+"/"+intent.getStringExtra("ilce"),25f,250f,arac_bilgileri)

        }
        else{
            canvas.drawText(intent.getStringExtra("adres").toString(),25f,190f,arac_bilgileri)
            canvas.drawText(intent.getStringExtra("il").toString()+"/"+intent.getStringExtra("ilce"),25f,210f,arac_bilgileri)


        }
        canvas.drawText("İSTEK VE ŞİKAYETLER",15f,320F,arac_bilgi)
        canvas.drawText("USTA",680f,320F,arac_bilgi)
        var x_iscilik=15f
        var y_iscilik=340f
        for (i in 0 until iscilik.size){
            var istek=iscilik[i]
            if(istek.length>200)
            {
            canvas.drawText(istek.substring(0,130),x_iscilik,y_iscilik,arac_bilgileri)
            canvas.drawText(istek.substring(130,265),x_iscilik,y_iscilik+20,arac_bilgileri)
            canvas.drawText(istek.substring(265,istek.length),x_iscilik,y_iscilik+40,arac_bilgileri)
            canvas.drawText( "Yapacak usta: "+this.usta[i]+"| Yaptı [ ] Yapmadı [ ]",x_iscilik,y_iscilik+60f,usta_bilgi)

            }
        else if(istek.length>100){
            canvas.drawText(istek.substring(0,130),x_iscilik,y_iscilik,arac_bilgileri)
            canvas.drawText(istek.substring(130,265),x_iscilik,y_iscilik+20,arac_bilgileri)
            canvas.drawText(istek.substring(265,istek.length),x_iscilik,y_iscilik+40,arac_bilgileri)
            canvas.drawText( "Yapacak usta: "+this.usta[i]+"| Yaptı [ ] Yapmadı [ ]",x_iscilik,y_iscilik+60f,usta_bilgi)
        }
        else if(100>=istek.length || istek.length>=50){
            canvas.drawLine(10f,y_iscilik-15,780f,y_iscilik-15,paint)
            canvas.drawText(istek.substring(0,istek.length),x_iscilik,y_iscilik,arac_bilgileri)
            if(this.usta[i].length>14) {
                canvas.drawText(this.usta[i].substring(0,13), 620F, y_iscilik, usta_bilgi)
            }
            else{
                canvas.drawText(this.usta[i], 620F, y_iscilik, usta_bilgi)

            }
            canvas.drawText( "| Yapıldı [  ]",775F,y_iscilik,yapildi)
            canvas.drawLine(10f,y_iscilik+5,780f,y_iscilik+5,paint)
            canvas.drawLine(610F,y_iscilik-15,610F,y_iscilik+5,paint)
        }
        else{
            canvas.drawText(istek.substring(0,istek.length),x_iscilik,y_iscilik,arac_bilgileri)
            canvas.drawText( "Yapacak usta: "+this.usta[i]+"| Yaptı [ ] Yapmadı [ ]",x_iscilik,y_iscilik+20f,usta_bilgi)



            }
            y_iscilik+=20
        }
        //Toast.makeText(this,intent.getStringExtra("resim"), Toast.LENGTH_LONG).show()
        // after adding all attributes to our
        // PDF file we will be finishing our page.

        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD))

        paint.textSize=50F
        // canvas.drawLine(670f,530F,670f,740f,paint)


        //toplam tutar

        var x_stok=15f
        var y_stok=y_iscilik+40
        var x_aciklama=140f
        var y_aciklama=y_iscilik+40
        var x_miktar=510f
        var y_miktar=y_iscilik+40
        var x_fiyat=570f
        var y_fiyat=y_iscilik+40
        var x_tutar=670f
        var y_tutar=y_iscilik+40
        var x_kdv=640f
        var y_kdv=y_iscilik+40
        var x_line=10F
        var y_line=y_iscilik+45
        //green line
        canvas.drawText("Stok No",15f,y_iscilik+20,arac_bilgileri)
        canvas.drawText("Açıklama",140F,y_iscilik+20,arac_bilgileri)
        canvas.drawText("Miktar",510f,y_iscilik+20,arac_bilgileri)
        canvas.drawText("Fiyat",575f,y_iscilik+20,arac_bilgileri)
        canvas.drawText("Kdv",640f,y_iscilik+20,arac_bilgileri)
        canvas.drawText("Tutar",700f,y_iscilik+20,arac_bilgileri)
        canvas.drawLine(10F,y_iscilik+10,780f,y_iscilik+10,paint)
        canvas.drawLine(10F,y_iscilik+25,780f,y_iscilik+25,paint)

        arac_bilgileri.textAlign = Paint.Align.LEFT
        toplu.textAlign = Paint.Align.RIGHT
        toplu2.textAlign = Paint.Align.RIGHT
        var binler = 1000
        var yuzler = 1000
        var ondalik = 18 % 100
        for (i in 0 until this.stok_no.size){

            canvas.drawText(this.stok_no[i],x_stok,y_stok,arac_bilgileri2)
            canvas.drawText(this.aciklama[i],x_aciklama,y_aciklama,arac_bilgileri2)
            canvas.drawText(this.miktar[i],x_miktar+20,y_miktar,arac_bilgileri)
            binler = this.fiyat[i].toInt() / 1000
            yuzler = this.fiyat[i].toInt() % 1000
            ondalik = (this.fiyat[i].toInt() * 100).toInt() % 100
            if(binler==0){
                canvas.drawText(String.format("%.2f",this.fiyat[i].toFloat()).replace(".",","),x_fiyat+40,y_fiyat,toplu2)

            }else
                canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_fiyat+40f,y_fiyat,toplu2)
            binler = this.tutar[i].toInt() / 1000
            yuzler = this.tutar[i].toInt() % 1000
            ondalik = (this.tutar[i].toInt() * 100).toInt() % 100
            if(binler==0){
                canvas.drawText(String.format("%.2f",this.tutar[i].toFloat()).replace(".",","),x_tutar+100F,y_tutar,toplu2)

            }else
                canvas.drawText(String.format("%,d.%03d,%02d", binler, yuzler, ondalik),x_tutar+100F,y_tutar,toplu2)

          //  canvas.drawText(String.format("%.2f",this.tutar[i].toFloat()),x_tutar+100f,y_tutar,toplu2)
            canvas.drawText(this.kdv[i],x_kdv,y_kdv,arac_bilgileri2)
            canvas.drawLine(x_line,y_line,x_line+770f,y_line,paint)
            y_stok+=20f
            y_aciklama+=20f
            y_miktar+=20f
            y_fiyat+=20f
            y_kdv+=20f
            y_tutar+=20f
            y_line+=20f
        }
        //canvas.drawLine(10F,530F,780F,530F,paint)
        //canvas.drawLine(500F,450F,500F,y_line-20f,paint)
        canvas.drawLine(10F,300f,780F,300f,paint)
        //canvas.drawLine(570f,530F,570f,650F,paint)
        canvas.drawLine(500F,y_iscilik+10,500F,y_line-20f,paint)

        canvas.drawLine(630f,y_iscilik+15,630f,y_line+40,paint)
        // canvas.drawLine(10F,650f,780f,650F,paint)
        canvas.drawLine(780f,y_iscilik+15,780f,y_line+40,paint)
        //canvas.drawLine(10F,450f,780f,450f,paint)
       // canvas.drawLine(10F,465f,780f,465f,paint)
        var thousands = toplam.toInt() / 1000
        var hundreds = toplam.toInt() % 1000
        var decimals = (toplam * 100).toInt() % 100
        if(thousands==0){
            canvas.drawText(String.format("%.2f",toplam.toFloat()),x_kdv+130f,y_line+35f,toplu)

        }
        else
            canvas.drawText(String.format("%,d.%03d,%02d", thousands, hundreds, decimals),x_kdv+130f,y_line+35,toplu)

        thousands = tolpam_kdvsiz.toInt() / 1000
        hundreds = tolpam_kdvsiz.toInt() % 1000
        decimals = (tolpam_kdvsiz * 100).toInt() % 100
        if(thousands==0){
            canvas.drawText(String.format("%.2f",tolpam_kdvsiz.toFloat()),x_kdv+130f,y_line-5f,toplu)

        }
        else
            canvas.drawText(String.format("%,d.%03d,%02d", thousands, hundreds, decimals),x_kdv+130f,y_line-5f,toplu)

        thousands = toplam_kdv.toInt() / 1000
        hundreds = toplam_kdv.toInt() % 1000
        decimals = (toplam_kdv * 100).toInt() % 100
        if(thousands==0){
            canvas.drawText(String.format("%.2f",toplam_kdv.toFloat()).replace(".",","),x_kdv+130f,y_line+15f,toplu)

        }
        else
            canvas.drawText(String.format("%,d.%03d,%02d", thousands, hundreds, decimals),x_kdv+130f,y_line+15f,toplu)
        //tablo
        canvas.drawLine(780f,130f,780f,y_line+40F,paint)
        canvas.drawLine(10F,130f,780f,130f,paint)
        //canvas.drawLine(10F,400f,780f,400f,paint)
        canvas.drawLine(310f,130f,310f,300f,paint)
        canvas.drawLine(130f,y_iscilik+10,130f,y_line+40f,paint)
       // canvas.drawLine(x_kdv+35f,450f,x_kdv+35f,y_line+40F,paint)
        canvas.drawLine(x_kdv-10f,y_iscilik+10,x_kdv-10f,y_line+40F,paint)
        canvas.drawLine(x_kdv+45f,y_iscilik+10,x_kdv+45f,y_line+40F,paint)
        canvas.drawLine(x_miktar+40,y_iscilik+10,x_miktar+40,y_line-20F,paint)

        //tablo
        canvas.drawText("Toplam",x_kdv-8f,y_line-5,toplubilgi)
     //   canvas.drawLine(500f,650f,500f,y_line+40f,paint)
        // canvas.drawLine(620f,650f,620f,y_line+40F,paint)
        //canvas.drawLine(780f,650f,780f,y_line+60f,paint)
        canvas.drawText("KDV",x_kdv-8f,y_line+15,toplubilgi)
        canvas.drawLine(x_kdv-10f,y_line,780f,y_line,paint)
        canvas.drawText("Öd.Tut.",x_kdv-8f,y_line+35F,toplubilgi)
        canvas.drawLine(x_kdv-10f,y_line+20f,780f,y_line+20f,paint)
        canvas.drawLine(10F,y_line+40F,780f,y_line+40f,paint)

        canvas.drawLine(10F,130f,10F,y_line+40F,paint)
        //canvas.drawLine(x_fiyat-10f,x_miktar+50f,x_fiyat-10f,y_line+40F,paint)
        //canvas.drawLine(x_tutar-10f,x_kdv+50f,x_tutar-10f,y_line+60f,paint)
        canvas.drawLine(780f,130f,780f,y_line+40f,paint)
        //canvas.drawLine(x_fiyat,y_miktar,x_fiyat,y_line+40f,paint)
        //SİGORTA BİLGİLERİ yok
        var image:ImageView
        image=findViewById<ImageView>(R.id.image_tespit)

        var yol:String="https://pratikhasar.com/tespit/1/202300000009/6452551ed2854.png"
        if(y_line+20>=1000){
            pdfDocument.finishPage(myPage)
            var myPageInfo2: PdfDocument.PageInfo? =
                PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

            // below line is used for setting
            // start page for our PDF file.
            var myPage2: PdfDocument.Page = pdfDocument.startPage(myPageInfo2)


            // creating a variable for canvas
            // from our page of PDF.
            var canvas2: Canvas = myPage2.canvas
            canvas2.drawText("Servise Getiren", 10F, 60f, arac_bilgileri)
            canvas2.drawText(intent.getStringExtra("unvan").toString(), 10F, 80f, arac_bilgileri)
            canvas2.drawText(intent.getStringExtra("tel").toString(), 10F, 100f, arac_bilgileri)
            //canvas.drawLine(130f,530f,130f,y_line+40F,paint)
            canvas2.drawText("Teslim Eden ", x_miktar - 200, 60f, arac_bilgileri)
            canvas2.drawText("Teslim Alan ", x_miktar, 60f, arac_bilgileri)
            canvas2.drawText("İş bu rapor ", 70f, 170f, arac_bilgileri)
            textblue.color = Color.BLUE
            canvas2.drawText("www.pratikhasar.com", 140f, 170f, textblue)
            canvas2.drawText(
                "yazılımından bilgi amaçlı olarak alınmıştır. Kanunen bir değeri yoktur.",
                280f,
                170f,
                arac_bilgileri
            )

            pdfDocument.finishPage(myPage2)
        }
        else {
            canvas.drawText("Servise Getiren", 10F, 1000f, arac_bilgileri)
            canvas.drawText(intent.getStringExtra("unvan").toString(), 10F, 1020f, arac_bilgileri)
            canvas.drawText(intent.getStringExtra("tel").toString(), 10F, 1040f, arac_bilgileri)
            //canvas.drawLine(130f,530f,130f,y_line+40F,paint)
            canvas.drawText("Teslim Eden ", x_miktar - 200, 1000f, arac_bilgileri)
            canvas.drawText("Teslim Alan ", x_miktar, 1000f, arac_bilgileri)
            canvas.drawText("İş bu rapor ", 70f, 1110f, arac_bilgileri)
            textblue.color = Color.BLUE
            canvas.drawText("www.pratikhasar.com", 140f, 1110f, textblue)
            canvas.drawText(
                "yazılımından bilgi amaçlı olarak alınmıştır. Kanunen bir değeri yoktur.",
                280f,
                1110f,
                arac_bilgileri
            )


            pdfDocument.finishPage(myPage)

        }




        // below line is used to set the name of
        // our PDF file and its path.
        val file: File = File(Environment.getExternalStorageDirectory(), "MEKANIK.pdf")
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




    // on below line we are creating a function to request permission.
    fun requestPermission() {

        // on below line we are requesting read and write to
        // storage permission for our application.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PERMISSION_CODE
        )
    }

    // on below line we are calling
    // on request permission result.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // on below line we are checking if the
        // request code is equal to permission code.
        if (requestCode == PERMISSION_CODE) {

            // on below line we are checking if result size is > 0
            if (grantResults.size > 0) {

                // on below line we are checking
                // if both the permissions are granted.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {

                    // if permissions are granted we are displaying a toast message.
                  //  Toast.makeText(this, "İzin verildi..", Toast.LENGTH_SHORT).show()

                } else {
                    finish()
                }
            }
        }}


/*
    private fun resimGetir(): ArrayList<String> {
        var yol:ArrayList<String> = ArrayList()
        val urlsb ="&kadi=" + intent.getStringExtra("kadi") +"&firma_id="+intent.getStringExtra("firma_id")+"&kabulnom="+intent.getStringExtra("kabulnom")+"&ozel_id="+ intent.getStringExtra("ozel_id")
        var url = "https://pratikhasar.com/netting/mobil.php?tur=tespit_resim_bul"+urlsb
        Log.d("KABULBULLL: ",url)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            {  response ->
                try{
                    val json = response["resim"] as JSONArray
                    val courseModelArrayList: ArrayList<BitmisModel> = ArrayList<BitmisModel>()

                    val itemList: java.util.ArrayList<ItemModel> = java.util.ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        yol.add("https://pratikhasar.com/tespit/" +intent.getStringExtra("firma_id")+"/"+intent.getStringExtra("kabulnom")+"/"+item.getString("yol"))
                        //Log.d("TESPİT",yol)
                        // yol[i] ="https://pratikhasar.com/netting/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        //yol[i] ="https://pratikhasar.com/netting/"+item.getString("yol")


                        //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                    }}catch (e:Exception){}





            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
            }
        )
        queue.add(request)
        return yol
    }
*/
}



