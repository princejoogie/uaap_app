package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public String data;

    public Basic(){

    }
    public Basic(String status, String data){
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
