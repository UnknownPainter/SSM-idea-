package com.frame.dao;

import java.util.List;

public interface TagMapper {
    public void createTag(int artworkId,String tagName);
    public List<Integer> getArtworkIdByTag(String tagName,int page);
    public List<String> getTagOfArtwork(int artworkId);
}
