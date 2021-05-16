package com.example.cardealer.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;

public class SignInActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button btn_sign_in;
    Button btn_move_to_SignUp;
    CheckBox cb;
    SharedPrefManager sharedPrefManager;
    Intent intentToSignUp;
    Intent intentSignIn;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // activity data
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        btn_sign_in = (Button) findViewById(R.id.btnSignIn);
        btn_move_to_SignUp = (Button) findViewById(R.id.btnGoToSignUp);
        cb = (CheckBox) findViewById(R.id.checkBox);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        linearLayout = (LinearLayout) findViewById(R.id.layout);

        // ------------------------- Setup Email & Password Values -----------------------------
        editTextEmail.setText(sharedPrefManager.readString("Email","noValue"));
        editTextPassword.setText(sharedPrefManager.readString("Password","noValue"));

        // ---- Setup intents
        intentToSignUp = new Intent(SignInActivity.this,SignUpActivity.class);
        intentSignIn = new Intent(SignInActivity.this, NavActivity.class);
        // initialize db
        DataBaseHelper dataBaseHelper =new DataBaseHelper(SignInActivity.this,"PROJ", null,1);

        // configure sign in  button
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // first check if any fields are empty
                if(editTextEmail.getText().toString().isEmpty() ||
                        editTextPassword.getText().toString().isEmpty()){
                    // if so show an alert
                    showAlertDialogEmpty(linearLayout);
                }

                // if not empty
                else{
                    // check if such a user exists
                    boolean var = dataBaseHelper.checkUser(editTextEmail.getText().toString(),
                            editTextPassword.getText().toString());

                    // if it doest exist
                    if (var){

                        // check Remember Me checkbox state
                        if (cb.isChecked()) {
                            // if checked we need to save in sharedPref then move to next screen
                            sharedPrefManager.writeString("Email",editTextEmail.getText().toString());
                            sharedPrefManager.writeString("Password",editTextPassword.getText().toString());
                            Toast.makeText(SignInActivity.this, "Values written to shared Preferences",
                                    Toast.LENGTH_SHORT).show();
                            cb.setChecked(false);
                            Toast.makeText(SignInActivity.this, "Signed In Successfully",
                                    Toast.LENGTH_SHORT).show();

                            // save a user session
                            sharedPrefManager.writeString("Session",editTextEmail.getText().toString());

                            startActivity(intentSignIn);
                            finish();
                        }
                        // if not then simply just move to next screen
                        else{
                            // save a user session
                            sharedPrefManager.writeString("Session",editTextEmail.getText().toString());

                            Toast.makeText(SignInActivity.this, "Signed In Successfully",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(intentSignIn);
                            finish();
                        }

                    }
                    // if no such user exist send a toast
                    else {
                        Toast.makeText(SignInActivity.this, "Sign In failed, no such user",
                                Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        // configure sign up button
        btn_move_to_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentToSignUp);
                finish();
            }
        });
    }
    // empty field dialog alert setup
    public void showAlertDialogEmpty(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("Some fields are empty");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(SignInActivity.this, "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
}