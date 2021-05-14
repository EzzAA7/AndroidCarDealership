package com.example.cardealer.model;

public class Car {
    private String year;
    private String make;
    private String distance;
    private String price;
    private Boolean accidents;
    private Boolean offers;

    public Car() {
    }

    public Car(String year, String make, String distance, String price, Boolean accidents, Boolean offers) {
        this.year = year;
        this.make = make;
        this.distance = distance;
        this.price = price;
        this.accidents = accidents;
        this.offers = offers;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getAccidents() {
        return accidents;
    }

    public void setAccidents(Boolean accidents) {
        this.accidents = accidents;
    }

    public Boolean getOffers() {
        return offers;
    }

    public void setOffers(Boolean offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "Car{" +
                "year='" + year + '\'' +
                ", make='" + make + '\'' +
                ", distance='" + distance + '\'' +
                ", price='" + price + '\'' +
                ", accidents=" + accidents +
                ", offers=" + offers +
                '}';
    }
}
