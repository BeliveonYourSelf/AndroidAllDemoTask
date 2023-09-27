package com.chitras.gotine.chipkavo.activity.imagesearch;

import static com.chitras.gotine.chipkavo.util.Constant.EDIT_DATA;
import static com.chitras.gotine.chipkavo.util.Constant.FROM_EDIT;
import static com.chitras.gotine.chipkavo.util.Constant.PASS_TITLE;
import static com.chitras.gotine.chipkavo.util.Constant.PASS_URL;
import static com.chitras.gotine.chipkavo.util.Constant.RESULT_EDIT_TO_SEARCH;
import static com.chitras.gotine.chipkavo.util.Utils.hideKeyboard;
import static com.chitras.gotine.chipkavo.util.Utils.isSearch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chitras.gotine.chipkavo.activity.common.BaseActivity;
import com.chitras.load.ad.AdShow;
import com.chitras.load.ad.HandleClick.HandleClick;
import com.chitras.load.ad.Qureka.QuerkaNative;
import com.chitras.load.utils.AdConstant;
import com.chitras.load.utils.AdUtils;
import com.chitras.gotine.chipkavo.MyApp;
import com.chitras.gotine.chipkavo.R;
import com.chitras.gotine.chipkavo.adapter.imagesearch.Chitras_ImageConnector;
import com.chitras.gotine.chipkavo.adapter.imagesearch.Chitras_SuggestionConnector;
import com.chitras.gotine.chipkavo.databinding.ActivitySearchBinding;
import com.chitras.gotine.chipkavo.imagesearch.Api;
import com.chitras.gotine.chipkavo.imagesearch.HistoryDataBase;
import com.chitras.gotine.chipkavo.imagesearch.RetrofitClient;
import com.chitras.gotine.chipkavo.listner.onImageClick;
import com.chitras.gotine.chipkavo.listner.onImageDataClick;
import com.chitras.gotine.chipkavo.model.imagesearch.ImageData;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chitras_SearchIamgesActivity extends BaseActivity {
    Api api;
    String TAG = Chitras_SearchIamgesActivity.class.getSimpleName();
    Activity activity;
    ActivitySearchBinding x;
    Chitras_ImageConnector chitrasImageConnector;
    Chitras_SuggestionConnector chitrasSuggestionConnector;
    ArrayList<ImageData> imageDataArrayList = new ArrayList<>();
    ArrayList<String> arrayListHistory = new ArrayList<>();
    ArrayList<String> searchArrayList = new ArrayList<>();
    String searchQuery;
    int item = 0;
    HistoryDataBase historyDataBase;
    TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        historyDataBase = new HistoryDataBase(activity);
        todo();
    }

    private void todo() {
        api = RetrofitClient.getInstance().getApi();
        x.recyclerSuggestion.setVisibility(View.GONE);
        QuerkaNative.getInstance(activity).QuerkaAd(x.toolbar.gifImageView, null, x.toolbar.txtAdTitle, null, x.toolbar.qurka);

        if (getIntent().getStringExtra("search") != null) {
            imageDataArrayList.clear();
            x.progress.setVisibility(View.VISIBLE);
            x.etSearch.setText(getIntent().getStringExtra("search"));
            search();
        }

        chitrasSuggestionConnector = new Chitras_SuggestionConnector(activity, searchArrayList, new onImageClick() {
            @Override
            public void onImageClicked(String url, String title) {
                x.imgNotFound.setVisibility(View.GONE);
                x.progress.setVisibility(View.VISIBLE);
                getData();
                x.recyclerSuggestion.setVisibility(View.GONE);
                x.etSearch.removeTextChangedListener(textWatcher);
                x.constraintLayout.setBackgroundResource(R.drawable.bg_button_tran10);
                imageDataArrayList.clear();
                hideKeyboard(activity);
                x.etSearch.setText(url);
                x.etSearch.setSelection(x.etSearch.getText().length());
                x.recyclerSuggestion.setVisibility(View.GONE);
                if (MyApp.preference.getSearchOption().isEmpty()) {
                    getImages(url);
                } else {
                    getImagesByOption(url);
                }
                if (arrayListHistory.equals(x.etSearch.getText().toString().trim())) {
                    historyDataBase.deleteHistory(x.etSearch.getText().toString().trim());
                }
                historyDataBase.insertSearch(x.etSearch.getText().toString().trim());
                searchArrayList.clear();
                chitrasSuggestionConnector.notifyDataSetChanged();

            }
        });

        chitrasImageConnector = new Chitras_ImageConnector(activity, imageDataArrayList, x.recycler, new onImageDataClick() {
            @Override
            public void imageClick(ImageData imageData) {
                String[] permissionHorn = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                Permissions.check(activity, permissionHorn, null, null, new PermissionHandler() {
                    @Override
                    public void onGranted() {

                        if (getIntent().getStringExtra(FROM_EDIT).equals(FROM_EDIT)) {
                            Intent intent = new Intent();
                            intent.putExtra(EDIT_DATA, imageData.getOriginalImageUrl());
                            setResult(RESULT_EDIT_TO_SEARCH, intent);
                            finish();
                        } else {

                            AdShow.getInstance(activity).ShowAd(new HandleClick() {
                                @Override
                                public void Show(boolean adShow) {
                                    startActivity(new Intent(activity, Chitras_ImageDetailActivity.class).putExtra(PASS_URL, imageData.getOriginalImageUrl()).putExtra("searchQuery", x.etSearch.getText().toString().trim()).putExtra(PASS_TITLE, imageData.getTitle()));
                                }
                            }, AdUtils.ClickType.MAIN_CLICK);

                        }
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        super.onDenied(context, deniedPermissions);

                    }
                });
            }
        });

        x.recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager linearLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == imageDataArrayList.size() - 1) {
                    //bottom of list!
                    item++;
                    x.progress.setVisibility(View.VISIBLE);
                    if (MyApp.preference.getSearchOption().isEmpty()) {
                        getImages(searchQuery);
                    } else {
                        getImagesByOption(searchQuery);
                    }
                }

            }
        });

        GridLayoutManager gl = new GridLayoutManager(activity, 3);
        if (AdConstant.GRID_VIEW_PER_THREE_ITEM_AD_MULTIPLY != 0) {
            gl.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position % AdConstant.GRID_VIEW_PER_THREE_ITEM_AD_MULTIPLY == 0) {
                        return 3;
                    }
                    return 1;
                }
            });
        }

        x.recycler.setLayoutManager(gl);
        x.recycler.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                chitrasImageConnector.isLoadAd(velocityY < 4000 && velocityY > -4000);
                return false;
            }
        });
        x.recycler.setAdapter(chitrasImageConnector);

        x.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x.constraintLayout.setBackgroundResource(R.drawable.bg_button_tran10);
                if (!x.etSearch.getText().toString().isEmpty()) {
                    search();
                } else {
                    Toast.makeText(activity, "Please Enter Text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        x.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                x.recyclerSuggestion.setVisibility(View.GONE);
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    x.constraintLayout.setBackgroundResource(R.drawable.bg_button_tran10);
                    search();
                    return true;
                }
                return false;
            }
        });


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Call<ResponseBody> call = api.getSearchSuggestion("en", "firefox", String.valueOf(charSequence));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        searchArrayList.clear();

                        try {
                            String responseText = null;
                            if (response.body() != null) {
                                x.recyclerSuggestion.setVisibility(View.VISIBLE);
                                x.constraintLayout.setBackgroundResource(R.drawable.bg_search_top_curve);
                                responseText = response.body().string();
                                int start = responseText.indexOf(",[");
                                int end = responseText.indexOf("]");

                                String[] searchString = responseText.substring(start + 2, end).split(",");
                                for (String s : searchString) {
                                    searchArrayList.add(s.replace("\"", "").trim());
                                }
                            } else {
                                x.constraintLayout.setBackgroundResource(R.drawable.bg_button_tran10);
                                x.recyclerSuggestion.setVisibility(View.GONE);
                            }
                            chitrasSuggestionConnector.notifyDataSetChanged();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        x.etSearch.addTextChangedListener(textWatcher);
        x.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                x.recyclerSuggestion.setVisibility(View.VISIBLE);
                x.etSearch.addTextChangedListener(textWatcher);
            }
        });
        x.recyclerSuggestion.setAdapter(chitrasSuggestionConnector);

        x.btnFitter.setOnClickListener(V -> {
            hideSuggestion();
            AdShow.getInstance(activity).ShowAd(new HandleClick() {
                @Override
                public void Show(boolean adShow) {
                    startActivity(new Intent(activity, Chitras_SettingActivity.class));
                }
            }, AdUtils.ClickType.MAIN_CLICK);

        });
        x.toolbar.textViewTitle.setText("Search Image");
        x.toolbar.imageViewBack.setOnClickListener(V -> {
            hideKeyboard(activity);
            arrayListHistory.clear();
            chitrasSuggestionConnector.notifyDataSetChanged();
            onBackPressed();
        });

        x.recyclerSuggestion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    hideSuggestion();
                }
                return true;
            }
        });
    }

    void hideSuggestion() {
        x.constraintLayout.setBackgroundResource(R.drawable.bg_button_tran10);
        x.recyclerSuggestion.setVisibility(View.GONE);
    }

    private void search() {
        x.imgNotFound.setVisibility(View.GONE);
        AdConstant.nativeAdViewHashMapBig.clear();
        getData();
        hideKeyboard(activity);
        imageDataArrayList.clear();
        x.etSearch.removeTextChangedListener(textWatcher);
        x.recyclerSuggestion.setVisibility(View.GONE);
        item = 0;


        searchQuery = isSearch(x.etSearch.getText().toString().trim());

        if (!searchQuery.isEmpty() && !searchQuery.trim().equals("")) {
            if (arrayListHistory.equals(searchQuery)) {
                historyDataBase.deleteHistory(searchQuery);
            }
            historyDataBase.insertSearch(searchQuery);
            x.progress.setVisibility(View.VISIBLE);

            if (MyApp.preference.getSearchOption().isEmpty()) {
                getImages(searchQuery);
            } else {
                getImagesByOption(searchQuery);
            }
        }
    }


    private void getImages(String searchQuery) {

        Call<ResponseBody> call = api.getImage(searchQuery, "isch", "en", String.valueOf(item), String.valueOf(item * 100), "ichunk", "0", "_id:rg_s,_pms:s,_fmt:pc", "active");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                try {
                    if (response.body() != null) {
                        Document doc;
                        doc = Jsoup.parse(response.body().string());
                        Elements elements = doc.getElementsByClass("rg_meta notranslate");
                        for (int i = 0, l = elements.size(); i < l; i++) {
                            String key = elements.get(i).text();
                            try {
                                JSONObject jsonObj = new JSONObject(key);
                                String imageWidth = jsonObj.get("ow").toString();
                                String imageHeight = jsonObj.get("oh").toString();
                                String title = jsonObj.get("st").toString();
                                String id = jsonObj.get("id").toString();
                                String rid = jsonObj.get("rid").toString();
                                String imageUrl = jsonObj.get("tu").toString();
                                String originalImageUrl = jsonObj.get("ou").toString();
                                ImageData imageData = new ImageData(imageWidth, imageHeight, title, id, rid, imageUrl, originalImageUrl);
                                imageDataArrayList.add(imageData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < imageDataArrayList.size(); i++) {
                    if (imageDataArrayList.get(i) == null) {
                        imageDataArrayList.remove(i);
                    }
                }

                if (AdConstant.GRID_VIEW_PER_THREE_ITEM_AD_MULTIPLY != 0) {
                    for (int i = 0; i < imageDataArrayList.size(); i++) {
                        if (i % AdConstant.GRID_VIEW_PER_THREE_ITEM_AD_MULTIPLY == 0) {
                            imageDataArrayList.add(i, null);
                        }
                    }
                }


                x.progress.setVisibility(View.GONE);
                chitrasImageConnector.notifyDataSetChanged();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void getImagesByOption(String searchQuery) {
        Call<ResponseBody> call = api.getImageByOption(searchQuery, "isch", "en", String.valueOf(item), String.valueOf(item * 100), "ichunk", "0", "_id:rg_s,_pms:s,_fmt:pc", "active", MyApp.preference.getSearchOption());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                try {
                    if (response.body() != null) {
                        Document doc;
                        doc = Jsoup.parse(response.body().string());
                        Elements elements = doc.getElementsByClass("rg_meta notranslate");
                        for (int i = 0, l = elements.size(); i < l; i++) {
                            String key = elements.get(i).text();
                            try {
                                JSONObject jsonObj = new JSONObject(key);
                                String imageWidth = jsonObj.get("ow").toString();
                                String imageHeight = jsonObj.get("oh").toString();
                                String title = jsonObj.get("st").toString();
                                String id = jsonObj.get("id").toString();
                                String rid = jsonObj.get("rid").toString();
                                String imageUrl = jsonObj.get("tu").toString();
                                String originalImageUrl = jsonObj.get("ou").toString();
                                ImageData imageData = new ImageData(imageWidth, imageHeight, title, id, rid, imageUrl, originalImageUrl);
                                imageDataArrayList.add(imageData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < imageDataArrayList.size(); i++) {
                    if (imageDataArrayList.get(i) == null) {
                        imageDataArrayList.remove(i);
                    }
                }

                if (AdConstant.GRID_VIEW_PER_THREE_ITEM_AD_MULTIPLY != 0) {
                    for (int i = 0; i < imageDataArrayList.size(); i++) {
                        if (i % AdConstant.GRID_VIEW_PER_THREE_ITEM_AD_MULTIPLY == 0) {
                            imageDataArrayList.add(i, null);
                        }
                    }
                }
                x.progress.setVisibility(View.GONE);
                chitrasImageConnector.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void getData() {
        arrayListHistory.clear();

        Cursor cursor = historyDataBase.GetAllHistory();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            arrayListHistory.add(name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdShow.getInstance(activity).ShowNativeAd(x.nativeSmall.nativeAdLayout, AdUtils.NativeType.NATIVE_BOTTOM_BANNER);
    }

    @Override
    public void onBackPressed() {

        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                Chitras_SearchIamgesActivity.super.onBackPressed();
            }
        }, AdUtils.ClickType.BACK_CLICK);

    }
}