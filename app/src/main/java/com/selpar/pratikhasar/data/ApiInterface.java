package com.selpar.pratikhasar.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("mobil.php?tur=evrak_kaydet")
    Call<ImageClass> uploadImage(@Field("title") String title,@Field("image") String image);
}
