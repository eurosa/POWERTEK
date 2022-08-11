package com.crgb.clock.mpchart;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GraphXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter
    {

        private final java.text.SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

        long millis = TimeUnit.HOURS.toMillis((long) value);
        return mFormat.format(new Date(millis));
    }

    }
