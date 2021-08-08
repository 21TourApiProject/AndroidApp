package com.starrynight.tourapiproject.signUpPage.signUpRetrofit;

import java.io.Serializable;

public class UserParams implements Serializable {

    private String realName;

    private Boolean sex;

    private String birthDay;

    private String mobilePhoneNumber;

    private String email;

    private String password;

    public UserParams(){}

    public UserParams(String realName, Boolean sex, String birthDay, String email, String password) {
        this.realName = realName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
        this.mobilePhoneNumber = "";
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
