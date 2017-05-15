package com.android001.common.hardware.sim;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android001.common.app.AppHolder;

/**
 * Created by xixionghui on 2017/5/12.
 */

public abstract class BaseDeviceIDRetriever implements DeviceIDRetriever {
    public Context mContext;
    public TelephonyManager mTelephonyManager;

    @Override
    public void init() {
        mContext = AppHolder.getContext();
        mTelephonyManager = (TelephonyManager) mContext.getSystemService("phone");
//        retrieveDeviceId();
    }

    @Override
    public synchronized void retrieveDeviceId() {
        synchronized (DeviceIDRetriever.class) {
            addDeviceId();
        }
    }



    public abstract void addDeviceId();


}
