package com.frame.service;

import com.frame.dao.CommentMapper;
import com.frame.po.Comment;
import com.frame.utils.CallbackForController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void createComment(int artworkId, String content, int userId,CallbackForController callback) {
        Runnable r = ()->{
            Comment comment = new Comment();
            comment.setComment_content(content);
            comment.setComment_artworkId(artworkId);
            comment.setComment_userId(userId);
            commentMapper.createComment(comment);
            callback.callback(true);
        };
        threadPool.execute(r);
    }

    @Override
    public void getCommentByTimeOrder(int artworkId, int page, CallbackForController callback) {
        Runnable r = ()->{
            callback.callback(commentMapper.getCommentByTimeOrder(artworkId,page));
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
