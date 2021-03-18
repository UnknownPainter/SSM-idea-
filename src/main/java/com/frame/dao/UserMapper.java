package com.frame.dao;

import com.frame.po.User;

public interface UserMapper {
    public User getUserByName(String username);
    public void creatUser(User user);
}
