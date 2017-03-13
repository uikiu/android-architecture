package com.android001.www.example;

import android.app.Application;

import com.android001.common.app.AppHolder;

/**
 * Created by xixionghui on 2017/2/28.
 */

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppHolder.getImp().initAppContext(this);
    }
}

