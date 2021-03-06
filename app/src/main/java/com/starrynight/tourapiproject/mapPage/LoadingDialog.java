package com.starrynight.tourapiproject.mapPage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.starrynight.tourapiproject.R;

class LoadingDialog extends Dialog {


    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.map__loading);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


}

