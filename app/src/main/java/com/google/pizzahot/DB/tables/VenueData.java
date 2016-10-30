package com.google.pizzahot.DB.tables;

import com.google.pizzahot.model.Venue;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Алексей on 28.10.2016.
 */
@DatabaseTable(tableName = "venue_data")
public class VenueData {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String name;

    @DatabaseField(canBeNull = false)
    private LocationData location;

    public VenueData(Venue venue) {
        this.name = venue.getName();
    }

    VenueData() {
    }


}
