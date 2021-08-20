package com.infant.warmer.mpchart;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GraphXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
    private String[] mValues;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.hh");

    public void GraphXAxisValueFormatr(String[] values) {
        this.mValues = values; }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mValues[(int) value];
    }
}
