package com.frame.controller;

import com.frame.service.CommentService;
import com.frame.utils.CallbackForController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@ResponseBody
public class CommentController {
    @Autowired
    private CommentService commentService;

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
}
