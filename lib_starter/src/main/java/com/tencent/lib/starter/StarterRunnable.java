package com.tencent.lib.starter;

import android.os.Looper;
import android.os.Process;

import androidx.core.os.TraceCompat;


/**
 * * Create by 岑胜德
 * * on 2019/4/16.
 * * com.emwit
 **/
public class StarterRunnable implements Runnable{

    private Task mTask;
    private AppStarter mAppStarter;

    public StarterRunnable(Task task) {
        this.mTask = task;
    }
    public StarterRunnable(Task task, AppStarter dispatcher) {
        this.mTask = task;
        this.mAppStarter = dispatcher;
    }


    @Override
    public void run() {
        TraceCompat.beginSection(mTask.getClass().getSimpleName());
        StarterLog.i(mTask.getClass().getSimpleName()
                + " begin run" + "  Situation  " + TaskStat.getCurrentSituation());

        Process.setThreadPriority(mTask.priority());

        long startTime = System.currentTimeMillis();

        mTask.setWaiting(true);
        mTask.waitToSatisfy();

        long waitTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 执行Task
        mTask.setRunning(true);
        mTask.run();

        // 执行Task的尾部任务
        Runnable tailRunnable = mTask.getTailRunnable();
        if (tailRunnable != null) {
            tailRunnable.run();
        }

        if (!mTask.needCall() || !mTask.runOnMainThread()) {
            printTaskLog(startTime, waitTime);

            TaskStat.markTaskDone();
            mTask.setFinished(true);
            if(mAppStarter != null){
                mAppStarter.satisfyChildren(mTask);
                mAppStarter.markTaskDone(mTask);
            }
            StarterLog.i(mTask.getClass().getSimpleName() + " finish");
        }
        TraceCompat.endSection();
    }
    /**
     * 打印出来Task执行的日志
     *
     * @param startTime
     * @param waitTime
     */
    private void printTaskLog(long startTime, long waitTime) {
        long runTime = System.currentTimeMillis() - startTime;
        if (StarterLog.isDebug()) {
            StarterLog.i(mTask.getClass().getSimpleName() + "  wait " + waitTime + "    run "
                    + runTime + "   isMain " + (Looper.getMainLooper() == Looper.myLooper())
                    + "  needWait " + (mTask.needWait() || (Looper.getMainLooper() == Looper.myLooper()))
                    + "  ThreadId " + Thread.currentThread().getId()
                    + "  ThreadName " + Thread.currentThread().getName()
                    + "  Situation  " + TaskStat.getCurrentSituation()
            );
        }
    }
}
