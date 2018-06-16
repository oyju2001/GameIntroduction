package com.example.dsm2016.gameintroduction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    TextView textView;
    Button btn_myGameActivity;
    Button btn_filterActivity;
    Button btn_addGameActivity;
    Button btn_searchActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = (TextView) findViewById(R.id.text_title);
        btn_myGameActivity = (Button) findViewById(R.id.btn_myGameActivity);
        btn_filterActivity = (Button) findViewById(R.id.btn_filterActivity);
        btn_addGameActivity = (Button) findViewById(R.id.btn_addGameActivity);
        btn_searchActivity = (Button) findViewById(R.id.btn_searchActivity);
    }

    public void searchActivity(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void myGameActivity(View view){
        Intent intent = new Intent(this, MyGameActivity.class);
        startActivity(intent);
    }

    public void filterActivity(View view){
        Intent intent = new Intent(this, GameFilterActivity.class);
        startActivity(intent);
    }

    public void addGameActivity(View view){
        Intent intent = new Intent(this, AddGameActivity.class);
        startActivity(intent);
    }
}
