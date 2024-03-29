package com.frame.dao;

import com.frame.po.Comment;
import com.frame.po.CommentWithUserInfo;

import java.util.List;

public interface CommentMapper {
    public List<CommentWithUserInfo> getChildComment(int commentId,int page);
    public List<CommentWithUserInfo> getCommentByTimeOrder(int artworkId,int page);
    public List<CommentWithUserInfo> getTwoChildComment(int commentId);
    public void createComment(Comment comment);
    public void createReplyOfComment(Comment comment);
    public void updateCommentReplyCount(int commentId,int count);
}
