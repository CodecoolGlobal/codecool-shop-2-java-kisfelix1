package com.codecool.shop.model;

public class AddressModel {
    private String country;
    private String county;
    private int zipCode;
    private String city;
    private String streetAddress;

    public AddressModel(String country, String county, int zipCode, String city, String streetAddress) {
        this.country = country;
        this.county = county;
        this.zipCode = zipCode;
        this.city = city;
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public String getCounty() {
        return county;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
}
