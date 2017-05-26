package com.iot.mymap;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LocationAlertActivity extends MapActivity
{
    LocationReceiver receiver;

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
        receiver = new LocationReceiver();
        IntentFilter filter = new IntentFilter(intentKey);
        registerReceiver(receiver, filter);
    }


    public void onStop()   //해제
    {
        super.onStop();
        unregister();
//        locManager.removeUpdates(locationListener);
//        unregisterReceiver(receiver);
//        receiver=null;
    }
    private void unregister() {
        if (_PendingIntentList != null) {
            for (int i = 0; i < _PendingIntentList.size(); i++) {
                PendingIntent curIntent = (PendingIntent) _PendingIntentList.get(i);
                locManager.removeProximityAlert(curIntent);
                _PendingIntentList.remove(i);
            }
        }

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}