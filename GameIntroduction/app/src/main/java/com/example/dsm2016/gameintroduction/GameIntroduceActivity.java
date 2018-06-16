package com.example.dsm2016.gameintroduction;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class GameIntroduceActivity extends AppCompatActivity {

    TextView gameName, num, time, meterial, coutury, place, rule;
    ListView localRule;
    RuleAdapter adapter;
    Button btn_addRule;
    ToggleButton btn_myGameChange;
    DBHelper dbHelper;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_introduce);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.gameintroduce_toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        gameName = (TextView) findViewById(R.id.text_gameName );
        num = (TextView) findViewById(R.id.text_num);
        time = (TextView) findViewById(R.id.text_time);
        meterial = (TextView) findViewById(R.id.text_meterial);
        place = (TextView) findViewById(R.id.text_place);
        coutury = (TextView) findViewById(R.id.text_contury);
        rule = (TextView) findViewById(R.id.text_rule);
        btn_myGameChange = (ToggleButton) findViewById(R.id.btn_myGameChange);
        localRule = (ListView) findViewById(R.id.listview_localRule);
        btn_addRule = (Button) findViewById(R.id.btn_addRule);
        adapter = new RuleAdapter();
        dbHelper = new DBHelper(getApplicationContext(), "MYGAME.db",null,1);

        //code 받아오기
        final Intent intent = getIntent();
        final String code = intent.getExtras().getString("code");

        //localRule Adapter 설정하기
        localRule.setAdapter(adapter);

        //코드로 게임정보 불러오기..
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("game").child(code).child("data");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GameData a = dataSnapshot.getValue(GameData.class);
                gameName.setText(a.getGameName());
                if(a.getNumberEnd().equals("~")){
                    num.setText(a.getNumberStart() + " 명 이상");
                }else if(a.getNumberStart().equals(a.getNumberEnd())){
                    num.setText(a.getNumberStart() + " 명");
                }else{
                    num.setText(a.getNumberStart() + " ~ " + a.getNumberEnd() + " 명");
                }

                time.setText(a.getTime() + " 분");
                if(a.getMaterial().equals("유")){
                    meterial.setText(a.getMaterial() + " - " + a.getMaterialText());
                }else{
                    meterial.setText(a.getMaterial());
                }
                place.setText(a.getPlace());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("game").child(code).child("rule");
        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String aa = dataSnapshot.getValue().toString();
                aa = aa.substring(1, aa.length() -1);
                String[] word = aa.split("=");
                coutury.setText(word[0]);
                rule.setText(word[1]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference().child("game").child(code).child("localRule");
        mDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    DataLocalRule temp = new DataLocalRule(child.getKey(), child.getValue().toString());
                    adapter.addItem(temp);
                }
                adapter.notifyDataSetChanged();
                setListViewHeightBasedOnItems(localRule);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(dbHelper.search(code)){
            btn_myGameChange.setChecked(true);
        }

        //btn_addRule
        btn_addRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), AddRuleActivity.class);
                intent1.putExtra("groupCode",code);
                startActivity(intent1);
            }
        });

        //내 게임 처리, btn_myGameChange
        btn_myGameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_myGameChange.isChecked()){
                    //내 게임이 아닐경우
                    dbHelper.insert(code);
                }else{
                    //이미 내 게임일경우
                    dbHelper.delete(code);
                }
            }
        });

    }

    public void setListViewHeightBasedOnItems(ListView listView) {

        // Get list adpter of listview;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)  return;

        int numberOfItems = listAdapter.getCount();

        // Get total height of all items.
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
            Log.w("aaaa",String.valueOf(totalItemsHeight));
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() *  (numberOfItems - 1);

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    //출처: http://gpark.tistory.com/entry/Android-ScrollView-안에-ListView-height-지정 [Gpark's Blog]
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
