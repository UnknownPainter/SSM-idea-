package com.frame.service;

import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.ArtworkWithLabel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ArtworkService {
    public void createArtwork(List<String> labels,HttpServletRequest request);
    public ArtworkWithLabel getArtwork(int artworkId, int userId);
    public List<ArtworkForUser> getHomeArtwork(int userId,int page,int group);
    public boolean deleteArtwork(int id,int userId);
    public List<ArtworkForUser> getUserArtwork(int page,int userId,int artistId);
    public boolean adminDelete(int id,int userId,int artistId);
}
