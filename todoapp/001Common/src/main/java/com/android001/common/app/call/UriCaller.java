package com.android001.common.app.call;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/22 下午1:45
 */

public class UriCaller extends BaseCaller
{
    public static final String URL_START_WITH = "http://";
    private final Uri mUri;//uri---->intent----->PendingIntent---->Notification

    public UriCaller(ActiveApp activeApp, Uri uri)
    {
        super(activeApp);
        this.mUri = uri;
    }

    @Override
    public void call(Context context, Intent intent)
    {
        if (null != mUri)
            intent.setData(mUri);
        context.startActivity(intent);
    }





    /**
     * 将String类型的url转换为uri
     * @param url
     * @return
     */
    public static Uri getIntentDataUrl(String url)
    {
        if (TextUtils.isEmpty(url)) return null;
        if (!url.startsWith(URL_START_WITH))
            url = URL_START_WITH + url;
        return Uri.parse(url);
    }

    /**
     * 将String类型的uri转换为Uri对象
     * @param uri
     * @return
     */
    public static Uri getIntentDataUri(String uri)
    {
        if (TextUtils.isEmpty(uri)) return null;
        return Uri.parse(uri);
    }
}
