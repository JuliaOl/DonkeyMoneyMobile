package com.thebestgroup.io.donkeymoney_io.utils;

import com.thebestgroup.io.donkeymoney_io.utils.model.SecurityTokenResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Bartek on 08.01.2018.
 */

public interface SecurityTokenService {

    @POST("/api/user/securityToken")
    @Headers("Content-Type: application/json")
    Call<SecurityTokenResponse> getSecurityToken(
            @Body Map<String, String> body);

}
