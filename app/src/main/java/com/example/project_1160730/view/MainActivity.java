package com.example.project_1160730.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1160730.R;
import com.example.project_1160730.model.Car;
import com.example.project_1160730.service.ConnectionAsyncTask;

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
        intent = new Intent(MainActivity.this, SignInActivity.class);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("http://www.mocky.io/v2/5bfea5963100006300bb4d9a");
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.layout);
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void fillCars(List<Car> cars) {
        LinearLayout linearLayout = (LinearLayout)
                findViewById(R.id.layout);
        linearLayout.removeAllViews();
        if(cars.size() < 1){
            showAlertDialog(linearLayout);
        }
        else{
            for (int i = 0; i < cars.size(); i++) {
                TextView textView = new TextView(this);
                textView.setText(cars.get(i).toString());
                linearLayout.addView(textView);
            }
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

