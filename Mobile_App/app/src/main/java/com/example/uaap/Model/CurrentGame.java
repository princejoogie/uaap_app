package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentGame {
    public String gameCode;
    public String gameId;
    @SerializedName("playingA")
    public String[] playingA;

    @SerializedName("playingB")
    public String[] playingB;

    public int period;
    public String periodName;
    public String teamA;
    public String teamB;
    public long timeInMillis;
    public int ScoreA;
    public int ScoreB;

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public int getScoreA() {
        return ScoreA;
    }

    public void setScoreA(int scoreA) {
        ScoreA = scoreA;
    }

    public int getScoreB() {
        return ScoreB;
    }

    public void setScoreB(int scoreB) {
        ScoreB = scoreB;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

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
