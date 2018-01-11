package com.thebestgroup.io.donkeymoney_io.utils.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperationResponse {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("tag")
    @Expose
    private Tag tag;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("familyId")
    @Expose
    private Object familyId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("amount")
    @Expose
    private Double amount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Object familyId) {
        this.familyId = familyId;
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