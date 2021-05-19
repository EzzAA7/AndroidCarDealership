package com.example.cardealer.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.Car;
import com.example.cardealer.service.ConnectionAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    LinearLayout linearLayout;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgress(false);

        // Configure activity data
        intent = new Intent(MainActivity.this, SignInActivity.class);
        button = (Button) findViewById(R.id.button);

        // --- Configure button action
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // simply connect to REST API
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("http://www.mocky.io/v2/5bfea5963100006300bb4d9a");
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.layout);
    }

    public void setButtonText(String text) {

        button.setText(text);
    }

    public void fillCars(ArrayList<Car> cars) {
        if(cars.size() < 1){
            showAlertDialog(linearLayout);
        }
        else{
//            deleteDatabase("PROJ");
            startActivity(intent);
            finish();
        }

    }

    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar)
                findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showAlertDialog(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unsuccessful Action Error");
        alert.setMessage("No cars were able to be loaded");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(MainActivity.this, "Not connected, try Again! :)", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

}

