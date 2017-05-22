package com.example.nenad.retrofittest.data.remote;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nenad on 5/9/2017.
 */

public class RestClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                   .baseUrl(baseUrl)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
        }
        return retrofit;
    }
}
