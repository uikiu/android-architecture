package com.android001.common.hardware;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.android001.common.app.AppHolder;
import com.android001.common.shell.ShellUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by oeager on 2015/10/21.
 * email:oeager@foxmail.com
 */
public final class IMTools {


    private static final String SPLIT = ",";

    private final HashSet<String> IM_EI_SET = new HashSet<>();

    private final HashSet<String> IM_SI_SET = new HashSet<>();

    private volatile static IMTools INSTANCE = null;


    static {
        Context mContext = AppHolder.getContext();
        get().init(mContext);

    }


    private synchronized void init(Context mContext) {

        TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        addImEi(tm.getDeviceId());
        addImSi(tm.getSubscriberId());

//        if (isCompleteCheck()) {
//            return;
//        }
        //---------------------------
        initIMOfMTK(tm);
//        if (isCompleteCheck()) {
//            return;
//        }
        //---------------------------

        initIMOfQua(mContext);
//        if (isCompleteCheck()) {
//            return;
//        }
        //---------------------------
        initIMOfSpread(mContext, tm);
//        if (isCompleteCheckImEi()) {
//            return;
//        }
        //---------------------------

        initImeiExtra(mContext);
//        if (isCompleteCheckImEi()) {
//            return;
//        }
        //---------------------------

        initImeiByHuaWei();
//        if (isCompleteCheckImEi()) {
//            return;
//        }
        //---------------------------

        initImeiSM7000(mContext);
//        if (isCompleteCheckImEi()) {
//            return;
//        }
        //---------------------------
        initImeiVivo();
//        if (isCompleteCheckImEi()) {
//            return;
//        }
        //---------------------------
        initImeiHuaWei2(mContext);
//        if (isCompleteCheckImEi()) {
//            return;
//        }
        //---------------------------
        initImeiSMG3812(mContext);
//        if (isCompleteCheckImEi()) {
//            return;
//        }
        //---------------------------
//        if (isCompleteCheckImEi()) {
//            return;
//        }

        try {
            getIMEIWtihId(mContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        initImeiByLog();

    }

    @TargetApi(23)
    private void getIMEIWtihId(Context mContext) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            for (int i = 0; i < 3; i++) {
                String imei = telephonyManager.getDeviceId(i);
                if(null!=imei&&imei.trim().length()>0) {
                    addImEi(imei);
                }
            }
        } catch (Throwable t) {
        }

    }


    void addImEi(String imEi) {
        if (isEmpty(imEi)) return;
        IM_EI_SET.add(imEi);
    }

    void addImSi(String imSi) {
        if (isEmpty(imSi)) return;
        IM_SI_SET.add(imSi);
    }

    void addImEi(Set<String> set) {
        if (set == null || set.isEmpty()) return;
        IM_EI_SET.addAll(set);
    }

    void addImSi(Set<String> set) {
        if (set == null || set.isEmpty())
            return;
        IM_SI_SET.addAll(set);
    }

    boolean isCompleteCheck() {
        return IM_EI_SET.size() > 0 && IM_SI_SET.size() > 0;
    }

    boolean isCompleteCheckImEi() {
        return IM_EI_SET.size() > 0;
    }

    static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;
        if (input.equals("null")) {
            return true;
        }
        if (input.replaceAll(" ", "").equals("{}")) {
            return true;
        }


        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static IMTools get() {
        if (INSTANCE == null) {
            synchronized (IMTools.class) {
                if (INSTANCE == null) {
                    INSTANCE = new IMTools();
                }
            }
        }
        return INSTANCE;
    }

    private static boolean isImei(String imei) {
        return ((!TextUtils.isEmpty(imei)) && imei.length() > 5);
    }

    public String getImEiAnyWay() {

        if (!isCompleteCheckImEi()) {
            return "100000000000";
        }
        return translateSetToString(IM_EI_SET);
    }


    public String getImSiAnyWay() {
        TelephonyManager tm = (TelephonyManager) AppHolder.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        String imSi = tm.getSubscriberId();
        addImSi(imSi);
        if (IM_SI_SET.isEmpty()) {
            return "";
        }

        return translateSetToString(IM_SI_SET);
    }


