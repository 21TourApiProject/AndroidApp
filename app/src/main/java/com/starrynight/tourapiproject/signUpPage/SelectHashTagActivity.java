package com.starrynight.tourapiproject.signUpPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postPage.OnRelatePostItemClickListener;
import com.starrynight.tourapiproject.postPage.RelatePost;
import com.starrynight.tourapiproject.postPage.RelatePostAdapter;

public class SelectHashTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hash_tag);

        RecyclerView recyclerView = findViewById(R.id.selectHashTag);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        HashTagAdapter adapter = new HashTagAdapter();
        adapter.addItem(new HashTag("# 조용한"));
        adapter.addItem(new HashTag("# 활기찬"));
        adapter.addItem(new HashTag("# 어두운"));
        adapter.addItem(new HashTag("# 밝은"));
        adapter.addItem(new HashTag("# 교통이 편한"));
        adapter.addItem(new HashTag("# 사람이 적은"));
        adapter.addItem(new HashTag("# 저렴한"));


        recyclerView.setAdapter(adapter);

        adapter.setOnHashTagItemClickListener(new OnHashTagItemClickListener() {
            @Override
            public void onItemClick(HashTagAdapter.ViewHolder holder, View view, int position) {
                HashTag item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "클릭됨 : " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}