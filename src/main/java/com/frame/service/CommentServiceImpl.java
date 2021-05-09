package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CommentMapper;
import com.frame.po.Comment;
import com.frame.po.CommentWithUserInfo;
import com.frame.utils.CallbackForController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArtworkMapper artworkMapper;

    private static final int PAGE_COUNT = 20;

    @Override
    @Transactional
    public Comment createReplyOfComment(int commentId, int artworkId, String content, int userId) {
        Comment comment = new Comment();
        comment.setComment_content(content);
        comment.setComment_artworkId(artworkId);
        comment.setComment_userId(userId);
        comment.setComment_toId(commentId);
        comment.setComment_createTime(new Date());
        commentMapper.createReplyOfComment(comment);
        commentMapper.updateCommentReplyCount(commentId,1);
        artworkMapper.updateArtworkCommentCount(artworkId,1);
        return comment;
    }

    @Override
    @Transactional
    public Comment createComment(int artworkId, String content, int userId) {
        Comment comment = new Comment();
        comment.setComment_content(content);
        comment.setComment_artworkId(artworkId);
        comment.setComment_userId(userId);
        comment.setComment_createTime(new Date());
        commentMapper.createComment(comment);
        artworkMapper.updateArtworkCommentCount(artworkId,1);
        return comment;
    }

    @Override
    public List<CommentWithUserInfo> getCommentByTimeOrder(int artworkId, int page) {
        List<CommentWithUserInfo> commentList = commentMapper.getCommentByTimeOrder(artworkId,page*PAGE_COUNT);
        if(commentList==null)return null;
        List<CommentWithUserInfo> tempList = new ArrayList<>();
        for(CommentWithUserInfo comment: commentList){
            if(comment.getComment_replyCount()!=0){
                tempList.addAll(commentMapper.getTwoChildComment(comment.getComment_id()));
            }
        }
        commentList.addAll(tempList);
        return commentList;
    }

}
