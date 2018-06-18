package com.example.dsm2016.gameintroduction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocalRulePopupActivity extends Activity {

    TextView contury, rule;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_local_rule_popup);

        contury = (TextView) findViewById(R.id.text_localRuleContury);
        rule = (TextView) findViewById(R.id.text_localRuleContent);

        intent = getIntent();
        DataLocalRule data = (DataLocalRule) intent.getParcelableExtra("code");

        contury.setText(data.getLocalPlace());
        rule.setText(data.getLocalRule());

    }
}
