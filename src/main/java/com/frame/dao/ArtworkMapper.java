package com.frame.dao;

import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.ArtworkWithLabel;

import java.util.List;

public interface ArtworkMapper {
    public ArtworkWithLabel getArtworkById(int id);
    public ArtworkForUser getArtworkByName();
    public List<ArtworkForUser> getDefaultArtwork(int page);
    public void updateArtworkLocationById(String location,int id);
    public void creatArtwork(Artwork artwork);
    public void deleteArtwork(int id,int userId);
    public List<Artwork> getUserArtwork(int userId,int page);
    public void updateArtworkCollectCount(int artworkId,int count);
}
