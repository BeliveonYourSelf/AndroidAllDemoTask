package com.camera.backgroundvideorecorder.Activities.VideoPlayer;

import static com.camera.load.ad.AdShow.getInstance;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.camera.backgroundvideorecorder.Activities.Comman.BaseActivity;
import com.camera.backgroundvideorecorder.Activities.Comman.SplashActivity;
import com.camera.backgroundvideorecorder.Adapters.AllVideoAdapter;
import com.camera.backgroundvideorecorder.Models.Video;
import com.camera.backgroundvideorecorder.R;
import com.camera.backgroundvideorecorder.Utils.GlobalVariable;
import com.camera.backgroundvideorecorder.databinding.ActivityAllVideoBinding;
import com.camera.backgroundvideorecorder.databinding.BottomSheetVideoInformationDialogLayoutBinding;
import com.camera.backgroundvideorecorder.databinding.BottomSheetVideoMoreDialogLayoutBinding;
import com.camera.backgroundvideorecorder.databinding.DialogDeleteVideoBinding;
import com.camera.load.ad.AdShow;
import com.camera.load.ad.HandleClick.HandleClick;
import com.camera.load.ad.Qureka.QuerkaNative;
import com.camera.load.utils.AdConstant;
import com.camera.load.utils.AdUtils;
import com.camera.load.utils.MyApplication;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AllVideoActivity extends BaseActivity {

    private static final String TAG = AllVideoActivity.class.getSimpleName();
    private static final int VIDEO_DELETE_REQUEST = 60;
    private static final int VIDEO_FAVOURITE_REQUEST = 54;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newSingleThreadExecutor();
    Activity activity;
    ActivityAllVideoBinding x;
    List<Video> videoList = new ArrayList<>();
    AllVideoAdapter allVideoAdapter;
    Video currentlyVideoEdit;
    int currentVideoEditPostion;
    List<Video> favoritesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityAllVideoBinding.inflate(getLayoutInflater());
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
        x.viewToolbar.textViewTitle.setText("All Video");
        QuerkaNative.getInstance(activity).QuerkaAd(x.viewToolbar.gifImageView, null,x.viewToolbar.qurekaTitle,null, x.viewToolbar.qurka);
        executor.execute(() -> {
            x.progressBarVideoLoading.setVisibility(View.VISIBLE);
            if (getIntent().hasExtra(GlobalVariable.VIDEO_NAME)) {
                x.viewToolbar.textViewTitle.setText(getIntent().getStringExtra(GlobalVariable.VIDEO_NAME));
                getAllVideoFromFolder();
            } else {
                getAllVideoFromDevice();
            }
            handler.post(this::SetAllVideo);
        });


    }

    private void getAllVideoFromFolder() {
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DATA};
        String selection;
        String[] selectionArgs;
        if (getIntent().hasExtra(GlobalVariable.BUCKET_ID)) {
            selection = MediaStore.Video.Media.BUCKET_ID + " like?";
            selectionArgs = new String[]{"%" + getIntent().getIntExtra(GlobalVariable.BUCKET_ID, 0) + "%"};
        } else {
            selection = MediaStore.Video.Media.DATA + " like?";
            selectionArgs = new String[]{"%/" + getIntent().getStringExtra(GlobalVariable.VIDEO_NAME).trim() + "/%"};
        }

        String sortOrder = MediaStore.Video.Media.DATE_TAKEN + " DESC";
        try (Cursor cursor = getApplicationContext().getContentResolver().query(collection, projection, selection, selectionArgs, sortOrder)) {

            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            int resolutionColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION);
            int date_addedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);
            int data_Column = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            while (cursor.moveToNext()) {

                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String resolution = cursor.getString(resolutionColumn);
                String date_added = cursor.getString(date_addedColumn);
                date_added = GlobalVariable.convertDate(date_added, "dd/MM/yyyy hh:mm:ss");
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                String data = cursor.getString(data_Column);
                videoList.add(new Video(String.valueOf(contentUri), name, duration, size, date_added, resolution, data));
            }
        }
    }

    private void SetAllVideo() {
        x.progressBarVideoLoading.setVisibility(View.GONE);
        if (videoList.isEmpty()) {
            //Video Not Available
            x.layoutDateNotFound.setVisibility(View.VISIBLE);
            x.recyclerViewVideo.setVisibility(View.GONE);
        } else {

            //Video Found
            x.layoutDateNotFound.setVisibility(View.GONE);
            x.recyclerViewVideo.setVisibility(View.VISIBLE);
            if (getIntent().hasExtra(GlobalVariable.FAVORITES)) {
                x.viewToolbar.textViewTitle.setText("Favourite");
                if (SplashActivity.getSharedPreferencesHelper.getFavorites() != null) {
                    if (SplashActivity.getSharedPreferencesHelper.getFavorites().isEmpty()) {
                        x.layoutDateNotFound.setVisibility(View.VISIBLE);
                        x.recyclerViewVideo.setVisibility(View.GONE);
                    } else {

                        for (int i = 0; i < SplashActivity.getSharedPreferencesHelper.getFavorites().size(); i++) {
                            for (int j = 0; j < videoList.size(); j++) {
                                if (videoList.get(j) != null) {
                                    if (SplashActivity.getSharedPreferencesHelper.getFavorites().get(i).getName().equals(videoList.get(j).getName())) {
                                        favoritesList.add(videoList.get(j));
                                    }
                                }

                            }
                        }
                        if (favoritesList.isEmpty()) {
                            x.layoutDateNotFound.setVisibility(View.VISIBLE);
                            x.recyclerViewVideo.setVisibility(View.GONE);
                        } else {

                            allVideoAdapter = new AllVideoAdapter(activity, favoritesList);
                            x.recyclerViewVideo.setAdapter(allVideoAdapter);
                        }
                    }


                } else {
                    x.layoutDateNotFound.setVisibility(View.VISIBLE);
                    x.recyclerViewVideo.setVisibility(View.GONE);
                }

            } else {
                allVideoAdapter = new AllVideoAdapter(activity, videoList);
                x.recyclerViewVideo.setAdapter(allVideoAdapter);
            }

        }
    }

    private void getAllVideoFromDevice() {
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DATA};

        String sortOrder = MediaStore.Video.Media.DATE_TAKEN + " DESC";
        try (Cursor cursor = getApplicationContext().getContentResolver().query(collection, projection, null, null, sortOrder)) {

            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            int resolutionColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION);
            int date_addedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);
            int data_Column = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            while (cursor.moveToNext()) {

                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String resolution = cursor.getString(resolutionColumn);
                String date_added = cursor.getString(date_addedColumn);
                date_added = GlobalVariable.convertDate(date_added, "dd/MM/yyyy hh:mm:ss");
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                String data = cursor.getString(data_Column);
                videoList.add(new Video(String.valueOf(contentUri), name, duration, size, resolution, date_added, data));
            }
        }
    }

    public void onMoreClick(Video video, int position) {
        currentVideoEditPostion = position;
        currentlyVideoEdit = video;
        showBottomSheetVideoDialog();
    }

    private void showBottomSheetVideoDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        BottomSheetVideoMoreDialogLayoutBinding bottomSheetVideoMoreDialogLayoutBinding = BottomSheetVideoMoreDialogLayoutBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetVideoMoreDialogLayoutBinding.getRoot());
        bottomSheetDialog.setOnDismissListener(dialog -> {
            // Instructions on bottomSheetDialog Dismiss
        });
        bottomSheetVideoMoreDialogLayoutBinding.layoutShareVideo.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            GlobalVariable.ShareVideo(activity, Uri.parse(currentlyVideoEdit.getUri()));

        });
        boolean videoFavorite = false;
        if (SplashActivity.getSharedPreferencesHelper.getFavorites() != null) {
            for (int i = 0; i < SplashActivity.getSharedPreferencesHelper.getFavorites().size(); i++) {
                if (SplashActivity.getSharedPreferencesHelper.getFavorites().get(i).getName().equals(currentlyVideoEdit.getName())) {
                    videoFavorite = true;
                }
            }

        }
        if (videoFavorite) {
            bottomSheetVideoMoreDialogLayoutBinding.imageViewLike.setImageResource(R.drawable.ic_favorite);
            bottomSheetVideoMoreDialogLayoutBinding.textViewFavourite.setText("Remove favorite");
        } else {
            bottomSheetVideoMoreDialogLayoutBinding.imageViewLike.setImageResource(R.drawable.ic_make_it_favourite);
            bottomSheetVideoMoreDialogLayoutBinding.textViewFavourite.setText("Make it Favourite");
        }
        boolean finalVideoFavorite = videoFavorite;
        bottomSheetVideoMoreDialogLayoutBinding.layoutMakeItFavourite.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            if (finalVideoFavorite) {
                if (SplashActivity.getSharedPreferencesHelper.getFavorites() != null) {
                    for (int i = 0; i < SplashActivity.getSharedPreferencesHelper.getFavorites().size(); i++) {
                        if (SplashActivity.getSharedPreferencesHelper.getFavorites().get(i).getName().equals(currentlyVideoEdit.getName())) {
                            SplashActivity.getSharedPreferencesHelper.removeFavorite(i);
                        }
                    }
                    if (getIntent().hasExtra(GlobalVariable.FAVORITES)) {
                        favoritesList.remove(currentlyVideoEdit);
                    }
                    allVideoAdapter.notifyDataSetChanged();
                    if (allVideoAdapter.getItemCount() == 0) {
                        x.layoutDateNotFound.setVisibility(View.VISIBLE);
                        x.recyclerViewVideo.setVisibility(View.GONE);
                    }

                }
                Toast.makeText(activity, "UnFavourite Successfully", Toast.LENGTH_SHORT).show();

            } else {
                SplashActivity.getSharedPreferencesHelper.addFavorite(currentlyVideoEdit);
                allVideoAdapter.notifyItemChanged(currentVideoEditPostion);
                allVideoAdapter.notifyDataSetChanged();
                Toast.makeText(activity, "Video Favourite Successfully", Toast.LENGTH_SHORT).show();
            }

        });
        bottomSheetVideoMoreDialogLayoutBinding.layoutPlayInPopupPlayer.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            GlobalVariable.getInstance().playingVideo = currentlyVideoEdit;
            List<Video> tempVideos = new ArrayList<>();
            if (getIntent().hasExtra(GlobalVariable.FAVORITES)) {
                for (int i = 0; i < favoritesList.size(); i++) {
                    if (favoritesList.get(i) != null) {
                        tempVideos.add(favoritesList.get(i));
                    }
                }
            } else {
                for (int i = 0; i < videoList.size(); i++) {
                    if (videoList.get(i) != null) {
                        tempVideos.add(videoList.get(i));
                    }
                }
            }
            GlobalVariable.getInstance().videoItemsPlaylist = tempVideos;

            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).showFloatingView(activity, true);
            }
        });
        bottomSheetVideoMoreDialogLayoutBinding.layoutProperties.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            showBottomSheetVideoInformationDialog(currentlyVideoEdit);
        });
        bottomSheetVideoMoreDialogLayoutBinding.layoutDelete.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                PendingIntent editPendingIntent = MediaStore.createTrashRequest(getApplicationContext().getContentResolver(), Collections.singleton(Uri.parse(currentlyVideoEdit.getUri())), true);
                try {
                    startIntentSenderForResult(editPendingIntent.getIntentSender(), VIDEO_DELETE_REQUEST, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                DeleteVideo();
            }
        });

        bottomSheetDialog.show();

    }

    private void DeleteVideo() {
        Dialog dialog = new Dialog(activity);
        DialogDeleteVideoBinding dialogDeleteVideoBinding = DialogDeleteVideoBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogDeleteVideoBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialogDeleteVideoBinding.textViewYes.setOnClickListener(v -> {
            dialog.dismiss();
            String[] projection = {MediaStore.Video.Media._ID};
            String selection = MediaStore.Video.Media.DATA + " = ?";
            String[] selectionArgs = new String[]{String.valueOf(currentlyVideoEdit.getData())};
            Uri queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = activity.getContentResolver();
            Cursor c = contentResolver.query(queryUri, projection, selection, selectionArgs, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    long id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    Uri deleteUri = ContentUris.withAppendedId(queryUri, id);
                    contentResolver.delete(deleteUri, null, null);
                }
                videoList.remove(currentlyVideoEdit);
                allVideoAdapter.notifyItemRemoved(currentVideoEditPostion);
                allVideoAdapter.notifyDataSetChanged();
                c.close();
            }


        });
        dialogDeleteVideoBinding.textViewNo.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_DELETE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                videoList.remove(currentlyVideoEdit);
                allVideoAdapter.notifyItemRemoved(currentVideoEditPostion);
                allVideoAdapter.notifyDataSetChanged();
            } else {

            }
        }
        if (requestCode == VIDEO_FAVOURITE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

            } else {

            }
        }
    }

    private void showBottomSheetVideoInformationDialog(Video video) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        BottomSheetVideoInformationDialogLayoutBinding bottomSheetVideoInformationDialogLayoutBinding = BottomSheetVideoInformationDialogLayoutBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetVideoInformationDialogLayoutBinding.getRoot());
        bottomSheetDialog.setOnDismissListener(dialog -> {
            // Instructions on bottomSheetDialog Dismiss
        });
        bottomSheetVideoInformationDialogLayoutBinding.textViewLocation.setText(video.getData() + "");
        bottomSheetVideoInformationDialogLayoutBinding.textViewFormat.setText(GlobalVariable.getFileExtension(video.getData() + ""));
        bottomSheetVideoInformationDialogLayoutBinding.textViewSize.setText(GlobalVariable.toHumanReadable(video.getSize()));
        bottomSheetVideoInformationDialogLayoutBinding.textViewDuration.setText(GlobalVariable.makeReadable(video.getDuration()));
        bottomSheetVideoInformationDialogLayoutBinding.textViewResolution.setText(video.getResolution());
        bottomSheetVideoInformationDialogLayoutBinding.textViewDate.setText(video.getDate_added());
        bottomSheetDialog.show();

    }

    public void onItemClick(Video video, int position) {
        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                List<Video> tempVideos = new ArrayList<>();
                if (getIntent().hasExtra(GlobalVariable.FAVORITES)) {
                    for (int i = 0; i < favoritesList.size(); i++) {
                        if (favoritesList.get(i) != null) {
                            tempVideos.add(favoritesList.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < videoList.size(); i++) {
                        if (videoList.get(i) != null) {
                            tempVideos.add(videoList.get(i));
                        }
                    }
                }
                GlobalVariable.getInstance().videoItemsPlaylist = tempVideos;
                GlobalVariable.getInstance().playingVideo = video;
                GlobalVariable.getInstance().seekPosition = 0;
                if (GlobalVariable.getInstance().getPlayer() == null) {
                    Log.e(TAG, "Go to --------->  getPlayer === NULL  " );
                } else if (!GlobalVariable.getInstance().isMutilSelectEnalble) {
                    if (!GlobalVariable.getInstance().isPlayingAsPopup()) {
                        Log.e(TAG, "Go to --------->  isPlayingAsPopup VideoPlayerActivity: " );
                        GlobalVariable.getInstance().videoService.playVideo(GlobalVariable.getInstance().seekPosition, false);
                        Intent intent = new Intent(activity, VideoPlayerActivity.class);
                        startActivity(intent);
                        if (GlobalVariable.getInstance().videoService != null)
                            GlobalVariable.getInstance().videoService.releasePlayerView();
                    } else {
                        Log.e(TAG, "showFloatingView" );
                        ((BaseActivity) activity).showFloatingView(activity, true);
                    }
                }
            }
        }, AdUtils.ClickType.MAIN_CLICK);

    }
    @Override
    public void onBackPressed() {
        AdShow.getInstance(activity).ShowAd(adShow -> AllVideoActivity.super.onBackPressed(), AdUtils.ClickType.BACK_CLICK);
    }
}
