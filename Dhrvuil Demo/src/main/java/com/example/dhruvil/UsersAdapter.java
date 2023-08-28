package com.example.dhruvil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhruvil.databinding.ItemUserListBinding;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.Myclass> {
    private List<GetUser> listUsers = new ArrayList<>();

    public UsersAdapter(List<GetUser> listUsers) {

        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public UsersAdapter.Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list,parent,false);
        return new Myclass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.Myclass holder, int position) {
        holder.binding.TvName.setText("Name :-"+listUsers.get(position).getName());
        holder.binding.TvEmail.setText("Email :-"+listUsers.get(position).getEmail());
        holder.binding.TvNumber.setText("Number :-"+listUsers.get(position).getNumber());
        holder.binding.TvPassword.setText("Password :-"+listUsers.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }


    public class Myclass extends RecyclerView.ViewHolder {

        ItemUserListBinding binding;
        public Myclass(@NonNull View itemView) {
            super(itemView);

            binding = ItemUserListBinding.bind(itemView);
        }
    }
}
