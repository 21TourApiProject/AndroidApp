package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;
import com.starrynight.tourapiproject.mapPage.MapFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        Button maptest_btn = (Button) v.findViewById(R.id.test_map);
        maptest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance());
            }
        });
        Button button  =(Button)v.findViewById(R.id.Touristspot_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TouristPointActivity.class);
                startActivity(intent);
            }
        });
        Button observation_btn = (Button)v.findViewById(R.id.observation_button);
        observation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),ObservationsiteActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}