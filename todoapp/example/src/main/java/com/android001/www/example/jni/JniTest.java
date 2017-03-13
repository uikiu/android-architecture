package com.android001.www.example.jni;

/**
 * Created by xixionghui on 2017/3/7.
 */

public class JniTest {

    static {
        System.loadLibrary("jni-test");
    }

    public native String get();
    public native void set(String str);

    public static void main(String args[]){
        JniTest jniTest = new JniTest();
        System.out.println(jniTest.get());
        jniTest.set("hello word");
    }
}
