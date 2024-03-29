package com.frame.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class User implements Serializable {
    private int user_id;
    private String user_name;
    private String user_password;
    private String user_avatar;
    private int user_following;
    private int user_followers;
    private int user_collectionCount;
    private int user_artworkCount;
    private String user_comment;
    private int user_role;

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public int getUser_collectionCount() {
        return user_collectionCount;
    }

    public void setUser_collectionCount(int user_collectionCount) {
        this.user_collectionCount = user_collectionCount;
    }

    public int getUser_artworkCount() {
        return user_artworkCount;
    }

    public void setUser_artworkCount(int user_artworkCount) {
        this.user_artworkCount = user_artworkCount;
    }

    public User(){}

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public int getUser_following() {
        return user_following;
    }

    public void setUser_following(int user_following) {
        this.user_following = user_following;
    }

    public int getUser_followers() {
        return user_followers;
    }

    public void setUser_followers(int user_followers) {
        this.user_followers = user_followers;
    }

    @JsonIgnore
    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
