package com.crgb.clock.mpchart;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.crgb.clock.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RealtimeCharteMultiLine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_linechart);
        LineChart chart = (LineChart) findViewById(R.id.chart1);

        int[] numArr = {1,2,3,4,5,6};

        final HashMap<Integer, String> numMap = new HashMap<>();
        numMap.put(1, "first");
        numMap.put(2, "second");
        numMap.put(3, "third");
        numMap.put(4, "fourth");
        numMap.put(5, "fifth");
        numMap.put(6, "sixth");

        List<Entry> entries1 = new ArrayList<Entry>();

        for(int num : numArr){
            entries1.add(new Entry(num, num));
        }

        LineDataSet dataSet = new LineDataSet(entries1, "Numbers");


        LineData data = new LineData(dataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new  IndexAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("values_float",""+numMap.get((int)value));
                return numMap.get((int)value);
            }

        });
        chart.setData(data);
        chart.invalidate();

    }
}