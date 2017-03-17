package com.bayer.turfid;

public class TIPest {
    public Integer PestId;
    public String PestName;
    public String PestFamily;
    public String PestDescription;
    public String PestImage;
    public String PestCultural;

    public TIPest(Integer Id, String name, String family, String desc, String img, String cultural) {
        this.PestId = Id;
        this.PestName = name;
        this.PestFamily = family;
        this.PestDescription = desc;
        this.PestImage = img;
        this.PestCultural = cultural;
    }

    public Integer getPestId() {
        return PestId;
    }

    public String getPestName() {
        return PestName;
    }

    public String getPestFamily() {
        return PestFamily;
    }

    public String getPestImage() {
        return PestImage;
    }

    public String getPestCultural() {
        return PestCultural;
    }
}
