package com.google.pizzahot.DB.tables;

import com.google.pizzahot.model.Venue;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Алексей on 28.10.2016.
 */

@DatabaseTable(tableName = "venue")
public class VenueData implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private double distance;

    @DatabaseField
    private double latitude;

    @DatabaseField
    private double longitude;

    @DatabaseField
    private String city;

    @DatabaseField
    private String url;

    @DatabaseField
    private double rating;

    @DatabaseField
    private String phone;

    @DatabaseField
    private String address;

    private String urlPhoto;

    @DatabaseField
    private int width;

    @DatabaseField
    private int height;

    @DatabaseField
    private String prefix;

    @DatabaseField
    private String suffix;


    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getRating() {
        return rating;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public double getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getHeight() {
        return height;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getWidth() {
        return width;
    }

    public VenueData(Venue venue) {
        this.city = venue.getLocation().getCity();
        this.distance = venue.getLocation().getDistance();
        this.latitude = venue.getLocation().getLat();
        this.longitude = venue.getLocation().getLng();
        this.name = venue.getName();
        this.url = venue.getUrl();
        this.rating = venue.getRating();
        this.phone = venue.getContact().getPhone();
        this.address = venue.getLocation().getAddress();
        this.width = venue.getPhoto().getGroups().get(0).getItems().get(0).getWidth();
        this.height = venue.getPhoto().getGroups().get(0).getItems().get(0).getHeight();
        this.prefix = venue.getPhoto().getGroups().get(0).getItems().get(0).getPrefix();
        this.suffix = venue.getPhoto().getGroups().get(0).getItems().get(0).getSuffix();



    }

    public VenueData() {
    }

    @Override
    public String toString() {
        return "VenueData{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", distance=" + distance +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                ", rating=" + rating +
                ", phone='" + phone + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}