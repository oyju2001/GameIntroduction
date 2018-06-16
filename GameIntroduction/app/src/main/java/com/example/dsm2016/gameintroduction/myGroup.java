package com.example.dsm2016.gameintroduction;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2018-05-10.
 */

public class myGroup {
    public ArrayList<String> child;
    public String groupName;
    public ImageView imageView;
    myGroup(String name, ImageView imageView){
        this.groupName = name;
        this.imageView = imageView;
        child = new ArrayList<String>();
    }
}
