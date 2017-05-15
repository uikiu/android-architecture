package com.android001.common.hardware.sim.huaweiContact;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android001.common.app.AppHolder;
import com.android001.common.CommonUtilMethods;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;

/**
 * Created by xixionghui on 2017/5/10.
 */

public class SimFactoryManager {

    private static boolean sIsDualSim = true;//默认为双卡----initDualSim进行修改


    private static final String UNKNOW_SYSTEM_PROPERTIES_NET = "unknow";

    /**
     * 反编译SystemProperties类的getSimCombination方法
     *
     * @return
     */
    public static String systemProperties_getSimCombination(String properties) {

        try {
            Class<?> systemPropertiesClazz = Class.forName("android.os.SystemProperties");
            Method getMethod = systemPropertiesClazz.getMethod("get", String.class);
            String propertiesNetType = (String) getMethod.invoke(null, properties);
            if (!TextUtils.isEmpty(properties)) {
                return propertiesNetType;
            } else {
                return UNKNOW_SYSTEM_PROPERTIES_NET;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return UNKNOW_SYSTEM_PROPERTIES_NET;
        }
    }

    public static final int CDMA_GSM = 2;
    public static final int UMTS_GSM = 4;
    public static final int UNKNOW = -1;


    /*
* 返回SIM卡组合。combination是组合的意思
* 如果是C/G双网则返回2.
* 如果是仅有G网络则返回4.
* 如果是其他则返回-1
*/
    public static int getSimCombination() {
//        if (SystemProperties.get("ro.config.dsds_mode").equals("cdma_gsm")) {
//            if (LogConfig.HWDBG) {
//                Log.d("CSP_SimFactoryManager", "Inside CDMA GSM combination");
//            }
//            return 2;
//        }
//        if (SystemProperties.get("ro.config.dsds_mode").equals("umts_gsm")) {
//            if (LogConfig.HWDBG) {
//                Log.d("CSP_SimFactoryManager", "Inside GSM GSM combination");
//            }
//            return 4;
//        }
//        return -1;

        //以上为源码，以下为通过反射运行源码------------
        String netType = systemProperties_getSimCombination("ro.config.dsds_mode");
        if (("cdma_gsm").equals(netType)) {
            return CDMA_GSM;
        } else if (("umts_gsm").equals(netType)) {
            return UMTS_GSM;
        } else {
            return UNKNOW;
        }
    }


    /**
     * 返回对SIM卡的默认订阅sim卡槽的id
     *
     * @return
     */
    public static int getUserDefaultSubscription() {
        try {
            Class<?> telephonyManagerExClazz = Class.forName("com.huawei.android.telephony.TelephonyManagerEx");
            //我们要执行的方法：int i = TelephonyManagerEx.getDefault4GSlotId();
            Method getDefault4GSlotIdMethod = telephonyManagerExClazz.getMethod("getDefault4GSlotId");
            if (null == getDefault4GSlotIdMethod) {
                Logger.e("没有找到getDefault4GSlotIdMethod方法");
            }
            getDefault4GSlotIdMethod.setAccessible(true);
            int tUserDefaultSubscription = (int) getDefault4GSlotIdMethod.invoke(telephonyManagerExClazz);
            return tUserDefaultSubscription;
        } catch (Throwable localThrowable) {
            Logger.e("getUserDefaultSubscription出错");

        }
        return 0;
    }


    /**
     * @param slotId SIM卡槽的ID
     * @return
     */
    public static String getImei(int slotId) {
        Object localObject = (TelephonyManager) AppHolder.getContext().getSystemService("phone");
        if (sIsDualSim) {//双卡
            try {
                int j = getSubscriptionIdBasedOnSlot(slotId);
                int i = j;
                if (j == -1) {
                    i = slotId;
                }
//                String str = ((TelephonyManager) localObject).getImei(i);
                String str = telephonyManager_getImei(i);
                return str;
            } catch (Exception localException2) {
                Logger.e("Unsupported exception thrown in getImei method by solt hence returning null: " + localException2.getMessage());
                try {
//                    localObject = ((TelephonyManager) localObject).getImei();
//                    return localObject;
                    return telephonyManager_getImei();
                } catch (Exception localException1) {
//                    Log.w("CSP_SimFactoryManager", "Unsupported exception thrown in getImei method in TelephonyManager yet hence returning null");
                    Logger.e("Unsupported exception thrown in getImei method in TelephonyManager yet hence returning null: " + localException1.getMessage());
                    return null;
                }
            }
        } else {//单卡
//        return CommonUtilMethods.getTelephonyManager(mContext).getImei();
            return telephonyManager_getImei();
        }
    }

    /**
     * 反射TelephonyManager类的getImei方法:getImei(int slotId)
     *
     * @return
     */
    public static String telephonyManager_getImei(int slotId) {

        String imei = null;
        try {
            Class<?> telephonyManagerClazz = Class.forName("android.telephony.TelephonyManager");
            TelephonyManager tm = (TelephonyManager) AppHolder.getContext().getSystemService(Context.TELEPHONY_SERVICE);

            Method getImeiMethod = telephonyManagerClazz.getMethod("getImei", int.class);
            getImeiMethod.setAccessible(true);
            imei = (String) getImeiMethod.invoke(tm, slotId);

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("反射TelephonyManager类的getImei方法:getImei(int slotId)出错：" + e.getMessage());
        }
        return imei;
    }

    /**
     * 反射TelephonyManager类的getImei方法:getImei()
     *
     * @return
     */
    public static String telephonyManager_getImei() {

        String imei = null;
        try {
            Class<?> telephonyManagerClazz = Class.forName("android.telephony.TelephonyManager");
            TelephonyManager tm = (TelephonyManager) AppHolder.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            Method getImeiMethod = telephonyManagerClazz.getMethod("getImei");
            getImeiMethod.setAccessible(true);
            imei = (String) getImeiMethod.invoke(tm);

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("反射TelephonyManager类的getImei方法:getImei()出错：" + e.getMessage());
        }
        return imei;
    }


    /**
     * @param slotId SIM卡槽的ID
     * @return
     */
    public static int getSubscriptionIdBasedOnSlot(int slotId) {
        int j = -1;//默认值
        int i = j;
        int[] subIds = null;//本质是int[] subIds

        //如果是双卡
        if (sIsDualSim) {
            subIds = getSubId(slotId);
            i = j;
            if (subIds != null) {
                i = j;
                if (subIds.length > 0) {
                    i = subIds[0];
                }
            }
        }

//        label50:

//        do {
//            do {
//                return i;
//                i = j;
//            } while (!(subIds instanceof int[]));
//            subIds = (int[]) subIds;
//            i = j;
//        } while (subIds.length <= 0);


        return subIds[0];
    }

    //com.android.contacts.util.EmuiVersion;
    public static void initDualSim()
    {
//        if (EmuiVersion.isSupportEmui())
//        {
//            sIsDualSim = MSimTelephonyManager.getDefault().isMultiSimEnabled();
//            return;
//        }
//        sIsDualSim = false;


//        Class<?> emuiVersionClazz = Class.forName("com.android.contacts.util.EmuiVersion");
//        Method isSupportEmuiMethod = emuiVersionClazz.getMethod("isSupportEmui");
//        isSupportEmuiMethod.setAccessible(true);
//        boolean isEmui = isSupportEmuiMethod.invoke(null);
//        if (isEmui) {
//            Class<?> mSimTelephonyManagerClazz = Class.forName("android.telephony.MSimTelephonyManager");
//        }

    }


    /**
     * 反射SubscriptionManager的这个方法：public static int[] getSubId(int slotId)
     *
     * @param slotId
     * @return
     */
    public static int[] getSubId(int slotId) {

        try {
            Class<?> subScriptionManagerClazz = Class.forName("android.telephony.SubscriptionManager");
            Method getSubMethod = subScriptionManagerClazz.getMethod("getSubId", int.class);
            getSubMethod.setAccessible(true);
            int[] subIds = (int[]) getSubMethod.invoke(subScriptionManagerClazz, slotId);

//            StringBuilder sb = new StringBuilder();
//            for (int subId :
//                    subIds) {
//                sb.append(subId).append(",");
//            }
//            Logger.e("获取到的subId分别是：" + sb.toString());

            return subIds;
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.e("反射SubscriptionManager的这个方法：public static int[] getSubId(int slotId)出错：" + e.getMessage());
            return null;
        }

    }


    //以上为获取imei，以下为获取meid------
    public static String getMeid(int paramInt) {
//        Object localObject = HwTelephonyManager.getDefault();
        Object localObject = getHwTelephonyManager();
        String meid = null;
        if (sIsDualSim) {
            try {
                int j = getSubscriptionIdBasedOnSlot(paramInt);
                int i = j;
                if (j == -1) {
                    i = paramInt;
                }
//                String str = ((HwTelephonyManager) localObject).getMeid(i);
                meid = hwTelephonyManagerGetMeidWithId(i);
                return meid;
            } catch (Exception localException2) {
                Log.w("CSP_SimFactoryManager", "Unsupported exception thrown in getMeid method by solt hence returning null");
                try {
//                    localObject = ((HwTelephonyManager) localObject).getMeid();
                    meid = hwTelephonyManagerGetMeid();
                    return meid;
                } catch (Exception localException1) {
//                    ExceptionCapture.captureReadMeidException("HwTm.getMeid() is null", null);
                    Logger.e("Unsupported exception thrown in getMeid method in TelephonyManager yet hence returning null");
                    return null;
                }
            }
        }
//        ExceptionCapture.captureReadMeidException("getMeid is null", null);
        return null;
    }


    /**
     * 反编译这个HwTelephonyManager类的getDefault()这个方法
     */
    public static Object getHwTelephonyManager() {
        Object object = null;
        try {
            Class<?> hwTelephonyManagerClazz = Class.forName("android.telephony.HwTelephonyManager");
            Method getDefaultMethod = hwTelephonyManagerClazz.getMethod("getDefault");
            getDefaultMethod.setAccessible(true);
            object = getDefaultMethod.invoke(hwTelephonyManagerClazz.newInstance());
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return object;
        }

    }

    /**
     * 反射hwTelephonyManager类的getMeid(i)方法
     *
     * @param i
     * @return
     */
    public static String hwTelephonyManagerGetMeidWithId(int i) {
        String meid = null;
        try {
            Class<?> hwTelephonyManagerClazz = Class.forName("android.telephony.HwTelephonyManager");
            Method getMeid = hwTelephonyManagerClazz.getDeclaredMethod("getMeid", int.class);//-----------------------------注意这行
            meid = (String) getMeid.invoke(hwTelephonyManagerClazz.newInstance(), i);
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.e("反射hwTelephonyManager类的getMeid方法出错：" + e.getMessage());
        } finally {
            return meid;
        }
    }

    /**
     * 反射hwTelephonyManager类的getMeid()方法
     *
     * @param
     * @return
     */
    public static String hwTelephonyManagerGetMeid() {
        String meid = null;
        try {
            Class<?> hwTelephonyManagerClazz = Class.forName("android.telephony.HwTelephonyManager");
            Method getMeid = hwTelephonyManagerClazz.getDeclaredMethod("getMeid");//-----------------------------注意这行
            meid = (String) getMeid.invoke(hwTelephonyManagerClazz.newInstance());
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.e("反射hwTelephonyManager类的getMeid方法出错：" + e.getMessage());
        } finally {
            return meid;
        }
    }

    /**
     * 反编译MSimTelephonyManager类的getCurrentPhoneType方法
     * if (2 != ((MSimTelephonyManager)localObject1).getCurrentPhoneType(k))// public int getCurrentPhoneType(int subscription)
     *
     * @return 返回网络类型
     * int NO_PHONE = 0;
     * int GSM_PHONE = 1;
     * int CDMA_PHONE = 2;
     * int SIP_PHONE  = 3;
     * int THIRD_PARTY_PHONE = 4;
     * int IMS_PHONE = 5;
     * int CDMA_LTE_PHONE = 6;
     */
//    public static int mSimTelephonyManager_getCurrentPhoneType(int subscription) {
//        int phoneNetType = 0;
//        try {
//            Class<?> mSimTelephonyManagerClazz = Class.forName("android.telephony.MSimTelephonyManager");
//            MSimTelephonyManager mSimTelephonyManager = AppHolder.getContext().getSystemService("phone_msim");
//            //
//            Method getCurrentPhoneTypeMethod = mSimTelephonyManagerClazz.getMethod("getCurrentPhoneType", int.class);
//            getCurrentPhoneTypeMethod.setAccessible(true);
//            getCurrentPhoneTypeMethod.invoke(mSimTelephonyManagerClazz.newInstance(), subscription);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.e("反编译MSimTelephonyManager类的getCurrentPhoneType方法时出错："+e.getMessage());
//        }
//        return phoneNetType;
//    }




    //不区分imei，meid 统一获取deviceId---------
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getDeviceId(int paramInt)
    {
        if (sIsDualSim) {
            try
            {
                int j = getSubscriptionIdBasedOnSlot(paramInt);
                int i = j;
                if (j == -1) {
                    i = paramInt;
                }
                String str = CommonUtilMethods.getTelephonyManager(AppHolder.getContext()).getDeviceId(i);
                return str;
            }
            catch (Exception localException)
            {
                Log.w("CSP_SimFactoryManager", "Unsupported exception thrown in getDeviceId method hence returning null");
                return null;
            }
        }
        return ((TelephonyManager)AppHolder.getContext().getSystemService("phone")).getDeviceId();
    }

}
