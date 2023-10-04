package com.linerock.ccvalidate.bankdetail.activity.bankdetails.bankingstuff.bankholiday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.iten.trendzad.utils.AdConstant;
import com.iten.trendzad.utils.MyApplication;
import com.linerock.ccvalidate.bankdetail.Interface.OnClickInterface;
import com.linerock.ccvalidate.bankdetail.activity.BaseActivity;
import com.linerock.ccvalidate.bankdetail.adapter.BankHolidayAdapter;
import com.linerock.ccvalidate.bankdetail.databinding.ActivityBankHolidayBinding;
import com.linerock.ccvalidate.bankdetail.global.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BankHolidayActivity extends BaseActivity {
    Activity activity;
    ActivityBankHolidayBinding x;
    BankHolidayAdapter bankHolidayAdapter;
    String getJsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        x = ActivityBankHolidayBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        todo();
    }

    private void todo() {
        getJsonData();
        setData();

    }


    private void getJsonData() {
        Bundle b = getIntent().getExtras();
        getJsonData = b.getString(GlobalVariables.HOLIDAY_DATA);
    }

    private void setData() {

        try {
            JSONArray jsonArray = new JSONArray(getJsonData);
            if(MyApplication.sharedPreferencesHelper.getQurekaLite() && !AdConstant.getRandomLogo().trim().equals(""))
            {
                for (int i = 0; i < jsonArray.length(); i++) {
                        if(i % AdConstant.QUREKA_VIEW_PER_AD == 0)
                        {
                            jsonArray.put(i,"QUREKA");
                        }

            }
            }
            if(AdConstant.LIST_VIEW_PER_AD != 0)
            {
                for (int i = 0; i < jsonArray.length(); i++) {
                    if(i % AdConstant.LIST_VIEW_PER_AD == 0)
                    {
                        jsonArray.put(i,null);
                    }

                }
            }
            bankHolidayAdapter = new BankHolidayAdapter(activity, jsonArray, new OnClickInterface() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(activity, BankHolidayDataActivity.class);
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(position);
                        Bundle b = new Bundle();
                        b.putString(GlobalVariables.HOLIDAY_DATA, jsonObject.toString());
                        intent.putExtras(b);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);


                }
            });
            x.recyclerViewHolidayData.setAdapter(bankHolidayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }



}