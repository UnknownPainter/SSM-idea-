package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.TagMapper;
import com.frame.po.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArtworkMapper artworkMapper;

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
}
