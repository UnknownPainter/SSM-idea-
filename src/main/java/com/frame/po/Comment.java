package com.frame.po;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private int comment_id;
    private int comment_artworkId;
    private String comment_content;
    private Date comment_createTime;
    private int comment_toId;
    private int comment_replyCount;
    private int comment_userId;

    public int getComment_replyCount() {
        return comment_replyCount;
    }

    public void setComment_replyCount(int comment_replyCount) {
        this.comment_replyCount = comment_replyCount;
    }

    public int getComment_userId() {
        return comment_userId;
    }

    public void setComment_userId(int comment_userId) {
        this.comment_userId = comment_userId;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getComment_artworkId() {
        return comment_artworkId;
    }

    public void setComment_artworkId(int comment_artworkId) {
        this.comment_artworkId = comment_artworkId;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public Date getComment_createTime() {
        return comment_createTime;
    }

    public void setComment_createTime(Date comment_createTime) {
        this.comment_createTime = comment_createTime;
    }


    public int getComment_toId() {
        return comment_toId;
    }

    public void setComment_toId(int comment_toId) {
        this.comment_toId = comment_toId;
    }
}
