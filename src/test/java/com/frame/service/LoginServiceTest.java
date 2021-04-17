package com.frame.service;

import com.frame.component.ArtworkAsyncTask;
import com.frame.dao.*;
import com.frame.po.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.ch.ThreadPool;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class LoginServiceTest {

    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ArtworkMapper artworkMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    private ArtworkAsyncTask artworkAsyncTask;
    @Test
    public void login() {
        Comment comment = new Comment();
        comment.setComment_userId(5);
        comment.setComment_artworkId(46);
        comment.setComment_toId(44);
       for(int i=1;i<30;i++){
           comment.setComment_content("回复评论"+i);
           commentMapper.createReplyOfComment(comment);
       }
    }

    @Test
    @Transactional
    public void register() throws Exception
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                artworkAsyncTask.createArtwork(null,null);
                System.out.println("???");
            }
        };
        threadPool.execute(r);
        Thread.sleep(3000);
        String i="4";i.compareTo("4");

    }
}
