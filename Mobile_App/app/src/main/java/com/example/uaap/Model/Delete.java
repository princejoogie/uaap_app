package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Delete {
    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    public Delete(){

    }
    public Delete(String status, String message){
        this.status = status;
        this.message = message;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
