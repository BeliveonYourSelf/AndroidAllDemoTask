package com.ep.ai.hd.live.wallpaper.utils;

import android.util.Log;

import com.ep.ai.hd.live.wallpaper.DataModel.SelectionModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class GlobalVar {
    public static final String HOME = "HOME";

    public static final String FROM = "FROM";
    public static final String FROMDOWNLOAD = "FROMDOWNLOAD";
    public static final String FROMCOLOR = "FROMCOLOR";
    public static final String FROMPOPULAR = "FROMPOPULAR";
    public static final String FROMPREMIUM = "FROMPREMIUM";
    public static final String FROMFAV = "FROMFAV";
    public static final String DATAMODEL = "DATAMODEL";
    public static  String vLoginToken = "http://192.168.29.119:1500/";
    public static final String FAV = "FAV";
    public static final String USER = "USER";
    public static final String UPLOAD = "UPLOAD";
    public static final String DOWNLOAD = "DOWNLOAD";
    public static final String SETTING = "SETTING";
    public static final String SHARE = "SHARE";
    public static final String RATE = "RATE";
    public static final String FEEDBACK = "FEEDBACK";
    public static final String ABOUT = "ABOUT";
    public static final String USERNAME = "USERNAME";
    public static final String USERPHOTO = "USERPHOTO";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String REMEMBER = "REMEMBER";
    public static final String LOGINTOKEN = "LOGINTOKEN";
    public static final String FAVOURITEURL = "FAVOURITEURL";
    public static final String DOWNLOADED = "DOWNLOADED";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String SIGNUP = "SIGNUP";
    public static final String LOGINSUCCESS = "LOGINSUCCESS";
    public static final String FROGMAIL = "FROGMAIL";
    public static final String HOMEBTN = "HOMEBTN";
    public static final String CATBTN = "CATBTN";
    public static final String POPUBTN = "POPUBTN";
    public static final String PREMIBTN = "PREMIBTN";
    public static final String ARRAYLISTCOLOR = "ARRAYLISTCOLOR";
    public static final String ARRAYLISTCAT = "ARRAYLISTCAT";
    public static final String ARRAYLISDOWNLOAD = "ARRAYLISDOWNLOAD";
    public static final String ARRAYPOPULAR = "ARRAYPOPULAR";
    public static final String ARRAYPREMI = "ARRAYPREMI";
    public static final String ARRAYFAV = "ARRAYFAV";
    public static final String POSITION = "POSITION";
    public static final String CATEGORY = "CATEGORY";
    public static final String COLOR = "COLOR";
    public static final String IMAGEURL = "IMAGEURL" ;
    public static final String IMAGEID = "IMAGEID" ;
    private static final long BYTE = 1L;
    private static final long KB = BYTE << 10;
    private static final long MB = KB << 10;
    private static final long GB = MB << 10;
    private static final long TB = GB << 10;
    private static final long PB = TB << 10;
    private static final DecimalFormat DEC_FORMAT = new DecimalFormat("#.##");
    public static ArrayList<MultipartBody.Part> convertArrayListToRequestBody(ArrayList<SelectionModel> arrayList) {
        ArrayList<MultipartBody.Part> requestBodyArrayList = new ArrayList<>();
        for (SelectionModel item : arrayList) {
            Log.e("TAG", "convertArrayListToRequestBody:-> gettting ID's of Colors-->  "+ item.getSelectedId());
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), item.getSelectedId());
            MultipartBody.Part part = MultipartBody.Part.createFormData("arrColorId", null, requestBody);
            requestBodyArrayList.add(part);
        }

        Log.e("TAG", "ArrayList Details-------> : "+requestBodyArrayList.size() );
        Log.e("TAG", "ArrayList body-------> : "+requestBodyArrayList.get(0).body().toString() );
        Log.e("TAG", "ArrayList contentType-------> : "+requestBodyArrayList.get(0).body().contentType());

        return requestBodyArrayList;
    }

    public static String toHumanReadable(long size) {
        if (size < 0) {
            return "NA";
        }
        long EB = PB << 10;
        if (size >= EB) return formatSize(size, EB, "EB");
        if (size >= PB) return formatSize(size, PB, "PB");
        if (size >= TB) return formatSize(size, TB, "TB");
        if (size >= GB) return formatSize(size, GB, "GB");
        if (size >= MB) return formatSize(size, MB, "MB");
        if (size >= KB) return formatSize(size, KB, "KB");
        return formatSize(size, BYTE, "Bytes");
    }
    private static String formatSize(long size, long divider, String unitName) {
        return DEC_FORMAT.format((double) size / divider) + " " + unitName;
    }



}
