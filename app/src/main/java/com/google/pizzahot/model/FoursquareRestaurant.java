package com.google.pizzahot.model;

/**
 * Created by Алексей on 27.10.2016.
 */

public class FoursquareRestaurant {
    private String name;
    private long distance;

    public FoursquareRestaurant() {
           }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}