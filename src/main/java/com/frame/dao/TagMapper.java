package com.frame.dao;

import com.frame.po.ArtworkForUser;

import java.util.List;

public interface TagMapper {
    public void createTag(int artworkId,String tagName);
    public List<ArtworkForUser> getArtworkByTag(String tagName, int page,int userId,int group);
    public int getSearchCountOfTag(String tagName,int group);
    public List<String> getTagOfArtwork(int artworkId);
    public int getTagCount(int artworkId);
    public int deleteTag(int artworkId,String tagName);
}
