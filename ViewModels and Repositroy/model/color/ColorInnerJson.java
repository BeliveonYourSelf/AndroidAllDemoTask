package com.ep.ai.hd.live.wallpaper.apiClient.model.color;

import com.google.gson.annotations.SerializedName;

public class ColorInnerJson {
    @SerializedName("colorId")          String colorId;
    @SerializedName("colorName")          String colorName;
    @SerializedName("colorCode")          String colorCode;
    @SerializedName("colorTotalImg")          String colorTotalImg;
    @SerializedName("type")          String type;

//Constructor
    public ColorInnerJson(String type) {
        this.type = type;
    }

    public ColorInnerJson(String colorId, String colorName, String colorCode, String colorTotalImg, String type) {
        this.colorId = colorId;
        this.colorName = colorName;
        this.colorCode = colorCode;
        this.colorTotalImg = colorTotalImg;
        this.type = type;
    }

//Getter & Setter
    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorTotalImg() {
        return colorTotalImg;
    }

    public void setColorTotalImg(String colorTotalImg) {
        this.colorTotalImg = colorTotalImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
