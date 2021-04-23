package com.frame.controller;

import com.frame.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
public class FollowController {
    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    private FollowService followService;

    @RequestMapping(value = "/follow/{followingId}",method = RequestMethod.POST)
    public DeferredResult<Boolean> addFollowing(@PathVariable("followingId")int followingId, HttpSession session){
        final DeferredResult<Boolean> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(followService.addFollow((int)session.getAttribute("userId"),followingId));
        });
        return deferredResult;
    }
    @RequestMapping(value = "/follow/{followingId}",method = RequestMethod.DELETE)
    public DeferredResult<Boolean> deleteFollowing(@PathVariable("followingId")int followingId, HttpSession session){
        final DeferredResult<Boolean> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(followService.deleteFollow((int)session.getAttribute("userId"),followingId));
        });
        return deferredResult;
    }
}
