package com.android001.common.hardware.sim;

import android.os.Build;
import android.util.Log;

import com.android001.common.hardware.sim.common.CommonDeviceIDRetriever;
import com.android001.common.hardware.sim.gione.GioneeDeviceIDRetriever;
import com.android001.common.hardware.sim.hisense.HisenseDeviceIdRetriever;
import com.android001.common.hardware.sim.huawei.HWDeviceIDRetriever;
import com.android001.common.hardware.sim.leEco.LeEcoDeviceIdRetriever;
import com.android001.common.hardware.sim.meitu.MeituDeviceIDRetriever;
import com.android001.common.hardware.sim.meizu.MeiZuDeviceIdRetriever;
import com.android001.common.hardware.sim.oppo.OppoDeviceIDRetriever;
import com.android001.common.hardware.sim.vivo.VivoSIMInfoRetriever;
import com.android001.common.hardware.sim.xiaomi.XiaomiDeviceIDRetriever;
import com.android001.common.hardware.sim.zte.ZTEDeviceIDRetriever;
import com.android001.common.os.others.OSUtils;
import com.orhanobut.logger.Logger;


/**
 * Created by android001 on 2017/5/15.
 */

public class DeviceIdSelector {
    private static final String TAG = "DeviceIdSelector";

//    private DeviceIdSelector() {
//    }
//
//    private static class DeviceIdSelectorHolder {
//        private static final DeviceIdSelector INSTANCE = new DeviceIdSelector();
//    }
//
//    public static DeviceIdSelector getInstance() {
//        return DeviceIdSelectorHolder.INSTANCE;
//    }

    public static void addDeviceID() {
        try {
            synchronized (DeviceIdSelector.class) {
                //1. 通用方法
                CommonDeviceIDRetriever commonDeviceIDRetriever = CommonDeviceIDRetriever.getInstance();
                commonDeviceIDRetriever.addDeviceId();
                //2. 分品牌方法
                OSUtils.ROM_TYPE romType = OSUtils.getROMName();
                BaseDeviceIDRetriever deviceIDRetriever = null;
                Log.e(TAG, "手机品牌：" + Build.BRAND + "\n手机OS：" + romType.name());
                switch (romType) {
                    case MIUI://小米
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(XiaomiDeviceIDRetriever.class);
                        break;
                    case FLYME://魅族
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(MeiZuDeviceIdRetriever.class);
                        break;
                    case EMUI://华为
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(HWDeviceIDRetriever.class);
                        break;
                    case COLOR_OS://oppo
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(OppoDeviceIDRetriever.class);
                        break;
                    case FUNTOUCH_UI://VIVO
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(VivoSIMInfoRetriever.class);
                        break;
                    case EUI://乐视
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(LeEcoDeviceIdRetriever.class);
                        break;
                    case COOLUI://酷派

                        break;
                    case AMIGO://金立
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(GioneeDeviceIDRetriever.class);
                        break;
                    case TOUCH_WIZ://三星
                        break;
                    case VISION://海信
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(HisenseDeviceIdRetriever.class);
                        break;
                    case MEIOS:
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(MeituDeviceIDRetriever.class);
                        break;
                    case ZTE:
                        deviceIDRetriever = DeviceIDRetrieverFactory.createDeviceIDRetriever(ZTEDeviceIDRetriever.class);
                        break;
                    case UNKNOW://-----未知系统：启用默认
                    default://-----默认：执行所有
                        Log.e(TAG, "未知的系统，执行所有品牌获取deviceId的方法");
                        break;
                }

                if (null != deviceIDRetriever) deviceIDRetriever.addDeviceId();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }
}
