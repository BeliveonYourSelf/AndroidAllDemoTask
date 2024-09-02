package com.ep.ai.hd.live.wallpaper.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ep.ai.hd.live.wallpaper.DataModel.CategoryResponse;
import com.ep.ai.hd.live.wallpaper.Repository.CategoryRepository;

public class CategoryViewModel extends ViewModel {
    public CategoryRepository categoryRepository;

    public CategoryViewModel() {
        this.categoryRepository = new CategoryRepository();
    }

    public MutableLiveData<CategoryResponse> getColorDetails() {
        return categoryRepository.getColorDetails();
    }
    public MutableLiveData<CategoryResponse> getCategoryDetails() {
        return categoryRepository.getCategoryData();
    }
}
