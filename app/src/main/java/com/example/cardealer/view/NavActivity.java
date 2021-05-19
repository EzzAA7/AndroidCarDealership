package com.example.cardealer.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.Image;
import com.example.cardealer.model.User;
import com.example.cardealer.service.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class NavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // setup db
        DataBaseHelper dataBaseHelper =new DataBaseHelper(NavActivity.this,"PROJ", null,1);

        // get session
        sharedPrefManager = SharedPrefManager.getInstance(this);
        String email = sharedPrefManager.readString("Session","noValue");

        // get HeaderView
        View headerView = navigationView.getHeaderView(0);

        // get picture view in menu header
        ImageView img = (ImageView) headerView.findViewById(R.id.imageViewProfileMenu);

        // setup a drawer listener for picture change in profileFragment
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

                headerDataHandler(dataBaseHelper, email, headerView);

                // handle change of profile picture
                profilePictureHandler(dataBaseHelper, email, img);
            }
        });

//        profilePictureHandler(dataBaseHelper, email, img);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_car_menu, R.id.nav_your_reservations, R.id.nav_your_favourites,
                R.id.nav_special_offers, R.id.nav_profile, R.id.nav_contact, R.id.nav_logout,
                R.id.nav_add_admin, R.id.nav_delete_customers, R.id.nav_view_reservations, R.id.nav_home_admin)
                .setDrawerLayout(drawer)
                .build();

        // setup which menu fragments should appear
        if(dataBaseHelper.isUserAdmin(email)){
            // in case of admin only (home_admin, add_admin, deleteCustomers, viewReservations) should appear
            navController.navigate(R.id.nav_home_admin);
            navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_car_menu).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_your_reservations).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_your_favourites).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_special_offers).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_contact).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
        }
        else{
            // in case of user these (home_admin, add_admin, deleteCustomers, viewReservations) shouldn't appear
            navigationView.getMenu().findItem(R.id.nav_home_admin).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_add_admin).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_delete_customers).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_view_reservations).setVisible(false);

        }
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    // handler to setup picture in drawer header in case of changes and in intial drawer swipe
    private void profilePictureHandler(DataBaseHelper dataBaseHelper, String email, ImageView img) {
        try {
            // get all images
            ArrayList<Image> images = dataBaseHelper.getAllImages();
            // check that we have any images
            if (images != null) {

                Bitmap myImage = null;
                // find a saved picture for our user using their session
                for (Image image : images) {
                    if (image.getTitle().equals(email)) {
                        myImage = image.getImage();
                    }
                }

                if (myImage != null) {
                    // in case we found a picture for our user then set it
                    img.setImageBitmap(myImage);
                }
                else {
                    // in case non of the pictures in db belong to our current user
                    // then display a generic picture
                    img.setImageResource(R.drawable.default_profile);
                }
            }
            // if we have no images in db then display a generic profile picture
            else {
                img.setImageResource(R.drawable.default_profile);
            }
        } catch (Exception e) {
            Toast.makeText(NavActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
        }
    }

    private void headerDataHandler(DataBaseHelper dataBaseHelper, String email, View header) {

        // setup email value in menu header to user's email
        TextView navEmail = (TextView) header.findViewById(R.id.tvProfileEmail);
        navEmail.setText(email);

        // get current user
        User currentUser = dataBaseHelper.getUser(email);

        // setup name value in menu header to current user's name
        TextView navName = (TextView) header.findViewById(R.id.tvProfileName);
        navName.setText(currentUser.getfName() + " " + currentUser.getlName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}