package com.example.smdfinalproject;

import android.graphics.drawable.Drawable;

public class Articles {

    private String title;
    private String detail;

    private String audio;
    private String image;

    public Articles(){
    }

    public Articles(String title, String detail, String img, String audio) {
        this.title = title;
        this.detail = detail;
        this.image = img;
        this.audio = audio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title= title;
    }

    public String getdetail() {
        return detail;
    }

    public String getAudio() {
        return audio;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
