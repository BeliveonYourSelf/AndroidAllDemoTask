package com.ep.video.player.videoplayer.Utills;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {
    Activity activity;
    ActivityResultLauncher<String[]> resultLauncher;

    public PermissionUtils(Activity activity, ActivityResultLauncher<String[]> resultLauncher) {
        this.activity = activity;
        this.resultLauncher = resultLauncher;
    }

    public void takeStoragePermission() {
        String[] permission;
        if (Build.VERSION.SDK_INT >= 33) {
            permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO};
        } else {
            permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }

        resultLauncher.launch(permission);
    }

    public boolean isStorageCameraPermissionGranted() {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            int readPhotoStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES);
            int readVideoStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO);
            int readAudiPerimssion = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO);
            return (readPhotoStoragePermission == PackageManager.PERMISSION_GRANTED && readVideoStoragePermission == PackageManager.PERMISSION_GRANTED && readAudiPerimssion == PackageManager.PERMISSION_GRANTED);
        } else {
            int readExternalStoragePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writeExternalStorage = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return (readExternalStoragePermission == PackageManager.PERMISSION_GRANTED && writeExternalStorage == PackageManager.PERMISSION_GRANTED);
        }
    }

    public static String getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return "blocked";
            }
            return "denied";
        }
        return "granted";
    }
}
