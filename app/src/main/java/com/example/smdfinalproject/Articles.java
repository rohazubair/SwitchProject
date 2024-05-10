package com.example.smdfinalproject;

import android.graphics.drawable.Drawable;

public class Articles {

    private String Title;
    private String Data;

    private String audio;
    private String image;

    public Articles(){
    }

    public Articles(String Title, String Data, String image, String audio) {
        this.Title = Title;
        this.Data = Data;
        this.image = image;
        this.audio = audio;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title= Title;
    }

    public String getData() {
        return Data;
    }

    public String getAudio() {
        return audio;
    }

    public void setData(String Data) {
        this.Data = Data;
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
