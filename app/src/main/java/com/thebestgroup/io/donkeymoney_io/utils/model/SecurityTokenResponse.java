package com.thebestgroup.io.donkeymoney_io.utils.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bartek on 08.01.2018.
 */

public class SecurityTokenResponse {
    @SerializedName("securityToken")
    @Expose
    private String securityToken;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }
}
