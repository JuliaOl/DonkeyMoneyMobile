package com.thebestgroup.io.donkeymoney_io.utils;


public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://donkeymoney-dev-ed.my.salesforce.com/";

    public static APIService getAPIService() {

        return RestClient.getClient(BASE_URL).create(APIService.class);
    }

    public static String accessToken = null;
}
