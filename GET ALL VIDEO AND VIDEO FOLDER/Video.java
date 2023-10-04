package com.camera.backgroundvideorecorder.Models;

import java.io.Serializable;

public class Video implements Serializable {
    public String uri;
    public String name;
    public int duration;
    public int size;
    public String resolution;
    public String date_added;
    public String data;

    public Video(String uri, String name, int duration, int size, String resolution, String date_added, String data) {
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.resolution = resolution;
        this.date_added = date_added;
        this.data = data;
    }

    public Video() {
        this.uri = "";
        this.name = "";
        this.duration = 0;
        this.size = 0;
        this.resolution = "";
        this.date_added = "date_added";
        this.data = "";
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return size;
    }

    public String getResolution() {
        return resolution;
    }

    public String getDate_added() {
        return date_added;
    }

    public String getData() {
        return data;
    }
}
