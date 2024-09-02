package com.ep.ai.hd.live.wallpaper.apiClient.model.categoryList;

import com.google.gson.annotations.SerializedName;

public class CategoryListInnerJson {
    @SerializedName("Category")
    CategoryList_categoryNameJson categoryNameJsons;

    @SerializedName("snippet")
    CategoryList_snippetList snippetLists;

    @SerializedName("statistics")
    CategoryList_statisticsList statisticsLists;

    @SerializedName("type")     String type;

//Constructor
    public CategoryListInnerJson(String type) {
        this.type = type;
    }

    public CategoryListInnerJson(CategoryList_categoryNameJson categoryNameJsons, CategoryList_snippetList snippetLists, CategoryList_statisticsList statisticsLists, String type) {
        this.categoryNameJsons = categoryNameJsons;
        this.snippetLists = snippetLists;
        this.statisticsLists = statisticsLists;
        this.type = type;
    }

    public CategoryList_categoryNameJson getCategoryNameJsons() {
        return categoryNameJsons;
    }

    public void setCategoryNameJsons(CategoryList_categoryNameJson categoryNameJsons) {
        this.categoryNameJsons = categoryNameJsons;
    }

    public CategoryList_snippetList getSnippetLists() {
        return snippetLists;
    }

    public void setSnippetLists(CategoryList_snippetList snippetLists) {
        this.snippetLists = snippetLists;
    }

    public CategoryList_statisticsList getStatisticsLists() {
        return statisticsLists;
    }

    public void setStatisticsLists(CategoryList_statisticsList statisticsLists) {
        this.statisticsLists = statisticsLists;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
