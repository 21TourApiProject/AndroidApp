package com.starrynight.tourapiproject.mapPage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.observationPage.ObservationsiteActivity;
import com.starrynight.tourapiproject.observationPage.RecyclerDecoration;
import com.starrynight.tourapiproject.observationPage.RecyclerHashTagAdapter;
import com.starrynight.tourapiproject.observationPage.RecyclerHashTagItem;
import com.starrynight.tourapiproject.searchPage.FilterFragment;
import com.starrynight.tourapiproject.searchPage.SearchResultFragment;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.Filter;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchKey;
import com.starrynight.tourapiproject.searchPage.searchPageRetrofit.SearchParams1;
import com.starrynight.tourapiproject.touristPointPage.TouristPointActivity;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
* @className : MapFragment.java
* @description : 지도 프래그먼트
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class MapFragment extends Fragment {

    private static final String TAG = "map page";

    private Long userId;
    Boolean isWish;

    private MapView mapView;
    ViewGroup mapViewContainer;

    //내위치설정
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_CALL_PHONE = 2;
    int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    LocationManager lm;
    //gps와 네트워크 중 알맞은 프로바이더 제공
    Criteria criteria = new Criteria();

    //마커, 위치, 오브젝트 생성
    private MapPOIItem mMarker;
    //    private MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);
    private MapPoint MY_POINT;

    private List<MapPOIItem> tourPOIItems = new ArrayList<>();
    private List<MapPOIItem> observePOIItems = new ArrayList<>();

    private List<BalloonObject> observationBalloonObjects = new ArrayList<>();
    private List<BalloonObject> tourBalloonObjects = new ArrayList<>();

    private MarkereventListner markereventListner = new MarkereventListner();
    private MapeventListner mapeventListner = new MapeventListner();

    List<String> observeHashTags;
    private RecyclerHashTagAdapter recyclerHashTagAdapter;

    boolean from_detail = false;

    LoadingDialog dialog;

    RelativeLayout details;
    TextView detailsName_txt;
    TextView detailsIntro_txt;
    TextView detailsAddress_txt;
    TextView detailsType_txt;
    Button kmap_btn;
    CheckBox tour_ckb;
    CheckBox observe_ckb;
    ImageButton myLocation_btn;
    RecyclerView hashTagsrecyclerView;
    ImageView main_img;
    LinearLayout selectFilterItem;
    HorizontalScrollView horizontalScrollView;
    androidx.appcompat.widget.SearchView searchView;

    Long[] areaCode = {1L, 31L, 2L, 32L, 33L, 34L, 3L, 8L, 37L, 5L, 38L, 35L, 4L, 36L, 6L, 7L, 39L}; //관광지 지역코드
    String[] areaName = {"서울", "경기", "인천", "강원", "충북", "충남", "대전", "세종", "전북", "광주", "전남", "경북", "대구", "경남", "부산", "울산", "제주"};
    String[] hashTagName = {"공기 좋은", "깔끔한", "감성적인", "이색적인", "인생샷", "전문적인", "캠핑", "차박", "뚜벅이", "드라이브",
            "반려동물", "한적한", "근교", "도심 속", "연인", "가족", "친구", "혼자", "가성비", "소확행", "럭셔리한", "경치 좋은"};

    List<SearchParams1> obResult; //관측지 필터 결과
    List<SearchParams1> tpResult; //관광지 필터 결과
    ArrayList<Integer> area; //어떤 지역필터 선택했는지 Integer값(0이면 선택x, 1이면 선택o)으로 받아온 배열
    ArrayList<Integer> hashTag; //어떤 해시태그필터 선택했는지 Integer값(0이면 선택x, 1이면 선택o)으로 받아온 배열
    List<Long> areaCodeList;
    List<Long> hashTagIdList;
    String keyword = null;

    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();

        return mapFragment;
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.map_custom_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            //말풍선 내용 설정
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }


    class MarkereventListner implements MapView.POIItemEventListener {

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
            //상세정보 레이아웃 등장 설정
            BalloonObject bobject = (BalloonObject) mapPOIItem.getUserObject();
            detailsName_txt.setText(bobject.getName());
            detailsIntro_txt.setText(bobject.getIntro());
            detailsAddress_txt.setText(bobject.getAddress());
            detailsType_txt.setText(bobject.getPoint_type());
            details.setVisibility(View.VISIBLE);
            String url = "kakaomap://look?p=" + bobject.getLatitude() + "," + bobject.getLongitude();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            initHashtagRecycler();
            observeHashTags = bobject.getHashtags();
            if (observeHashTags != null) {
                int i = 0;
                for (String p : observeHashTags) {
                    if (i == 3)
                        break;
                    RecyclerHashTagItem item = new RecyclerHashTagItem();
                    item.setHashtagName(p);

                    recyclerHashTagAdapter.addItem(item);
                    i++;
                }
                recyclerHashTagAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG, "해쉬태그 비었음");
            }

            if (bobject.getImage() != null) {
                Glide.with(getContext())
                        .load(bobject.getImage())
                        .into(main_img);
            } else {
                main_img.setImageResource(R.drawable.default_image);
                Log.e(TAG, "이미지 비었음");
            }


            kmap_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        String url2 = "market://details?id=net.daum.android.map";
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                        startActivity(intent2);
                    }

                }
            });

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //상세정보 클릭시 관측지나 관광지로 이동
                    if (!from_detail) {
                        if (bobject.getTag() == 2) {
                            //관광지
                            Intent intent = new Intent(getActivity(), TouristPointActivity.class);
                            intent.putExtra("contentId", bobject.getId());
                            startActivity(intent);

                        } else if (bobject.getTag() == 1) {
                            Intent intent = new Intent(getActivity(), ObservationsiteActivity.class);
                            intent.putExtra("observationId", bobject.getId());
                            startActivity(intent);
                        }
                    }
                }
            });

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

        }
    }

    class MapeventListner implements MapView.MapViewEventListener {

        @Override
        public void onMapViewInitialized(MapView mapView) {
//            MapPoint classPoint = MapPoint.mapPointWithGeoCoord(37.537229, 127.005515);
//            mapView.setMapCenterPoint(classPoint, true);
            mapView.setZoomLevel(2, true);
        }

        @Override
        public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewZoomLevelChanged(MapView mapView, int i) {

        }

        @Override
        public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
            //상세정보 숨기기
            details.setVisibility(View.GONE);
        }

        @Override
        public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

        }
    }


    private final LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d(TAG, "onLocationChanged, location:" + location);
            MY_POINT = MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude());
            mapView.setMapCenterPoint(MY_POINT, true);
            lm.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.

        }

        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.e(TAG, "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.e(TAG, "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d(TAG, "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mapViewContainer.removeView(mapView);
        super.onDestroy();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        mapViewContainer.addView(mapView);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        lm.removeUpdates(mLocationListener);
//        mapViewContainer.removeView(mapView);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        details = view.findViewById(R.id.detail_layout);
        detailsName_txt = view.findViewById(R.id.name_txt);
        detailsIntro_txt = view.findViewById(R.id.intro_txt);
        detailsAddress_txt = view.findViewById(R.id.address_txt);
        detailsType_txt = view.findViewById(R.id.type_txt);
        kmap_btn = view.findViewById(R.id.kmap_btn);
        observe_ckb = view.findViewById(R.id.observe_ck);
        tour_ckb = view.findViewById(R.id.tour_ck);
        myLocation_btn = view.findViewById(R.id.myLocation_btn);
        hashTagsrecyclerView = view.findViewById(R.id.hashtags_layout);
        main_img = view.findViewById(R.id.main_img);
        searchView = view.findViewById(R.id.map_search);
        selectFilterItem = view.findViewById(R.id.map_selectFilterItem);
        horizontalScrollView = view.findViewById(R.id.map_selectFilterScrollView);
        selectFilterItem.removeAllViews();
        //지도 띄우기
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        ((MainActivity) getActivity()).showBottom();

        dialog = new LoadingDialog(getContext());
        //어댑터, 리스너 설정
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.setPOIItemEventListener(markereventListner);
        mapView.setMapViewEventListener(mapeventListner);

        check_userId();
        observePOIItems.clear();
        tourPOIItems.clear();

        //검색관련 설정
        obResult = new ArrayList<>();
        tpResult = new ArrayList<>();
        searchView.setIconifiedByDefault(false);
        area = new ArrayList<Integer>(Collections.nCopies(17, 0));
        hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));
        RecyclerDecoration hashtagDecoration = new RecyclerDecoration(16);
        hashTagsrecyclerView.addItemDecoration(hashtagDecoration);

        if (getArguments() != null) {
            Activities fromWhere = (Activities) getArguments().getSerializable("FromWhere");
            //지도 초기화
            initMapView();

            if (fromWhere == Activities.OBSERVATION || fromWhere == Activities.TOURISTPOINT || fromWhere == Activities.POST || fromWhere == Activities.MAINPOST) {
                //주소가 하나만 넘어올 때
                BalloonObject singleBalloonObject = (BalloonObject) getArguments().getSerializable("BalloonObject");
                from_detail = true;
                if (singleBalloonObject != null) {
                    if (singleBalloonObject.getTag() == 1) {
                        observationBalloonObjects.add(singleBalloonObject);
                        createObserveMarker(mapView, singleBalloonObject,false);
                    } else if (singleBalloonObject.getTag() == 2) {
                        tourBalloonObjects.add(singleBalloonObject);
                        createTourMarker(mapView, singleBalloonObject, false);
                    }
                } else {
                    Log.e(TAG, "번들에 balloon 없음");
                }
            } else if (fromWhere == Activities.SEARCHRESULT) {
                area = getArguments().getIntegerArrayList("area"); //선택한 지역 필터
                hashTag = getArguments().getIntegerArrayList("hashTag"); //선택한 해시태그 필터
                keyword = getArguments().getString("keyword");

                LoadingAsyncTask task = new LoadingAsyncTask(getActivity(), 4500);
                task.execute();

                selectFilterItem.setVisibility(View.VISIBLE);
                horizontalScrollView.setVisibility(View.VISIBLE);
                selectFilterItem.removeAllViews(); //초기화
                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText(" " + areaName[i] + " ");

                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText("#" + hashTagName[i]);

                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                if (keyword == null) {
                    searchView.setQueryHint("검색어를 입력하세요");
                } else {
                    searchView.setQuery(keyword, false);
                }

                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }

                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams1>> call1 = RetrofitClient.getApiService().getObservationWithFilter(searchKey);
                call1.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();

                            for (SearchParams1 params1 : obResult) {
                                BalloonObject balloonObject = setupMaker(params1, 1);
                                observationBalloonObjects.add(balloonObject);

                                Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, balloonObject.getId(), 0);
                                call0.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body()) {
                                                Log.e(TAG, "찜해놓음");
                                                createObserveMarker(mapView, balloonObject,true);
                                            } else {
                                                createObserveMarker(mapView, balloonObject,false);
                                            }
                                        } else {
                                            Log.d(TAG, "내 찜 조회하기 실패");
                                            createObserveMarker(mapView, balloonObject,false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Log.e("연결실패", t.getMessage());
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "관측지 검색 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
                Call<List<SearchParams1>> call2 = RetrofitClient.getApiService().getTouristPointWithFilterforMap(searchKey);
                call2.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            tpResult = response.body();


                            for (SearchParams1 params1 : tpResult) {
                                BalloonObject balloonObject = setupMaker(params1, 2);
                                tourBalloonObjects.add(balloonObject);

                                Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, balloonObject.getId(), 1);
                                call0.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body()) {
                                                Log.e(TAG, "찜해놓음");
                                                createTourMarker(mapView, balloonObject, true);
                                            } else {
                                                createTourMarker(mapView, balloonObject, false);
                                            }
                                        } else {
                                            Log.d(TAG, "내 찜 조회하기 실패");
                                            createTourMarker(mapView, balloonObject, false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Log.e("연결실패", t.getMessage());
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "관광지 필터 검색 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

            } else if (fromWhere == Activities.SEARCH) {
                searchView.setQueryHint("검색어를 입력하세요");
                area = new ArrayList<Integer>(Collections.nCopies(17, 0));
                hashTag = new ArrayList<Integer>(Collections.nCopies(22, 0));

            } else if (fromWhere == Activities.FILTER) {

                area = getArguments().getIntegerArrayList("area"); //선택한 지역 필터
                hashTag = getArguments().getIntegerArrayList("hashTag"); //선택한 해시태그 필터
                keyword = getArguments().getString("keyword");

                LoadingAsyncTask task = new LoadingAsyncTask(getActivity(), 5500);
                task.execute();

                selectFilterItem.setVisibility(View.VISIBLE);
                horizontalScrollView.setVisibility(View.VISIBLE);
                selectFilterItem.removeAllViews(); //초기화
                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText(" " + areaName[i] + " ");

                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) {
                        TextView textView = new TextView(getContext());
                        textView.setText("#" + hashTagName[i]);

                        textView.setTextSize(10);
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hashtag_background));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = 20;
                        textView.setLayoutParams(params);
                        selectFilterItem.addView(textView);
                        selectFilterItem.setDividerPadding(5);
                    }
                }
                if (keyword == null) {
                    searchView.setQueryHint("검색어를 입력하세요");
                } else {
                    searchView.setQuery(keyword, false);
                }

                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }

                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams1>> call1 = RetrofitClient.getApiService().getObservationWithFilter(searchKey);
                call1.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();

                            for (SearchParams1 params1 : obResult) {
                                BalloonObject balloonObject = setupMaker(params1, 1);
                                observationBalloonObjects.add(balloonObject);
                                Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, balloonObject.getId(), 0);
                                call0.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body()) {
                                                Log.e(TAG, "찜해놓음");
                                                createObserveMarker(mapView, balloonObject,true);
                                            } else {
                                                createObserveMarker(mapView, balloonObject,false);
                                            }
                                        } else {
                                            Log.d(TAG, "내 찜 조회하기 실패");
                                            createObserveMarker(mapView, balloonObject,false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Log.e("연결실패", t.getMessage());
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "관측지 검색 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                //관광지 추가해야함
                Call<List<SearchParams1>> call2 = RetrofitClient.getApiService().getTouristPointWithFilterforMap(searchKey);
                call2.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            tpResult = response.body();


                            for (SearchParams1 params1 : tpResult) {
                                BalloonObject balloonObject = setupMaker(params1, 2);

                                tourBalloonObjects.add(balloonObject);
                                Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, balloonObject.getId(), 1);
                                call0.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body()) {
                                                Log.e(TAG, "찜해놓음");
                                                createTourMarker(mapView, balloonObject, true);
                                            } else {
                                                createTourMarker(mapView, balloonObject, false);
                                            }
                                        } else {
                                            Log.d(TAG, "내 찜 조회하기 실패");
                                            createTourMarker(mapView, balloonObject, false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Log.e("연결실패", t.getMessage());
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "관광지 필터 검색 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        }

        //searchview설정
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                initMapView();

                keyword = query;
                areaCodeList = new ArrayList<>();
                hashTagIdList = new ArrayList<>();

                for (int i = 0; i < 17; i++) {
                    if (area.get(i) == 1) { //선택했으면
                        areaCodeList.add(areaCode[i]);
                    }
                }
                for (int i = 0; i < 22; i++) {
                    if (hashTag.get(i) == 1) { //선택했으면
                        hashTagIdList.add((long) (i + 1));
                    }
                }

                LoadingAsyncTask task = new LoadingAsyncTask(getActivity(), 5500);
                task.execute();

                Filter filter = new Filter(areaCodeList, hashTagIdList);
                SearchKey searchKey = new SearchKey(filter, keyword);
                Call<List<SearchParams1>> call1 = RetrofitClient.getApiService().getObservationWithFilter(searchKey);
                call1.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관측지 검색 성공");
                            obResult = response.body();

                            for (SearchParams1 params1 : obResult) {
                                BalloonObject balloonObject = setupMaker(params1, 1);
                                observationBalloonObjects.add(balloonObject);
                                Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, balloonObject.getId(), 0);
                                call0.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body()) {
                                                Log.e(TAG, "찜해놓음");
                                                createObserveMarker(mapView, balloonObject,true);
                                            } else {
                                                createObserveMarker(mapView, balloonObject,false);
                                            }
                                        } else {
                                            Log.d(TAG, "내 찜 조회하기 실패");
                                            createObserveMarker(mapView, balloonObject,false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Log.e("연결실패", t.getMessage());
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "관측지 검색 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                Call<List<SearchParams1>> call2 = RetrofitClient.getApiService().getTouristPointWithFilterforMap(searchKey);
                call2.enqueue(new Callback<List<SearchParams1>>() {
                    @Override
                    public void onResponse(Call<List<SearchParams1>> call, Response<List<SearchParams1>> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "관광지 검색 성공");
                            tpResult = response.body();

                            for (SearchParams1 params1 : tpResult) {
                                BalloonObject balloonObject = setupMaker(params1, 2);

                                tourBalloonObjects.add(balloonObject);
                                Call<Boolean> call0 = com.starrynight.tourapiproject.myPage.myPageRetrofit.RetrofitClient.getApiService().isThereMyWish(userId, balloonObject.getId(), 1);
                                call0.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body()) {
                                                Log.e(TAG, "찜해놓음");
                                                createTourMarker(mapView, balloonObject, true);
                                            } else {
                                                createTourMarker(mapView, balloonObject, false);
                                            }
                                        } else {
                                            Log.d(TAG, "내 찜 조회하기 실패");
                                            createTourMarker(mapView, balloonObject, false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Log.e("연결실패", t.getMessage());
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "관광지 필터 검색 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SearchParams1>> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //필터버튼 설정
        ImageButton filter_btn = (ImageButton) view.findViewById(R.id.map_filterBtn);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("keyword", keyword);
                bundle.putSerializable("fromWhere", Activities.MAP);
                Fragment filterFragment = ((MainActivity) getActivity()).getFilter();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(MapFragment.this);
                ((MainActivity) getActivity()).setMap(null);

                if (((MainActivity) getActivity()).getFilter() == null) {
                    filterFragment = new FilterFragment();
                    filterFragment.setArguments(bundle);
                    ((MainActivity) getActivity()).setFilter(filterFragment);
                    transaction.add(R.id.main_view, filterFragment);
                    transaction.commit();
                } else {
                    filterFragment.setArguments(bundle);
                    transaction.show(filterFragment);
                    transaction.commit();
                }
            }
        });


        //내위치로 정렬
        setMyLocation();
        myLocation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);    //denied면 -1

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    getMyLocation();

                } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                    Log.d(TAG, "permission denied");
                    Toast.makeText(getContext(), "위치권한이 없습니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                }
            }
        });

        //목록이동
        ImageButton list_btn = (ImageButton) view.findViewById(R.id.searchList_Btn);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putIntegerArrayList("area", area);
                bundle.putIntegerArrayList("hashTag", hashTag);
                bundle.putString("keyword", keyword);
                bundle.putInt("type", 3);

                Fragment searchResultFragment = new SearchResultFragment();
                ((MainActivity) getActivity()).setSearchResult(searchResultFragment);
                searchResultFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(MapFragment.this);
                ((MainActivity) getActivity()).setMap(null);
                transaction.add(R.id.main_view, searchResultFragment);
                transaction.commit();

            }
        });

        observe_ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (observe_ckb.isChecked()) {
                    //관측지필터 체크시
                    for (MapPOIItem p : observePOIItems) {
                        mapView.addPOIItem(p);
                    }
//                    LoadingAsyncTask task = new LoadingAsyncTask(getActivity(), 2000);
//                    task.execute();
                } else {
                    for (MapPOIItem p : observePOIItems)
                        mapView.removePOIItem(p);
                }

            }
        });


        tour_ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tour_ckb.isChecked()) {
                    //관광지 필터 체크시
                    for (MapPOIItem p : tourPOIItems) {
                        mapView.addPOIItem(p);
                    }
//                    LoadingAsyncTask task = new LoadingAsyncTask(getActivity(), 5000);
//                    task.execute();
                } else {
                    for (MapPOIItem p : tourPOIItems)
                        mapView.removePOIItem(p);
                }
            }
        });


        return view;
    }

    private void setMyLocation() {
        lm = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

    }

    private void getMyLocation() {
        String provider = lm.getBestProvider(criteria, true);

        try {
            if (!lm.isProviderEnabled(provider) && lm.getLastKnownLocation(provider) != null) {
                lm.requestLocationUpdates(provider, 1000, 10, mLocationListener);
            } else {
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                provider = lm.getBestProvider(criteria, true);
                lm.requestLocationUpdates(provider, 1000, 10, mLocationListener);
            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

    }


    private void createObserveMarker(MapView mapView, BalloonObject balloonObject, Boolean isWished) {
        //관측지 마커 생성, 태그는 1번, 파란핀
        mMarker = new MapPOIItem();
        mMarker.setItemName(balloonObject.getName());
        mMarker.setTag(1);
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(balloonObject.getLatitude(), balloonObject.getLongitude());
        mMarker.setMapPoint(MARKER_POINT);

        if (isWished) {
            mMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            mMarker.setCustomImageResourceId(R.drawable.map__custompin); //마커타입을 커스텀으로 지정 후 이용
            mMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            mMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        } else {
            mMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커타입을 지정
        }
        mMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mMarker.setUserObject(balloonObject);
        observePOIItems.add(mMarker);

        mapView.addPOIItem(mMarker);
        mapView.selectPOIItem(mMarker, true);
        mapView.setMapCenterPoint(MARKER_POINT, false);

    }

    private void createTourMarker(MapView mapView, BalloonObject balloonObject, Boolean isWished) {
        //관광지 마커 생성, 태그는 2번, 노란핀
        mMarker = new MapPOIItem();
        mMarker.setItemName(balloonObject.getName());
        mMarker.setTag(2);
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(balloonObject.getLatitude(), balloonObject.getLongitude());
        mMarker.setMapPoint(MARKER_POINT);
        if (isWished) {
            mMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            mMarker.setCustomImageResourceId(R.drawable.map__custompin); //마커타입을 커스텀으로 지정 후 이용
            mMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            mMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        } else {
            mMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커타입을 지정
        }

        mMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mMarker.setUserObject(balloonObject);
        tourPOIItems.add(mMarker);

        mapView.addPOIItem(mMarker);
        mapView.selectPOIItem(mMarker, true);
        mapView.setMapCenterPoint(MARKER_POINT, false);

    }

    private BalloonObject setupMaker(SearchParams1 params1, int t) {
        //마커 기본정보 object 생성 및 setup
        BalloonObject balloon_Object = new BalloonObject();
        balloon_Object.setId(params1.getItemId());
        balloon_Object.setName(params1.getTitle());
        balloon_Object.setIntro(params1.getIntro());
        if (t == 2) {
            //주소를 두단어까지 줄임
            String address = params1.getAddress();
            int i = address.indexOf(' ');
            if (i != -1) {
                int j = address.indexOf(' ', i + 1);
                if (j != -1) {
                    balloon_Object.setAddress(params1.getAddress().substring(0, j));
                } else {
                    balloon_Object.setAddress(params1.getAddress());
                }
            } else {
                balloon_Object.setAddress(params1.getAddress());
            }
        } else {
            balloon_Object.setAddress(params1.getAddress());
        }
        balloon_Object.setPoint_type(params1.getContentType());
        balloon_Object.setTag(t);
        balloon_Object.setLatitude(params1.getLatitude());
        balloon_Object.setLongitude(params1.getLongitude());
        balloon_Object.setImage(params1.getThumbnail());
        balloon_Object.setHashtags(params1.getHashTagNames());

        return balloon_Object;
    }

    private void initHashtagRecycler() {
        //해쉬태그 리사이클러 초기화
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        hashTagsrecyclerView.setLayoutManager(linearLayoutManager);
        recyclerHashTagAdapter = new RecyclerHashTagAdapter();
        hashTagsrecyclerView.setAdapter(recyclerHashTagAdapter);
    }

    private void initMapView() {
        for (MapPOIItem p : observePOIItems)
            mapView.removePOIItem(p);
        observePOIItems.clear();

        for (MapPOIItem p : tourPOIItems)
            mapView.removePOIItem(p);
        tourPOIItems.clear();

        tourBalloonObjects.clear();
        observationBalloonObjects.clear();
    }

    private class LoadingAsyncTask extends AsyncTask<String, Long, Boolean> {
        private Context mContext = null;
        private Long mtime;

        public LoadingAsyncTask(Context context, long time) {
            mContext = context.getApplicationContext();
            mtime = time;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Thread.sleep(mtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (true);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
        }
    }


    private void check_userId() {
        try {
            String fileName = "userId";
            FileInputStream fis = getActivity().openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}