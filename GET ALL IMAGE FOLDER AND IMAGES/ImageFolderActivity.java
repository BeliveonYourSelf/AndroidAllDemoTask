package topphotoeditor.bokehphotoeditor.editor.Activity;

import static topphotoeditor.bokehphotoeditor.editor.util.Constant.IS_BOOLEAN;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iten.bokeh.ad.AdMob.AdMobBannerAd;
import com.iten.bokeh.ad.AdShow;
import com.iten.bokeh.utils.AdConstant;
import com.iten.bokeh.utils.AdUtils;
import com.iten.bokeh.utils.MyApplication;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import topphotoeditor.bokehphotoeditor.R;
import topphotoeditor.bokehphotoeditor.databinding.ActivityImageFolderBinding;
import topphotoeditor.bokehphotoeditor.editor.adapter.ImageFolderAdapter;
import topphotoeditor.bokehphotoeditor.editor.model.Model_images;
import topphotoeditor.bokehphotoeditor.editor.util.Constant;
import topphotoeditor.bokehphotoeditor.editor.util.Utils;

public class ImageFolderActivity extends BaseActivity implements View.OnClickListener {

    Activity activity;
    ImageView backImg;
    RecyclerView rv_imaginal;
    ImageFolderAdapter imageFolderAdapter;
    boolean boolean_folder;
    public static ArrayList<Model_images> all_images = new ArrayList<>();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    RxPermissions mRxPermissions;

    RelativeLayout rlBannerView;
    Boolean isBottomAdLoad = false;

    ActivityImageFolderBinding x;
    String[] permission33 = {Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO};
    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityImageFolderBinding.inflate(getLayoutInflater());
        View view = x.getRoot();
        setContentView(view);
        activity = this;

        if (Build.VERSION.SDK_INT >= 33) {
            Permissions.check(activity, permission33, null, null, new PermissionHandler() {
                @Override
                public void onGranted() {
                }
            });

        } else {
            Permissions.check(activity, permission, null, null, new PermissionHandler() {
                @Override
                public void onGranted() {

                }
            });

        }
        AdConstant.mOpenBannerAdsActivitys.put(activity.getClass().getSimpleName(), false);
        if (!AdMobBannerAd.bannerAdIds.get(0).isEmpty()) {
            AdMobBannerAd.getInstance(activity).ShowAd(activity, x.collpasingBannerView);
        } else {
            x.collpasingBannerView.setVisibility(View.GONE);
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


    protected void onResume() {
        super.onResume();

        if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            setNativeInResume();
        }
    }

    private void setNativeInResume() {
        if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
            AdShow.getInstance(activity).ShowNativeAd(x.nativeBig.nativeAdLayout, AdUtils.NativeType.NATIVE_MEDIUM);
        } else {
            x.nativeBig.nativeAdLayout.setVisibility(View.GONE);
        }
    }

    private void FindID() {
        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(this);
        rv_imaginal = findViewById(R.id.rv_imagefolder);
        rv_imaginal.setHasFixedSize(true);
        rv_imaginal.setLayoutManager(new LinearLayoutManager(activity));
        try {
            Utils.showprogressdialog(activity);
            fn_imagers();
            handler.post(() -> {
                Utils.dismissprogressdialog();
                List<Object> setCombinationData = new ArrayList<>(Constant.all_images_origi);
                imageFolderAdapter = new ImageFolderAdapter(activity, setCombinationData);
                rv_imaginal.setAdapter(imageFolderAdapter);
            });
        } catch (Exception ignored) {
        }
    }


    @Override
    public void onBackPressed() {
        IS_BOOLEAN = false;
        AdShow.getInstance(activity).ShowAd(adShow -> ImageFolderActivity.super.onBackPressed(), AdUtils.ClickType.BACK_CLICK);
    }

    public void fn_imagers() {
        all_images.clear();
        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            for (int i = 0; i < all_images.size(); i++) {
                if (all_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    String folderName = all_images.get(i).getStr_folder();
                    if (folderName != null) {
                        boolean_folder = true;
                        int_position = i;
                        break;
                    }
                } else {
                    boolean_folder = false;
                }
            }
            if (boolean_folder) {
                ArrayList<String> al_path = new ArrayList<>(all_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                all_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                String folderName = cursor.getString(column_index_folder_name);
                if (folderName != null) {
                    Model_images obj_model = new Model_images(folderName, al_path);
                    all_images.add(obj_model);
                }
            }
        }
        cursor.close();
        Constant.all_images_origi = all_images;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backImg) {
            onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && !Constant.IS_FRAM_IMAGE_REPLACE) {
            Log.e("TAG", "onActivityResult:  ImageFolder Result");
            activity.finish();
        }
    }
}