package com.example.check24products.data.remote;

import okhttp3.OkHttpClient;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static retrofit2.Retrofit INSTANCE = null;

    public static retrofit2.Retrofit getClient(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        if (INSTANCE == null) {
            INSTANCE = new retrofit2.Retrofit.Builder().baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return INSTANCE;
    }
}
