package com.frame.service;

import com.frame.po.Artist;
import com.frame.po.ArtworkForUser;

import java.util.List;

public interface FollowService {
    public boolean addFollow(int followerId,int followingId);
    public boolean deleteFollow(int followerId,int followingId);
    public List<ArtworkForUser> getFollowArtwork(int userId,int page);
    public List<Artist> getAllFollower(int userId,int artistId,int page);
    public List<Artist> getAllFollowing(int userId,int artistId,int page);
}
