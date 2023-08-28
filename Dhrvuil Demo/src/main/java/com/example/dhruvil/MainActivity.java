package com.example.dhruvil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dhruvil.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.TvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQLite();


            }
        });

        binding.TvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        initObjects();
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(MainActivity.this);
        inputValidation = new InputValidation(MainActivity.this);
    }


    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextPassword, binding.textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        if (databaseHelper.checkUser(binding.textInputEditTextEmail.getText().toString().trim()
                , binding.textInputEditTextPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(MainActivity.this, LoginActivity.class);
            accountsIntent.putExtra("EMAIL", binding.textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(binding.nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        binding.textInputEditTextEmail.setText(null);
        binding.textInputEditTextPassword.setText(null);
    }
}