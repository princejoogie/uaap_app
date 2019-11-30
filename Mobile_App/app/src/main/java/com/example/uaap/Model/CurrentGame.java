package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentGame {
    public String gameCode;
    public String gameId;
    public ArrayList<PersonDetails> staffA;
    public ArrayList<PersonDetails> staffB;
    public ArrayList<PlayersDetails> playerA;
    public ArrayList<PlayersDetails> playerB;
    public int period;
    public String periodName;
    public String teamA;
    public String teamAId;
    public String teamBId;
    public String teamB;
    public long timeInMillis;
    public String time;
    public int ScoreA;
    public int colorTeamA;
    public int colorTeamB;

    public int getColorTeamA() {
        return colorTeamA;
    }

    public void setColorTeamA(int colorTeamA) {
        this.colorTeamA = colorTeamA;
    }

    public int getColorTeamB() {
        return colorTeamB;
    }

    public void setColorTeamB(int colorTeamB) {
        this.colorTeamB = colorTeamB;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int ScoreB;


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

    public String getTeamAId() {
        return teamAId;
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

    public void setTeamAId(String teamAId) {
        this.teamAId = teamAId;
    }

    public String getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(String teamBId) {
        this.teamBId = teamBId;
    }

    public ArrayList<PersonDetails> getReferee() {
        return referee;
    }

    public void setReferee(ArrayList<PersonDetails> referee) {
        this.referee = referee;
    }

    public ArrayList<PersonDetails> referee;

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



}
