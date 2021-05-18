package com.frame.service;

import com.frame.po.ArtworkForUser;

import java.util.List;

public interface TagService {
    public boolean addTag(int artworkId,String tagName,int userId);
    public boolean deleteTag(int artworkId,String tagName,int userId);
    public List<ArtworkForUser> getArtworkByTag(String tagName, int page,int userId,int group);
    public int getSearchCountOfTag(String name,int group);
}
