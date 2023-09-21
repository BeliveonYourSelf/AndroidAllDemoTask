package com.tenoku.hornsound.audiotool.Thomsan_pranksound;

import static android.view.View.GONE;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.iten.tenoku.ad.AdShow;
import com.iten.tenoku.ad.HandleClick.HandleClick;
import com.iten.tenoku.utils.AdConstant;
import com.iten.tenoku.utils.AdUtils;
import com.iten.tenoku.utils.MyApplication;
import com.tenoku.hornsound.audiotool.Comman.BaseActivity;
import com.tenoku.hornsound.audiotool.R;
import com.tenoku.hornsound.audiotool.adapters.AllAudioAdapter;
import com.tenoku.hornsound.audiotool.databinding.ActivityAllAudioBinding;
import com.tenoku.hornsound.audiotool.model.Audio;
import com.tenoku.hornsound.audiotool.utils.GlobalVariable;
import com.tenoku.hornsound.audiotool.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Thomsan_AllSongsActivity extends BaseActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newSingleThreadExecutor();
    List<Audio> audioList = new ArrayList<>();
    Activity activity;
    ActivityAllAudioBinding x;
    AllAudioAdapter allAudioAdapter;
    private MenuItem itemToHide;
    private MenuItem itemToShow;
    private Menu menus;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityAllAudioBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        setSupportActionBar(x.toolbar);
        todo();
    }

    private void todo() {

        // x.toolbar.imageViewBack.setOnClickListener(v -> onBackPressed());
        // x.toolbar.textViewTitle.setText("Audio");
        // QuerkaNative.getInstance(Thomsan_activity).QuerkaAd(x.toolbar.gifImageView, null, x.toolbar.adTitle, null, x.toolbar.qurka);
        // x.toolbar.qurka.setVisibility(GONE);
        if (getIntent().hasExtra(GlobalVariable.AUDIO_NAME)) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(GlobalVariable.AUDIO_NAME));
        } else {
            getSupportActionBar().setTitle(R.string.audio_tools);
        }


        executor.execute(new Runnable() {
            @Override
            public void run() {
                x.progressBarVideoLoading.setVisibility(View.VISIBLE);
                if (getIntent().hasExtra(GlobalVariable.AUDIO_NAME)) {
                    //  x.viewToolbar.textViewTitle.setText(getIntent().getStringExtra(GlobalVariable.AUDIO_NAME));
                    getAllAudioFromFolder();
                } else {
                    getAllAudioFromDevice();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SetAllAudio();
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sortingmenu, menu);
        menus = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.sortbyaz:
                item.setVisible(false);
                MenuItem item1 = menus.findItem(R.id.sortbyza);
                item1.setVisible(true);
                Collections.sort(audioList, new Comparator<Audio>() {
                    @Override
                    public int compare(Audio o1, Audio o2) {

                        return o1.getName().compareTo(o2.getName());

                    }

                });
//                if (AdConstant.LIST_VIEW_PER_AD * 2 != 0) {
//                    for (int i = 0; i <= audioList.size(); i++) {
//                        if (i % AdConstant.LIST_VIEW_PER_AD * 2 == 0) {
//                            audioList.add(i, null);
//                        }
//                    }
//
//                }
                allAudioAdapter.notifyDataSetChanged();

                return true;

            case R.id.sortbyza:
                item.setVisible(false);
                MenuItem item2 = menus.findItem(R.id.sortbyaz);
                item2.setVisible(true);

//                MenuItem item3 = menus.findItem(R.id.sortbyaz);
//                MenuItem item4 = menus.findItem(R.id.sortbyza);
//                item3.setVisible(true);
//                item4.setVisible(false);

                Collections.sort(audioList, new Comparator<Audio>() {
                    @Override
                    public int compare(Audio o1, Audio o2) {

                        return o2.getName().compareTo(o1.getName());

                    }

                });
//                if (AdConstant.LIST_VIEW_PER_AD * 2 != 0) {
//                    for (int i = 0; i <= audioList.size(); i++) {
//                        if (i % AdConstant.LIST_VIEW_PER_AD * 2 == 0) {
//                            audioList.add(i, null);
//                        }
//                    }
//
//                }
                allAudioAdapter.notifyDataSetChanged();

                return true;
            case R.id.sortbydurationdown:

                Collections.sort(audioList, new Comparator<Audio>() {
                    @Override
                    public int compare(Audio o1, Audio o2) {

                        return o1.getDuration() - o2.getDuration();

                    }

                });
//                if (AdConstant.LIST_VIEW_PER_AD * 2 != 0) {
//                    for (int i = 0; i <= audioList.size(); i++) {
//                        if (i % AdConstant.LIST_VIEW_PER_AD * 2 == 0) {
//                            audioList.add(i, null);
//                        }
//                    }
//
//                }
                allAudioAdapter.notifyDataSetChanged();
                item.setVisible(false);
                MenuItem item3 = menus.findItem(R.id.sortbydurationdups);
                item3.setVisible(true);

                return true;
            case R.id.sortbydurationdups:

                Collections.sort(audioList, new Comparator<Audio>() {
                    @Override
                    public int compare(Audio o1, Audio o2) {

                        return o2.getDuration() - o1.getDuration();

                    }

                });
//                if (AdConstant.LIST_VIEW_PER_AD * 2 != 0) {
//                    for (int i = 0; i <= audioList.size(); i++) {
//                        if (i % AdConstant.LIST_VIEW_PER_AD * 2 == 0) {
//                            audioList.add(i, null);
//                        }
//                    }
//
//                }
                allAudioAdapter.notifyDataSetChanged();
                item.setVisible(false);
                MenuItem item4 = menus.findItem(R.id.sortbydurationdown);
                item4.setVisible(true);
                return true;

            case R.id.sortbydatedown:
                MenuItem item5 = menus.findItem(R.id.sortbydatedown);
                MenuItem item6 = menus.findItem(R.id.sortbydateup);
                item5.setVisible(false);
                item6.setVisible(true);
                Collections.sort(audioList, new Comparator<Audio>() {
                    @Override
                    public int compare(Audio o1, Audio o2) {

                        return o1.getDate_added().compareTo(o2.getDate_added());

                    }

                });
//                if (AdConstant.LIST_VIEW_PER_AD * 2 != 0) {
//                    for (int i = 0; i <= audioList.size(); i++) {
//                        if (i % AdConstant.LIST_VIEW_PER_AD * 2 == 0) {
//                            audioList.add(i, null);
//                        }
//                    }
//
//                }
                allAudioAdapter.notifyDataSetChanged();

                return true;
            case R.id.sortbydateup:
                MenuItem item7 = menus.findItem(R.id.sortbydatedown);
                MenuItem item8 = menus.findItem(R.id.sortbydateup);
                item7.setVisible(true);
                item8.setVisible(false);
                Collections.sort(audioList, new Comparator<Audio>() {
                    @Override
                    public int compare(Audio o1, Audio o2) {

                        return o2.getDate_added().compareTo(o1.getDate_added());

                    }

                });
//                if (AdConstant.LIST_VIEW_PER_AD * 2 != 0) {
//                    for (int i = 0; i <= audioList.size(); i++) {
//                        if (i % AdConstant.LIST_VIEW_PER_AD * 2 == 0) {
//                            audioList.add(i, null);
//                        }
//                    }
//
//                }
                allAudioAdapter.notifyDataSetChanged();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void getAllAudioFromFolder() {
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATE_ADDED, MediaStore.Audio.Media.DATA};
        String selection = MediaStore.Audio.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%/" + getIntent().getStringExtra(GlobalVariable.AUDIO_NAME) + "/%"};
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";
        try (Cursor cursor = getApplicationContext().getContentResolver().query(collection, projection, selection, selectionArgs, sortOrder)) {

            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int date_addedColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED);
            int data_Column = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            while (cursor.moveToNext()) {

                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String date_added = cursor.getString(date_addedColumn);
                date_added = Utils.convertDate(date_added, "dd/MM/yyyy hh:mm:ss");
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                String data = cursor.getString(data_Column);
                String colors[] = {"#FCCF68", "#71C7FF", "#9CA1FF", "#FF7FA5", "#60E1C3", "#D479F0", "#FF9065"};
                Random random = new Random();

                // Generate a random integer between 0 (inclusive) and 100 (exclusive)
                int randomNumber = random.nextInt(6);
                if (duration != 0) {
                    audioList.add(new Audio(contentUri, name, duration, size, date_added, data, Color.parseColor(colors[randomNumber])));
                }

            }
        }
    }

    private void getAllAudioFromDevice() {
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATE_ADDED,
                MediaStore.Audio.Media.DATA};
        String selection = MediaStore.Audio.Media.DURATION + " >= ?";
        String[] selectionArgs = new String[]{String.valueOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS))};
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";
        try (Cursor cursor = getApplicationContext().getContentResolver().query(collection, projection, selection, selectionArgs, sortOrder)) {

            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int date_addedColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED);
            int data_Column = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            while (cursor.moveToNext()) {

                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                String date_added = cursor.getString(date_addedColumn);
                date_added = Utils.convertDate(date_added, "dd/MM/yyyy hh:mm:ss");
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                String data = cursor.getString(data_Column);
                String colors[] = {"#FCCF68", "#71C7FF", "#9CA1FF", "#FF7FA5", "#60E1C3", "#D479F0", "#FF9065"};
                Random random = new Random();

                // Generate a random integer between 0 (inclusive) and 100 (exclusive)
                int randomNumber = random.nextInt(6);
                if (size != 0) {
                    audioList.add(new Audio(contentUri, name, duration, size, date_added, data, Color.parseColor(colors[randomNumber])));
                }

            }
        }
    }

    private void SetAllAudio() {
        x.progressBarVideoLoading.setVisibility(GONE);
        if (audioList.isEmpty()) {
            //Video Not Available
            x.layoutDateNotFound.setVisibility(View.VISIBLE);
            x.recyclerViewAudio.setVisibility(GONE);
        } else {
//            if (AdConstant.LIST_VIEW_PER_AD * 2 != 0) {
//                for (int i = 0; i <= audioList.size(); i++) {
//                    if (i % AdConstant.LIST_VIEW_PER_AD * 2 == 0) {
//                        audioList.add(i, null);
//                    }
//                }
//
//            }

            x.layoutDateNotFound.setVisibility(GONE);
            x.recyclerViewAudio.setVisibility(View.VISIBLE);
            allAudioAdapter = new AllAudioAdapter(activity, audioList);
            x.recyclerViewAudio.setAdapter(allAudioAdapter);
            //Video Found
        }
    }

    public void onMoreClick(Audio audio) {
        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {

            }
        }, AdUtils.ClickType.MAIN_CLICK);

    }

    @Override
    public void onBackPressed() {
        AdShow.getInstance(activity).ShowAd(adShow -> Thomsan_AllSongsActivity.super.onBackPressed(), AdUtils.ClickType.BACK_CLICK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
                setNativeInResume();
        }
    }

    private void setNativeInResume() {
        AdShow.getInstance(activity).ShowNativeAd(x.nativeBottomAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_BANNER);
        if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
            AdShow.getInstance(activity).ShowNativeAd(x.nativeBig.nativeAdLayout, AdUtils.NativeType.NATIVE_BIG);
        } else {
            x.nativeBig.nativeAdLayout.setVisibility(GONE);
        }
    }

    public void onMp3Click(Audio audio) {
        if (getIntent().hasExtra(GlobalVariable.AUDIO_NAME)) {
            AdShow.getInstance(activity).ShowAd(new HandleClick() {
                @Override
                public void Show(boolean adShow) {
                    File newFile = new File(audio.getData());
                    Intent intent1 = new Intent(activity, Thomsan_PrankSoundPlayActivity.class);
                    intent1.putExtra(GlobalVariable.MUSIC_FILE, newFile);
                    startActivity(intent1);
                }
            }, AdUtils.ClickType.MAIN_CLICK);


        } else {
            AdShow.getInstance(activity).ShowAd(new HandleClick() {
                @Override
                public void Show(boolean adShow) {
                    GlobalVariable.audio = audio;
                    Intent intent = new Intent(activity, Thomsan_PrankSoundPlayActivity.class);
                    startActivity(intent);
                }
            }, AdUtils.ClickType.MAIN_CLICK);

        }


    }
}