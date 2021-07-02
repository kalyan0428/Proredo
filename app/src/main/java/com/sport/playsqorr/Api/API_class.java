package com.sport.playsqorr.Api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface API_class
{

    @GET(DZ_URL.USer)
    Call<JsonElement> USER(@Header("Accept") String authorization);

    @GET(DZ_URL.myrefers)
    Call<JsonElement> GetPrefere(@Header("Accept") String authorization, @Header("sessionToken") String sessionToken , @Header("Authorization") String NEWTOKEN);

    /*    @FormUrlEncoded
        @POST(DZ_URL.mapprices)
        Call<JsonElement> PRICE(@Header("Accept") String Accept, @Header("Authorization") String Authorization, @Field("from") String from, @Field("to") String to);

     */
    @GET(DZ_URL.info)
    Call<JsonElement> info(@Header("Accept") String authorization, @Header("Authorization") String NEWTOKEN);
//    Call<JsonElement> info(@Header("Accept") String authorization, @Header("sessionToken") String sessionToken,@Header("Authorization") String NEWTOKEN);
}
