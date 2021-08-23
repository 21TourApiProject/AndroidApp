package com.starrynight.tourapiproject.observationPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.Observation;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.ObserveHashTag;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.postPage.PostActivity;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObservationsiteActivity extends AppCompatActivity {

    private static final String TAG = "observation ";
    Observation observation;
    List<String> observeHashTags;
    private RecyclerHashTagAdapter recyclerHashTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observationsite);

        long observationId = 1;

        Call<Observation> call1 = RetrofitClient.getApiService().getObservation(observationId);
        call1.enqueue(new Callback<Observation>() {
            @Override
            public void onResponse(Call<Observation> call, Response<Observation> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "관측지 호출 성공");
                    observation = response.body();

                    TextView name = findViewById(R.id.obs_name_txt);
                    name.setText(observation.getObservationName());
                    TextView intro = findViewById(R.id.obs_intro_txt);
                    intro.setText(observation.getIntro());
                    TextView obs_type = findViewById(R.id.obs_type_txt);
                    obs_type.setText(observation.getObserveType());
                    TextView outline = findViewById(R.id.obs_outline_txt);
                    outline.setText(observation.getOutline());

                    if (observation.getNature()) {
                        RelativeLayout nature_layout = findViewById(R.id.obs_fornature_layout);
                        nature_layout.setVisibility(View.VISIBLE);

                        LinearLayout operating_layout = findViewById(R.id.obs_foroperating_layout);
                        operating_layout.setVisibility(View.GONE);
                        TextView parking_img_btn = findViewById(R.id.obs_parkingimg_btn);
                        if (observation.getParkingImg() == null)
                            parking_img_btn.setVisibility(View.GONE);

                        TextView nature_parking = findViewById(R.id.obs_nature_parking_txt);
                        nature_parking.setText(observation.getParking());
                        TextView nature_address = findViewById(R.id.obs_nature_address_txt);
                        nature_address.setText(observation.getAddress());
                        TextView nature_guide = findViewById(R.id.obs_nature_guide_txt);
                        nature_guide.setText(observation.getGuide());



                    } else {
                        TextView address = findViewById(R.id.obs_address_txt);
                        address.setText(observation.getAddress());
                        TextView phonenumber = findViewById(R.id.obs_phonenumber_txt);
                        phonenumber.setText(observation.getPhoneNumber());
                        TextView operatinghour = findViewById(R.id.obs_address_txt);
                        operatinghour.setText(observation.getOperatingHour());
                        TextView closedday = findViewById(R.id.obs_closedday_txt);
                        closedday.setText(observation.getClosedDay());
                        TextView entrancefee = findViewById(R.id.obs_entrancefee_txt);
                        entrancefee.setText(observation.getEntranceFee());
                        TextView guide = findViewById(R.id.obs_guide_txt);
                        guide.setText(observation.getGuide());
                        TextView parking = findViewById(R.id.obs_parking_txt);
                        parking.setText(observation.getParking());
                        TextView link = findViewById(R.id.obs_url_txt);
                        link.setText(observation.getLink());

                        ToggleButton moreinfo_btn = findViewById(R.id.obs_moreinfo_btn);
                        RelativeLayout moreinfo_layout = findViewById(R.id.obd_moreinfo_layout);
                        moreinfo_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    moreinfo_layout.setVisibility(View.VISIBLE);
                                } else {
                                    moreinfo_layout.setVisibility(View.GONE);
                                }
                            }
                        });
                    }


                    //해쉬태그 리사이클러 설정
                    initHashtagRecycler();

                    Call<List<String>> call2 = RetrofitClient.getApiService().getObserveHashTags(observationId);
                    call2.enqueue(new Callback<List<String>>(){

                                     @Override
                                     public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                         if (response.isSuccessful()) {
                                             Log.d(TAG, "관측지 해쉬태그 호출 성공");
                                             observeHashTags = response.body();

                                             for (String p : observeHashTags) {
                                                 RecyclerHashTagItem item = new RecyclerHashTagItem();
                                                 item.setHashtagName(p);

                                                 recyclerHashTagAdapter.addItem(item);
                                             }
                                             recyclerHashTagAdapter.notifyDataSetChanged();

                                         } else {
                                             Log.e(TAG, "관측지 해쉬태그 호출 실패");
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<List<String>> call, Throwable t) {
                                         Log.e(TAG, "연결실패" + t.getMessage());

                                     }
                                 });


                } else {
                    Log.e(TAG, "관측지 호출 실패");
                }
            }

            @Override
            public void onFailure(Call<Observation> call, Throwable t) {
                Log.e(TAG, "연결실패" + t.getMessage());
            }

        });

//        RecyclerView recyclerView = findViewById(R.id.obv_recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
//        recyclerView.setAdapter(adapter);
//
//        adapter.addItem(new post_point_item("게시글1", "https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
//        adapter.addItem(new post_point_item("게시글2", "https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
//        adapter.addItem(new post_point_item("게시글3", "https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
//
//        adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
//            @Override
//            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
//                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
//                startActivity(intent);
//            }
//        });

        Button heart_btn = findViewById(R.id.obs_save_btn);
        heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        Button back_btn = findViewById(R.id.obs_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        Button link_btn = findViewById(R.id.obvlink_btn);
//        link_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kasi.re.kr/kor/index"));
//                startActivity(intent);
//            }
//        });

        ImageView obv_btn = findViewById(R.id.obvimagebutton);
        ImageView obv_btn2 = findViewById(R.id.obvimagebutton2);
        ImageView obv_btn3 = findViewById(R.id.obvimagebutton3);
        ImageView obv_btn4 = findViewById(R.id.obvimagebutton4);
        LinearLayout linearLayout = findViewById(R.id.obvlayout);
        LinearLayout linearLayout2 = findViewById(R.id.obvlayout2);
        LinearLayout linearLayout3 = findViewById(R.id.obvlayout3);
        LinearLayout linearLayout4 = findViewById(R.id.obvlayout4);

        obv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(true);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(true);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);
                linearLayout4.setVisibility(View.INVISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(true);
                obv_btn4.setSelected(false);
            }
        });
        obv_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                linearLayout.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);
                linearLayout4.setVisibility(View.VISIBLE);
                obv_btn.setSelected(false);
                obv_btn2.setSelected(false);
                obv_btn3.setSelected(false);
                obv_btn4.setSelected(true);
            }
        });

    }

    public void onClick(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (v.getId()){
            case R.id.obs_parkingimg_btn:

                break;
            case R.id.obs_url_txt:
                intent.setData(Uri.parse(observation.getLink()));
                startActivity(intent);
                break;
            case R.id.obs_outline_btn:
                break;

        }
    }

    private void initHashtagRecycler(){
        //해쉬태그 리사이클러 초기화
        RecyclerView hashTagsrecyclerView = findViewById(R.id.obs_hashtags_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        hashTagsrecyclerView.setLayoutManager(linearLayoutManager);

        recyclerHashTagAdapter = new RecyclerHashTagAdapter();
        hashTagsrecyclerView.setAdapter(recyclerHashTagAdapter);
    }

}