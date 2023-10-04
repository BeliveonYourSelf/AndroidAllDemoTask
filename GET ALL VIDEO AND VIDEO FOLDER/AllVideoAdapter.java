package com.camera.backgroundvideorecorder.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.FileUtils;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.camera.backgroundvideorecorder.Activities.VideoPlayer.AllVideoActivity;
import com.camera.backgroundvideorecorder.Models.Video;
import com.camera.backgroundvideorecorder.R;
import com.camera.backgroundvideorecorder.Utils.GlobalVariable;
import com.camera.backgroundvideorecorder.databinding.ItemVideoBinding;
import java.io.IOException;
import java.util.List;

public class AllVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private static final int NO_VIEW_TYPE = 2;
    Activity activity;
    List<Video> videoList;

    public AllVideoAdapter(Activity activity, List<Video> videoList) {
        this.activity = activity;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            try {
                Bitmap thumbnail = activity.getContentResolver().loadThumbnail(Uri.parse(videoList.get(position).getUri()), new Size(640, 550), null);
                itemViewHolder.x.imageViewVideoImage.setImageBitmap(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Glide.with(activity).load(videoList.get(position).getUri()).centerCrop().placeholder(R.drawable.ic_logo).into(itemViewHolder.x.imageViewVideoImage);
        }
        itemViewHolder.x.textViewVideoName.setText(videoList.get(position).getName());
        itemViewHolder.x.textViewTimeSize.setText(GlobalVariable.makeReadable(videoList.get(position).getDuration()) + "   " + GlobalVariable.toHumanReadable(videoList.get(position).getSize()));
        itemViewHolder.x.imageViewMore.setOnClickListener(v -> {
            Log.e("TAG", "All Video List More Clicked: " );
            ((AllVideoActivity) activity).onMoreClick(videoList.get(position), position);
        });
        itemViewHolder.itemView.setOnClickListener(v -> {
            Log.e("TAG", "All Video List Item Clicked: " );
            ((AllVideoActivity) activity).onItemClick(videoList.get(position), position);
        });



    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public int getItemViewType(int position) {
            return ITEM_VIEW_TYPE;

    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemVideoBinding x;

        public ItemViewHolder(ItemVideoBinding inflate) {
            super(inflate.getRoot());
            x = inflate;
        }
    }




}
