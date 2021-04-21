package com.frame.service;

public interface FollowService {
    public boolean addFollow(int followerId,int followingId);
    public boolean deleteFollow(int followerId,int followingId);
}
