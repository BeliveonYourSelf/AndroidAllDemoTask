package topphotoeditor.bokehphotoeditor.stickersView2;

import android.app.Activity;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import topphotoeditor.bokehphotoeditor.R;


public class TextColorAdapter extends RecyclerView.Adapter<TextColorAdapter.ViewHolder> {
    ArrayList<Integer> colorList;
    int resource;
    String Selection = "";
    private Activity activity;
    Color color;

    public TextColorAdapter(ArrayList<Integer> colorList, int resource, Activity activity) {
        this.colorList = colorList;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.adapter_color_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (Selection.equals("")) {
            holder.ivBorder.setVisibility(View.GONE);
        } else {
            if (Selection.equals(String.valueOf(position)))
                holder.ivBorder.setVisibility(View.VISIBLE);
            else
                holder.ivBorder.setVisibility(View.GONE);
        }
        if (colorList.get(position) == 1) {
            holder.ivNoneColor.setVisibility(View.VISIBLE);
        } else {
            holder.ivNoneColor.setVisibility(View.GONE);
            if (resource == 1) {
                Drawable drawable = activity.getResources().getDrawable(R.drawable.stroke_rect1);
                ColorFilter filter = new LightingColorFilter(colorList.get(position), colorList.get(position));
                drawable.setColorFilter(filter);
                holder.ivColor.setImageDrawable(drawable);
            } else {
                holder.ivColor.setImageResource(colorList.get(position));
            }
        }
    }

    public void setSelection(int position) {
        this.Selection = String.valueOf(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivColor, ivBorder, ivNoneColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivColor = (ImageView) itemView.findViewById(R.id.ivColor);
            ivBorder = (ImageView) itemView.findViewById(R.id.ivBorder);
            ivNoneColor = (ImageView) itemView.findViewById(R.id.ivNoneColor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color.onColorClick(getAdapterPosition());
                }
            });
        }
    }
    public interface Color {
        void onColorClick(int position);
    }

    public void setOnColorListener(Color color) {
        this.color = color;
    }
}
