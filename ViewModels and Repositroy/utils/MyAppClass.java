package com.ep.ai.hd.live.wallpaper.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import com.eventopackage.epads.utils.MyApplication;

import java.util.Locale;

public class MyAppClass extends MyApplication {
    private static final String TAG = "MyAppClass";
    public static AppPrefrenceHelper appPrefrenceHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        appPrefrenceHelper = AppPrefrenceHelper.init(this);
        setLanguage(this);
    }

    public static void setLanguage(Context context) {
        if (context == null) {
            Log.e(TAG, "context === null");
            return;
        }
        String prefLanguage = MyAppClass.appPrefrenceHelper.getLanguage();
        Log.e(TAG, "setLanguage: ->   " + prefLanguage);
        if (prefLanguage == null) {
            Log.e(TAG, "setLanguage: prefLanguage == null  ->   " + prefLanguage);
            prefLanguage = Locale.getDefault().getLanguage();
        }
        Locale locale = new Locale(prefLanguage);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
