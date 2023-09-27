package com.ansley.image.chatgpt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ansley.image.chatgpt.adapter.FeatureAdapter;
import com.ansley.image.chatgpt.adapter.PriceAdapter;
import com.ansley.image.chatgpt.databinding.ActivityDetailsBinding;
import com.ansley.image.chatgpt.model.GPTTool;
import com.ansley.image.chatgpt.model.Price;
import com.ansley.image.chatgpt.model.TopFeature;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChatGPTDetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding x;
    Activity activity;
    ArrayList<TopFeature> featureList = new ArrayList<>();
    ArrayList<Price> priceList = new ArrayList<>();
    int position = 0;
    String imgUrl, strLink, strName;
    FeatureAdapter featureAdapter;
    PriceAdapter priceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;

        position = getIntent().getIntExtra("position", 0);
        getJsonResponse();
        x.tvHeader.setText(strName);
        Log.d("TAG", "onCreateUrl: " + imgUrl);
        Glide.with(activity)
                .load(imgUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(x.imageView);
        x.tvLink.setText(strLink);

        featureAdapter = new FeatureAdapter(featureList, activity);
        x.rvFeature.setAdapter(featureAdapter);
        priceAdapter = new PriceAdapter(priceList, activity);
        x.rvPrice.setAdapter(priceAdapter);
        x.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        x.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(x.tvLink.getText().toString()));
                startActivity(browserIntent);
            }
        });
    }

    public void getJsonResponse() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //data
            JSONArray gptArray = obj.getJSONArray("GPTTools");
            for (int i = 0; i < gptArray.length(); i++) {
                JSONObject gptObj = gptArray.getJSONObject(i);
                if (gptObj.getInt("id") == position) {
                    imgUrl = gptObj.getString("image");
                    strLink = gptObj.getString("Link");
                    strName = gptObj.getString("name");
                    JSONArray featureArray = gptObj.getJSONArray("Top Features");
                    for (int f = 0; f < featureArray.length(); f++) {
                        JSONObject featureObj = featureArray.getJSONObject(f);
                        TopFeature topFeature = new TopFeature(featureObj.getString("f"));
                        featureList.add(topFeature);
                    }
                    JSONArray priceArray = gptObj.getJSONArray("Price");
                    for (int p = 0; p < priceArray.length(); p++) {
                        JSONObject priceObj = priceArray.getJSONObject(p);
                        Price price = new Price(priceObj.getString("p"));
                        priceList.add(price);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("ChatGPT.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
