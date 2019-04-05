package com.example.freight_travel.service;

import com.example.freight_travel.constants.ConstantsRestAPI;
import com.example.freight_travel.models.Conductor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QueriesRestAPIService {

    //@FormUrlEncoded
    @GET(ConstantsRestAPI.GET_ALL_DRIVERS)
    Call<List<Conductor>> listConductores();
}
