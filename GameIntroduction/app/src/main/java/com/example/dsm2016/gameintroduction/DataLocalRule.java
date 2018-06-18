package com.example.dsm2016.gameintroduction;

import android.os.Parcel;
import android.os.Parcelable;

public class DataLocalRule implements Parcelable {
    private String localPlace;
    private String localRule;

    public DataLocalRule(String localPlace, String localRule){
        this.localPlace = localPlace;
        this.localRule = localRule;
    }

    protected DataLocalRule(Parcel in) {
        localPlace = in.readString();
        localRule = in.readString();
    }

    public static final Creator<DataLocalRule> CREATOR = new Creator<DataLocalRule>() {
        @Override
        public DataLocalRule createFromParcel(Parcel in) {
            return new DataLocalRule(in);
        }

        @Override
        public DataLocalRule[] newArray(int size) {
            return new DataLocalRule[size];
        }
    };

    public String getLocalPlace() {
        return localPlace;
    }

    public void setLocalPlace(String localPlace) {
        this.localPlace = localPlace;
    }

    public String getLocalRule() {
        return localRule;
    }

    public void setLocalRule(String localRule) {
        this.localRule = localRule;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localPlace);
        dest.writeString(localRule);
    }
}
