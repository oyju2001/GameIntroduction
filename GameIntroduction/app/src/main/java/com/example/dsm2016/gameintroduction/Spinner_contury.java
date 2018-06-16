package com.example.dsm2016.gameintroduction;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Context;


/**
 * Created by dsm2016 on 2018-05-11.
 */

public class Spinner_contury{

    Spinner spinner_big;
    Spinner spinner_small;
    Context content;

    public Spinner_contury(Spinner a, Spinner b, android.content.Context c){
        spinner_big = a;
        spinner_small = b;
        content = c;
    }

    public void setSpinner(){
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(content, R.array.big_place,
                android.R.layout.simple_list_item_1);
        fAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner_big.setAdapter(fAdapter);
        spinner_big.setOnItemSelectedListener(spinSelectedlistener);
        spinner_big.setSelection(17);
    }

    public void setSpinner(int itemNum){
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(content, itemNum,
                android.R.layout.simple_list_item_1);
        fAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner_small.setAdapter(fAdapter);
    }

    public AdapterView.OnItemSelectedListener spinSelectedlistener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    switch (position) {
                        case (0):
                            setSpinner(R.array.Gangwondo); break;
                        case (1):
                            setSpinner(R.array.Gyeonggido); break;
                        case (2):
                            setSpinner(R.array.Gyeongsangnamdo); break;
                        case (3):
                            setSpinner(R.array.Gyeongsangbukdo); break;
                        case (4):
                            setSpinner(R.array.Gwangju); break;
                        case (5):
                            setSpinner(R.array.Daegu); break;
                        case (6):
                            setSpinner(R.array.Daejeon); break;
                        case (7):
                            setSpinner(R.array.Busan); break;
                        case (8):
                            setSpinner(R.array.Seoul); break;
                        case (9):
                            setSpinner(R.array.Sejong); break;
                        case (10):
                            setSpinner(R.array.Ulsan); break;
                        case (11):
                            setSpinner(R.array.Incheon); break;
                        case (12):
                            setSpinner(R.array.Jeollanamdo); break;
                        case (13):
                            setSpinner(R.array.Jeollabukdo); break;
                        case (14):
                            setSpinner(R.array.Jeju); break;
                        case (15):
                            setSpinner(R.array.Chungcheongnamdo); break;
                        case (16):
                            setSpinner(R.array.Chungcheongbukdo); break;
                        case (17):
                            setSpinner(R.array.default_array); break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }

            };
}
