package com.ep.ai.hd.live.wallpaper.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ep.ai.hd.live.wallpaper.Repository.UserRepository;
import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    UserRepository userRepository;

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public MutableLiveData<JsonElement> setUserLoginCount() {
        return userRepository.setUserLoginCount();
    }

    public MutableLiveData<JsonElement> ImageReport(RequestBody vDescription, MultipartBody.Part vImage) {
        return userRepository.upLoadReport(vDescription, vImage);

    }

    public MutableLiveData<JsonElement> ReportContent(RequestBody requestBody) {
        return userRepository.ReportCotent(requestBody);

    }


}
