package com.starrynight.tourapiproject.mapPage;

public enum Activities {
    MAINPOST(444),
    POST(555),
    MAP(666),
    OBSERVATION(777),
    TOURISTPOINT(888),
    SEARCH(999)
    ;

    private final int value;
    Activities(int value) {
        this.value = value;
    }
    public int getValue() { return value; }
}
