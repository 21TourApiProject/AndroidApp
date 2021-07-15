package com.starrynight.tourapiproject.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


public class MapFragment extends Fragment {
    //마커, 위치, 오브젝트 생성
    private MapPOIItem mMarker;
    private MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);
    BalloonObject balloon_Object = new BalloonObject();

    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.map_custom_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            //말풍선 내용 설정
            BalloonObject bobject = (BalloonObject)poiItem.getUserObject();
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText(bobject.getContent());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapView mapView = new MapView(getActivity());

        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //지도 중심점 설정
       mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304), true);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        // 줌 레벨 변경
        mapView.setZoomLevel(4, true);

        setupMaker(1,"1100고지", "제주도에 있는 관측지", 37.54892296550104, 126.99089033876304);
        if(balloon_Object.getTag()==1)
            createObserveMarker(mapView);
        else if(balloon_Object.getTag()==2)
            createTourMarker(mapView);

        setupMaker(2,"안반데기", "강릉에 있는 관측지", 37.53737528, 127.00557633);
        if(balloon_Object.getTag()==1)
            createObserveMarker(mapView);
        else if(balloon_Object.getTag()==2)
            createTourMarker(mapView);

        return view;
    }

    private void createObserveMarker(MapView mapView) {
        //관측지 마커 생성, 태그는 1번, 파란핀
        mMarker = new MapPOIItem();
        mMarker.setItemName(balloon_Object.getName());
        mMarker.setTag(1);
        mMarker.setMapPoint(MARKER_POINT);
        mMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커타입을 지정
        mMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mMarker.setUserObject(balloon_Object);   //

//        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_red); //마커타입을 커스텀으로 지정 후 이용
//        mCustomMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        mapView.addPOIItem(mMarker);
        mapView.selectPOIItem(mMarker, true);
        mapView.setMapCenterPoint(MARKER_POINT, false);
    }

    private void createTourMarker(MapView mapView) {
        //관광지 마커 생성, 태그는 2번, 노란핀
        mMarker = new MapPOIItem();
        mMarker.setItemName(balloon_Object.getName());
        mMarker.setTag(2);
        mMarker.setMapPoint(MARKER_POINT);
        mMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커타입을 지정
        mMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mMarker.setUserObject(balloon_Object);   //

//        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_red); //마커타입을 커스텀으로 지정 후 이용
//        mCustomMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        mapView.addPOIItem(mMarker);
        mapView.selectPOIItem(mMarker, true);
        mapView.setMapCenterPoint(MARKER_POINT, false);
    }

    private void setupMaker(int tag, String name, String content, double  latitude, double longitude)
    {
        balloon_Object.setName(name);
        balloon_Object.setContent(content);
        balloon_Object.setTag(tag);

        MARKER_POINT=MapPoint.mapPointWithGeoCoord(latitude, longitude);
    }
}