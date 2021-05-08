package com.frame.dao;

import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;

import java.util.List;

public interface CollectionMapper {
    void createCollection(int artworkId,int userId);
    void deleteCollection(int artworkId,int userId);
    List<Integer> getUserCollection(int userId);
    List<ArtworkForUser> getCollectionByPage(int artistId, int page, int limit,int userId);
    int requestIfHasCollected(int userId,int artworkId);
}
