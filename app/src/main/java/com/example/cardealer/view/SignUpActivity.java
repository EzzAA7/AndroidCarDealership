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

public class SignUpActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    Intent intentToSignIn;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        linearLayout = (LinearLayout) findViewById(R.id.layout);

        etFirstName = (EditText)findViewById(R.id.editTextFirstName);
        etLastName = (EditText)findViewById(R.id.editTextLastName);
        etEmail = (EditText)findViewById(R.id.editText_email);
        etPassword = (EditText)findViewById(R.id.editTextPassword);
        etConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        etPhoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);

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

        Button addCustomerButton = (Button) findViewById(R.id.btnSignUp);
        Button switchToLogIn = (Button) findViewById(R.id.btnSwitchToLogin);
        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    boolean var = dataBaseHelper.registerUser(etFirstName.getText().toString(),
                            etLastName.getText().toString(), etEmail.getText().toString(),
                            etPassword.getText().toString(), gender.getSelectedItem().toString(),
                            countries.getSelectedItem().toString(),
                            cities.getSelectedItem().toString(),
                            etPhoneNumber.getText().toString());

                    if(var){
                        Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Registration Failed, try Again!", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    showAlertDialogNotEqual(linearLayout);
                }
            }
        });

        switchToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentToSignIn);
                finish();
            }
        });


//        newCustomer.setmGender(genderSpinner.getSelectedItem().toString());
    }

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
}