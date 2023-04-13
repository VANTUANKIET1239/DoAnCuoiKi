package com.example.doanbanhoa.Models;

import com.google.firebase.database.ServerValue;

public class Commit {
    private String id_user;
    private String content;
    private String user_name;
    private String user_img;
    private Object time;

    public Commit(String id_user, String content, String user_name, String user_img) {
        this.id_user = id_user;
        this.content = content;
        this.user_name = user_name;
        this.user_img = user_img;
        this.time = ServerValue.TIMESTAMP;
    }
    public Commit() {
        this.id_user = id_user;
        this.content = content;
        this.user_name = user_name;
        this.user_img = user_img;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }
}
