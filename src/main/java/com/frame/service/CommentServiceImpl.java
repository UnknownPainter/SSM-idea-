package com.frame.service;

import com.frame.dao.CommentMapper;
import com.frame.po.Comment;
import com.frame.po.CommentWithUserInfo;
import com.frame.utils.CallbackForController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @Autowired
    private CommentMapper commentMapper;

    private static final int PAGE_COUNT = 20;

    @Override
    @Transactional
    public void createReplyOfComment(int commentId, int artworkId, String content, int userId, CallbackForController callback) {
        Runnable r = ()->{
            Comment comment = new Comment();
            comment.setComment_content(content);
            comment.setComment_artworkId(artworkId);
            comment.setComment_userId(userId);
            comment.setComment_toId(commentId);
            commentMapper.createComment(comment);
            commentMapper.updateCommentReplyCount(commentId,1);
            callback.callback(comment);
        };
        threadPool.execute(r);
    }

    @Override
    public void createComment(int artworkId, String content, int userId,CallbackForController callback) {
        Runnable r = ()->{
            Comment comment = new Comment();
            comment.setComment_content(content);
            comment.setComment_artworkId(artworkId);
            comment.setComment_userId(userId);
            commentMapper.createComment(comment);
            callback.callback(comment);
        };
        threadPool.execute(r);
    }

    @Override
    public void getCommentByTimeOrder(int artworkId, int page, CallbackForController callback) {
        Runnable r = ()->{
            List<CommentWithUserInfo> commentList = commentMapper.getCommentByTimeOrder(artworkId,page*PAGE_COUNT);
            List<CommentWithUserInfo> tempList = new ArrayList<>();
            for(CommentWithUserInfo comment: commentList){
                if(comment.getComment_replyCount()!=0){
                    tempList.addAll(commentMapper.getTwoChildComment(comment.getComment_id()));
                }
            }
            commentList.addAll(tempList);
            callback.callback(commentList);
        };
        threadPool.execute(r);
    }

    @Override
    public int test(final CallbackForController callback) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int x = 3;
                try{

                    Thread.sleep(3000);
                    System.out.println("sleep done");
                }
                catch (Exception e){

                }
                finally {

                }
                callback.callback("成功");
            }
        };
        threadPool.execute(r);
        return 0;
    }
}
