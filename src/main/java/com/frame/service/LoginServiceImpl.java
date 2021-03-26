package com.frame.service;

import com.frame.dao.UserMapper;
import com.frame.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserMapper userMapper;
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean getName(String username) {
        User user = userMapper.getUserByName(username);
        if(user!=null){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.getUserByName(username);
        if (user!=null&&password.equals(user.getUser_password())){
            return user;
        }
        return null;
    }

    @Override
    public String updateAvatar(HttpServletRequest request) {
        return "yes";
    }

    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setUser_name(username);
        user.setUser_password(password);
        userMapper.creatUser(user);
        return user;
    }
}
