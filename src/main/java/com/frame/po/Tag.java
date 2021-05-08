package com.frame.po;

import java.io.Serializable;

public class Tag implements Serializable {
    private int tag_id;
    private String tag_name;
    private int artwork_id;

    public Tag() {
    }

    public Tag(String tag_name, int artwork_id) {
        this.tag_name = tag_name;
        this.artwork_id = artwork_id;
    }

    public int getId() {
        return tag_id;
    }

    public void setId(int id) {
        this.tag_id = id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public int getArtwork_id() {
        return artwork_id;
    }

    public void setArtwork_id(int artwork_id) {
        this.artwork_id = artwork_id;
    }
}
