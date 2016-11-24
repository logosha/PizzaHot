package com.google.pizzahot.model;

import java.util.List;

/**
 * Created by Алексей on 24.11.2016.
 */

public class Photo {

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    private List<Group> groups;


}
