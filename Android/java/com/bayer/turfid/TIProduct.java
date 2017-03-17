package com.bayer.turfid;

public class TIProduct {
    public Integer ProductId;
    public String ProductName;
    public String ProductType;
    public String ProductOverview;
    public String FungicideGroup;
    public String UseArea;
    public String KeyFeatures;
    public String ProductImage;
    public String ProductPack;
    public Integer ProductOrder;

    public TIProduct(Integer Id, String name, String overview, String type, String group, String area, String features, String prodImg, String prodPack, Integer prodOrder) {
        this.ProductId = Id;
        this.ProductName = name;
        this.ProductType = type;
        this.ProductOverview = overview;
        this.FungicideGroup = group;
        this.UseArea = area;
        this.KeyFeatures = features;
        this.ProductImage = prodImg;
        this.ProductPack = prodPack;
        this.ProductOrder = prodOrder;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductType() {
        return ProductType;
    }

    public String getProductOverview() {
        return ProductOverview;
    }

    public String getFungicideGroup() {
        return FungicideGroup;
    }

    public String getUseArea() {
        return UseArea;
    }

    public String getKeyFeatures() {
        return KeyFeatures;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public String getProductPack() {
        return ProductPack;
    }

    public Integer getProductOrder() {
        return ProductOrder;
    }
}
