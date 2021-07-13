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

public class AddHashTagActivity extends AppCompatActivity {
    TextView findHashTag;
    LinearLayout dynamicLayout2;
    int numOfHT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hash_tag);

        findHashTag = findViewById(R.id.findHashTag);
        dynamicLayout2 = (LinearLayout)findViewById(R.id.dynamicLayout2);

        Button addHashTag = findViewById(R.id.addHashTag);
        addHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findHashTag != null){
                    addHashTag(findHashTag.getText().toString());
                }
            }
        });
    }

    private void addHashTag(String data) {
        numOfHT ++;
        TextView textView = new TextView(this);
        textView.setText(data);
        //String id = "@+id/hashTag"+ String.valueOf(numOfHT);
        textView.setId(numOfHT);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.post_write__edge));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        dynamicLayout2.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
    }
}