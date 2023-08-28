package com.example.dhruvil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.dhruvil.databinding.ActivityRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private GetUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_register);


        initObjects();

        binding.TvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToSQLite();
            }
        });

    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextName, binding.textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(binding.textInputEditTextPassword, binding.textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(binding.textInputEditTextPassword, binding.textInputEditTextConfirmPassword,
                binding.textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(binding.textInputEditTextEmail.getText().toString().trim())) {
            user.setName(binding.textInputEditTextName.getText().toString().trim());
            user.setEmail(binding.textInputEditTextEmail.getText().toString().trim());
            user.setPassword(binding.textInputEditTextPassword.getText().toString().trim());
            databaseHelper.addUser(user);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(binding.nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(binding.nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }

    }

    private void emptyInputEditText() {
        binding.textInputEditTextName.setText(null);
        binding.textInputEditTextEmail.setText(null);
        binding.textInputEditTextPassword.setText(null);
        binding.textInputEditTextConfirmPassword.setText(null);
    }

    private void initObjects() {
        inputValidation = new InputValidation(RegisterActivity.this);
        databaseHelper = new DatabaseHelper(RegisterActivity.this);
        user = new GetUser();
    }
}