package com.google.pizzahot.model;

import java.util.List;

/**
 * Created by Алексей on 28.10.2016.
 */

public class Response {

    private List<VenueGroup> venueGroups;

    public List<VenueGroup> getVenueGroups() {
        return venueGroups;
    }

    public void setVenueGroups(List<VenueGroup> venueGroups) {
        this.venueGroups = venueGroups;
    }

}
