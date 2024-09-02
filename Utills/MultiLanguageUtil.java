package com.ep.video.player.videoplayer.Utills;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import com.eventopackage.epads.utils.MyApplication;

import java.util.Locale;

public class MultiLanguageUtil {
    private static final String TAG = "MultiLanguageUtil";
    public static void setLanguage(Context context) {
        if (context == null) {
            return;
        }
        String prefLanguage = MyApplication.sharedPreferencesHelper.getLanguage();
        Log.e(TAG, "setLanguage: ->   "+prefLanguage );
        if (prefLanguage == null) {
            Log.e(TAG, "setLanguage: prefLanguage == null  ->   "+prefLanguage );
            prefLanguage = Locale.getDefault().getLanguage();
        }
        Locale locale = new Locale(prefLanguage.toLowerCase());
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static String getLanguageName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String prefLanguage = MyApplication.sharedPreferencesHelper.getLanguage();
            if (prefLanguage == null) {
                prefLanguage = Locale.getDefault().getLanguage();
            }
            Locale locale = new Locale(prefLanguage.toLowerCase());
            Locale.setDefault(locale);
            return locale.getDisplayName() + "";
        } catch (Exception unused) {
            return "";
        }
    }
}
