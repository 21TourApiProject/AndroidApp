package com.starrynight.tourapiproject.starPage.starItemPage;

public class StarItem {
    String celName;
    String celImage;

    public StarItem(String celName,String celImage) {
        this.celName = celName;
        this.celImage= celImage;
    }

    public String getCelName() {
        return celName;
    }

    public void setCelName(String celName) {
        this.celName = celName;
    }

    public String getCelImage() {
        return celImage;
    }

    public void setCelImage(String celImage) {
        this.celImage = celImage;
    }
}
