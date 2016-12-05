package com.google.pizzahot.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Алексей on 24.11.2016.
 */

public class VenueGroup {

    public List<GroupItem> getGroupItems() {
        return groupItems;
    }

    public void setGroupItems(List<GroupItem> groupItems) {
        this.groupItems = groupItems;
    }

    @SerializedName("items")
    private List<GroupItem> groupItems;

}
