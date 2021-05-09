package com.frame.dao;

import com.frame.po.Artist;
import com.frame.po.ArtworkForUser;
import com.frame.po.User;

import java.util.List;

public interface FollowMapper {
    public void addFollow(int followerId,int followingId);
    public void deleteFollow(int followerId,int followingId);
    public List<ArtworkForUser> getFollowArtwork(int userId, int page);
    public List<Artist> getAllFollower(int artistId,int userId,int page);
    public List<Artist> getAllFollowing(int artistId,int userId,int page);
}
