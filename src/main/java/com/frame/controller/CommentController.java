package com.frame.controller;

import com.frame.po.Comment;
import com.frame.po.CommentWithUserInfo;
import com.frame.service.CommentService;
import com.frame.utils.CallbackForController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@ResponseBody
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @RequestMapping(value = "/comment/{artworkId}",method = RequestMethod.POST)
    public DeferredResult<Comment> createComment(@PathVariable("artworkId")int artworkId, String content, HttpSession session){
        final DeferredResult<Comment> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(commentService.createComment(artworkId,content,(int)session.getAttribute("userId")));
        });
        return deferredResult;
    }

    @RequestMapping(value = "/comment/{artworkId}/timeorder/{page}",method = RequestMethod.GET)
    public DeferredResult<List<CommentWithUserInfo>> getCommentByTimeOrder(@PathVariable("artworkId")int artworkId, @PathVariable("page")int page){
        final DeferredResult<List<CommentWithUserInfo>> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(commentService.getCommentByTimeOrder(artworkId,page));
        });
        return deferredResult;
    }
    @RequestMapping(value = "/comment/{artworkId}/{commentId}",method = RequestMethod.POST)
    public DeferredResult<Comment> createReply(@PathVariable("artworkId")int artworkId, @PathVariable("commentId")int commentId,String content, HttpSession session){
        final DeferredResult<Comment> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(commentService.createReplyOfComment(commentId,artworkId,content,(int)session.getAttribute("userId")));
        });
        return deferredResult;
    }
    @RequestMapping(value = "/comment/child/{commentId}/{page}",method = RequestMethod.GET)
    public DeferredResult<List<CommentWithUserInfo>> getCommentChild(@PathVariable("commentId")int commentId, @PathVariable("page")int page){
        final DeferredResult<List<CommentWithUserInfo>> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(commentService.getChildComment(commentId,page));
        });
        return deferredResult;
    }
}
