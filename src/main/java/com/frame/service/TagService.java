package com.frame.service;

public interface TagService {
    boolean addTag(int artworkId,String tagName,int userId);
    boolean deleteTag(int artworkId,String tagName,int userId);
}
