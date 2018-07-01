package com.example.dsm2016.gameintroduction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class GameFilterActivity extends AppCompatActivity {
    Toolbar toolbar;
    AlertDialog.Builder alert;
    ListView listView;
    ListviewAdapter adapter;
    Button open_popup;
    TextView place, num, material, time;
    LinearLayout viewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_filter);
        toolbar = (Toolbar) findViewById(R.id.gamefilter_toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("게임 분류");
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        open_popup = (Button) findViewById(R.id.open_popup);
        num = (TextView) findViewById(R.id.filter_num);
        time = (TextView) findViewById(R.id.filter_time);
        material = (TextView) findViewById(R.id.filter_meterial);
        place = (TextView) findViewById(R.id.filter_place);
        viewState = (LinearLayout) findViewById(R.id.viewState);

        //alert다이어그램
        alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        //결과 보여주는 ListView
        listView = (ListView)findViewById(R.id.resultList);
        adapter = new ListviewAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //리스트뷰중 하나 눌렀을때 이벤트
                GameData name = (GameData) adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), GameIntroduceActivity.class);
                intent.putExtra("code", name.getGameName());
                startActivity(intent);
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

    public void OnPopupClick(View v){
        Intent intent = new Intent(this, GameFilterPopupActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DataSearch search = null;
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                search = (DataSearch) data.getParcelableExtra("result");
                //state 바꿔줌
                place.setText(search.getPlace());
                num.setText(search.getNum() + "명");
                material.setText(search.getMaterial());
                time.setText(search.getTime()+" 분");
                viewState.setVisibility(View.VISIBLE);

                //검색 후 등록

                final ProgressDialog pd = new ProgressDialog(GameFilterActivity.this);
                pd.setMessage("검색중");
                pd.show();

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("game");
                //데이터베이스에서 불러온다.
                final DataSearch finalSearch = search;
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                        while (child.hasNext()) {
                            GameData gameData = child.next().child("data").getValue(GameData.class);
                            Log.w("aaaa", gameData.getGameName());
                            //다 들고옴
                            if (gameData.getMaterial().equals(finalSearch.getMaterial()) || finalSearch.getMaterial().equals("공통")) {
                                //재료가 같거나 search가 공통일때

                                if(gameData.getPlace().equals(finalSearch.getPlace()) || finalSearch.getPlace().equals("공통")){
                                    //장소가 같거나 search가 공통일때

                                    int searchTime = Integer.parseInt(finalSearch.getTime());
                                    int dataTime = Integer.parseInt(gameData.getTime());
                                    int checktime = 20;
                                    if(( searchTime + checktime ) >= dataTime && dataTime >= (searchTime - checktime ) ){
                                        //시간이 플마 20분일때;
                                        int dataNumStart = Integer.parseInt(gameData.getNumberStart());
                                        int searchNum = Integer.parseInt(finalSearch.getNum());
                                        if(gameData.getNumberEnd().equals("~")){
                                            //자료의 end가 ~ 일경우
                                            if(searchNum >= 8 || dataNumStart <= searchNum) {
                                                //인원수가 8명 이상이거나, 검색인원보다 같거나 많을 경우
                                                adapter.addItem(gameData);
                                            }
                                        }else{
                                            int dataNumEnd = Integer.parseInt(gameData.getNumberEnd());
                                            if(searchNum >= dataNumStart && searchNum <= dataNumEnd ){
                                                //start와 end 사이에 있을경우
                                                adapter.addItem(gameData);
                                            }
                                        }
                                    }
                                }


                            }
                            pd.dismiss();
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        }
    }

}
