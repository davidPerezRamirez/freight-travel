package com.example.freight_travel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConductorService {

    @GET("Conductor")
    Call<List<Conductor>> all();
}
