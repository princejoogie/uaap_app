package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentGame {
    @SerializedName("playingA")
    public String[] playingA;

    @SerializedName("playingB")
    public String[] playingB;

    public CurrentGame(){

    }

    public CurrentGame(String[] playingA, String[] playingB){
        this.playingA = playingA;
        this.playingB = playingB;
    }

    public String[] getPlayingA() {
        return playingA;
    }

    public void setPlayingA(String[] playingA) {
        this.playingA = playingA;
    }

    public String[] getPlayingB() {
        return playingB;
    }

    public void setPlayingB(String[] playingB) {
        this.playingB = playingB;
    }
}
