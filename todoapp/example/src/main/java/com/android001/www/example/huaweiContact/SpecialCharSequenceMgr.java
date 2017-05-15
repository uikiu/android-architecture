package com.android001.www.example.huaweiContact;

import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by xixionghui on 2017/5/10.
 */

public class SpecialCharSequenceMgr {


    public static void showDual_IMEI_Panel(Context paramContext, boolean paramBoolean) {
        //获取imei
        int k = SimFactoryManager.getUserDefaultSubscription();
        if (k == 0) {
            String imei1 = SimFactoryManager.getImei(0);
            String imei2 = SimFactoryManager.getImei(1);
            Logger.e("反编译华为拨号器获取到的imei：" + "\nimei1 = " + imei1 + "\nimei2 = " + imei2);
        }

        //获取meid-----这里缺少对C网类型的判断
//        if (SimFactoryManager.mSimTelephonyManager_getCurrentPhoneType(0) == 2) {//
            String meid1 = SimFactoryManager.getMeid(0);
//        String meid2  = SimFactoryManager.getMeid(1);
            Logger.e("反编译华为拨号器获取到的meid：" + "\nmeid1 = " + meid1
//                +"\nmeid2 = "+meid2
            );
//        }

    }

}
