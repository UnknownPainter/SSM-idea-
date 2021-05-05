package com.frame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<String> getHotTag() {
        return redisTemplate.opsForList().range("hotTag",0,redisTemplate.opsForList().size("hotTag"));
    }
}
