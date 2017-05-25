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
        //ProximityAlert를 통해 수신한 브로드캐스트메시지를 포함하며
        //LocationManager.KEY_PROXIMITY_ENTERING를 통해
        //전달 받을 수 있으며 이 값이 true면 지정한 지점 근접, false면 지정한 지점 벗어났다는 의미미
        Log.d("#","onReceive");
        boolean isEntering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
        if(isEntering)
            Toast.makeText(context, "목표지점접근..", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "목표지점벗어남.", Toast.LENGTH_LONG).show();
    }
}
