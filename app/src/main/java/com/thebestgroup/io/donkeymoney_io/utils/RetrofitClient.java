package com.thebestgroup.io.donkeymoney_io.utils;


import com.thebestgroup.io.donkeymoney_io.utils.model.LoginResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.SecurityTokenResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zosia on 08.01.2018.
 */

public class RetrofitClient {
    private static Retrofit restAPI = null;
    private static Retrofit securityAPI = null;

    public static Retrofit getClient(String baseUrl, APIType type) {
        Retrofit client;
        if (type == APIType.REST_API) {
            client = restAPI;
        } else {
            client = securityAPI;
        }
        if (client == null) {
            client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return client;
    }

}
