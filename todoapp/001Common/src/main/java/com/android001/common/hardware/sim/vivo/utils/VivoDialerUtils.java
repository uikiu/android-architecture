package com.android001.common.hardware.sim.vivo.utils;

import com.android001.common.os.android.SystemPropertiesAccessor;

/**
 * Created by android001 on 2017/5/16.
 */

public class VivoDialerUtils {

    private static final String ENTRY_PROP;

    static {
        ENTRY_PROP = SystemPropertiesAccessor.get("ro.vivo.op.entry", "").toUpperCase();
    }

    public static boolean isCTCC()
    {
        return ENTRY_PROP.contains("CTCC");
    }

    public static boolean isFULL()
    {
        return ENTRY_PROP.contains("FULL");
    }

    public static boolean isUnicom()
    {
        return ("UNICOM_SC".equals(ENTRY_PROP)) || ("UNICOM".equals(ENTRY_PROP)) || ("UNICOM_RWA".equals(ENTRY_PROP)) || ("UNICOM_RWB".equals(ENTRY_PROP)) || ("FULL_UNICOM_RWA".equals(ENTRY_PROP));
    }



}
