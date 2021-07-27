package com.starrynight.tourapiproject.signUpPage;

public class UserParams {

    private String realName;

    private Boolean sex;

    private String birthDay;

    //private String mobilePhoneNumber;

    private String email;

    private String loginId;

    private String password;

    public UserParams(){}

    public UserParams(String realName, Boolean sex, String birthDay, String email, String loginId, String password) {
        this.realName = realName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
    }
}
