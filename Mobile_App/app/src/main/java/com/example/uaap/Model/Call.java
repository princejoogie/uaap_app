package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Call {
    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public ArrayList<CallToIssue> result;

    public Call(){

    }
    public Call(String status, ArrayList<CallToIssue> result){
        this.status = status;
        this.result = result;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CallToIssue> getResult() {
        return result;
    }

    public void setResult(ArrayList<CallToIssue> result) {
        this.result = result;
    }
}
