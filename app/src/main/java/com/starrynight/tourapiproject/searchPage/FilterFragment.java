package com.starrynight.tourapiproject.searchPage;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.mapPage.Activities;
import com.starrynight.tourapiproject.mapPage.MapFragment;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @className : FilterFragment.java
 * @description : 검색 필터 Fragment 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class FilterFragment extends Fragment {

    int[] filter = {0, 0};
    ArrayList<Integer> area = new ArrayList<Integer>(Collections.nCopies(17, 0));
    ArrayList<Integer> hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));

    LinearLayout areaLayout;
    LinearLayout hashTagLayout;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        ((MainActivity) getActivity()).showOffBottom();

        //x버튼
        ImageButton backFilter = v.findViewById(R.id.backFilter);
        backFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        areaLayout = v.findViewById(R.id.areaLayout);
        hashTagLayout = v.findViewById(R.id.hashTagLayout);

        //지역
        Button areaBtn = v.findViewById(R.id.areaBtn);
        FrameLayout areaLinearLayout = v.findViewById(R.id.areaLinearLayout);
        areaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter[0] == 0) {
                    filter[0] = 1;
                    areaBtn.setRotation(90);
                    areaLayout.setVisibility(View.VISIBLE);
                } else {
                    filter[0] = 0;
                    areaBtn.setRotation(0);
                    areaLayout.setVisibility(View.GONE);
                }
            }
        });

        //여행 테마
        Button hashTagBtn = v.findViewById(R.id.hashTagBtn);
        FrameLayout hashTagLinearLayout = v.findViewById(R.id.hashTagLinearLayout);
        hashTagLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter[1] == 0) {
                    filter[1] = 1;
                    hashTagBtn.setRotation(90);
                    hashTagLayout.setVisibility(View.VISIBLE);
                } else {
                    filter[1] = 0;
                    hashTagBtn.setRotation(0);
                    hashTagLayout.setVisibility(View.GONE);
                }
            }
        });

        //완료 버튼
        Button submitFilter = v.findViewById(R.id.submitFilter);
        submitFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putIntegerArrayList("area", area);
                bundle.putIntegerArrayList("hashTag", hashTag);
                String keyword;

                Activities fromWhere;
                if (getArguments() != null) {
                    fromWhere = (Activities) getArguments().getSerializable("fromWhere");
                    Fragment filterFragment, searchResultFragment;
                    filterFragment = ((MainActivity) getActivity()).getFilter();
                    if (fromWhere == Activities.SEARCH) {
                        keyword = null;
                        bundle.putString("keyword", keyword);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        searchResultFragment = new SearchResultFragment();
                        searchResultFragment.setArguments(bundle);
                        transaction.add(R.id.main_view, searchResultFragment);
                        ((MainActivity) getActivity()).setSearchResult(searchResultFragment);
                        transaction.commit();
                        if (filterFragment != null) {
                            getFragmentManager().beginTransaction().hide(filterFragment).commit();
                        }

                    } else if (fromWhere == Activities.SEARCHRESULT) {
                        keyword = getArguments().getString("keyword");
                        bundle.putString("keyword", keyword);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        searchResultFragment = new SearchResultFragment();
                        searchResultFragment.setArguments(bundle);
                        transaction.add(R.id.main_view, searchResultFragment);
                        ((MainActivity) getActivity()).setSearchResult(searchResultFragment);
                        transaction.commit();
                        if (filterFragment != null) {
                            getFragmentManager().beginTransaction().hide(filterFragment).commit();
                        }

                    } else if (fromWhere == Activities.MAP) {
                        bundle.putSerializable("FromWhere", Activities.FILTER);
                        keyword = getArguments().getString("keyword");
                        bundle.putString("keyword", keyword);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        Fragment mapfragment = new MapFragment();
                        mapfragment.setArguments(bundle);
                        transaction.add(R.id.main_view, mapfragment);
                        ((MainActivity) getActivity()).setMap(mapfragment);
                        transaction.commit();
                        if (filterFragment != null) {
                            getFragmentManager().beginTransaction().hide(filterFragment).commit();
                        }
                    }
                }

            }
        });

        Button areaBtn1 = v.findViewById(R.id.areaBtn1);
        Button areaBtn2 = v.findViewById(R.id.areaBtn2);
        Button areaBtn3 = v.findViewById(R.id.areaBtn3);
        Button areaBtn4 = v.findViewById(R.id.areaBtn4);
        Button areaBtn5 = v.findViewById(R.id.areaBtn5);
        Button areaBtn6 = v.findViewById(R.id.areaBtn6);
        Button areaBtn7 = v.findViewById(R.id.areaBtn7);
        Button areaBtn8 = v.findViewById(R.id.areaBtn8);
        Button areaBtn9 = v.findViewById(R.id.areaBtn9);
        Button areaBtn10 = v.findViewById(R.id.areaBtn10);
        Button areaBtn11 = v.findViewById(R.id.areaBtn11);
        Button areaBtn12 = v.findViewById(R.id.areaBtn12);
        Button areaBtn13 = v.findViewById(R.id.areaBtn13);
        Button areaBtn14 = v.findViewById(R.id.areaBtn14);
        Button areaBtn15 = v.findViewById(R.id.areaBtn15);
        Button areaBtn16 = v.findViewById(R.id.areaBtn16);
        Button areaBtn17 = v.findViewById(R.id.areaBtn17);
        LinearLayout area1 = v.findViewById(R.id.area1);
        LinearLayout area2 = v.findViewById(R.id.area2);
        LinearLayout area3 = v.findViewById(R.id.area3);
        LinearLayout area4 = v.findViewById(R.id.area4);
        LinearLayout area5 = v.findViewById(R.id.area5);
        LinearLayout area6 = v.findViewById(R.id.area6);
        LinearLayout area7 = v.findViewById(R.id.area7);
        LinearLayout area8 = v.findViewById(R.id.area8);
        LinearLayout area9 = v.findViewById(R.id.area9);
        LinearLayout area10 = v.findViewById(R.id.area10);
        LinearLayout area11 = v.findViewById(R.id.area11);
        LinearLayout area12 = v.findViewById(R.id.area12);
        LinearLayout area13 = v.findViewById(R.id.area13);
        LinearLayout area14 = v.findViewById(R.id.area14);
        LinearLayout area15 = v.findViewById(R.id.area15);
        LinearLayout area16 = v.findViewById(R.id.area16);
        LinearLayout area17 = v.findViewById(R.id.area17);

        Button hashTagBtn1 = v.findViewById(R.id.hashTagBtn1);
        Button hashTagBtn2 = v.findViewById(R.id.hashTagBtn2);
        Button hashTagBtn3 = v.findViewById(R.id.hashTagBtn3);
        Button hashTagBtn4 = v.findViewById(R.id.hashTagBtn4);
        Button hashTagBtn5 = v.findViewById(R.id.hashTagBtn5);
        Button hashTagBtn6 = v.findViewById(R.id.hashTagBtn6);
        Button hashTagBtn7 = v.findViewById(R.id.hashTagBtn7);
        Button hashTagBtn8 = v.findViewById(R.id.hashTagBtn8);
        Button hashTagBtn9 = v.findViewById(R.id.hashTagBtn9);
        Button hashTagBtn10 = v.findViewById(R.id.hashTagBtn10);
        Button hashTagBtn11 = v.findViewById(R.id.hashTagBtn11);
        Button hashTagBtn12 = v.findViewById(R.id.hashTagBtn12);
        Button hashTagBtn13 = v.findViewById(R.id.hashTagBtn13);
        Button hashTagBtn14 = v.findViewById(R.id.hashTagBtn14);
        Button hashTagBtn15 = v.findViewById(R.id.hashTagBtn15);
        Button hashTagBtn16 = v.findViewById(R.id.hashTagBtn16);
        Button hashTagBtn17 = v.findViewById(R.id.hashTagBtn17);
        Button hashTagBtn18 = v.findViewById(R.id.hashTagBtn18);
        Button hashTagBtn19 = v.findViewById(R.id.hashTagBtn19);
        Button hashTagBtn20 = v.findViewById(R.id.hashTagBtn20);
        Button hashTagBtn21 = v.findViewById(R.id.hashTagBtn21);
        Button hashTagBtn22 = v.findViewById(R.id.hashTagBtn22);
        LinearLayout hashTag1 = v.findViewById(R.id.hashTag1);
        LinearLayout hashTag2 = v.findViewById(R.id.hashTag2);
        LinearLayout hashTag3 = v.findViewById(R.id.hashTag3);
        LinearLayout hashTag4 = v.findViewById(R.id.hashTag4);
        LinearLayout hashTag5 = v.findViewById(R.id.hashTag5);
        LinearLayout hashTag6 = v.findViewById(R.id.hashTag6);
        LinearLayout hashTag7 = v.findViewById(R.id.hashTag7);
        LinearLayout hashTag8 = v.findViewById(R.id.hashTag8);
        LinearLayout hashTag9 = v.findViewById(R.id.hashTag9);
        LinearLayout hashTag10 = v.findViewById(R.id.hashTag10);
        LinearLayout hashTag11 = v.findViewById(R.id.hashTag11);
        LinearLayout hashTag12 = v.findViewById(R.id.hashTag12);
        LinearLayout hashTag13 = v.findViewById(R.id.hashTag13);
        LinearLayout hashTag14 = v.findViewById(R.id.hashTag14);
        LinearLayout hashTag15 = v.findViewById(R.id.hashTag15);
        LinearLayout hashTag16 = v.findViewById(R.id.hashTag16);
        LinearLayout hashTag17 = v.findViewById(R.id.hashTag17);
        LinearLayout hashTag18 = v.findViewById(R.id.hashTag18);
        LinearLayout hashTag19 = v.findViewById(R.id.hashTag19);
        LinearLayout hashTag20 = v.findViewById(R.id.hashTag20);
        LinearLayout hashTag21 = v.findViewById(R.id.hashTag21);
        LinearLayout hashTag22 = v.findViewById(R.id.hashTag22);


        area1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(0) == 0) {
                    areaBtn1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(0, 1);
                } else {
                    areaBtn1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(0, 0);
                }
            }
        });
        area2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(1) == 0) {
                    areaBtn2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(1, 1);
                } else {
                    areaBtn2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(1, 0);
                }
            }
        });
        area3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(2) == 0) {
                    areaBtn3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(2, 1);
                } else {
                    areaBtn3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(2, 0);
                }
            }
        });
        area4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(3) == 0) {
                    areaBtn4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(3, 1);
                } else {
                    areaBtn4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(3, 0);
                }
            }
        });
        area5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(4) == 0) {
                    areaBtn5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(4, 1);
                } else {
                    areaBtn5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(4, 0);
                }
            }
        });
        area6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(5) == 0) {
                    areaBtn6.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(5, 1);
                } else {
                    areaBtn6.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(5, 0);
                }
            }
        });
        area7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(6) == 0) {
                    areaBtn7.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(6, 1);
                } else {
                    areaBtn7.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(6, 0);
                }
            }
        });
        area8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(7) == 0) {
                    areaBtn8.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(7, 1);
                } else {
                    areaBtn8.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(7, 0);
                }
            }
        });
        area9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(8) == 0) {
                    areaBtn9.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(8, 1);
                } else {
                    areaBtn9.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(8, 0);
                }
            }
        });
        area10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(9) == 0) {
                    areaBtn10.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(9, 1);
                } else {
                    areaBtn10.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(9, 0);
                }
            }
        });
        area11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(10) == 0) {
                    areaBtn11.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(10, 1);
                } else {
                    areaBtn11.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(10, 0);
                }
            }
        });
        area12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(11) == 0) {
                    areaBtn12.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(11, 1);
                } else {
                    areaBtn12.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(11, 0);
                }
            }
        });
        area13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(12) == 0) {
                    areaBtn13.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(12, 1);
                } else {
                    areaBtn13.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(12, 0);
                }
            }
        });
        area14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(13) == 0) {
                    areaBtn14.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(13, 1);
                } else {
                    areaBtn14.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(13, 0);
                }
            }
        });
        area15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(14) == 0) {
                    areaBtn15.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(14, 1);
                } else {
                    areaBtn15.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(14, 0);
                }
            }
        });
        area16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(15) == 0) {
                    areaBtn16.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(15, 1);
                } else {
                    areaBtn16.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(15, 0);
                }
            }
        });
        area17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.get(16) == 0) {
                    areaBtn17.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    area.set(16, 1);
                } else {
                    areaBtn17.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    area.set(16, 0);
                }
            }
        });
        hashTag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(0) == 0) {
                    hashTagBtn1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(0, 1);
                } else {
                    hashTagBtn1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(0, 0);
                }
            }
        });
        hashTag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(1) == 0) {
                    hashTagBtn2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(1, 1);
                } else {
                    hashTagBtn2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(1, 0);
                }
            }
        });
        hashTag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(2) == 0) {
                    hashTagBtn3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(2, 1);
                } else {
                    hashTagBtn3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(2, 0);
                }
            }
        });
        hashTag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(3) == 0) {
                    hashTagBtn4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(3, 1);
                } else {
                    hashTagBtn4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(3, 0);
                }
            }
        });
        hashTag5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(4) == 0) {
                    hashTagBtn5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(4, 1);
                } else {
                    hashTagBtn5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(4, 0);
                }
            }
        });
        hashTag6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(5) == 0) {
                    hashTagBtn6.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(5, 1);
                } else {
                    hashTagBtn6.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(5, 0);
                }
            }
        });
        hashTag7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(6) == 0) {
                    hashTagBtn7.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(6, 1);
                } else {
                    hashTagBtn7.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(6, 0);
                }
            }
        });
        hashTag8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(7) == 0) {
                    hashTagBtn8.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(7, 1);
                } else {
                    hashTagBtn8.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(7, 0);
                }
            }
        });
        hashTag9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(8) == 0) {
                    hashTagBtn9.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(8, 1);
                } else {
                    hashTagBtn9.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(8, 0);
                }
            }
        });
        hashTag10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(9) == 0) {
                    hashTagBtn10.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(9, 1);
                } else {
                    hashTagBtn10.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(9, 0);
                }
            }
        });
        hashTag11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(10) == 0) {
                    hashTagBtn11.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(10, 1);
                } else {
                    hashTagBtn11.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(10, 0);
                }
            }
        });
        hashTag12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(11) == 0) {
                    hashTagBtn12.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(11, 1);
                } else {
                    hashTagBtn12.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(11, 0);
                }
            }
        });
        hashTag13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(12) == 0) {
                    hashTagBtn13.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(12, 1);
                } else {
                    hashTagBtn13.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(12, 0);
                }
            }
        });
        hashTag14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(13) == 0) {
                    hashTagBtn14.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(13, 1);
                } else {
                    hashTagBtn14.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(13, 0);
                }
            }
        });
        hashTag15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(14) == 0) {
                    hashTagBtn15.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(14, 1);
                } else {
                    hashTagBtn15.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(14, 0);
                }
            }
        });
        hashTag16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(15) == 0) {
                    hashTagBtn16.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(15, 1);
                } else {
                    hashTagBtn16.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(15, 0);
                }
            }
        });
        hashTag17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(16) == 0) {
                    hashTagBtn17.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(16, 1);
                } else {
                    hashTagBtn17.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(16, 0);
                }
            }
        });
        hashTag18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(17) == 0) {
                    hashTagBtn18.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(17, 1);
                } else {
                    hashTagBtn18.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(17, 0);
                }
            }
        });
        hashTag19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(18) == 0) {
                    hashTagBtn19.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(18, 1);
                } else {
                    hashTagBtn19.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(18, 0);
                }
            }
        });
        hashTag20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(19) == 0) {
                    hashTagBtn20.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(19, 1);
                } else {
                    hashTagBtn20.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(19, 0);
                }
            }
        });
        hashTag21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(20) == 0) {
                    hashTagBtn21.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(20, 1);
                } else {
                    hashTagBtn21.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(20, 0);
                }
            }
        });
        hashTag22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hashTag.get(21) == 0) {
                    hashTagBtn22.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox));
                    hashTag.set(21, 1);
                } else {
                    hashTagBtn22.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.filter__checkbox_non));
                    hashTag.set(21, 0);
                }
            }
        });

        return v;
    }

}