package com.google.pizzahot.DB.tables;

import com.google.pizzahot.model.LocationData;
import com.google.pizzahot.model.Venue;
import com.google.pizzahot.model.VenuesData;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Алексей on 28.10.2016.
 */

public class VenueTable  {


    // id is generated by the database and set on the object automatically
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField(foreign = true)
    private LocationTable location;

    public VenueTable(Venue venue) {
        this.name = venue.getName();
    }

    VenueTable() {
    }


}