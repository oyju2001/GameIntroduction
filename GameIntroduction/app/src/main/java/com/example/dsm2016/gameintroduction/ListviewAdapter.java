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

    private ArrayList<GameData> data = new ArrayList<>();

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
        TextView time=(TextView)convertView.findViewById(R.id.text_time);
        TextView isMaterial=(TextView)convertView.findViewById(R.id.text_material);
        TextView place=(TextView)convertView.findViewById(R.id.text_place);

        GameData listviewitem = data.get(position);

        name.setText(listviewitem.getGameName());
        numStart.setText(listviewitem.getNumberStart());
        time.setText(listviewitem.getTime());
        isMaterial.setText(listviewitem.getMaterial());
        place.setText(listviewitem.getPlace());

        return convertView;
    }

    public void addItem(GameData temp){
        String arr;
        if(temp.getNumberEnd().equals("~")){
            arr = temp.getNumberStart()+" 명 이상 | ";
        }else if(temp.getNumberStart().equals(temp.getNumberEnd())){
            arr =temp.getNumberStart()+ " 명 | ";
        }else{
            arr = temp.getNumberStart() +" ~ " + temp.getNumberEnd()+ " 명 | ";
        }

        data.add(new GameData(temp.getGameName(), arr, temp.getNumberEnd(), temp.getTime(), temp.getMaterial(), temp.getPlace()));
    }

    public void addItem(String name, String numStart, String numEnd, String time, String isMaterial, String place){
        String arr;
        if(numEnd.equals("~")){
            arr = numStart+" 명 이상 | ";
        }else if(numStart.equals(numEnd)){
            arr =numStart+ " 명 | ";
        }else{
            arr = numStart +" ~ " + numEnd+ " 명 | ";
        }

        data.add(new GameData(name, arr, numEnd, time, isMaterial, place));
    }

    public void clear(){ data.clear();}
}
