package com.camera.backgroundvideorecorder.Activities;

import static com.camera.load.ad.AdShow.getInstance;
import static com.camera.load.utils.AdUtils.ClickType.BACK_CLICK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.camera.backgroundvideorecorder.Activities.Comman.BaseActivity;
import com.camera.backgroundvideorecorder.Prefs.SharedPrefs;
import com.camera.backgroundvideorecorder.R;
import com.camera.backgroundvideorecorder.Utils.AlarmReceiver;
import com.camera.backgroundvideorecorder.databinding.ActivityAlarmPageBinding;
import com.camera.load.ad.HandleClick.HandleClick;
import com.camera.load.utils.AdConstant;
import com.camera.load.utils.AdUtils;
import com.camera.load.utils.MyApplication;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AlarmPage extends BaseActivity {
    private AlarmManager alarmManager;
    private TextView alrmDate;
    private TextView alrmTime;
    AppCompatButton btnCancelAlrm;
    AppCompatButton btnSaveAlarm;
    private Calendar calendar;
    private MaterialDatePicker datePicker;
    ConstraintLayout image_AlrmDate;
    ConstraintLayout image_AlrmTime;
    private PendingIntent pendingIntent;
    private SharedPrefs prefs;
    String str_currentDate;
    String str_currentTime;
    private MaterialTimePicker timePicker;
    Activity activity;
    long time;
    boolean isOnPause = false;

    CountDownTimer countDownTimer;
    ActivityAlarmPageBinding x;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        x = ActivityAlarmPageBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        setTitle("Set Alarm");
        if (!MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            ShowAd();
        }
        countDownTimer = new CountDownTimer(MyApplication.sharedPreferencesHelper.getRefreshCount(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                ShowAd();
                start();
            }
        };
        if (MyApplication.sharedPreferencesHelper.getRefreshCount() != 0) {
            countDownTimer.start();
        }
//        x.ivBackarrow.setOnClickListener(v -> onBackPressed());
        this.alrmTime = findViewById(R.id.id_txtAlarmTime);
        this.alrmDate = findViewById(R.id.id_txtAlarmDate);
        this.prefs = new SharedPrefs(this);
        this.btnSaveAlarm = findViewById(R.id.id_btnSaveAlarm);
        this.image_AlrmDate = findViewById(R.id.id_imgAlarmDate);
        this.image_AlrmTime = findViewById(R.id.id_imgSelectTime);
        this.btnCancelAlrm = findViewById(R.id.id_btnCancelAlarm);
        this.str_currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        this.str_currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        if (this.prefs.getStr("alarm_date").equals("DNF")) {
            this.alrmDate.setText(this.str_currentDate);
        } else {
            this.alrmDate.setText(this.prefs.getStr("alarm_date"));
        }
        if (this.prefs.getStr("alarm_time").equals("DNF")) {
            this.alrmTime.setText(this.str_currentTime);
        } else {
            this.alrmTime.setText(this.prefs.getStr("alarm_time"));
        }
        createNotificationChannel();
        this.image_AlrmTime.setOnClickListener(view -> showTimePicker());
        this.image_AlrmDate.setOnClickListener(view -> showDatePicker());
        this.btnCancelAlrm.setOnClickListener(view -> cancelAlarm());
        this.btnSaveAlarm.setOnClickListener(view -> setAlarm());
    }


    private void cancelAlarm() {
        this.pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 201326592);
        if (this.alarmManager == null) {
            this.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        this.alarmManager.cancel(this.pendingIntent);
        Toast.makeText(this, "Alarm Cancelled!", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        this.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), PendingIntent.FLAG_IMMUTABLE);

        if (calendar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Log.e("TAG", " M VERSION Alarm Saved Successfully!: ");

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Log.e("TAG", " KITKAT Alarm Saved Successfully!: ");

            } else {
                Log.e("TAG", "Alarm Saved Successfully!: ");

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarm Saved Successfully!", Toast.LENGTH_SHORT).show();
            return;
        }
