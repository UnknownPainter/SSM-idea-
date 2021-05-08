package com.frame.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.ArtworkWithLabel;
import com.frame.po.Comment;
import com.frame.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@Controller
@ResponseBody
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @RequestMapping(value = "/artworks",method = RequestMethod.POST)
    public DeferredResult<Boolean> uploadArtwork(@RequestParam("labels")String labels, HttpServletRequest request) throws Exception{
        final DeferredResult<Boolean> deferredResult = new DeferredResult<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> labelList = objectMapper.readValue(labels, new TypeReference<List<String>>() {});
        threadPool.execute(()->{
            artworkService.createArtwork(labelList,request);
            deferredResult.setResult(true);
        });
        return deferredResult;
    }

    @RequestMapping(value = "/artworks/{artworkId}",method = RequestMethod.GET)
    public ArtworkWithLabel getArtwork(@PathVariable("artworkId") int artworkId, HttpServletRequest request){
        Object userId = request.getSession().getAttribute("userId");
        if(userId!=null){
            return artworkService.getArtwork(artworkId,(int)userId);
        }
        else{
            return artworkService.getArtwork(artworkId,-1);
        }
    }

    @RequestMapping(value = "/artworks/home/{page}",method = RequestMethod.GET)
    public List<ArtworkForUser> getHomeArtwork(@PathVariable("page") int page,HttpServletRequest request){
        Object userId = request.getSession().getAttribute("userId");
        if(userId!=null){
            return artworkService.getHomeArtwork((int)userId,page);
        }
        else{
            return artworkService.getHomeArtwork(-1,page);
        }
    }
    @RequestMapping(value = "/user/artworks/{page}",method = RequestMethod.GET)
    public List<ArtworkForUser> getUserArtwork(@PathVariable("page") int page, HttpSession session){
        return artworkService.getUserArtwork(page,0,(int)session.getAttribute("userId"));
    }
    @RequestMapping(value = "/artist/{id}/artworks/{page}",method = RequestMethod.GET)
    public List<ArtworkForUser> getArtistArtwork(@PathVariable("id")int id,@PathVariable("page")int page,HttpSession session){
        Object obj = session.getAttribute("userId");
        int userId = obj==null?-1:(int)obj;
        return artworkService.getUserArtwork(page,userId,id);
    }

    @RequestMapping(value = "/artworks/{artworkId}",method = RequestMethod.DELETE)
    public boolean deleteArtwork(@PathVariable("artworkId")int artworkId,HttpSession session){
        artworkService.deleteArtwork(artworkId,(int)session.getAttribute("userId"));
        return true;
    }
}
