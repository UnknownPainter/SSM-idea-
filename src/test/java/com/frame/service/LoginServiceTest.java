package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CollectionMapper;
import com.frame.dao.CommentMapper;
import com.frame.dao.UserMapper;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.Comment;
import com.frame.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    @Test
    public void login() {
        Comment comment = new Comment();
        comment.setComment_userId(5);
        comment.setComment_artworkId(46);
       for(int i=1;i<30;i++){
           comment.setComment_content("评论"+i);
           commentMapper.createComment(comment);
       }
    }

    @Test
    public void register() {
    }
}
