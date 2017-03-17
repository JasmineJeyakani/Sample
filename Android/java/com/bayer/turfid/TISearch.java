//package com.bayer.turfid;
//
//public class TISearch {
//	public String Name;
//	public String Family;
//	public String Description;
//	public String Image;
//	public String Img;
//
//	public String categoryType;
//
//    public TISearch( String name, String family, String desc, String image,String img,String categoryType) {
//    	this.Name = name;
//        this.Family = family;
//        this.Description = desc;
//        this.Image = image;
//        this.Img = img;
//
//        this.categoryType=categoryType;
//    }
//    
//    public String getName() {
//    	return Name;
//    }
//
//    public String getCategoryType() {
//    	return categoryType;
//    }
//
//
//    public String getFamily() {
//    	return Family;
//    }
//
//    public String getDescription() {
//    	return Description;
//    }
//
//    public String getImage() {
//    	return Image;
//    }
//    public String getImg() {
//    	return Img;
//    }
//
//}


package com.bayer.turfid;

public class TISearch {
    public String Name;
    public String Family;
    public String Description;
    public String Image;
    public String Img;

    public String CategoryType;
    public String Cultural;

    public TISearch(String name, String family, String desc, String image, String img, String categoryType, String cultural) {
        this.Name = name;
        this.Family = family;
        this.Description = desc;
        this.Image = image;
        this.Img = img;

        this.CategoryType = categoryType;
        this.Cultural = cultural;
    }

    public String getName() {
        return Name;
    }

    public String getFamily() {
        return Family;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }

    public String getImg() {
        return Img;
    }

    public String getCategoryType() {
        return CategoryType;
    }

    public String getCultural() {
        return Cultural;
    }

}
