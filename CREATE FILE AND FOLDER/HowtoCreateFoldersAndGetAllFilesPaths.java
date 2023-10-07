package com.camera.backgroundvideorecorder;

import android.os.Environment;

import com.camera.backgroundvideorecorder.Utils.Utils;

import java.io.File;

public class HowtoCreateFoldersAndGetAllFilesPaths {
     Utils.createFileFolder();

    public class Utilss {

        public static File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/Facebook");
        public static File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/Instagram");
        public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/Whatsapp");
        public static File RootDirectoryTwitter = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/Twitter");
        public static File RootDirectoryTikTok = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/TikTok");
        public static File RootDirectoryShareChat = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/ShareChat");
        public static File RootDirectoryChingari = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/Chingari");
        public static File RootDirectoryMoj = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/Moj");
        public static File RootDirectoryJosh = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/Josh");
        public static File RootDirectoryTiki = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/TikiShort");
        public static File RootDirectoryWallpaper = new File(Environment.getExternalStorageDirectory() + "/Download/All Video Download/WallPaper");


        public static String RootDirectoryInsta = "/All Video Download/Instagram/";

        public static String RootDirectoryFacebook = "/All Video Download/Facebook/";
        public static String RootTwitter = "/All Video Download/Twitter/";
        public static String RootTikTOk = "/All Video Download/TikTok/";
        public static String RootSharechat = "/All Video Download/ShareChat/";

        public static void createFileFolder() {
            if (!RootDirectoryFacebookShow.exists()) {
                RootDirectoryFacebookShow.mkdirs();
            }
            if (!RootDirectoryInstaShow.exists()) {
                RootDirectoryInstaShow.mkdirs();
            }

            if (!RootDirectoryWhatsappShow.exists()) {
                RootDirectoryWhatsappShow.mkdirs();
            }
            if (!RootDirectoryTwitter.exists()) {
                RootDirectoryTwitter.mkdirs();
            }
            if (!RootDirectoryTikTok.exists()) {
                RootDirectoryTikTok.mkdirs();
            }
            if (!RootDirectoryShareChat.exists()) {
                RootDirectoryShareChat.mkdirs();
            }
        }
    }
}

