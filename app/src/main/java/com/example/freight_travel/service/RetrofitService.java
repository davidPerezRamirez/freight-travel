package com.example.freight_travel.service;

import com.example.freight_travel.constants.ConstantsRestAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public QueriesRestAPIService connectToHerokuApp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsRestAPI.ROOT_URL_API_REST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(QueriesRestAPIService.class);
    }
}
