package com.frame.service;

import com.frame.dao.UserMapper;
import com.frame.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Service
public class UserServiceImpl implements UserService {

    private static final String PATH = "E:\\Artworks";
    private static final String MAP_PATH = "/frame-artworks";

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
    public String updateAvatar(HttpServletRequest request) throws Exception{

        int userId = (int)request.getSession().getAttribute("userId");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");

        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf('.'));

        String location = MAP_PATH+"/user/"+userId+".jpg";
        userMapper.updateAvatar(userId,location);

        String path = PATH+ File.separator+"user";

        File mkfile = new File(path);
        if(!mkfile.exists()){
            mkfile.mkdirs();
        }
        File avatar = new File(path+File.separator+userId+".jpg");
        if(!avatar.exists()){
            avatar.delete();
        }
        file.transferTo(avatar);

        return location;
    }

    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setUser_name(username);
        user.setUser_password(password);
        userMapper.creatUser(user);
        return user;
    }

    @Override
    public User getUserByArtworkId(int artworkId) {
        return userMapper.getUserByArtworkId(artworkId);
    }
}
