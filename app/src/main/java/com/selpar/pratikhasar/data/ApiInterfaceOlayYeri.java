package com.selpar.pratikhasar.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface ApiInterfaceOlayYeri {

    @FormUrlEncoded
    @POST("mobil.php?tur=olay_yeri_kaydet")
    Call<OlayYeri> uploadImage(@Field("kadi") String kadi, @Field("ozel_id") Integer ozel_id,
                                 @Field("yol") String yol);
}
