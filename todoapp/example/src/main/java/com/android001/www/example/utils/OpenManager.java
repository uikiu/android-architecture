package com.android001.www.example.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/**
* class design：
* @author android001
* created at 2017/9/18 下午5:25
*/

public class OpenManager {

    /**
     * 判断是否打开了允许虚拟位置,如果打开了 则弹窗让他去关闭
     */
    public static boolean isAllowMockLocation(final Context context) {
        /**
         * 判断用户是否开启了模拟位置功能
         */
        boolean isOpen = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) != 0;

        if (isOpen) {
            Log.e("android001","已经打开未知来源");
        } else {
            Log.e("android001","没有打开未知来源");
        }

        return isOpen;
    }
}
