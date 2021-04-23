package com.frame.dao;

import com.frame.po.Artist;
import com.frame.po.User;

public interface UserMapper {
    public User getUserByName(String username);
    public Artist getUserByArtworkId(int artworkId,int userId);
    public void createUser(User user);
    public void updateAvatar(int userId,String location);
    public void updateFollowingOfUser(int userId,int count);
    public void updateFollowerOfUser(int userId,int count);
}
