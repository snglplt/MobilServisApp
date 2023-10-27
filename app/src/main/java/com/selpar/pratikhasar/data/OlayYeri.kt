package com.selpar.pratikhasar.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OlayYeri {
    @SerializedName("kadi")
    @Expose
    private lateinit var kadi:String


    @SerializedName("yol")
    @Expose
    private lateinit var yol:String

}