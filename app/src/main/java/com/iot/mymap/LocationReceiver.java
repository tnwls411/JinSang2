package com.iot.mymap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class LocationReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("#","onReceive");
        boolean isEntering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
        if(isEntering)
            Toast.makeText(context, "목표지점접근..", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "목표지점벗어남.", Toast.LENGTH_LONG).show();
    }
}
