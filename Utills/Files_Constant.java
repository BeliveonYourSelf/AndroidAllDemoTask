package com.ep.video.player.videoplayer.Utills;


import com.ep.video.player.videoplayer.Models.Directorys.PhotoData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Files_Constant {
    public static final String PREF_SDCARD_TREE_URI = "my_file_manager_sd_card_tree_uri";
    public static final String SELECTED_LANG = "selected_language";
    public static final String SELECTED_THEME = "selected_theme";
    public static final String SHARED_PREFS = "my_file_manager";
    public static final String SHARED_PREFS_DIR_LIST_GRID = "my_file_manager_dir_list_grid";
    public static final String SHARED_PREFS_DOCUMENT_LIST_GRID = "my_file_manager_document_list_grid";
    public static final String SHARED_PREFS_FAVOURITE_LIST = "my_file_manager_favourite_list";
    public static final String SHARED_PREFS_FIRST_INTERSTITIAL = "first_time_INTERSTITIAL";
    public static final String SHARED_PREFS_FIRST_TIME = "first_time_app";
    public static final String SHARED_PREFS_PERMISSION_SCREEN = "open_permission_screen";
    public static final String SHARED_PREFS_RATE_US = "my_file_manager_rate_us";
    public static final String SHARED_PREFS_SHOW_HIDDEN_FILE = "my_file_manager_hidden_file";
    public static final String SHARED_PREFS_SORT_FILE_LIST = "my_file_manager_sort_file_list";
    public static String storagePath;
    public static List<PhotoData> displayImageList = new ArrayList();
    public static List<PhotoData> displayVideoList = new ArrayList();
    public static ArrayList<File> pastList = new ArrayList<>();
    public static boolean isCopyData = false;
    public static boolean isFileFromSdCard = false;
    public static ArrayList<String> arrayListFilePaths = new ArrayList<>();
    public static boolean isOpenImage = false;
    public static boolean showOpenAds = true;
    public static boolean isSplashScreen = false;
    public static String isLang = "en";
    public static String isLangNm = "English";

    public static void hideOpenAd() {
        showOpenAds = false;
    }
}
