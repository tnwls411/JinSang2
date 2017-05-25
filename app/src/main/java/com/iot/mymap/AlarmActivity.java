package com.iot.mymap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Oracle on 2017-05-24.
 */

public class AlarmActivity extends FragmentActivity {
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    AlarmManager alarmManager;
    TimePicker alarm_timepicker;
    TextClock update_time;
    PendingIntent pendingIntent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sublayout_alarm);
        this.context = this;
        update_time = (TextClock) findViewById(R.id.textClock);
        Calendar calendar = Calendar.getInstance();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(AlarmActivity.this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this,0,intent,0);


        View.OnClickListener listener1 = new View.OnClickListener() {
            public void onClick(View view) {
              //  textView2.setText("");
                Bundle bundle = new Bundle();

                bundle.putInt(MyConstants.HOUR, timeHour);
                bundle.putInt(MyConstants.MINUTE, timeMinute);
                MyDialogFragment fragment = new MyDialogFragment(new MyHandler());
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment, MyConstants.TIME_PICKER);
                transaction.commit();
            }
        };

        Button btn1 = (Button)findViewById(R.id.set_time);
        btn1.setOnClickListener(listener1);
    }

//        View.OnClickListener listener2 = new View.OnClickListener() {
//            public void onClick(View view) {
//                textView2.setText("");
//                cancelAlarm();
//            }
//        };
//        Button btn2 = (Button)findViewById(R.id.button2);
//        btn2.setOnClickListener(listener2);
//    }




    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage (Message msg){
            Bundle bundle = msg.getData();
            int timeHour = bundle.getInt(MyConstants.HOUR);
            timeMinute = bundle.getInt(MyConstants.MINUTE);
            update_time.setText(timeHour + ":" + timeMinute);
            setAlarm();
        }
    }
    }


//    private void cancelAlarm() {
//        if (alarmManager!= null) {
//            alarmManager.cancel(pendingIntent);
//        }
//    }
//    }

