package com.android001.common.os.others;

/**
 * Created by xixionghui on 2017/1/6.
 */

public class OSUtils {

    public enum ROM_TYPE {

        UNKNOW("unknow",-1),
        MIUI("miui", 0),//小米
        FLYME("flyme", 1),//魅族
        EMUI("emui", 2),//华为
        COLOR_OS("colorOS", 3),//OPPO
        FUNTOUCH_UI("funTouchUI", 4),//VIVO
        EUI("eui", 5),//乐视
        COOLUI("coolUI", 6),//酷派
        AMIGO("amigo", 7),//金立
        TOUCH_WIZ("TouchWiz", 8);//三星


        String uiName;
        int order;

        ROM_TYPE(String uiName, int order) {
            this.uiName = uiName;
            this.order = order;
        }
    }

    //金立
    private static final String AMIGO_KEY = "ro.gn.amigo.systemui.support";
    //华为
    private static final String EMUI_KEY_1 = "ro.build.version.emui";
    private static final String EMUI_KEY_2 = "ro.build.hw_emui_api_level";
    //小米
    private static final String MIUI_KEY_1 = "ro.miui.ui.version.code";
    private static final String MIUI_KEY_2 = "ro.miui.ui.version.name";
    //魅族
    private static final String FLYME_KEY_1 = "ro.flyme.published";
    private static final String FLYME_KEY_2 = "ro.meizu.setupwizard.flyme";
    //乐视
    private static final String EUI_KEY = "ro.leui_oem_unlock_enable";
    //oppo
    private static final String COLOROS_KEY = "ro.build.version.opporom";
    //vivo
    private static final String FUNTOUCHUI_KEY_1 = "ro.vivo.os.name";
    private static final String FUNTOUCHUI_KEY_2 = "ro.vivo.os.build.display.id";
    //酷派
    private static final String COOLUI_KEY_1 = "ro.yulong.version.kernel";
    private static final String COOLUI_KEY_2 = "ro.yulong.version.release";
    //三星
    private static final String SAMSUNG_VALUE= "samsung";


    public static String getROMName(){
        ROM_TYPE rom_type = ROM_TYPE.UNKNOW;

        RomBuildProperties romBuildProperties = RomBuildProperties.getInstance();

        if (romBuildProperties.containsKey(AMIGO_KEY)) {//金立
            rom_type = ROM_TYPE.AMIGO;
        }else if (romBuildProperties.containsKey(EMUI_KEY_1)||romBuildProperties.containsKey(EMUI_KEY_2)) {//华为
            rom_type = ROM_TYPE.EMUI;
        }else if (romBuildProperties.containsKey(MIUI_KEY_1)||romBuildProperties.containsKey(MIUI_KEY_2)) {//小米
            rom_type = ROM_TYPE.MIUI;
        } else if (romBuildProperties.containsKey(FLYME_KEY_1)||romBuildProperties.containsKey(FLYME_KEY_2)) {//魅族
            rom_type = ROM_TYPE.FLYME;
        }else if (romBuildProperties.containsKey(EUI_KEY)){//乐视
            rom_type = ROM_TYPE.EUI;
        }else if (romBuildProperties.containsKey(COLOROS_KEY)) {//oppo
            rom_type = ROM_TYPE.COLOR_OS;
        }else if (romBuildProperties.containsKey(FUNTOUCHUI_KEY_1)||romBuildProperties.containsKey(FUNTOUCHUI_KEY_2)){//vivo
            rom_type = ROM_TYPE.FUNTOUCH_UI;
        }else if (romBuildProperties.containsKey(COOLUI_KEY_1)||romBuildProperties.containsKey(COOLUI_KEY_2)) {//酷派
            rom_type = ROM_TYPE.COOLUI;
        }else if (romBuildProperties.containsValue(SAMSUNG_VALUE)) {//三星
            rom_type = ROM_TYPE.TOUCH_WIZ;
        }

        return rom_type.uiName;
    }

}
