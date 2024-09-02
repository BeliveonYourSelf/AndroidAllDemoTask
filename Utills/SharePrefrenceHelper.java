package com.ep.video.player.videoplayer.Utills;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefrenceHelper {
    public SharedPreferences sharedPreferences;

    public SharePrefrenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static SharePrefrenceHelper init(Context context) {
        SharedPreferences sharedPreferencess = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return new SharePrefrenceHelper(sharedPreferencess);
    }

    public void setPlaylistSong(String playlistname, String playlist) {
        sharedPreferences.edit().putString(playlistname, playlist).apply();
    }

    public String getPlaylistSong(String playlistname) {
        return sharedPreferences.getString(playlistname, "");
    }

    public void setPlaylistVideo(String playlistname, String playlist) {
        sharedPreferences.edit().putString(playlistname, playlist).apply();
    }

    public String getPlaylistVideo(String playlistname) {
        return sharedPreferences.getString(playlistname, "");
    }

    public void setPlaylist(String playlist) {
        sharedPreferences.edit().putString(GlobalVariable.PLAYLIST, playlist).apply();
    }

    public String getPlaylist() {
        return sharedPreferences.getString(GlobalVariable.PLAYLIST, "");
    }

    public void setVideoPlaylist(String playlist) {
        sharedPreferences.edit().putString(GlobalVariable.VIDEOPLAYLIST, playlist).apply();
    }

    public String getVideoPlaylist() {
        return sharedPreferences.getString(GlobalVariable.VIDEOPLAYLIST, "");
    }

    public void setRecentPLayed(String recnetPLayed) {
        sharedPreferences.edit().putString(GlobalVariable.RECENTPLAYED, recnetPLayed).apply();

    }

    public void setFringerPrint(boolean fingerPrint) {
        sharedPreferences.edit().putBoolean(GlobalVariable.FINGERPRINT, fingerPrint).apply();
    }

    public boolean getFringerPrint() {
        return sharedPreferences.getBoolean(GlobalVariable.FINGERPRINT, true);
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString(GlobalVariable.PASSWORD, password).apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(GlobalVariable.PASSWORD, null);
    }

    public void setPattern(String password) {
        sharedPreferences.edit().putString(GlobalVariable.PASSWORD, password).apply();
    }

    public String getPattern() {
        return sharedPreferences.getString(GlobalVariable.PASSWORD, null);
    }

    public void setPasswordType(String passwordType) {
        sharedPreferences.edit().putString(GlobalVariable.PASSWORD, passwordType).apply();
    }

    public String getPasswordType() {
        return sharedPreferences.getString(GlobalVariable.PASSWORDTYPE, null);
    }

    public void setIsPasswordSet(boolean str) {
        sharedPreferences.edit().putBoolean(GlobalVariable.IsPasswordSet, str).apply();
    }

    public boolean getIsPasswordSet() {
        return sharedPreferences.getBoolean(GlobalVariable.IsPasswordSet, false);
    }

    public void setLanguage(String language) {
        sharedPreferences.edit().putString(GlobalVariable.LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(GlobalVariable.LANGUAGE, null);
    }

    public void setSelectedLanguage(int language) {
        sharedPreferences.edit().putInt(GlobalVariable.LANGUAGESELECT, language).apply();
    }

    public int getSelectedLanguage() {
        return sharedPreferences.getInt(GlobalVariable.LANGUAGESELECT, 0);
    }

}

