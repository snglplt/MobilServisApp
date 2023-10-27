package com.selpar.pratikhasar.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGiris {
    @GET("netting/mobil.php")
    fun giris(
        @Query("tur") tur: String,
        @Query("kadi") kadi: String
    ): Call<List<Giris>>

}