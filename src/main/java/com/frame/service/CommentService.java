package com.frame.service;

import com.frame.po.Comment;
import com.frame.utils.CallbackForController;

import java.util.List;

public interface CommentService {
    int test(CallbackForController callback);
    void getCommentByTimeOrder(int artworkId,int page,CallbackForController callback);
    void createComment(int artworkId,String content,int userId,CallbackForController callback);
}
