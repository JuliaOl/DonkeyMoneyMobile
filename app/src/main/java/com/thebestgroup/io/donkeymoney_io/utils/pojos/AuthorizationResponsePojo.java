package com.thebestgroup.io.donkeymoney_io.utils.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 28.12.17
 */

public class AuthorizationResponsePojo {

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("instance_url")
    @Expose
    private String instanceUrl;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("signature")
    @Expose
    private String signature;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("issued_at")
    @Expose
    private String issuedAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }
}
