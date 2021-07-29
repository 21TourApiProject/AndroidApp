package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starrynight.tourapiproject.postItemPage.OnPostItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.postPage.ImageSliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StarFragment extends Fragment {
    private ViewPager2 sliderViewPager;
    private LinearLayout indicator;
    private String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg",
            "https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639125_960_720.jpg",
            "https://cdn.pixabay.com/photo/2015/03/17/15/25/horoscope-677900_960_720.jpg",
            "https://cdn.pixabay.com/photo/2015/02/17/08/24/horoscope-639123_960_720.jpg"
    };


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StarFragment newInstance(String param1, String param2) {
        StarFragment fragment = new StarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_star, container, false);

        RecyclerView recyclerView =v.findViewById(R.id.recyclerview_star);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        recyclerView.setAdapter(adapter);

        adapter.addItem(new post_point_item("별자리 1","https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639127_960_720.jpg"));
        adapter.addItem(new post_point_item("별자리 2","https://cdn.pixabay.com/photo/2015/02/17/08/25/horoscope-639125_960_720.jpg"));
        adapter.addItem(new post_point_item("별자리 3","https://cdn.pixabay.com/photo/2015/02/17/08/24/horoscope-639123_960_720.jpg"));
        adapter.setOnItemClicklistener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), StarActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter((adapter));

        sliderViewPager = v.findViewById(R.id.starslider);
        indicator = v.findViewById(R.id.indicator_star);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(getContext(), images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(images.length);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.post__indicator_inactive));
            indicators[i].setLayoutParams(params);
            indicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = indicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.post__indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.post__indicator_inactive
                ));
            }
        }
    }
}