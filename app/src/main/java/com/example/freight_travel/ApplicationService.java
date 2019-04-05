package com.example.freight_travel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApplicationService {

    @GET("Conductor")
    Call<List<Conductor>> listConductores();
}
