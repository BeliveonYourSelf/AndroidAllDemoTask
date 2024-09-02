package com.ep.ai.hd.live.wallpaper.apiClient.model.splash;

import com.google.gson.annotations.SerializedName;

public class SplashMainJson {
    @SerializedName("ReasonCode")           String ReasonCode;
    @SerializedName("Description")           String Description;
    @SerializedName("auth_token")           String auth_token;
    @SerializedName("user")           String user;

    public SplashMainJson(String reasonCode, String description, String auth_token, String user) {
        ReasonCode = reasonCode;
        Description = description;
        this.auth_token = auth_token;
        this.user = user;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public void setReasonCode(String reasonCode) {
        ReasonCode = reasonCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
