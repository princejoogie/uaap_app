package com.example.uaap.Model;

import android.app.Person;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    @SerializedName("status")
    public String status;

    @SerializedName("playerA")
    public ArrayList<PlayersDetails> playerA;

    @SerializedName("playerB")
    public ArrayList<PlayersDetails> playerB;

    @SerializedName("staffA")
    public ArrayList<PersonDetails> staffA;

    @SerializedName("staffB")
    public ArrayList<PersonDetails> staffB;

    @SerializedName("referee")
    public ArrayList<PersonDetails> referee;

    public Game(){

    }
    public Game(String status,
                ArrayList<PlayersDetails> playerA,
                ArrayList<PlayersDetails> playerB,
                ArrayList<PersonDetails> staffA,
                ArrayList<PersonDetails> staffB,
                ArrayList<PersonDetails> referee
        ){
        this.status = status;
        this.playerA = playerA;
        this.playerB = playerB;
        this.staffA = staffA;
        this.staffB = staffB;
        this.referee = referee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PlayersDetails> getPlayerA() {
        return playerA;
    }

    public void setPlayerA(ArrayList<PlayersDetails> playerA) {
        this.playerA = playerA;
    }

    public ArrayList<PlayersDetails> getPlayerB() {
        return playerB;
    }

    public void setPlayerB(ArrayList<PlayersDetails> playerB) {
        this.playerB = playerB;
    }

    public ArrayList<PersonDetails> getStaffA() {
        return staffA;
    }

    public void setStaffA(ArrayList<PersonDetails> staffA) {
        this.staffA = staffA;
    }

    public ArrayList<PersonDetails> getStaffB() {
        return staffB;
    }

    public void setStaffB(ArrayList<PersonDetails> staffB) {
        this.staffB = staffB;
    }

    public ArrayList<PersonDetails> getReferee() {
        return referee;
    }

    public void setReferee(ArrayList<PersonDetails> referee) {
        this.referee = referee;
    }
}
