package com.linerock.ccvalidate.bankdetail.activity.bankdetails.bankingstuff.bankholiday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.linerock.ccvalidate.bankdetail.activity.BaseActivity;
import com.linerock.ccvalidate.bankdetail.databinding.ActivityBankHolidayDataBinding;
import com.linerock.ccvalidate.bankdetail.global.GlobalVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BankHolidayDataActivity extends BaseActivity {
    Activity activity;
    ActivityBankHolidayDataBinding x;
    String getJsonData;
    ArrayList<String> dayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        x = ActivityBankHolidayDataBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;

        getData();
        todo();

    }

    private void getData() {

            Bundle b = getIntent().getExtras();
            getJsonData = b.getString(GlobalVariables.HOLIDAY_DATA);

    }

    private void todo() {
        try {
            JSONObject object = new JSONObject(getJsonData);
            x.textViewHolidayData.setText(object.optString("day"));
            x.textViewHoliday.setText(object.optString("holiday"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}