    private static String translateSetToString(HashSet<String> set) {
        if (set == null || set.size() <= 0) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (String imei : set) {
            if (isImei(imei)) {
                buffer.append(imei).append(SPLIT);
            }
        }
        if (buffer.length() > 0) {
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        return buffer.toString();
    }

    private void initIMOfMTK(TelephonyManager tm) {
        try {
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            int simId_1, simId_2;
            try {
                Field fields1 = c.getField("GEMINI_SIM_1");
                fields1.setAccessible(true);
                simId_1 = (Integer) fields1.get(null);
                Field fields2 = c.getField("GEMINI_SIM_2");
                fields2.setAccessible(true);
                simId_2 = (Integer) fields2.get(null);
            } catch (Exception | Error ex) {
                simId_1 = 0;
                simId_2 = 1;
            }

            Method m1 = TelephonyManager.class
                    .getDeclaredMethod("getDeviceIdGemini", int.class);
            Method mx = TelephonyManager.class.getMethod(
                    "getDefault", int.class);
            Method m3 = TelephonyManager.class
                    .getDeclaredMethod("getSubscriberIdGemini", int.class);
            String imsi_1 = ((String) m3.invoke(tm, simId_1)).trim();
            String imsi_2 = ((String) m3.invoke(tm, simId_2)).trim();
            if (TextUtils.isEmpty(imsi_1)) {
                TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
                imsi_1 = (tm1.getSubscriberId()).trim();
            }
            if (TextUtils.isEmpty(imsi_2)) {
                TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);
                imsi_2 = (tm2.getSubscriberId()).trim();
            }
            addImSi(imsi_1);
            addImSi(imsi_2);
            String imei_1 = ((String) m1.invoke(tm, simId_1)).trim();
            String imei_2 = ((String) m1.invoke(tm, simId_2)).trim();
            if (TextUtils.isEmpty(imei_1)) {
                TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
                imei_1 = (tm1.getDeviceId()).trim();
            }
            if (TextUtils.isEmpty(imei_2)) {
                TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);
                imei_2 = (tm2.getDeviceId()).trim();
            }
            addImEi(imei_1);
            addImEi(imei_2);

        } catch (Exception | Error ignored) {
        }
    }

    private void initIMOfQua(Context context) {

        try {
            Class<?> cx = Class
                    .forName("android.telephony.MSimTelephonyManager");
            Object obj = context.getSystemService("phone_msim");
            int simId_1 = 0;
            int simId_2 = 1;
            Method md = cx
                    .getMethod("getDeviceId", int.class);
            String imei_1 = ((String) md.invoke(obj, simId_1)).trim();
            String imei_2 = ((String) md.invoke(obj, simId_2)).trim();
            addImEi(imei_1);
            addImEi(imei_2);
            //--------------
            Method ms = cx.getMethod("getSubscriberId", int.class);
            String imsi_1 = ((String) ms.invoke(obj, simId_1)).trim();
            String imsi_2 = ((String) ms.invoke(obj, simId_2)).trim();
            addImSi(imsi_1);
            addImSi(imsi_2);
        } catch (Exception | Error ignored) {
        }
    }

    private void initIMOfSpread(Context context, TelephonyManager tm) {
        try {

            Class<?> c = Class
                    .forName("com.android.internal.telephony.PhoneFactory");
            Method m = c.getMethod("getServiceName",
                    String.class, int.class);
            String spreadTmService = (String) m.invoke(c,
                    Context.TELEPHONY_SERVICE, 1);
            TelephonyManager tm1 = (TelephonyManager) context
                    .getSystemService(spreadTmService);
            String imei_1 = (tm.getDeviceId()).trim();

            String imei_2 = (tm1.getDeviceId()).trim();
            addImEi(imei_1);
            addImEi(imei_2);
            //-----------------
            String imsi_1 = tm.getSubscriberId().trim();

            String imsi_2 = (tm1.getSubscriberId()).trim();
            addImSi(imsi_1);
            addImSi(imsi_2);
        } catch (Exception | Error ignored) {
        }
    }

    private void initImeiByLog() {
        BufferedReader bufferedreader = null;
        String imei1 = null;
        String imei2 = null;
        String line;
        File file = new File("/etc/EngineX/Phonelog");

        if (!file.exists() || !file.canRead())
            return;

        try {
            bufferedreader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            while ((line = bufferedreader.readLine()) != null) {
                if (line.contains("IMEI1:")) {
                    imei1 = line.substring(6);

                } else if (line.contains("IMEI2:")) {
                    imei2 = line.substring(6);
                }
            }
            bufferedreader.close();

            if (!TextUtils.isEmpty(imei1) && imei1.length() > 3) {
                addImEi(imei1);
            }

            if (!TextUtils.isEmpty(imei2) && imei2.length() > 3) {
                addImEi(imei2);
            }


        } catch (Exception e) {

        } catch (Error e) {
        } finally {
            try {
                if (bufferedreader != null)
                    bufferedreader.close();
            } catch (Exception e2) {
            }
        }
    }

    @Deprecated //refuse get root permission auto
    private void initImEiByShell() {
        int index;
        String imei;
        String command = "dumpsys iphonesubinfo";

        if (!ShellUtils.checkRootPermission())
            return;

        ShellUtils.CommandResult commandResult = ShellUtils.execShellCommand(command, true, true);
        if (commandResult.result == 0 && commandResult.successMsg != null) {
            String result = commandResult.successMsg;
            result = Pattern.compile("\\s*|\t|\r|\n").matcher(result)
                    .replaceAll("");

            if (!TextUtils.isEmpty(result)
                    && (index = result.indexOf("DeviceID=")) != -1) {
                imei = result.substring(index + 9);
                addImEi(imei);
            }
        }

    }


    private void initImeiExtra(Context context) {
        String model = Build.MODEL;
        String brand = Build.BRAND.toLowerCase().trim();
        try {
            if (!(brand.contains("huawei") || brand.contains("honor"))) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                Integer Meid = 0;
                Integer sim1 = 1;
                Integer sim2 = 2;
                String strMeid;
                String strIMEI1;
                String strIMEI2;
                Method getDeviceIdMethod = telephonyManager.getClass().getMethod("getDeviceId", int.class);
                if (getDeviceIdMethod != null) {
                    strMeid = (String) getDeviceIdMethod.invoke(telephonyManager, Meid);
                    addImEi(strMeid);
                    strIMEI1 = (String) getDeviceIdMethod.invoke(telephonyManager, sim1);
                    addImEi(strIMEI1);
                    strIMEI2 = (String) getDeviceIdMethod.invoke(telephonyManager, sim2);
                    addImEi(strIMEI2);
                }

            }
        } catch (Exception | Error ignored) {

        }

    }


    private void initImeiByHuaWei() {
        String strMEID;
        try {
            Method getNVMEID = Class.forName("com.huawei.android.hwnv.HWNVFuncation").getMethod("getNUMEID");
            strMEID = (String) getNVMEID.invoke(0, 0);
            addImEi(strMEID);
        } catch (Exception | Error ignored) {
        }
    }

    private void initImeiHuaWei2(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        Class<?> sysClazz = context.getSystemService("phone").getClass();
        try {
            Method typeMethod = sysClazz.getMethod("getCurrentPhoneType");
            if (typeMethod == null) {
                return;
            }
            int correnttype = ((Integer) typeMethod.invoke(telephonyManager, 0)).intValue();

            if (correnttype == 0x1 || correnttype != 0x2) {
                return;
            }

            String strCDMAIMEI = GetCDMAIMEI();
            if (strCDMAIMEI == null || strCDMAIMEI.length() <= 0) {
                return;
            }
            addImEi(strCDMAIMEI);

        } catch (Exception | Error ignored) {
        }

    }

    private void initImeiVivo() {
        String strMEID;

        try {
            Method GetService = Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class);
            IBinder PhoneBinder = (IBinder) GetService.invoke(null, "phone");
            Method method = Class.forName("com.android.internal.telephony.ITelephony$Stub")
                    .getMethod("asInterface", IBinder.class);
            Object invoke = method.invoke(0, PhoneBinder);
            strMEID = (String) invoke.getClass().getMethod("getMeid").invoke(invoke);
            addImEi(strMEID);

        } catch (Exception | Error e) {
            // TODO Auto-generated catch block
        }
    }

    private void initImeiSM7000(Context context) {
        String IMEI2;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone2");
        try {
            Method method = telephonyManager.getClass().getMethod("getImeiInCDMAGSMPhone");
            if (method == null) {
                return;
            }
            IMEI2 = method.invoke(telephonyManager).toString();
            addImEi(IMEI2);

        } catch (Exception | Error e) {
        }

    }

    private void initImeiSMG3812(Context context) {
        String IMEI1;
        String IMEI2;
        TelephonyManager tmManager = (TelephonyManager) context.getSystemService("phone");
        try {
            Method method_getDeviceIdDs = Class.forName("android.telephony.TelephonyManager").getMethod("getDeviceIdDs", int.class);
            if (method_getDeviceIdDs == null) {
                return;
            }
            IMEI1 = (String) method_getDeviceIdDs.invoke(tmManager, 0);
            IMEI2 = (String) method_getDeviceIdDs.invoke(tmManager, 1);
            addImEi(IMEI1);
            addImEi(IMEI2);


        } catch (Exception | Error e) {
            // TODO Auto-generated catch block
        }
    }

    private String GetCDMAIMEI() {
        try {
            Class<?> sysProClazz = Class.forName("android.os.SystemProperties");
            Method methodGet = sysProClazz.getMethod("get", String.class);
            if (methodGet == null) {
                return null;
            }
            Object invoke = methodGet.invoke(sysProClazz.newInstance(), "telephony.lteOnCdmaDevice");
            if (!invoke.equals("1")) {
                return null;
            }
            Method getNVIMEI = Class.forName("com.huawei.android.hwnv.HWNVFuncation").getMethod("getNVIMEI");
            if (getNVIMEI == null) {
                return null;
            }
            getNVIMEI.setAccessible(true);
            return (String) getNVIMEI.invoke(0, 0);
        } catch (Exception e) {
            return null;
        } catch (Error ignored) {
        }
        return null;
    }
}
