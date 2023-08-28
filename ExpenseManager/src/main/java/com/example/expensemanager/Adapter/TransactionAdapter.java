package com.example.expensemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Model.BillModel;
import com.example.expensemanager.Model.TransactionModel;
import com.example.expensemanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context context;
    List<TransactionModel> transactionModelList = new ArrayList<>();

    public TransactionAdapter(Context context, List<TransactionModel> transactionModelList) {
        this.context = context;
        this.transactionModelList = transactionModelList;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {

        holder.tvName.setText(transactionModelList.get(position).getName());
        holder.tvAmount.setText(transactionModelList.get(position).getAmount());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(transactionModelList.get(position).getDate());

        holder.tvDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return transactionModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAmount, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);

        }
    }

}
