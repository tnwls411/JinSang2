package com.iot.mymap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase db;
//    TextView status = (TextView) findViewById(R.id.status);

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
//                        executeRawQuery("MyList");
                    }
                }
        );
    }

    private void createDatabase(String dbName){
        try{
            db = openOrCreateDatabase(dbName, Activity.MODE_PRIVATE, null);
//            print("데이터베이스 생성 성공");
        }
        catch(Exception e){
            e.printStackTrace();
//            print("데이터베이스 생성 실패");
//            print(e.getMessage().toString());
        }
    }

    private void createTable(String tableName){
        try{
            db.execSQL("create table if not exists "
                    + tableName
                    + " ( "
                    + " place text, "
                    + " count integer, "
                    + " distance integer);");
//            print("테이블 생성 성공");
        }
        catch (Exception e) {
            e.printStackTrace();
//            print("테이블 생성 실패");
//            print(e.getMessage().toString());
        }
    }

    private void insertRecord(String tableName){
//        EditText placeInput = (EditText) findViewById(R.id.placeInput);
//        EditText countInput = (EditText) findViewById(R.id.countInput);
//        EditText distanceInput = (EditText) findViewById(R.id.distanceInput);
//
//        String place = placeInput.getText().toString();
//        String count = countInput.getText().toString();
//        String distance = distanceInput.getText().toString();
        try {
            db.execSQL("insert into "
                    + tableName
                    + " ( place, count, distance ) "
                    + " values ( '" + "place" + "'" + ", '" + "count" +  "', '" +  "distance" +  "' );");
//            print("테이블 삽입 성공");
            Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            e.printStackTrace();
//            print("테이블 삽입 실패");
//            print(e.getMessage().toString());
        }
    }

//    private void executeRawQuery(String tableName){
//        try {
//            Cursor cursor = db.rawQuery(
//                    "select count(*) as total from "
//                            + tableName, null);
//            println("getCount : " + cursor.getCount());
//            cursor.moveToNext();
//            print("getIng : " + cursor.getInt(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//            print(tableName + "조회 실패");
//            print(e.getMessage().toString());
//        }
//    }

//    private void print(String msg){
//        status.append("\n"+msg);
//    }

}
