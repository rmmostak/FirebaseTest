package com.rmproduct.apptest;

public class ImageModel {
    private String id, title, imageURL, date;

    public ImageModel() {
    }

    public ImageModel(String id, String title, String imageURL, String date) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
