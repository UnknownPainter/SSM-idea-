package com.frame.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.ArtworkWithLabel;
import com.frame.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value = "/artworks",method = RequestMethod.POST)
    public boolean uploadArtwork(@RequestParam("labels")String labels, HttpServletRequest request) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> labelList = objectMapper.readValue(labels, new TypeReference<List<String>>() {});
        return artworkService.createArtwork(labelList,request);
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
    public List<Artwork> getUserArtwork(@PathVariable("page") int page, HttpSession session){
        return artworkService.getUserArtwork(page,(int)session.getAttribute("userId"));
    }
    @RequestMapping(value = "/user/artworks/count",method = RequestMethod.GET)
    public int getUserArtworkCount(HttpSession session){
        return artworkService.getUserArtworkCount((int)session.getAttribute("userId"));
    }
    @RequestMapping(value = "/artworks/{artworkId}",method = RequestMethod.DELETE)
    public boolean deleteArtwork(@PathVariable("artworkId")int artworkId,HttpSession session){
        artworkService.deleteArtwork(artworkId,(int)session.getAttribute("userId"));
        return true;
    }
}
