package com.frame.controller;

import com.frame.po.Artist;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
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
import java.util.List;

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
    @RequestMapping(value = "/follow/artwork/{page}",method = RequestMethod.GET)
    public DeferredResult<List<ArtworkForUser>> getFollowArtwork(@PathVariable("page")int page, HttpSession session){
        final DeferredResult<List<ArtworkForUser>> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(followService.getFollowArtwork((int)session.getAttribute("userId"),page));
        });
        return deferredResult;
    }
    @RequestMapping(value = "/follow/following/{id}/{page}",method = RequestMethod.GET)
    public DeferredResult<List<Artist>> getFollowing(@PathVariable("page")int page, @PathVariable("id")int id, HttpSession session){
        final DeferredResult<List<Artist>> deferredResult = new DeferredResult<>();
        Object obj = session.getAttribute("userId");
        int userId = obj==null?-1:(int)obj;
        threadPool.execute(()->{
            deferredResult.setResult(followService.getAllFollowing(userId,id,page));
        });
        return deferredResult;
    }
    @RequestMapping(value = "/follow/follower/{id}/{page}",method = RequestMethod.GET)
    public DeferredResult<List<Artist>> getFollower(@PathVariable("page")int page, @PathVariable("id")int id, HttpSession session){
        final DeferredResult<List<Artist>> deferredResult = new DeferredResult<>();
        Object obj = session.getAttribute("userId");
        int userId = obj==null?-1:(int)obj;
        threadPool.execute(()->{
            deferredResult.setResult(followService.getAllFollower(userId,id,page));
        });
        return deferredResult;
    }
}
