package com.frame.dao;

import com.frame.po.Comment;
import com.frame.po.CommentWithUserInfo;

import java.util.List;

public interface CommentMapper {
    public List<CommentWithUserInfo> getAllChildComment(int commentId);
    public List<CommentWithUserInfo> getCommentByTimeOrder(int artworkId,int page);
    public List<CommentWithUserInfo> getTwoChildComment(int commentId);
    public void createComment(Comment comment);
}
