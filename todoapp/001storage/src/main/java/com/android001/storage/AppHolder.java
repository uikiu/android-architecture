package com.android001.storage;

import android.content.Context;

/**
 * Created by oeager on 16-5-24.
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
