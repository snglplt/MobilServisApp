package com.selpar.pratikhasar.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.selpar.pratikhasar.R

class ScanCode3Activity :AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    lateinit var btn_geri: ImageView

    @SuppressLint("MissingInflatedId")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code2)
        btn_geri = findViewById(R.id.btn_cikis)
        btn_geri.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
            i.putExtra("yetki", intent.getStringExtra("yetki"))
            i.putExtra("firma_id", intent.getStringExtra("firma_id"))
            i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
            i.putExtra("sifre", intent.getStringExtra("sifre"))
            i.putExtra("kadi", intent.getStringExtra("kadi"))
            i.putExtra("gorev", intent.getStringExtra("gorev"))
            startActivity(i)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScaning()
        }

    }

    private fun startScaning() {
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
             //   Toast.makeText(this, "sonuç:" + it.text, Toast.LENGTH_LONG).show()
                //val i= Intent(this,ScanSonucActivity::class.java)
                // i.putExtra("url",it.text)
                val i = Intent(this, KartAcActivity::class.java)
                i.putExtra("dilsecimi", intent.getStringExtra("dilsecimi"))
                i.putExtra("yetki", intent.getStringExtra("yetki"))
                i.putExtra("firma_id", intent.getStringExtra("firma_id"))
                i.putExtra("kullanici_id", intent.getStringExtra("kullanici_id"))
                i.putExtra("sifre", intent.getStringExtra("sifre"))
                i.putExtra("kadi", intent.getStringExtra("kadi"))
                i.putExtra("gorev", intent.getStringExtra("gorev"))
                //arac bilgileri
                i.putExtra("aracturu", intent.getStringExtra("aracturu"))
                i.putExtra("plaka", intent.getStringExtra("plaka"))
                i.putExtra("marka", intent.getStringExtra("marka"))
                i.putExtra("model", intent.getStringExtra("model"))
                i.putExtra("model_y", intent.getStringExtra("model_y"))
                i.putExtra("modelvrs", intent.getStringExtra("modelvrs"))
                i.putExtra("kasa_tipi", intent.getStringExtra("kasa_tipi"))
                i.putExtra("km", intent.getStringExtra("km"))
                i.putExtra("renk", intent.getStringExtra("renk"))
                i.putExtra("saseno", intent.getStringExtra("saseno"))
                i.putExtra("motorno", intent.getStringExtra("motorno"))
                i.putExtra("konum", intent.getStringExtra("konum"))
                i.putExtra("durum", intent.getStringExtra("durum"))
                i.putExtra("mua", intent.getStringExtra("mua"))
                i.putExtra("tahmini_teslim_tarihi", intent.getStringExtra("tahmini_teslim_tarihi"))
                i.putExtra("egzoz", intent.getStringExtra("egzoz"))
                i.putExtra("trafik", intent.getStringExtra("trafik"))
                i.putExtra("kasko", intent.getStringExtra("kasko"))
                i.putExtra("unvan", intent.getStringExtra("unvan"))
                i.putExtra("gelenplaka", it.text)

                startActivity(i)
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
               // Toast.makeText(this, "sonuç:" + it.message, Toast.LENGTH_LONG).show()


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
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this, "permission gradiend", Toast.LENGTH_LONG).show()
                startScaning()

            } else {
            //    Toast.makeText(this, "permission deniend", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()

        }
    }
}