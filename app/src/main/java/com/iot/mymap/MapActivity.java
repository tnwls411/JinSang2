package com.iot.mymap;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener
{
    private static final String TAG = "MapActivity";

    LocationManager locManager;
    LocationListener locationListener;
//    LocationReceiver receiver;        // 브로드캐스트 리시버의 인스턴스 정의

    SupportMapFragment mapFragment;
    GoogleMap map;

    final static double _Latitude = 37.541697;   //위도
    final static double _Longitude = 126.840417;  //경도

    String intentKey = "MapActivity";

    public int map_size = 14;
    public int radius = 50;
    public int Id = 0;

    ArrayList _PendingIntentList;

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
        _PendingIntentList = new ArrayList();
        CurrentLocation();

        Button button = (Button) findViewById(R.id.btnCurrentLocation);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CurrentLocation();
                Log.d(TAG,"clickbtncurrentlocation");
            }
        });

//        receiver = new LocationReceiver();
//        IntentFilter filter = new IntentFilter(intentKey);
//        registerReceiver(receiver, filter);
    }

    private void CurrentLocation()
    {
        locationListener = new LocationListener()
        {

            @Override

            public void onLocationChanged(Location location)
            {
                showCurrentLocation(location);
                Toast.makeText(getApplicationContext(), "1 위도 : " + location.getLatitude() + " 경도 : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
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

    public void biggerClicked(View v)
    {
        map_size += 1;
        onMapChanged(map);
    }

    public void smallerClicked(View v)
    {
        map_size -= 1;
        onMapChanged(map);
    }

    public void onMapChanged(GoogleMap googleMap)
    {
        Log.d(TAG, "GoogleMap is ready.");
        this.map = googleMap;

        //지도타입 - 일반
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(map_size);

        googleMap.animateCamera(zoom);

        map.setOnMapClickListener(this);
    }

    public void goLocintentClicked(View v)
    {
        Intent intent = new Intent(MapActivity.this, LocationAlertActivity.class);
        startActivity(intent);
    }

//    public void onStop()   //해제
//    {
//        super.onStop();
//        locManager.removeUpdates(locationListener);
//        unregisterReceiver(receiver);
//        receiver=null;
//    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Log.d(TAG, "GoogleMap is ready.");
        this.map = googleMap;

        //지도타입 - 일반
        this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //기본위치
        LatLng position = new LatLng(_Latitude,_Longitude);

        //화면중앙의 위치와 카메라 줌비율
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, map_size));

        map.setOnMapClickListener(this);
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
        map.setMyLocationEnabled(true);  // 현재위치 표시
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (map != null)
        {
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
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (map != null)
        {
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
            map.setMyLocationEnabled(false);
        }
    }

    public void onAddMarker(double _latitude, double _longitude)
    {
        LatLng position = new LatLng(_latitude, _longitude);  //마커의 위치

        MarkerOptions mymarker = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.alertloc))  //마커색상지정
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
        Log.d("맵좌표","좌표: 위도(" + String.valueOf(point.latitude) + "), 경도(" + String.valueOf(point.longitude) + ")");
        Log.d("화면좌표","화면좌표: X(" + String.valueOf(screenPt.x) + "), Y(" + String.valueOf(screenPt.y) + ")");

        onAddMarker(point.latitude, point.longitude);

        //ProximityAlert 등록 , 리시버를 실행하도록 ProximityAlert에 의뢰
        Intent intent = new Intent(intentKey);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, Id, intent, 0);
        Id++;

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

        //locationManager에게 위치 알려주기

        /**
         * point.latitude, point.longitude 인수 = >위도와 경도 정보
         * radius => 지정한 위도와 경도를 기준으로 근접알람을 제공하는 기준이 되는 위치 ( 단위 : m)
         * expiration => 근접알림이 발생한 후 해당 지점에 대해서 근접 알림을
         *              해제하기까지의 지연시간을 지정 ( 단위 : mills)
         *              근접알림을 계속 지정하고 싶으면 -1
         * intent => 근접이벤트가 발생했을 때 실행할 인텐트 지정 ( 팬딩 인텐트)
         */
        locManager.addProximityAlert(point.latitude, point.longitude, radius, -1, proximityIntent);
        _PendingIntentList.add(intent);
        Log.d(TAG, "addproximityAlert.");
        Log.d(TAG,Integer.toString(Id));
        Toast.makeText(getApplicationContext(), "2 위도 : " + point.latitude + " 경도 : " + point.longitude, Toast.LENGTH_SHORT).show();

        String Latitude = String.valueOf(point.latitude);
        String Longitude = String.valueOf(point.longitude);
        Intent send1 = new Intent(MapActivity.this, MainActivity.class);
        Content content = new Content(0, null, 0, 0, Latitude, Longitude);
        send1.putExtra("content", content);
        startActivity(send1);
        finish();
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
    }

}