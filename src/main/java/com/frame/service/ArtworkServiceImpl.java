package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CollectionMapper;
import com.frame.dao.TagMapper;
import com.frame.dao.UserMapper;
import com.frame.po.Artist;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.ArtworkWithLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Service
public class ArtworkServiceImpl implements ArtworkService{

    private static final String PATH = "E:\\Artworks";
    private static final String MAP_PATH = "/frame-artworks";
    private static final int PAGE_COUNT = 24;

    @Autowired
    private ArtworkMapper artworkMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;


    @Transactional
    @Override
    public void createArtwork(List<String> labels,HttpServletRequest request){
        Artwork artwork = new Artwork();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf('.'));

        artwork.setArtwork_comment(request.getParameter("comment"));
        artwork.setArtwork_artistId((int)request.getSession().getAttribute("userId"));
        artwork.setArtwork_name(request.getParameter("name"));
        artwork.setArtwork_location("");
        artwork.setArtwork_height(image.getHeight());
        artwork.setArtwork_width(image.getWidth());
        artwork.setArtwork_group(Integer.valueOf(request.getParameter("group")));

        artworkMapper.creatArtwork(artwork);

        String path = PATH+ File.separator+artwork.getArtwork_artistId();

        File mkfile = new File(path);
        if(!mkfile.exists()){
            mkfile.mkdirs();
        }
        try {
            file.transferTo(new File(path+File.separator+artwork.getArtwork_id()+fileType));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        artworkMapper.updateArtworkLocationById(MAP_PATH+"/"+artwork.getArtwork_artistId()+"/"+artwork.getArtwork_id()+fileType,artwork.getArtwork_id());
        userMapper.updateArtworkCountOfUser(artwork.getArtwork_artistId(),1);
        for(String label:labels){
            tagMapper.createTag(artwork.getArtwork_id(),label);
        }

    }

    @Override
    public ArtworkWithLabel getArtwork(int artworkId, int userId) {
        redisTemplate.boundHashOps("artwork").increment(String.valueOf(artworkId),1);
        ArtworkWithLabel artworkWithLabel = artworkMapper.getArtworkById(artworkId);
        if(artworkWithLabel==null)return null;
        artworkWithLabel.setHasCollect(false);
        artworkWithLabel.setHasPraise(false);
        if(userId==-1){
            artworkWithLabel.setHasCollect(false);
        }
        else{
            if(collectionMapper.requestIfHasCollected(userId,artworkId)!=0){
                artworkWithLabel.setHasCollect(true);
            }
        }
        artworkWithLabel.setLabel(tagMapper.getTagOfArtwork(artworkId));
        for(String tag : artworkWithLabel.getLabel()){
            redisTemplate.boundHashOps("tag").increment(tag,1);
        }
        return artworkWithLabel;
    }

    @Override
    public List<ArtworkForUser> getHomeArtwork(int userId,int page,int group) {
        List<ArtworkForUser> list = artworkMapper.getDefaultArtwork(page*15,group);
        if(userId==-1){
            for(ArtworkForUser artworkForUser:list){
                artworkForUser.setHasPraise(false);
                artworkForUser.setHasCollect(false);
            }
        }
        else{
            List<Integer> li = collectionMapper.getUserCollection(userId);
            HashMap<Integer,Boolean> map = new HashMap<Integer, Boolean>();
            for(int id:li){
                map.put(id,true);
            }
            for(ArtworkForUser artwork:list){
                if(map.get(artwork.getArtwork_id())!=null){
                    artwork.setHasCollect(true);
                }
                else{
                    artwork.setHasCollect(false);
                }
            }
        }

        return list;
    }

    @Override
    public List<ArtworkForUser> getUserArtwork(int page, int userId,int artistId) {
        return artworkMapper.getUserArtwork(artistId,page*PAGE_COUNT,PAGE_COUNT,userId);
    }

    @Override
    public boolean deleteArtwork(int id, int userId) {
        Artwork artwork = artworkMapper.getArtworkById(id);
        String location = artwork.getArtwork_location().replace(MAP_PATH,PATH);
        location = location.replace("/","\\");
        System.out.println(location);
        if(artwork.getArtwork_artistId()==userId){
            File file = new File(location);
            if(file.exists()){
                file.delete();
                artworkMapper.deleteArtwork(id,userId);
            }
        }
        userMapper.updateArtworkCountOfUser(artwork.getArtwork_artistId(),-1);
        return true;
    }

    @Override
    public boolean adminDelete(int id, int userId, int artistId) {
        Artist artist = userMapper.getUserById(-1,userId);
        if(artist.getUser_role()==2){
            return deleteArtwork(id,artistId);
        }
        return false;
    }
}
