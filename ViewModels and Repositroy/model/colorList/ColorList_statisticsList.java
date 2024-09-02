package com.ep.ai.hd.live.wallpaper.apiClient.model.colorList;

import com.google.gson.annotations.SerializedName;

public class ColorList_statisticsList {
    @SerializedName("imageDownload")            String imageDownload;
    @SerializedName("imageViewer")            String imageViewer;
    @SerializedName("imageLike")            String imageLike;
    @SerializedName("imageShare")            String imageShare;
    @SerializedName("image_width")            String image_width;
    @SerializedName("image_height")            String image_height;
    @SerializedName("image_size")            String image_size;

    public ColorList_statisticsList(String imageDownload, String imageViewer, String imageLike, String imageShare, String image_width, String image_height, String image_size) {
        this.imageDownload = imageDownload;
        this.imageViewer = imageViewer;
        this.imageLike = imageLike;
        this.imageShare = imageShare;
        this.image_width = image_width;
        this.image_height = image_height;
        this.image_size = image_size;
    }

    public String getImageDownload() {
        return imageDownload;
    }

    public void setImageDownload(String imageDownload) {
        this.imageDownload = imageDownload;
    }

    public String getImageViewer() {
        return imageViewer;
    }

    public void setImageViewer(String imageViewer) {
        this.imageViewer = imageViewer;
    }

    public String getImageLike() {
        return imageLike;
    }

    public void setImageLike(String imageLike) {
        this.imageLike = imageLike;
    }

    public String getImageShare() {
        return imageShare;
    }

    public void setImageShare(String imageShare) {
        this.imageShare = imageShare;
    }

    public String getImage_width() {
        return image_width;
    }

    public void setImage_width(String image_width) {
        this.image_width = image_width;
    }

    public String getImage_height() {
        return image_height;
    }

    public void setImage_height(String image_height) {
        this.image_height = image_height;
    }

    public String getImage_size() {
        return image_size;
    }

    public void setImage_size(String image_size) {
        this.image_size = image_size;
    }
}
