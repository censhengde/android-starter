package com.tencent.lib.starter;

import android.os.Looper;
import android.os.MessageQueue;


import java.util.LinkedList;
import java.util.Queue;

public class DelayTaskStarter {
    private DelayTaskStarter(){}

    public static DelayTaskStarter create(){
        return new DelayTaskStarter();
    }
    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            if(mDelayTasks.size()>0){
                Task task = mDelayTasks.poll();
                new StarterRunnable(task).run();
            }
            return !mDelayTasks.isEmpty();
        }
    };

    public DelayTaskStarter addTask(Task task){
        mDelayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
