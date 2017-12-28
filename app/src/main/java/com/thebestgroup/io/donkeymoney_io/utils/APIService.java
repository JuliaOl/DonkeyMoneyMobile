package com.thebestgroup.io.donkeymoney_io.utils;

import com.thebestgroup.io.donkeymoney_io.utils.pojos.AuthorizationResponsePojo;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {
    @POST("/services/oauth2/token")
    Call<AuthorizationResponsePojo> login(
            @Query("grant_type") String grantType,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("username") String username,
            @Query("password") String password
    );
}
