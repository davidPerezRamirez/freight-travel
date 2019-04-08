package com.example.freight_travel.service;

import com.example.freight_travel.constants.ConstantsRestAPI;
import com.example.freight_travel.models.Conductor;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface QueriesRestAPIService {


    @GET(ConstantsRestAPI.GET_ALL_DRIVERS)
    Call<List<Conductor>> listConductores();

    @FormUrlEncoded
    @POST(ConstantsRestAPI.SAVE_DRIVER)
    Call<Integer> saveDriver(@Field("nombre")String nombre);

    @Multipart
    @POST(ConstantsRestAPI.SAVE_IMAGE)
    Call<ResponseBody> saveImage(@Part MultipartBody.Part file, @Part("image") RequestBody requestBody);
}
