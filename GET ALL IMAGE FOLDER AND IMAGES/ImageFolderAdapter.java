package topphotoeditor.bokehphotoeditor.editor.adapter;

import static com.iten.bokeh.ad.AdShow.getInstance;
import static com.iten.bokeh.utils.AdUtils.ClickType.MAIN_CLICK;
import static topphotoeditor.bokehphotoeditor.editor.util.Constant.IS_FRAM_IMAGE_REPLACE;
import static topphotoeditor.bokehphotoeditor.editor.util.Constant.IS_IMAGE_REPLACE;
import static topphotoeditor.bokehphotoeditor.editor.util.Constant.IS_IMAGE_REPLACE_MANUAL;
import static topphotoeditor.bokehphotoeditor.editor.util.Constant.subImagePathList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iten.bokeh.ad.HandleClick.HandleClick;

import java.util.List;

import topphotoeditor.bokehphotoeditor.databinding.AdapterImageFolderBinding;
import topphotoeditor.bokehphotoeditor.editor.Activity.ImageListActivity;
import topphotoeditor.bokehphotoeditor.editor.model.Model_images;
import topphotoeditor.bokehphotoeditor.editor.util.Constant;

public class ImageFolderAdapter extends RecyclerView.Adapter {
    Activity context;
    LayoutInflater inflater;
    List<Object> getAllImageList;

    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int Native_AD_VIEW_TYPE = 1;

    public ImageFolderAdapter(Activity context, List<Object> getAllImageList) {
        this.context = context;
        this.getAllImageList = getAllImageList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getItemViewType(int position) {
        return MENU_ITEM_VIEW_TYPE;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(AdapterImageFolderBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        Model_images model_images = (Model_images) getAllImageList.get(position);

        Glide.with(context).load("file://" + model_images.getAl_imagepath().get(0)).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                myViewHolder.x.rPlaceholder.setVisibility(View.GONE);
                return false;
            }
        }).into(myViewHolder.x.albumImg);

        myViewHolder.x.lblName.setText(model_images.getStr_folder());

        myViewHolder.x.main.setOnClickListener(v -> {
            getInstance(context).ShowAd(new HandleClick() {
                @Override
                public void Show(boolean adShow) {
                    if (IS_IMAGE_REPLACE_MANUAL) {
                        Log.e("TAG", " REPLACE IMAGE FROM---> MANUAL CUT  ");
//                        IS_IMAGE_REPLACE_MANUAL = false;
                        subImagePathList = model_images.getAl_imagepath();
                        context.startActivityForResult(new Intent(context, ImageListActivity.class), 100);
                        context.setResult(Activity.RESULT_OK);
                        context.finish();
                    } else if (IS_FRAM_IMAGE_REPLACE) {
                        Log.e("TAG", " IMAGE REPLACE  FROM FRAME---> MAIN MENU   ");
//                        IS_IMAGE_REPLACE_MANUAL = false;
                        subImagePathList = model_images.getAl_imagepath();
//                        context.startActivity(new Intent(context,ImageListActivity.class));
                        context.startActivityForResult(new Intent(context, ImageListActivity.class), 100);
//                        context.setResult(Activity.RESULT_OK);
//                        context.finish();
                    }else if (IS_IMAGE_REPLACE) {
                        Log.e("TAG", " IMAGE REPLACE  FROM BG CHANGER---> MAIN MENU   ");
//                        IS_IMAGE_REPLACE_MANUAL = false;
                        subImagePathList = model_images.getAl_imagepath();
                        context.startActivity(new Intent(context,ImageListActivity.class));
//                        context.startActivityForResult(new Intent(context, ImageListActivity.class), 100);
//                        context.setResult(Activity.RESULT_OK);
//                        context.finish();
                    }else if (Constant.IS_TEMPLATE_IMAGE_REPLACE) {
                        Log.e("TAG", " IMAGE REPLACE  FROM TEMPLATED---> MAIN MENU   ");
//                        IS_IMAGE_REPLACE_MANUAL = false;
                        subImagePathList = model_images.getAl_imagepath();
                        context.startActivity(new Intent(context,ImageListActivity.class));
//                        context.startActivityForResult(new Intent(context, ImageListActivity.class), 100);
//                        context.setResult(Activity.RESULT_OK);
//                        context.finish();
                    }else if (Constant.IS_IMAGE_BLUR_REPLACE) {
                        Log.e("TAG", " IMAGE REPLACE  BLUR ---> MAIN MENU   ");
//                        IS_IMAGE_REPLACE_MANUAL = false;
                        subImagePathList = model_images.getAl_imagepath();
                        context.startActivity(new Intent(context, ImageListActivity.class));
                        context.startActivityForResult(new Intent(context, ImageListActivity.class), 100);
                        context.setResult(Activity.RESULT_OK);
                        context.finish();
                    } else {
                        Log.e("TAG", " NOT FROM MANUAL CUT OR REPLACE ");
                        subImagePathList = model_images.getAl_imagepath();
                        context.startActivity(new Intent(context, ImageListActivity.class));
                    }
//            context.startActivityForResult(new Intent(context, ImageListActivity.class), 100);
//            context.setResult(Activity.RESULT_OK);
//            context.finish();
                }
            }, MAIN_CLICK);
//            context.finish();
        });

    }


    @Override
    public int getItemCount() {
        return getAllImageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterImageFolderBinding x;

        public MyViewHolder(@NonNull AdapterImageFolderBinding itemView) {
            super(itemView.getRoot());
            x = itemView;
        }
    }
}
