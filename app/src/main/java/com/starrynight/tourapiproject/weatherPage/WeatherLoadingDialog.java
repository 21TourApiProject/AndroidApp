package com.starrynight.tourapiproject.weatherPage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.starrynight.tourapiproject.R;

public class WeatherLoadingDialog extends Dialog {
    public WeatherLoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.map__loading);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
