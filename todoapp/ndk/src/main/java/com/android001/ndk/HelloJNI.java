package com.android001.ndk;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/27 下午2:44
 */

public class HelloJNI {


    static {
        System.loadLibrary("JniInteraction");
    }

    /**
     * java static 方法调用 jni的String类型
     *
     * @return
     */
    public native static String javaStaticCallJniString();


    /**
     * java对象 调用 jni的String类型
     *
     * @return
     */
    public native String javaCallJniString();

    /**
     * jni 调用 java的String类型
     *
     * @return
     */
    public native String jniCallJavaString();
}
