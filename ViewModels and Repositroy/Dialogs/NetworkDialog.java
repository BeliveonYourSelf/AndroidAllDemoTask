package com.ep.ai.hd.live.wallpaper.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ep.ai.hd.live.wallpaper.R;
import com.ep.ai.hd.live.wallpaper.databinding.NetworkDialogBinding;
import com.ep.ai.hd.live.wallpaper.interfaces.DialogCallBack;

public class NetworkDialog extends DialogFragment {

    NetworkDialogBinding binding;

    Activity activity;
    DialogCallBack dialogCallBack;

    public NetworkDialog(Activity activity, DialogCallBack dialogCallBack) {
        this.activity = activity;
        this.dialogCallBack = dialogCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = NetworkDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setAttributes(getLayoutParams(getDialog()));
        setCancelable(false);
        binding.cvTryAgain.setOnClickListener(v -> {
            getDialog().dismiss();
            dialogCallBack.onButtonClicked();
        });
        binding.ivClose.setOnClickListener(v -> getDialog().dismiss());
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
