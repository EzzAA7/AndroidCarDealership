package com.example.cardealer.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.Car;
import com.example.cardealer.service.ConnectionAsyncTask;

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

                DataBaseHelper dataBaseHelper =new DataBaseHelper(MainActivity.this,"PROJ",null,1);
                dataBaseHelper.insertCar(cars.get(i));

//                TextView textView = new TextView(this);
//                textView.setText(cars.get(i).toString());
//                linearLayout.addView(textView);
            }
            startActivity(intent);
            finish();
        }

    }

    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper =new DataBaseHelper(MainActivity.this,"PROJ", null,1);
        LinearLayout linearLayout = (LinearLayout)
                findViewById(R.id.layout);
        linearLayout.removeAllViews();
        Cursor allCarsCursor = dataBaseHelper.getAllCars();
        linearLayout .removeAllViews();
        while (allCarsCursor.moveToNext()){
            TextView textView =new TextView(MainActivity.this);

            textView.setText(
                    "Year= "+allCarsCursor.getString(0)
                            +"\nMake= "+allCarsCursor.getString(1)
                            +"\nDistance= "+allCarsCursor.getString(2)
                            +"\nPrice= "+allCarsCursor.getString(3)
                            +"\nAccidents= "+allCarsCursor.getString(4)
                            +"\nOffers= "+allCarsCursor.getString(5)
                            +"\n\n"
            );
            linearLayout.addView(textView);
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

