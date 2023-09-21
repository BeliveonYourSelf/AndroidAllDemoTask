package com.tenoku.hornsound.audiotool.Airhorn;


import static com.iten.tenoku.ad.AdShow.getInstance;
import static com.iten.tenoku.utils.AdConstant.commonFeatureHideList;
import static com.iten.tenoku.utils.AdUtils.ClickType.MAIN_CLICK;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.iten.tenoku.ad.AdShow;
import com.iten.tenoku.ad.HandleClick.HandleClick;
import com.iten.tenoku.ad.Qureka.QuerkaNative;
import com.iten.tenoku.utils.AdConstant;
import com.iten.tenoku.utils.AdUtils;
import com.iten.tenoku.utils.MyApplication;
import com.tenoku.hornsound.audiotool.Comman.BaseActivity;
import com.tenoku.hornsound.audiotool.Comman.SettingActivity;
import com.tenoku.hornsound.audiotool.FavouriteAct.FavoriteActivity;
import com.tenoku.hornsound.audiotool.R;
import com.tenoku.hornsound.audiotool.adapters.HomeSubAdapter;
import com.tenoku.hornsound.audiotool.database.DatabaseHelper;
import com.tenoku.hornsound.audiotool.databinding.ActivityHornCategoryBinding;
import com.tenoku.hornsound.audiotool.interfaces.ButtonClicked;
import com.tenoku.hornsound.audiotool.model.HomeSubModel;
import com.tenoku.hornsound.audiotool.utils.Constant;

import java.util.ArrayList;

public class HornCategoryActivity extends BaseActivity {


