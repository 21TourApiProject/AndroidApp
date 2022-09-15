package com.starrynight.tourapiproject.starPage.horPageRetriofit;

import com.google.gson.annotations.SerializedName;

/**
 * @className : Horoscope
 * @description : 별자리 운세 DTO입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class Horoscope {
    @SerializedName("horId")
    private Long horId;

    @SerializedName("horImage")
    private String horImage;

    @SerializedName("horEngTitle")
    private String horEngTitle;

    @SerializedName("horKrTitle")
    private String horKrTitle;

    @SerializedName("horPeriod")
    private String horPeriod;

    @SerializedName("horDesc1")
    private String horDesc1;

    @SerializedName("horDesc2")
    private String horDesc2;

    @SerializedName("horDesc3")
    private String horDesc3;

    @SerializedName("horDesc4")
    private String horDesc4;

    @SerializedName("horDesc5")
    private String horDesc5;

    @SerializedName("horDesc6")
    private String horDesc6;

    @SerializedName("horDesc7")
    private String horDesc7;

    @SerializedName("horDesc8")
    private String horDesc8;

    @SerializedName("horDesc9")
    private String horDesc9;

    @SerializedName("horDesc10")
    private String horDesc10;

    @SerializedName("horDesc11")
    private String horDesc11;

    @SerializedName("horDesc12")
    private String horDesc12;

    @SerializedName("horGuard")
    private String horGuard;

    @SerializedName("horPersonality")
    private String horPersonality;

    @SerializedName("horTravel")
    private String horTravel;

    public Long getHorId() {
        return horId;
    }

    public String getHorImage() {
        return horImage;
    }

    public String getHorEngTitle() {
        return horEngTitle;
    }

    public String getHorKrTitle() {
        return horKrTitle;
    }

    public String getHorPeriod() {
        return horPeriod;
    }

    public String getHorDesc1() {
        return horDesc1;
    }

    public String getHorDesc2() {
        return horDesc2;
    }

    public String getHorDesc3() {
        return horDesc3;
    }

    public String getHorDesc4() {
        return horDesc4;
    }

    public String getHorDesc5() {
        return horDesc5;
    }

    public String getHorDesc6() {
        return horDesc6;
    }

    public String getHorDesc7() {
        return horDesc7;
    }

    public String getHorDesc8() {
        return horDesc8;
    }

    public String getHorDesc9() {
        return horDesc9;
    }

    public String getHorDesc10() {
        return horDesc10;
    }

    public String getHorDesc11() {
        return horDesc11;
    }

    public String getHorDesc12() {
        return horDesc12;
    }

    public String getHorGuard() {
        return horGuard;
    }

    public String getHorPersonality() {
        return horPersonality;
    }

    public String getHorTravel() {
        return horTravel;
    }
}
