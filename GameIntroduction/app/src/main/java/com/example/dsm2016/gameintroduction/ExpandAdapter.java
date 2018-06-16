package com.example.dsm2016.gameintroduction;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.dsm2016.gameintroduction.R.layout.activity_game_filter;

public class ExpandAdapter extends BaseExpandableListAdapter{

    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<myGroup> DataList;
    private LayoutInflater myinf = null;
    private boolean listPressed = false;

    public void setListPressed(boolean pState){
        listPressed = pState;
        notifyDataSetChanged();
    }

    public ExpandAdapter(Context context,int groupLay,int chlidLay,ArrayList<myGroup> DataList ){
        this.DataList = DataList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }
        TextView groupName = (TextView)convertView.findViewById(R.id.groupName);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        groupName.setText(DataList.get(groupPosition).groupName);
        if(listPressed){
            imageView.setImageResource(R.drawable.ic_arrow_drop_up_black_30dp);
        }else{
            imageView.setImageResource(R.drawable.ic_arrow_drop_down_black_30dp);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }
        TextView childName = (TextView)convertView.findViewById(R.id.childName);
        childName.setText(DataList.get(groupPosition).child.get(childPosition));

        Button btn_search = (Button) convertView.findViewById(R.id.btn_search);
        final RadioGroup radio_material = (RadioGroup) convertView.findViewById(R.id.radio_material);
        final RadioGroup radio_place = (RadioGroup) convertView.findViewById(R.id.radio_place);
        final EditText input_num = (EditText) convertView.findViewById(R.id.input_num);
        final EditText input_time = (EditText) convertView.findViewById(R.id.input_time);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(input_num.getText())){
                    Toast.makeText(context, "인원수를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(input_time.getText())){
                    Toast.makeText(context, "시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if(radio_material.getCheckedRadioButtonId() == -1){
                    Toast.makeText(context, "재료를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if(radio_place.getCheckedRadioButtonId() == -1){
                    Toast.makeText(context, "장소를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
                //여기서 gamefilteractivity로 값을 전달해 줘야 되는데...

            }
        });


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.size();
    }

    @Override
    public myGroup getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

}
