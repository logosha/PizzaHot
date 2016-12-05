package com.google.pizzahot.model;

import java.util.List;

/**
 * Created by Алексей on 26.11.2016.
 */
public class GroupItem {

    public List<Venue> getVenues() {
        return venue;
    }

    public void setVenues(List<Venue> venues) {
        this.venue = venues;
    }

    private List<Venue> venue;
}
