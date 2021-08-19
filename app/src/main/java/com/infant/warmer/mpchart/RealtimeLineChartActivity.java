
package com.infant.warmer.mpchart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.infant.warmer.DataModel;
import com.infant.warmer.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.github.mikephil.charting.components.YAxis.AxisDependency.*;


public class RealtimeLineChartActivity extends DemoBase implements
        OnChartValueSelectedListener {

    private LineChart chart;
    private TimerTask timerTask;
    private Timer repeatTimer;
    private DataModel dataModel;
    private Singleton b;

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
        chart.setOnChartValueSelectedListener(this);

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
        l.setTextColor(Color.WHITE);

        XAxis xl = chart.getXAxis();
        xl.setTypeface(tfLight);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);// Bottom position of changing time value
        xl.setTextColor(Color.WHITE);
        // xl.setGranularity(1f); // only intervals of 1 day
        xl.setDrawGridLines(true);

        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();

        rightAxis.setEnabled(false);

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

    private List<Entry> getIncomeEntries() {
        ArrayList<Entry> incomeEntries = new ArrayList<>();

        incomeEntries.add(new Entry(1, 1));
        incomeEntries.add(new Entry(2, 2));
        incomeEntries.add(new Entry(3, 3));
        incomeEntries.add(new Entry(4, 4));
        incomeEntries.add(new Entry(5, 5));
        incomeEntries.add(new Entry(6, 6));
        incomeEntries.add(new Entry(7, 7));
        incomeEntries.add(new Entry(8, 8));
        incomeEntries.add(new Entry(9, 9));
        incomeEntries.add(new Entry(10, 10));
        incomeEntries.add(new Entry(11, 11));
        incomeEntries.add(new Entry(12, 12));
        return incomeEntries.subList(0, 12);
    }

    private void addEntry() {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(120);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
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


    private void addData() {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), Float.parseFloat(dataModel.getSkinTempValue())), 0);
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


    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(getIncomeEntries(), "Dynamic Data");
        set.setAxisDependency(LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
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

            @Override
            public void run() {
                addData();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.actionAdd: {
                addEntry();
                break;
            }
            case R.id.actionClear: {
                chart.clearValues();
                Toast.makeText(this, "Chart cleared!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.actionFeedMultiple: {
                feedMultiple();
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
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (thread != null) {
            thread.interrupt();
        }
    }
}
