package com.android001.storage.external;

import android.os.Environment;

/**
 * Created by xixionghui on 2017/5/18.
 */

public class StorageChecker {


    /* Checks if external storage is available for read and write
    外部存储是否可读可写
    */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /* Checks if external storage is available to at least read
    是否可读
    */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



}
