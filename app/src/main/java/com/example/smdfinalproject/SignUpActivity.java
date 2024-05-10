package com.example.smdfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

public class SignUpActivity extends AppCompatActivity {

    EditText et_Email, et_Password, et_Name, et_RePassword, et_UName;
    Button RegisterBtn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_Email=findViewById(R.id.editTextEmail);
        et_Password=findViewById(R.id.editTextPassword);
        et_Name=findViewById(R.id.editTextName);
        et_UName=findViewById(R.id.editTextUserName);
        et_RePassword=findViewById(R.id.editTextConfirmPassword);

        RegisterBtn=findViewById(R.id.buttonRegister);

        String email = et_Email.getText().toString();
        String password = et_Password.getText().toString();
        String RePassword = et_RePassword.getText().toString();
        String Name = et_Name.getText().toString();
        String UserName = et_UName.getText().toString();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}