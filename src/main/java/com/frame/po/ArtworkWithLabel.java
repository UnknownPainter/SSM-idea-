package com.frame.po;

import java.util.List;

public class ArtworkWithLabel extends ArtworkForUser{
    private List<String> label;

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }
}
