package com.example.cardealer.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardealer.R;

public class SignInActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button btn_sign_in;
    Button btn_move_to_SignUp;
    CheckBox cb;
    SharedPrefManager sharedPrefManager;
    Intent intentToSignUp;
    Intent intentSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        btn_sign_in = (Button) findViewById(R.id.btnSignIn);
        btn_move_to_SignUp = (Button) findViewById(R.id.btnGoToSignUp);
        cb = (CheckBox) findViewById(R.id.checkBox);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        intentToSignUp = new Intent(SignInActivity.this,SignUpActivity.class);
        intentSignIn = new Intent(SignInActivity.this, NavActivity.class);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.writeString("Email",editTextEmail.getText().toString());
                sharedPrefManager.writeString("password",editTextPassword.getText().toString());
                Toast.makeText(SignInActivity.this, "Values written to shared Preferences",
                        Toast.LENGTH_SHORT).show();
                startActivity(intentSignIn);
                finish();
            }
        });
        btn_move_to_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentToSignUp);
                finish();
            }
        });
    }
}