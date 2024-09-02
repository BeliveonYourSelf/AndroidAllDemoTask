package com.ep.ai.hd.live.wallpaper.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ep.ai.hd.live.wallpaper.databinding.AdLoadingBinding;

public class UpLoading extends Dialog {
    Activity activity;
    AdLoadingBinding binding;
    String tittle;

    public UpLoading(Activity activity, String tittle) {
        super(activity);
        this.activity = activity;
        this.tittle = tittle;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = AdLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textView.setText(""+tittle);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);


    }

}