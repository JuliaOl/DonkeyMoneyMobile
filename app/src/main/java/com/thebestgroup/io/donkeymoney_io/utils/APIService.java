package com.thebestgroup.io.donkeymoney_io.utils;

import com.thebestgroup.io.donkeymoney_io.utils.model.LoginResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.SecurityTokenResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zosia on 08.01.2018.
 */

public interface APIService {

    @POST("/services/oauth2/token")
    Call<LoginResponse> getLoginResponse(
            @Query("grant_type") String grantType,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("username") String username,
            @Query("password") String password
    );
}
