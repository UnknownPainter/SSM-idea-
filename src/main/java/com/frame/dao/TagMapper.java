package com.frame.dao;

import com.frame.po.ArtworkForUser;

import java.util.List;

public interface TagMapper {
    public void createTag(int artworkId,String tagName);
    public List<ArtworkForUser> getArtworkIdByTag(String tagName, int page,int userId);
    public List<String> getTagOfArtwork(int artworkId);
    public int getTagCount(int artworkId);
    public int deleteTag(int artworkId,String tagName);
}
