package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.starrynight.tourapiproject.R;

public class SearchLoadingDialog extends Dialog {

    public SearchLoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.map__loading);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
