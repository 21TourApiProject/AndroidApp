package com.starrynight.tourapiproject.mapPage;

/**
* @className : Activities.java
* @description : 지도로 넘어오는 페이지 분류를 위한 ENUM
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public enum Activities {
    FILTER(222),
    SEARCHRESULT(333),
    MAINPOST(444),
    POST(555),
    MAP(666),
    OBSERVATION(777),
    TOURISTPOINT(888),
    SEARCH(999);

    private final int value;

    Activities(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