    Activity activity;
    ActivityHornCategoryBinding x;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DatabaseHelper myDb;
    ArrayList<HomeSubModel> videoPlayerHomeModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityHornCategoryBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        todo();
    }

    private void todo() {
        myDb = new DatabaseHelper(this);
        setData();
        x.toolbar.textViewTitle.setText("Horns");
        x.toolbar.imageViewBack.setOnClickListener(v -> onBackPressed());
        preferences = getSharedPreferences("database", MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences.getBoolean("FIRST_TIME", true)) {
            insertData();
        }
        QuerkaNative.getInstance(activity).QuerkaAd(x.toolbar.gifImageView,null,x.toolbar.adTitle,null,x.toolbar.qurka);
        if (!MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            setNativeInResume();
        }
        x.ivFave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance(activity).ShowAd(new HandleClick() {
                    @Override
                    public void Show(boolean adShow) {
                        startActivity(new Intent(activity, FavoriteActivity.class));
                    }
                }, MAIN_CLICK);
            }
        });
        x.ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstance(activity).ShowAd(new HandleClick() {
                    @Override
                    public void Show(boolean adShow) {
                        startActivity(new Intent(activity, SettingActivity.class));
                    }
                }, MAIN_CLICK);
            }
        });


    }

    private void setData() {

        videoPlayerHomeModelArrayList = new ArrayList<>();
        if (!commonFeatureHideList.contains(AdConstant.APP_FUNCTION.HORN.toString())) {
            videoPlayerHomeModelArrayList.add(new HomeSubModel(R.drawable.ic_whit_airhorn, Color.parseColor("#FEE7B1"), Color.parseColor("#FCCF68"), activity.getString(R.string.air_horns), 0));
        }
        if (!commonFeatureHideList.contains(AdConstant.APP_FUNCTION.HORN.toString())) {
            videoPlayerHomeModelArrayList.add(new HomeSubModel(R.drawable.ic_white_vehicle, Color.parseColor("#B7E3FE"), Color.parseColor("#76B7EB"), activity.getString(R.string.vehicle_horns), 1));
        }
        if (!commonFeatureHideList.contains(AdConstant.APP_FUNCTION.HORN.toString())) {
            videoPlayerHomeModelArrayList.add(new HomeSubModel(R.drawable.ic_white_bell, Color.parseColor("#CDCFFF"), Color.parseColor("#9CA1FF"), activity.getString(R.string.emergency_horn), 2));
        }
        if (!commonFeatureHideList.contains(AdConstant.APP_FUNCTION.HORN.toString())) {
            videoPlayerHomeModelArrayList.add(new HomeSubModel(R.drawable.ic_white_siren, Color.parseColor("#FEBFD2"), Color.parseColor("#FF7FA5"), activity.getString(R.string.siren_horns), 3));
        }
        if (!commonFeatureHideList.contains(AdConstant.APP_FUNCTION.HORN.toString())) {
            videoPlayerHomeModelArrayList.add(new HomeSubModel(R.drawable.ic_white_alert, Color.parseColor("#B0F0E0"), Color.parseColor("#60E1C3"), activity.getString(R.string.alert_horns), 4));
        }
        if (!commonFeatureHideList.contains(AdConstant.APP_FUNCTION.HORN.toString())) {
            videoPlayerHomeModelArrayList.add(new HomeSubModel(R.drawable.ic_white_bells, Color.parseColor("#E9BCF7"), Color.parseColor("#D479F0"), activity.getString(R.string.bell_horns), 5));
        }

        HomeSubAdapter videoPlayerHomeAdapter = new HomeSubAdapter(activity, videoPlayerHomeModelArrayList, new ButtonClicked() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        AdShow.getInstance(activity).ShowAd(new HandleClick() {
                            @Override
                            public void Show(boolean adShow) {
                                startActivity(new Intent(activity, HornListActivity.class).putExtra(Constant.HORN_TYPE, "0"));
                            }
                        }, AdUtils.ClickType.MAIN_CLICK);
                        break;
                    case 1:
                        AdShow.getInstance(activity).ShowAd(new HandleClick() {
                            @Override
                            public void Show(boolean adShow) {
                                startActivity(new Intent(activity, HornListActivity.class).putExtra(Constant.HORN_TYPE, "1"));
                            }
                        }, AdUtils.ClickType.MAIN_CLICK);

                        break;
                    case 2:
                        AdShow.getInstance(activity).ShowAd(new HandleClick() {
                            @Override
                            public void Show(boolean adShow) {
                                startActivity(new Intent(activity, HornListActivity.class).putExtra(Constant.HORN_TYPE, "2"));
                            }
                        }, AdUtils.ClickType.MAIN_CLICK);

                        break;
                    case 3:
                        AdShow.getInstance(activity).ShowAd(new HandleClick() {
                            @Override
                            public void Show(boolean adShow) {
                                startActivity(new Intent(activity, HornListActivity.class).putExtra(Constant.HORN_TYPE, "3"));
                            }
                        }, AdUtils.ClickType.MAIN_CLICK);

                        break;
                    case 4:
                        AdShow.getInstance(activity).ShowAd(new HandleClick() {
                            @Override
                            public void Show(boolean adShow) {
                                startActivity(new Intent(activity, HornListActivity.class).putExtra(Constant.HORN_TYPE, "4"));
                            }
                        }, AdUtils.ClickType.MAIN_CLICK);


                        break;
                    case 5:
                        AdShow.getInstance(activity).ShowAd(new HandleClick() {
                            @Override
                            public void Show(boolean adShow) {
                                startActivity(new Intent(activity, HornListActivity.class).putExtra(Constant.HORN_TYPE, "5"));
                            }
                        }, AdUtils.ClickType.MAIN_CLICK);


                        break;

                }
            }
        });

        x.recyclerViewHome.setAdapter(videoPlayerHomeAdapter);

    }

    private void insertData() {
        String locale = MyApplication.localeManager.getLanguage();
        Log.e("TAG", "localeManager get Locale:-----> " + locale);

        editor.putBoolean("FIRST_TIME", false).commit();
        myDb.insertData("airhornremix", R.drawable.ic_whit_airhorn, activity.getString(R.string.airhornremix), false, false, "0");
        myDb.insertData("carhorn", R.drawable.animaton_drawable_2, activity.getString(R.string.carhorn), false, true, "0");
        myDb.insertData("cavalry", R.drawable.animaton_drawable_3, activity.getString(R.string.cavalry), false, false, "0");
        myDb.insertData("clownhorn", R.drawable.animaton_drawable_4, activity.getString(R.string.clownhorn), false, false, "0");
        myDb.insertData("countdown", R.drawable.animaton_drawable_5, activity.getString(R.string.countdown), false, false, "0");
        myDb.insertData("djhorn", R.drawable.animaton_drawable_6, activity.getString(R.string.djhorn), false, true, "0");
        myDb.insertData("dukeshorn", R.drawable.animaton_drawable_7, activity.getString(R.string.dukeshorn), false, false, "0");


        myDb.insertData("emergency", R.drawable.animaton_drawable_8, activity.getString(R.string.emergency), false, false, "1");
        myDb.insertData("emergencyamongus", R.drawable.animaton_drawable_9, activity.getString(R.string.emergency3), false, false, "1");
        myDb.insertData("farthorn", R.drawable.animaton_drawable_10, activity.getString(R.string.farthorn), false, true, "1");
        myDb.insertData("ghostsiren", R.drawable.animaton_drawable_11, activity.getString(R.string.ghostsiren), false, false, "1");
        myDb.insertData("goalhorn", R.drawable.animaton_drawable_12, activity.getString(R.string.goalhorn), false, false, "1");
        myDb.insertData("hellahorn", R.drawable.animaton_drawable_13, activity.getString(R.string.hellahorn), false, false, "1");
        myDb.insertData("icecreambell", R.drawable.animaton_drawable_14, activity.getString(R.string.icecreambell), false, true, "1");


        myDb.insertData("miatahorn", R.drawable.animaton_drawable_15, activity.getString(R.string.miatahorn), false, false, "2");
        myDb.insertData("oldcarhorn", R.drawable.animaton_drawable_16, activity.getString(R.string.oldcarhorn), false, false, "2");
        myDb.insertData("partyhorn", R.drawable.animaton_drawable_17, activity.getString(R.string.partyhorn), false, true, "2");
        myDb.insertData("policesiren", R.drawable.animaton_drawable_18, activity.getString(R.string.policesiren), false, false, "2");
        myDb.insertData("sadairhornmeme", R.drawable.animaton_drawable_19, activity.getString(R.string.sadmemehorn), false, true, "2");
        myDb.insertData("schoolbell", R.drawable.animaton_drawable_20, activity.getString(R.string.schoolbell), false, false, "2");
        myDb.insertData("sirenwahwah", R.drawable.animaton_drawable_21, activity.getString(R.string.sirenwahwah), false, false, "2");


        myDb.insertData("sirenwhistle", R.drawable.animaton_drawable_22, activity.getString(R.string.sirenwhistle), false, false, "3");
        myDb.insertData("snailhorn", R.drawable.animaton_drawable_23, activity.getString(R.string.snailhorn), false, false, "3");
        myDb.insertData("spaceship", R.drawable.animaton_drawable_24, activity.getString(R.string.spaceship), false, true, "3");
        myDb.insertData("trafficjam", R.drawable.animaton_drawable_25, activity.getString(R.string.traffics), false, false, "3");
        myDb.insertData("trainbell", R.drawable.animaton_drawable_26, activity.getString(R.string.trainbell), false, true, "3");
        myDb.insertData("trainhorn", R.drawable.animaton_drawable_27, activity.getString(R.string.trainhorn), false, false, "3");
        myDb.insertData("traphorn", R.drawable.animaton_drawable_28, activity.getString(R.string.traphorn), false, false, "3");


        myDb.insertData("truckhorn", R.drawable.animaton_drawable_29, activity.getString(R.string.truckhorn), false, false, "4");
        myDb.insertData("victory", R.drawable.animaton_drawable_30, activity.getString(R.string.victoryhorn), false, false, "4");
        myDb.insertData("vikinghorn", R.drawable.animaton_drawable_31, activity.getString(R.string.vikinghorn), false, false, "4");
        myDb.insertData("vwhorn", R.drawable.animaton_drawable_32, activity.getString(R.string.wvhorn), false, true, "4");
        myDb.insertData("wrong", R.drawable.animaton_drawable_33, activity.getString(R.string.incorrect), false, false, "4");
        myDb.insertData("airhorn", R.drawable.animaton_drawable_34, activity.getString(R.string.airborne), false, false, "4");
        myDb.insertData("airhornsonata", R.drawable.animaton_drawable_35, activity.getString(R.string.airhornsonata), false, true, "4");


        myDb.insertData("alert", R.drawable.animaton_drawable_36, activity.getString(R.string.alerthorn), false, false, "5");
        myDb.insertData("airhornmega", R.drawable.animaton_drawable_37, activity.getString(R.string.airhornmega), false, false, "5");
        myDb.insertData("ahoogacorn", R.drawable.animaton_drawable_38, activity.getString(R.string.ahoogahorn), false, false, "5");
        myDb.insertData("ambulance", R.drawable.animaton_drawable_39, activity.getString(R.string.ambulance), false, false, "5");
        myDb.insertData("boathorn", R.drawable.animaton_drawable_40, activity.getString(R.string.boathorn), false, true, "5");
        myDb.insertData("bruh", R.drawable.animaton_drawable_41, activity.getString(R.string.brushhorn), false, false, "5");
        myDb.insertData("buzzer", R.drawable.animaton_drawable_42, activity.getString(R.string.buzzerhorn), false, true, "5");


    }

    @Override
    public void onBackPressed() {
        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                HornCategoryActivity.super.onBackPressed();
            }
        }, AdUtils.ClickType.BACK_CLICK);

    }

    @Override
    protected void onResume() {
        super.onResume();
         if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
                     setNativeInResume();
                 }


