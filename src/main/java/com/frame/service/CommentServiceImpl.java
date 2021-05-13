package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CommentMapper;
import com.frame.po.Comment;
import com.frame.po.CommentWithUserInfo;
import com.frame.utils.CallbackForController;
import com.frame.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtils redisUtils;

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
        redisUtils.removeObject("childComment:"+commentId);
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
        redisUtils.removeObject("comment:"+artworkId);
        return comment;
    }

    @Override
    public List<CommentWithUserInfo> getCommentByTimeOrder(int artworkId, int page) {
        String pageStr = String.valueOf(page);
        List<CommentWithUserInfo> commentList = (List<CommentWithUserInfo>)redisUtils.getObject("comment:"+artworkId,pageStr);
        if(null==commentList){
            commentList = commentMapper.getCommentByTimeOrder(artworkId,page*PAGE_COUNT);
            if(commentList==null)return null;
            redisUtils.putObject("comment:"+artworkId,""+pageStr,commentList);
        }
//        List<CommentWithUserInfo> commentList = commentMapper.getCommentByTimeOrder(artworkId,page*PAGE_COUNT);
//        if(commentList==null)return null;
        List<CommentWithUserInfo> tempList = new ArrayList<>();
        List<CommentWithUserInfo> child=null;
        for(CommentWithUserInfo comment: commentList){
            if(comment.getComment_replyCount()!=0){
                child = (List<CommentWithUserInfo>)redisUtils.getObject("childComment:"+comment.getComment_id(),"two");
                if(null!=child) {
                    tempList.addAll(child);
                    continue;
                }
                child = commentMapper.getTwoChildComment(comment.getComment_id());
                if(null==child)continue;
                redisUtils.putObject("childComment:"+comment.getComment_id(),"two",child);
                tempList.addAll(child);
            }
        }
        commentList.addAll(tempList);
        return commentList;
    }

    @Override
    public List<CommentWithUserInfo> getChildComment(int commentId, int page) {
        return commentMapper.getChildComment(commentId,page*PAGE_COUNT);
    }
}
