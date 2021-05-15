package com.example.cardealer.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cardealer.model.Car;

import java.util.ArrayList;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, factory, version);
    }


    public void insertCar(Car car) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("YEAR", car.getYear());
        contentValues.put("MAKE",
                car.getMake());
        contentValues.put("MODEL", car.getModel());
        contentValues.put("DISTANCE", car.getDistance());
        contentValues.put("PRICE", car.getPrice());
        contentValues.put("ACCIDENTS", car.getAccidents());
        contentValues.put("OFFERS", car.getOffers());
        sqLiteDatabase.insert("CAR", null, contentValues);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE CAR(YEAR TEXT, MAKE TEXT,MODEL TEXT, DISTANCE TEXT, PRICE TEXT, ACCIDENTS BOOLEAN, OFFERS BOOLEAN ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(db);
    }


    public Cursor getAllCars() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CAR", null);
    }

    public ArrayList<Car> getAllCarsList() {
        ArrayList<Car> cars = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CAR", null);

        if (cursor.moveToFirst()) {
            do {

                String year = cursor.getString(0);
                String make = cursor.getString(1);
                String model = cursor.getString(2);
                String distance = cursor.getString(3);
                String price = cursor.getString(4);
                Boolean accidents = (cursor.getInt(5) == 1);
                Boolean offers = (cursor.getInt(6) == 1);
//                String x = cursor.getString(5);
//                Boolean accidents;
//                if(x.equals("true")) {
//                    accidents = true;
//                }else accidents = false;
//                String y = cursor.getString(6);
//                Boolean offers;
//                if(y.equals("true")) {
//                    offers = true;
//                }else offers = false;

                Car car = new Car(year,make, model, distance, price, accidents, offers);
                System.out.println(car.toString());
                cars.add(car);
            } while (cursor.moveToNext());
        }
        return cars;
    }


}
