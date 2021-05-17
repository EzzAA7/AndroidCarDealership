package com.example.cardealer.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;

import org.mindrot.jbcrypt.BCrypt;

public class SignUpActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    Button addCustomerButton,switchToLogIn;
    Intent intentToSignIn;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // activity data
        linearLayout = (LinearLayout) findViewById(R.id.layout);
        etFirstName = (EditText)findViewById(R.id.editTextFirstName);
        etLastName = (EditText)findViewById(R.id.editTextLastName);
        etEmail = (EditText)findViewById(R.id.editText_email);
        etPassword = (EditText)findViewById(R.id.editTextPassword);
        etConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        etPhoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
        addCustomerButton = (Button) findViewById(R.id.btnSignUp);
        switchToLogIn = (Button) findViewById(R.id.btnSwitchToLogin);

        // ------------ Configuring the spinner adapters ----------------------------

        // Gender adapter
        Spinner gender = (Spinner) findViewById(R.id.gender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender.setAdapter(adapter);

        // Countries adapter
        Spinner countries = (Spinner) findViewById(R.id.country);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        countries.setAdapter(adapter1);

        // Cities adapter
        Spinner cities = (Spinner) findViewById(R.id.city);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.palestinian_cities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        cities.setAdapter(adapter2);

        intentToSignIn = new Intent(SignUpActivity.this,SignInActivity.class);
        DataBaseHelper dataBaseHelper =new DataBaseHelper(SignUpActivity.this,"PROJ", null,1);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first check if any fields are empty
                if(etFirstName.getText().toString().isEmpty() ||
                    etLastName.getText().toString().isEmpty()||
                        etEmail.getText().toString().isEmpty() ||
                        etPassword.getText().toString().isEmpty() ||
                        etConfirmPassword.getText().toString().isEmpty()){

                    showAlertDialogEmpty(linearLayout);
                }
                // if not empty then check for validation
                else{

                    // if passwords match then we can send register action to db
                    if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){

                        String password = etPassword.getText().toString();
                        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());

                        boolean var = dataBaseHelper.registerUser(etFirstName.getText().toString(),
                                etLastName.getText().toString(), etEmail.getText().toString(),
                                pw_hash, gender.getSelectedItem().toString(),
                                countries.getSelectedItem().toString(),
                                cities.getSelectedItem().toString(),
                                etPhoneNumber.getText().toString(),
                                "customer"
                                );

                        // check if db registeration action succeeded
                        if(var){
                            Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Registration Failed, try Again!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    // if passwords dont match then alert the user
                    else{
                        showAlertDialogNotEqual(linearLayout);
                    }

                }
            }
        });

        // simply just move to sign in activity
        switchToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentToSignIn);
                finish();
            }
        });

    }

    // the alert func for not equal passwords
    public void showAlertDialogNotEqual(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("Passwords don't match");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(SignUpActivity.this, "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    // the alert func for empty field
    public void showAlertDialogEmpty(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("Some fields are empty");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(SignUpActivity.this, "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
}