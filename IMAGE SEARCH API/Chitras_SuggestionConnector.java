package com.chitras.gotine.chipkavo.adapter.imagesearch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chitras.load.utils.AdConstant;
import com.chitras.gotine.chipkavo.R;
import com.chitras.gotine.chipkavo.databinding.ItemSuggestionBinding;
import com.chitras.gotine.chipkavo.listner.onImageClick;

import java.util.ArrayList;

public class Chitras_SuggestionConnector extends RecyclerView.Adapter {

    Activity activity;
    ArrayList<String> arrayList;
    onImageClick onImageClick;

    public Chitras_SuggestionConnector(Activity activity, ArrayList<String> arrayList, onImageClick onImageClick) {
        AdConstant.nativeAdViewHashMapBig.clear();
        this.activity = activity;
        this.arrayList = arrayList;
        this.onImageClick = onImageClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemSuggestionBinding.inflate(LayoutInflater.from(activity), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.x.txSuggestion.setText(arrayList.get(position));
        if (position == arrayList.size() - 1) {
            myViewHolder.x.txSuggestion.setBackgroundResource(R.drawable.bg_suggestion_item_bottom);
        } else {
            myViewHolder.x.txSuggestion.setBackgroundResource(R.drawable.bg_suggestion_item);
        }
        myViewHolder.itemView.setOnClickListener(V -> {
            onImageClick.onImageClicked(arrayList.get(position), "");
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemSuggestionBinding x;

        public MyViewHolder(@NonNull ItemSuggestionBinding itemView) {
            super(itemView.getRoot());
            x = itemView;
        }
    }
}
