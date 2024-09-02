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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ep.ai.hd.live.wallpaper.R;
import com.ep.ai.hd.live.wallpaper.databinding.DialogReportcontentBinding;
import com.ep.ai.hd.live.wallpaper.interfaces.ReportCallBack;

public class ReportDialog extends DialogFragment {
    Activity activity;
    DialogReportcontentBinding binding;
    ReportCallBack reportCallBack;
    RadioButton selectedRadioButton;
    public ReportDialog(Activity activity, ReportCallBack reportCallBack) {
        this.activity = activity;
        this.reportCallBack = reportCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        binding = DialogReportcontentBinding.inflate(inflater, container, false);
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
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    selectedRadioButton  = binding.getRoot().findViewById(checkedId);
                } else {
                }
            }
        });
        binding.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                reportCallBack.onRadioButtonSelected(selectedRadioButton.getText().toString());
            }
        });

//        int selectedId = binding.radioGroup.getCheckedRadioButtonId();


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
