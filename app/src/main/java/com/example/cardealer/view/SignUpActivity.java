package com.example.cardealer.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.User;

import org.mindrot.jbcrypt.BCrypt;

public class SignUpActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    TextView tvAreaCode;
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
        tvAreaCode = (TextView) findViewById(R.id.textViewAreaCode);
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

        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String selected_country = countries.getSelectedItem().toString();
                // setup which cities to appear based on selected country
                switch (selected_country){
                    case "Palestine":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        ArrayAdapter<CharSequence> adapterCities = ArrayAdapter.createFromResource(SignUpActivity.this,
                                R.array.palestinian_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00970
                        tvAreaCode.setText("00970");
                        break;
                    case "Jordan":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        adapterCities = ArrayAdapter.createFromResource(SignUpActivity.this,
                                R.array.jordan_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00962
                        tvAreaCode.setText("00962");

                        break;
                    case "Syria":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        adapterCities = ArrayAdapter.createFromResource(SignUpActivity.this,
                                R.array.syrian_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00963
                        tvAreaCode.setText("00963");
                        break;
                    case "Lebanon":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        adapterCities = ArrayAdapter.createFromResource(SignUpActivity.this,
                                R.array.leb_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00961
                        tvAreaCode.setText("00961");

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });


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
                        etPhoneNumber.getText().toString().isEmpty() ||
                        etConfirmPassword.getText().toString().isEmpty()){

                    showAlertDialogEmpty(linearLayout);
                }
                // if not empty then check for validation
                else{

                    String pw = etPassword.getText().toString();
                    int pwNumOfChars = pw.length();
                    int digitCount = 0;
                    int letterCount = 0;
                    int scCount = 0;

                    for( int i = 0; i < pwNumOfChars; i++ )
                    {
                        if (Character.isDigit(pw.charAt(i))) {
                            digitCount++;
                        }

                        if (Character.isAlphabetic(pw.charAt(i))) {
                            letterCount++;
                        }

                        if (!Character.isDigit(pw.charAt(i)) && !Character.isAlphabetic(pw.charAt(i))
                                && !Character.isWhitespace(pw.charAt(i))) {
                            scCount++;
                        }
                    }

                    if(etFirstName.getText().toString().length() < 3 ||
                            etLastName.getText().toString().length() < 3 || pwNumOfChars < 5 ||
                                    digitCount < 1 || letterCount < 1 || scCount < 1 ){

                        showAlertDialogWrongValid(linearLayout);
                    }

                    else{

                        // if passwords match then we can send register action to db
                        if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){

                            // we need to check if user exists before registering them

                            User currentUser = dataBaseHelper.getUser(etEmail.getText().toString());

                            // there already exists such a user
                            if(currentUser != null){
                                showAlertDialogUserExists(linearLayout);
                            }

                            // no such user => proceed to register them
                            else {

                                String password = etPassword.getText().toString();
                                String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
                                String phone = etPhoneNumber.getText().toString();

                                boolean var = dataBaseHelper.registerUser(etFirstName.getText().toString(),
                                        etLastName.getText().toString(), etEmail.getText().toString(),
                                        pw_hash, gender.getSelectedItem().toString(),
                                        countries.getSelectedItem().toString(),
                                        cities.getSelectedItem().toString(),
                                        (tvAreaCode.getText().toString() + phone),
                                        "customer"
                                );

                                // check if db registeration action succeeded
                                if (var) {
                                    Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Registration Failed, try Again!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        // if passwords dont match then alert the user
                        else{
                            showAlertDialogNotEqual(linearLayout);
                        }

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

    // the alert func for wrong validation in a field
    public void showAlertDialogWrongValid(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("Some fields are not valid");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(SignUpActivity.this, "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    // the alert func for that user exists
    public void showAlertDialogUserExists(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("A user with this email already exists");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(SignUpActivity.this, "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
}