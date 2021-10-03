package com.starrynight.tourapiproject.postWritePage;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.postItemPage.PostWriteHashTagItem;
import com.starrynight.tourapiproject.postItemPage.PostWriteHashTagItemAdapter;
import com.starrynight.tourapiproject.postWritePage.postWriteRetrofit.PostHashTagParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddHashTagActivity extends AppCompatActivity {
    List<PostHashTagParams>postHashTagParams = new ArrayList<>();
    RecyclerView optionHashTagRecyclerView;
    TextView findHashTag;
    String optionHashTag;
    EditText editText;
    String[] optionHashTagList = new String[10];
    String[] hashTaglist =new String[22];
    String[] clicked = new String[22];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hash_tag);
        findHashTag = findViewById(R.id.findHashTag);

        Intent intent= getIntent();
        for(int i=0; i<22; i++){
            clicked[i]="";
        }

        Arrays.fill(hashTaglist, "");
        Arrays.fill(optionHashTagList, "");
        final List<String> finallist= new ArrayList<>();
        optionHashTagRecyclerView =findViewById(R.id.optionHashTagRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        optionHashTagRecyclerView.setLayoutManager(layoutManager);
        PostWriteHashTagItemAdapter adapter = new PostWriteHashTagItemAdapter();
        optionHashTagRecyclerView.addItemDecoration(new RecyclerViewDecoration(20));
        optionHashTagRecyclerView.setAdapter(adapter);
        Button plusHashTag = findViewById(R.id.finish_add_hashTag);
        plusHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<22; i++){
                    if (!clicked[i].isEmpty()){
                        PostHashTagParams postHashTagParam = new PostHashTagParams();
                        postHashTagParam.setHashTagName(clicked[i]);
                        postHashTagParams.add(postHashTagParam);
                    }
                }
                for (int i=0;i<hashTaglist.length;i++){
                    if (!clicked[i].isEmpty()){
                        if (hashTaglist[i]==""){
                            hashTaglist[i]=clicked[i];
                        }
                    }
                }
                Collections.addAll(finallist,hashTaglist);
                for (int i=21;i>=0;i--){
                    if (finallist.get(i)==""){
                        finallist.remove(i);
                    }
                }
                for (int i=9;i>=0;i--){
                    if (optionHashTagList[i]==""){
                        optionHashTagList = Arrays.copyOf(optionHashTagList, optionHashTagList.length-1);
                    }
                }
                intent.putExtra("postHashTagParams", (Serializable) postHashTagParams);
                intent.putExtra("hashTagList", (Serializable) finallist);
                intent.putExtra("optionHashTagList",optionHashTagList);
                setResult(3,intent);
                finish();
            }
        });
        Button back = findViewById(R.id.addHashTag_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editText = findViewById(R.id.findHashTag);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN&&keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!editText.getText().toString().equals("")) {
                        optionHashTag = editText.getText().toString();
                        for (int i = 0; i < optionHashTagList.length; i++) {
                            if (optionHashTagList[i] == "") {
                                optionHashTagList[i] = optionHashTag;
                                break;
                            }
                        }
                        adapter.addItem(new PostWriteHashTagItem(optionHashTag));
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    return false;
                }
                return true;
            }
        });
        Button add_hashTag= findViewById(R.id.addHashTag);
        add_hashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    optionHashTag = editText.getText().toString();
                    for (int i = 0; i < optionHashTagList.length; i++) {
                        if (optionHashTagList[i] == "") {
                            optionHashTagList[i] = optionHashTag;
                            break;
                        }
                    }
                    adapter.addItem(new PostWriteHashTagItem(optionHashTag));
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
    //recyclerview 간격
    public static class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

        private final int divRight;

        public RecyclerViewDecoration(int divRight)
        {

            this.divRight = divRight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.right = divRight;
        }
    }

    public void ClickEvent(View view) {
        Button button = (Button) view;

        if(button.getTag() == "isClicked"){
            button.setTag("");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.selectmyhashtag_hashtag_non));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id-1] = "";
        }
        else{
            button.setTag("isClicked");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.selectmyhashtag_hashtag));

            String viewId = view.getResources().getResourceEntryName(view.getId());
            int id = Integer.parseInt(viewId.substring(2));
            clicked[id-1] = button.getText().toString();
        }
    }
}