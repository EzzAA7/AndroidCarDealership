package com.example.cardealer.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.mindrot.jbcrypt.BCrypt;


public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, factory, version);
    }

    public boolean registerUser(String firstName, String lastName, String email, String password, String gender, String country, String city, String phoneNumber, String role) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FNAME", firstName);
        contentValues.put("LNAME",
                lastName);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", password);
        contentValues.put("GENDER", gender);
        contentValues.put("COUNTRY", country);
        contentValues.put("CITY", city);
        contentValues.put("PHONENUMBER", phoneNumber);
        contentValues.put("ROLE", role);
        long result = sqLiteDatabase.insert("USER", null, contentValues);

        if(result == -1){
            return false;
        }
        return true;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());

        String []  columns = {"PASSWORD"};
        String selections = "EMAIL" + "=?";
        String [] selectionArgs = { email};

        Cursor cursor = sqLiteDatabase.query("USER", columns, selections, selectionArgs, null, null, null);

        int count = cursor.getCount();
        sqLiteDatabase.close();

        if(cursor.moveToFirst() && count >= 1) {
            do {
                String hashed = cursor.getString(0);

                if (BCrypt.checkpw(password, hashed)){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }

    public boolean isUserAdmin(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String []  columns = {"ROLE"};
        String selections = "EMAIL" + "=?";
        String [] selectionArgs = { email };

        Cursor cursor = sqLiteDatabase.query("USER", columns, selections, selectionArgs, null, null, null);

        int count = cursor.getCount();
        sqLiteDatabase.close();

        if(cursor.moveToFirst() && count >= 1) {
            do {
                Boolean role = (cursor.getString(0).equals("admin"));

                if (role){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE CAR(YEAR TEXT, MAKE TEXT,MODEL TEXT, DISTANCE TEXT, PRICE TEXT, ACCIDENTS BOOLEAN, OFFERS BOOLEAN ) ");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS USER(ID INTEGER PRIMARY KEY AUTOINCREMENT, FNAME TEXT,LNAME TEXT, EMAIL TEXT, PASSWORD TEXT, GENDER TEXT, COUNTRY TEXT, CITY TEXT, PHONENUMBER TEXT, ROLE TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(db);
    }

//    public void insertCar(Car car) {
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("YEAR", car.getYear());
//        contentValues.put("MAKE",
//                car.getMake());
//        contentValues.put("MODEL", car.getModel());
//        contentValues.put("DISTANCE", car.getDistance());
//        contentValues.put("PRICE", car.getPrice());
//        contentValues.put("ACCIDENTS", car.getAccidents());
//        contentValues.put("OFFERS", car.getOffers());
//        sqLiteDatabase.insert("CAR", null, contentValues);
//    }

//    public Cursor getAllCars() {
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.rawQuery("SELECT * FROM CAR", null);
//    }

//    public ArrayList<Car> getAllCarsList() {
//        ArrayList<Car> cars = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CAR", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//
//                String year = cursor.getString(0);
//                String make = cursor.getString(1);
//                String model = cursor.getString(2);
//                String distance = cursor.getString(3);
//                String price = cursor.getString(4);
//                Boolean accidents = (cursor.getInt(5) == 1);
//                Boolean offers = (cursor.getInt(6) == 1);
//                Car car = new Car(year,make, model, distance, price, accidents, offers);
//                System.out.println(car.toString());
//                cars.add(car);
//            } while (cursor.moveToNext());
//        }
//        return cars;
//    }


}
