package com.example.uaap.Model;

public class CallToIssue {
    private boolean committingTeam;
    private String committingType;
    private String committing;
    private boolean disTeam;
    private String disType;
    private String dis;
    private String callType;
    private String call;
    private String refereeId;
    private String area;
    private String areaOfPlay;
    private String reviewDecision;
    private String comment;

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

    public boolean isCommittingTeam() {
        return committingTeam;
    }

    public void setCommittingTeam(boolean committingTeam) {
        this.committingTeam = committingTeam;
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

    public boolean isDisTeam() {
        return disTeam;
    }

    public void setDisTeam(boolean disTeam) {
        this.disTeam = disTeam;
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
}
