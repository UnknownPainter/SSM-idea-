package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.TagMapper;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArtworkMapper artworkMapper;

    private static final int PAGE_COUNT = 20;

    @Override
    @Transactional
    public boolean addTag(int artworkId, String tagName,int userId) {

        Artwork artwork;
        if((artwork = artworkMapper.getArtworkById(artworkId))==null||artwork.getArtwork_artistId()!=userId){
            return false;
        }
        if(tagMapper.getTagCount(artworkId)<5){
            tagMapper.createTag(artworkId,tagName);
        }
        else{
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteTag(int artworkId, String tagName,int userId) {
        Artwork artwork;
        if((artwork = artworkMapper.getArtworkById(artworkId))==null||artwork.getArtwork_artistId()!=userId){
            return false;
        }
        tagMapper.deleteTag(artworkId,tagName);
        return true;
    }

    @Override
    public List<ArtworkForUser> getArtworkByTag(String tagName, int page, int userId,int group) {
        return tagMapper.getArtworkByTag(tagName,page*PAGE_COUNT,userId,group);
    }

    @Override
    public int getSearchCountOfTag(String name,int group) {
        return tagMapper.getSearchCountOfTag(name,group);
    }
}
