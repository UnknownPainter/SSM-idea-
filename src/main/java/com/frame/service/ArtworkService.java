package com.frame.service;

import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.ArtworkWithLabel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ArtworkService {
    public boolean createArtwork(List<String> labels,HttpServletRequest request)throws Exception;
    public ArtworkWithLabel getArtwork(int artworkId, int userId);
    public List<ArtworkForUser> getHomeArtwork(int userId,int page);
    public boolean deleteArtwork(int id,int userId);
    public List<Artwork> getUserArtwork(int page,int userId);
    public int getUserArtworkCount(int userId);
}
