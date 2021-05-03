package com.frame.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HotService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 * * * *")
    void updateHotArtwork() {
        Map map = redisTemplate.opsForHash().entries("artwork");
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        for(int i=0;i<10;i++){
            redisTemplate.boundHashOps("hotArtwork").put(String.valueOf(i),list.get(i).getKey());
        }
    }
}
