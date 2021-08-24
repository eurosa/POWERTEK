package com.infant.warmer.mDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.infant.warmer.DataModel;


import java.util.ArrayList;

/**
 * DATABASE ADAPTER CLASS
 */
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    /*
    1. INITIALIZE DB HELPER AND PASS IT A CONTEXT

     */
    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }


    /*
    SAVE DATA TO DB
     */
    public boolean saveSpacecraft(DataModel model) {
        try {
            db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(Constants.KEY_SKIN_TEMP, model.getSkinTempValue());
            cv.put(Constants.KEY_ARI_TEMP, model.getAirTempValue());
            cv.put(Constants.KEY_HM, model.getCurrent_time());


            long result = db.insert(Constants.TB_NAME, Constants.KEY_ID, cv);
            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

        return false;
    }

    /*
     1. RETRIEVE SPACECRAFTS FROM DB AND POPULATE ARRAYLIST
     2. RETURN THE LIST
     */

    public ArrayList<DataModel> retrieveSpacecrafts()
    {
        ArrayList<DataModel> spacecrafts=new ArrayList<>();

        String[] columns={Constants.KEY_ID,Constants.KEY_SKIN_TEMP,Constants.KEY_ARI_TEMP,Constants.KEY_HM};

        try
        {
            db = helper.getWritableDatabase();
            Cursor c=db.query(Constants.TB_NAME,columns,null,null,null,null,null);

            DataModel s;

            if(c != null)
            {
                while (c.moveToNext())
                {
                    String s_name=c.getString(1);
                    String s_propellant=c.getString(2);
                    String s_destination=c.getString(3);


                    s=new DataModel();
                    s.setSkinTempValue(s_name);
                    s.setAirTempValue(s_propellant);
                    s.setCurrent_time(s_destination);

                    spacecrafts.add(s);
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return spacecrafts;
    }

}
