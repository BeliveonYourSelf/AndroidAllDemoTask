package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.expensemanager.Adapter.BillAdapter;
import com.example.expensemanager.Adapter.OtherAdapter;
import com.example.expensemanager.Adapter.TransactionAdapter;
import com.example.expensemanager.Database.ExpenseDatabase;
import com.example.expensemanager.Model.BillModel;
import com.example.expensemanager.Model.OtherModel;
import com.example.expensemanager.Model.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    RecyclerView rvView;
    Spinner spinnerExpense;

    ExpenseDatabase expenseDatabase;

    List<String> expenseList = new ArrayList<>();

    List<BillModel> billModelList = new ArrayList<>();
    List<TransactionModel> transactionModelList = new ArrayList<>();
    List<OtherModel> otherModelList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        rvView = findViewById(R.id.rvView);
        spinnerExpense = findViewById(R.id.spinnerExpense);

        expenseDatabase = new ExpenseDatabase(ViewActivity.this);

        expenseList.add("Bill");
        expenseList.add("Transaction");
        expenseList.add("Other");

        ArrayAdapter adapter = new ArrayAdapter(ViewActivity.this,  android.R.layout.simple_list_item_1, expenseList);
        spinnerExpense.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewActivity.this);
        rvView.setLayoutManager(layoutManager);

        billModelList = expenseDatabase.getBill();
        transactionModelList = expenseDatabase.getTransaction();
        otherModelList = expenseDatabase.getOther();

        BillAdapter billAdapter = new BillAdapter(ViewActivity.this, billModelList);
        TransactionAdapter transactionAdapter = new TransactionAdapter(ViewActivity.this, transactionModelList);
        OtherAdapter otherAdapter = new OtherAdapter(ViewActivity.this, otherModelList);

        rvView.setAdapter(billAdapter);

        spinnerExpense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        rvView.setAdapter(billAdapter);
                        break;
                    case 1:
                        rvView.setAdapter(transactionAdapter);
                        break;
                    case 2:
                        rvView.setAdapter(otherAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}