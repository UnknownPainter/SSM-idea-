package com.frame.service;

import com.frame.dao.UserMapper;
import com.frame.po.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface LoginService {

    User login(String username, String password);
    boolean register(String username,String password);
    boolean getName(String username);
}
