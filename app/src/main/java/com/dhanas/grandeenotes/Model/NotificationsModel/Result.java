package com.dhanas.grandeenotes.Model.NotificationsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String image_url;
    @SerializedName("date")
    @Expose
    private String date;

    private final List<com.dhanas.grandeenotes.Model.NotificationsModel.Result> result = null;

    public Result(String id, String title, String description, String image_url, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image_url = image_url;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public List<com.dhanas.grandeenotes.Model.NotificationsModel.Result> getResult() {
        return result;
    }
}
