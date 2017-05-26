package com.iot.mymap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;


/**
 * 브로드캐스팅 메시지를 받았을 때 처리할 수신자 정의
 */
public class LocationReceiver extends BroadcastReceiver
{
    private String _ExpectedAction;
    private Intent _LastReceivedIntent;

    public LocationReceiver(String expectedAction) {
        _ExpectedAction = expectedAction;
        _LastReceivedIntent = null;
    }

    public IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter(_ExpectedAction);
        return filter;
    }
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //ProximityAlert를 통해 수신한 브로드캐스트메시지를 포함하며
        //LocationManager.KEY_PROXIMITY_ENTERING를 통해
        //전달 받을 수 있으며 이 값이 true면 지정한 지점 근접, false면 지정한 지점 벗어났다는 의미미
        if (intent != null)
        {
            Log.d("#", "onReceive");
            _LastReceivedIntent = intent;
            int id = intent.getIntExtra("id", 0);
            boolean isEntering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
            if (isEntering)
                Toast.makeText(context, "# " + id + "번" + " 금지구역접근!!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "# " + id + "번" + " 금지구역에서 벗어났습니다~", Toast.LENGTH_LONG).show();
        }
    }

    public Intent getLastReceivedIntent() {
        return _LastReceivedIntent;
    }

    public void clearReceivedIntents() {
        _LastReceivedIntent = null;
    }
}
