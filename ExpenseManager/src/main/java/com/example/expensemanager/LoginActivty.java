package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanager.Database.ExpenseDatabase;
import com.example.expensemanager.Model.BillModel;
import com.example.expensemanager.databinding.ActivityLoginActivtyBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivty extends AppCompatActivity {

    Activity activity;
    ActivityLoginActivtyBinding x;
    ExpenseDatabase expenseDatabase;
    List<BillModel> userlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityLoginActivtyBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        expenseDatabase= new ExpenseDatabase(activity);
        todo();
    }

    private void todo() {
        x.btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!x.etName.getText().toString().isEmpty() && !x.etAmount.getText().toString().isEmpty())
                {
                    userlist = expenseDatabase.getBill();
                    Log.e("TAG", "Getting UserList --->: "+userlist.size());
                    for (int i = 0; i < userlist.size(); i++) {
                        Log.e("TAG", "Getting UserList  Name --->: "+userlist.get(i).getName());
                        Log.e("TAG", "Getting UserList Amount --->: "+userlist.get(i).getAmount());
                        if(userlist.get(i).getName().equals(x.etName.getText().toString()) && userlist.get(i).getAmount().equals(x.etAmount.getText().toString()))
                        {
                            startActivity(new Intent(activity, ViewActivity.class));
                        }else{
                            Toast.makeText(activity, "Wrong Password or Username", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(activity, "please fill Details First", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}