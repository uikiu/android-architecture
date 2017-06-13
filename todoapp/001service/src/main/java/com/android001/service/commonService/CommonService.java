package com.android001.service.commonService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android001.service.alarmService.AlarmSetter;
import com.android001.service.alarmService.PendingAlarm;

/**
* class design：
* @author android001
* created at 2017/6/12 下午5:38
*/

public class CommonService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("android001","onStartCommand");
        setAutoSkip2OtherApp();
        return Service.START_STICKY;
    }

    private void setAutoSkip2OtherApp() {
        AlarmSetter.setAlarm
                (
                        new PendingAlarm.PendingAlarmBuilder(
                                PendingAlarm.getActivityPendingIntent("com.dc.geek","com.stkj.presenter.ui.splash.SplashActivity")
                        ).setTriggerAtMillis(System.currentTimeMillis()+5*1000).build()
                );

        AlarmSetter.setAlarm
                (
                        new PendingAlarm.PendingAlarmBuilder(
                                PendingAlarm.getActivityPendingIntent("com.baidu.searchbox","com.baidu.searchbox.SplashActivity")
                        ).setTriggerAtMillis(System.currentTimeMillis()+15*1000).build()
                );
    }
}
