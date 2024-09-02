package com.ep.video.player.videoplayer.Utills;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import com.google.android.exoplayer2.C;
import com.ep.video.player.videoplayer.R;

import net.lingala.zip4j.util.InternalZipConstants;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


public class Files_StorageUtils {

    public static String StoragePath(String str, Context context) {
        List<String> storageDirectories = getStorageDirectories(context);
        if (storageDirectories.size() > 0) {
            try {
                if (str.equalsIgnoreCase("InternalStorage")) {
                    return storageDirectories.get(0);
                }
                return (!str.equalsIgnoreCase("ExternalStorage") || storageDirectories.size() < 1) ? "" : storageDirectories.get(0);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    public static List<String> getStorageDirectories(Context context) {
        String[] extSdCardPaths;
        Pattern compile = Pattern.compile(InternalZipConstants.ZIP_FILE_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        String str = System.getenv("EXTERNAL_STORAGE");
        String str2 = System.getenv("SECONDARY_STORAGE");
        String str3 = System.getenv("EMULATED_STORAGE_TARGET");
        if (TextUtils.isEmpty(str3)) {
            if (TextUtils.isEmpty(str)) {
                arrayList.add("/storage/sdcard0");
            } else {
                arrayList.add(str);
            }
        } else {
            String str4 = "";
            if (Build.VERSION.SDK_INT >= 17) {
                String[] split = compile.split(Environment.getExternalStorageDirectory().getAbsolutePath());
                boolean z = true;
                String str5 = split[split.length - 1];
                try {
                    Integer.valueOf(str5);
                } catch (NumberFormatException unused) {
                    z = false;
                }
                if (z) {
                    str4 = str5;
                }
            }
            if (TextUtils.isEmpty(str4)) {
                arrayList.add(str3);
            } else {
                arrayList.add(str3 + File.separator + str4);
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            Collections.addAll(arrayList, str2.split(File.pathSeparator));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            arrayList.clear();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            for (String str6 : getExtSdCardPaths(context)) {
                File file = new File(str6);
                if (!arrayList.contains(str6) && canListFiles(file)) {
                    arrayList.add(str6);
                }
            }
        }
        return arrayList;
    }

    public static boolean canListFiles(File file) {
        try {
            if (file.canRead()) {
                if (file.isDirectory()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static String[] getExtSdCardPaths(Context context) {
        File[] externalFilesDirs;
        ArrayList arrayList = new ArrayList();
        for (File file : ContextCompat.getExternalFilesDirs(context, "external")) {
            if (file != null && !file.equals(context.getExternalFilesDir("external"))) {
                int lastIndexOf = file.getAbsolutePath().lastIndexOf("/Android/data");
                if (lastIndexOf < 0) {

                } else {
                    String substring = file.getAbsolutePath().substring(0, lastIndexOf);
                    try {
                        substring = new File(substring).getCanonicalPath();
                    } catch (IOException unused) {
                    }
                    arrayList.add(substring);
                }
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add("/storage/sdcard1");
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

}
