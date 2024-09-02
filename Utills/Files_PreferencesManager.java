package com.ep.video.player.videoplayer.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class Files_PreferencesManager {
    public static void saveToDirList_Grid(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putBoolean(Files_Constant.SHARED_PREFS_DIR_LIST_GRID, z);
        edit.commit();
    }

    public static void saveToFirstTime(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putBoolean(Files_Constant.SHARED_PREFS_FIRST_TIME, z);
        edit.commit();
    }

    public static boolean getFirstTimeAppOpen(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getBoolean(Files_Constant.SHARED_PREFS_FIRST_TIME, false);
    }

    public static void saveToFirstInterstitial(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putBoolean(Files_Constant.SHARED_PREFS_FIRST_INTERSTITIAL, z);
        edit.commit();
    }

    public static boolean getFirstInterstitial(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getBoolean(Files_Constant.SHARED_PREFS_FIRST_INTERSTITIAL, true);
    }

    public static void savePermissionscreen(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putBoolean(Files_Constant.SHARED_PREFS_PERMISSION_SCREEN, z);
        edit.commit();
    }

    public static boolean getPermissionscreenAppOpen(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getBoolean(Files_Constant.SHARED_PREFS_PERMISSION_SCREEN, false);
    }

    public static boolean getDirList_Grid(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getBoolean(Files_Constant.SHARED_PREFS_DIR_LIST_GRID, false);
    }

    public static void saveToDocList_Grid(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putBoolean(Files_Constant.SHARED_PREFS_DOCUMENT_LIST_GRID, z);
        edit.commit();
    }

    public static boolean getDocList_Grid(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getBoolean(Files_Constant.SHARED_PREFS_DOCUMENT_LIST_GRID, false);
    }

    public static void saveToShowHidden(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putBoolean(Files_Constant.SHARED_PREFS_SHOW_HIDDEN_FILE, z);
        edit.commit();
    }

    public static boolean getShowHidden(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getBoolean(Files_Constant.SHARED_PREFS_SHOW_HIDDEN_FILE, false);
    }

    public static void saveToSortType(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putInt(Files_Constant.SHARED_PREFS_SORT_FILE_LIST, i);
        edit.commit();
    }

    public static int getSortType(Context context) {
        try {
            return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getInt(Files_Constant.SHARED_PREFS_SORT_FILE_LIST, 1);
        } catch (Exception unused) {
            return 1;
        }
    }

    public static void setRateUs(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putBoolean(Files_Constant.SHARED_PREFS_RATE_US, z);
        edit.commit();
    }

    public static boolean getRate(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getBoolean(Files_Constant.SHARED_PREFS_RATE_US, false);
    }

    public static String getSDCardTreeUri(Context context) {
        return context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getString(Files_Constant.PREF_SDCARD_TREE_URI, "");
    }

    public static void setSDCardTreeUri(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
        edit.putString(Files_Constant.PREF_SDCARD_TREE_URI, str);
        edit.apply();
    }

    public static ArrayList<String> getFavouriteList(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            return (ArrayList) new Gson().fromJson(context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).getString(Files_Constant.SHARED_PREFS_FAVOURITE_LIST, ""), new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    public static void setFavouriteList(Context context, ArrayList<String> arrayList) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences(Files_Constant.SHARED_PREFS, 0).edit();
            edit.putString(Files_Constant.SHARED_PREFS_FAVOURITE_LIST, new Gson().toJson(arrayList));
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getTheme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Files_Constant.SELECTED_THEME, 2);
    }



    public static String getLanguage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Files_Constant.SELECTED_LANG, "");
    }

    public static String getLanguageNm(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("LangNm", "English");
    }

    public static void setLanguage(Context context, String str, String str2) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Files_Constant.SELECTED_LANG, str).putString("LangNm", str2).apply();
    }

    public static int getPermsistion(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("getPermsistion", 0);
    }

    public static void setPermsistion(Context context, int i) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("getPermsistion", i).apply();
    }
}
