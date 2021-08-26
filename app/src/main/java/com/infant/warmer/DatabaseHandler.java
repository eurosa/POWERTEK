package com.infant.warmer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.levitnudi.legacytableview.LegacyTableView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.infant.warmer.mDB.Constants.KEY_ARI_TEMP;
import static com.infant.warmer.mDB.Constants.KEY_HM;
import static com.infant.warmer.mDB.Constants.KEY_SKIN_TEMP;

public class DatabaseHandler extends SQLiteOpenHelper implements Serializable {

    // Database Name
    private static final String DATABASE_NAME = "infant_db";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_INFANT_WARMER = "infant_warmer";

    // Table Columns Name
    private static final String KEY_ID = "id";
    private static final String KEY_SKIN_TEMP = "skin_temp";
    private static final String KEY_ARI_TEMP = "air_temp";
    private static final String KEY_HM = "curr_time";




    Context context;

    private final List<String> skinTempList = new ArrayList<>();
    private final List<String> airTempList = new ArrayList<>();
    private final List<String> timeDateList = new ArrayList<>();
    HashMap<String, String> hashMap = new HashMap<String, String>();
    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INFANT_WARMER + "("
           //     + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SKIN_TEMP + " TEXT,"+ KEY_ARI_TEMP + " TEXT,"+ KEY_HM + " TEXT  UNIQUE "+")";
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_INFANT_WARMER + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_SKIN_TEMP + " TEXT NOT NULL, " +
                KEY_ARI_TEMP + " TEXT NOT NULL, " +
                KEY_HM + " TEXT UNIQUE NOT NULL );"
        );

      //  db.execSQL(CREATE_CONTACTS_TABLE);
       // db.execSQL("INSERT INTO " + TABLE_INFANT_WARMER+ "("+KEY_SKIN_TEMP+","+KEY_ARI_TEMP+" ) VALUES ('1','2')");
        //db.execSQL("INSERT INTO "+TABLE_DISPLAY_TOKEN+"("+KEY_DEVICE_ID+","+KEY_NO_OF_DIGIT+","+KEY_SOUND,KEY_TYPE+")"+" VALUES(?,?,?,?)", new Object[]{"1", "2","3","4"}");");
        //  db.execSQL("INSERT INTO TABLE_DISPLAY_TOKEN(name, amount) VALUES(?, ?)", new Object[]{"Jerry", moneyOfJerry});


        /*db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DISPLAY_TOKEN + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_DEVICE_ID + " TEXT DEFAULT '1', "
                + KEY_NO_OF_DIGIT + " TEXT DEFAULT '1', "
                + KEY_TYPE + " TEXT DEFAULT '1', "
                + KEY_SOUND + " TEXT DEFAULT 'English');");*/
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older Table if already Exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFANT_WARMER);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void Add_displayToken() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(KEY_DEVICE_ID, "1");



        // Inserting Row
        long rowInserted = db.insert(TABLE_INFANT_WARMER, null, values);

        if(rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        db.close(); // Close Database Connection
    }


    // Adding new contact
    public void AddData(String skintemp , String airTemp, String currentTime) {
        Log.d("database_data inert",""+ skintemp+" "+airTemp+ " " +currentTime );
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ARI_TEMP, airTemp); // Name
        values.put(KEY_SKIN_TEMP, skintemp); // Name
        values.put(KEY_HM, currentTime); // Name


        // Inserting Row
        try {
            long rowInserted = db.insert(TABLE_INFANT_WARMER, null, values);


        if(rowInserted != -1)
            Log.d("New row added",""+ rowInserted);
            //Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Log.d("Some thing wrong","Something wrong"+ rowInserted);
            //Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
        db.close(); // Close Database Connection
    }


    // Getting All QmsUtility
    public HashMap<String,String> GetInfantData() {
        hashMap.clear();
        try {
            // https://stackoverflow.com/questions/14331175/load-from-spinner-sqlite-with-text-and-value
            //dataList.clear();
           // dataList.add("New Record");
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_INFANT_WARMER;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                   // DataModel contact = new DataModel();
                   //  contact.setID(Integer.parseInt(cursor.getString(0)));


                   // dataList.add(cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP)));
                  //  dataList.add(cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP)));
                    hashMap.put("skin_temp#"+cursor.getString(cursor.getColumnIndex(KEY_ID))+"#"+cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP)),cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP)));
                    hashMap.put("air_temp#"+cursor.getString(cursor.getColumnIndex(KEY_ID))+"#"+cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP)),cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP)));
                    hashMap.put("time_date#"+cursor.getString(cursor.getColumnIndex(KEY_ID))+"#"+cursor.getString(cursor.getColumnIndex(KEY_HM)),cursor.getString(cursor.getColumnIndex(KEY_HM)));
                    Log.d("database_data get", ""+cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP))+" " +
                            ""+cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP))+" "+cursor.getString(cursor.getColumnIndex(KEY_HM)));

                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return hashMap;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_qmsUtility", "" + e);
        }

        return hashMap;
    }


    public List<String> GetSKinTemp() {
        skinTempList.clear();
        try {

            String selectQuery = "SELECT  * FROM " + TABLE_INFANT_WARMER;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    skinTempList.add(cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP)));

                    Log.d("database_data get", ""+cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP))+" " +
                            ""+cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP))+" "+cursor.getString(cursor.getColumnIndex(KEY_HM)));

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return skinTempList;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_qmsUtility", "" + e);
        }

        return skinTempList;
    }
    public List<String> GetAirTemp() {
        airTempList.clear();
        try {

            String selectQuery = "SELECT  * FROM " + TABLE_INFANT_WARMER;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    airTempList.add(cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP)));

                    Log.d("database_data get", ""+cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP))+" " +
                            ""+cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP))+" "+cursor.getString(cursor.getColumnIndex(KEY_HM)));

                } while (cursor.moveToNext());
            }


            cursor.close();
            db.close();
            return airTempList;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_qmsUtility", "" + e);
        }

        return airTempList;
    }


    public List<String> timeDate() {
        timeDateList.clear();
        try {

            String selectQuery = "SELECT  * FROM " + TABLE_INFANT_WARMER;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    timeDateList.add(cursor.getString(cursor.getColumnIndex(KEY_HM)));

                    Log.d("database_data get", ""+cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP))+" " +
                            ""+cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP))+" "+cursor.getString(cursor.getColumnIndex(KEY_HM)));

                } while (cursor.moveToNext());
            }


            cursor.close();
            db.close();
            return timeDateList;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_qmsUtility", "" + e);
        }

        return timeDateList;
    }



    public List < SpinnerObject> getAllLabels(){
        List< SpinnerObject > labels = new ArrayList < SpinnerObject > ();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INFANT_WARMER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if ( cursor.moveToFirst () ) {
            do {
                labels.add ( new SpinnerObject ( cursor.getString(0) , cursor.getString(1) ) );
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning labels
        return labels;
    }

    // Getting All QmsUtility
    public void getQmsUtilityById(String id, DataModel dataModel) {
        try {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_INFANT_WARMER+" WHERE id = ?";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[] {id});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // DataModel contact = new DataModel();
                    //  contact.setID(Integer.parseInt(cursor.getString(0)));
                    // Toast.makeText(context, cursor.getString(cursor.getColumnIndex("devId")), Toast.LENGTH_LONG).show();
                    //   dataModel.setDevId(cursor.getString(cursor.getColumnIndex("devId")));


                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_qmsUtility", "" + e);
        }


    }


    public int updateSound(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_DEVICE_ID, dataModel.getDevId());
        //values.put(KEY_NO_OF_DIGIT, dataModel.getDigitNo());

        // values.put(KEY_TYPE, dataModel.getTypeNo());
       // Toast.makeText(context, "Row ID: " + dataModel.getID(), Toast.LENGTH_SHORT).show();
        // updating row

        return db.update(TABLE_INFANT_WARMER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dataModel.getID()) });

    }

    public void delOlderData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_INFANT_WARMER +" WHERE "+KEY_HM +"<= date('now','-2 day')";
        db.execSQL(sql);
    }



    public int updateDigitNo(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_DEVICE_ID, dataModel.getDevId());
      //  values.put(KEY_NO_OF_DIGIT, dataModel.getDigitNo());
        // values.put(KEY_SOUND, dataModel.getSoundType());
        // values.put(KEY_TYPE, dataModel.getTypeNo());
        // Toast.makeText(context, "Label Eleven: " + dataModel.getCntLabelEleven(), Toast.LENGTH_SHORT).show();
        // updating row

        return db.update(TABLE_INFANT_WARMER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dataModel.getID()) });

    }

    public int up_nav_id(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
     //   values.put(KEY_DEVICE_ID, dataModel.getDevId());
        //values.put(KEY_NO_OF_DIGIT, dataModel.getDigitNo());
       // values.put(KEY_SOUND, dataModel.getSoundType());
        // values.put(KEY_TYPE, dataModel.getTypeNo());
        // Toast.makeText(context, "Label Eleven: " + dataModel.getCntLabelEleven(), Toast.LENGTH_SHORT).show();
        // updating row

        return db.update(TABLE_INFANT_WARMER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dataModel.getID()) });



    }


    public int updateTypeNo(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_DEVICE_ID, dataModel.getDevId());
        //values.put(KEY_NO_OF_DIGIT, dataModel.getDigitNo());
     //   values.put(KEY_TYPE, dataModel.getTypeNo());
        // values.put(KEY_TYPE, dataModel.getTypeNo());
        // Toast.makeText(context, "Label Eleven: " + dataModel.getCntLabelEleven(), Toast.LENGTH_SHORT).show();
        // updating row

        return db.update(TABLE_INFANT_WARMER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dataModel.getID()) });

    }


    // Updating single qmsUtility
    public int Update_QmsUtility(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       // values.put(KEY_DEVICE_ID, dataModel.getDevId());
       // values.put(KEY_NO_OF_DIGIT, dataModel.getDigitNo());
      //  values.put(KEY_SOUND, dataModel.getSoundType());
       // values.put(KEY_TYPE, dataModel.getTypeNo());
        // Toast.makeText(context, "Label Eleven: " + dataModel.getCntLabelEleven(), Toast.LENGTH_SHORT).show();
        // updating row

        return db.update(TABLE_INFANT_WARMER, values, KEY_ID + " = ?",
              new String[] { String.valueOf(dataModel.getID()) });

    }


    // Deleting single qmsUtility
    public void Delete_QmsUtility(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFANT_WARMER, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


    // Getting qmsUtility Count
    public int Get_Total_QmsUtility() {
        String countQuery = "SELECT  * FROM " + TABLE_INFANT_WARMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

      //  Toast.makeText(context, "Row No: " + cursor.getCount(), Toast.LENGTH_SHORT).show();
        Log.d("Database",""+cursor.getCount());

        // return qms utility
        cursor.close();
        return cursor.getCount();
    }
    public void getFromDatabase(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM infant_warmer", null);

        if(cursor.getCount()>0){
            //use database column names or custom names for the columns
            /* insert your column titles using legacy insertLegacyTitle() function*/
            LegacyTableView.insertLegacyTitle("Skin Temp", "Air Temp","Time & Date");
        }
        while(cursor.moveToNext()) {
            //simple table content insert method for table contents
            LegacyTableView.insertLegacyContent(cursor.getString(cursor.getColumnIndex(KEY_SKIN_TEMP)),
                    cursor.getString(cursor.getColumnIndex(KEY_ARI_TEMP)), cursor.getString(cursor.getColumnIndex(KEY_HM)));
        }
        //remember to close your database to avoid memory leaks
        cursor.close();
    }

}