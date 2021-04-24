package com.frame.controller;


import com.frame.po.Artwork;
import com.frame.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@ResponseBody
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/collections/{artworkId}",method = RequestMethod.POST)
    public boolean createCollection(@PathVariable("artworkId") int artworkId, HttpServletRequest request){
        int userId = (int)request.getSession().getAttribute("userId");
        return collectionService.createCollection(artworkId,userId);
    }
    @RequestMapping(value = "/collections/{artworkId}",method = RequestMethod.DELETE)
    public boolean deleteCollection(@PathVariable("artworkId") int artworkId, HttpServletRequest request){
        int userId = (int)request.getSession().getAttribute("userId");
        return collectionService.deleteCollection(artworkId,userId);
    }
    @RequestMapping(value = "/collections/{page}",method = RequestMethod.GET)
    public List<Artwork> getCollections(@PathVariable("page")int page, HttpSession session){
        return collectionService.getCollectionByPage(page,(int)session.getAttribute("userId"));
    }
    
}
