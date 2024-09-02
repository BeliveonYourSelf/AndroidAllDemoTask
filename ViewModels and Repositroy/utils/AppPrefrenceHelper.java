package com.ep.ai.hd.live.wallpaper.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class AppPrefrenceHelper {
    public SharedPreferences sharedPreferences;

    public AppPrefrenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static AppPrefrenceHelper init(Context context) {
        SharedPreferences sharedPreferencess = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return new AppPrefrenceHelper(sharedPreferencess);
    }

    public void setFavouriteList(String language) {
        sharedPreferences.edit().putString(GlobalVar.ARRAYFAV, language).apply();
    }

    public String getFavouriteList() {
        return sharedPreferences.getString(GlobalVar.ARRAYFAV, null);
    }
    public void setLanguage(String language) {
        sharedPreferences.edit().putString(GlobalVar.LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(GlobalVar.LANGUAGE, "en");
    }
    public void setDownloadStringSet(Set<String> sStringSet) {
        sharedPreferences.edit().putStringSet(GlobalVar.DOWNLOADED, sStringSet).apply();
    }

    public Set<String> getDownloadStringSet() {
        return sharedPreferences.getStringSet(GlobalVar.DOWNLOADED,new HashSet<>());
    }

    public void setFavStringSet(Set<String> sStringSet) {
        sharedPreferences.edit().putStringSet(GlobalVar.FAVOURITEURL, sStringSet).apply();
    }

    public Set<String> getFavStringSet() {
        return sharedPreferences.getStringSet(GlobalVar.FAVOURITEURL,new HashSet<>());
    }


    public String getLoginToken() {
        return sharedPreferences.getString(GlobalVar.LOGINTOKEN,"");
    }













}
