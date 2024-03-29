package com.frame.service;

import com.frame.dao.FollowMapper;
import com.frame.dao.UserMapper;
import com.frame.po.Artist;
import com.frame.po.ArtworkForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService{

    private static final int PAGE_COUNT = 24;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FollowMapper followMapper;

    @Override
    @Transactional
    public boolean addFollow(int followerId, int followingId) {
        followMapper.addFollow(followerId,followingId);
        userMapper.updateFollowingOfUser(followerId,1);
        userMapper.updateFollowerOfUser(followingId,1);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteFollow(int followerId, int followingId) {
        followMapper.deleteFollow(followerId,followingId);
        userMapper.updateFollowingOfUser(followerId,-1);
        userMapper.updateFollowerOfUser(followingId,-1);
        return true;
    }

    @Override
    public List<ArtworkForUser> getFollowArtwork(int userId, int page) {
        return followMapper.getFollowArtwork(userId,page*PAGE_COUNT);
    }

    @Override
    public List<Artist> getAllFollower(int userId, int artistId,int page) {
        return followMapper.getAllFollower(artistId,userId,page*PAGE_COUNT);
    }

    @Override
    public List<Artist> getAllFollowing(int userId, int artistId,int page) {
        return followMapper.getAllFollowing(artistId,userId,page*PAGE_COUNT);
    }
}
