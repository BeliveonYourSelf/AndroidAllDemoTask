package com.example.expensemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Model.BillModel;
import com.example.expensemanager.Model.OtherModel;
import com.example.expensemanager.R;

import java.util.ArrayList;
import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {

    Context context;
    List<OtherModel> otherModelList = new ArrayList<>();

    public OtherAdapter(Context context, List<OtherModel> otherModelList) {
        this.context = context;
        this.otherModelList = otherModelList;
    }

    @NonNull
    @Override
    public OtherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_other, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherAdapter.ViewHolder holder, int position) {

        holder.tvName.setText(otherModelList.get(position).getName());
        holder.tvAmount.setText(otherModelList.get(position).getAmount());
        holder.tvCategory.setText(otherModelList.get(position).getCategory());

    }

    @Override
    public int getItemCount() {
        return otherModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAmount, tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);

        }
    }

}
