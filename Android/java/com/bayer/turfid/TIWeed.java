package com.bayer.turfid;

public class TIWeed {
    public Integer WeedId;
    public String WeedName;
    public String WeedFamily;
    public String WeedDescription;
    public String YoungImage;
    public String FlowerImage;
    public String IsGrassWeed;

    public TIWeed(Integer Id, String name, String family, String desc, String yngImg, String floImg, String isGrassWeed) {
        this.WeedId = Id;
        this.WeedName = name;
        this.WeedFamily = family;
        this.WeedDescription = desc;
        this.YoungImage = yngImg;
        this.FlowerImage = floImg;
        this.IsGrassWeed = isGrassWeed;
    }

    public Integer getWeedId() {
        return WeedId;
    }

    public String getWeedName() {
        return WeedName;
    }

    public String getWeedFamily() {
        return WeedFamily;
    }

    public String getWeedDescription() {
        return WeedDescription;
    }

    public String getYoungImage() {
        return YoungImage;
    }

    public String getFlowerImage() {
        return FlowerImage;
    }
}
