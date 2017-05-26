package com.iot.mymap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase db;
    int place_no = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabase("MyMap");
        createTable("MyList");

        Button button = (Button) findViewById(R.id.btnMap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
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
                    inflater.inflate(R.layout.sublayout_alarm, container,true);


                } else {
                    LinearLayout container = (LinearLayout) findViewById(R.id.container);
                   container.removeAllViewsInLayout();


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
                    container.removeAllViewsInLayout();

                }
        }});





        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertRecord("MyList");
                        executeRawQuery("MyList");
                    }
                }
        );

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord("MyList");
            }
        });
    }

    private void createDatabase(String dbName){
        try{
            db = openOrCreateDatabase(dbName, Activity.MODE_PRIVATE, null);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void createTable(String tableName){
        try{
            db.execSQL("create table if not exists "
                    + tableName
                    + " ( "
                    + " no integer, "
                    + " place text, "
                    + " count integer, "
                    + " distance integer, "
                    + " latitude text, "
                    + " longitude text );");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertRecord(String tableName){

        LinearLayout container =
                (LinearLayout) findViewById(R.id.container);
        LayoutInflater inflater =
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sublayout_distance,container,true);

        EditText placeInput = (EditText) findViewById(R.id.PLACE);
        EditText countInput = (EditText) findViewById(R.id.editText3);
        EditText distanceInput = (EditText) container.findViewById(R.id.editText2);
        String place = placeInput.getText().toString();
        int count = countInput.getInputType();
        int distance = distanceInput.getInputType();

        Intent send1 = getIntent();
        if (send1 != null) {
            Content content = (Content) send1.getSerializableExtra("content");
            String Latitude = content.getLatitude();
            String Longitude = content.getLongitude();
            try {
                place_no += 1;
                db.execSQL("insert into "
                        + tableName
                        + " ( no, place, count, distance, latitude, longitude ) "
                        + " values ( '" + place_no + "', '" + place + "', '" + count + "', '" + distance + "', '" + Latitude + "', '" + Longitude + "' );");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void executeRawQuery(String tableName){
        try {
            Cursor cursor = db.rawQuery(
                    "select count(*) as total from "
                            + tableName, null);
            cursor.moveToNext();
            Toast.makeText(getApplicationContext(),
                    cursor.getInt(0) + "개의 장소 입력 성공", Toast.LENGTH_SHORT).show();

            String[] columns = {"no", "place", "count", "distance", "latitude", "longitude"};
            Cursor c1 = db.query("MyList", columns, null, null, null, null, null);
            int recordCount = c1.getCount();
            for (int i = 0; i < recordCount; i++) {
                c1.moveToNext();
                int no = c1.getInt(0);
                String place = c1.getString(1);
                int count = c1.getInt(2);
                int distance = c1.getInt(3);
                String Latitude = c1.getString(4);
                String Longitude = c1.getString(5);
                Toast.makeText(getApplicationContext(),
                        place + ", " + count + "회, " + Longitude, Toast.LENGTH_SHORT).show();
            }

            sendData(c1.getInt(0), c1.getString(1), c1.getInt(2), c1.getInt(3), c1.getString(4), c1.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteRecord(String tableName){
        db.execSQL("drop table " + tableName);
        Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
    }

    public void sendData(int no2, String place2, int count2, int distance2, String Latitude2, String Longitude2){
        Intent send = new Intent(MainActivity.this, ListActivity.class);
        Content1 content1 = new Content1(no2, place2, count2, distance2, Latitude2, Longitude2);
        send.putExtra("content1", (Serializable) content1);
        startActivity(send);
    }
}
