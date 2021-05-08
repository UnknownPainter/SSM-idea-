package com.frame.service;

import com.frame.po.Artwork;

import java.util.List;

public interface SearchService {
    List<String> getHotTag();
    List<Artwork> getHotArtwork();
}
