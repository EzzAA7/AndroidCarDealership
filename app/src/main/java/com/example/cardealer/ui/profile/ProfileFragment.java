package com.example.cardealer.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.User;
import com.example.cardealer.service.SharedPrefManager;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    EditText etFirstName, etLastName, etPassword, etConfirmPassword, etPhoneNumber;
    Button editCustomerButton;
    LinearLayout linearLayout;
    SharedPrefManager sharedPrefManager;
    ArrayList<User> users;

    public static ProfileFragment newInstance() {

        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        // activity data
        linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        etFirstName = (EditText)view.findViewById(R.id.editTextFirstNameCustEdit);
        etLastName = (EditText)view.findViewById(R.id.editTextLastNameCustEdit);
        etPassword = (EditText)view.findViewById(R.id.editTextPasswordCustEdit);
        etConfirmPassword = (EditText)view.findViewById(R.id.editTextConfirmPasswordCusEdit);
        etPhoneNumber = (EditText)view.findViewById(R.id.editText_phoneCustEdit);
        editCustomerButton = (Button) view.findViewById(R.id.btnCustEdit);

        // setup db
        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);

        // get user session
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        String email = sharedPrefManager.readString("Session","noValue");
        // get current user from db using userSession
        User currentUser = dataBaseHelper.getUser(email);

        // configure initial values for profile to be current user's values
        etFirstName.setText(currentUser.getfName());
        etLastName.setText(currentUser.getlName());
        etPassword.setText(currentUser.getPassword());
        etConfirmPassword.setText(currentUser.getPassword());
        etPhoneNumber.setText(currentUser.getPhoneNumber());

        editCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first check if any fields are empty
                if(etFirstName.getText().toString().isEmpty() ||
                        etLastName.getText().toString().isEmpty()||
                        etPassword.getText().toString().isEmpty() ||
                        etPhoneNumber.getText().toString().isEmpty() ||
                        etConfirmPassword.getText().toString().isEmpty()){

                    showAlertDialogEmpty(linearLayout);
                }
                // if not empty then check for validation
                else{

                    // if passwords match then we can send register action to db
                    if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){

                        String password = etPassword.getText().toString();
                        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());

                        boolean var = dataBaseHelper.updateUser(etFirstName.getText().toString(),
                                etLastName.getText().toString(), email,
                                pw_hash,
                                etPhoneNumber.getText().toString());

                        // check if db registeration action succeeded
                        if(var){
                            Toast.makeText(getActivity(), "User edited successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Editing Failed, try Again!", Toast.LENGTH_SHORT).show();
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
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
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