package com.starrynight.tourapiproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Layout_spot extends LinearLayout {
    ImageView PostButton;
    TextView PostTextview;

    public Layout_spot(Context context) {
        super(context);
        init(context);
    }

    public Layout_spot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_spot, this, true);
        PostTextview = findViewById(R.id.PostText);
        PostButton = findViewById(R.id.postimage);
    }

    public void setImage(int resId) {
        PostButton.setImageResource(resId);
    }

    public void setposttext(String tourname) {
        PostTextview.setText(tourname);
    }

}

