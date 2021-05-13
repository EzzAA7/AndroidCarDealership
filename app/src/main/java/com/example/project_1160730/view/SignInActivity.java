package com.example.project_1160730.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_1160730.R;

public class SignInActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextPassword;
    Button btnSignIn;
    Button btnMoveToSignUp;
    SharedPrefManager sharedPrefManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnMoveToSignUp = (Button) findViewById(R.id.btnMoveToSignUp);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        intent = new Intent(SignInActivity.this,SignUpActivity.class);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.writeString("userName",editTextUserName.getText().toString());

                sharedPrefManager.writeString("password",editTextPassword.getText().toString());
                Toast.makeText(SignInActivity.this, "Values written to shared Preferences",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnMoveToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });
    }
}