package com.example.expensemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.Database.ExpenseDatabase;
import com.example.expensemanager.Model.OtherModel;
import com.example.expensemanager.Model.TransactionModel;

public class OtherActivity extends AppCompatActivity {

    EditText etName, etAmount, etCategory;
    Button btnOther;

    ExpenseDatabase expenseDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);

        btnOther = findViewById(R.id.btnOther);

        expenseDatabase = new ExpenseDatabase(OtherActivity.this);

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etName.getText().toString().isEmpty()) {
                    if (!etAmount.getText().toString().isEmpty()) {
                        if (!etCategory.getText().toString().isEmpty()) {
                            OtherModel otherModel = new OtherModel();
                            otherModel.setName(etName.getText().toString());
                            otherModel.setAmount(etAmount.getText().toString());
                            otherModel.setCategory(etCategory.getText().toString());
                            expenseDatabase.addOther(otherModel);
                        } else {
                            Toast.makeText(OtherActivity.this, "Enter Category", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OtherActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtherActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}