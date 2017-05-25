package com.iot.mymap;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.iot.mymap.R.id.btnCurrentLocation;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener
{
    private static final String TAG = "MapActivity";
    //private final String POI_REACHED="com.iot.mymap.POI_REACHED";

    LocationManager locManager;
    LocationListener locationListener;
    LocationReceiver receiver;        // 브로드캐스트 리시버의 인스턴스 정의

    SupportMapFragment mapFragment;
    GoogleMap map;

    final static double mLatitude = 37.541697;   //위도
    final static double mLongitude = 126.840417;  //경도

    String intentKey = "MapActivity";

    public int map_size = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        try
        {
            MapsInitializer.initialize(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Button button = (Button) findViewById(btnCurrentLocation);
        CurrentLocation();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CurrentLocation();
            }
        });

        receiver = new LocationReceiver();
        IntentFilter filter = new IntentFilter(intentKey);
        registerReceiver(receiver, filter);
    }

    public void biggerClicked(View v) {

        map_size += 1;
        onMapChanged(map);
    }
    public void smallerClicked(View v) {

        map_size -= 1;
        onMapChanged(map);
    }

    public void onMapChanged(GoogleMap googleMap) {
        Log.d(TAG, "GoogleMap is ready.");
        this.map = googleMap;

        //지도타입 - 일반
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(map_size);

        googleMap.animateCamera(zoom);

        map.setOnMapClickListener(this);
    }

    private void CurrentLocation()
    {
        locationListener = new LocationListener()
        {

            @Override
            public void onLocationChanged(Location location)
            {
                showCurrentLocation(location);
                Toast.makeText(getApplicationContext(), "위도 : " + location.getLatitude() + " 경도 : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderEnabled(String provider)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {
                // TODO Auto-generated method stub
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);

    }

    public void onStop()
    {
        super.onStop();
        locManager.removeUpdates(locationListener);
        unregisterReceiver(receiver);
        receiver=null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Log.d(TAG, "GoogleMap is ready.");
        this.map = googleMap;

        //지도타입 - 일반
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //기본위치
        LatLng position = new LatLng(mLatitude, mLongitude);

        //화면중앙의 위치와 카메라 줌비율
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14));

        map.setOnMapClickListener(this);
    }

    public void onAddMarker(double _latitude, double _longitude)
    {
        LatLng position = new LatLng(_latitude, _longitude);  //마커의 위치

        MarkerOptions mymarker = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(300f))  //마커색상지정
                .title("NO")
                .position(position)
                .alpha(0.7f);   //마커위치

        //마커추가
        this.map.addMarker(mymarker);
    }

    public void onMapClick(LatLng point)
    {
        Point screenPt = map.getProjection().toScreenLocation(point);
        LatLng latLng = map.getProjection().fromScreenLocation(screenPt);

        onAddMarker(point.latitude, point.longitude);

        Log.d("맵좌표","좌표: 위도(" + String.valueOf(point.latitude) + "), 경도(" + String.valueOf(point.longitude) + ")");
        Log.d("화면좌표","화면좌표: X(" + String.valueOf(screenPt.x) + "), Y(" + String.valueOf(screenPt.y) + ")");
        // ProximityAlert 등록
        Intent intent = new Intent(intentKey);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Log.d(TAG, "getBroadcast.");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.addProximityAlert(point.latitude, point.longitude, 100f, -1, proximityIntent);
        Log.d(TAG, "addproximityAlert.");
        Toast.makeText(getApplicationContext(), "위도 : " + point.latitude + " 경도 : " + point.longitude, Toast.LENGTH_SHORT).show();

    }

//    private void requestMyLocation() {
//        LocationManager manager =
//                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        try {
//            long minTime = 10000;
//            float minDistance = 0;
//            manager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    minTime,
//                    minDistance,
//                    new LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            showCurrentLocation(location);
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//
//                        }
//                    }
//            );
//
//            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (lastLocation != null) {
//                showCurrentLocation(lastLocation);
//            }
//
//            manager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,
//                    minTime,
//                    minDistance,
//                    new LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            showCurrentLocation(location);
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//
//                        }
//                    }
//            );
//
//
//        } catch(SecurityException e) {
//            e.printStackTrace();
//        }
//    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
    }

}