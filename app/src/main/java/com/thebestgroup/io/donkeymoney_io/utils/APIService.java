package com.thebestgroup.io.donkeymoney_io.utils;

import com.thebestgroup.io.donkeymoney_io.utils.model.LoginResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.OperationResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.SecurityTokenResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.SimpleOperationRequest;
import com.thebestgroup.io.donkeymoney_io.utils.model.UserDataResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zosia on 08.01.2018.
 */

public interface APIService {

    @Headers("X-Requested-With: null")
    @POST("/https://donkeymoney-dev-ed.my.salesforce.com/services/oauth2/token")
    Call<LoginResponse> getLoginResponse(
            @Query("grant_type") String grantType,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("username") String username,
            @Query("password") String password
    );

    @Headers("X-Requested-With: null")
    @GET("/https://donkeymoney-dev-ed.my.salesforce.com/services/apexrest/operation")
    Call<List<OperationResponse>> getOperations(
            @HeaderMap Map<String, String> headers,
            @Query("last") Integer last
    );
    @Headers("X-Requested-With: null")
    @GET("/https://donkeymoney-dev-ed.my.salesforce.com/services/apexrest/user/me")
    Call<UserDataResponse> getUser(
            @HeaderMap Map<String, String> headers
    );

    @Headers("X-Requested-With: null")
    @POST("/https://donkeymoney-dev-ed.my.salesforce.com/services/apexrest/operation")
    Call<Void> addOperation(
            @HeaderMap Map<String, String> headers,
            @Body SimpleOperationRequest operationBody
    );
}
