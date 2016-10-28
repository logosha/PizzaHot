package com.google.pizzahot.model;

/**
 * Created by Алексей on 28.10.2016.
 */

public class VenuesData {

    private int id;

    private String name;

    private Location location;

    private double distance;

    public VenuesData() {
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
