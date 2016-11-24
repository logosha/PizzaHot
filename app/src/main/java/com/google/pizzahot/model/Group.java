package com.google.pizzahot.model;

import java.util.List;

/**
 * Created by Алексей on 24.11.2016.
 */

public class Group {
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private List<Item> items;
}
