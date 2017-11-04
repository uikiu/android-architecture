package com.android001.www.example.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
* class design：
* @author android001
* created at 2017/9/15 下午3:19
 * 禁止锁屏
*/

public class ScreenManager {

    public static void setLockScreen(){

        try {
            Class<?> clazz = Class.forName("com.android.systemui.keyguard.KeyguardViewMediator");
            Method setKeyguardEnabledMethod = clazz.getDeclaredMethod("setKeyguardEnabled",boolean.class);
            setKeyguardEnabledMethod.setAccessible(true);
            setKeyguardEnabledMethod.invoke(clazz.newInstance(),false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
