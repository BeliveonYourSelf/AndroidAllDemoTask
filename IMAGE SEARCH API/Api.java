package com.chitras.gotine.chipkavo.imagesearch;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    @Headers({"User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Mobile Safari/537.36", "Referer:https://www.google.com"})
    @GET("search?")
    Call<ResponseBody> getImage(
            @Query("q") String q,
            @Query("tbm") String tbm,
            @Query("hl") String hl,
            @Query("ijn") String ijn,
            @Query("start") String start,
            @Query("asearch") String asearch,
            @Query("yv") String yv,
            @Query("async") String async,
            @Query("safe") String safe
    );

    @Headers({"User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Mobile Safari/537.36", "Referer:https://www.google.com"})
    @GET("search?")
    Call<ResponseBody> getImageByOption(
            @Query("q") String q,
            @Query("tbm") String tbm,
            @Query("hl") String hl,
            @Query("ijn") String ijn,
            @Query("start") String start,
            @Query("asearch") String asearch,
            @Query("yv") String yv,
            @Query("async") String async,
            @Query("safe") String safe,
            @Query("tbs") String tbs
    );

    @GET("complete/search?")
    Call<ResponseBody> getSearchSuggestion(
            @Query("hl") String hl,
            @Query("output") String output,
            @Query("q") String q
    );

    @Headers({"User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Mobile Safari/537.36", "Referer:https://www.google.com"})
    @GET("search?")
    Call<ResponseBody> getVideo(
            @Query("q") String q,
            @Query("tbm") String tbm,
            @Query("start") String start
    );

    @Headers({"User-Agent:Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Mobile Safari/537.36", "Referer:https://www.google.com"})
    @GET("search?")
    Call<ResponseBody> getSearchByImage(@Query("tbs") String tbs,
                                        @Query("q") String q,
                                        @Query("tbm") String tbm,
                                        @Query("sa") String sa,
                                        @Query("ved") String ved);

}