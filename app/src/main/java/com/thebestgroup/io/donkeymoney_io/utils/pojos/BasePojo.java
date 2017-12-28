package com.thebestgroup.io.donkeymoney_io.utils.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 28.12.17
 */

public abstract class BasePojo {

    @SerializedName("Authorization")
    @Expose
    private String authorization;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
