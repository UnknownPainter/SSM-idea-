package com.frame.service;

import com.frame.po.Artwork;

import java.util.List;

public interface CollectionService {
    boolean createCollection(int artworkId,int userId);
    boolean deleteCollection(int artWorkId,int userId);
    List<Artwork> getCollectionByPage(int page,int userId);
    int getCollectionCount(int userId);
}
