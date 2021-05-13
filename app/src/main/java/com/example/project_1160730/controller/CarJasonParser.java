package com.example.project_1160730.controller;

import com.example.project_1160730.model.Car;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarJasonParser {
    public static List<Car> getObjectFromJason(String jason) {
        List<Car> cars;
        try {
            JSONArray jsonArray = new JSONArray(jason);
            cars = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Car car = new Car();
                car.setYear(jsonObject.getString("year"));
                car.setMake(jsonObject.getString("make"));
                car.setDistance(jsonObject.getString("distance"));
                car.setPrice(jsonObject.getString("price"));
                car.setAccidents(jsonObject.getBoolean("accidents"));
                car.setOffers(jsonObject.getBoolean("offers"));
                cars.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return cars;
    }
}

