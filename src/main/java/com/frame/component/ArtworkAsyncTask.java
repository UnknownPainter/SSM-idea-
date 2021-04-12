package com.frame.component;

import com.frame.dao.ArtworkMapper;
import com.frame.dao.CollectionMapper;
import com.frame.dao.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class ArtworkAsyncTask {
    @Autowired
    private TagMapper tagMapper;

    @Transactional
    public void createArtwork(List<String> labels, HttpServletRequest request){

//        tagMapper.createTag(1,"fdgdg");
        int i=1/0;
    }
}
