package com.android001.common.app;

import android.content.Context;

/**
* class design：
* @author android001
* created at 2017/6/6 上午9:25
*/
public final class AppHolder {

    private Context mContext;


    public void initAppContext(Context context) {
        mContext = context.getApplicationContext();
    }

    private AppHolder() {
    }


    static class Holder {

        private final static AppHolder IMPL = new AppHolder();
    }

    public static AppHolder getImp() {
        return Holder.IMPL;
    }

    public static Context getContext() {
        return getImp().mContext;
    }

}
