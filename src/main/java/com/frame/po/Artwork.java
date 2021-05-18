package com.frame.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Artwork implements Serializable {
    private int artwork_id;
    private String artwork_comment;
    private String artwork_name;
    private int artwork_artistId;
    private String artwork_location;
    private int artwork_collectCount;
    private int artwork_width;
    private int artwork_height;
    private Date artwork_createTime;
    private int artwork_commentCount;
    private int artwork_group;

    public  Artwork(){}

    public int getArtwork_group() {
        return artwork_group;
    }

    public void setArtwork_group(int artwork_group) {
        this.artwork_group = artwork_group;
    }

    public int getArtwork_commentCount() {
        return artwork_commentCount;
    }

    public void setArtwork_commentCount(int artwork_commentCount) {
        this.artwork_commentCount = artwork_commentCount;
    }

    public Date getArtwork_createTime() {
        return artwork_createTime;
    }

    public void setArtwork_createTime(Date artwork_createTime) {
        this.artwork_createTime = artwork_createTime;
    }


    public int getArtwork_width() {
        return artwork_width;
    }

    public void setArtwork_width(int artwork_width) {
        this.artwork_width = artwork_width;
    }

    public int getArtwork_height() {
        return artwork_height;
    }

    public void setArtwork_height(int artwork_height) {
        this.artwork_height = artwork_height;
    }

    public int getArtwork_collectCount() {
        return artwork_collectCount;
    }

    public void setArtwork_collectCount(int artwork_collectCount) {
        this.artwork_collectCount = artwork_collectCount;
    }

    public int getArtwork_id() {
        return artwork_id;
    }

    public void setArtwork_id(int artwork_id) {
        this.artwork_id = artwork_id;
    }

    public String getArtwork_comment() {
        return artwork_comment;
    }

    public void setArtwork_comment(String artwork_comment) {
        this.artwork_comment = artwork_comment;
    }

    public String getArtwork_name() {
        return artwork_name;
    }

    public void setArtwork_name(String artwork_name) {
        this.artwork_name = artwork_name;
    }

    public int getArtwork_artistId() {
        return artwork_artistId;
    }

    public void setArtwork_artistId(int artwork_artist) {
        this.artwork_artistId = artwork_artist;
    }

    public String getArtwork_location() {
        return artwork_location;
    }

    public void setArtwork_location(String artwork_location) {
        this.artwork_location = artwork_location;
    }
}
