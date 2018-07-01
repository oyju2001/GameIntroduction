package com.example.dsm2016.gameintroduction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText input_gameName;
    Button btn_submit;
    ListView listview_search;
    ListviewAdapter adapter;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("game");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.search_toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("게임 검색");
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        input_gameName = (EditText) findViewById(R.id.input_searchGameName);
        btn_submit = (Button) findViewById(R.id.btn_searchSubmit);
        listview_search = (ListView) findViewById(R.id.listview_search);
        adapter = new ListviewAdapter();
        listview_search.setAdapter(adapter);

        listview_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void SearchBtn(View view){
        adapter.clear();
        //비어있나 확인 후 (스페이스..제거)
        if(TextUtils.isEmpty(input_gameName.getText().toString().trim())) {
            Toast.makeText(this,"aaa",Toast.LENGTH_SHORT).show();
            return;
        }

        //데이터베이스에서 불러온다.
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                while (child.hasNext()){
                    GameData aa = child.next().child("data").getValue(GameData.class);
                    //다 들고옴
                    if(aa.getGameName().contains(input_gameName.getText().toString().trim())){
                        //같을 경우에는
                        adapter.addItem(aa.getGameName(),aa.getNumberStart(),aa.getNumberEnd(),aa.getTime(),aa.getMaterial(),aa.getPlace());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return;
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
