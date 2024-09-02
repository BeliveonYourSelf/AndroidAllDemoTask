package com.ep.ai.hd.live.wallpaper.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ep.ai.hd.live.wallpaper.R;

public class MyController {

    public static String TAG = "4kWallpaperAppController";
    public static final String SharePreKey = "4kWallpaperSharePre";

    public static String DeviceId, PackageName, VersionCode, clientMasterKey;

    public static boolean isOnline(Context mContext) {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }

    public static void shoToast(Context mContext, String getText){
        View layout = LayoutInflater.from(mContext).inflate(R.layout.custom_toast, null);
        TextView text = (TextView) layout.findViewById(R.id.txt_toast);
        text.setText(getText);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static String findDeviceId(Context mContext){
        String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static String findVersionKey(Context mContext){
        String versionKey = "";
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            versionKey = String.valueOf(pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionKey = "";
        }
        return versionKey;
    }

    public static String findMasterKey(Context mContext){
        String ab1 = "absoultealgos";
        String ab2 = "application";
        DeviceId = findDeviceId(mContext);
        String clientId = ab1+DeviceId+ab2;

        return clientId;
    }
}
