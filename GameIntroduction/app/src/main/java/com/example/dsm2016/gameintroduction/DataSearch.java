package com.example.dsm2016.gameintroduction;

import android.os.Parcel;
import android.os.Parcelable;

public class DataSearch implements Parcelable{

    private String num;
    private String place;
    private String time;
    private String material;

    public DataSearch(){}
    
    public DataSearch(String num, String time, String material, String place){
        this.num = num;
        this.time = time;
        this.material = material;
        this.place = place;
    }

    protected DataSearch(Parcel in) {
        num = in.readString();
        place = in.readString();
        time = in.readString();
        material = in.readString();
    }

    public static final Creator<DataSearch> CREATOR = new Creator<DataSearch>() {
        @Override
        public DataSearch createFromParcel(Parcel in) {
            return new DataSearch(in);
        }

        @Override
        public DataSearch[] newArray(int size) {
            return new DataSearch[size];
        }
    };

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(num);
        dest.writeString(place);
        dest.writeString(time);
        dest.writeString(material);
    }
}
