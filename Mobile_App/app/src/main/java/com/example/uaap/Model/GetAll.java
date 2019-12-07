package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAll {
    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public ArrayList<GetAllDetails> result;

    public GetAll(){

    }
    public GetAll(String status, ArrayList<GetAllDetails> result){
        this.status = status;
        this.result = result;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<GetAllDetails> getResult() {
        return result;
    }

    public void setResult(ArrayList<GetAllDetails> result) {
        this.result = result;
    }
}