package com.ep.ai.hd.live.wallpaper.apiClient.model.colorList;

import com.google.gson.annotations.SerializedName;

public class ColorListInnerJson {
    @SerializedName("Category")
    ColorList_categoryNameJson categoryNameJsons;

    @SerializedName("snippet")
    ColorList_snippetList snippetLists;

    @SerializedName("statistics")
    ColorList_statisticsList statisticsLists;

    @SerializedName("type")     String type;

//Constructor
    public ColorListInnerJson(String type) {
        this.type = type;
    }

    public ColorListInnerJson(ColorList_categoryNameJson categoryNameJsons, ColorList_snippetList snippetLists, ColorList_statisticsList statisticsLists, String type) {
        this.categoryNameJsons = categoryNameJsons;
        this.snippetLists = snippetLists;
        this.statisticsLists = statisticsLists;
        this.type = type;
    }

    public ColorList_categoryNameJson getCategoryNameJsons() {
        return categoryNameJsons;
    }

    public void setCategoryNameJsons(ColorList_categoryNameJson categoryNameJsons) {
        this.categoryNameJsons = categoryNameJsons;
    }

    public ColorList_snippetList getSnippetLists() {
        return snippetLists;
    }

    public void setSnippetLists(ColorList_snippetList snippetLists) {
        this.snippetLists = snippetLists;
    }

    public ColorList_statisticsList getStatisticsLists() {
        return statisticsLists;
    }

    public void setStatisticsLists(ColorList_statisticsList statisticsLists) {
        this.statisticsLists = statisticsLists;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
