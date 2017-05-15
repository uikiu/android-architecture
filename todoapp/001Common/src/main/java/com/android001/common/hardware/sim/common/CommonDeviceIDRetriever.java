package com.android001.common.hardware.sim.common;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

import com.android001.common.app.AppHolder;
import com.android001.common.hardware.sim.BaseDeviceIDRetriever;

/**
 * 通用获取deviceId方式
 */

public class CommonDeviceIDRetriever extends BaseDeviceIDRetriever {

    private CommonDeviceIDRetriever() {
        init();
    }

    static class CommonDeviceIDManagerHolder {
        private static final CommonDeviceIDRetriever INSTANCE = new CommonDeviceIDRetriever();
    }

    public static CommonDeviceIDRetriever getInstance() {
        return CommonDeviceIDManagerHolder.INSTANCE;
    }

    //---


    @Override
    public void addDeviceId() {
        addDeviceStand();

        if (Build.VERSION.SDK_INT >= 23) {//android 6.0之后
            addDeviceIdAfterSDK23();
        } else {

        }
    }

    void addDeviceStand() {
        try {
            String deviceId = mTelephonyManager.getDeviceId();
            DeviceIdManager.getInstance().addDeviceId(deviceId);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    void addDeviceIdAfterSDK23() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) AppHolder.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            for (int i = 0; i < 3; i++) {
                String deviceId = telephonyManager.getDeviceId(i);
                if (null != deviceId && deviceId.trim().length() > 0) {
                    DeviceIdManager.getInstance().addDeviceId(deviceId);
                }
            }
        } catch (Throwable t) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) AppHolder.getContext().getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
