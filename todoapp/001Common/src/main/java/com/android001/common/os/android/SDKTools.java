package com.android001.common.os.android;

import android.icu.util.Calendar;
import android.os.Build;

/**
 * Created by xixionghui on 2017/3/20.
 * 这个工具类目前只封装了sdkVersion也就是一个int类型。以后要修改为object，并扩展功能
 */

public class SDKTools {

    static class SDKToolsSingleton {
        private static final SDKTools SINGLETON = new SDKTools();
    }

    private SDKTools() {
    }

    public static SDKTools getSDKToolsInstance() {
        return SDKToolsSingleton.SINGLETON;
    }


    public int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 当前系统的sdk版本号与提供的sdk的版本号对比。
     *
     * @param offeredSdkVersion 提供的sdk的版本号
     * @return 当前系统sdk的版本号与提供的sdk的版本号对比结果，大于返回1，等于返回0，小于返回-1
     */
    private int compareTo(int offeredSdkVersion) {
        int currentSdkVersion = getSDKVersion();
        return (currentSdkVersion > offeredSdkVersion) ? 1 : (currentSdkVersion == offeredSdkVersion) ? 0 : -1;
    }

    /**
     * 当前系统sdk版本小于指定的sdk版本号情况，不包含指定的版本号
     *
     * @param offeredSdkVersion
     * @return
     */
    public boolean before(int offeredSdkVersion) {
        return compareTo(offeredSdkVersion) < 0;
    }


    /**
     * 当前系统sdk版本小于等于指定的sdk版本号情况
     *
     * @param offeredSdkVersion
     * @return
     */
    public boolean equalOrLesser(int offeredSdkVersion) {
        return compareTo(offeredSdkVersion) <= 0;
    }

    /**
     * 当前系统sdk版本大于指定的sdk版本号情况，不包含指定的版本号
     *
     * @param offeredSdkVersion
     * @return
     */
    public boolean after(int offeredSdkVersion) {
        return compareTo(offeredSdkVersion) > 0;
    }

    /**
     * 当前系统sdk版本大于等于指定的sdk版本号情况
     * @param offeredSdkVersion
     * @return
     */
    public boolean equalOrGrater(int offeredSdkVersion) {
        return compareTo(offeredSdkVersion) >= 0;
    }


}
