package com.android001.common.hardware.sim.huawei;

import android.os.Build;

import com.android001.common.hardware.sim.BaseDeviceIDRetriever;
import com.android001.common.hardware.sim.common.DeviceIdDAO;

/**
 * Created by xixionghui on 2017/5/10.
 */

public class HWDeviceIDRetriever extends BaseDeviceIDRetriever {


/*    private static class SpecialCharSequenceMgrHolder {
        private static final HWDeviceIDRetriever HW_DEVICE_ID_RETRIEVER = new HWDeviceIDRetriever();
    }

    private HWDeviceIDRetriever() {
        init();
    }

    public static HWDeviceIDRetriever getInstance() {
        return SpecialCharSequenceMgrHolder.HW_DEVICE_ID_RETRIEVER;
    }

    static {
        getInstance().init();
    }*/



    public HWDeviceIDRetriever() {
        init();
    }

    @Override
    public void addDeviceId() {

        try {
            switch (SimFactoryManager.getSimCombination()) {
                case SimFactoryManager.CDMA_GSM:
                    addCDMA_GSM();
                    break;
                case SimFactoryManager.UMTS_GSM:
                    addUMTS_GSM();
                    break;
                case SimFactoryManager.UNKNOW:
                default:
                    addCDMA_GSM();
                    addUMTS_GSM();
                    break;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private void addCDMA_GSM() {

        try {
            //以下均为全万通双卡情况
            int subscriptionId = SimFactoryManager.getUserDefaultSubscription();
            if (subscriptionId == 0) {
                String imei1 = SimFactoryManager.getImei(0);
                String imei2 = SimFactoryManager.getImei(1);

                DeviceIdDAO.getInstance().addDeviceId(imei1);
                DeviceIdDAO.getInstance().addDeviceId(imei2);
            }

            String meid1 = SimFactoryManager.getMeid(0);
            DeviceIdDAO.getInstance().addDeviceId(meid1);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }


    private void addUMTS_GSM(){

        try {
            String imei1 = SimFactoryManager.getImei(0);
            String imei2 = SimFactoryManager.getImei(1);

            DeviceIdDAO.getInstance().addDeviceId(imei1);
            DeviceIdDAO.getInstance().addDeviceId(imei2);

            if (Build.VERSION.SDK_INT>=23) {
                String deviceId1 = SimFactoryManager.getDeviceId(0);
                String deviceId2 = SimFactoryManager.getDeviceId(1);

                DeviceIdDAO.getInstance().addDeviceId(deviceId1);
                DeviceIdDAO.getInstance().addDeviceId(deviceId2);

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
