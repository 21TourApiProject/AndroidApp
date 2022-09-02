package com.starrynight.tourapiproject.retrofitConfig;

import com.starrynight.tourapiproject.BuildConfig;

/**
* @className : TaskServer.java
* @description : Retrofit연결 서버주소 공통관리
* @modification : gyul chyoung (2022-08-30) 주석생성
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석생성
 */
public class TaskServer {

    public static final String v1Server = BuildConfig.SPRING_SERVER_V1;
//    public static final String v1Server = "http://localhost:8080/v1/";

    public static final String kkoMapURL = "https://dapi.kakao.com/";

    public static final String fineDustURL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/";

    public static final String openWeatherURL = "https://api.openweathermap.org/data/2.5/";
}
