package com.ep.video.player.videoplayer.Utills;

import java.io.File;

public class Files_FileUtils {

    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length();
        } else {
            return 0;
        }
    }

    public static String formatFileSize(long size) {
        if (size <= 0) return "0 B";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }

    public static void main(String[] args) {
        String filePath = "your_file_path_here";
        long fileSize = getFileSize(filePath);
        System.out.println("File size in bytes: " + fileSize);
        System.out.println("Formatted file size: " + formatFileSize(fileSize));
    }
}
