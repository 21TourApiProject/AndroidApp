package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;

import java.io.Serializable;

/**
 * @className : UserParams.java
 * @description : 회원가입 시 사용하는 사용자 정보 클래스 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class UserParams implements Serializable {

    private String realName;

    private Boolean sex;

    private String birthDay;

    private String mobilePhoneNumber;

    private String email;

    private String password;

    private Boolean isMarketing;

    private Boolean kakao;

    public UserParams() {
    }

    public UserParams(String realName, Boolean sex, String birthDay, String email, String password, Boolean isMarketing, Boolean kakao) {
        this.realName = realName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
        this.mobilePhoneNumber = "";
        this.isMarketing = isMarketing;
        this.kakao = kakao;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
