
package com.infant.warmer.mpchart;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.infant.warmer.DataModel;
import com.infant.warmer.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.github.mikephil.charting.components.YAxis.AxisDependency.LEFT;


public class RealtimeLineChartActivity_df extends DemoBase
         {

    private LineChart chart;
    private TimerTask timerTask;
    private Timer repeatTimer;
    private DataModel dataModel;
    private Singleton b;
     private LineData data;

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

       runDataSendThread();

        chart = findViewById(R.id.chart1);


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
        // xl.setGranularity(1f); // only intervals of 1 day
        xl.setDrawGridLines(true);

        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.RED);

        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setLabelCount(11, /*force: */true);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();

        rightAxis.setEnabled(false);
      //  setData(10,  20);
                // feedMultiple();
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
                                            Log.d("skin_temp_update real",""+dataModel.getSkinTempValue()+"  "+b.getSkinTempData());

                                          feedMultiple();

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

        repeatTimer.schedule(timerTask, 0, 6000);//wait 0 ms before doing the action and do it evry 1000ms (1second)

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addData() {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet1();

                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), Float.parseFloat(b.getSkinTempData())), 0);
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
    private LineDataSet createSet1() {
         // LineDataSet set = new LineDataSet(getIncomeEntries(), "Dynamic Data");
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);

        // set.setValueFormatter(new IndexAxisValueFormatter(weekdays));
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.RED);
        set.setValueTextSize(9f);
        set.setDrawValues(true);
        return set;
    }



    private Thread thread;

    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
             //   addData();
                setData(10,10);
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

             private void setData(int count, float range) {

                 ArrayList<Entry> values1 = new ArrayList<>();

                 for (int i = 0; i < count; i++) {
                     float val = (float) (Math.random() * (range / 2f)) + 50;
                     values1.add(new Entry(i, val));
                 }

                 ArrayList<Entry> values2 = new ArrayList<>();

                 for (int i = 0; i < count; i++) {
                     float val = (float) (Math.random() * range) + 450;
                     values2.add(new Entry(i, val));
                 }

                 ArrayList<Entry> values3 = new ArrayList<>();

                 for (int i = 0; i < count; i++) {
                     float val = (float) (Math.random() * range) + 500;
                     values3.add(new Entry(i, val));
                 }

                 LineDataSet set1, set2, set3;

                 if (chart.getData() != null &&
                         chart.getData().getDataSetCount() > 0) {
                     set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
                     set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
                     set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
                     set1.setValues(values1);
                     set2.setValues(values2);
                     set3.setValues(values3);
                     chart.getData().notifyDataChanged();
                     chart.notifyDataSetChanged();
                     // let the chart know it's data has changed
                     chart.notifyDataSetChanged();

                     // limit the number of visible entries
                     chart.setVisibleXRangeMaximum(10);
                     //chart.setVisibleYRange(30, AxisDependency.LEFT);
                     // move to the latest entry
                     chart.moveViewToX(data.getEntryCount());
                     // move to the latest entry

                 } else {
                     // create a dataset and give it a type
                     set1 = new LineDataSet(values1, "DataSet 1");

                     set1.setAxisDependency(AxisDependency.LEFT);
                     set1.setColor(ColorTemplate.getHoloBlue());
                     set1.setCircleColor(Color.WHITE);
                     set1.setLineWidth(2f);
                     set1.setCircleRadius(3f);
                     set1.setFillAlpha(65);
                     set1.setFillColor(ColorTemplate.getHoloBlue());
                     set1.setHighLightColor(Color.rgb(244, 117, 117));
                     set1.setDrawCircleHole(false);
                     //set1.setFillFormatter(new MyFillFormatter(0f));
                     //set1.setDrawHorizontalHighlightIndicator(false);
                     //set1.setVisible(false);
                     //set1.setCircleHoleColor(Color.WHITE);

                     // create a dataset and give it a type
                     set2 = new LineDataSet(values2, "DataSet 2");
                     set2.setAxisDependency(AxisDependency.RIGHT);
                     set2.setColor(Color.RED);
                     set2.setCircleColor(Color.WHITE);
                     set2.setLineWidth(2f);
                     set2.setCircleRadius(3f);
                     set2.setFillAlpha(65);
                     set2.setFillColor(Color.RED);
                     set2.setDrawCircleHole(false);
                     set2.setHighLightColor(Color.rgb(244, 117, 117));
                     //set2.setFillFormatter(new MyFillFormatter(900f));

                     set3 = new LineDataSet(values3, "DataSet 3");
                     set3.setAxisDependency(AxisDependency.RIGHT);
                     set3.setColor(Color.YELLOW);
                     set3.setCircleColor(Color.WHITE);
                     set3.setLineWidth(2f);
                     set3.setCircleRadius(3f);
                     set3.setFillAlpha(65);
                     set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
                     set3.setDrawCircleHole(false);
                     set3.setHighLightColor(Color.rgb(244, 117, 117));

                     // create a data object with the data sets
                      data = new LineData(set1, set2, set3);
                     data.setValueTextColor(Color.WHITE);
                     data.setValueTextSize(9f);

                     // set data
                     chart.setData(data);
                     // let the chart know it's data has changed
                     chart.notifyDataSetChanged();

                     // limit the number of visible entries
                     chart.setVisibleXRangeMaximum(10);
                     //chart.setVisibleYRange(30, AxisDependency.LEFT);

                     // move to the latest entry
                     chart.moveViewToX(data.getEntryCount());
                 }
             }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.realtime, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


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
}
