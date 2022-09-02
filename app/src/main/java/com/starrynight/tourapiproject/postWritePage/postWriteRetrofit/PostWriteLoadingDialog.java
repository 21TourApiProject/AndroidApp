package com.starrynight.tourapiproject.postWritePage.postWriteRetrofit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.starrynight.tourapiproject.R;
/**
* @className : PostWriteLoadingDialog
* @description : 게시물 작성 완료 전 로딩 이미지 출력하는 클래스 입니다.
* @modification : 2022-09-02 (jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-09-02
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-09-02      jinhyeok       주석 수정

 */
public class PostWriteLoadingDialog extends Dialog {
    public PostWriteLoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.map__loading);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
