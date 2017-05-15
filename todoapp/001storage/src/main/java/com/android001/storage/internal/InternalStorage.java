package com.android001.storage.internal;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by xixionghui on 2017/4/25.
 */

public class InternalStorage {

    public static synchronized boolean storeContent2File(Context context, String fileName, String content) {
        try {
            FileOutputStream out = context.openFileOutput(fileName, MODE_PRIVATE);
            out.write(content.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static synchronized String readContentFromFile(Context context, String fileName) {
        String content = null;
        try {
            FileInputStream in = context.openFileInput(fileName);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[128];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                out.flush();
            }
            out.close();
            in.close();

            content = new String(out.toByteArray());
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
        return content;
    }
}

