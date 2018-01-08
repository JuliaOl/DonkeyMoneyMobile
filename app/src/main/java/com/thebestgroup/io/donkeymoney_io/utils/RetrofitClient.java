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

    // -------------------------------------------------------------------
    // do testowania tylko
    public static void main(String[] args) {
        // tworzenie serwisow do api i tokenu
        final APIService service = APIUtils.getAPIService();
        final SecurityTokenService tokenService = APIUtils.getTokenService();


        // pobieranie security token

        // przesylanie requesta jako 'json', serwer powinien sobie sam poradzic z rozkodowaniem tego
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "donkeymoneyapp@gmail.com");
        requestBody.put("password", "12345678");

        tokenService.getSecurityToken(requestBody)
                .enqueue(new Callback<SecurityTokenResponse>() {
                    @Override
                    public void onResponse(Call<SecurityTokenResponse> call, Response<SecurityTokenResponse> response) {
                        System.out.println("------------- security token --------------");
                        System.out.println("status: " + response.code());
                        System.out.println(response.body().getSecurityToken());
                    }

                    @Override
                    public void onFailure(Call<SecurityTokenResponse> call, Throwable t) {
                        System.out.println("uops!");
                        System.out.println(call);
                        System.out.println(t);
                    }
                });

        // -------------------------------
        // pobieranie login response
        service.getLoginResponse(
                "password",
                "3MVG9I5UQ_0k_hTmeUVaC9dV..7VgXlT69Oraw3ycdvmAmmiykCsDVWLaJFImgV6lJi2M6BhU8Y0mQvA7WINR",
                "6219607681359612175",
                "donkeymoneyapp@gmail.com",
                "12345678fCX9cOnMr0HEccp3xWqNZsdpv"
        ).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("------------- sales force login response --------------");
                System.out.println("Authorization " + String.valueOf(response.code()));
                System.out.println("Access token " + response.body().getAccessToken());
                System.out.println("Token type " + response.body().getTokenType());
//                APIUtils.accessToken = response.body().getTokenType() + " " + response.body().getAccessToken();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("Authorization failed");
                System.out.println(t);
            }
        });


        // -------------------------------
        // wykorzystanie uzyskanego securityToken w kolejnym zapytaniu - nie jestem piewien czy tak to się robi, ale dziala.. u mnie
        tokenService.getSecurityToken(requestBody)
                .enqueue(new Callback<SecurityTokenResponse>() {
                    @Override
                    public void onResponse(Call<SecurityTokenResponse> call, Response<SecurityTokenResponse> response) {

                        service.getLoginResponse(
                                "password",
                                "3MVG9I5UQ_0k_hTmeUVaC9dV..7VgXlT69Oraw3ycdvmAmmiykCsDVWLaJFImgV6lJi2M6BhU8Y0mQvA7WINR",
                                "6219607681359612175",
                                "donkeymoneyapp@gmail.com",
                                "12345678" + response.body().getSecurityToken()
                        ).enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                System.out.println("------------- security token + salesforce response --------------");
                                System.out.println("Authorization " + String.valueOf(response.code()));
                                System.out.println("Access token " + response.body().getAccessToken());
                                System.out.println("Token type " + response.body().getTokenType());
//                APIUtils.accessToken = response.body().getTokenType() + " " + response.body().getAccessToken();
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                System.out.println("Authorization failed");
                                System.out.println(t);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<SecurityTokenResponse> call, Throwable t) {
                        System.out.println("uops!");
                        System.out.println(call);
                        System.out.println(t);
                    }
                });
    }
}
