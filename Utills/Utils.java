package com.ep.video.player.videoplayer.Utills;

import android.app.Activity;
import android.os.Build;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.text.DecimalFormat;


public class Utils {
    public static String formatSize(long j) {
        double d;
        if (j <= 0) {
            return "";
        }
        int log10 = (int) (Math.log10(j) / Math.log10(1024.0d));
        return new DecimalFormat("#,##0.#").format(log10 / Math.pow(1024.0d, log10)) + " " + new String[]{"B", "KB", "MB", "GB", "TB"}[log10];
    }

    public static String getFileName(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }

    public static File[] getFileList(String str) {
        File file = new File(str);
        return !file.isDirectory() ? new File[0] : file.listFiles();
    }

    public static boolean checkSelfPermission(Activity activity, String str) {
        return !isAndroid23() || ContextCompat.checkSelfPermission(activity, str) == 0;
    }

    public static boolean isAndroid23() {
        return Build.VERSION.SDK_INT >= 23;
    }

}
