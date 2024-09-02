package com.ep.ai.hd.live.wallpaper.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ep.ai.hd.live.wallpaper.DataModel.ListPostResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.PopularResponse;
import com.ep.ai.hd.live.wallpaper.Repository.ImageRepository;

import okhttp3.RequestBody;

public class ImageViewModel extends ViewModel {
    public ImageRepository imageRepository;

    public ImageViewModel() {
        imageRepository = new ImageRepository();
    }

    public MutableLiveData<ListPostResponse> getPostListById(RequestBody requestBody) {
        return imageRepository.getPostListById(requestBody);
    }

    public MutableLiveData<PopularResponse> getPopularImages() {
        return imageRepository.getPopularImages();
    }

    public MutableLiveData<PopularResponse> getPremiumImages() {
        return imageRepository.getPremiumImages();
    }


}
