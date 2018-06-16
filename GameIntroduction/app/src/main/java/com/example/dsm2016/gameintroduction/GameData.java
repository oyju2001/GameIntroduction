package com.example.dsm2016.gameintroduction;

public class GameData {
    private String gameName;
    private String numberStart;
    private String numberEnd;
    private String time;
    private String material;
    private String materialText;
    private String place;

    public GameData(){}

    public GameData(String gameName, String numberStart, String numbserEnd, String time,
                    String material, String materialText, String place){
        this.gameName = gameName;
        this.numberStart = numberStart;
        this.numberEnd = numbserEnd;
        this.time = time;
        this.material = material;
        this.materialText = materialText;
        this.place = place;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getNumberStart() {
        return numberStart;
    }

    public void setNumberStart(String numberStart) {
        this.numberStart = numberStart;
    }

    public String getNumberEnd() {
        return numberEnd;
    }

    public void setNumberEnd(String numberEnd) {
        this.numberEnd = numberEnd;
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

    public String getMaterialText() {
        return materialText;
    }

    public void setMaterialText(String materialText) {
        this.materialText = materialText;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
