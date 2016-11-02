package com.google.pizzahot.model;

import com.google.pizzahot.DB.tables.VenueData;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Алексей on 28.10.2016.
 */

public class Venue {

    private String id;

    private String name;

    private Location location;

    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }


    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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


    public static Comparator<Venue> VenueDistanceComparator
            = new Comparator<Venue>() {

        public int compare(Venue venue1, Venue venue2) {

            Double venueDistance1 = venue1.getLocation().getDistance();
            Double venueDistance2 = venue2.getLocation().getDistance();

            return venueDistance1.compareTo(venueDistance2);
        }

    };
}
