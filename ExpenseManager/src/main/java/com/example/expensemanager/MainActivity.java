package com.example.expensemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.Database.ExpenseDatabase;
import com.example.expensemanager.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnView, btnLogin, btnAssetImage;
    ArrayList<Integer> arrayListImage = new ArrayList<>();
    ArrayList<Integer> retiveList = new ArrayList<>();
    ExpenseDatabase expenseDatabase;
    ActivityMainBinding x;
    private boolean isFirstime = false;
    private int counter = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());

        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnLogin = findViewById(R.id.btnLogin);
        btnAssetImage = findViewById(R.id.btnArray);
        expenseDatabase = new ExpenseDatabase(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivty.class);
                startActivity(intent);
            }
        });
        String[] paths = new String[0];
        try {
            paths = getAssets().list("frames");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < paths.length; i++) {

            Log.e("TAG", "paths --->: " + i + "--->" + paths[i]);
        }
        arrayListImage.add(R.drawable.bg_1);
        arrayListImage.add(R.drawable.bg_2);
        arrayListImage.add(R.drawable.bg_3);
        arrayListImage.add(R.drawable.bg_4);
        arrayListImage.add(R.drawable.bg_5);
        arrayListImage.add(R.drawable.bg_6);
        arrayListImage.add(R.drawable.bg_7);
        arrayListImage.add(R.drawable.bg_8);
        arrayListImage.add(R.drawable.bg_9);
        arrayListImage.add(R.drawable.bg_10);


        btnAssetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFirstime) {
                    isFirstime = true;
                    expenseDatabase.insertArrayList(arrayListImage);
                }
                retiveList = expenseDatabase.getArrayList();

                for (int i = 0; i < retiveList.size(); i++) {

                    Log.e("TAG", "retrive list --->: " + i + "--->" + retiveList.get(i));
                }
                x.ivFrames.setImageResource(retiveList.get(counter));
                if (counter == retiveList.size() - 1) {
                    counter = 0;
                }else {

                counter++;
                }
            }
        });
    }
}