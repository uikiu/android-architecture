package com.android001.common.hardware.sim;

import android.util.Log;

import com.android001.common.hardware.sim.common.CommonDeviceIDRetriever;
import com.android001.common.hardware.sim.gione.GioneeDeviceIDRetriever;
import com.android001.common.hardware.sim.huaweiContact.HWDeviceIDRetriever;
import com.android001.common.hardware.sim.leEco.LeEcoDeviceIdRetriever;
import com.android001.common.hardware.sim.meizu.MeiZuDeviceIdRetriever;
import com.android001.common.hardware.sim.oppo.OppoDeviceIDRetriever;
import com.android001.common.hardware.sim.vivo.VivoSIMInfoRetriever;
import com.android001.common.hardware.sim.vivo.utils.VivoSimCardUtils;
import com.android001.common.hardware.sim.vivo.utils.VivoTelephonyUtils;
import com.android001.common.os.android.SystemPropertiesAccessor;
import com.android001.common.os.others.OSUtils;
import com.orhanobut.logger.Logger;


/**
 * Created by xixionghui on 2017/5/15.
 */

public class DeviceIdSelector {
    private static final String TAG = "DeviceIdSelector";

    private DeviceIdSelector() {
    }

    private static class DeviceIdSelectorHolder {
        private static final DeviceIdSelector INSTANCE = new DeviceIdSelector();
    }

    public static DeviceIdSelector getInstance() {
        return DeviceIdSelectorHolder.INSTANCE;
    }

    public void addDeviceID() {
        //1. 通用方法
        CommonDeviceIDRetriever commonDeviceIDRetriever = CommonDeviceIDRetriever.getInstance();
        commonDeviceIDRetriever.addDeviceId();
        //2. 分品牌方法
        OSUtils.ROM_TYPE romType = OSUtils.getROMName();
        switch (romType) {
            case UNKNOW://-----未知系统：启用默认
                Log.e(TAG,"未知的系统");
                CommonDeviceIDRetriever.getInstance().addDeviceId();
            case MIUI://小米
                break;
            case FLYME://魅族
                MeiZuDeviceIdRetriever meiZuDeviceIdRetriever = new MeiZuDeviceIdRetriever();
                meiZuDeviceIdRetriever.addDeviceId();
                break;
            case EMUI://华为
                HWDeviceIDRetriever.getInstance().addDeviceId();
                break;
            case COLOR_OS://oppo
                OppoDeviceIDRetriever oppoDeviceIDRetriever = new OppoDeviceIDRetriever();
                oppoDeviceIDRetriever.addDeviceId();
                break;
            case FUNTOUCH_UI://VIVO
                VivoSIMInfoRetriever vivoSIMInfoRetriever = new VivoSIMInfoRetriever();
                vivoSIMInfoRetriever.addDeviceId();

                break;
            case EUI://乐视
                LeEcoDeviceIdRetriever leEcoDeviceIdRetriever = new LeEcoDeviceIdRetriever();
                leEcoDeviceIdRetriever.addDeviceId();
                break;
            case COOLUI://酷派

                break;
            case AMIGO://金立
                GioneeDeviceIDRetriever gioneeDeviceIDRetriever = new GioneeDeviceIDRetriever();
                gioneeDeviceIDRetriever.addDeviceId();
                break;
            case TOUCH_WIZ://三星
                break;
            default://-----默认：执行所有
                Log.e(TAG,"未知的系统，执行所有品牌获取deviceId的方法");
                break;
        }
    }
}
