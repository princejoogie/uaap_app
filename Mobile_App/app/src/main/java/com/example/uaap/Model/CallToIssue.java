package com.example.uaap.Model;

import com.google.gson.annotations.SerializedName;

public class CallToIssue {
    @SerializedName("committingTeam")
    private String committingTeam;
    @SerializedName("committingType")
    private String committingType;
    @SerializedName("committing")
    private String committing;
    @SerializedName("disTeam")
    private String disTeam;
    @SerializedName("disType")
    private String disType;
    @SerializedName("dis")
    private String dis;
    @SerializedName("callType")
    private String callType;
    @SerializedName("call")
    private String call;
    @SerializedName("refereeId")
    private String refereeId;
    @SerializedName("area")
    private String area;
    @SerializedName("areaOfPlay")
    private String areaOfPlay;
    @SerializedName("reviewDecision")
    private String reviewDecision;
    @SerializedName("comment")
    private String comment;
    @SerializedName("period")
    private int period;
    @SerializedName("periodName")
    private String periodName;

    public CallToIssue(){

    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }


    public String getCommittingType() {
        return committingType;
    }

    public void setCommittingType(String committingType) {
        this.committingType = committingType;
    }

    public String getCommitting() {
        return committing;
    }

    public void setCommitting(String committing) {
        this.committing = committing;
    }



    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCommittingTeam() {
        return committingTeam;
    }

    public void setCommittingTeam(String committingTeam) {
        this.committingTeam = committingTeam;
    }

    public String getDisTeam() {
        return disTeam;
    }

    public void setDisTeam(String disTeam) {
        this.disTeam = disTeam;
    }
}
