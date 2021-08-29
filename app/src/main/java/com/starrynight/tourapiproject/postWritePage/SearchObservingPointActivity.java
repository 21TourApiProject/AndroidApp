package com.starrynight.tourapiproject.postWritePage;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostObservePointParams;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchObservingPointActivity extends AppCompatActivity {
    TextView findObservePoint;
    LinearLayout dynamicLayout;
    int numOfOP = 0;
    String observePoint;

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
                observePoint = ((EditText)(findViewById(R.id.findObservePoint))).getText().toString();
                PostObservePointParams postObservePointParams = new PostObservePointParams();
                postObservePointParams.setObservePointName(observePoint);
                Call<Void> call = RetrofitClient.getApiService().createPostObservePoint(postObservePointParams);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            System.out.println("관측지 생성 성공");
                        }
                        else{System.out.println("관측지 실패");}
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("관측지 실패 2");
                    }
                });
                Intent intent = new Intent();
                intent.putExtra("postObservePointParams", postObservePointParams);
                intent.putExtra("postObservePointName",observePoint);
                setResult(2,intent);
                finish();
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