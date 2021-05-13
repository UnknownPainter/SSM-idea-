package com.frame.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frame.component.HotService;
import com.frame.dao.*;
import com.frame.po.*;
import com.frame.utils.RedisUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import redis.clients.jedis.Jedis;
import sun.nio.ch.ThreadPool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    @Autowired
    RedisUtils redisUtils;
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
        List<CommentWithUserInfo> comment = commentMapper.getChildComment(300078,0);
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

    @Test
    @Transactional
    public void dataBaseInsert()
    {
//        List<ArtworkForUser> artworks = artworkMapper.getUserArtwork(5,0,20,5);
//        for(int i=0;i<100000;i++){
//            int r = (int)(Math.random()*20);
//
//            Artwork artwork = new Artwork();
//
//            artwork.setArtwork_comment("留言1");
//            artwork.setArtwork_artistId(12+(int)(Math.random()*9900));
//            artwork.setArtwork_name("测试作品"+i);
//            artwork.setArtwork_location(artworks.get(r).getArtwork_location());
//            artwork.setArtwork_height(artworks.get(r).getArtwork_height());
//            artwork.setArtwork_width(artworks.get(r).getArtwork_width());
//            artworkMapper.creatArtwork(artwork);
//
//        }
//        for(int i=0;i<10000;i++){
//            User user = new User();
//            user.setUser_name("用户"+i);
//            user.setUser_password("123456");
//            userMapper.createUser(user);
//        }
//        for(int i=0;i<300000;i++){
//            Comment comment = new Comment();
//            comment.setComment_content("评论测试数据"+i);
//            comment.setComment_artworkId(50+(int)(Math.random()*9900));
//            comment.setComment_userId(12+(int)(Math.random()*9900));
//            comment.setComment_createTime(new Date());
//            commentMapper.createComment(comment);
//        }
        for(int i=0;i<80000;i++){
            int userId = 12+(int)(Math.random()*9900);
            int artworkId = 50+(int)(Math.random()*9900);
            collectionMapper.createCollection(artworkId,userId);
            artworkMapper.updateArtworkCollectCount(artworkId,1);
            userMapper.updateCollectionCountOfUser(userId,1);
        }
    }

}
