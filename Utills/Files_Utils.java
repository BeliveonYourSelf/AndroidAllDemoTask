package com.ep.video.player.videoplayer.Utills;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.webkit.MimeTypeMap;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.InputDeviceCompat;

import com.google.android.exoplayer2.util.MimeTypes;

import net.lingala.zip4j.util.InternalZipConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Locale;


public class Files_Utils {
    


    public static boolean isInterstitialShow = true;
    public static boolean isShow = false;
    static String[] types = {".pdf", ".xml", ".java", ".php", ".html", ".htm", ".text/x-asm", ".pl", ".xls", ".xlsx", ".xld", ".xlc", ".ppt", ".pptx", ".ppsx", ".pptm", ".doc", ".docx", ".msg", ".odt", ".txt", ".tex", ".text", ".wpd", ".wps"};

    public static String formateSize(long j) {
        if (j <= 0) {
            return "0 B";
        }
        int log10 = (int) (Math.log10(j) / Math.log10(1024.0d));
        double fileSize = j / Math.pow(1024.0d, log10);
        return new DecimalFormat("#,##0.#").format(fileSize) + " " + new String[]{"B", "KB", "MB", "GB", "TB"}[log10];
    }

    public static boolean externalMemoryAvailable(Context context) {
        boolean z;
        boolean z2;
        String externalStorageState = Environment.getExternalStorageState();
        if ("mounted".equals(externalStorageState)) {
            z = true;
            z2 = true;
        } else {
            z = "mounted_ro".equals(externalStorageState);
            z2 = false;
        }
        return z && z2;
    }

