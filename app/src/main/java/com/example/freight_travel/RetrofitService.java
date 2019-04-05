package com.example.freight_travel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitService {

    public static ConductorService connectToHerokuApp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://freight-travel.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ConductorService.class);
    }
}
