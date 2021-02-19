package com.tencent.demo.multitasking;

import android.app.Application;

import com.tencent.lib.multitasking.TaskDispatcher;
import com.tencent.lib.multitasking.launchtimer.TimeRecorder;

/**
 * Author：岑胜德 on 2021/2/19 14:29
 * <p>
 * 说明：
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TimeRecorder.startRecord();
        TaskDispatcher.init(this);
        TaskDispatcher.create()
                .addTask(null)
                .addTask(null)
                .start();
        TimeRecorder.endRecord("启动耗时：");
    }
}
