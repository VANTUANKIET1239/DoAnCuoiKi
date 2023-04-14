package com.example.doanbanhoa.Models;

import com.google.firebase.database.ServerValue;

public class Comment {
    private String id_user;
    private String content;
    private Object time;
    private Float rating;


    public Comment() {

    }

    public Comment(String id, String comment, Float rating_user) {
        this.id_user = id;
        this.content = comment;
        this.rating = rating_user;
        this.time = ServerValue.TIMESTAMP;
    }


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

}
