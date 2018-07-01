package com.example.dsm2016.gameintroduction;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class AddRuleActivity extends AppCompatActivity {
    TextView text_gameName;
    Spinner spinner_small, spinner_big;
    Button btn_submit,btn_back;
    EditText input_rule;
    String localGroupCode;
    AlertDialog.Builder alert;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCheck = FirebaseDatabase.getInstance().getReference().child("game");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_rule_popup);

        final Intent intent = getIntent();
        localGroupCode =  intent.getExtras().getString("groupCode");

        text_gameName = (TextView) findViewById(R.id.text_AddRuleGameName);
        spinner_small = (Spinner) findViewById(R.id.spinner_small);
        spinner_big = (Spinner) findViewById(R.id.spinner_big);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_back = (Button) findViewById(R.id.btn_back);
        input_rule = (EditText) findViewById(R.id.input_rule);

        //기본 설정
        text_gameName.setText(localGroupCode);

        Spinner_contury x = new Spinner_contury(spinner_big, spinner_small,this);
        x.setSpinner();

        //alert다이어그램
        alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        //submit button 클릭이벤트
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //내용 비었나 확인
                if(spinner_big.getSelectedItem().toString().equals("선택")){
                    alert.setTitle("지역을 선택해주세요.");
                    alert.show();
                    return;
                }else if(TextUtils.isEmpty(input_rule.getText().toString().trim())){
                    alert.setTitle("규칙을 입력해주세요.");
                    alert.show();
                    return;
                }

                //스피너 지역 확인 - 1 (Rule에 있나)
                final String contury = spinner_big.getSelectedItem().toString() + " "+spinner_small.getSelectedItem().toString();
                mCheck.child(localGroupCode).child("rule").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String aa = dataSnapshot.getValue().toString();
                        aa = aa.substring(1);
                        String[] word = aa.split("=");
                        if(contury.equals(word[0])){
                            alert.setTitle("지역이 이미 있습니다.");
                            alert.show();
                            return;
                        }

                        //스피너 지역 확인 - 2 (localRule에 있나)
                        DatabaseReference mCheck2 = FirebaseDatabase.getInstance().getReference().child("game").child(localGroupCode).child("localRule");
                        mCheck2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                                while (child.hasNext()){
                                    if(contury.equals(child.next().getKey().toString())){
                                        alert.setTitle("지역이 이미 있습니다.");
                                        alert.show();
                                        return;
                                    }
                                }
                                alert.setTitle("insert 되었습니다.");
                                alert.show();
                                //이것도 통과하면 insert DB
                                mDatabase.child("game").child(localGroupCode).child("localRule").
                                        child(contury).setValue(input_rule.getText().toString());
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        //back button 클릭이벤트
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



}
