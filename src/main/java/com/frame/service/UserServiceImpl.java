package com.frame.service;

import com.frame.dao.UserMapper;
import com.frame.po.Artist;
import com.frame.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisTemplate redisTemplate;
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
            Integer count = (Integer) redisTemplate.boundHashOps("userCollectCount").get(String.valueOf(user.getUser_id()));
            if(count==null) return user;
            user.setUser_collectionCount(count+user.getUser_collectionCount());
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
        userMapper.createUser(user);
        return user;
    }

    @Override
    public Artist getUserByArtworkId(int artworkId,int userId) {
        return userMapper.getUserByArtworkId(artworkId,userId);
    }

    @Override
    public boolean updateComment(int userId, String comment) {
        userMapper.updateCommentOfUser(userId,comment);
        return true;
    }

    @Override
    public Artist getUserById(int userId, int artistId) {
        Artist user = userMapper.getUserById(userId,artistId);
        Integer count = (Integer) redisTemplate.boundHashOps("userCollectCount").get(String.valueOf(user.getUser_id()));
        if(count==null) return user;
        user.setUser_collectionCount(count+user.getUser_collectionCount());
        return user;
    }

    @Override
    public boolean alterRole(int userId, int artistId, int role) {
        Artist artist = userMapper.getUserById(-1,userId);
        if(artist.getUser_role()==2){
            userMapper.updateUserRole(artistId,role);
            return true;
        }
        return false;
    }
}
