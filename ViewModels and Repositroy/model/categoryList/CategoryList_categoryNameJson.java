package com.ep.ai.hd.live.wallpaper.apiClient.model.categoryList;

import com.google.gson.annotations.SerializedName;

public class CategoryList_categoryNameJson {
    @SerializedName("categoryId")           String categoryId;
    @SerializedName("categoryName")           String categoryName;

    public CategoryList_categoryNameJson(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

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
}