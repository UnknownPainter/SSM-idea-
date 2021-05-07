package com.frame.dao;

import com.frame.po.ArtworkForUser;

import java.util.List;

public interface FollowMapper {
    public void addFollow(int followerId,int followingId);
    public void deleteFollow(int followerId,int followingId);
    public List<ArtworkForUser> getFollowArtwork(int userId, int page);
}
