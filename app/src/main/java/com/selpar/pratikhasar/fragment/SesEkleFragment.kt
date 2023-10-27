package com.selpar.pratikhasar.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ClipData
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.selpar.pratikhasar.data.DataPart
import com.selpar.pratikhasar.data.SesModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import okhttp3.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SesEkleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SesEkleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var output: String
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false
    val LOG_TAG = "AudioRecordTest"
    val REQUEST_RECORD_AUDIO_PERMISSION = 200
    lateinit var btn_ses_kaydet: Button
    lateinit var button_stop_recording: Button
    lateinit var button_pause_recording: Button
    lateinit var button_play_recording: Button
    lateinit var ses_kayit_image: ImageView
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
    var number: Int = 0
    lateinit var btniscilikekle: Button
    var itemList_usta: ArrayList<String> = ArrayList()

    private lateinit var newRecyclerviewm: RecyclerView
    private lateinit var newArrayList: ArrayList<ClipData.Item>
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
        val view = inflater.inflate(R.layout.fragment_ses_ekle, container, false)
        val args = this.arguments
        kadi = args?.getString(("kadi")).toString()
        ozel_id = args?.getString("ozel_id").toString()
        sifre = args?.getString("sifre").toString()
        firma_id = args?.getString("firma_id").toString()
        plaka = args?.getString("plaka").toString()
        ses_kayit_image = view.findViewById(R.id.ses_kayit_image)
        button_pause_recording = view.findViewById(R.id.button_pause_recording)
        button_pause_recording = view.findViewById(R.id.button_pause_recording)
        button_stop_recording = view.findViewById(R.id.button_stop_recording)
        button_play_recording = view.findViewById(R.id.button_play_recording)
        newRecyclerviewm = view.findViewById(R.id.id_rc_ses)
        //btniscilikekle=view.findViewById(R.id.btniscilikekle)
        alert()
        mediaRecorder = MediaRecorder()
        number = Random.nextInt(1000)
        output = Environment.getExternalStorageDirectory().absolutePath + "/recording$number.3gp"

        sesveri = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"

        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        //  mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder!!.setOutputFile(output)
        firmaGetir(kadi, ozel_id)

        button_stop_recording.isEnabled = false
        ses_kayit_image.setOnClickListener {
            button_stop_recording.isEnabled = true
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
            } else {
                startRecording(output)
            }
        }
        button_play_recording.setOnClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(output)
            mp.prepare()
            mp.start()
        }
        button_stop_recording.setOnClickListener {
            stopRecording()
        }

        button_pause_recording.setOnClickListener {
            pauseRecording()
        }
        return view
    }

    private fun alert() {
        val mBuilder =
            AlertDialog.Builder(requireContext()).setTitle("Ses kaydetmek için mikrofona dokunun..")
                .setPositiveButton("Tamam") { dialogInterface, i ->


                }.show()
    }

    private fun istekEkle(
        firmaId: String,
        plaka: String,
        ozelId: String,
        iscilik: String,
        usta: String
    ) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(requireContext(), "Ekleme başarılı", Toast.LENGTH_LONG).show()
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

    private fun firmaGetir(kadi: String, ozelId: String) {

        val urlsb = "&kadi=" + kadi
        var url = "https://pratikhasar.com/netting/mobil.php?tur=firma_id_getir" + urlsb
        Log.d("RESİMYOL", url)
        val queue: RequestQueue = Volley.newRequestQueue(this.requireContext())
        //val requestBody = "tur=deneme"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["firma_id"] as JSONArray
                    val itemList: ArrayList<SesModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        firma = item.getString("firma_id")
                        Log.d("FIRMA22: ", firma)
                        evrak_ses_getir(kadi, firma)

                        Log.d("RESIMGETTTT", yol[i])
                        val itemModel = SesModel(
                            yol[i], item.getString("aciklama"), output
                        )


                        itemList.add(itemModel)


                    }
                    val adapter =
                        SesAdapter(itemList)

                    newRecyclerviewm.adapter = adapter
                    newRecyclerviewm.layoutManager = LinearLayoutManager(context)
                    newRecyclerviewm.setHasFixedSize(false)
                    newArrayList = arrayListOf<ClipData.Item>()
                    context?.let {
                        getUserData(it)

                    }
                } catch (e: Exception) {
                }
            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                Toast.makeText(context, "GETİRME BAŞARISIZ", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        queue.add(request)

    }

    private fun evrak_ses_getir(kadi: String, firma: String) {
        val urlsb = "&kadi=" + kadi + "&ozel_id=" + ozel_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=kabul_no_getir" + urlsb
        Log.d("KABULNOOOO. ", url)
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["bilgi"] as JSONArray
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)
                        val kabulnom = item.getString("kabulnom").toString()
                        Log.d("kabulnom: ", kabulnom)
                        evrak_ses_bul(kadi, firma, kabulnom, ozel_id)

                    }
                } catch (e: Exception) {
                }


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                Toast.makeText(context, "Fail to get response", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        queue.add(request)

    }

    private fun evrak_ses_bul(kadi: String, firma: String, kabulnom: String, ozelId: String) {
        val urlsb =
            "&kadi=" + kadi + "&firma_id=" + firma + "&kabulnom=" + kabulnom + "&ozel_id=" + this.ozel_id
        var url = "https://pratikhasar.com/netting/mobil.php?tur=evrak_ses_bul" + urlsb
        Log.d("KABULBULLL: ", url)
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val json = response["ses"] as JSONArray

                    val itemList: ArrayList<SesModel> = ArrayList()
                    for (i in 0 until json.length()) {
                        val item = json.getJSONObject(i)

                        yol[i] =
                            "https://pratikhasar.com/ses/" + firma + "/" + kabulnom + "/" + item.getString(
                                "yol"
                            )
                        // yol[i] ="https://pratikhasar.com/netting/evrak/" +firma+"/"+kabulnom+"/"+item.getString("yol")
                        //yol[i] ="https://pratikhasar.com/netting/"+item.getString("yol")

                        Log.d("YOLLL: ", yol[i])


                        val itemModel = SesModel(
                            yol[i], item.getString("aciklama"), output
                        )


                        itemList.add(itemModel)
                    }
                    val adapter =
                        SesAdapter(itemList)

                    newRecyclerviewm.adapter = adapter
                    newRecyclerviewm.layoutManager = LinearLayoutManager(context)
                    newRecyclerviewm.setHasFixedSize(false)
                    newArrayList = arrayListOf<ClipData.Item>()
                    //val kabulnom = item.getString("kabulnom").toString()
                    // Log.d("kabulnom: ", kabulnom)
                    //  evrak_resim_bul(kadi,firma,kabulnom)
                    // evrak_resim_getir(kadi,firma)
                    //Picasso.get().load("https://pratikhasar.com"+item.getString("yol")).into(img1)
                    context?.let {
                        getUserData(it)

                    }
                } catch (e: Exception) {
                }


            }, { error ->
                Log.e("TAG", "RESPONSE IS $error")
                // in this case we are simply displaying a toast message.
                /* Toast.makeText(context, "Resim Bulmadı", Toast.LENGTH_SHORT)
                      .show()*/
            }
        )
        queue.add(request)
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                // If you want to add a background, a text, an icon
                //  as the user swipes, this is where to start decorating
                //  I will link you to a library I created for that below
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    .addSwipeLeftLabel("SİL")
                    .addSwipeRightLabel("OYNAT")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            @SuppressLint("ResourceAsColor", "ResourceType")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewHolder.itemId
                        val txt_image =
                            viewHolder.itemView.findViewById<TextView>(R.id.txt_ses).text
                        var yol = txt_image.toString()
                            .replace("https://pratikhasar.com/ses/$firma/$kabulnom/", "")

                        val donustur_yol = txt_image.subSequence(45, txt_image.length).toString()
                        //val donustur_yol=txt_image.subSequence(32, txt_image.length).toString()
                        Log.d("DONUSTUR: ", yol)
                        Toast.makeText(requireContext(), "dONUSTU:" + yol, Toast.LENGTH_SHORT)
                            .show()


                        val builder =
                            AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                        builder.setTitle("Kayıt silinsin mi?")
                        builder.setPositiveButton("Evet") { dialog, which ->
                            ses_sil(yol)
                            //  resimGetir(kadi,ozel_id)
                            //

                        }

                        builder.setNegativeButton("Hayır") { dialog, which ->
                            // resimGetir(kadi,ozel_id)
                        }
                        builder.show()


                        // sil_alert(txt_stokAdi.getText().toString(),
                        //     txt_StokNo.getText().toString(),txtMiktar.getText().toString(),txtFiyat.getText().toString())
                        //   kayit_getir()
                        // newRecyclerviewm.removeItemDecorationAt(position)
                        // newRecyclerviewm.adapter= adapter
                        //  viewHolder.itemView.setBackgroundColor(Color.parseColor("#cc0000"))
                        // viewHolder.itemView.setBackgroundColor(R.color.red)
                        //exampleAdapter.notifyDataSetChanged();
                        // Do something when a user swept left

                    }
                    ItemTouchHelper.RIGHT -> {
                        val txt_ses = viewHolder.itemView.findViewById<TextView>(R.id.txt_ses).text


                        // val donustur_yol=txt_image.subSequence(32, txt_image.length).toString()
                        ses_oynat(txt_ses)
                        //  resimGetir(kadi,ozel_id)
                        Toast.makeText(requireContext(), "dONUSTU:" + yol, Toast.LENGTH_SHORT)
                            .show()

                        Toast.makeText(
                            requireContext(),
                            "txt_evrakturu: " + txt_ses,
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(requireContext(), "txt_image: " + yol, Toast.LENGTH_SHORT)
                            .show()

                        /* onarim_guncelle(txt_stokAdi.getText().toString(),
                             txt_StokNo.getText().toString(),txtMiktar.getText().toString(),txtFiyat.getText().toString())
                         // Do something when a user swept right*/
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(newRecyclerviewm)


    }

    private fun ses_sil(yol: String) {
        val queue = Volley.newRequestQueue(requireContext())
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = @SuppressLint("RestrictedApi")
        object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                firmaGetir(kadi, firma)
                Toast.makeText(requireContext(), "Silme Başarılı: " + kadi, Toast.LENGTH_SHORT)
                    .show()

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["yol"] = yol
                params["tur"] = "ses_sil"
                return params
            }
        }
        queue.add(postRequest)

    }


    private fun ses_oynat(txtSes: CharSequence?) {
        if (mMediaPlayer == null) {
            mMediaPlayer?.setDataSource(output)
            //  mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.prepare()
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()

    }

    private fun getUserData(context: Context) {
        val itemList: ArrayList<SesModel> = ArrayList()


        // return newArrayList.add()
        // newRecyclerviewm.adapter= resimgetir(newArrayList)
    }

    private fun startRecording(output: String) {
        try {
            // output_olustur()
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(requireContext(), "Kayıt Başladı!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun pauseRecording() {
        if (state) {
            var mp = MediaPlayer()
            mp.setDataSource(output)
            mp.prepare()
            mp.start()
            if (!recordingStopped) {
                Toast.makeText(requireContext(), "Durdu!", Toast.LENGTH_SHORT).show()
                mediaRecorder?.pause()
                recordingStopped = true
                button_pause_recording.text = "Resume"
            } else {
                resumeRecording()
            }
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun resumeRecording() {
        Toast.makeText(requireContext(), "Resume!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        button_pause_recording.text = "Pause"
        recordingStopped = false
    }

    private fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            onApi(kadi,firma_id,plaka,ozel_id,output)
        } else {
            // onApi(output)
            Toast.makeText(requireContext(), "You are not recording right now!", Toast.LENGTH_SHORT)
                .show()
            state = true
        }


    }

    private fun onApi(
        kadi: String,
        firma_id: String,
        plaka: String,
        ozel_id: String,
        output: String
    ) {
        val file = File(output)
        val bytes = file.readBytes()

        val queue = Volley.newRequestQueue(context)
        var url = "https://pratikhasar.com/netting/mobil.php"
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response -> // response
                Log.d("Response", response!!)
                Toast.makeText(requireContext(),"kayıt başarılı", Toast.LENGTH_LONG).show()
                //resimGetir(kadi,ozel_id)
                firmaGetir(kadi,ozel_id)

            },
            com.android.volley.Response.ErrorListener {
                Log.d("Response", "HATALI")
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["kadi"] = kadi
                params["firma_id"] = firma_id
                params["plaka"] = plaka
                params["ozel_id"] = ozel_id
                params["ses"] = output
                params["tur"] = "istek_ses_kaydet"

                // Add any other parameters here if needed
                return params
            }
              fun getByteData(): MutableMap<String, DataPart> {
                val params = HashMap<String, DataPart>()
                params["ses"] = DataPart(
                    "audio.3gp", bytes, "audio/3gpp"
                )

                return params

            }
        }
        queue.add(postRequest)
    }
}

