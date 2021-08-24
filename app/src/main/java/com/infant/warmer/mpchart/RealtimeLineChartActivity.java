
package com.infant.warmer.mpchart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.infant.warmer.DataModel;
import com.infant.warmer.DatabaseHandler;
import com.infant.warmer.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.github.mikephil.charting.components.YAxis.AxisDependency.LEFT;


public class RealtimeLineChartActivity extends DemoBase
{

    private LineChart chart;
    private TimerTask timerTask;
    private Timer repeatTimer;
    private DataModel dataModel;
    private Singleton b;
    private DatabaseHandler dbHandler;
    private List<Entry> incomeEntries,incomeEntries2;
    private HashMap<String,String> xAxisValues;
    private ArrayList<String> xAxisValues34;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_realtime_linechart);
        repeatTimer = new Timer();
        // setTitle("RealtimeLineChartActivity");
        b = Singleton.getInstance();

        //=========================Adding Toolbar in android layout=======================================
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_too);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            // back button pressed
        });
        //=========================Toolbar End============================================================


        dataModel = (DataModel) getIntent().getSerializableExtra("MyModel");
        dbHandler = new DatabaseHandler(this);



        chart = findViewById(R.id.chart1);

       xAxisValues34 = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"));
     //   incomeEntries = getIncomeEntries();
      //  incomeEntries2 = getIncomeEntries2();
        // enable description text
        chart.getDescription().setEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextColor(Color.RED);

        XAxis xl = chart.getXAxis();
        xl.setTypeface(tfLight);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);// Bottom position of changing time value
        xl.setTextColor(Color.RED);
       // chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        // xl.setGranularity(1f); // only intervals of 1 day
        xl.setDrawGridLines(true);


        xl.setGranularity(1f); // one hour

        // xl.setValueFormatter(new GraphXAxisValueFormatter());
        // xl.setValueFormatter(new IndexAxisValueFormatter(weekdays));
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.RED);

        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextSize(15);

        leftAxis.setCenterAxisLabels(true);
        leftAxis.setLabelCount(11, true);
        //leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();

       // rightAxis.setEnabled(false);

        runDataSendThread();

       // plotStrignLAbelCOunt();

    }
    public void setChartProperties() {
        YAxis rightAxis = chart.getAxisRight();
        YAxis leftAxis = chart.getAxisLeft();
        XAxis xAxis = chart.getXAxis();
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);
        chart.setPinchZoom(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setExtraOffsets(0, 0, 0, 0);
        xAxis.setLabelCount(6, true);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setDrawLabels(true);
        leftAxis.setSpaceBottom(60);
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(3, true);
        leftAxis.setCenterAxisLabels(true);
        leftAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
        xAxis.setAvoidFirstLastClipping(true);
        //dataSet.setColor(R.color.graphLineColor);
    }

    private List<Entry> getIncomeEntries(float x, float y) {
        ArrayList<Entry> incomeEntries = new ArrayList<>();

        incomeEntries.add(new Entry(x, y));

        return incomeEntries;
    }


    private List<Entry> getIncomeEntries2(float x, float y) {
        ArrayList<Entry> incomeEntries = new ArrayList<>();

        incomeEntries.add(new Entry(x, y));

        return incomeEntries;
    }




    public void runDataSendThread(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //what you want to do


                try {


                    final Thread receive = new Thread(() -> {
                        try {
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                  //  String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
//                                    dataModel.setAirTempValue(b.getAirtTempData());
                                //    dataModel.setCurrent_time(currentTime);
                                //    dbHandler.AddData(dataModel);

                                    xAxisValues= dbHandler.GetInfantData();
                                    Iterator myVeryOwnIterator = xAxisValues.keySet().iterator();
                                    while(myVeryOwnIterator.hasNext()) {
                                        String key=(String)myVeryOwnIterator.next();
                                        String value=(String)xAxisValues.get(key);

                                        incomeEntries = getIncomeEntries(10,20);
                                        incomeEntries2 = getIncomeEntries2(11,25);

                                       // xAxisValues.clear();
                                        Log.d("key_value","Key: "+key+" Value: "+value);
                                       // Toast.makeText(getApplicationContext(), "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
                                    }

                                   chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues34));
                                  // for(String name:dataList) {
                                 //     Log.d("skin_temp_update List",name);
                                //    }
                                   // setData(10,15);
                                    Log.d("skin_temp_update real",""+dataModel.getSkinTempValue()+"  "+b.getAirtTempData());

                                   feedMultiple(incomeEntries,incomeEntries2);
                                }
                            });
                        }catch (Exception e){}
                    });
                    receive.start();



                } catch (Exception e) {
                    Log.d("timer_thread_stopped",""+e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        repeatTimer.schedule(timerTask, 0, 60000);//wait 0 ms before doing the action and do it evry 1000ms (1second)

     /*   Runnable runnable = new Runnable() {
            @Override
            public void run() {

                String data="$I0R;";
                try {
                    if(btSocket!=null) {
                        Log.d("checkSendReceive",""+checkSendReceive);
                        if(checkSendReceive) {
                            btSocket.getOutputStream().write(data.getBytes());
                            receiveData();
                        }
                    }
                } catch (IOException e) {
                    Log.d("timer_thread_stopped",""+e.getMessage());
                    e.printStackTrace();
                }
                // /* and here comes the "trick" /
                handler.postDelayed(this, 500);
            }
        };

        handler.postDelayed(runnable, 500);*/

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addData(List<Entry> incomeEntries, List<Entry> incomeEntries2) {

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
       // List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "Decemeber"));

        dataSets = new ArrayList<>();
        LineDataSet set0,set1;
        set0 = createSet1(incomeEntries);
        set1 = createSet2(incomeEntries);
        dataSets.add(set0);
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        chart.setData(data);

       // LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            ILineDataSet set2 = data.getDataSetByIndex(1);
            // set.addEntry(...); // can be called as well

           if (set == null) {
             //   set = createSet1(incomeEntries);
             //   set2 = createSet2(incomeEntries2);
              //  data.addDataSet(set);
               // data.addDataSet(set2);
          }
            Log.d("entry_count",""+set.getEntryCount());
             data.addEntry(new Entry(0, 20), 0);
             data.addEntry(new Entry(1, 25), 0);

             data.addEntry(new Entry(set2.getEntryCount(), 25), 1);

            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(10);
            //chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private LineDataSet createSet1(List<Entry> incomeEntries) {
        // LineDataSet set = new LineDataSet(incomeEntries, "Skin Temp");
        LineDataSet set = new LineDataSet(null, "Skin Temp");
        set.setAxisDependency(LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);




        // set.setValueFormatter(new IndexAxisValueFormatter(weekdays));
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.RED);
        set.setValueTextSize(9f);
        set.setDrawValues(true);
        return set;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private LineDataSet createSet2(List<Entry> incomeEntries) {
        // LineDataSet set = new LineDataSet(getIncomeEntries(), "Dynamic Data");


        // create a dataset and give it a type
        //LineDataSet set  = new LineDataSet(incomeEntries2, "Air Temp");
        LineDataSet set  = new LineDataSet(null, "Air Temp");
        set.setAxisDependency(LEFT);
        set.setColor(Color.YELLOW);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        // set.setValueFormatter(new IndexAxisValueFormatter(weekdays));
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.RED);
        set.setValueTextSize(9f);
        set.setDrawValues(true);
        return set;
    }

    private Thread thread;

    private void feedMultiple(List<Entry> incomeEntries, List<Entry> incomeEntries2) {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                addData(incomeEntries, incomeEntries2);
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // for (int i = 0; i < 1000; i++) {

                // Don't generate garbage runnables inside the loop.
                runOnUiThread(runnable);

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // }
            }
        });

        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.realtime, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionClear: {
                chart.clearValues();
                Toast.makeText(this, "Chart cleared!", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }
        }
        return true;
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "RealtimeLineChartActivity");
    }



    @Override
    protected void onPause() {
        super.onPause();

        if (thread != null) {
            thread.interrupt();
        }
    }


    private void setData12(int count, float range) {

        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());

        ArrayList<Entry> values = new ArrayList<>();

        // count = hours
        float to = now + count;

        // increment by 1 hour
        for (float x = now; x < to; x++) {

            float y = getRandom(range, 50);
            values.add(new Entry(x, y)); // add one entry per hour
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        chart.setData(data);
    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }
        xVals.add("15");

        // create a dataset and give it a type
        LineDataSet set = new LineDataSet(yVals, "Test set");

        set.setColor(Color.GREEN);
        set.setCircleColor(Color.BLACK);
        set.setLineWidth(2f);
        set.setCircleSize(3f);
        set.setFillAlpha(10);
        set.setFillColor(Color.BLACK);

        set.setDrawCircles(false);


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData((ILineDataSet) xVals, (ILineDataSet) dataSets);


        // set data
        chart.setData(data);
    }
