package com.dhanas.grandeenotes.Model.downloads;

public class DownloadModel {
    private int Id;
    private String book_name;
    private String image;

    public DownloadModel(int id, String book_name, String image) {
        Id = id;
        this.book_name = book_name;
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
