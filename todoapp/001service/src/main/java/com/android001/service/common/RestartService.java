package com.android001.service.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android001.service.common.proxy.ReStartServiceProxy;

public class RestartService extends Service {

    ReStartServiceProxy mRestartServiceProxy;

    public RestartService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRestartServiceProxy = new ReStartServiceProxy(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (flags == 0) {
            if (null!=intent) {

            }else {
                handleRestartService();
            }
        } else if (flags == START_FLAG_RETRY) {
            handleRestartService();
        } else {
            Log.e("android001","onStartCommand 接收到未知flags = "+flags);
        }
            return START_STICKY;
    }

    private void handleRestartService() {
        //1. 判断是否已经上报，如果没上报则执行上报
        //2. 卸载
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
