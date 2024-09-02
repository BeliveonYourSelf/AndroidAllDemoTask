package com.ep.ai.hd.live.wallpaper.apiClient;

import com.ep.ai.hd.live.wallpaper.DataModel.CategoryResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.ListPostResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.PopularResponse;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiClients {


    @POST("user/userCounter")
    Call<JsonElement> setUserCount();

    @POST("gallery/getStatic")
    Call<JsonElement> setiViewCount(@Body RequestBody requestBody);

    @POST("gallery/getStatic")
    Call<JsonElement> setDownloadCount(@Body RequestBody requestBody);

    @POST("gallery/getStatic")
    Call<JsonElement> setShareCount(@Body RequestBody requestBody);

    @GET("category/details")
    Call<CategoryResponse> getCategory();

    @GET("color/details")
    Call<CategoryResponse> getColorDetails();

    @Multipart
    @POST("support/details")
    Call<JsonElement> uploadReport(
            @Part("vDescription") RequestBody vCatId,
            @Part MultipartBody.Part part);

    @POST("report/details")
    Call<JsonElement> reportContent(@Body RequestBody requestBody);


    @Multipart
    @POST("gallery/details")
    Call<JsonElement> uploadImage2(
            @Part("vCatId") RequestBody vCatId,
            @Part("arrColorId[]") ArrayList<RequestBody> arrColorId,
            @Part("vHashTage") RequestBody vHashTag,
            @Part MultipartBody.Part part,
            @Part("vName") RequestBody vName);

    @POST("gallery/list")
    Call<ListPostResponse> getPostList(@Body RequestBody requestBody);

    @GET("gallery/popularImage")
    Call<PopularResponse> getPopular();

    @GET("gallery/premiamImage")
    Call<PopularResponse> getPremium();


}
