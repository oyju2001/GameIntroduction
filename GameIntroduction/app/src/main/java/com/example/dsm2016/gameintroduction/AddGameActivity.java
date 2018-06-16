package com.example.dsm2016.gameintroduction;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class AddGameActivity extends AppCompatActivity {

    Toolbar toolbar;
    RadioGroup radio_material,radio_place;
    EditText input_rule, input_material, input_gameName, input_time;
    Spinner spinner_small, spinner_big, spinner_numStart, spinner_numEnd;
    ArrayAdapter<String> adspin_numStart, adspin_numEnd;
    Button btn_ok;
    AlertDialog.Builder alert;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        //툴바 백 액션
        toolbar = (Toolbar) findViewById(R.id.addgame_toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("게임 추가");
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //선언
        radio_material = (RadioGroup) findViewById(R.id.radio_material);
        radio_place = (RadioGroup) findViewById(R.id.radio_place);
        input_rule = (EditText) findViewById(R.id.input_rule);
        input_material = (EditText) findViewById(R.id.input_material);
        input_gameName = (EditText) findViewById(R.id.input_gameName);
        input_time = (EditText) findViewById(R.id.input_time);
        spinner_small = (Spinner) findViewById(R.id.spinner_small);
        spinner_big = (Spinner) findViewById(R.id.spinner_big);
        spinner_numStart = (Spinner) findViewById(R.id.spinner_numStart);
        spinner_numEnd = (Spinner) findViewById(R.id.spinner_numEnd);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        //스피너 어댑터 - 인원수
        adspin_numStart = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                (String[])getResources().getStringArray(R.array.numberStart));
        adspin_numStart.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner_numStart.setAdapter(adspin_numStart);
        spinner_numStart.setSelection(7);

        adspin_numEnd = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                (String[])getResources().getStringArray(R.array.numberEnd));
        adspin_numEnd.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner_numEnd.setAdapter(adspin_numEnd);
        spinner_numEnd.setSelection(8);

        //스피너 어댑터 - 지역
        Spinner_contury x = new Spinner_contury(spinner_big, spinner_small,this);
                x.setSpinner();

        //라디오버튼 - material
        radio_material.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radio_materialYes){
                    input_material.setVisibility(View.VISIBLE);
                }else if(checkedId == R.id.radio_materialNo) {
                    input_material.setVisibility(View.INVISIBLE);
                }
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
    }

    //ok버튼 눌렀을때
    public void gameAddOkay(View v){
        if(checkNull()){
            alert.setMessage("빈칸을 채워주세요");
            alert.show();
            return;
        }else if((!spinner_numEnd.getSelectedItem().toString().equals("~")) &&
                Integer.parseInt(spinner_numStart.getSelectedItem().toString()) > Integer.parseInt(spinner_numEnd.getSelectedItem().toString()) ){
            //인원수가 numEnd!=무한 && numstart > numend인가
            alert.setMessage("인원수를 확인해주세요");
            alert.show();
            return;
        }else if(Integer.parseInt(input_time.getText().toString()) == 0 ){
            alert.setMessage("시간이 너무 짧습니다");
            alert.show();
            return;
        }else if(Integer.parseInt(input_time.getText().toString()) >= 500 ){
            alert.setMessage("시간이 너무 깁니다");
            alert.show();
            return;
        }
        //gamename이 중복되는게 있나 확인해 준다.
        DatabaseReference mTestDatabase = FirebaseDatabase.getInstance().getReference("game");
        mTestDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                while (child.hasNext()){
                    if(child.next().getKey().equals(input_gameName.getText().toString())){
                        Log.w("aaa","겹치는 게임 이씀");
                        alert.setMessage("동일 이름의 게임이 있습니다.");
                        alert.show();
                        return;
                    }
                }
                //겹치는 게임이 없을경우 firebase에 넣어준다. gamename을 key로
                String key = input_gameName.getText().toString();
                GameData adds = new GameData();

                adds.setGameName(key);
                adds.setNumberStart(spinner_numStart.getSelectedItem().toString());
                adds.setNumberEnd(spinner_numEnd.getSelectedItem().toString());
                adds.setTime(input_time.getText().toString());
                adds.setMaterial(((RadioButton) findViewById(radio_material.getCheckedRadioButtonId())).getText().toString());
                if(radio_material.getCheckedRadioButtonId() == R.id.radio_materialYes){
                    adds.setMaterialText(input_material.getText().toString());
                }else{
                    adds.setMaterialText(null);
                }
                adds.setPlace(((RadioButton) findViewById(radio_place.getCheckedRadioButtonId())).getText().toString());

                mDatabase.child("game").child(key).child("data").setValue(adds);

                String key2 = spinner_big.getSelectedItem().toString() + " "+spinner_small.getSelectedItem().toString();
                mDatabase.child("game").child(key).child("rule").child(key2).setValue(input_rule.getText().toString());

                finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public boolean checkNull(){
        if(TextUtils.isEmpty(input_gameName.getText())){
            return true;
        }else if(spinner_numStart.getSelectedItem().toString().equals("선택")){
            return true;
        }else if(spinner_numEnd.getSelectedItem().toString().equals("선택")){
            return true;
        }else if(TextUtils.isEmpty(input_time.getText())) {
            return true;
        }else if(radio_material.getCheckedRadioButtonId() == -1){
            return true;
        }else if((radio_material.getCheckedRadioButtonId() == R.id.radio_materialYes) && (TextUtils.isEmpty(input_material.getText())) ){
                return true;
        }else if(radio_place.getCheckedRadioButtonId() == -1){
            return true;
        }else if(spinner_big.getSelectedItem().toString().equals("선택")){
            return true;
        }else if(TextUtils.isEmpty(input_rule.getText())){
            return true;
        }
        return false;
    }

}
