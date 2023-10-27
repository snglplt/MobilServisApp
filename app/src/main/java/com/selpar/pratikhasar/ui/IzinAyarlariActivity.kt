package com.selpar.pratikhasar.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.storage.StorageManager
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.selpar.pratikhasar.R

class IzinAyarlariActivity : AppCompatActivity() {
    var PERMISSION_CODE=1
    lateinit var switchcamera:Switch
    lateinit var switchdosya:Switch
    lateinit var switchses:Switch
    lateinit var switchsms:Switch
    lateinit var switcharama:Switch
    lateinit var switchdepolama:Switch
    lateinit var switchkonum:Switch
    lateinit var mBack: ImageView
    lateinit var mForward: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_izin_ayarlari)
        onBaslat()
        mBack = findViewById(R.id.back)
        mForward = findViewById(R.id.forward)
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
            finish()
        }
        if (checkSelfPermission(android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                android.Manifest.permission.CAMERA)
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            switchcamera.setChecked(true)
            // openCamera()
        }
        switchcamera?.setOnCheckedChangeListener({ _, isChecked ->
            val message = if (isChecked) {
                val permission = arrayOf(
                    android.Manifest.permission.CAMERA
                )
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                if (checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED){
                    val permission = arrayOf(
                        android.Manifest.permission.CAMERA)
                    switchcamera.setChecked(true)
               }
                    else{
                    switchcamera.setChecked(false)
                    }

            }


        })
        if (checkSelfPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            switchdosya.setChecked(true)
            // openCamera()
        }
        switchdosya?.setOnCheckedChangeListener({ _, isChecked ->
            val message = if (isChecked) {
                val permission = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                    val permission = arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    switchdosya.setChecked(true)
                }
                else{
                    switchdosya.setChecked(false)
                }

            }


        })
        if (checkSelfPermission(
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                android.Manifest.permission.RECORD_AUDIO
            )
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            switchses.setChecked(true)
            // openCamera()
        }
        switchses?.setOnCheckedChangeListener({ _, isChecked ->
            val message = if (isChecked) {
                val permission = arrayOf(
                    android.Manifest.permission.RECORD_AUDIO
                )
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED){
                    val permission = arrayOf(
                        android.Manifest.permission.RECORD_AUDIO)
                    switchses.setChecked(true)
                }
                else{
                    switchses.setChecked(false)
                }

            }


        })
        if (checkSelfPermission(
                android.Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                android.Manifest.permission.SEND_SMS
            )
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            switchsms.setChecked(true)
            // openCamera()
        }
        switchsms?.setOnCheckedChangeListener({ _, isChecked ->
            val message = if (isChecked) {
                val permission = arrayOf(
                    android.Manifest.permission.SEND_SMS
                )
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                if (checkSelfPermission(android.Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED){
                    val permission = arrayOf(
                        android.Manifest.permission.SEND_SMS)
                    switchsms.setChecked(true)
                }
                else{
                    switchsms.setChecked(false)
                }

            }


        })
        if (checkSelfPermission(
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                android.Manifest.permission.CALL_PHONE
            )
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            switcharama.setChecked(true)
            // openCamera()
        }
        switcharama?.setOnCheckedChangeListener({ _, isChecked ->
            val message = if (isChecked) {
                val permission = arrayOf(
                    android.Manifest.permission.CALL_PHONE
                )
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED){
                    val permission = arrayOf(
                        android.Manifest.permission.CALL_PHONE)
                    switcharama.setChecked(true)
                }
                else{
                    switcharama.setChecked(false)
                }

            }


        })
        if (checkSelfPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            switchdepolama.setChecked(true)
            // openCamera()
        }
        switchdepolama?.setOnCheckedChangeListener({ _, isChecked ->
            val message = if (isChecked) {
                val permission = arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                    val permission = arrayOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    switchdepolama.setChecked(true)
                }
                else{
                    switchdepolama.setChecked(false)
                }

            }


        })
        if (checkSelfPermission(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED){
            val permission = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            requestPermissions(permission, PERMISSION_CODE)
        } else {
            switchkonum.setChecked(true)
            // openCamera()
        }
        switchkonum?.setOnCheckedChangeListener({ _, isChecked ->
            val message = if (isChecked) {
                val permission = arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                    val permission = arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                    switchkonum.setChecked(true)
                }
                else{
                    switchkonum.setChecked(false)
                }

            }


        })

    }


    private fun onBaslat() {
        switchcamera=findViewById(R.id.switchcamera)
        switchdosya=findViewById(R.id.switchdosya)
        switchses=findViewById(R.id.switchses)
        switchsms=findViewById(R.id.switchsms)
        switcharama=findViewById(R.id.switcharama)
        switchdepolama=findViewById(R.id.switchdepolama)
        switchkonum=findViewById(R.id.switchkonum)
    }
}