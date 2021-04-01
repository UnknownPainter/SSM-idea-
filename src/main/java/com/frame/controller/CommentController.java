package com.frame.controller;

import com.frame.po.Comment;
import com.frame.service.CommentService;
import com.frame.utils.CallbackForController;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/comment/{artworkId}",method = RequestMethod.POST)
    public DeferredResult<Boolean> createComment(@PathVariable("artworkId")int artworkId, String content, HttpSession session){
        final DeferredResult<Boolean> deferredResult = new DeferredResult<>();
        commentService.createComment(artworkId,content,(int)session.getAttribute("userId"),(
                result -> {
                    deferredResult.setResult((Boolean)result);
                }
        ));
        return deferredResult;
    }
    @RequestMapping("/test")
    public DeferredResult<String> test(){
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        commentService.test(new CallbackForController() {
            @Override
            public void callback(Object result) {
                deferredResult.setResult((String)result);
            }
        });
        System.out.println("完成");
        return deferredResult;
    }
    @RequestMapping(value = "/comment/{artworkId}/timeorder/{page}",method = RequestMethod.GET)
    public DeferredResult<List<Comment>> getCommentByTimeOrder(@PathVariable("artworkId")int artworkId,@PathVariable("page")int page){
        final DeferredResult<List<Comment>> deferredResult = new DeferredResult<>();
        commentService.getCommentByTimeOrder(artworkId,page,(result -> {
            deferredResult.setResult((List<Comment>)result);
        }));
        return deferredResult;
    }
}
