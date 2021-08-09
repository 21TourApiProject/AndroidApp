package com.starrynight.tourapiproject.postWritePage;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostHashTagParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.starPage.StarActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHashTagActivity extends AppCompatActivity {
    List<PostHashTagParams>postHashTagParams = new ArrayList<>();
    TextView findHashTag;
    LinearLayout dynamicLayout2;
    int numOfHT = 0;
    String PostHashTags;

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
                PostHashTags = ((TextView)(findViewById(R.id.findHashTag))).getText().toString();
                PostHashTagParams postHashTagParam= new PostHashTagParams();
                postHashTagParam.setHashTagName(PostHashTags);
                postHashTagParams.add(postHashTagParam);
                Intent intent = new Intent();
                intent.putExtra("postHashTagParams",postHashTagParam);
                setResult(3,intent);
                finish();
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