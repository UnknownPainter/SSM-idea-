package com.frame.service;

import com.frame.dao.FollowMapper;
import com.frame.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowServiceImpl implements FollowService{

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
}
