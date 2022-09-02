package com.starrynight.tourapiproject.weatherPage.wtObFit;

/**
 * @className : ObFitItem
 * @description : 날씨페이지 별 관측적합도 Item입니다.
 * @modification : 2022-09-03 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-03
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
hyeonz       2022-09-03   주석추가
 */
public class ObFitItem {
    private int obFitImage;
    private String obFitTime;
    private String obFitPercent;

    public ObFitItem(int obFitImage, String obFitTime, String obFitPercent) {
        this.obFitImage = obFitImage;
        this.obFitTime = obFitTime;
        this.obFitPercent = obFitPercent;
    }

    public int getObFitImage() {
        return obFitImage;
    }

    public String getObFitPercent() {
        return obFitPercent;
    }

    public String getObFitTime() {
        return obFitTime;
    }

    public void setObFitImage(int obFitImage) {
        this.obFitImage = obFitImage;
    }

    public void setObFitPercent(String obFitPercent) {
        this.obFitPercent = obFitPercent;
    }

    public void setObFitTime(String obFitTime) {
        this.obFitTime = obFitTime;
    }
}
