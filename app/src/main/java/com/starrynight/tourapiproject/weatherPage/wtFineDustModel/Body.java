package com.starrynight.tourapiproject.weatherPage.wtFineDustModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Body {
    @SerializedName("items")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
