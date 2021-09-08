package com.starrynight.tourapiproject.mapPage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.starrynight.tourapiproject.R;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


public class MapFragment extends Fragment {

    private MapView mapView;

    //내위치설정
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_CALL_PHONE = 2;
    int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = new String[]{ Manifest.permission.ACCESS_FINE_LOCATION};
    LocationManager lm;
    //gps와 네트워크 중 알맞은 프로바이더 제공
    Criteria criteria = new Criteria();

    //마커, 위치, 오브젝트 생성
    private MapPOIItem mMarker;
    private MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);
    private MapPoint MY_POINT;

    private MapPOIItem[] tourPOIItems = new MapPOIItem[10];
    private MapPOIItem[] observePOIItems = new MapPOIItem[10];
    private int tourPOIIdx = 0;
    private int observePOIIdx = 0;


    private MarkereventListner markereventListner= new MarkereventListner();
    private MapeventListner mapeventListner=new MapeventListner();


    RelativeLayout details;
    TextView detailsName_Text;
    TextView detailsContent_Text;
    Button kmap_btn;
    CheckBox tour_ckb;
    CheckBox observe_ckb;
    ImageButton myLocation_btn;

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
//            BalloonObject bobject = (BalloonObject)poiItem.getUserObject();
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
//            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText(bobject.getContent());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }


    class MarkereventListner implements MapView.POIItemEventListener{

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
            //상세정보 레이아웃 등장 설정
            BalloonObject bobject = (BalloonObject) mapPOIItem.getUserObject();
            detailsName_Text.setText(bobject.getName());
            detailsContent_Text.setText(bobject.getContent());
            details.setVisibility(View.VISIBLE);
            String url ="kakaomap://look?p="+bobject.getLatitude()+","+bobject.getLongitude();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            kmap_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "상세정보 클릭됨", Toast.LENGTH_SHORT).show();
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

    class MapeventListner implements MapView.MapViewEventListener
    {

        @Override
        public void onMapViewInitialized(MapView mapView) {
            MapPoint classPoint = MapPoint.mapPointWithGeoCoord( 37.537229,127.005515 );
            mapView.setMapCenterPoint(classPoint, true);
            mapView.setZoomLevel( 2, true );
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

            Log.e("test", "onLocationChanged, location:" + location);

            MY_POINT= MapPoint.mapPointWithGeoCoord(location.getLatitude(),location.getLongitude());
            mapView.setMapCenterPoint(MY_POINT, true);
            lm.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.

        }

        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.e("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.e("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.e("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        lm.removeUpdates(mLocationListener);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        details = view.findViewById(R.id.detail_layout);
        detailsName_Text = view.findViewById(R.id.name_txt);
        detailsContent_Text = view.findViewById(R.id.content_txt);
        kmap_btn = view.findViewById(R.id.kmap_btn);
        observe_ckb = view.findViewById(R.id.observe_ck);
        tour_ckb = view.findViewById(R.id.tour_ck);
        myLocation_btn = view.findViewById(R.id.myLocation_btn);
        //지도 띄우기
//        MapView mapView = new MapView(getActivity());
        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

    //어댑터, 리스너 설정
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.setPOIItemEventListener(markereventListner);
        mapView.setMapViewEventListener(mapeventListner);

        BalloonObject balloonObject1= setupMaker (1,"관측지1", "제주도에 있는 관측지", 37.54892296550104, 126.99089033876304);
        createObserveMarker(mapView, balloonObject1);

        BalloonObject balloonObject2 = setupMaker(2,"관광지1", "강릉에 있는 관광지", 37.53737528, 127.00557633);
        createTourMarker(mapView, balloonObject2);

        //내위치로 정렬
        setMyLocation();
        myLocation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);    //denied면 -1

                Log.d("test", "onClick: location clicked");
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    getMyLocaiton();

                } else if (permissionCheck == PackageManager.PERMISSION_DENIED){
                    Log.d("test", "permission denied");
                    Toast.makeText(getContext(), "위치권한이 없습니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                }
            }
        });

        observe_ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(observe_ckb.isChecked()){

                    BalloonObject balloonObject1= setupMaker (1,"관측지1", "제주도에 있는 관측지", 37.54892296550104, 126.99089033876304);
                    createObserveMarker(mapView, balloonObject1);
                }else {
                    if(observePOIIdx!=0)
                        for(int i=0;i<observePOIIdx;i++)
                            mapView.removePOIItem(observePOIItems[i]);
                    observePOIIdx = 0;
                }
            }
        });


        tour_ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tour_ckb.isChecked()) {
                    BalloonObject balloonObject2 = setupMaker(2,"관광지1", "강릉에 있는 관광지", 37.53737528, 127.00557633);
                    createTourMarker(mapView, balloonObject2);
                } else {
                    if(tourPOIIdx!=0)
                        for(int i=0;i<tourPOIIdx;i++)
                            mapView.removePOIItem(tourPOIItems[i]);
                    tourPOIIdx = 0;
                }
            }
        });


        return view;
    }

    private void setMyLocation() {
        lm=(LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

    }

    private void getMyLocaiton(){
        String provider = lm.getBestProvider(criteria, true);

        try {
            if (!lm.isProviderEnabled(provider) && lm.getLastKnownLocation(provider) != null) {
                lm.requestLocationUpdates(provider,1000,10,mLocationListener);
            }else {
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                provider = lm.getBestProvider(criteria, true);
                lm.requestLocationUpdates(provider,1000,10,mLocationListener);
            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

    }


    private void createObserveMarker(MapView mapView, BalloonObject balloonObject) {
        //관측지 마커 생성, 태그는 1번, 파란핀
        mMarker = new MapPOIItem();
        mMarker.setItemName(balloonObject.getName());
        mMarker.setTag(1);
        mMarker.setMapPoint(MARKER_POINT);
        mMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커타입을 지정
        mMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mMarker.setUserObject(balloonObject);   //

//        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_red); //마커타입을 커스텀으로 지정 후 이용
//        mCustomMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        observePOIItems[observePOIIdx]=mMarker;
        observePOIIdx++;

        mapView.addPOIItem(mMarker);
        mapView.selectPOIItem(mMarker, true);
        mapView.setMapCenterPoint(MARKER_POINT, false);

    }

    private void createTourMarker(MapView mapView, BalloonObject balloonObject) {
        //관광지 마커 생성, 태그는 2번, 노란핀
        mMarker = new MapPOIItem();
        mMarker.setItemName(balloonObject.getName());
        mMarker.setTag(2);
        mMarker.setMapPoint(MARKER_POINT);
        mMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커타입을 지정
        mMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mMarker.setUserObject(balloonObject);   //

//        mCustomMarker.setCustomImageResourceId(R.drawable.custom_marker_red); //마커타입을 커스텀으로 지정 후 이용
//        mCustomMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        mCustomMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        tourPOIItems[tourPOIIdx] = mMarker;
        tourPOIIdx++;

        mapView.addPOIItem(mMarker);
        mapView.selectPOIItem(mMarker, true);
        mapView.setMapCenterPoint(MARKER_POINT, false);

    }

    private BalloonObject setupMaker(int tag, String name, String content, double  latitude, double longitude)
    {
        //마커 기본정보 object 생성 및 setup
        BalloonObject balloon_Object = new BalloonObject();
        balloon_Object.setName(name);
        balloon_Object.setContent(content);
        balloon_Object.setTag(tag);
        balloon_Object.setLatitude(latitude);
        balloon_Object.setLongitude(longitude);

        MARKER_POINT= MapPoint.mapPointWithGeoCoord(latitude, longitude);

        return  balloon_Object;
    }
}