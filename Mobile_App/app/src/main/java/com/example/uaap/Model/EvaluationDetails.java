package com.example.uaap.Model;

public class EvaluationDetails {

    public int id;
    public String period;
    public String time;
    public String callType;
    public String committing;
    public String disadvantaged;
    public String referee;
    public String area;
    public String reviewDecision;
    public String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCommitting() {
        return committing;
    }

    public void setCommitting(String committing) {
        this.committing = committing;
    }

    public String getDisadvantaged() {
        return disadvantaged;
    }

    public void setDisadvantaged(String disadvantaged) {
        this.disadvantaged = disadvantaged;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReviewDecision() {
        return reviewDecision;
    }

    public void setReviewDecision(String reviewDecision) {
        this.reviewDecision = reviewDecision;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
