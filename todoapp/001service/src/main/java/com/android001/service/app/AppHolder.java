package com.android001.service.app;

import android.content.Context;

/**
* class design：
* @author android001
* created at 2017/6/8 上午9:39
*/

public class AppHolder {
    private AppHolder(){}

    private static class Holder {
       private static final AppHolder INSTANCE = new AppHolder();
    }

    public static AppHolder getInstance() {
        return Holder.INSTANCE;
    }

    //---
    private Context mContext;
    public static Context getContext(){
        return getInstance().mContext;
    }
    public void initAppContext(Context context){
        mContext = context.getApplicationContext();
    }
}