//        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this,0,new Intent(this,AlarmReceiver.class),0);

        Toast.makeText(this, "Select the time first", Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {
        MaterialTimePicker build = new MaterialTimePicker.Builder().setTheme(R.style.theme_App_TimePickers).setTimeFormat(TimeFormat.CLOCK_12H).setHour(12).setMinute(0).setTitleText("Select Recording Alarm Time").build();
        this.timePicker = build;
        build.show(getSupportFragmentManager(), "alarmid");
        this.timePicker.addOnPositiveButtonClickListener(view -> {
            Log.e("TAG", "getHour: " + timePicker.getHour());
            Log.e("TAG", "getMinute: " + timePicker.getMinute());
            String str;
            time = System.currentTimeMillis();
            calendar = Calendar.getInstance();
            if (AlarmPage.this.timePicker.getHour() > 12) {
                if (timePicker.getMinute() >= 9) {
                    str = (AlarmPage.this.timePicker.getHour() - 12) + " : " + AlarmPage.this.timePicker.getMinute() + " PM";
                } else {
                    str = (AlarmPage.this.timePicker.getHour() - 12) + " : " + "0" + AlarmPage.this.timePicker.getMinute() + " PM";

                }
            } else {
                if (timePicker.getHour() == 0) {
                    if (timePicker.getMinute() <= 9) {
                        str = timePicker.getHour() + " : 0" + timePicker.getMinute() + " AM";
                    } else {

                        str = timePicker.getHour() + " : " + timePicker.getMinute() + " AM";
                    }
                } else {
                    if (timePicker.getMinute() <= 9) {
                        str = AlarmPage.this.timePicker.getHour() + " : 0" + AlarmPage.this.timePicker.getMinute() + " AM";
                    } else {

                        str = AlarmPage.this.timePicker.getHour() + " : " + AlarmPage.this.timePicker.getMinute() + " AM";
                    }

                }
            }
            alrmTime.setText(str);
            prefs.setStr("alarm_time", str);

            Log.e("TAG", "calendar.getTimeInMillis() <= calendar.currentTimeMillis() " + calendar.getTimeInMillis());
            Log.e("TAG", "System.getTimeInMillis() <= System.currentTimeMillis() " + System.currentTimeMillis());

            calendar.set(HOUR_OF_DAY, timePicker.getHour());
            calendar.set(MINUTE, timePicker.getMinute());
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                Log.e("TAG", "calendar Time is Less Than System Time <= calendar.currentTimeMillis() IF " + calendar.getTimeInMillis());
                Log.e("TAG", "calendar Time is Less Than System Time <= System.currentTimeMillis() IF " + System.currentTimeMillis());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

        });
    }

    private void showDatePicker() {
//        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
//        builder.setTheme(R.style.MyDatePickerStyle);
//        MaterialDatePicker<Long> datePicker = builder.build();
//        datePicker.show(getSupportFragmentManager(), "datePicker");
        MaterialDatePicker<Long> build = MaterialDatePicker.Builder.datePicker().setTheme(R.style.MaterialCalendarTheme).setTitleText("Select Date").build();
        this.datePicker = build;

        build.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public final void onPositiveButtonClick(Object obj) {
                AlarmPage.this.m114x49efeae2(obj);
            }
        });
        this.datePicker.show(getSupportFragmentManager(), "Tag");
    }


    public void m114x49efeae2(Object obj) {
        String str = "" + this.datePicker.getHeaderText();
        this.alrmDate.setText(str);
        this.prefs.setStr("alarm_date", str);
        String months = prefs.getStr("alarm_date");
        String[] spliteddate = months.split(" ");
        Log.e("TAG", "MONTH : " + spliteddate[1]);
        Log.e("TAG", "DATE : " + spliteddate[0]);
        Log.e("TAG", "YEAR : " + spliteddate[2]);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("alarmid", "AlarmReminder", 4);
            notificationChannel.setDescription("Channel for Alarm manager");
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause = true;
        countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.prefs.setStr("alarm_date", "DNF");
        Log.e("TAG", "onResume: ");
        if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            ShowAd();
        }
        if (isOnPause) {
            isOnPause = false;
            if (MyApplication.sharedPreferencesHelper.getRefreshCount() != 0) {
                countDownTimer.start();
            }
        }


    }

    private void ShowAd() {
        getInstance(activity).ShowNativeAd(findViewById(R.id.nativeSmall).findViewById(R.id.native_ad_layout), AdUtils.NativeType.NATIVE_BANNER);
        if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
            getInstance(activity).ShowNativeAd(findViewById(R.id.nativeBig).findViewById(R.id.native_ad_layout), AdUtils.NativeType.NATIVE_BIG);
        } else {
            findViewById(R.id.nativeBig).findViewById(R.id.native_ad_layout).setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                AlarmPage.super.onBackPressed();
            }
        }, BACK_CLICK);
    }
}
