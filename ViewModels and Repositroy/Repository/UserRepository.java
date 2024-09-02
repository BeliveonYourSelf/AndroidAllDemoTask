package com.ep.ai.hd.live.wallpaper.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ep.ai.hd.live.wallpaper.apiClient.ApiClients;
import com.ep.ai.hd.live.wallpaper.apiClient.RetrofitClient;
import com.eventopackage.epads.utils.LogUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";
    public ApiClients apiClients;

    public UserRepository() {
        apiClients = RetrofitClient.getRetrofit().getApi();
    }


    public MutableLiveData<JsonElement> setUserLoginCount() {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.setUserCount().enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    LogUtils.logE(TAG, "Google setUserLoginCount SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    LogUtils.logE(TAG, "onResponse: setUserLoginCount is Successfull ");
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                LogUtils.logE(TAG, "onResponse: setUserLoginCount is onFailure ");
            }
        });
        return data;

    }

    public MutableLiveData<JsonElement> upLoadReport(RequestBody vDescription, MultipartBody.Part vImage) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.uploadReport(vDescription, vImage).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    LogUtils.logE(TAG, "onResponse:upLoadReport SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        LogUtils.logE(TAG, "onResponse:upLoadReport Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                LogUtils.logE(TAG, "onFailure:upLoadReport --------------> " + t.toString());
                data.setValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<JsonElement> ReportCotent(RequestBody requestBody) {
        MutableLiveData<JsonElement> data = new MutableLiveData<>();
        apiClients.reportContent(requestBody).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Log.e(TAG, "onResponse:upLoadImageRepo SuccessFull--------->" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                } else {
                    try {
                        Log.e(TAG, "onResponse:upLoadImageRepo Failed--------->" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    data.setValue(null);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, "onFailure:uploadImage --------------> " + t.toString());
                data.setValue(null);
            }
        });

        return data;
    }



}
