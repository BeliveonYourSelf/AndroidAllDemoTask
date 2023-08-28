package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensemanager.Database.ExpenseDatabase;
import com.example.expensemanager.Model.BillModel;

public class BillActivity extends AppCompatActivity {

    EditText etName, etAmount;
    Button btnAddBill;

    ExpenseDatabase expenseDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);

        btnAddBill = findViewById(R.id.btnAddBill);

        expenseDatabase = new ExpenseDatabase(BillActivity.this);

        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etName.getText().toString().isEmpty()) {
                    if (!etAmount.getText().toString().isEmpty()) {
                        if(expenseDatabase.isUserExists(etName.getText().toString(),etAmount.getText().toString()))
                        {
                            Toast.makeText(BillActivity.this, "User Already Exits", Toast.LENGTH_SHORT).show();
                        }else {
                            BillModel billModel = new BillModel();
                            billModel.setName(etName.getText().toString());
                            billModel.setAmount(etAmount.getText().toString());
                            expenseDatabase.addBill(billModel);
                        }

                    } else {
                        Toast.makeText(BillActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BillActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}