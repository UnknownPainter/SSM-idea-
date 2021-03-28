package com.frame.dao;

import com.frame.po.User;

public interface UserMapper {
    public User getUserByName(String username);
    public User getUserByArtworkId(int userId);
    public void creatUser(User user);
    public void updateAvatar(int userId,String location);
}
