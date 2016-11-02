package com.google.pizzahot.model;

import java.util.List;

/**
 * Created by Алексей on 28.10.2016.
 */

public class Response {

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    private List<Venue> venues;


}
