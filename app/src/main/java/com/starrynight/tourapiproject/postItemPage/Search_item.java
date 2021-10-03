package com.starrynight.tourapiproject.postItemPage;

public class Search_item {
    String itemName;
    String address;

    public Search_item(String itemName, String address) {
        this.itemName = itemName;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
