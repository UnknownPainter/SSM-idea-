package com.frame.dao;

public interface FollowMapper {
    public void addFollow(int followerId,int followingId);
    public void deleteFollow(int followerId,int followingId);
}
