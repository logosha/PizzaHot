package com.google.pizzahot.DB.tables;

import com.google.pizzahot.model.Venue;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Comparator;

/**
 * Created by Алексей on 28.10.2016.
 */

@DatabaseTable(tableName = "venue")
public class VenueData implements Comparable<VenueData> {

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

    public VenueData(Venue venue) {
        this.name = venue.getName();
        this.distance = venue.getLocation().getDistance();
        this.latitude = venue.getLocation().getLat();
        this.longitude = venue.getLocation().getLng();
    }

    VenueData() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append(", ").append("name=").append(name);
        sb.append(", ").append("distance=").append(distance);
        sb.append(", ").append("latitude=").append(latitude);
        sb.append(", ").append("longitude=").append(longitude);
        return sb.toString();
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