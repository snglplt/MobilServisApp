package com.selpar.pratikhasar.data

import retrofit2.Call
import retrofit2.http.*

interface ApiInterfaceOlayYeri2 {
    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("mobil.php?tur=olay_yeri_kaydet")
    fun uploadImage(
        @Field("kadi") kadi: String?,
        @Field("yol") yol: String?
    ): Call<OlayYeri?>?
}