package com.frame.service;

import com.frame.dao.UserMapper;
import com.frame.po.Artist;
import com.frame.po.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User login(String username, String password);
    User register(String username,String password);
    boolean getName(String username);
    String updateAvatar(HttpServletRequest request) throws Exception;
    Artist getUserByArtworkId(int artworkId,int userId);
}
