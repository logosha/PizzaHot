package com.google.pizzahot.model;

import java.util.ArrayList;

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
}
