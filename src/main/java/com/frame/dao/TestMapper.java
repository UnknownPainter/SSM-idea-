package com.frame.dao;

import com.frame.po.ArtworkForUser;

import java.util.List;

public interface TestMapper {
    List<ArtworkForUser> getCollectionByPage(int artistId, int page, int limit, int userId);
    List<ArtworkForUser> getCollectionWithoutJoin(int artistId, int page, int limit);
    int requestIfHasCollected(int userId,int artworkId);
    List<Integer> getUserCollection(int userId);
}
