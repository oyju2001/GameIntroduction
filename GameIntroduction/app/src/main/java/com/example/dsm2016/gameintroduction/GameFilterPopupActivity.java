package com.example.dsm2016.gameintroduction;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GameFilterPopupActivity extends Activity {

    EditText input_num, input_time;
    RadioGroup radio_material, radio_place;
    Button btn_search, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_filter_popup);


        input_num = (EditText) findViewById(R.id.input_num);
        input_time = (EditText) findViewById(R.id.input_time);
        radio_material = (RadioGroup) findViewById(R.id.radio_material);
        radio_place = (RadioGroup) findViewById(R.id.radio_place);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_back = (Button) findViewById(R.id.btn_back_popup);

    }

    public void onClose(View v){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void onGo(View v){
        if(TextUtils.isEmpty(input_num.getText())){
            Toast.makeText(getApplicationContext(), "인원수를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }else if(input_num.getText().equals("1")){
            Toast.makeText(getApplicationContext(), "1명이서 하는 게임은 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(input_time.getText())){
            Toast.makeText(getApplicationContext(), "시간을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }else if(radio_material.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "재료를 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }else if(radio_place.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "장소를 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        DataSearch temp = new DataSearch();
        temp.setNum(input_num.getText().toString());
        temp.setTime(input_time.getText().toString());
        temp.setMaterial(((RadioButton) findViewById(radio_material.getCheckedRadioButtonId())).getText().toString());
        temp.setPlace(((RadioButton) findViewById(radio_place.getCheckedRadioButtonId())).getText().toString());

        Intent intent = new Intent();
        intent.putExtra("result", (Parcelable) temp);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


}
