package com.ep.video.player.videoplayer.Utills;

import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.util.Log;
import android.view.Window;

import com.ep.video.player.videoplayer.R;

import java.io.File;
import java.io.IOException;

public abstract class Utility {
    private static final String TAG = "Utility";
    public static Dialog dialog;

    public static String currentPhotoPath = "";

    public static File getImageFile(Activity activity) throws IOException {
        String imageFileName = "IMG_" + System.currentTimeMillis() + "_";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),activity.getString(R.string.app_name)
        );
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File file = File.createTempFile(
                imageFileName, ".jpg", storageDir
        );
        currentPhotoPath = "file:" + file.getAbsolutePath();
        return file;
    }

    public static String timeConversion(Long millie) {
        if (millie != null) {
            long seconds = (millie / 1000);
            long sec = seconds % 60;
            long min = (seconds / 60) % 60;
            long hrs = (seconds / (60 * 60)) % 24;
            if (hrs > 0) {
                return String.format("%02d:%02d:%02d", hrs, min, sec);
            } else {
                return String.format("%02d:%02d", min, sec);
            }
        } else {
            return null;
        }
    }

    public static void Showloader(Activity activity) {
        try {
            if (dialog != null) {
                cancelLoader();
                dialog = null;
            }
            {
                dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_dialog_loading_view);
                dialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.rounded_bg));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e);
        }
    }


    public static void cancelLoader() {
        try {
            if (dialog != null || dialog.isShowing()) {
                dialog.cancel();
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e);
        }
    }

}
