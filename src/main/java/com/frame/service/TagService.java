package com.frame.service;

import com.frame.po.ArtworkForUser;

import java.util.List;

public interface TagService {
    boolean addTag(int artworkId,String tagName,int userId);
    boolean deleteTag(int artworkId,String tagName,int userId);
    public List<ArtworkForUser> getArtworkByTag(String tagName, int page,int userId);
}
