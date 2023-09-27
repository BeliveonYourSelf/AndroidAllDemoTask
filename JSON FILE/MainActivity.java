package com.ansley.image.chatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ansley.image.chatgpt.adapter.HomeAdapter;
import com.ansley.image.chatgpt.databinding.ActivityMainBinding;
import com.ansley.image.chatgpt.interfaces.HomeInterface;
import com.ansley.image.chatgpt.model.GPTTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements HomeInterface {

    ActivityMainBinding x;
    Activity activity;
    ArrayList<GPTTool> arrayList = new ArrayList<>();

    HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;

        getJsonResponse();

//        try {
//            GetJsonFiles();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        homeAdapter = new HomeAdapter(arrayList, activity, this);
        x.recyclerview.setAdapter(homeAdapter);
    }

//    private void GetJsonFiles() throws IOException, JSONException {
//        String JsonFile = null;
//        InputStream inputStream = getAssets().open("ChatGPT.json");
//        int size = inputStream.available();
//        byte[] buffer = new byte[size];
//        inputStream.read(buffer);
//        inputStream.close();
//
//        JsonFile= new String(buffer, StandardCharsets.UTF_8);
//
//        JSONObject jsonObject= new JSONObject(JsonFile);
//        JSONArray jsonArray= jsonObject.getJSONArray("GPTTools");
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//
//            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//            GPTTool gptTool = new GPTTool(jsonObject1.getInt("id"),jsonObject1.getString("name"),jsonObject1.getString("image"),jsonObject1.getString("Link"),jsonObject1.getString("detail"));
//            arrayList.add(gptTool);
//        }
//    }


    public void getJsonResponse() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //data
            JSONArray gptArray = obj.getJSONArray("GPTTools");
            for (int i = 0; i < gptArray.length(); i++) {
                JSONObject gptObj = gptArray.getJSONObject(i);
                GPTTool gptTool = new GPTTool(gptObj.getInt("id"), gptObj.getString("name"), gptObj.getString("image"), gptObj.getString("Link"), gptObj.getString("detail"));
                Log.d("TAG", "getJsonResponse: " + gptObj.getInt("id") + ", name: " + gptObj.getString("name") + ", image: " + gptObj.getString("image") + ", Link: " + gptObj.getString("Link") + ", detail:" + gptObj.getString("detail"));
                arrayList.add(gptTool);
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

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(activity, ChatGPTDetailsActivity.class).putExtra("position", position));
    }
}