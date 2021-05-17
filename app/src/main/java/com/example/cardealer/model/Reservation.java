package com.example.cardealer.model;

public class Reservation {

    private String carInfo;
    private String carDistance;
    private String carPrice;
    private String name;
    private String phone;
    private String dateTime;

    public Reservation() {

    }

    public Reservation(String carInfo, String carDistance, String carPrice, String name, String phone, String dateTime) {
        this.carInfo = carInfo;
        this.carDistance = carDistance;
        this.carPrice = carPrice;
        this.name = name;
        this.phone = phone;
        this.dateTime = dateTime;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getCarDistance() {
        return carDistance;
    }

    public void setCarDistance(String carDistance) {
        this.carDistance = carDistance;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
