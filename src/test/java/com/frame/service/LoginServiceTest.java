package com.frame.service;

import com.frame.dao.*;
import com.frame.po.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
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
    private  UserMapper userMapper;
    @Autowired
    FollowMapper followMapper;
    @Autowired
    RedisTemplate redisTemplate;
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
    public void register() throws Exception
    {

//        List<ArtworkForUser> list = tagMapper.getArtworkIdByTag("fgo",0,5);
//        System.out.println(list.get(0).isHasCollect());

//        Jedis redis = new Jedis("localhost");
//        redis.set("a","ee")
//        artworkMapper.getArtworkById(46);
        redisTemplate.boundValueOps("dff").set("dddd");
    }

}
