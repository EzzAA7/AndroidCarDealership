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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.view.SignInActivity;

import org.mindrot.jbcrypt.BCrypt;

public class AddAdminFragment extends Fragment {

    private AddAdminViewModel mViewModel;
    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
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
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.palestinian_cities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        cities.setAdapter(adapter2);

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

                    // if passwords match then we can send register action to db
                    if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){

                        String password = etPassword.getText().toString();
                        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());

                        boolean var = dataBaseHelper.registerUser(etFirstName.getText().toString(),
                                etLastName.getText().toString(), etEmail.getText().toString(),
                                pw_hash, gender.getSelectedItem().toString(),
                                countries.getSelectedItem().toString(),
                                cities.getSelectedItem().toString(),
                                etPhoneNumber.getText().toString());

                        // check if db registeration action succeeded
                        if(var){
                            Toast.makeText(getActivity(), "User registered successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Registration Failed, try Again!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    // if passwords dont match then alert the user
                    else{
                        showAlertDialogNotEqual(linearLayout);
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

}