    public static String getExternalStoragePath(Context context, boolean z) {
        String str = "";
        try {
            for (StorageVolume storageVolume : ((StorageManager) context.getSystemService(Context.STORAGE_SERVICE)).getStorageVolumes()) {
                if (z && Build.VERSION.SDK_INT >= 30) {
                    str = storageVolume.getDirectory().getPath();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getExternalStoragePath_(Context context, boolean z) {
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?> cls = Class.forName("android.os.storage.StorageVolume");
            Method method = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
            Method method2 = cls.getMethod("getPath", new Class[0]);
            Method method3 = cls.getMethod("isRemovable", new Class[0]);
            Object invoke = method.invoke(storageManager, new Object[0]);
            int length = Array.getLength(invoke);
            for (int i = 0; i < length; i++) {
                Object obj = Array.get(invoke, i);
                String str = (String) method2.invoke(obj, new Object[0]);
                if (z == ((Boolean) method3.invoke(obj, new Object[0])).booleanValue()) {
                    return str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String externalStoragePath = getExternalStoragePath(context, z);
        return (externalStoragePath == null || externalStoragePath.isEmpty()) ? "" : externalStoragePath;
    }

    public static String getMimeTypeFromFilePath(String str) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFilenameExtension(str));
        if (mimeTypeFromExtension == null) {
            return null;
        }
        return mimeTypeFromExtension;
    }

    public static String getFilenameExtension(String str) {
        return str.substring(str.lastIndexOf(".") + 1);
    }

    public static File moveFile(File file, File file2) throws IOException {
        FileChannel fileChannel;
        File file3 = new File(file2, file.getName());
        FileChannel fileChannel2 = null;
        try {
            fileChannel = new FileOutputStream(file3).getChannel();
        } catch (Throwable th) {
            fileChannel = null;
        }
        try {
            fileChannel2 = new FileInputStream(file).getChannel();
            fileChannel2.transferTo(0L, fileChannel2.size(), fileChannel);
            fileChannel2.close();
            file.delete();
            if (fileChannel2 != null) {
                fileChannel2.close();
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
            return file3;
        } catch (Throwable th2) {
            if (fileChannel2 != null) {
                fileChannel2.close();
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
        }
        return file3;
    }

    public static void moveDirectory(File file, File file2, Context context) throws IOException {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdir();
            }
            String[] list = file.list();
            for (int i = 0; i < file.listFiles().length; i++) {
                moveDirectory(new File(file, list[i]), new File(file2, list[i]), context);
            }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileInputStream.close();
                fileOutputStream.close();
                MediaScannerConnection.scanFile(context, new String[]{file2.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                return;
            }
        }
    }

    public static boolean copyDirectoryOneLocationToAnotherLocation(File file, File file2, Context context) {
        boolean z = false;
        try {
            if (file.isDirectory()) {
                if (!file2.exists()) {
                    file2.mkdir();
                }
                try {
                    String[] list = file.list();
                    for (int i = 0; i < file.listFiles().length; i++) {
                        copyDirectoryOneLocationToAnotherLocation(new File(file, list[i]), new File(file2, list[i]), context);
                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        fileOutputStream.close();
                        try {
                            MediaScannerConnection.scanFile(context, new String[]{file2.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String str, Uri uri) {
                                }
                            });
                            return true;
                        } catch (Exception e2) {
                            z = true;
                            return z;
                        }
                    }
                }
            } catch (Exception e3) {
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return false;
        }
        return z;
        
        
    }
   

    public static void updateLanguage(Context context, String str) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        context.createConfigurationContext(configuration);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static void changeTheme(Context context) {
        int theme = Files_PreferencesManager.getTheme(context);
        if (theme == 0) {
            AppCompatDelegate.setDefaultNightMode(1);
        } else if (theme == 1) {
            AppCompatDelegate.setDefaultNightMode(2);
        } else if (theme != 2) {
        } else {
            AppCompatDelegate.setDefaultNightMode(-1);
        }
    }





    public static String getAllAudioFileSizes(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_size"}, null, null, null);
        long j = 0;
        if (query != null) {
            while (query.moveToNext()) {
                j += query.getLong(query.getColumnIndexOrThrow("_size"));
            }
            query.close();
            return getReadableSize(j);
        }
        return getReadableSize(0L);
    }

    public static String getAllImageFileSizes(Context context) {
        try {
            Cursor query = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_size"}, null, null, null);
            if (query != null) {
                long totalSize = 0;
                int sizeColumnIndex = query.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

                while (query.moveToNext()) {
                    totalSize += query.getLong(sizeColumnIndex);
                }
                query.close();
                return getReadableSize(totalSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0 B";
    }

    public static long getFileSize(File file) {
        long j = 0;
        if (file != null) {
            if (file.isDirectory()) {
                if (file.listFiles() != null) {
                    for (File file2 : file.listFiles()) {
                        j += getFileSize(file2);
                    }
                    return j;
                }
                return 0L;
            }
            return file.length();
        }
        return 0L;
    }

    static boolean contains(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.endsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String getAllDocumentFileSizes(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_data", "_size", "mime_type"}, null, null, null);
        if (query != null && query.getCount() > 0) {
            if (query.moveToFirst()) {
                long j = 0;
                do {
                    long j2 = query.getLong(query.getColumnIndexOrThrow("_size"));
                    String string = query.getString(query.getColumnIndexOrThrow("_data"));
                    if (j2 != 0 && string != null && contains(types, string)) {
                        j += new File(string).length();
                    }
                } while (query.moveToNext());
                return getReadableSize(j);
            }
            query.close();
        }
        return getReadableSize(0L);
    }

    public static String getAllCompressFileSizes(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_data", "_size", "mime_type"}, null, null, null);
        if (query != null && query.moveToFirst()) {
            long j = 0;
            do {
                long j2 = query.getLong(query.getColumnIndex("_size"));
                String string = query.getString(query.getColumnIndex("_data"));
                if (j2 != 0 && string != null && string.endsWith(".zip")) {
                    j += new File(string).length();
                }
            } while (query.moveToNext());
            query.close();
            return getReadableSize(j);
        }
        return getReadableSize(0L);
    }

    public static String getAllApkFileSizes(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_data", "_size", "mime_type"}, null, null, null);
        long j = 0;
        if (query != null && query.moveToFirst()) {
            do {
                query.getLong(query.getColumnIndex("_size"));
                String string = query.getString(query.getColumnIndex("_data"));
                if (string != null && string.endsWith(".apk")) {
                    j += new File(string).length();
                }
            } while (query.moveToNext());
            query.close();
            return getReadableSize(j);
        }
        return getReadableSize(0L);
    }

    public static String getAllVideoFileSizes(Context context) {
        Cursor query = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_size"}, null, null, null);
        long j = 0;
        if (query != null) {
            while (query.moveToNext()) {
                j += query.getLong(query.getColumnIndex("_size"));
            }
            query.close();
            return getReadableSize(j);
        }
        return getReadableSize(0L);
    }

    public static String getReadableSize(long size) {
        if (size <= 0) {
            return "0 B";
        }

        final int unit = 1024;
        // Ensure that exp does not go below 0, which corresponds to bytes
        int exp = Math.max(0, (int) (Math.log(size) / Math.log(unit)));
        // Handle bytes separately (since they do not have a prefix)
        if (exp == 0) {
            return String.format("%d B", size);
        }
        String pre = "KMGTPE".charAt(exp-1) + "B";
        return String.format("%.1f %s", size / Math.pow(unit, exp), pre);
    }

    public static void setOrientation(Activity activity) {
        if (Build.VERSION.SDK_INT == 26) {
            activity.setRequestedOrientation(-1);
        } else {
            activity.setRequestedOrientation(1);
        }
    }

    public static String compressImage(Context context, String str) {
        try {
            String realPathFromURI = getRealPathFromURI(context, str);
            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap decodeFile = BitmapFactory.decodeFile(realPathFromURI, options);
            int i = options.outHeight;
            int i2 = options.outWidth;
            float f = i2 / i;
            float f2 = i;
            if (f2 > 816.0f || i2 > 612.0f) {
                if (f < 0.75f) {
                    i2 = (int) ((816.0f / f2) * i2);
                    i = (int) 816.0f;
                } else {
                    i = f > 0.75f ? (int) ((612.0f / i2) * f2) : (int) 816.0f;
                    i2 = (int) 612.0f;
                }
            }
            options.inSampleSize = calculateInSampleSize(options, i2, i);
            options.inJustDecodeBounds = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16384];
            try {
                decodeFile = BitmapFactory.decodeFile(realPathFromURI, options);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
            try {
                bitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
            }
            float f3 = i2;
            float f4 = f3 / options.outWidth;
            float f5 = i;
            float f6 = f5 / options.outHeight;
            float f7 = f3 / 2.0f;
            float f8 = f5 / 2.0f;
            Matrix matrix = new Matrix();
            matrix.setScale(f4, f6, f7, f8);
            Canvas canvas = new Canvas(bitmap);
            canvas.setMatrix(matrix);
            canvas.drawBitmap(decodeFile, f7 - (decodeFile.getWidth() / 2), f8 - (decodeFile.getHeight() / 2), new Paint(2));
            try {
                int attributeInt = new ExifInterface(realPathFromURI).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 0);

                Matrix matrix2 = new Matrix();
                if (attributeInt == 6) {
                    matrix2.postRotate(90.0f);

                } else if (attributeInt == 3) {
                    matrix2.postRotate(180.0f);

                } else if (attributeInt == 8) {
                    matrix2.postRotate(270.0f);

                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, true);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            String filename = getFilename();
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(filename));
            } catch (FileNotFoundException e4) {
                e4.printStackTrace();
            }
            return filename;
        } catch (Exception e5) {
            e5.printStackTrace();
            return "";
        }
    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + InternalZipConstants.ZIP_FILE_SEPARATOR + System.currentTimeMillis() + ".jpg";
    }

    private static String getRealPathFromURI(Context context, String str) {
        Uri parse = Uri.parse(str);
        Cursor query = context.getContentResolver().query(parse, null, null, null, null);
        if (query == null) {
            return parse.getPath();
        }
        query.moveToFirst();
        return query.getString(query.getColumnIndex("_data"));
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int round;
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 > i2 || i4 > i) {
            round = Math.round(i3 / i2);
            int round2 = Math.round(i4 / i);
            if (round >= round2) {
                round = round2;
            }
        } else {
            round = 1;
        }
        while ((i4 * i3) / (round * round) > i * i2 * 2) {
            round++;
        }
        return round;
    }

    public static void setFullScreenView(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {
            if (Build.VERSION.SDK_INT >= 30) {
                WindowInsetsController windowInsetsController = decorView.getWindowInsetsController();
                if (windowInsetsController != null) {
                    windowInsetsController.hide(WindowInsets.Type.navigationBars());
                    windowInsetsController.setSystemBarsBehavior(2);
                    return;
                }
                return;
            }
            int i = activity.getResources().getConfiguration().uiMode & 48;
            if (Files_PreferencesManager.getTheme(activity) == 1 || i == 32) {
                if (Build.VERSION.SDK_INT >= 23) {
                    decorView.setSystemUiVisibility((decorView.getSystemUiVisibility() & 8192) | 2 | 4096);
                    return;
                }
                return;
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(12290);
            return;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
    }




    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        Uri uri2;
        String str;
        String[] strArr;
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                return Environment.getExternalStorageDirectory() + InternalZipConstants.ZIP_FILE_SEPARATOR + split[1];
            } else if (isDownloadsDocument(uri)) {
                uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue());
            } else if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str2 = split2[0];
                if ("image".equals(str2)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str2)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (MimeTypes.BASE_TYPE_AUDIO.equals(str2)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                uri2 = uri;
                str = "_id=?";
                strArr = new String[]{split2[1]};
                if (!"content".equalsIgnoreCase(uri2.getScheme())) {
                    if (isGooglePhotosUri(uri2)) {
                        return uri2.getLastPathSegment();
                    }
                    try {
                        Cursor query = context.getContentResolver().query(uri2, new String[]{"_data"}, str, strArr, null);
                        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
                        if (query.moveToFirst()) {
                            return query.getString(columnIndexOrThrow);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if ("file".equalsIgnoreCase(uri2.getScheme())) {
                    return uri2.getPath();
                }
                return null;
            }
        }
        uri2 = uri;
        str = null;
        strArr = null;
        if (!"content".equalsIgnoreCase(uri2.getScheme())) {
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
