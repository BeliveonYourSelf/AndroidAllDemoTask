package com.ep.ai.hd.live.wallpaper.apiClient.model.category;

import com.google.gson.annotations.SerializedName;

public class CategoryInnerJson {
    @SerializedName("categoryId")         String categoryId;
    @SerializedName("categoryName")    String categoryName;
    @SerializedName("categoryImg")    String categoryImg;
    @SerializedName("categoryTotalImg")    String categoryTotalImg;
    @SerializedName("type")         String type;

//Constructor
    public CategoryInnerJson(String type) {
        this.type = type;
    }

    public CategoryInnerJson(String categoryId, String categoryName, String categoryImg, String categoryTotalImg, String type) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
        this.categoryTotalImg = categoryTotalImg;
        this.type = type;
    }

//Getter & Setter
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }
    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryTotalImg() {
        return categoryTotalImg;
    }
    public void setCategoryTotalImg(String categoryTotalImg) {
        this.categoryTotalImg = categoryTotalImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
