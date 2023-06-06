package com.maayan.integrative_20.Utils;

import com.maayan.integrative_20.Interfaces.API_Interface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Retrofit retrofit;
    private API_Interface api_interface;

    public RetrofitClient(String baseAddress) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api_interface = retrofit.create(API_Interface.class);

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public API_Interface getApi_interface() {
        return api_interface;
    }
}
