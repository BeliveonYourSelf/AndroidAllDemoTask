package com.example.expensemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.Database.ExpenseDatabase;
import com.example.expensemanager.Model.BillModel;
import com.example.expensemanager.Model.TransactionModel;

public class TransactionActivity extends AppCompatActivity {

    EditText etName, etAmount;
    Button btnAddTransaction;

    ExpenseDatabase expenseDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcation);

        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);

        btnAddTransaction = findViewById(R.id.btnAddTransaction);

        expenseDatabase = new ExpenseDatabase(TransactionActivity.this);

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etName.getText().toString().isEmpty()) {
                    if (!etAmount.getText().toString().isEmpty()) {
                        TransactionModel transactionModel = new TransactionModel();
                        transactionModel.setName(etName.getText().toString());
                        transactionModel.setAmount(etAmount.getText().toString());
                        transactionModel.setDate(System.currentTimeMillis());
                        expenseDatabase.addTransaction(transactionModel);
                    } else {
                        Toast.makeText(TransactionActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TransactionActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}