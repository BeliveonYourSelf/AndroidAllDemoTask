package com.ep.ai.hd.live.wallpaper.apiClient.model.colorList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ColorListMainJson {
    @SerializedName("error_code")           String error_code;
    @SerializedName("error_msg")           String error_msg;
    @SerializedName("count")                    String count;
    @SerializedName("WallpaperList")
    List<ColorListInnerJson> categoryListInnerJsons;

//Constructor
    public ColorListMainJson(String error_code, String error_msg, String count, List<ColorListInnerJson> categoryListInnerJsons) {
        this.error_code = error_code;
        this.error_msg = error_msg;
        this.count = count;
        this.categoryListInnerJsons = categoryListInnerJsons;
    }

//Getter & Setter

    public String getError_code() {
        return error_code;
    }
    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }

    public List<ColorListInnerJson> getCategoryListInnerJsons() {
        return categoryListInnerJsons;
    }
    public void setCategoryListInnerJsons(List<ColorListInnerJson> categoryListInnerJsons) {
        this.categoryListInnerJsons = categoryListInnerJsons;
    }
}
