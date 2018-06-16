package com.example.dsm2016.gameintroduction;

/**
 * Created by dsm2016 on 2018-05-16.
 */

public class DataResult {
    private String name;
    private String numStart;
    private String numEnd;
    private String time;
    private String isMaterial;
    private String place;

    public String getName(){return name;}
    public String getIsMaterial(){return isMaterial;}
    public String getPlace(){return place;}
    public String getNumStart() {
        return numStart;
    }

    public String getNumEnd() {
        return numEnd;
    }

    public String getTime() {
        return time;
    }

    public DataResult(String name, String numStart, String numEnd, String time, String isMaterial, String place){
        this.name=name;
        this.numStart=numStart;
        this.numEnd=numEnd;
        this.time = time;
        this.isMaterial=isMaterial;
        this.place=place;
    }
}
