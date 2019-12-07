package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RefSumModel {
    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public ArrayList<RefereeSum> result;

    public RefSumModel(){

    }
    public RefSumModel(String status, ArrayList<RefereeSum> result){
        this.status = status;
        this.result = result;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<RefereeSum> getResult() {
        return result;
    }

    public void setResult(ArrayList<RefereeSum> result) {
        this.result = result;
    }
}
