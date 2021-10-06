package com.starrynight.tourapiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.MapFragment;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.postItemPage.OnPostPointItemClickListener;
import com.starrynight.tourapiproject.postItemPage.Post_point_item_Adapter;
import com.starrynight.tourapiproject.postItemPage.post_point_item;
import com.starrynight.tourapiproject.searchPage.FilterFragment;
import com.starrynight.tourapiproject.searchPage.SearchResultFragment;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

public class SearchFragment extends Fragment {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        ((MainActivity)getActivity()).showBottom();

        ((MainActivity)getActivity()).setFilter(null);

        SearchView searchView = v.findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("검색어를 입력하세요");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putInt("type",2);
                bundle.putString("keyword", query);

                Fragment resultfragment = new SearchResultFragment();
                resultfragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_view, resultfragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //지도 페이지로
        ImageButton map_btn = (ImageButton) v.findViewById(R.id.mapBtn);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putSerializable("FromWhere", Activities.SEARCH);//번들에 넘길 값 저장

                MapFragment mapFragment = new MapFragment();
                mapFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_view, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
//                ((MainActivity)getActivity()).replaceFragment(mapFragment);
            }
        });

        //필터 고르는 페이지로
        ImageButton filter_btn = (ImageButton) v.findViewById(R.id.filterBtn);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("fromWhere", Activities.SEARCH);

                FilterFragment filterFragment = new FilterFragment();
                filterFragment.setArguments(bundle);
                ((MainActivity) getActivity()).setFilter(filterFragment);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.main_view, filterFragment);
                transaction.replace(R.id.main_view,filterFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        if (getArguments() != null)
        {
            int type = getArguments().getInt("type");
            if (type == 0) {
                ((MainActivity) getActivity()).showBottom();
            }
        }

        //요즘 핫한 밤하늘 명소
        RecyclerView recyclerView = v.findViewById(R.id.hotpointRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Post_point_item_Adapter adapter = new Post_point_item_Adapter();
        recyclerView.setAdapter(adapter);
        adapter.addItem(new post_point_item("게시글1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter.addItem(new post_point_item("게시글3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));
        adapter.setOnItemClicklistener(new OnPostPointItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                intent.putExtra("observationId", 1L);
                startActivity(intent);
            }
        });
        //나와 가까운 밤하늘 명소
        RecyclerView recyclerView2 = v.findViewById(R.id.nearPointRecyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        Post_point_item_Adapter adapter2 = new Post_point_item_Adapter();
        recyclerView2.setAdapter(adapter2);
        adapter2.addItem(new post_point_item("게시글1","https://cdn.pixabay.com/photo/2018/08/11/20/37/cathedral-3599450_960_720.jpg"));
        adapter2.addItem(new post_point_item("게시글2","https://cdn.pixabay.com/photo/2018/07/15/23/22/prague-3540883_960_720.jpg"));
        adapter2.addItem(new post_point_item("게시글3","https://cdn.pixabay.com/photo/2019/12/13/07/35/city-4692432_960_720.jpg"));

        adapter2.setOnItemClicklistener(new OnPostPointItemClickListener() {
            @Override
            public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), TouristPointActivity.class);
                intent.putExtra("contentId", 125266L);
                startActivity(intent);
            }
        });
        return v;
    }
}