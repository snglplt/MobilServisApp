package com.selpar.pratikhasar.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.selpar.pratikhasar.AramaAdapter
import com.selpar.pratikhasar.R
import com.selpar.pratikhasar.data.AramaModel
import com.selpar.pratikhasar.data.ItemTespitModel
import com.selpar.pratikhasar.data.news
import com.selpar.pratikhasar.data.ustalar
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.HashMap

class KayitliUstalarAdapter( val itemList: ArrayList<ustalar>) :
    RecyclerView.Adapter<Usta_View_Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Usta_View_Holder {
        return Usta_View_Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_kayitli_ustalar_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Usta_View_Holder, position: Int) {
        holder.bindItems(itemList[position])
    }
}
