package com.selpar.pratikhasar.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ClipData
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.adapter.SesAdapter
import com.selpar.pratikhasar.data.SesModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.json.JSONArray
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IstekSikayetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IstekSikayetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var output: String
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false
     val LOG_TAG = "AudioRecordTest"
     val REQUEST_RECORD_AUDIO_PERMISSION = 200
    lateinit var btn_istek_ses:Button
    lateinit var button_stop_recording:Button
    lateinit var button_pause_recording:Button
    lateinit var button_play_recording:Button
    lateinit var ses_kayit_image:ImageView
    private var mMediaPlayer: MediaPlayer? = null

    private var audioFilePath: String? = null
    private var isRecording = false
    lateinit var kadi: String
    lateinit var sifre: String
    lateinit var firma_id: String
    lateinit var plaka: String
    lateinit var ozel_id: String
    lateinit var sesveri: String
    lateinit var firma: String
    var yol = Array<String>(10) { "" }
    var number:Int=0
    lateinit var btniscilikekle:Button
    lateinit var btn_yazi_ekle:Button
    lateinit var btn_iscilik_parca:Button
     var itemList_usta:ArrayList<String> = ArrayList()

    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
    lateinit var btn_yapilacaklar:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_istek_sikayet, container, false)
        val args = this.arguments
        kadi = args?.getString(("kadi")).toString()
        ozel_id = args?.getString("ozel_id").toString()
        sifre = args?.getString("sifre").toString()
        firma_id = args?.getString("firma_id").toString()
        plaka = args?.getString("plaka").toString()
        btn_istek_ses=view.findViewById(R.id.btn_istek_ses)
        btn_yazi_ekle=view.findViewById(R.id.btn_istek_yazi)
        btn_iscilik_parca=view.findViewById(R.id.btn_iscilik_parca)
        //isteksesgetir()
        yaziEkle()
        btn_istek_ses.setOnClickListener {
            isteksesgetir()
        }
        btn_yazi_ekle.setOnClickListener {
          yaziEkle()
        }
        btn_iscilik_parca.setOnClickListener {
                    iscilik()
                }
        return view
    }

    private fun yaziEkle() {
        btn_istek_ses.setBackgroundColor(Color.BLACK)
        btn_yazi_ekle.setBackgroundColor(Color.BLUE)
        btn_iscilik_parca.setBackgroundColor(Color.BLACK)
        var bundlem=Bundle()
        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        val fragobj = IstekTextKayitFragment()
        fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()

            .replace(R.id.fragment_istek_sikayet,  fragobj)
            .commit()
    }

    private fun iscilik() {
        btn_yazi_ekle.setBackgroundColor(Color.BLACK)
        btn_istek_ses.setBackgroundColor(Color.BLACK)
        btn_iscilik_parca.setBackgroundColor(Color.BLUE)
        var bundlem=Bundle()
        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        val fragobj = IscilikParcaEkleFragment()
        fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()

            .replace(R.id.fragment_istek_sikayet,  fragobj)
            .commit()
    }

    private fun isteksesgetir() {

        btn_istek_ses.setBackgroundColor(Color.BLUE)
        btn_yazi_ekle.setBackgroundColor(Color.BLACK)
        btn_iscilik_parca.setBackgroundColor(Color.BLACK)
        var bundlem=Bundle()
        bundlem.putString("plaka",plaka)
        bundlem.putString("ozel_id",ozel_id)
        bundlem.putString("kadi",kadi)
        bundlem.putString("sifre",sifre)
        bundlem.putString("firma_id",firma_id)
        val fragobj = SesEkleFragment()
        fragobj.arguments=bundlem
        //fragobj.setArguments(bundlem)
        val fragmentmaneger=requireActivity().supportFragmentManager.beginTransaction()

            .replace(R.id.fragment_istek_sikayet,  fragobj)
            .commit()
    }
    fun uploadAudioFile(audioFile: File) {
        val url = URL("http://yourserver.com/upload.php")

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true

        val boundary = UUID.randomUUID().toString()
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")

        val outputStream = DataOutputStream(connection.outputStream)

        outputStream.writeBytes("--$boundary\r\n")
        outputStream.writeBytes("Content-Disposition: form-data; name=\"audio_file\"; filename=\"${audioFile.name}\"\r\n")
        outputStream.writeBytes("Content-Type: audio/mp3\r\n\r\n")

        val fileInputStream = FileInputStream(audioFile)
        val buffer = ByteArray(1024)
        var bytesRead = fileInputStream.read(buffer)

        while (bytesRead != -1) {
            outputStream.write(buffer, 0, bytesRead)
            bytesRead = fileInputStream.read(buffer)
        }

        outputStream.writeBytes("\r\n--$boundary--\r\n")

        fileInputStream.close()
        outputStream.flush()
        outputStream.close()

        val responseCode = connection.responseCode
        val responseMessage = connection.responseMessage
        // handle server response as needed
    }

    private fun istekEkle(firmaId: String, plaka: String, ozelId: String, iscilik: String, usta: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(requireContext(),"Ekleme başarılı",Toast.LENGTH_LONG).show()
            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["firma_id"] = firmaId
                params["ozel_id"] = ozelId
                params["plaka"] = plaka
                params["iscilik"] = iscilik
                params["usta"] = usta
                params["tur"] = "istek_iscilik_kaydet"
                return params
            }
        }
        queue.add(postRequest)
    }

    private fun output_olustur() {
        val number = Random.nextInt(1000)
        output = Environment.getExternalStorageDirectory().absolutePath + "/recording$number.3gp"
    }










     companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IstekSikayetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IstekSikayetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}