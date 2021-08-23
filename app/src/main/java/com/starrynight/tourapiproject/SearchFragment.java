package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
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
        RecyclerView recyclerView = v.findViewById(R.id.nightpointRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        recyclerView.setAdapter(adapter);
        adapter.addItem(new post_point_item("게시글1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
        RecyclerView recyclerView2 = v.findViewById(R.id.distancepointRecyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        Post_point_item_Adapter adapter2 = new Post_point_item_Adapter();
        recyclerView2.setAdapter(adapter2);
        adapter2.addItem(new post_point_item("게시글1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter2.addItem(new post_point_item("게시글2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter2.addItem(new post_point_item("게시글3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
        adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                startActivity(intent);
            }
        });
        adapter2.setOnItemClicklistener(new OnPostPointItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), TouristPointActivity.class);
                intent.putExtra("contentId", 126273L);
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