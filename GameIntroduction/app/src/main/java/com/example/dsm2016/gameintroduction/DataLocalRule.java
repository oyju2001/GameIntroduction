package com.example.dsm2016.gameintroduction;

public class DataLocalRule {
    private String localPlace;
    private String localRule;

    public DataLocalRule(String localPlace, String localRule){
        this.localPlace = localPlace;
        this.localRule = localRule;
    }

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
}
