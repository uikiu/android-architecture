package com.android001.www.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

/**
 * Created by xixionghui on 2017/3/17.
 */

public class CalendarChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_TIME_CHANGED)) {
            Logger.e("=================时间已经变更====================");
        }
    }
}
