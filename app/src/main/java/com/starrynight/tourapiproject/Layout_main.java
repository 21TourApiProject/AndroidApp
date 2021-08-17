package com.starrynight.tourapiproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Layout_main extends RelativeLayout {

    TextView Button;
    TextView Button2;

    public Layout_main(Context context) {
        super(context);
        init (context);
    }

    public Layout_main(Context context, AttributeSet attrs) {
        super(context, attrs);
        init (context);
    }
    public void init (Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_main, this,true);
        Button =findViewById(R.id.hash__button);
        Button2 = findViewById(R.id.hash__button2);
    }

    public void setbutton(String hash){
        Button.setText(hash);
    }
    public void setButton2(String hash2){
        Button2.setText(hash2);
    }
}
