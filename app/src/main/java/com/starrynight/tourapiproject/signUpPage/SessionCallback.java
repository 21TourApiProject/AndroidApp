package com.starrynight.tourapiproject.signUpPage;

import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.KakaoUserParams;

public class SessionCallback implements ISessionCallback {

    //로그인 성공
    @Override
    public void onSessionOpened() {
        requestMe();
    }


    //로그인 실패
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    //사용자 정보 요청
    public KakaoUserParams requestMe() {
        KakaoUserParams kakaoUserParams = new KakaoUserParams();
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                String id = String.valueOf(result.getId());
                UserAccount kakaoAccount = result.getKakaoAccount();
                if (kakaoAccount != null) {

                    // 이메일
                    String email = kakaoAccount.getEmail();
                    if (email != null) {
                        Log.d("KAKAO_API", "onSuccess:getEmail " + email);
                        kakaoUserParams.setEmail(email);
                    } else {
                        Log.d("KAKAO_API", "onSuccess:email null ");
                    }
                    if (kakaoAccount.getAgeRange() != null) {
                        Log.d("KAKAO_API", "onSuccess:get ageRange " + kakaoAccount.getAgeRange());
                        kakaoUserParams.setAgeRange(kakaoAccount.getAgeRange().toString());
                    }
                    if (kakaoAccount.getBirthday() != null) {
                        Log.d("KAKAO_API", "onSuccess:get Birthday " + kakaoAccount.getBirthday());
                        kakaoUserParams.setBirthDay(kakaoAccount.getBirthday());
                    }
                    if (kakaoAccount.getGender()!=null) {
                        Log.d("KAKAO_API", "onSuccess:get Gender " + kakaoAccount.getGender());
                        if(kakaoAccount.getGender()== Gender.FEMALE)
                            kakaoUserParams.setSex(true);
                        else if (kakaoAccount.getGender()== Gender.MALE)
                            kakaoUserParams.setSex(false);
                    }

                    Profile profile = kakaoAccount.getProfile();
                    if (profile ==null){
                        Log.d("KAKAO_API", "onSuccess:profile null ");
                    }else{
                        Log.d("KAKAO_API", "onSuccess:getProfileImageUrl "+profile.getProfileImageUrl());
                        kakaoUserParams.setProfileImage(profile.getProfileImageUrl());
                        Log.d("KAKAO_API", "onSuccess:getNickname "+profile.getNickname());
                        kakaoUserParams.setNickName(profile.getNickname());
                    }
                    if (email != null) {

                        Log.d("KAKAO_API", "onSuccess:email " + email);
                    } else {
                        Log.d("KAKAO_API", "onSuccess:email null ");
                    }

                    // 프로필
                    Profile _profile = kakaoAccount.getProfile();

                    if (_profile != null) {

                        Log.d("KAKAO_API", "nickname: " + _profile.getNickname());
                        kakaoUserParams.setNickName(profile.getNickname());
                        Log.d("KAKAO_API", "profile image: " + _profile.getProfileImageUrl());
                        kakaoUserParams.setProfileImage(profile.getProfileImageUrl());
                        Log.d("KAKAO_API", "thumbnail image: " + _profile.getThumbnailImageUrl());


                    } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                        // 동의 요청 후 프로필 정보 획득 가능

                    } else {
                        // 프로필 획득 불가
                    }
                }else{
                    Log.i("KAKAO_API", "onSuccess: kakaoAccount null");
                }

            }
        });
        return kakaoUserParams;
    }
}
