package com.selpar.pratikhasar.`interface`

import android.media.Image
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MyApi {

    @Multipart
    @POST("/netting/mobil.php?tur=resimgetir")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("desc") desc: RequestBody

    )

    companion object {
        operator fun invoke(): MyApi{
            return Retrofit.Builder()
                .baseUrl("https://pratikhasar.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}