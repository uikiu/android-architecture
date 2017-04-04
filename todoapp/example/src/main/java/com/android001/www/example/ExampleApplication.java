package com.android001.www.example;

import android.app.Application;

import com.android001.common.app.AppHolder;
import com.orhanobut.logger.Logger;

/**
 * Created by xixionghui on 2017/2/28.
 */

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("xixionghui");
        AppHolder.getImp().initAppContext(this);
    }
}

