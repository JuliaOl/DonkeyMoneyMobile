package com.thebestgroup.io.donkeymoney_io.utils;

/**
 * Created by Zosia on 08.01.2018.
 */

public class APIUtils {
    //ten do logowania (sales Force)
    public static final String BASE_URL = "https://donkeymoney-dev-ed.my.salesforce.com/";
    //ten do uzyskiwania security token
    public static final String BASE_URL_TOKEN = "https://donkeymoney.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getTokenService() {

        return RetrofitClient.getClient(BASE_URL_TOKEN).create(APIService.class);
    }

}
