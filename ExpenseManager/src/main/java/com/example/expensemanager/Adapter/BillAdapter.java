package com.example.expensemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Model.BillModel;
import com.example.expensemanager.R;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    Context context;
    List<BillModel> billModelList = new ArrayList<>();

    public BillAdapter(Context context, List<BillModel> billModelList) {
        this.context = context;
        this.billModelList = billModelList;
    }

    @NonNull
    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.ViewHolder holder, int position) {

        holder.tvName.setText(billModelList.get(position).getName());
        holder.tvAmount.setText(billModelList.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return billModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);

        }
    }

}
