package com.example.uaap.Model;
import com.google.gson.annotations.SerializedName;

public class GameId {
    @SerializedName("status")
    public String status;

    @SerializedName("gameId")
    public String gameId;

    @SerializedName("gameCode")
    public String gameCode;
    public GameId(){

    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public GameId(String status, String gameId, String gameCode){
        this.status = status;
        this.gameId = gameId;
        this.gameCode = gameCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
