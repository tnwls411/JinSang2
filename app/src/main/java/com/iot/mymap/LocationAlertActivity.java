package com.iot.mymap;

import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LocationAlertActivity extends MapActivity
{
    LocationReceiver receiver;  // 브로드캐스트 리시버의 인스턴스 정의

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_alert);

        Button stopBtn = (Button) findViewById(R.id.Unregister);
        stopBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                unregister();
                Toast.makeText(getApplicationContext(), "근접 리스너 해제", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //수신자 객체 생성하여 등록
        receiver = new LocationReceiver(intentKey);
        registerReceiver(receiver, receiver.getFilter());
    }


    /**
     * 등록한 정보 해제
     */
    public void onStop()
    {
        super.onStop();
        unregister();
    }
    private void unregister() {
        if (_PendingIntentList != null) {
            for (int i = 0; i < _PendingIntentList.size(); i++) {
                PendingIntent curIntent = (PendingIntent) _PendingIntentList.get(i);
                locManager.removeProximityAlert(curIntent);
                _PendingIntentList.remove(i);
            }
        }

        if (receiver != null)
        {
            locManager.removeUpdates(locationListener);
            unregisterReceiver(receiver);
            receiver = null;
        }

    }
}