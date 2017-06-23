package com.android001.common.app.call;

import android.content.Context;
import android.content.Intent;


/**
* class design：
* @author android001
* created at 2017/6/22 下午1:51
*/
public class SimpleCaller extends BaseCaller {
    public SimpleCaller(ActiveApp activeApp) {
        super(activeApp);
    }

    @Override
    public void call(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
