package topphotoeditor.bokehphotoeditor.editor.Activity;

import static topphotoeditor.bokehphotoeditor.editor.util.Constant.IS_BOOLEAN;
import static topphotoeditor.bokehphotoeditor.editor.util.Constant.IS_IMAGE_REPLACE;
import static topphotoeditor.bokehphotoeditor.editor.util.MyApp.accountProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iten.bokeh.ad.AdMob.AdMobBannerAd;
import com.iten.bokeh.ad.AdShow;
import com.iten.bokeh.utils.AdConstant;
import com.iten.bokeh.utils.AdUtils;
import com.iten.bokeh.utils.MyApplication;

import java.util.ArrayList;

import topphotoeditor.bokehphotoeditor.R;
import topphotoeditor.bokehphotoeditor.databinding.ActivityImageListBinding;
import topphotoeditor.bokehphotoeditor.editor.adapter.PhotoListAdapter;
import topphotoeditor.bokehphotoeditor.editor.util.Constant;

public class ImageListActivity extends BaseActivity implements View.OnClickListener {
    Activity activity;
    ImageView backImg;
    RecyclerView PhotoListRecyclerView;
    PhotoListAdapter photoAdapter;
    public static ArrayList<String> all_images_ads = new ArrayList<>();
    public static ArrayList<String> all_images_ads1 = new ArrayList<>();

    Boolean isBottomAdLoad = false;

    ActivityImageListBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        activity = this;
        all_images_ads1 = Constant.subImagePathList;
        AdConstant.mOpenBannerAdsActivitys.put(activity.getClass().getSimpleName(),false);
        if (!AdMobBannerAd.bannerAdIds.get(0).isEmpty()) {
            AdShow.getInstance(activity).ShowBannerAd(binding.collpasingBannerView);
        } else {
            binding.collpasingBannerView.setVisibility(View.GONE);

        }
        if (!MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            setNativeInResume();
        }
        FindID();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isBottomAdLoad = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            setNativeInResume();
        }
    }

    private void setNativeInResume() {
        if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
            AdShow.getInstance(activity).ShowNativeAd(binding.nativeBig.nativeAdLayout, AdUtils.NativeType.NATIVE_BIG);
        } else {
            binding.nativeBig.nativeAdLayout.setVisibility(View.GONE);
        }
    }

    private void FindID() {
        backImg = findViewById(R.id.backImg);
        PhotoListRecyclerView = findViewById(R.id.PhotoListRecyclerView);
        backImg.setOnClickListener(this);
        PhotoListRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        all_images_ads.clear();
        all_images_ads.addAll(all_images_ads1);
        photoAdapter = new PhotoListAdapter(this, all_images_ads);
        PhotoListRecyclerView.setAdapter(photoAdapter);
        if (!accountProvider.getBooleanVal(Constant.PREF_VISITE_GALLARY_POP)) {
            ShowSelectPicDialog();
        }


    }

    @Override
    public void onBackPressed() {
        IS_BOOLEAN = false;
        AdShow.getInstance(activity).ShowAd(adShow -> ImageListActivity.super.onBackPressed(), AdUtils.ClickType.BACK_CLICK);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backImg) {
            onBackPressed();
        }
    }

    public void ShowSelectPicDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(RESULT_FIRST_USER);
        dialog.setContentView(R.layout.dialog_select_pic);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        TextView btn_positive = dialog.findViewById(R.id.btn_positive);
        ImageView iv_close = dialog.findViewById(R.id.iv_close);

        dialog.setOnCancelListener(dialogInterface -> accountProvider.setVal(Constant.PREF_VISITE_GALLARY_POP, true));

        btn_positive.setOnClickListener(view -> dialog.cancel());

        iv_close.setOnClickListener(view -> dialog.cancel());
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Log.e("TAG", "onActivityResult:  ImageList Result");
            this.finish();
        }
        if (requestCode == 200) {
           activity.finish();
        }
    }
}