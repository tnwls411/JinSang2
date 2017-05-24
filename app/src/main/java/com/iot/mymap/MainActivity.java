package com.iot.mymap;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btnMap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        final CheckBox setdstnce = (CheckBox) findViewById(R.id.setdstnce);
        final CheckBox setalarm = (CheckBox) findViewById(R.id.setalarm);
        setalarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    setdstnce.setChecked(false);

                    LinearLayout container = (LinearLayout) findViewById(R.id.container);
                    LayoutInflater inflater =
                            (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.sublayout_alarm, container, true);


                } else {
                    LinearLayout container = (LinearLayout) findViewById(R.id.container);
                    LayoutInflater inflater =
                            (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.sublayout_alarm, container, false);

                }
            }
        });
        setdstnce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setalarm.setChecked(false);
                    LinearLayout container = (LinearLayout) findViewById(R.id.container);
                    LayoutInflater inflater =
                            (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.sublayout_distance, container, true);
            }else {
                    LinearLayout container = (LinearLayout) findViewById(R.id.container);
                    LayoutInflater inflater =
                            (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.sublayout_alarm, container, false);

                }
        }});






      //  EditText distance = (EditText) findViewById(R.id.distance);


    }

}
