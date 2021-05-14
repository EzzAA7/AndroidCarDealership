package com.example.cardealer.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cardealer.model.Car;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper{
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
        contentValues.put("DISTANCE", car.getDistance());
        contentValues.put("PRICE", car.getPrice());
        contentValues.put("ACCIDENTS", car.getAccidents());
        contentValues.put("OFFERS", car.getOffers());
        sqLiteDatabase.insert("CAR", null, contentValues);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE CAR(YEAR TEXT, MAKE TEXT,DISTANCE TEXT, PRICE TEXT, ACCIDENTS BOOLEAN, OFFERS BOOLEAN ) ");
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



}
