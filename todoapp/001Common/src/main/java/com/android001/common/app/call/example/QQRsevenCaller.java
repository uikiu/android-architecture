package com.android001.common.app.call.example;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;

import com.android001.common.app.AppHolder;
import com.android001.common.app.call.ActiveApp;
import com.android001.common.app.call.BaseCaller;
import com.android001.common.app.utils.AppTool;
import com.android001.common.resource.sp.AppSharePreferenceMgr;
import com.android001.common.resource.sp.SharedPreferencesKey;
import com.android001.common.state.AppNetworkMgr;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/7 下午2:52
 */

public class QQRsevenCaller extends BaseCaller {

    public QQRsevenCaller(ActiveApp activeApp) {
        super(activeApp);
    }

    @Override
    public boolean fitCall() {
        boolean flagSuper = super.fitCall();
        return flagSuper && firstStartAndConnected();
    }

    /**
     * 首次启动并且已经联网
     *
     * @return
     */
    public boolean firstStartAndConnected() {
        boolean firstStarted = (boolean) AppSharePreferenceMgr.get(AppHolder.getContext(), SharedPreferencesKey.FIRST_STARTED, false);
        boolean isConnected = AppNetworkMgr.isNetworkConnected(AppHolder.getContext());
        return (!firstStarted)&&isConnected;
//        return true;
    }

    @Override
    public void call(Context context, Intent intent) {
        context.startActivity(intent);
        firstStartFinished();
    }

    private void firstStartFinished() {
        AppSharePreferenceMgr.put
                (
                        AppHolder.getContext(),
                        SharedPreferencesKey.FIRST_STARTED
                        , true
                );
    }
}
