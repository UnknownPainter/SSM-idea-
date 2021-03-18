package com.frame.dao;

import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;

import java.util.List;

public interface ArtworkMapper {
    public ArtworkForUser getArtworkById(int id);
    public ArtworkForUser getArtworkByName();
    public List<ArtworkForUser> getDefaultArtwork(int page);
    public void updateArtworkLocationById(String location,int id);
    public void creatArtwork(Artwork artwork);
    public void deleteArtwork(int id,int userId);
    public List<Artwork> getUserArtwork(int userId,int page);
}
