package com.example.dsm2016.gameintroduction;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;

public class GameFilterActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;
    AlertDialog.Builder alert;

    String place, num, material, time;
    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_filter);
        toolbar = (Toolbar) findViewById(R.id.gamefilter_toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("게임 분류");
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //선언
        imageView = (ImageView) findViewById(R.id.imageView);

        //expandAdapter 제작
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        final ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView)findViewById(R.id.mylist);
        myGroup temp = new myGroup("분류",imageView);
        temp.child.add("ㄱ");
        DataList.add(temp);

        final ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.parent_listview,R.layout.child_listview,DataList);
        listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);

        // 그룹이 열릴 경우 이벤트 발생
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                adapter.setListPressed(true);
            }
        });

        // 그룹이 닫힐 경우 이벤트 발생
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                adapter.setListPressed(false);
            }
        });

        //alert다이어그램
        alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        //ExpandAdapter에서 값 받아와서 DB 검색 후 ListView에 출력해줘야 한다.


        //검색

        //결과 보여주는 ListView
        ListView listView=(ListView)findViewById(R.id.resultList);
        ListviewAdapter adapter2 = new ListviewAdapter();
        listView.setAdapter(adapter2);

        adapter2.addItem("one","one1","one2","one3","one3","obe1");
        adapter2.notifyDataSetChanged();

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