//        if (MyApplication.sharedPreferencesHelper.getBannerAdshow()) {
//            x.nativeBottomAdLayout.nativeAdLayout.setVisibility(View.INVISIBLE);
//            AdShow.getInstance(activity).ShowBannerAd(x.collpasingBannerView.bannerViewContainer);
//        } else {
//            x.collpasingBannerView.bannerViewContainer.setVisibility(View.GONE);
//            AdShow.getInstance(activity).ShowNativeAd(x.nativeBottomAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_BANNER);
//        }
//        try {
//            Translate.DEFAULT.execute("Hello",Language.ENGLISH, Language.FRENCH)
//        } catch (GoogleAPIException e) {
//            throw new RuntimeException(e);
//        }
//        GoogleAPI.setHttpReferrer("https://softicetechnology.com/");
//        try {
//          String names =  Translate.DEFAULT.execute("Hello",Language.ENGLISH, Language.FRENCH);
//            Toast.makeText(activity, ""+names, Toast.LENGTH_SHORT).show();
//        } catch (GoogleAPIException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void setNativeInResume() {
        if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
            AdShow.getInstance(activity).ShowNativeAd(x.nativeBigAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_BIG);
        } else {
            x.nativeBigAdLayout.getRoot().setVisibility(View.GONE);
        }
    }


}