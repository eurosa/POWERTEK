package com.infant.warmer;

import android.content.Context;


import com.infant.warmer.mDB.DBAdapter;

import java.util.ArrayList;

/**
 * TABLE HELPER CLASS. GETS ARRAYLIST FROM SQLITE DATABASE AND RETURNS A MULTIDIMENSIONAL ARRAY FOR BINDING TO OUR ADAPTER
 */
public class TableHelper {

    //DECLARATIONS
    Context c;
    private String[] spaceProbeHeaders={"SKin Temp","Air Temp","Date & Time"};
    private String[][] spaceProbes;

    //CONSTRUCTOR
    public TableHelper(Context c) {
        this.c = c;
    }

    //RETURN TABLE HEADERS
    public String[] getSpaceProbeHeaders()
    {
        return spaceProbeHeaders;
    }

    //RETURN TABLE ROWS
    public  String[][] getSpaceProbes()
    {
        ArrayList<DataModel> spacecrafts=new DBAdapter(c).retrieveSpacecrafts();
        DataModel s;

        spaceProbes= new String[spacecrafts.size()][3];

        for (int i=0;i<spacecrafts.size();i++) {

             s=spacecrafts.get(i);

            spaceProbes[i][0]=s.getSkinTempValue();
            spaceProbes[i][1]=s.getAirTempValue();
            spaceProbes[i][2]=s.getCurrent_time();
        }

        return spaceProbes;





    }
}





