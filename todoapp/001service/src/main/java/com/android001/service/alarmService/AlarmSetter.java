package com.android001.service.alarmService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.android001.service.app.AppHolder;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/9 上午11:01
 */

public class AlarmSetter {
    public static final AlarmManager am = (AlarmManager) AppHolder.getContext().getSystemService(Context.ALARM_SERVICE);

    public static void setAlarm(PendingAlarm pendingAlarm){

        int type = pendingAlarm.getType();
        long triggerAtMillis = pendingAlarm.getTriggerAtMillis();
        PendingIntent pendingIntent = pendingAlarm.getPendingIntent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //android 4.4以后系统
        {
            am.setExact(type, triggerAtMillis, pendingIntent);
        } else {
            am.set(type, triggerAtMillis, pendingIntent);
        }
    }

}
