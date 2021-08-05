package com.infant.warmer;

import java.util.TimerTask;

public class CoresTimerTask extends TimerTask {

    private boolean hasStarted = false;

    @Override
    public void run() {
        this.hasStarted = true;
        //rest of run logic here...
    }

    public boolean hasRunStarted() {
        return this.hasStarted;
    }
}
