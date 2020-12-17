package com.dhanas.grandeenotes.Model.AnswerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("a_id")
    @Expose
    private String a_id;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("a_bio")
    @Expose
    private String a_bio;
    @SerializedName("vote")
    @Expose
    private String vote;
    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("q_id")
    @Expose
    private String q_id;

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getA_bio() {
        return a_bio;
    }

    public void setA_bio(String a_bio) {
        this.a_bio = a_bio;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }
}
