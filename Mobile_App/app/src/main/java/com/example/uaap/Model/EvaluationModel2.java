package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EvaluationModel2 {
    @SerializedName("status")
    public String status;

    @SerializedName("evaluation")
    public ArrayList<EvaluationDetails> evaluation;

    @SerializedName("referee")
    public ArrayList<RefereeSum2> referee;
    public EvaluationModel2(){

    }

    public EvaluationModel2(String status, ArrayList<EvaluationDetails> evaluation, ArrayList<RefereeSum2> referee) {
        this.status = status;
        this.evaluation = evaluation;
        this.referee = referee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<EvaluationDetails> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(ArrayList<EvaluationDetails> evaluation) {
        this.evaluation = evaluation;
    }

    public ArrayList<RefereeSum2> getReferee() {
        return referee;
    }

    public void setReferee(ArrayList<RefereeSum2> referee) {
        this.referee = referee;
    }
}
