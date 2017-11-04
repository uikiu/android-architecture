package com.android001.www.example;

import android.app.Application;

//import com.android.system.ReporterApi;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by android001 on 2017/2/28.
 */

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        com.android001.www.example.AppHolder.getImp().initAppContext(this);
        com.android001.common.app.AppHolder.getImp().initAppContext(this);
        com.android001.ui.app.AppHolder.getInstance().initAppContext(this);
        com.android001.service.app.AppHolder.getInstance().initAppContext(this);
//        com.dlht.common.app.AppHolder.getImp().initAppContext(this);
//        com.android001.storage.AppHolder.getImp().initAppContext(this);

        //计数器sdk
//        ReporterApi.onApplicationCreated(this);
//        ReporterApi.startService(this, ReporterApi.POST_AS_REGULAR);

    }
}

