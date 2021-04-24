package com.frame.dao;

import com.frame.po.Artwork;

import java.util.List;

public interface CollectionMapper {
    void createCollection(int artworkId,int userId);
    void deleteCollection(int artworkId,int userId);
    List<Integer> getUserCollection(int userId);
    List<Artwork> getCollectionByPage(int userId,int page);
    int requestIfHasCollected(int userId,int artworkId);
}
