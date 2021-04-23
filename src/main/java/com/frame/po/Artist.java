package com.frame.po;

import java.util.List;

public class Artist extends User{
    private boolean hasFollow;
    private List<Artwork> sampleArtworks;

    public boolean isHasFollow() {
        return hasFollow;
    }

    public void setHasFollow(boolean hasFollow) {
        this.hasFollow = hasFollow;
    }

    public List<Artwork> getSampleArtworks() {
        return sampleArtworks;
    }

    public void setSampleArtworks(List<Artwork> sampleArtworks) {
        this.sampleArtworks = sampleArtworks;
    }
}
