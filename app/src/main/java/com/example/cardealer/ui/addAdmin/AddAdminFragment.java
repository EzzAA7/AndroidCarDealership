package com.example.cardealer.ui.addAdmin;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.User;
import com.example.cardealer.service.SharedPrefManager;
import com.example.cardealer.view.SignInActivity;
import com.example.cardealer.view.SignUpActivity;

import org.mindrot.jbcrypt.BCrypt;

public class AddAdminFragment extends Fragment {

    private AddAdminViewModel mViewModel;
    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    TextView tvAreaCodeAdmin;
    Button createAdminButton;
    Intent intentToSignIn;
    LinearLayout linearLayout;

    public static AddAdminFragment newInstance() {

        return new AddAdminFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_admin_fragment, container, false);

        // activity data
        linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        etFirstName = (EditText)view.findViewById(R.id.editTextFirstNameAdmin);
        etLastName = (EditText)view.findViewById(R.id.editTextLastNameAdmin);
        etEmail = (EditText)view.findViewById(R.id.editText_emailAdmin);
        etPassword = (EditText)view.findViewById(R.id.editTextPasswordAdmin);
        etConfirmPassword = (EditText)view.findViewById(R.id.editTextConfirmPasswordAdmin);
        tvAreaCodeAdmin = (TextView) view.findViewById(R.id.tvAreaCodeAdmin);
        etPhoneNumber = (EditText)view.findViewById(R.id.editTextPhoneNumberAdmin);
        createAdminButton = (Button) view.findViewById(R.id.btnCreateAdmin);

        // ------------ Configuring the spinner adapters ----------------------------

        // Gender adapter
        Spinner gender = (Spinner) view.findViewById(R.id.genderAdmin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.genders_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender.setAdapter(adapter);

        // Countries adapter
        Spinner countries = (Spinner) view.findViewById(R.id.countryAdmin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.countries_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        countries.setAdapter(adapter1);

        // Cities adapter
        Spinner cities = (Spinner) view.findViewById(R.id.cityAdmin);

        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String selected_country = countries.getSelectedItem().toString();
                // setup which cities to appear based on selected country
                switch (selected_country){
                    case "Palestine":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        ArrayAdapter<CharSequence> adapterCities = ArrayAdapter.createFromResource(getActivity(),
                                R.array.palestinian_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00970
                        tvAreaCodeAdmin.setText("00970");
                        break;
                    case "Jordan":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        adapterCities = ArrayAdapter.createFromResource(getActivity(),
                                R.array.jordan_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00962
                        tvAreaCodeAdmin.setText("00962");

                        break;
                    case "Syria":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        adapterCities = ArrayAdapter.createFromResource(getActivity(),
                                R.array.syrian_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00963
                        tvAreaCodeAdmin.setText("00963");
                        break;
                    case "Lebanon":
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        adapterCities = ArrayAdapter.createFromResource(getActivity(),
                                R.array.leb_cities_array, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        cities.setAdapter(adapterCities);

                        // set area code to 00961
                        tvAreaCodeAdmin.setText("00961");

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        intentToSignIn = new Intent(getActivity(), SignInActivity.class);
        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);

        // setup admin button
        createAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first check if any fields are empty
                if(etFirstName.getText().toString().isEmpty() ||
                        etLastName.getText().toString().isEmpty()||
                        etEmail.getText().toString().isEmpty() ||
                        etPassword.getText().toString().isEmpty()){

                    showAlertDialogEmpty(linearLayout);
                }
                // if not empty then check for validation
                else{

                    // calculate validation characters
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

                    else {
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
                                        (tvAreaCodeAdmin.getText().toString() + phone),
                                        "admin");

                                // check if db registeration action succeeded
                                if (var) {
                                    Toast.makeText(getActivity(), "User registered successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Registration Failed, try Again!", Toast.LENGTH_SHORT).show();

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddAdminViewModel.class);
        //
    }

    // the alert func for not equal passwords
    public void showAlertDialogNotEqual(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("Passwords don't match");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getActivity(), "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    // the alert func for empty field
    public void showAlertDialogEmpty(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("Some fields are empty");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getActivity(), "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    // the alert func for wrong validation in a field
    public void showAlertDialogWrongValid(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("Some fields are not valid");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getActivity(), "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    // the alert func that user exists
    public void showAlertDialogUserExists(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Unsuccessful Action");
        alert.setMessage("An admin with this email already exists");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getActivity(), "Try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

}