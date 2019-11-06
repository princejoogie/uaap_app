package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EvaluationModel {
    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public ArrayList<EvaluationDetails> result;

    public EvaluationModel(){

    }
    public EvaluationModel(String status, ArrayList<EvaluationDetails> result){
        this.status = status;
        this.result = result;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<EvaluationDetails> getResult() {
        return result;
    }

    public void setResult(ArrayList<EvaluationDetails> result) {
        this.result = result;
    }
}
