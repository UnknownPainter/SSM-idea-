package com.frame.service;

import com.frame.po.Comment;
import com.frame.po.CommentWithUserInfo;
import com.frame.utils.CallbackForController;

import java.util.List;

public interface CommentService {
    List<CommentWithUserInfo> getCommentByTimeOrder(int artworkId, int page);
    Comment createComment(int artworkId,String content,int userId);
    Comment createReplyOfComment(int commentId, int artworkId, String content, int userId);
    List<CommentWithUserInfo> getChildComment(int commentId,int page);
}
