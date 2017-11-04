package com.android001.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oeager on 2015/10/23.
 * email:oeager@foxmail.com
 */
public final class DateUtils {

    private final static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(long time){
        Date date = new Date(time);
        return formatter.format(date);
    }
}
    