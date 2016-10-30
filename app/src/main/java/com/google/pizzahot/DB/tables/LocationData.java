package com.google.pizzahot.DB.tables;

import com.google.pizzahot.model.Location;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Алексей on 28.10.2016.
 */
@DatabaseTable(tableName = "location_data")
public class LocationData {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private double distance;

    @DatabaseField(canBeNull = false)
    private double latitude;

    @DatabaseField(canBeNull = false)
    private double longitude;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "page_id")
    private VenueData page;

    public LocationData(Location location, VenueData page) {
        this.distance = location.getDistance();
        this.latitude = location.getLat();
        this.longitude = location.getLng();
        this.page = page;
    }

    LocationData() {
    }
}
