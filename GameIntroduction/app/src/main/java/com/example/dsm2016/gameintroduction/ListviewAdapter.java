package com.example.dsm2016.gameintroduction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2018-05-16.
 */

public class ListviewAdapter extends BaseAdapter {

    private ArrayList<DataResult> data = new ArrayList<>();

    public ListviewAdapter(){ }
    @Override
    public int getCount(){return data.size();}
    @Override
    public Object getItem(int position){return data.get(position);}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.result_listview, parent, false);
        }

        TextView name=(TextView)convertView.findViewById(R.id.text_name);
        TextView numStart=(TextView)convertView.findViewById(R.id.text_numStart);
        TextView numEnd=(TextView)convertView.findViewById(R.id.text_numEnd);
        TextView time=(TextView)convertView.findViewById(R.id.text_time);
        TextView isMaterial=(TextView)convertView.findViewById(R.id.text_material);
        TextView place=(TextView)convertView.findViewById(R.id.text_place);

        DataResult listviewitem = data.get(position);

        name.setText(listviewitem.getName());
        numStart.setText(listviewitem.getNumStart());
        numEnd.setText(listviewitem.getNumEnd());
        time.setText(listviewitem.getTime());
        isMaterial.setText(listviewitem.getIsMaterial());
        place.setText(listviewitem.getPlace());

        return convertView;
    }

    public void addItem(DataResult temp){
        data.add(temp);
    }

    public void addItem(String name, String numStart, String numEnd, String time, String isMaterial, String place){
        data.add(new DataResult(name, numStart, numEnd, time, isMaterial, place));
    }

    public void clear(){ data.clear();}
}
