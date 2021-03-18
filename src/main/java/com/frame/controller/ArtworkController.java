package com.frame.controller;

import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
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
    public void uploadArtwork(HttpServletRequest request) throws Exception{
        artworkService.createArtwork(request);
    }

    @RequestMapping(value = "/artworks/{artworkId}",method = RequestMethod.GET)
    public ArtworkForUser getArtwork(@PathVariable("artworkId") int artworkId,HttpServletRequest request){
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
}
