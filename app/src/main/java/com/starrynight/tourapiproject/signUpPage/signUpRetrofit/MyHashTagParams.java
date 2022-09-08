package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;

import java.io.Serializable;

/**
 * @className : MyHashTagParams.java
 * @description : 회원가입 시 선택하는 선호 해시태그 정보 클래스 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class MyHashTagParams implements Serializable {

    private String hashTagName;

    public MyHashTagParams() {
    }

    public MyHashTagParams(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }

}
