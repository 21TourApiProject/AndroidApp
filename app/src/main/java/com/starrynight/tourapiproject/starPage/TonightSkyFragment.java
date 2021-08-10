package com.starrynight.tourapiproject.starPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.starPage.starItemPage.OnStarItemClickListener;
import com.starrynight.tourapiproject.starPage.starItemPage.StarItem;
import com.starrynight.tourapiproject.starPage.starItemPage.StarViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class TonightSkyFragment extends Fragment {
    //bottomSheet 관련
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    private EditText editText;

    //recyclerview 관련
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tonight_sky, container, false);

        // bottomSheet 설정
        editText = v.findViewById(R.id.edit_search);
        editText.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        editText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        bottomSheetBehavior.setPeekHeight(editText.getBottom()+ 50);
                    }
                }
        );

        bottomSheet = v.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setPeekHeight(editText.getBottom());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        // 뒤로 가기 버튼 클릭 이벤트
        ImageButton backBtn =(ImageButton) v.findViewById(R.id.star_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // recyclerview 설정
        recyclerView = v.findViewById(R.id.today_cel_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3) ;
        recyclerView.setLayoutManager(layoutManager);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);

        StarViewAdapter adapter = new StarViewAdapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new StarItem("별자리1", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리1", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리1", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리1", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new StarItem("별자리", "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));

        recyclerView.setAdapter(adapter);

//        adapter.setOnItemClickListener(new OnStarItemClickListener() {
//            @Override
//            public void onItemClick(StarViewAdapter.ViewHolder holder, View view, int position) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), StarActivity.class);
//                startActivity(intent);
//            }
//        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}