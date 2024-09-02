package com.ep.ai.hd.live.wallpaper.apiClient.model.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryMainJson {

    @SerializedName("error_code")       String error_code;
    @SerializedName("error_msg")       String error_msg;
    @SerializedName("count")       String count;

    @SerializedName("Category_List")
    List<CategoryInnerJson> Category_List;

//Constructor
    public CategoryMainJson(String error_code, String error_msg, String count, List<CategoryInnerJson> category_List) {
        this.error_code = error_code;
        this.error_msg = error_msg;
        this.count = count;
        Category_List = category_List;
    }

    //Getter & Setter
    public List<CategoryInnerJson> getCategory_List() {
        return Category_List;
    }
    public void setCategory_List(List<CategoryInnerJson> category_List) {
        Category_List = category_List;
    }

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

}
