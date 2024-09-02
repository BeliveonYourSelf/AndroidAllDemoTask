package com.ep.ai.hd.live.wallpaper.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.eventopackage.epads.ad.AdShow;
import com.eventopackage.epads.utils.AdUtils;
import com.ep.ai.hd.live.wallpaper.R;
import com.ep.ai.hd.live.wallpaper.databinding.DialogRateusBinding;

import java.util.Objects;

public class RateUsDialog extends DialogFragment {
    Activity activity;
    DialogRateusBinding binding;

    public RateUsDialog(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        binding = DialogRateusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setAttributes(getLayoutParams(getDialog()));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        todo();
    }

    private void todo() {
        AdShow.getInstance(activity).ShowNativeAd(binding.nativeSmall.nativeAdLayout, AdUtils.NativeType.NATIVE_BANNER);
        binding.ivClose.setOnClickListener(v -> getDialog().dismiss());
        binding.tvSend.setOnClickListener(v -> {
            RateApp(activity);
        });


    }

    public void RateApp(final Context mContext) {
        Objects.requireNonNull(getDialog()).dismiss();
        try {

            final ReviewManager create = ReviewManagerFactory.create(mContext);
            create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                @Override
                public void onComplete(@NonNull Task<ReviewInfo> task) {
                    if (task.isSuccessful()) {
                        create.launchReviewFlow(activity, task.getResult()).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
                            }


                        });

                    }
                }


            });

        } catch (ActivityNotFoundException e) {
            Log.d("TAG", "RateApp: Falur" + e.getMessage());
            mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + mContext.getPackageName())));
            e.printStackTrace();
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
