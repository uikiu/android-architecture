package com.android001.service.common.proxy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

/**
* class design：
* @author android001
* created at 2017/9/28 下午3:24
*/

public class ReStartServiceProxy {

    Context mContext;
    public ReStartServiceProxy(Context context){
        this.mContext = context;
    }

    public void onStartCommand(Intent intent){

    }

}
