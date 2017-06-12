package com.android001.service.alarmService;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android001.service.app.AppHolder;

import java.util.LinkedList;


/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/9 下午4:22
 */

public class PendingAlarmDispatcher {
    LinkedList<PendingHandler> pendingAlarmList = new LinkedList<>();

    public void addLast(PendingHandler pendingAlarm) {
        synchronized (this) {
            if (null == pendingAlarmList) return;
            if (!pendingAlarmList.contains(pendingAlarm))
                pendingAlarmList.addLast(pendingAlarm);
        }
    }


    public void addFirst(PendingHandler pendingAlarm) {
        synchronized (this) {
            if (null == pendingAlarmList) return;
            if (!pendingAlarmList.contains(pendingAlarm))
                pendingAlarmList.addFirst(pendingAlarm);
        }
    }

    public void addMiddle(int position, PendingHandler pendingAlarm) {
        synchronized (this) {
            if (null == pendingAlarmList) return;
            if (!pendingAlarmList.contains(pendingAlarm))
                pendingAlarmList.add(position, pendingAlarm);
        }
    }

    public void clearTask() {
        if (null != pendingAlarmList) pendingAlarmList.clear();
    }

    public void addSkip2OtherAppTask() {
        Long currentTime = System.currentTimeMillis();
//        Runnable runable = new PendingHandler.AlarmListenerRunnable(new AlarmManager.OnAlarmListener() {
//            @Override
//            public void onAlarm() {
//                Log.e("android001", "收到系统报警");
//                Toast.makeText(AppHolder.getContext(),"收到系统报警",Toast.LENGTH_LONG);
//                skip2OtherApp();
//            }
//        }, new Handler(Looper.getMainLooper()));

//        PendingHandler firstPendingAlarm = new PendingHandler(AlarmManager.RTC_WAKEUP, currentTime + 5 * 1000, "first", runable);
//        addFirst(firstPendingAlarm);

        //-----

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.e("android001","开始执行任务");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                ComponentName cn = new ComponentName("com.tencent.qqpim","com.tencent.qqpim.ui.QQPimAndroid");
                intent.setComponent(cn);
                AppHolder.getContext().startActivity(intent);
            }
        };


        PendingHandler pendingAlarm = new PendingHandler(System.currentTimeMillis()+10*1000,"",runnable,new Handler(Looper.getMainLooper()));
        addLast(pendingAlarm);
    }

    PendingIntent getPendingIntent(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ComponentName cn = new ComponentName("com.tencent.qqpim","com.tencent.qqpim.ui.QQPimAndroid");
        intent.setComponent(cn);
        PendingIntent pi = PendingIntent.getActivity(AppHolder.getContext(),0,intent,0);
        return pi;
    }

    public void dispatch() {
        if (pendingAlarmList != null) {
            for (PendingHandler pendingAlarm :
                    pendingAlarmList) {
                pendingAlarm.dispatch();
            }
        }
    }


    void skip2OtherApp() {
        Log.e("android001", "开始跳转");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ComponentName cn = new ComponentName("com.tencent.qqpim", "com.tencent.qqpim.ui.QQPimAndroid");
        intent.setComponent(cn);
        AppHolder.getContext().startActivity(intent);
    }


}