/*public void plotStrignLAbelCOunt(){

    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "Decemeber"));
    List<Entry> incomeEntries = getIncomeEntries();
    List<Entry> incomeEntries2 = getIncomeEntries2();
    dataSets = new ArrayList<>();
    LineDataSet set1,set2;

    set1 = new LineDataSet(incomeEntries, "Skin Temp");
    set1.setColor(Color.rgb(65, 168, 121));
    set1.setValueTextColor(Color.rgb(55, 70, 73));
    set1.setValueTextSize(10f);

    set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

    set2 = new LineDataSet(incomeEntries2, "Air Temp");
    set2.setColor(Color.rgb(65, 168, 20));
    set2.setValueTextColor(Color.rgb(55, 70, 73));
    set2.setValueTextSize(10f);
    set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

    dataSets.add(set1);
    dataSets.add(set2);

//customization
    LineChart mLineGraph = findViewById(R.id.chart1);
    mLineGraph.setTouchEnabled(true);
    mLineGraph.setDragEnabled(true);
    mLineGraph.setScaleEnabled(false);
    mLineGraph.setPinchZoom(false);
    mLineGraph.setDrawGridBackground(false);
    mLineGraph.setExtraLeftOffset(15);
    mLineGraph.setExtraRightOffset(15);
//to hide background lines
    mLineGraph.getXAxis().setDrawGridLines(false);
    mLineGraph.getAxisLeft().setDrawGridLines(false);
    mLineGraph.getAxisRight().setDrawGridLines(false);

//to hide right Y and top X border
    YAxis rightYAxis = mLineGraph.getAxisRight();
    rightYAxis.setEnabled(false);
    YAxis leftYAxis = mLineGraph.getAxisLeft();
    leftYAxis.setEnabled(true);
    XAxis topXAxis = mLineGraph.getXAxis();
    topXAxis.setEnabled(false);


    XAxis xAxis = mLineGraph.getXAxis();
    xAxis.setGranularity(1f);
    xAxis.setCenterAxisLabels(true);
    xAxis.setEnabled(true);
    xAxis.setDrawGridLines(false);
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    set1.setLineWidth(4f);
    set1.setCircleRadius(3f);
    set1.setDrawValues(false);
    set1.setCircleHoleColor(Color.RED);
    set1.setCircleColor(Color.RED);

//String setter in x-Axis
    mLineGraph.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

    LineData data = new LineData(dataSets);
    mLineGraph.setData(data);
 //   mLineGraph.animateX(2000);
    mLineGraph.invalidate();
     mLineGraph.moveViewToX(set1.getEntryCount());
     mLineGraph.moveViewToX(set2.getEntryCount());
    mLineGraph.getLegend().setEnabled(true);// Label Name
    mLineGraph.getDescription().setEnabled(true);
}*/

}
