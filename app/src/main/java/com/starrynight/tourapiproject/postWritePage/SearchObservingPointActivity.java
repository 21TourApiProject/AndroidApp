package com.starrynight.tourapiproject.postWritePage;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

public class SearchObservingPointActivity extends AppCompatActivity {
    TextView findObservePoint;
    LinearLayout dynamicLayout;
    int numOfOP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_observing_point);

        findObservePoint = findViewById(R.id.findObservePoint);
        dynamicLayout = (LinearLayout)findViewById(R.id.dynamicLayout2);

        Button addObservePoint = findViewById(R.id.addObservePoint);
        addObservePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findObservePoint != null){
                    addObservePoint(findObservePoint.getText().toString());
                }
            }
        });
    }

    public void addObservePoint(String data) {
        numOfOP ++;
        TextView textView = new TextView(this);
        textView.setText(data);
        //String id = "@+id/observePoint"+ String.valueOf(numOfOP);
        textView.setId(numOfOP);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.post_write__edge));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        dynamicLayout.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));

    }

}