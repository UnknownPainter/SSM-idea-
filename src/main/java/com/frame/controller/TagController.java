package com.frame.controller;

import com.frame.po.ArtworkForUser;
import com.frame.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@ResponseBody
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @RequestMapping(value = "/tag/{artworkId}/{tagName}",method = RequestMethod.POST)
    public boolean addTag(@PathVariable("artworkId")int artworkId, @PathVariable("tagName")String tagName, HttpSession session){
        return tagService.addTag(artworkId,tagName,(int)session.getAttribute("userId"));
    }
    @RequestMapping(value = "/tag/{artworkId}/{tagName}",method = RequestMethod.DELETE)
    public boolean deleteTag(@PathVariable("artworkId")int artworkId, @PathVariable("tagName")String tagName, HttpSession session){
        return tagService.deleteTag(artworkId,tagName,(int)session.getAttribute("userId"));
    }
    @RequestMapping(value = "/tag/artwork/{group}/{tagName}/{page}",method = RequestMethod.GET)
    public DeferredResult<List<ArtworkForUser>> getArtworkByTag(@PathVariable("tagName")String tagName,@PathVariable("page")int page,@PathVariable("group")int group ,HttpSession session){
        final DeferredResult<List<ArtworkForUser>> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            Object userId = session.getAttribute("userId");
            if(userId==null)
                deferredResult.setResult(tagService.getArtworkByTag(tagName,page,-1,group));
            else
                deferredResult.setResult(tagService.getArtworkByTag(tagName,page,(int)userId,group));
        });
        return deferredResult;
    }
    @RequestMapping(value = "/tag/artwork/{group}/{tagName}/count",method = RequestMethod.GET)
    public DeferredResult<Integer> getArtworkByTag(@PathVariable("tagName")String tagName,@PathVariable("group")int group){
        final DeferredResult<Integer> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(tagService.getSearchCountOfTag(tagName,group));
        });
        return deferredResult;
    }
}
