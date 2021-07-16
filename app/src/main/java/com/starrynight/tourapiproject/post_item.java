package com.starrynight.tourapiproject;

public class post_item {
    String hash;
    String hash2;

    public post_item(String hash, String hash2) {
        this.hash = hash;
        this.hash2 = hash2;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash2() {
        return hash2;
    }

    public void setHash2(String hash2) {
        this.hash2 = hash2;
    }
}
