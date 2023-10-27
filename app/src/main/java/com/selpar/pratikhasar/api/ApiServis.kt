package com.selpar.pratikhasar.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServis {
    @GET("api/api.php")
    //fun getMarka(@Query("AracTuru")AracTuru:String):Call<List<Marka>>


    fun getMarka(@Query("username")username: String,
                 @Query("password")password:String,
                 @Query("kaynak")kaynak:String,
                 @Query("AracTuru")AracTuru:String): Call<List<Marka>>


}