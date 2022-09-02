package com.starrynight.tourapiproject.mapPage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.starrynight.tourapiproject.R;

/**
* @className : LoadingDialog.java
* @description : 지도 로딩용 다이얼로그
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

class LoadingDialog extends Dialog {


    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.map__loading);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


}

