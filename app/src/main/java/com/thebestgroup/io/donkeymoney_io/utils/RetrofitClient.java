package com.thebestgroup.io.donkeymoney_io.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient myclient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(myclient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit;
        }
        return client;
    }

}
