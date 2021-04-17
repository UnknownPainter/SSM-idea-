package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CollectionMapper;
import com.frame.po.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public boolean createCollection(int artworkId, int userId) {

        collectionMapper.createCollection(artworkId,userId);
        artworkMapper.updateArtworkCollectCount(artworkId,1);
        return true;
    }

    @Override
    public boolean deleteCollection(int artWorkId, int userId) {

        collectionMapper.deleteCollection(artWorkId,userId);
        artworkMapper.updateArtworkCollectCount(artWorkId,-1);
        return true;
    }

    @Override
    public int getCollectionCount(int userId) {
        return collectionMapper.getCollectionCount(userId);
    }

    @Override
    public List<Artwork> getCollectionByPage(int page,int userId) {
        return collectionMapper.getCollectionByPage(userId,page*COUNT_PER_PAGE);
    }
}
