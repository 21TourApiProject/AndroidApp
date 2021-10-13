package com.starrynight.tourapiproject.myPage.myPageRetrofit;

import com.google.gson.annotations.SerializedName;

public class MyHashTag {

    @SerializedName("myHashTagListId")
    private Long myHashTagListId;
    @SerializedName("user")
    private User user;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("hashTagId")
    private Long hashTagId;
    @SerializedName("hashTagName")
    private String hashTagName;

    public MyHashTag() {
    }

    ;

}
