package topphotoeditor.bokehphotoeditor.editor.adapter;

import static topphotoeditor.bokehphotoeditor.editor.util.Constant.FROM;
import static topphotoeditor.bokehphotoeditor.editor.util.Constant.IS_FRAM_IMAGE_REPLACE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iten.bokeh.ad.AdShow;
import com.iten.bokeh.utils.AdUtils;

import java.io.File;
import java.util.ArrayList;

import topphotoeditor.bokehphotoeditor.BuildConfig;
import topphotoeditor.bokehphotoeditor.R;
import topphotoeditor.bokehphotoeditor.editor.Activity.CartoonActivity;
import topphotoeditor.bokehphotoeditor.editor.Activity.FrameActivity;
import topphotoeditor.bokehphotoeditor.editor.Activity.StillCutPhotoActivity;
import topphotoeditor.bokehphotoeditor.editor.util.Constant;
import topphotoeditor.bokehphotoeditor.editor.util.Utils;

public class PhotoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity activity;
    ArrayList<String> al_menu;
    LayoutInflater inflater;  //for inflate layout items
    Uri selectedImage;
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    public PhotoListAdapter(Activity activity, ArrayList<String> al_menu) {
        this.activity = activity;
        this.al_menu = al_menu;
        inflater = (LayoutInflater.from(activity));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MENU_ITEM_VIEW_TYPE) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_gallery_photo, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int viewType = getItemViewType(position);
        if (viewType == MENU_ITEM_VIEW_TYPE) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            Glide.with(activity).load("file://" + al_menu.get(position)).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        myViewHolder.r_placeholder.setVisibility(View.GONE);
                    return false;
                }
            }).into(myViewHolder.iv_photo);
            myViewHolder.iv_photo.setOnClickListener(v -> AdShow.getInstance(activity).ShowAd(adShow -> {
                if (FROM.equals(Constant.Cartoon)) {
                    Log.e("TAG", "onBindViewHolder-->PhotoListAdapter---> Cartoon");
                    activity.startActivity(new Intent(activity, CartoonActivity.class).putExtra("cartoonBmp", al_menu.get(position)));
                } else if (FROM.equals(Constant.Auto_Blur_Click)) {
                    Log.e("TAG", "on Photo Click of Blur:----> " + al_menu.get(position));
                    selectedImage = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", new File(al_menu.get(position)));
                    if (selectedImage != null) {
                        Log.e("TAG", "on Photo Click of Blur selectedImage:---->  " + selectedImage);
                        Log.e("TAG", "onBindViewHolder-->PhotoListAdapter---> Auto_Blur_Click");
                        activity.startActivityForResult(new Intent(activity, StillCutPhotoActivity.class)
                                .putExtra(StillCutPhotoActivity.IMG, selectedImage)
                                .putExtra("isBlur", true)
                                .putExtra("string", "strBlur"), 100);
                        activity.setResult(Activity.RESULT_OK);
                        activity.finish();
                    } else {
                        Utils.showToast(activity, "Please Select Photo Again");
                    }

                } else if (FROM.equals(Constant.Photo_frame)) {
                    if (IS_FRAM_IMAGE_REPLACE) {
                        Log.e("TAG", "onBindViewHolder-->PhotoListAdapter---> IS_FRAM_IMAGE_REPLACE----> Photo_frame Click");
                        Constant.BLUR_IMAGE_PATH = al_menu.get(position);
                        IS_FRAM_IMAGE_REPLACE = false;
                        activity.finish();
                    } else {
                        Log.e("TAG", "onBindViewHolder-->PhotoListAdapter---> NO IS_IMAGE_REPLACE--->  Photo_frame Click");
                        activity.startActivityForResult(new Intent(activity, FrameActivity.class)
                                .putExtra(StillCutPhotoActivity.IMG, al_menu.get(position)), 100);
                        activity.setResult(Activity.RESULT_OK);
                        activity.finish();
                    }
                } else {
                    if (selectedImage != null) {
                        selectedImage = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", new File(al_menu.get(position)));
                        Log.e("TAG", "onBindViewHolder-->PhotoListAdapter");
                        activity.startActivityForResult(new Intent(activity, StillCutPhotoActivity.class)
//                            .putExtra(StillCutPhotoActivity.IMG, al_menu.get(position))
                                        .putExtra(StillCutPhotoActivity.IMG, selectedImage)
                                        .putExtra("isEffect", true)
                                        .putExtra("string", "strEffect")
                                , 100);
                        activity.setResult(Activity.RESULT_OK);
                        activity.finish();
                    } else {
                        Utils.showToast(activity, "Please Select Photo Again");
                    }

                }

            }, AdUtils.ClickType.MAIN_CLICK));
        }
    }


    @Override
    public int getItemCount() {
        return al_menu.size();
    }

    @Override
    public int getItemViewType(int position) {
        return MENU_ITEM_VIEW_TYPE;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.imageView);
        }
    }

}

