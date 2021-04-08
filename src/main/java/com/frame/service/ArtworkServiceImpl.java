package com.frame.service;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CollectionMapper;
import com.frame.dao.TagMapper;
import com.frame.po.Artwork;
import com.frame.po.ArtworkForUser;
import com.frame.po.ArtworkWithLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
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

    @Override
    public int getUserArtworkCount(int userId) {
        return artworkMapper.getUserArtworkCount(userId);
    }

    @Transactional
    @Override
    public boolean createArtwork(List<String> labels,HttpServletRequest request) throws Exception{
        Artwork artwork = new Artwork();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        BufferedImage image = ImageIO.read(file.getInputStream());

        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf('.'));

        artwork.setArtwork_comment(request.getParameter("comment"));
        artwork.setArtwork_artistId((int)request.getSession().getAttribute("userId"));
        artwork.setArtwork_name(request.getParameter("name"));
        artwork.setArtwork_location("");
        artwork.setArtwork_height(image.getHeight());
        artwork.setArtwork_width(image.getWidth());

        artworkMapper.creatArtwork(artwork);

        String path = PATH+ File.separator+artwork.getArtwork_artistId();

        File mkfile = new File(path);
        if(!mkfile.exists()){
            mkfile.mkdirs();
        }
        file.transferTo(new File(path+File.separator+artwork.getArtwork_id()+fileType));

        artworkMapper.updateArtworkLocationById(MAP_PATH+"/"+artwork.getArtwork_artistId()+"/"+artwork.getArtwork_id()+fileType,artwork.getArtwork_id());
        for(String label:labels){
            tagMapper.createTag(artwork.getArtwork_id(),label);
        }

        return true;
    }

    @Override
    public ArtworkWithLabel getArtwork(int artworkId, int userId) {
        ArtworkWithLabel artworkWithLabel = artworkMapper.getArtworkById(artworkId);
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
        return artworkWithLabel;
    }

    @Override
    public List<ArtworkForUser> getHomeArtwork(int userId,int page) {
        List<ArtworkForUser> list = artworkMapper.getDefaultArtwork(page*15);
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
    public List<Artwork> getUserArtwork(int page, int userId) {
        return artworkMapper.getUserArtwork(userId,page*PAGE_COUNT);
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
        return true;
    }
}
