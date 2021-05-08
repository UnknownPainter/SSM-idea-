package com.frame.service;

import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;

import java.util.List;

public interface CollectionService {
    boolean createCollection(int artworkId,int userId);
    boolean deleteCollection(int artWorkId,int userId);
    List<ArtworkForUser> getCollectionByPage(int page, int userId, int artistId);
}
