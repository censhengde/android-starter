package com.tencent.lib.multitasking;

import android.os.Looper;
import android.os.MessageQueue;


import com.tencent.lib.multitasking.task.DispatchRunnable;
import com.tencent.lib.multitasking.task.Task;

import java.util.LinkedList;
import java.util.Queue;

public class DelayTaskDispatcher {

    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            if(mDelayTasks.size()>0){
                Task task = mDelayTasks.poll();
                new DispatchRunnable(task).run();
            }
            return !mDelayTasks.isEmpty();
        }
    };

    public DelayTaskDispatcher addTask(Task task){
        mDelayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
