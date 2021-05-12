package com.frame.controller;

import com.frame.dao.TestMapper;
import com.frame.po.ArtworkForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.List;

@Controller
@ResponseBody
public class TestController {
    @Autowired
    TestMapper testMapper;
    @Autowired
    ThreadPoolTaskExecutor threadPool;
    @RequestMapping("/test/testArtwork")
    public List<ArtworkForUser> test1(){
        return testMapper.getCollectionByPage(5,0,24,5);
    }

    @RequestMapping("/test/testArtwork3")
    public DeferredResult<List<ArtworkForUser>> test3(){
        final DeferredResult<List<ArtworkForUser>> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(testMapper.getCollectionByPage(5,0,24,5));
        });
        return deferredResult;
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
