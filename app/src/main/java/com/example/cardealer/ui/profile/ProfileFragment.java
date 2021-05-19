package com.example.cardealer.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.Image;
import com.example.cardealer.model.User;
import com.example.cardealer.service.SharedPrefManager;
import com.example.cardealer.view.SignUpActivity;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    EditText etFirstName, etLastName, etPassword, etConfirmPassword, etPhoneNumber;
    TextView tvAreaCodeProfile;
    Button editCustomerButton, changePictureButton;
    LinearLayout linearLayout;
    SharedPrefManager sharedPrefManager;
    ArrayList<User> users;

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private ImageView selectedImageView;
    private EditText titleEditText;

    public static ProfileFragment newInstance() {

        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        this.selectedImageView = (ImageView) view.findViewById(R.id.imageViewProfile);

        // activity data
        linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        etFirstName = (EditText)view.findViewById(R.id.editTextFirstNameCustEdit);
        etLastName = (EditText)view.findViewById(R.id.editTextLastNameCustEdit);
        etPassword = (EditText)view.findViewById(R.id.editTextPasswordCustEdit);
        etConfirmPassword = (EditText)view.findViewById(R.id.editTextConfirmPasswordCusEdit);
        etPhoneNumber = (EditText)view.findViewById(R.id.editText_phoneCustEdit);
        tvAreaCodeProfile = (TextView) view.findViewById(R.id.tvAreaCodeProfile);
        editCustomerButton = (Button) view.findViewById(R.id.btnCustEdit);
        ImageView img = (ImageView) view.findViewById(R.id.imageViewProfile);
        changePictureButton = (Button) view.findViewById(R.id.buttonChangePic);

        // setup db
        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);

        // get user session
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        String email = sharedPrefManager.readString("Session","noValue");
        // get current user from db using userSession
        User currentUser = dataBaseHelper.getUser(email);

        // TODO: let user update instead of create in case of new press (copy this down to onClick)
        try {
            ArrayList<Image> images = dataBaseHelper.getAllImages();
            Bitmap myImage = null;

            for(Image image: images){
                if (image.getTitle().equals(email)){
                    myImage = image.getImage();
                }
            }

            if(myImage != null){
                img.setImageBitmap(myImage);
            }
            else {
                img.setImageResource(R.drawable.default_profile);
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // configure initial values for profile to be current user's values
        etFirstName.setText(currentUser.getfName());
        etLastName.setText(currentUser.getlName());
        etPassword.setText(currentUser.getPassword());
        etConfirmPassword.setText(currentUser.getPassword());

        String phone = currentUser.getPhoneNumber();
        tvAreaCodeProfile.setText(phone.substring(0,5));
        etPhoneNumber.setText(phone.substring(5));

        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
            }
        });

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

                            String password = etPassword.getText().toString();
                            String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
                            String phone = etPhoneNumber.getText().toString();

                            boolean var = dataBaseHelper.updateUser(etFirstName.getText().toString(),
                                    etLastName.getText().toString(), email,
                                    pw_hash,
                                    (tvAreaCodeProfile.getText().toString() + phone));

                            // check if db registration action succeeded
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
            }
        });


        return view;
    }

    public void cancel(View view) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE && data.getData()!= null && data!=null) {
            try {
                Uri imageFilePath = data.getData();
                Bitmap imageToStore = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageFilePath);
                selectedImageView.setImageBitmap(imageToStore);

                storeImage(imageToStore);

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    public void storeImage (Bitmap imageToStore){

        try {
            if(selectedImageView.getDrawable()!= null && imageToStore != null){
                DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);

                sharedPrefManager = SharedPrefManager.getInstance(getActivity());
                String email = sharedPrefManager.readString("Session","noValue");

                boolean result = dataBaseHelper.storeImage(new Image(email, imageToStore));
                if(result){
                    Toast.makeText(getActivity(), "added to db!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "failed adding pic!", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }

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

}