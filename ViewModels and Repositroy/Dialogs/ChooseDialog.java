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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ep.ai.hd.live.wallpaper.DataModel.CategoryResponse;
import com.ep.ai.hd.live.wallpaper.DataModel.SelectionModel;
import com.ep.ai.hd.live.wallpaper.ViewModel.CategoryViewModel;
import com.ep.ai.hd.live.wallpaper.adapter.SelectionAdapter;
import com.ep.ai.hd.live.wallpaper.databinding.DialogChooseBinding;
import com.ep.ai.hd.live.wallpaper.interfaces.ChooseCallBack;
import com.ep.ai.hd.live.wallpaper.interfaces.RecyclerviewClickListner;
import com.ep.ai.hd.live.wallpaper.utils.GlobalVar;

import java.util.ArrayList;

public class ChooseDialog extends DialogFragment {
    private static final String TAG = "ChooseDialog";
    Activity activity;
    ChooseCallBack chooseCallBack;
    String sFrom;
    DialogChooseBinding binding;
    CategoryViewModel categoryViewModel;
    ArrayList<SelectionModel> arrayListName = new ArrayList<>();
    ArrayList<SelectionModel> selectedNames = new ArrayList<>();
    SelectionAdapter selectionAdapter;

    public ChooseDialog(Activity activity, String sFrom, ChooseCallBack chooseCallBack) {
        this.activity = activity;
        this.sFrom = sFrom;
        this.chooseCallBack = chooseCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().windowAnimations = 0;
        binding = DialogChooseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setAttributes(getLayoutParams(getDialog()));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        todo();
    }

    private void todo() {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        if (sFrom.equals(GlobalVar.CATEGORY)) {
            getCategoryData();
        } else {
            getColorData();
        }
        binding.tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedData();
            }
        });

    }

    private void getCategoryData() {
        categoryViewModel.getCategoryDetails().observe(getViewLifecycleOwner(), new Observer<CategoryResponse>() {
            @Override
            public void onChanged(CategoryResponse categoryResponse) {
                if (categoryResponse != null) {
                    arrayListName.clear();
                    if (categoryResponse.getData().size() > 0) {
                        for (int i = 0; i < categoryResponse.getData().size(); i++) {
                            arrayListName.add(new SelectionModel(categoryResponse.getData().get(i).get_id(), categoryResponse.getData().get(i).getvName()));
                        }
                        setAdapter();
                    }
                } else {
                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getColorData() {
        categoryViewModel.getColorDetails().observe(getViewLifecycleOwner(), new Observer<CategoryResponse>() {
            @Override
            public void onChanged(CategoryResponse categoryResponse) {
                if (categoryResponse != null) {
                    arrayListName.clear();
                    if (categoryResponse.getData().size() > 0) {
                        for (int i = 0; i < categoryResponse.getData().size(); i++) {
                            arrayListName.add(new SelectionModel(categoryResponse.getData().get(i).get_id(), categoryResponse.getData().get(i).getvName()));
                        }
                        setAdapter();
                    }
                } else {
                    Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getSelectedData() {
        selectedNames = selectionAdapter.getSelectedNames();
//        Log.e(TAG, "getSelectedData: ------->" + new GsonBuilder().setPrettyPrinting().create().toJson(selectedNames));
        if (selectedNames !=null) {
            chooseCallBack.getSelectedData(selectedNames);
        }
        getDialog().dismiss();
    }

    private void setAdapter() {
        binding.progress.setVisibility(View.GONE);
        selectionAdapter = new SelectionAdapter(activity, sFrom, arrayListName, new RecyclerviewClickListner() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
        binding.recycler.setAdapter(selectionAdapter);
    }


    private WindowManager.LayoutParams getLayoutParams(Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (dialog.getWindow() != null) {
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return layoutParams;
    }
}
