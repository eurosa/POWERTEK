package com.power.tek;

import java.io.Serializable;

public class DataModel implements Serializable {

    // private variables
    public int _id;



    public String getSkinTempValue() {
        return skinTempValue;
    }

    public void setSkinTempValue(String skinTempValue) {
        this.skinTempValue = skinTempValue;
    }

    public String skinTempValue;

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public String current_time;

    public String getAirTempValue() {
        return airTempValue;
    }

    public void setAirTempValue(String airTempValue) {
        this.airTempValue = airTempValue;
    }

    public String airTempValue;


    public DataModel() {
        super();
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

}
