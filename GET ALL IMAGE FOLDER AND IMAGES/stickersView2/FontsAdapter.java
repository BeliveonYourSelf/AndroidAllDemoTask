package topphotoeditor.bokehphotoeditor.stickersView2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import topphotoeditor.bokehphotoeditor.databinding.FontAdapterBinding;


public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.ViewHolder> {
    ArrayList<Font> fontList;
    Activity activity;
    String Selection = "";
    private TextFont textFont;


    public FontsAdapter(Activity activity, ArrayList<Font> fontList) {
        this.activity = activity;
        this.fontList = fontList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FontAdapterBinding itemBinding = FontAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Font font = fontList.get(position);

        if (Selection.equals("")) {
            holder.x.ivBorder.setVisibility(View.GONE);
        } else {
            if (position == Integer.parseInt(Selection))
                holder.x.ivBorder.setVisibility(View.VISIBLE);
            else
                holder.x.ivBorder.setVisibility(View.GONE);
        }
        holder.x.fontItem.setTypeface(font.getFontType());

        holder.itemView.setOnClickListener(v -> textFont.onClick(font.getFontType(), position));
    }

    @Override
    public int getItemCount() {
        return fontList.size();
    }

    public void setSelection(int position) {
        this.Selection = String.valueOf(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FontAdapterBinding x;

        public ViewHolder(FontAdapterBinding itemView) {
            super(itemView.getRoot());
            x = itemView;
        }
    }

    public interface TextFont {
        void onClick(Typeface typeface, int pos);
    }

    public void setOnTextFontListener(TextFont textFont) {
        this.textFont = textFont;
    }


}