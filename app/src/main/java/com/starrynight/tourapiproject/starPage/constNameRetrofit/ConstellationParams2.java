package com.starrynight.tourapiproject.starPage.constNameRetrofit;

/**
 * @className : ConstellationParams2
 * @description : 별자리 param 입니다.
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class ConstellationParams2 {
    private String constName;

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

    public ConstellationParams2(String constName) {
        this.constName = constName;
    }
}
