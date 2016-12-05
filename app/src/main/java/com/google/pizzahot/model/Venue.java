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

    private Contact contact;

    private String url;

    private double rating;

    private Photo photos;


    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Photo getPhoto() {
        return photos;
    }

    public void setPhoto(Photo photo) {
        this.photos = photo;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
