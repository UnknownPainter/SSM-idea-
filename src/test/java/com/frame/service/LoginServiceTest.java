package com.frame.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frame.component.HotService;
import com.frame.dao.*;
import com.frame.po.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.Scheduled;
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
//        redisTemplate.boundListOps("hotTag").rightPush("封面");
//        redisTemplate.boundListOps("hotTag").rightPush("fgo");
//        redisTemplate.boundListOps("hotTag").rightPush("葛饰北斋");

    }
    @Test
    public void updateHotArtwork() {
        Map map = redisTemplate.opsForHash().entries("artwork");
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        redisTemplate.delete("hotArtwork");
        for(int i=0;i<10&&i<list.size();i++){
            Artwork artwork = artworkMapper.getArtworkById(Integer.parseInt(list.get(i).getKey()));
            redisTemplate.boundListOps("hotArtwork").rightPush(artwork);
        }

        redisTemplate.delete("artwork");
    }
    @Test
    public void updateHotTag() {
        Map map = redisTemplate.opsForHash().entries("tag");
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        redisTemplate.delete("hotTag");
        for(int i=0;i<10&&i<list.size();i++){
            redisTemplate.boundListOps("hotTag").rightPush(list.get(i).getKey());
        }
        redisTemplate.delete("tag");
    }

}
