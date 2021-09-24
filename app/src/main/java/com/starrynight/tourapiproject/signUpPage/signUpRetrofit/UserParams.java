package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;

import java.io.Serializable;

public class UserParams implements Serializable {

    private String realName;

    private Boolean sex;

    private String birthDay;

    private String mobilePhoneNumber;

    private String email;

    private String password;

    private Boolean isMarketing;

    public UserParams(){}

    public UserParams(String realName, Boolean sex, String birthDay, String email, String password, Boolean isMarketing) {
        this.realName = realName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
        this.mobilePhoneNumber = "";
        this.isMarketing = isMarketing;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
