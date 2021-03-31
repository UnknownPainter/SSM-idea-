package com.frame.dao;

import com.frame.po.Comment;

import java.util.List;

public interface CommentMapper {
    public List<Comment> getAllChildComment(int commentId);
    public List<Comment> getCommentByTimeOrder(int artworkId,int page);
    public List<Comment> getTwoChildComment(int commentId);
    public void createComment(Comment comment);
}
