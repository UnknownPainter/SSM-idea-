package com.frame.controller;

import com.frame.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
public class TagController {
    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/tag/{artworkId}/{tagName}",method = RequestMethod.POST)
    public boolean addTag(@PathVariable("artworkId")int artworkId, @PathVariable("tagName")String tagName, HttpSession session){
        return tagService.addTag(artworkId,tagName,(int)session.getAttribute("userId"));
    }
    @RequestMapping(value = "/tag/{artworkId}/{tagName}",method = RequestMethod.DELETE)
    public boolean deleteTag(@PathVariable("artworkId")int artworkId, @PathVariable("tagName")String tagName, HttpSession session){
        return tagService.deleteTag(artworkId,tagName,(int)session.getAttribute("userId"));
    }
}
