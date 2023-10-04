package com.linerock.ccvalidate.bankdetail.activity.bankdetails.bankingstuff.bankholiday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.iten.trendzad.ad.AdShow;
import com.iten.trendzad.ad.HandleClick.HandleClick;
import com.iten.trendzad.utils.AdConstant;
import com.iten.trendzad.utils.AdUtils;
import com.iten.trendzad.utils.MyApplication;
import com.linerock.ccvalidate.bankdetail.activity.BaseActivity;
import com.linerock.ccvalidate.bankdetail.adapter.BankSelectStateAdapter;
import com.linerock.ccvalidate.bankdetail.databinding.ActivityBankSelectStateBinding;
import com.linerock.ccvalidate.bankdetail.global.GlobalVariables;
import com.linerock.ccvalidate.bankdetail.model.BankHolidayModel;
import com.linerock.ccvalidate.bankdetail.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class BankSelectStateActivity extends BaseActivity {

    private static final String TAG = BankSelectStateActivity.class.getSimpleName();
    ActivityBankSelectStateBinding x;
    Activity activity;
    ArrayList<String> stateList = new ArrayList<>();
    BankSelectStateAdapter bankSelectStateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        x = ActivityBankSelectStateBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        todo();
    }



    private void todo() {

        String jsonString = AppUtils.getJsonFromAssets(activity, "statewisebankholiday.json");
        BankHolidayModel bankdata;
        Gson gson = new Gson();
        bankdata = gson.fromJson(jsonString, BankHolidayModel.class);
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONObject secoundobj = object.getJSONObject("data");
            Iterator<String> stateName = secoundobj.keys();
            while (stateName.hasNext()) {
                String key = stateName.next();
                stateList.add(key);

            }
            bankSelectStateAdapter = new BankSelectStateAdapter(activity, stateList, secoundobj);
            x.recyclerViewSelectState.setAdapter(bankSelectStateAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(MyApplication.sharedPreferencesHelper.getQurekaLite() && !AdConstant.getRandomLogo().equals(""))
        {
            for (int i = 0; i <= stateList.size(); i++) {
                if(i % AdConstant.QUREKA_VIEW_PER_AD == 0)
                {
                    stateList.add(i,"QUREKA");
                }

            }
        }
        if(AdConstant.LIST_VIEW_PER_AD != 0)
        {
            for (int i = 0; i < stateList.size(); i++) {
                if(i % AdConstant.LIST_VIEW_PER_AD == 0)
                {
                    stateList.add(i,null);
                }


            }
        }

    }

    public void onClickState(JSONArray jsonArray) throws JSONException {
        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                Bundle b = new Bundle();
                b.putString(GlobalVariables.HOLIDAY_DATA, jsonArray.toString());
                Intent intent = new Intent(activity, BankHolidayActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        }, AdUtils.ClickType.MAIN_CLICK);

    }
}