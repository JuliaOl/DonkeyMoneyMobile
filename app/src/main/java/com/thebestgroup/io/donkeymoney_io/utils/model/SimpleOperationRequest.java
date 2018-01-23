package com.thebestgroup.io.donkeymoney_io.utils.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Julia on 23.01.2018.
 */

public class SimpleOperationRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("amount")
    @Expose
    private Double amount;

    public SimpleOperationRequest(String name, String createdAt, Double amount) {
        this.name = name;
        this.createdAt = createdAt;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
