package com.android001.ndk;

/**
* class design：
* @author android001
* created at 2017/6/27 下午2:44
*/

public class Hello {


    static {
        System.loadLibrary("hello-jni");
    }
    public native String helloJni();
}
