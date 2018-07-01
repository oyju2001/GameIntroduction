package com.example.dsm2016.gameintroduction;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocalRulePopupActivity extends Activity {

    TextView contury, rule;
    Intent intent;
    Button popup_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_local_rule_popup);

        contury = (TextView) findViewById(R.id.text_localRuleContury);
        rule = (TextView) findViewById(R.id.text_localRuleContent);
        popup_close = (Button) findViewById(R.id.popup_close);

        intent = getIntent();
        DataLocalRule data = (DataLocalRule) intent.getParcelableExtra("code");

        contury.setText(data.getLocalPlace());
        rule.setText(data.getLocalRule());

        popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
