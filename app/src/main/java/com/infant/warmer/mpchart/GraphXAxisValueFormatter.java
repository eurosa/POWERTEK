package com.infant.warmer.mpchart;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GraphXAxisValueFormatter extends IndexAxisValueFormatter {

    @Override
    public String getFormattedValue(float value) {

        // Convert float value to date string
        // Convert from days back to milliseconds to format time  to show to the user
        long emissionsMilliSince1970Time = TimeUnit.DAYS.toMillis((long)value);
        // Show time in local version
        Date timeMilliseconds = new Date(emissionsMilliSince1970Time);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH");

        return dateTimeFormat.format(timeMilliseconds);
    }
}
