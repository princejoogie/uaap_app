package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeagueRef {
    @SerializedName("status")
    public String status;

    @SerializedName("leagues")
    public ArrayList<LeagueDetails> leagues;

    @SerializedName("referees")
    public ArrayList<LeagueDetails> referees;

    public LeagueRef(){

    }
    public LeagueRef(String status, ArrayList<LeagueDetails> leagues){
        this.status = status;
        this.leagues = leagues;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<LeagueDetails> getLeagues() {
        return leagues;
    }

    public void setLeagues(ArrayList<LeagueDetails> leagues) {
        this.leagues = leagues;
    }

    public ArrayList<LeagueDetails> getReferees() {
        return referees;
    }

    public void setReferees(ArrayList<LeagueDetails> referees) {
        this.referees = referees;
    }
}
