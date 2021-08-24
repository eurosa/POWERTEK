package com.infant.warmer.mDB;

/**
 * Created by Oclemy on 9/27/2016 for ProgrammingWizards Channel and http://www.camposha.info.
 */
public class Constants {
    /*
  COLUMNS
   */


    // Table Columns Name
    public static final String KEY_ID = "id";
    public static final String KEY_SKIN_TEMP = "skin_temp";
    public static final String KEY_ARI_TEMP = "air_temp";
    public static final String KEY_HM = "curr_time";


    /*
    DB PROPERTIES
     */
    static final String DB_NAME="infant_db";
    static final String TB_NAME="infant_warmer";
    static final int DB_VERSION=1;

    /*
    TABLE CREATION STATEMENT
     */
    static final String CREATE_TB="CREATE TABLE infant_warmer(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,propellant TEXT NOT NULL,destination TEXT NOT NULL);";


    /*
    TABLE DELETION STMT
     */
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;

}
