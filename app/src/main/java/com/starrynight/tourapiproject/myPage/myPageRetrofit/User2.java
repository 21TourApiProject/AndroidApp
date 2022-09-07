package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import java.io.Serializable;

/**
 * @className : User2.java
 * @description : 프로필과 관련한 사용자 정보 클래스 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class User2 implements Serializable {

    String nickName;
    String profileImage;

    public User2() {
    }

    ;

    public User2(String nickName, String profileImage) {
        this.nickName = nickName;
        this.profileImage = profileImage;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
