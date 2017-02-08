package com.example.maitrayshah.ilovezappos.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maitrayshah on 06/02/17.
 */
public class RetrofitClient {
    private static final String BASE_URL = "https://api.zappos.com/";
    private static Retrofit retroClient = null;


    public static Retrofit getClient() {
        if (retroClient==null) {
            retroClient = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retroClient;
    }
}
