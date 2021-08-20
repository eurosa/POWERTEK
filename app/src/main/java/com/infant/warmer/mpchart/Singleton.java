package com.infant.warmer.mpchart;

public class Singleton {

    private static Singleton instance;

    public String getSkinTempData() {
        return skinTempData;
    }

    public void setSkinTempData(String skinTempData) {
        this.skinTempData = skinTempData;
    }

    public String skinTempData;

    public String getAirtTempData() {
        return airtTempData;
    }

    public void setAirtTempData(String airtTempData) {
        this.airtTempData = airtTempData;
    }

    public String airtTempData;

    private Singleton(){

    }

    public static Singleton getInstance(){
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }
    // your fields, getter and setter here
}
