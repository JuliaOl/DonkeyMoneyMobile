package com.thebestgroup.io.donkeymoney_io.utils;

//import android.content.Intent;
import android.util.Log;

//import com.thebestgroup.io.donkeymoney_io.LoginActivity;
//import com.thebestgroup.io.donkeymoney_io.MainActivity;
import com.thebestgroup.io.donkeymoney_io.utils.model.LoginResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zosia on 08.01.2018.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    // base url: https://donkeymoney.herokuapp.com
    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void main(String[] args) {
        APIService service = APIUtils.getAPIService();

        service.getLoginResponse(
                "password",
                "3MVG9I5UQ_0k_hTmeUVaC9dV..7VgXlT69Oraw3ycdvmAmmiykCsDVWLaJFImgV6lJi2M6BhU8Y0mQvA7WINR",
                "6219607681359612175",
                "donkeymoneyapp@gmail.com",
                "12345678fCX9cOnMr0HEccp3xWqNZsdpv"
        ).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("Authorization" + String.valueOf(response.code()));
                System.out.println("Access token" + response.body().getAccessToken());
                System.out.println("TOken type" + response.body().getTokenType());
//                APIUtils.accessToken = response.body().getTokenType() + " " + response.body().getAccessToken();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Authorization", "Failure");
            }
        });

        APIService tokenService = APIUtils.getTokenService();

        

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "donkeymoneyapp@gmail.com");
        requestBody.put("password", "12345678");

        tokenService.getSecurityToken(requestBody)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("-------------");
                        System.out.println(response.code());
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }
}
