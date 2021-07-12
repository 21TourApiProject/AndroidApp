package com.starrynight.tourapiproject.postWritePage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

public class SearchObservingPointActivity extends AppCompatActivity {
    TextView findObservePoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_observing_point);

        findObservePoint = findViewById(R.id.findObservePoint);
        Button addObservePoint = findViewById(R.id.addObservePoint);
        addObservePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findObservePoint != null){

                }
            }
        });
    }

}