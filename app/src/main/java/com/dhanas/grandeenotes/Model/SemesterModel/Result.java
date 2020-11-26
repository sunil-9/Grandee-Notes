package com.dhanas.grandeenotes.Model.SemesterModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("sem_id")
    @Expose
    private String sid;
    @SerializedName("sem_name")
    @Expose
    private String sname;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
