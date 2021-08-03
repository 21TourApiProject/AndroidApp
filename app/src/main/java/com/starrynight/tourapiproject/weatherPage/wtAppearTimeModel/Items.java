package com.starrynight.tourapiproject.weatherPage.wtAppearTimeModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Items")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

