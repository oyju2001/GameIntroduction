package com.example.dsm2016.gameintroduction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RuleAdapter extends BaseAdapter {

    private ArrayList<DataLocalRule> data = new ArrayList<>();

    @Override
    public int getCount() { return data.size(); }
    @Override
    public Object getItem(int position) { return data.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.localrule_listview, parent, false);
        }

        TextView localPlace = (TextView)convertView.findViewById(R.id.text_localPlace);
        TextView localRule=(TextView)convertView.findViewById(R.id.text_localRule);

        DataLocalRule listviewitem = data.get(position);

        localPlace.setText(listviewitem.getLocalPlace());
        localRule.setText(listviewitem.getLocalRule());

        return convertView;
    }

    public void addItem(DataLocalRule a){
        data.add(a);
    }
    public void addItem(String a, String b){
        data.add(new DataLocalRule(a,b));
    }
    public void clear(){
        data.clear();
    }
}
