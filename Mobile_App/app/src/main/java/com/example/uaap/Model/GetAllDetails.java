package com.example.uaap.Model;
public class GetAllDetails {
    public int id;
    public String time;
    public int period;
    public String callType;
    public String committing;
    public String disadvantaged;
    public String referee;
    public String area;
    public String areaOfPlay;
    public String reviewDecision;
    public String comment;
    public String scores;

    public String getDisadvantaged() {
        return disadvantaged;
    }

    public void setDisadvantaged(String disadvantaged) {
        this.disadvantaged = disadvantaged;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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

    public String getAreaOfPlay() {
        return areaOfPlay;
    }

    public void setAreaOfPlay(String areaOfPlay) {
        this.areaOfPlay = areaOfPlay;
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

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
}