package com.google.pizzahot.DB.tables;

import com.google.pizzahot.model.Venue;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Алексей on 28.10.2016.
 */

@DatabaseTable(tableName = "venue")
public class VenueData implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private double distance;

    @DatabaseField
    private double latitude;

    @DatabaseField
    private double longitude;

    @DatabaseField
    private String city;

    @DatabaseField
    private String country;

    @DatabaseField
    private String postalCode;

    public double getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public VenueData(Venue venue) {
        this.city = venue.getLocation().getCity();
        this.country = venue.getLocation().getCountry();
        this.distance = venue.getLocation().getDistance();
        this.latitude = venue.getLocation().getLat();
        this.longitude = venue.getLocation().getLng();
        this.name = venue.getName();
        this.postalCode = venue.getLocation().getPostalCode();
    }

    public VenueData() {
    }

    @Override
    public String toString() {
        return "VenueData{" +
                "city='" + city + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", distance=" + distance +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }

}