package com.ep.ai.hd.live.wallpaper.Dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.eventopackage.epads.utils.Cpp;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.google.android.material.internal.FlowLayout;
import com.google.gson.Gson;
import com.ep.ai.hd.live.wallpaper.DataModel.FavouriteModel;
import com.ep.ai.hd.live.wallpaper.DataModel.ListPostResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.PopularResponse;
import com.ep.ai.hd.live.wallpaper.R;
import com.ep.ai.hd.live.wallpaper.databinding.DialogInfoBinding;
import com.ep.ai.hd.live.wallpaper.interfaces.ReportCallBack;
import com.ep.ai.hd.live.wallpaper.utils.GlobalVar;
import com.ep.ai.hd.live.wallpaper.utils.MyAppClass;

public class InfoDialog extends DialogFragment {
    Activity activity;
    DialogInfoBinding binding;
    ReportCallBack reportCallBack;
    ListPostResponse.ListData catAndColorModel;
    PopularResponse.PopularData popuLarAndPremiModel;
    FavouriteModel favouriteModel;
    String dataModel, from;
    String iImageName, iImageId;
    String imageUrl="";

    //    public InfoDialog(Activity activity, ListPostResponse.ListData infoModel, ReportCallBack reportCallBack) {
//        this.activity = activity;
//        this.reportCallBack = reportCallBack;
//        this.infoModel = infoModel;
//    }
    public InfoDialog(Activity activity, String dataModel, String from, ReportCallBack reportCallBack) {
        this.activity = activity;
        this.reportCallBack = reportCallBack;
        this.dataModel = dataModel;
        this.from = from;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        binding = DialogInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setAttributes(getLayoutParams(getDialog()));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
        todo();
    }

    private void todo() {
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        switch (from) {
            case GlobalVar.FROMFAV:
                favouriteModel = new Gson().fromJson(dataModel, FavouriteModel.class);
                iImageName = favouriteModel.getvName();
                iImageId = favouriteModel.getvImage();
                imageUrl = favouriteModel.getvImage();
                break;

            case GlobalVar.FROMPOPULAR:
            case GlobalVar.FROMPREMIUM:
                popuLarAndPremiModel = new Gson().fromJson(dataModel, PopularResponse.PopularData.class);
                iImageName = popuLarAndPremiModel.getvName();
                iImageId = popuLarAndPremiModel.getvImage();
                imageUrl = popuLarAndPremiModel.getvImage();
                break;
            case GlobalVar.FROMCOLOR:
                catAndColorModel = new Gson().fromJson(dataModel, ListPostResponse.ListData.class);
                iImageName = catAndColorModel.getvName();
                iImageId = catAndColorModel.getvImage();
                imageUrl = catAndColorModel.getvImage();
                break;
        }
        binding.tvImageName.setText(iImageName);

        binding.tvImageId.setText(iImageId.substring(iImageId.lastIndexOf("/")+1,iImageId.length()));
        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setHighlightColor(ContextCompat.getColor(activity, com.eventopackage.epads.R.color.colorSecendory))
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        Glide.with(activity)
                .load(Cpp.imageShowUrl()+imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(shimmerDrawable)
                        .error(R.drawable.iv_dialogpic))
                .into(binding.ivProfileLogo);


//        Log.e("TAG", "todo: ----->" + infoModel.getvHashTage());
//        if(!infoModel.getvHashTage().equals("") &&! infoModel.getvHashTage().isEmpty() )
//        {
//            String[] namesArray = infoModel.getvHashTage().split(",");
//            for (String keywords : namesArray) {
//                TextView textView = new TextView(activity);
//                textView.setText(keywords);
//                textView.setId(101);
//                textView.setWidth(250);
//                textView.setHeight(100);
//                textView.setGravity(Gravity.CENTER);
//
//                textView.setTextColor(ContextCompat.getColor(activity,R.color.white));
//                textView.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_dailog));
//                textView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity,R.color.white10)));
//                binding.conKeyword.addView(textView);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
//                params.setMargins(10,10,10,10);
//
//                textView.setLayoutParams(params);
//        }

        @SuppressLint("RestrictedApi") FlowLayout flowLayout = new FlowLayout(activity);
        flowLayout.setPadding(0, 20, 0, 0);
        binding.conKeyword.addView(flowLayout);

        switch (from) {
            case GlobalVar.FROMFAV:
                if (favouriteModel.getvHashTage() != null && !favouriteModel.getvHashTage().isEmpty()) {
                    String[] namesArray = favouriteModel.getvHashTage().split(",");
                    for (String keywords : namesArray) {
                        TextView textView = new TextView(activity);
                        textView.setText(keywords);
                        textView.setId(View.generateViewId()); // Generate ep IDs for each TextView
                        textView.setWidth(250);
                        textView.setHeight(100);
                        textView.setGravity(Gravity.CENTER);


                        textView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_dailog));
                        textView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.white10)));

                        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(5, 0, 5, 5);

                        textView.setLayoutParams(params);

                        flowLayout.addView(textView);
                    }

                }
                break;
            case GlobalVar.FROMPOPULAR:
            case GlobalVar.FROMPREMIUM:
                if (popuLarAndPremiModel.getvHashTage() != null && !popuLarAndPremiModel.getvHashTage().isEmpty()) {
                    String[] namesArray = popuLarAndPremiModel.getvHashTage().split(",");
                    for (String keywords : namesArray) {
                        TextView textView = new TextView(activity);
                        textView.setText(keywords);
                        textView.setId(View.generateViewId()); // Generate ep IDs for each TextView
                        textView.setWidth(250);
                        textView.setHeight(100);
                        textView.setGravity(Gravity.CENTER);


                        textView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_dailog));
                        textView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.white10)));

                        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(5, 0, 5, 5);

                        textView.setLayoutParams(params);

                        flowLayout.addView(textView);
                    }

                }
                break;
            case GlobalVar.FROMCOLOR:
                if (catAndColorModel.getvHashTage() != null && !catAndColorModel.getvHashTage().isEmpty()) {
                    String[] namesArray = catAndColorModel.getvHashTage().split(",");
                    for (String keywords : namesArray) {
                        TextView textView = new TextView(activity);
                        textView.setText(keywords);
                        textView.setId(View.generateViewId()); // Generate ep IDs for each TextView
                        textView.setWidth(250);
                        textView.setHeight(100);
                        textView.setGravity(Gravity.CENTER);


                        textView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_dailog));
                        textView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.white10)));

                        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(5, 0, 5, 5);

                        textView.setLayoutParams(params);

                        flowLayout.addView(textView);
                    }

                }
                break;
        }


    }

    private WindowManager.LayoutParams getLayoutParams(Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (dialog.getWindow() != null) {
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        return layoutParams;
    }
}
