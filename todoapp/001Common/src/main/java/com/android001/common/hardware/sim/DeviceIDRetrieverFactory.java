package com.android001.common.hardware.sim;

/**
 * Created by android001 on 2017/5/27.
 */

public class DeviceIDRetrieverFactory {

    public static BaseDeviceIDRetriever createDeviceIDRetriever(Class<? extends BaseDeviceIDRetriever> clazz) {
        try {
            return  clazz.newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
