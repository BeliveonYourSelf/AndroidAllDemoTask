package com.camera.backgroundvideorecorder.Activities.VideoPlayer;

import static com.camera.load.ad.AdShow.getInstance;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.camera.backgroundvideorecorder.Activities.Comman.BaseActivity;
import com.camera.backgroundvideorecorder.Adapters.AllVideoFolderAdapter;
import com.camera.backgroundvideorecorder.Models.VideoFolder;
import com.camera.backgroundvideorecorder.databinding.ActivityAllVideoFolderBinding;
import com.camera.load.ad.AdShow;
import com.camera.load.ad.Qureka.QuerkaNative;
import com.camera.load.utils.AdConstant;
import com.camera.load.utils.AdUtils;
import com.camera.load.utils.MyApplication;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AllVideoFolderActivity extends BaseActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newSingleThreadExecutor();
    Activity activity;
    ActivityAllVideoFolderBinding x;
    ArrayList<VideoFolder> videoFolderArrayList = new ArrayList<>();
    boolean checkFolder;
    boolean notCheckFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityAllVideoFolderBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        if (!MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            ShowAd();
        }
        todo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            ShowAd();
        }

    }

    private void ShowAd() {
        getInstance(activity).ShowNativeAd(x.nativeBottomAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_BANNER);
        if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
            getInstance(activity).ShowNativeAd(x.nativeBig.nativeAdLayout, AdUtils.NativeType.NATIVE_BIG);
        } else {
            x.nativeBig.nativeAdLayout.setVisibility(View.GONE);
        }
    }

    private void todo() {
        x.viewToolbar.imageViewBack.setOnClickListener(v -> onBackPressed());
        x.viewToolbar.textViewTitle.setText("All Video Folder");
        QuerkaNative.getInstance(activity).QuerkaAd(x.viewToolbar.gifImageView, null, x.viewToolbar.qurekaTitle, null, x.viewToolbar.qurka);
        executor.execute(() -> {
            x.progressBarVideoLoading.setVisibility(View.VISIBLE);
            getAllVideoFolderFromDevice();
            handler.post(this::SetAllFolder);
        });

    }

    private void SetAllFolder() {
        x.progressBarVideoLoading.setVisibility(View.GONE);
        if (videoFolderArrayList.isEmpty()) {
            //Video Not Available
            x.layoutDateNotFound.setVisibility(View.VISIBLE);
            x.recyclerViewVideoFolder.setVisibility(View.GONE);
        } else {

            //Video Found
            x.layoutDateNotFound.setVisibility(View.GONE);
            x.recyclerViewVideoFolder.setVisibility(View.VISIBLE);
            AllVideoFolderAdapter allVideoFolderAdapter = new AllVideoFolderAdapter(activity, videoFolderArrayList);
            x.recyclerViewVideoFolder.setAdapter(allVideoFolderAdapter);

        }
    }

    private void getAllVideoFolderFromDevice() {
        videoFolderArrayList.clear();
        Log.e("TAG", "getAllVideoFolderFromDevice: size --->"+videoFolderArrayList.size() );
        int int_position = 0;
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        Cursor cursor;
        int columnIndexData, columnIndexFolderName, columnIndexFolderSize, bucketId;

        String absolutePathOfVideo;
        String absoluteSizeOfVideo;
        int bucketIndex;


        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.BUCKET_ID};

        final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(collection, projection, null, null, orderBy + " DESC");
        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        columnIndexFolderSize = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
        bucketId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID);
        if(!videoFolderArrayList.isEmpty()) {
            for (int i = 0; i < videoFolderArrayList.size(); i++) {
                Log.e("TAG", "For Loop: ");
                if (cursor.getString(columnIndexFolderName) != null) {
                    Log.e("TAG", "Curson not Null: ");
                    notCheckFolder = false;
                    if (videoFolderArrayList.get(i).getFoldername().equals(cursor.getString(columnIndexFolderName))) {
                        checkFolder = true;
                        int_position = i;
                        break;
                    } else {
                        checkFolder = false;
                    }
                } else {
                    notCheckFolder = true;
                }

            }
        }
        while (cursor.moveToNext())  {
            Log.e("TAG", "cursor.moveToNext()");
            absolutePathOfVideo = cursor.getString(columnIndexData);
            absoluteSizeOfVideo = cursor.getString(columnIndexFolderSize);
            bucketIndex = cursor.getInt(bucketId);

            if (!notCheckFolder) {
                if (checkFolder) {
                    ArrayList<String> pathlist = new ArrayList<>(videoFolderArrayList.get(int_position).getStringArrayList());
                    pathlist.add(absolutePathOfVideo);
                    videoFolderArrayList.get(int_position).setStringArrayList(pathlist);
                    long temp_ = 0;
                    if (videoFolderArrayList.get(int_position).getFolderSize() != null) {
                        temp_ = Long.parseLong(videoFolderArrayList.get(int_position).getFolderSize());
                    }
                    if (absoluteSizeOfVideo != null) {
                        long new_temp = Long.parseLong(absoluteSizeOfVideo);
                        videoFolderArrayList.get(int_position).setFolderSize(String.valueOf(temp_ + new_temp));
                    }


                } else {
                    ArrayList<String> pathlist = new ArrayList<>();
                    pathlist.add(absolutePathOfVideo);
                    VideoFolder videoFolder = new VideoFolder();
                    videoFolder.setFoldername(cursor.getString(columnIndexFolderName));
                    videoFolder.setFolderSize(cursor.getString(columnIndexFolderSize));
                    videoFolder.setBucketId(bucketIndex);
                    videoFolder.setStringArrayList(pathlist);
                    videoFolderArrayList.add(videoFolder);


                }
            }
        }


    }

    @Override
    public void onBackPressed() {
        AdShow.getInstance(activity).ShowAd(adShow -> AllVideoFolderActivity.super.onBackPressed(), AdUtils.ClickType.BACK_CLICK);
    }

}