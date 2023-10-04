package com.camera.backgroundvideorecorder.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.camera.backgroundvideorecorder.Activities.VideoPlayer.AllVideoActivity;
import com.camera.backgroundvideorecorder.Models.VideoFolder;
import com.camera.backgroundvideorecorder.Utils.GlobalVariable;
import com.camera.backgroundvideorecorder.databinding.ItemVideoFolderBinding;
import com.camera.load.ad.AdShow;
import com.camera.load.ad.HandleClick.HandleClick;
import com.camera.load.utils.AdConstant;
import com.camera.load.utils.AdUtils;

import java.util.ArrayList;
import java.util.List;

public class AllVideoFolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private static final int NO_VIEW_TYPE = 2;
    Activity activity;
    List<VideoFolder> videoFolderList;

    public AllVideoFolderAdapter(Activity activity, ArrayList<VideoFolder> videoFolderList) {
        this.activity = activity;
        this.videoFolderList = videoFolderList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(ItemVideoFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.x.imageViewMore.setVisibility(View.GONE);
        itemViewHolder.x.textViewFolderName.setText(videoFolderList.get(position).getFoldername());
        try {
            itemViewHolder.x.textViewVideoFolderDetails.setText(videoFolderList.get(position).getStringArrayList().size() + " Videos" + "   " + GlobalVariable.toHumanReadable(Long.parseLong(videoFolderList.get(position).getFolderSize())));

        } catch (NumberFormatException e) {

        }
        holder.itemView.setOnClickListener(v -> {
            AdShow.getInstance(activity).ShowAd(new HandleClick() {
                @Override
                public void Show(boolean adShow) {
                    Intent intent = new Intent(activity, AllVideoActivity.class);
                    intent.putExtra(GlobalVariable.VIDEO_NAME, videoFolderList.get(position).getFoldername());
                    intent.putExtra(GlobalVariable.BUCKET_ID, videoFolderList.get(position).getBucketId());
                    activity.startActivity(intent);
                }
            }, AdUtils.ClickType.MAIN_CLICK);

        });
    }

    @Override
    public int getItemCount() {
        return videoFolderList.size();
    }


    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemVideoFolderBinding x;
        public ItemViewHolder(ItemVideoFolderBinding inflate) {
            super(inflate.getRoot());
            x = inflate;
        }
    }

}
