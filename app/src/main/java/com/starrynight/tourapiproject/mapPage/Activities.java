package com.starrynight.tourapiproject.mapPage;

public enum Activities {
    FILTER(222),
    SEARCHRESULT(333),
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
