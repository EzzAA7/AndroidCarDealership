package com.example.project_1160730.service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.project_1160730.controller.CarJasonParser;
import com.example.project_1160730.model.Car;
import com.example.project_1160730.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;

    public ConnectionAsyncTask(Activity activity) {

        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
        ((MainActivity) activity).setProgress(true);
    }

    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ((MainActivity) activity).setProgress(false);
//        ((MainActivity) activity).setButtonText("connected");
//        if (s != null) {
//            List<Student> students =
//                    StudentJasonParser.getObjectFromJason(s);
//            ((MainActivity) activity).fillStudents(students);
//        }
        if(s == null){
            List<Car> cars = new ArrayList<>();
            ((MainActivity) activity).fillCars(cars);
        }
        else{
            List<Car> cars = CarJasonParser.getObjectFromJason(s);
            ((MainActivity) activity).fillCars(cars);
            Toast.makeText(((MainActivity) activity), "Connected! :)", Toast.LENGTH_SHORT).show();
        }

    }

}
