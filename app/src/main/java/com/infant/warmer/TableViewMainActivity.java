package com.infant.warmer;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.levitnudi.legacytableview.LegacyTableView;

import static com.infant.warmer.mDB.Constants.KEY_ARI_TEMP;
import static com.infant.warmer.mDB.Constants.KEY_SKIN_TEMP;
import static com.infant.warmer.mDB.Constants.KEY_HM;

public class TableViewMainActivity  extends AppCompatActivity {

    DatabaseHandler dbHabn;
    SQLiteDatabase db;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //do something when user presses back
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_view);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("SQLiteToLegacyTableView");
        }
        dbHabn= new DatabaseHandler(this);
        //initialize database and insert dummy data
       // initializeDatabase();
        //get dummy data from database and insert in the arrays
        dbHabn.getFromDatabase();

        LegacyTableView legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
        //once you have inserted contents and titles, you can retrieve them
        //using readLegacyTitle() and readLegacyContent() methods
        legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        legacyTableView.setContent(LegacyTableView.readLegacyContent());
        //if you want a smaller table, change the padding setting
        legacyTableView.setTablePadding(7);
        //to enable users to zoom in and out:
        legacyTableView.setZoomEnabled(true);
        legacyTableView.setShowZoomControls(true);
        //remember to build your table as the last step
        legacyTableView.build();


    }



    public void getFromDatabase(){

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
