package com.example.uaap.Model;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public ArrayList<AccountDetails> result;

    public User(){

    }
    public User(String status, ArrayList<AccountDetails> result){
        this.status = status;
        this.result = result;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<AccountDetails> getResult() {
        return result;
    }

    public void setResult(ArrayList<AccountDetails> result) {
        this.result = result;
    }
}

