package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CollectionMapper;
import com.frame.dao.UserMapper;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CollectionServiceImpl implements CollectionService{

    final private int COUNT_PER_PAGE = 24;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ArtworkMapper artworkMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public boolean createCollection(int artworkId, int userId) {

        collectionMapper.createCollection(artworkId,userId);
        redisTemplate.boundHashOps("artworkCollectCount").increment(String.valueOf(artworkId),1);
        redisTemplate.boundHashOps("userCollectCount").increment(String.valueOf(userId),1);
//        artworkMapper.updateArtworkCollectCount(artworkId,1);
//        userMapper.updateCollectionCountOfUser(userId,1);
        return true;
    }

    @Override
    public boolean deleteCollection(int artWorkId, int userId) {

        collectionMapper.deleteCollection(artWorkId,userId);
        artworkMapper.updateArtworkCollectCount(artWorkId,-1);
        userMapper.updateCollectionCountOfUser(userId,-1);
        return true;
    }


    @Override
    public List<ArtworkForUser> getCollectionByPage(int page, int userId, int artistId) {
        return collectionMapper.getCollectionByPage(artistId,page*COUNT_PER_PAGE,COUNT_PER_PAGE,userId);
    }
}
