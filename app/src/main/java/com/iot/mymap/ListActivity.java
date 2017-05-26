package com.iot.mymap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final ArrayList<String> items = new ArrayList<String>();
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);

        // listview 생성 및 adapter 지정.
//        final ListView listview = (ListView) findViewById(R.id.listView) ;
//        listview.setAdapter(adapter) ;

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting = new Intent(ListActivity.this, MainActivity.class);
                startActivity(setting);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView show = (TextView) findViewById(R.id.textView13);
        show.append("new, 2회, 126.4X37.2\n");
//        Intent send2 = getIntent();
//        if (send2 != null) {
//            Content1 content1 = (Content1) send2.getSerializableExtra("content1");
//            String place2 = content1.getPlace2();
//            int count2 = content1.getCount2();
//            String Latitude2 = content1.getLatitude2();
//            String Longitude2 = content1.getLongitude2();
//            show.append(place2 + ", " + count2 + "회, " + Latitude2 + "X" + Longitude2);
//
//        }
    }

}
