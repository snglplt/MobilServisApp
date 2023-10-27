package com.selpar.pratikhasar.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

import com.selpar.pratikhasar.R
import java.io.IOException

class ScanCodeActivity : AppCompatActivity() {
    private lateinit var codeScanner:CodeScanner
    lateinit var btn_geri:ImageView

      @SuppressLint("MissingInflatedId")
      public override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_scan_code)
          btn_geri=findViewById(R.id.btn_cikis)
          btn_geri.setOnClickListener {
              val i=Intent(this,HomeActivity::class.java)
              i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
              i.putExtra("yetki", intent.getStringExtra("yetki"))
              i.putExtra("firma_id", intent.getStringExtra("firma_id"))
              i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
              i.putExtra("sifre", intent.getStringExtra("sifre"))
              i.putExtra("kadi", intent.getStringExtra("kadi"))
              i.putExtra("gorev", intent.getStringExtra("gorev"))
              startActivity(i)
          }
          if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==
              PackageManager.PERMISSION_DENIED){
              ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
          }else{
              startScaning()
          }

     }

    private fun startScaning() {
        val scannerView=findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner= CodeScanner(this,scannerView)
        codeScanner.camera=CodeScanner.CAMERA_BACK
        codeScanner.formats=CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode=AutoFocusMode.SAFE
        codeScanner.scanMode=ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled=true
        codeScanner.isFlashEnabled=false
        codeScanner.decodeCallback= DecodeCallback {
            runOnUiThread{
            //    Toast.makeText(this,"sonuç:"+it.text,Toast.LENGTH_LONG).show()
                //val i= Intent(this,ScanSonucActivity::class.java)
               // i.putExtra("url",it.text)
                val i=Intent(this,HomeActivity::class.java)
                i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
                i.putExtra("yetki", intent.getStringExtra("yetki"))
                i.putExtra("firma_id", intent.getStringExtra("firma_id"))
                i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
                i.putExtra("sifre", intent.getStringExtra("sifre"))
                i.putExtra("kadi", intent.getStringExtra("kadi"))
                i.putExtra("gorev", intent.getStringExtra("gorev"))
                i.putExtra("gelenplaka", it.text)
                startActivity(i)
                startActivity(i)
            }
        }
        codeScanner.errorCallback= ErrorCallback {
            runOnUiThread{
               // Toast.makeText(this,"sonuç:"+it.message,Toast.LENGTH_LONG).show()


            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==123){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
              //  Toast.makeText(this,"permission gradiend",Toast.LENGTH_LONG).show()
                startScaning()

            }
            else{
             //   Toast.makeText(this,"permission deniend",Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner.startPreview()

        }
    }

    override fun onPause() {
        super.onPause()
        if(::codeScanner.isInitialized){
            codeScanner.releaseResources()

        }
    }
/*+it

private fun initViews() {
txtBarcodeValue = findViewById(R.id.txtBarcodeValue)
surfaceView = findViewById(R.id.surfaceView)
btnAction = findViewById(R.id.btnAction)
btnAction!!.setOnClickListener {
    /*
    if (intentData.length > 0) {
        if (isEmail) ContextCompat.startActivity(
            Intent(
                this@ScannedBarcodeActivity,
                EmailActivity::class.java
            ).putExtra("email_address", intentData)
        ) else {
            ContextCompat.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(intentData)
                )
            )
        }
    }*/
}
}

private fun initialiseDetectorsAndSources() {
Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT)
    .show()
barcodeDetector = BarcodeDetector.Builder(this)
    .setBarcodeFormats(Barcode.ALL_FORMATS)
    .build()
/*
cameraSource = BarcodeScannerOptions.Builder(this, barcodeDetector)
    .setRequestedPreviewSize(1920, 1080)
    .setAutoFocusEnabled(true) //you should add this feature
    .build()*/
surfaceView?.getHolder()?.addCallback(object : SurfaceHolder.Callback {
    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            if (ActivityCompat.checkSelfPermission(
                    this@ScanCodeActivity,
                    Manifest.permission.CAMERA
                ) === PackageManager.PERMISSION_GRANTED
            ) {
                cameraSource!!.start(surfaceView!!.getHolder())
            } else {
                ActivityCompat.requestPermissions(
                    this@ScanCodeActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int
    ) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        cameraSource!!.stop()
    }
})
barcodeDetector?.setProcessor(object : Detector.Processor<Barcode?> {
    override fun release() {
        Toast.makeText(
            getApplicationContext(),
            "To prevent memory leaks barcode scanner has been stopped",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun receiveDetections(detections: Detector.Detections<Barcode?>) {
        val barcodes: SparseArray<Barcode?> = detections.getDetectedItems()
        if (barcodes.size() != 0) {
            txtBarcodeValue!!.post(Runnable {
                if (barcodes.valueAt(0)!!.email != null) {
                    txtBarcodeValue!!.removeCallbacks(null)
                    intentData = barcodes.valueAt(0)!!.email.address
                    txtBarcodeValue!!.setText(intentData)
                    isEmail = true
                    btnAction!!.setText("ADD CONTENT TO THE MAIL")
                } else {
                    isEmail = false
                    btnAction!!.setText("LAUNCH URL")
                    intentData = barcodes.valueAt(0)!!.displayValue
                    txtBarcodeValue!!.setText(intentData)
                }
            })
        }
    }
})
}
*/

}
