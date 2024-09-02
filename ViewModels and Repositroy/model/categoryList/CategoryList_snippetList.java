package com.ep.ai.hd.live.wallpaper.apiClient.model.categoryList;

import com.google.gson.annotations.SerializedName;

public class CategoryList_snippetList {
    @SerializedName("imageId")          String imageId;
    @SerializedName("imageName")          String imageName;
    @SerializedName("imageLocation")          String imageLocation;
    @SerializedName("isPremium")          String isPremium;

    public CategoryList_snippetList(String imageId, String imageName, String imageLocation, String isPremium) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageLocation = imageLocation;
        this.isPremium = isPremium;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }
}
