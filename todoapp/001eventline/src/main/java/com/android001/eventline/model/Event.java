package com.android001.eventline.model;

/**
 * Created by xixionghui on 2017/5/3.
 * 参考ComponentName
 */

public class Event {
    private final String mPackage;
    private final String mClass;
    private final String mMethod;

    public Event(String pkg, String cls, String method) {
        if (pkg == null) throw new NullPointerException("package name is null");
        if (cls == null) throw new NullPointerException("class name is null");
        if (method == null) throw new NullPointerException("method name is null");
        mPackage = pkg;
        mClass = cls;
        mMethod = method;
    }

    //---


}
