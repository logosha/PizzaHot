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
public class VenueData implements Comparable<VenueData>, Serializable {

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
    private double postalCode;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getPostalCode() {
        return postalCode;
    }

    public VenueData(String city, String country, double distance, int id, double latitude, double longitude, String name, int postalCode) {
        this.city = city;
        this.country = country;
        this.distance = distance;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.postalCode = postalCode;
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

    @Override
    public int compareTo(VenueData venueData) {
        return 0;
    }

    public static Comparator<VenueData> VenueDistanceComparator
            = new Comparator<VenueData>() {

        public int compare(VenueData venue1, VenueData venue2) {

            Double venueDistance1 = venue1.getDistance();
            Double venueDistance2 = venue2.getDistance();


            return venueDistance1.compareTo(venueDistance2);
        }

    };

}