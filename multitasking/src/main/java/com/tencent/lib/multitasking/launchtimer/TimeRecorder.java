package com.tencent.lib.multitasking.launchtimer;

import android.util.Log;


public final class TimeRecorder {
    private static final String TAG="TimeRecorder";
    private TimeRecorder() {
    }

    private static long sTime;

    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }

    public static void endRecord() {
        endRecord("");
    }

    public static void endRecord(String msg) {
        long cost = System.currentTimeMillis() - sTime;
        Log.e("LaunchTimer:", msg + "cost " + cost);
    }

}
