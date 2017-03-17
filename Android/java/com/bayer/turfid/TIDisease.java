package com.bayer.turfid;

public class TIDisease {
    public Integer DiseaseId;
    public String DiseaseName;
    public String DiseaseFamily;
    public String DiseaseDescription;
    public String DiseaseImage;
    public String DiseaseCultural;

    public TIDisease(Integer Id, String name, String family, String desc, String img, String cultural) {
        this.DiseaseId = Id;
        this.DiseaseName = name;
        this.DiseaseFamily = family;
        this.DiseaseDescription = desc;
        this.DiseaseImage = img;
        this.DiseaseCultural = cultural;
    }

    public Integer getDiseaseId() {
        return DiseaseId;
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public String getDiseaseFamily() {
        return DiseaseFamily;
    }

    public String getDiseaseImage() {
        return DiseaseImage;
    }

    public String getDiseaseCultural() {
        return DiseaseCultural;
    }
}
