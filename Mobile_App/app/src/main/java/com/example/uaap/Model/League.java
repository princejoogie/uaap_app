package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class League {
    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public ArrayList<LeagueDetails> result;

    public League(){

    }
    public League(String status, ArrayList<LeagueDetails> result){
        this.status = status;
        this.result = result;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<LeagueDetails> getResult() {
        return result;
    }

    public void setResult(ArrayList<LeagueDetails> result) {
        this.result = result;
    }
}
