package com.frame.controller;

import com.frame.dao.TestMapper;
import com.frame.po.ArtworkForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
public class TestController {
    @Autowired
    TestMapper testMapper;
    @RequestMapping("/test/testArtwork")
    public List<ArtworkForUser> test1(){
        return testMapper.getCollectionByPage(5,0,24,5);
    }
    @RequestMapping("/test/testArtwork2")
    public List<ArtworkForUser> test2(){
        List<ArtworkForUser> list = testMapper.getCollectionWithoutJoin(5,0,24);
        List<Integer> li=testMapper.getUserCollection(5);
        HashMap<Integer,Boolean> map = new HashMap<Integer, Boolean>();
        for(int id:li){
            map.put(id,true);
        }
        for(ArtworkForUser artwork:list){
            if(map.get(artwork.getArtwork_id())!=null){
                artwork.setHasCollect(true);
            }
            else{
                artwork.setHasCollect(false);
            }
        }

        return list;
    }
}
