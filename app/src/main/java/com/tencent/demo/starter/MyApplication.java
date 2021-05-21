package com.tencent.demo.starter;

import android.app.Application;

import com.tencent.lib.starter.AppStarter;
import com.tencent.lib.starter.TimeRecorder;

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
        AppStarter.init(this);
        AppStarter.create()
                .addTask(null)
                .addTask(null)
                .start();
        TimeRecorder.endRecord("启动耗时：");
    }
}
