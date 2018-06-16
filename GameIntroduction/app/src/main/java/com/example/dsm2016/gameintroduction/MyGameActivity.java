package com.example.dsm2016.gameintroduction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyGameActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listviews;
    ArrayList<String> items;
    ArrayAdapter adapter;
    DBHelper dbHelper;
    //Intent intent = new Intent(this, GameIntroduceActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game);
        toolbar = (Toolbar) findViewById(R.id.mygame_toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("내 게임");
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listviews = (ListView) findViewById(R.id.listviews);
        items = new ArrayList<String>() ;

        //Local DB에서 List 받아와서 items에 넣어주기
        dbHelper = new DBHelper(getApplicationContext(), "MYGAME.db",null,1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MYGAME", null);
        while (cursor.moveToNext()) {
            items.add(cursor.getString(1));
        }

        adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1, items) ;
        listviews.setAdapter(adapter);

        listviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //리스트뷰중 하나 눌렀을때 이벤트
                String name = items.get(position);

                Intent intent = new Intent(MyGameActivity.this, GameIntroduceActivity.class);
                intent.putExtra("code",name);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
